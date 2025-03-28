package controller;

import entity.OrderInformation;
import entity.User;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.DAOOrderInformation;
import model.DAOPayment;
import model.DAOShipping;
import model.DAOOrder;

@WebServlet(name = "ShipperOrderController", urlPatterns = {"/ShipperOrderController"})
public class ShipperOrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Kiểm tra đăng nhập
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null) {
            request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        // Lấy ID shipper
        int shipperId = user.getId();

        // Lấy filter
        String statusFilter = request.getParameter("status") != null ? request.getParameter("status") : "";
        String searchQuery = request.getParameter("search") != null ? request.getParameter("search") : "";

        // Lấy tham số phân trang
        int page = 1, pageSize = 10;
        try {
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            if (request.getParameter("pageSize") != null) {
                pageSize = Integer.parseInt(request.getParameter("pageSize"));
            }
        } catch (NumberFormatException e) {
            // Giữ nguyên mặc định
        }

        // Gọi DAO
        DAOOrderInformation daoOI = new DAOOrderInformation();
        int totalRecords = daoOI.countAllForShipper(shipperId, statusFilter, searchQuery);
        List<OrderInformation> orderList = daoOI.getAllForShipper(shipperId, statusFilter, searchQuery, page, pageSize);
        int totalPages = (int) Math.ceil(totalRecords * 1.0 / pageSize);

        // Đẩy sang JSP
        request.setAttribute("orderList", orderList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        // Giữ lại filter, search để hiển thị
        request.setAttribute("statusFilter", statusFilter);
        request.setAttribute("searchQuery", searchQuery);

        // Chuyển tiếp
        request.getRequestDispatcher("/WEB-INF/views/shipper-orders.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Kiểm tra đăng nhập
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null) {
            request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        int shipperId = user.getId();

        // Lấy các tham số chung
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String statusFilter = request.getParameter("statusFilter") != null ? request.getParameter("statusFilter") : "";
        String searchQuery = request.getParameter("searchQuery") != null ? request.getParameter("searchQuery") : "";
        int page = 1, pageSize = 10;
        try {
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            if (request.getParameter("pageSize") != null) {
                pageSize = Integer.parseInt(request.getParameter("pageSize"));
            }
        } catch (NumberFormatException e) {
            // Giữ nguyên
        }

        // Xác định action
        String action = request.getParameter("action");
        if ("updateShipping".equals(action)) {
            // Cập nhật shippingStatus
            String newShippingStatus = request.getParameter("newShippingStatus");
            DAOShipping daoShipping = new DAOShipping();
            DAOOrder daoOrder = new DAOOrder();
            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
            daoShipping.updateArrival(orderId, shipperId, currentDate);
            daoShipping.updateShippingStatus(orderId, shipperId, newShippingStatus);
            daoOrder.updateStatus(orderId, newShippingStatus);

        } else if ("updatePayment".equals(action)) {
            // Cập nhật paymentStatus
            String newPaymentStatus = request.getParameter("newPaymentStatus");
            DAOPayment daoPayment = new DAOPayment();
            daoPayment.updatePaymentStatus(orderId, newPaymentStatus);
        }

        // Sau khi cập nhật, quay lại trang GET
        String redirectUrl = "ShipperOrderController"
                + "?status=" + statusFilter
                + "&search=" + searchQuery
                + "&page=" + page
                + "&pageSize=" + pageSize;
        response.sendRedirect(redirectUrl);
    }
}
