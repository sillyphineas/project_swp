package controller;

import com.google.gson.Gson;
import entity.Feedback;
import entity.Order;
import entity.OrderDetail;
import entity.OrderInformation;
import entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import model.DAOFeedback;
import model.DAOOrder;
import model.DAOOrderInformation;
import org.json.JSONObject;
import jakarta.servlet.annotation.MultipartConfig;
import java.io.File;
import java.util.Collection;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
@WebServlet(name = "CustomerOrderController", urlPatterns = {"/CustomerOrderController"})
public class CustomerOrderController extends HttpServlet {

    private <T> List<T> paginateList(List<T> fullList, int page, int itemsPerPage) {
        if (fullList == null || fullList.isEmpty()) {
            return new ArrayList<>();
        }
        int start = (page - 1) * itemsPerPage;
        if (start >= fullList.size()) {
            return new ArrayList<>();
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
        // Placeholder nếu cần
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

        DAOOrderInformation daoOdInf = new DAOOrderInformation();
        DAOOrder daoOrder = new DAOOrder();
        DAOFeedback dao = new DAOFeedback();
        String service = request.getParameter("service");
        Integer customerID = (Integer) session.getAttribute("userID");
        request.setAttribute("sessionUserId", customerID);

        if (service == null) {
            service = "displayAllOrders";
        }

        if (service.equals("displayAllOrders")) {
            List<OrderInformation> orderInformations = daoOdInf.getAllOrderInformation();
            for (OrderInformation o : orderInformations) {
                boolean feedbackExists = dao.isFeedbackExists(o.getOrderDetailID());
                request.setAttribute("feedbackExists_" + o.getOrderDetailID(), feedbackExists);
                System.out.println("feedbackExists "+feedbackExists);
            }
            List<OrderInformation> awaitingPickupList = new ArrayList<>();
            List<OrderInformation> shippingList = new ArrayList<>();
            List<OrderInformation> deliveredList = new ArrayList<>();
            List<OrderInformation> cancelList = new ArrayList<>();
            List<OrderInformation> refundList = new ArrayList<>();

            for (OrderInformation o : orderInformations) {
                String shipping = o.getShippingStatus();
                if ("Shipping".equalsIgnoreCase(shipping)) {
                    Order od = daoOrder.getOrderById(o.getId());
                    od.setOrderStatus("Shipping");
                    daoOrder.updateOrder(od);
                    o.setOrderStatus("Shipping");
                    shippingList.add(o);
                } else if ("Delivered".equalsIgnoreCase(shipping)) {
                    Order od = daoOrder.getOrderById(o.getId());
                    od.setOrderStatus("Delivered");
                    daoOrder.updateOrder(od);
                    o.setOrderStatus("Delivered");
                    deliveredList.add(o);
                } else {
                    String status = o.getOrderStatus();
                    if ("Cancel".equalsIgnoreCase(status)) {
                        cancelList.add(o);
                    } else if ("Refund".equalsIgnoreCase(status)) {
                        refundList.add(o);
                    } else {
                        awaitingPickupList.add(o);
                    }
                }
            }
            int itemsPerPage = 3;
            int pageAll = parsePage(request.getParameter("pageAll"));
            int pageAwaiting = parsePage(request.getParameter("pageAwaiting"));
            int pageShipping = parsePage(request.getParameter("pageShipping"));
            int pageDelivered = parsePage(request.getParameter("pageDelivered"));
            int pageCanceled = parsePage(request.getParameter("pageCanceled"));
            int pageRefund = parsePage(request.getParameter("pageRefund"));

            List<OrderInformation> allOrdersPaged = paginateList(orderInformations, pageAll, itemsPerPage);
            List<OrderInformation> awaitingPickupPaged = paginateList(awaitingPickupList, pageAwaiting, itemsPerPage);
            List<OrderInformation> shippingPaged = paginateList(shippingList, pageShipping, itemsPerPage);
            List<OrderInformation> deliveredPaged = paginateList(deliveredList, pageDelivered, itemsPerPage);
            List<OrderInformation> cancelPaged = paginateList(cancelList, pageCanceled, itemsPerPage);
            List<OrderInformation> refundPaged = paginateList(refundList, pageRefund, itemsPerPage);

            int totalAllPages = (int) Math.ceil(orderInformations.size() / (double) itemsPerPage);
            int totalAwaitingPages = (int) Math.ceil(awaitingPickupList.size() / (double) itemsPerPage);
            int totalShippingPages = (int) Math.ceil(shippingList.size() / (double) itemsPerPage);
            int totalDeliveredPages = (int) Math.ceil(deliveredList.size() / (double) itemsPerPage);
            int totalCanceledPages = (int) Math.ceil(cancelList.size() / (double) itemsPerPage);
            int totalRefundPages = (int) Math.ceil(refundList.size() / (double) itemsPerPage);

            request.setAttribute("allOrdersPaged", allOrdersPaged);
            request.setAttribute("awaitingPickupPaged", awaitingPickupPaged);
            request.setAttribute("shippingPaged", shippingPaged);
            request.setAttribute("deliveredPaged", deliveredPaged);
            request.setAttribute("cancelPaged", cancelPaged);
            request.setAttribute("refundPaged", refundPaged);

            request.setAttribute("currentAllPage", pageAll);
            request.setAttribute("currentAwaitingPage", pageAwaiting);
            request.setAttribute("currentShippingPage", pageShipping);
            request.setAttribute("currentDeliveredPage", pageDelivered);
            request.setAttribute("currentCanceledPage", pageCanceled);
            request.setAttribute("currentRefundPage", pageRefund);

            request.setAttribute("totalAllPages", totalAllPages);
            request.setAttribute("totalAwaitingPages", totalAwaitingPages);
            request.setAttribute("totalShippingPages", totalShippingPages);
            request.setAttribute("totalDeliveredPages", totalDeliveredPages);
            request.setAttribute("totalCanceledPages", totalCanceledPages);
            request.setAttribute("totalRefundPages", totalRefundPages);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/my-orders.jsp");
            rd.forward(request, response);
        } else if (service.equals("cancelOrder")) {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            boolean isAjax = "true".equalsIgnoreCase(request.getParameter("ajax"));

            Order order = daoOrder.getOrderById(orderId);
            if (order == null) {
                JSONObject json = new JSONObject();
                json.put("success", false);
                json.put("message", "Order not found.");
                response.setContentType("application/json");
                response.getWriter().write(json.toString());
                return;
            }
            if (!"Awaiting Pickup".equalsIgnoreCase(order.getOrderStatus())) {
                JSONObject json = new JSONObject();
                json.put("success", false);
                json.put("message", "You can only cancel orders in Awaiting Pickup status.");
                response.setContentType("application/json");
                response.getWriter().write(json.toString());
                return;
            }

            boolean updated = daoOrder.updateStatus(orderId, "Cancel");
            JSONObject json = new JSONObject();
            if (updated) {
                json.put("success", true);
                json.put("message", "Order canceled successfully.");
            } else {
                json.put("success", false);
                json.put("message", "Unable to cancel order due to an internal error.");
            }
            response.setContentType("application/json");
            response.getWriter().write(json.toString());
        } else if (service.equals("refundOrder")) {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            boolean isAjax = "true".equalsIgnoreCase(request.getParameter("ajax"));

            Order order = daoOrder.getOrderById(orderId);
            if (order == null) {
                JSONObject json = new JSONObject();
                json.put("success", false);
                json.put("message", "Order not found.");
                response.setContentType("application/json");
                response.getWriter().write(json.toString());
                return;
            }
            if (!"Delivered".equalsIgnoreCase(order.getOrderStatus())) {
                JSONObject json = new JSONObject();
                json.put("success", false);
                json.put("message", "Refund is only allowed for Delivered orders.");
                response.setContentType("application/json");
                response.getWriter().write(json.toString());
                return;
            }

            boolean updated = daoOrder.updateStatus(orderId, "Refund");
            JSONObject json = new JSONObject();
            if (updated) {
                json.put("success", true);
                json.put("message", "Refund request processed successfully.");
            } else {
                json.put("success", false);
                json.put("message", "Unable to process refund due to an internal error.");
            }
            response.setContentType("application/json");
            response.getWriter().write(json.toString());
        } else {
            processRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                System.out.println("reviewerID :" + reviewerID);
                int productID = Integer.parseInt(request.getParameter("product_id"));
                int orderdetailID = Integer.parseInt(request.getParameter("orderdetailID"));
                int rating = Integer.parseInt(request.getParameter("rating"));
                String content = request.getParameter("content");

                System.out.println("reviewerID: " + reviewerID);
                System.out.println("orderDetailID: " + orderdetailID);
                System.out.println("product_id: " + productID);

                if (dao.isFeedbackExists(orderdetailID)) {
                    response.sendError(HttpServletResponse.SC_CONFLICT, "Feedback already exists for this order.");
                    return;
                }

                Collection<Part> fileParts = request.getParts();
                for (Part filePart : fileParts) {
                    System.out.println("File Part Name: " + filePart.getName() + ", Size: " + filePart.getSize());
                }
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
                System.out.println("JSON images: " + imagesJson);
                String reviewTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                boolean isDisabled = false;
                String Status = "visible";
                Feedback feedback = new Feedback(orderdetailID, reviewerID, productID, reviewTime, rating, content, imagesJson, isDisabled, Status);

                boolean success = dao.insertFeedback(feedback);

                if (success) {
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
        return "Customer Order Management Controller with pagination for 6 sections.";
    }
}
