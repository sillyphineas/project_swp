package controller;

import com.google.gson.Gson;
import entity.Feedback;
import entity.Order;
import entity.OrderInformation;
import entity.User;
import helper.Authorize;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import model.DAOFeedback;
import model.DAOOrder;
import model.DAOOrderInformation;
import org.json.JSONObject;
import jakarta.servlet.annotation.MultipartConfig;
import java.util.Collection;
import java.util.Comparator;
import model.DAOPayment;
import model.DAOUser;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
@WebServlet(name = "CustomerOrderController", urlPatterns = {"/CustomerOrderController"})
public class CustomerOrderController extends HttpServlet {

    private Map<Integer, List<OrderInformation>> groupByOrderId(List<OrderInformation> list) {
        Map<Integer, List<OrderInformation>> map = new LinkedHashMap<>();
        for (OrderInformation info : list) {
            map.computeIfAbsent(info.getId(), k -> new ArrayList<>()).add(info);
        }
        return map;
    }

    private List<Map.Entry<Integer, List<OrderInformation>>> mapToList(Map<Integer, List<OrderInformation>> map) {
        return new ArrayList<>(map.entrySet());
    }

    private <T> List<T> paginateList(List<T> fullList, int page, int itemsPerPage) {
        if (fullList == null || fullList.isEmpty()) {
            return Collections.emptyList();
        }
        int start = (page - 1) * itemsPerPage;
        if (start >= fullList.size()) {
            return Collections.emptyList();
        }
        int end = Math.min(start + itemsPerPage, fullList.size());
        return fullList.subList(start, end);
    }

    private int parsePage(String pageParam) {
        if (pageParam == null) {
            return 1;
        }
        try {
            int p = Integer.parseInt(pageParam);
            return (p < 1) ? 1 : p;
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head><title>CustomerOrderController</title></head><body>");
            out.println("<h1>CustomerOrderController at " + request.getContextPath() + "</h1>");
            out.println("</body></html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        if (!Authorize.isAccepted(user, "/CustomerOrderController")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }

        DAOOrderInformation daoOdInf = new DAOOrderInformation();
        DAOOrder daoOrder = new DAOOrder();
        DAOFeedback dao = new DAOFeedback();

        String service = request.getParameter("service");
        if (service == null) {
            service = "displayAllOrders";
        }

        if (service.equals("displayAllOrders")) {
            List<OrderInformation> allFlat = daoOdInf.getAllOrderInformation();

            // Đánh dấu feedbackExists cho từng OrderDetail
            for (OrderInformation o : allFlat) {
                boolean feedbackExists = dao.isFeedbackExists(o.getOrderDetailID());
                request.setAttribute("feedbackExists_" + o.getOrderDetailID(), feedbackExists);
            }

            // Đồng bộ orderStatus <-> shippingStatus (NHƯNG bỏ qua nếu orderStatus = "Completed")
            for (OrderInformation o : allFlat) {
                Order od = daoOrder.getOrderById(o.getId());
                if (od == null) {
                    continue;
                }
                // ---- BỎ QUA nếu orderStatus đã là Completed ----
                if ("Completed".equalsIgnoreCase(od.getOrderStatus())) {
                    // Không ép lại orderStatus
                    continue;
                }

                // Còn lại, vẫn giữ logic ép Shipping / Delivered từ shippingStatus
                if ("Shipping".equalsIgnoreCase(o.getShippingStatus())) {
                    od.setOrderStatus("Shipping");
                    daoOrder.updateOrder(od);
                    o.setOrderStatus("Shipping");
                } else if ("Delivered".equalsIgnoreCase(o.getShippingStatus())) {
                    od.setOrderStatus("Delivered");
                    daoOrder.updateOrder(od);
                    o.setOrderStatus("Delivered");
                }
            }

            // Gom nhóm OrderInformation theo orderId
            Map<Integer, List<OrderInformation>> allMap = groupByOrderId(allFlat);
            for (Map.Entry<Integer, List<OrderInformation>> entry : allMap.entrySet()) {
                List<OrderInformation> lines = entry.getValue();
                lines.sort(Comparator.comparingInt(OrderInformation::getOrderDetailID));
            }

            // Tạo listAll & phân loại theo orderStatus
            List<Map.Entry<Integer, List<OrderInformation>>> allList = mapToList(allMap);
            List<Map.Entry<Integer, List<OrderInformation>>> awaitingList = new ArrayList<>();
            List<Map.Entry<Integer, List<OrderInformation>>> shippingList = new ArrayList<>();
            List<Map.Entry<Integer, List<OrderInformation>>> deliveredList = new ArrayList<>();
            List<Map.Entry<Integer, List<OrderInformation>>> completedList = new ArrayList<>();
            List<Map.Entry<Integer, List<OrderInformation>>> canceledList = new ArrayList<>();
            List<Map.Entry<Integer, List<OrderInformation>>> refundList = new ArrayList<>();

            // Sắp xếp giảm dần orderId
            allList.sort((o1, o2) -> Integer.compare(o2.getKey(), o1.getKey()));

            // Phân loại
            for (Map.Entry<Integer, List<OrderInformation>> e : allList) {
                if (e.getValue().isEmpty()) {
                    continue;
                }
                String status = e.getValue().get(0).getOrderStatus();
                if ("Cancel".equalsIgnoreCase(status)) {
                    canceledList.add(e);
                } else if ("Refund".equalsIgnoreCase(status)) {
                    refundList.add(e);
                } else if ("Shipping".equalsIgnoreCase(status)) {
                    shippingList.add(e);
                } else if ("Delivered".equalsIgnoreCase(status)) {
                    deliveredList.add(e);
                } else if ("Completed".equalsIgnoreCase(status)) {
                    completedList.add(e);
                } else {
                    // Mặc định => Awaiting Pickup
                    awaitingList.add(e);
                }
            }

            // Sắp xếp các list canceled / refund / completed sao cho "vừa cập nhật" nổi lên đầu
            // canceled
            String newlyCanceledParam = request.getParameter("newlyCanceled");
            if (newlyCanceledParam != null) {
                try {
                    int newlyCanceledId = Integer.parseInt(newlyCanceledParam);
                    canceledList.sort((e1, e2) -> {
                        int id1 = e1.getKey();
                        int id2 = e2.getKey();
                        if (id1 == newlyCanceledId && id2 != newlyCanceledId) {
                            return -1;
                        }
                        if (id2 == newlyCanceledId && id1 != newlyCanceledId) {
                            return 1;
                        }
                        return 0;
                    });
                } catch (NumberFormatException ex) {
                }
            }
            // refund
            String newlyRefundedParam = request.getParameter("newlyRefunded");
            if (newlyRefundedParam != null) {
                try {
                    int newlyRefundedId = Integer.parseInt(newlyRefundedParam);
                    refundList.sort((e1, e2) -> {
                        int id1 = e1.getKey();
                        int id2 = e2.getKey();
                        if (id1 == newlyRefundedId && id2 != newlyRefundedId) {
                            return -1;
                        }
                        if (id2 == newlyRefundedId && id1 != newlyRefundedId) {
                            return 1;
                        }
                        return 0;
                    });
                } catch (NumberFormatException ex) {
                }
            }
            // completed
            String newlyCompletedParam = request.getParameter("newlyCompleted");
            if (newlyCompletedParam != null) {
                try {
                    int newlyCompletedId = Integer.parseInt(newlyCompletedParam);
                    completedList.sort((e1, e2) -> {
                        int id1 = e1.getKey();
                        int id2 = e2.getKey();
                        if (id1 == newlyCompletedId && id2 != newlyCompletedId) {
                            return -1;
                        }
                        if (id2 == newlyCompletedId && id1 != newlyCompletedId) {
                            return 1;
                        }
                        return 0;
                    });
                } catch (NumberFormatException ex) {
                }
            }

            // Phân trang cho từng tab
            int itemsPerPage = 3;
            int pageAll = parsePage(request.getParameter("pageAll"));
            int pageAwaiting = parsePage(request.getParameter("pageAwaiting"));
            int pageShipping = parsePage(request.getParameter("pageShipping"));
            int pageDelivered = parsePage(request.getParameter("pageDelivered"));
            int pageCanceled = parsePage(request.getParameter("pageCanceled"));
            int pageRefund = parsePage(request.getParameter("pageRefund"));
            int pageCompleted = parsePage(request.getParameter("pageCompleted"));

            // Tạo list Paged
            List<Map.Entry<Integer, List<OrderInformation>>> allPaged = paginateList(allList, pageAll, itemsPerPage);
            int totalAllPages = (int) Math.ceil(allList.size() / (double) itemsPerPage);

            List<Map.Entry<Integer, List<OrderInformation>>> awaitingPaged
                    = paginateList(awaitingList, pageAwaiting, itemsPerPage);
            int totalAwaitingPages = (int) Math.ceil(awaitingList.size() / (double) itemsPerPage);

            List<Map.Entry<Integer, List<OrderInformation>>> shippingPaged
                    = paginateList(shippingList, pageShipping, itemsPerPage);
            int totalShippingPages = (int) Math.ceil(shippingList.size() / (double) itemsPerPage);

            List<Map.Entry<Integer, List<OrderInformation>>> deliveredPaged
                    = paginateList(deliveredList, pageDelivered, itemsPerPage);
            int totalDeliveredPages = (int) Math.ceil(deliveredList.size() / (double) itemsPerPage);

            List<Map.Entry<Integer, List<OrderInformation>>> canceledPaged
                    = paginateList(canceledList, pageCanceled, itemsPerPage);
            int totalCanceledPages = (int) Math.ceil(canceledList.size() / (double) itemsPerPage);

            List<Map.Entry<Integer, List<OrderInformation>>> refundPaged
                    = paginateList(refundList, pageRefund, itemsPerPage);
            int totalRefundPages = (int) Math.ceil(refundList.size() / (double) itemsPerPage);

            List<Map.Entry<Integer, List<OrderInformation>>> completedPaged
                    = paginateList(completedList, pageCompleted, itemsPerPage);
            int totalCompletedPages = (int) Math.ceil(completedList.size() / (double) itemsPerPage);

            // Gửi attribute cho JSP
            request.setAttribute("allPaged", allPaged);
            request.setAttribute("awaitingPickupPaged", awaitingList.isEmpty() ? new ArrayList<>() : awaitingPaged);
            request.setAttribute("shippingPaged", shippingPaged);
            request.setAttribute("deliveredPaged", deliveredPaged);
            request.setAttribute("completedPaged", completedPaged);
            request.setAttribute("canceledPaged", canceledPaged);
            request.setAttribute("refundPaged", refundPaged);

            request.setAttribute("currentAllPage", pageAll);
            request.setAttribute("currentAwaitingPage", pageAwaiting);
            request.setAttribute("currentShippingPage", pageShipping);
            request.setAttribute("currentDeliveredPage", pageDelivered);
            request.setAttribute("currentCompletedPage", pageCompleted);
            request.setAttribute("currentCanceledPage", pageCanceled);
            request.setAttribute("currentRefundPage", pageRefund);

            request.setAttribute("totalAllPages", totalAllPages);
            request.setAttribute("totalAwaitingPages", totalAwaitingPages);
            request.setAttribute("totalShippingPages", totalShippingPages);
            request.setAttribute("totalDeliveredPages", totalDeliveredPages);
            request.setAttribute("totalCompletedPages", totalCompletedPages);
            request.setAttribute("totalCanceledPages", totalCanceledPages);
            request.setAttribute("totalRefundPages", totalRefundPages);

            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/my-orders.jsp");
            rd.forward(request, response);

        } else if (service.equals("cancelOrder")) {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            Order order = new DAOOrder().getOrderById(orderId);

            JSONObject json = new JSONObject();
            if (order == null) {
                json.put("success", false);
                json.put("message", "Order not found.");
            } else if (!"Awaiting Pickup".equalsIgnoreCase(order.getOrderStatus())) {
                json.put("success", false);
                json.put("message", "You can only cancel orders in Awaiting Pickup status.");
            } else {
                boolean updated = new DAOOrder().updateStatus(orderId, "Cancel");
                if (updated) {
                    json.put("success", true);
                    json.put("message", "Order canceled successfully.");
                    json.put("newlyCanceledId", orderId);
                } else {
                    json.put("success", false);
                    json.put("message", "Unable to cancel order due to an internal error.");
                }
            }
            response.setContentType("application/json");
            response.getWriter().write(json.toString());

        } else if (service.equals("refundOrder")) {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            Order order = daoOrder.getOrderById(orderId);

            JSONObject json = new JSONObject();
            if (order == null) {
                json.put("success", false);
                json.put("message", "Order not found.");
            } else {
                String orderStatus = order.getOrderStatus();
                DAOPayment daoPayment = new DAOPayment();
                String paymentStatus = daoPayment.getPaymentByOrderId(orderId).getPaymentStatus();
                if (!"Cancel".equalsIgnoreCase(orderStatus) || !"Paid".equalsIgnoreCase(paymentStatus)) {
                    json.put("success", false);
                    json.put("message", "Refund is only allowed for orders that are Cancel and Paid.");
                } else {
                    boolean updatedOrder = daoOrder.updateStatus(orderId, "Refund");
                    boolean updatedPayment = new DAOPayment().updatePaymentStatus(orderId, "Refund");

                    if (updatedOrder && updatedPayment) {
                        json.put("success", true);
                        json.put("message", "Order refunded successfully.");
                        json.put("newlyRefundedId", orderId);
                    } else {
                        json.put("success", false);
                        json.put("message", "Unable to process refund due to an internal error.");
                    }
                }
            }
            response.setContentType("application/json");
            response.getWriter().write(json.toString());

        } else if (service.equals("confirmDelivered")) {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            // Lấy order
            Order order = new DAOOrder().getOrderById(orderId);

            // Lấy user từ session
            user = (User) session.getAttribute("user");

            // Tính điểm, ví dụ: tổng tiền / 100000
            int point = (int) order.getTotalPrice() / 100000;

            // Tạo JSON trả về
            JSONObject json = new JSONObject();

            // Kiểm tra các điều kiện
            if (order == null) {
                json.put("success", false);
                json.put("message", "Order not found.");
            } else if (!"Delivered".equalsIgnoreCase(order.getOrderStatus())) {
                json.put("success", false);
                json.put("message", "You can only confirm orders that are in Delivered status.");
            } else {
                // Update trạng thái sang Completed
                boolean updated = new DAOOrder().updateStatus(orderId, "Completed");
                if (updated) {
                    // ========== CỘNG ĐIỂM CHO USER ==========
                    // 1) Cộng điểm trên session
                    int oldPoint = user.getPoint();
                    int newPoint = oldPoint + point;
                    user.setPoint(newPoint);

                    // 2) Cập nhật DB (nếu bạn có bảng Users)
                    //    Ví dụ gọi DAOUser để update
                    DAOUser daoUser = new DAOUser();
                    daoUser.updateUserPoint(user.getId(), newPoint);

                    json.put("success", true);
                    json.put("message", "Order confirmed as completed successfully.");
                    json.put("newlyCompleted", orderId);
                } else {
                    json.put("success", false);
                    json.put("message", "Unable to confirm order due to an internal error.");
                }
            }

            response.setContentType("application/json");
            response.getWriter().write(json.toString());
        } else {
            processRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String service = request.getParameter("service");
        DAOFeedback dao = new DAOFeedback();

        System.out.println("Service: " + service);

        if (service == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Service parameter is missing.");
            return;
        }

        if (service.equals("SubmitFeedback")) {
            System.out.println("Processing Feedback Submission...");

            try {
                int reviewerID = Integer.parseInt(request.getParameter("reviewerID"));
                int productID = Integer.parseInt(request.getParameter("product_id"));
                int orderdetailID = Integer.parseInt(request.getParameter("orderdetailID"));
                int rating = Integer.parseInt(request.getParameter("rating"));
                String content = request.getParameter("content");

                if (dao.isFeedbackExists(orderdetailID)) {
                    response.sendError(HttpServletResponse.SC_CONFLICT, "Feedback already exists for this order.");
                    return;
                }

                // Upload file (nếu có)
                Collection<Part> fileParts = request.getParts();
                List<String> imagePaths = new ArrayList<>();
                String uploadPath = getServletContext().getRealPath("/") + "uploads";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                for (Part filePart : fileParts) {
                    if (filePart.getName().equals("images") && filePart.getSize() > 0) {
                        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                        String filePath = "uploads/" + fileName;
                        filePart.write(uploadPath + File.separator + fileName);
                        imagePaths.add(filePath);
                    }
                }

                String imagesJson = new Gson().toJson(imagePaths);
                String reviewTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                boolean isDisabled = false;
                String Status = "visible";

                Feedback feedback = new Feedback(
                        orderdetailID,
                        reviewerID,
                        productID,
                        reviewTime,
                        rating,
                        content,
                        imagesJson,
                        isDisabled,
                        Status
                );

                boolean success = dao.insertFeedback(feedback);

                if (success) {
                    // Chuyển sang trang ListFeedbackWithId
                    response.sendRedirect("FeedBackController?service=ListFeedbackWithId&productId=" + productID);
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error submitting feedback.");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input data.");
                e.printStackTrace();
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing feedback.");
                e.printStackTrace();
            }
        } else {
            processRequest(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Customer Order Management Controller with stable product ordering & feedback logic.";
    }
}
