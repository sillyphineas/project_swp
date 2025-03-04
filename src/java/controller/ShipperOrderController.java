/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Order;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.DAOOrder;
import model.DAOUser;
import model.DAOVoucher;

/**
 *
 * @author DUC MINH
 */
@WebServlet(name = "ShipperOrderController", urlPatterns = {"/ShipperOrderController"})
public class ShipperOrderController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ShipperOrderController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ShipperOrderController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            int shipperId = user.getId(); // Lấy ID shipper từ user

            // Các tham số lọc và phân trang
            String statusFilter = request.getParameter("status") != null ? request.getParameter("status") : "";
            String searchQuery = request.getParameter("search") != null ? request.getParameter("search") : "";
            int page = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "1");
            int pageSize = Integer.parseInt(request.getParameter("pageSize") != null ? request.getParameter("pageSize") : "10");

            // Gọi DAOOrder để lấy danh sách đơn hàng cho shipper
            DAOOrder daoOrder = new DAOOrder();
            List<Order> orders = daoOrder.getOrdersForShipper(shipperId, statusFilter, searchQuery, page, pageSize);

            // Lấy tên người mua cho mỗi đơn hàng và lưu vào Map
            Map<Integer, String> buyerNames = new HashMap<>();
            Map<Integer, String> voucherCodes = new HashMap<>(); // Map lưu voucherCode
            DAOUser daoUser = new DAOUser();
            DAOVoucher daoVoucher = new DAOVoucher(); // Giả sử bạn có một lớp DAO để truy vấn bảng Vouchers
            for (Order order : orders) {
                User buyer = daoUser.getUserById(order.getBuyerID()); // Lấy thông tin người mua từ bảng User
                if (buyer != null) {
                    buyerNames.put(order.getBuyerID(), buyer.getName()); // Lưu buyerName vào Map
                }

                // Lấy voucherCode từ bảng Vouchers và lưu vào Map
                String voucherCode = daoVoucher.getVoucherCodeByOrderId(order.getId());
                voucherCodes.put(order.getId(), voucherCode); // Lưu voucherCode vào Map
            }

            // Tổng số đơn hàng (dùng để tính số trang)
            int totalOrders = daoOrder.getTotalOrdersForShipper(shipperId, statusFilter, searchQuery);
            int totalPages = (int) Math.ceil((double) totalOrders / pageSize);

            // Truyền thông tin vào request để JSP hiển thị
            request.setAttribute("orders", orders);
            request.setAttribute("buyerNames", buyerNames); // Truyền buyerNames vào request
            request.setAttribute("voucherCodes", voucherCodes); // Truyền voucherCodes vào request
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.getRequestDispatcher("/WEB-INF/views/orderlistforshipper.jsp").forward(request, response);
        } else {
            // Nếu người dùng chưa đăng nhập, chuyển đến trang đăng nhập
            request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    int orderId = Integer.parseInt(request.getParameter("orderId"));
    String newStatus = request.getParameter("status");

    // Chuyển đổi trạng thái chuỗi thành giá trị số tương ứng
    int statusValue = 0;  // Mặc định là giá trị 0
    switch (newStatus) {
        case "Processing":
            statusValue = 1;
            break;
        case "Shipped":
            statusValue = 2;
            break;
        case "Delivered":
            statusValue = 3;
            break;
        default:
            // Nếu trạng thái không hợp lệ, có thể cần xử lý thêm
            statusValue = 0; // hoặc báo lỗi nếu cần
            break;
    }

    // Cập nhật trạng thái đơn hàng
    DAOOrder daoOrder = new DAOOrder();
    boolean isUpdated = daoOrder.updateStatus(orderId, statusValue); // Cập nhật với giá trị số

    if (isUpdated) {
        // Lấy lại thông tin đơn hàng đã cập nhật, bao gồm các tham số lọc và phân trang
        User user = (User) request.getSession().getAttribute("user");
        int shipperId = user.getId(); // Lấy ID shipper từ user

        // Các tham số lọc và phân trang
        String statusFilter = request.getParameter("status") != null ? request.getParameter("status") : "";
        String searchQuery = request.getParameter("search") != null ? request.getParameter("search") : "";
        int page = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "1");
        int pageSize = Integer.parseInt(request.getParameter("pageSize") != null ? request.getParameter("pageSize") : "10");

        // Gọi DAOOrder để lấy lại danh sách đơn hàng cho shipper
        List<Order> orders = daoOrder.getOrdersForShipper(shipperId, statusFilter, searchQuery, page, pageSize);

        // Lấy tên người mua cho mỗi đơn hàng và lưu vào Map
        Map<Integer, String> buyerNames = new HashMap<>();
        Map<Integer, String> voucherCodes = new HashMap<>(); // Map lưu voucherCode
        DAOUser daoUser = new DAOUser();
        DAOVoucher daoVoucher = new DAOVoucher(); // Giả sử bạn có một lớp DAO để truy vấn bảng Vouchers
        for (Order order : orders) {
            User buyer = daoUser.getUserById(order.getBuyerID()); // Lấy thông tin người mua từ bảng User
            if (buyer != null) {
                buyerNames.put(order.getBuyerID(), buyer.getName()); // Lưu buyerName vào Map
            }

            // Lấy voucherCode từ bảng Vouchers và lưu vào Map
            String voucherCode = daoVoucher.getVoucherCodeByOrderId(order.getId());
            voucherCodes.put(order.getId(), voucherCode); // Lưu voucherCode vào Map
        }

        // Tổng số đơn hàng (dùng để tính số trang)
        int totalOrders = daoOrder.getTotalOrdersForShipper(shipperId, statusFilter, searchQuery);
        int totalPages = (int) Math.ceil((double) totalOrders / pageSize);

        // Truyền thông tin vào request để JSP hiển thị
        request.setAttribute("orders", orders);
        request.setAttribute("buyerNames", buyerNames); // Truyền buyerNames vào request
        request.setAttribute("voucherCodes", voucherCodes); // Truyền voucherCodes vào request
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        // Forward lại tới JSP để hiển thị danh sách đơn hàng mới
        request.getRequestDispatcher("/WEB-INF/views/orderlistforshipper.jsp").forward(request, response);
    } else {
        // Xử lý trường hợp lỗi cập nhật
        request.setAttribute("error", "Failed to update the order status.");
        request.getRequestDispatcher("/WEB-INF/views/orderlistforshipper.jsp").forward(request, response);
    }
}


    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
