package controller;

import entity.OrderShippingView;
import entity.User;
import helper.Authorize;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DAOOrder;
import model.DAOShipping;

@WebServlet(name = "ShipperOrderController", urlPatterns = {"/ShipperOrderController"})
public class ShipperOrderController extends HttpServlet {

    // Xử lý GET: Lấy danh sách đơn hàng gộp (Orders + Shipping) với filter và search
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Authorize
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        if (!Authorize.isAccepted(user, "/ShipperOrderController")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }

        int shipperId = user.getId();

        // Lấy tham số filter (trạng thái) và search (tìm theo buyerName hoặc OrderID)
        String statusFilter = request.getParameter("status") != null ? request.getParameter("status") : "";
        String searchQuery = request.getParameter("search") != null ? request.getParameter("search") : "";
        int page = 1;
        int pageSize = 10;
        try {
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            if (request.getParameter("pageSize") != null) {
                pageSize = Integer.parseInt(request.getParameter("pageSize"));
            }
        } catch (NumberFormatException e) {
            // Nếu parse lỗi, giữ mặc định page=1, pageSize=10
        }

        // Gọi DAOOrder để lấy danh sách đơn hàng gộp (với các trường cần hiển thị)
        DAOOrder daoOrder = new DAOOrder();
        List<OrderShippingView> orderShippingList = daoOrder.getOrderShippingView(
                shipperId, statusFilter, searchQuery, page, pageSize);

        // Lấy tổng số bản ghi để tính số trang
        int totalRecords = daoOrder.getTotalOrderShippingCount(shipperId, statusFilter, searchQuery);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // Đặt dữ liệu lên request
        request.setAttribute("orderShippingList", orderShippingList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("statusFilter", statusFilter);
        request.setAttribute("searchQuery", searchQuery);

        // Chuyển tiếp sang JSP hiển thị (JSP cần duyệt orderShippingList)
        request.getRequestDispatcher("/WEB-INF/views/orderlistforshipper.jsp").forward(request, response);
    }

    // Xử lý POST: Ví dụ cập nhật ShippingStatus cho đơn hàng (dựa trên OrderID và shipperId)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra user đã đăng nhập
        User user = (User) request.getSession(false).getAttribute("user");
        if (user == null) {
            request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        int shipperId = user.getId();

        // Lấy orderId (ẩn trong form) và trạng thái mới (ShippingStatus)
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String newStatus = request.getParameter("status");

        // Gọi DAOShipping để cập nhật ShippingStatus cho đơn hàng có OrderID và shipperId
        DAOShipping daoShipping = new DAOShipping();
        boolean updated = daoShipping.updateShippingStatus(orderId, shipperId, newStatus);

        // Sau khi cập nhật, load lại danh sách theo các tham số hiện tại
        String statusFilter = request.getParameter("statusFilter") != null ? request.getParameter("statusFilter") : "";
        String searchQuery = request.getParameter("searchQuery") != null ? request.getParameter("searchQuery") : "";
        int page = 1;
        int pageSize = 10;
        try {
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            if (request.getParameter("pageSize") != null) {
                pageSize = Integer.parseInt(request.getParameter("pageSize"));
            }
        } catch (NumberFormatException e) {
            // giữ mặc định nếu parse lỗi
        }

        DAOOrder daoOrder = new DAOOrder();
        List<OrderShippingView> orderShippingList = daoOrder.getOrderShippingView(
                shipperId, statusFilter, searchQuery, page, pageSize);
        int totalRecords = daoOrder.getTotalOrderShippingCount(shipperId, statusFilter, searchQuery);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        request.setAttribute("orderShippingList", orderShippingList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("statusFilter", statusFilter);
        request.setAttribute("searchQuery", searchQuery);

        if (!updated) {
            request.setAttribute("error", "Failed to update shipping status.");
        }
        request.getRequestDispatcher("/WEB-INF/views/orderlistforshipper.jsp").forward(request, response);
    }
}
