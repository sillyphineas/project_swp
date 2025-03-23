/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import entity.Order;
import entity.OrderDetail;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAOOrder;
import model.DAOOrderDetail;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "SaleOrderController", urlPatterns = {"/SaleOrderController"})
public class SaleOrderController extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        DAOOrder dao = new DAOOrder();
        DAOOrderDetail daoDetail = new DAOOrderDetail();
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String service = request.getParameter("service");
            if (service == null) {
                service = "listAllOrder";
            }
            if (service.equals("listAllOrder")) {
                try {
                    String pageStr = request.getParameter("page");
                    int page = 1;
                    if (pageStr != null && pageStr.matches("\\d+")) {
                        page = Integer.parseInt(pageStr);
                    }
                    int pageSize = 5;

                    List<Order> orders = dao.getOrdersWithPagination(pageSize, page);
                    int totalOrders = dao.getTotalOrders();
                    int totalPages = (totalOrders + pageSize - 1) / pageSize;

                    request.setAttribute("orders", orders);
                    request.setAttribute("currentPage", page);
                    request.setAttribute("totalPages", totalPages);

                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/Order-List.jsp");
                    dispatcher.forward(request, response);

                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid page number");
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing orders");
                }
            }
            if (service.equals("sort")) {
                String sortBy = request.getParameter("sortBy");
                String sortOrder = request.getParameter("sortOrder");
                String pageStr = request.getParameter("page");
                int page = 1;
                int pageSize = 5;

                if (pageStr != null && !pageStr.trim().isEmpty()) {
                    pageStr = pageStr.trim();
                    try {
                        page = Integer.parseInt(pageStr);
                    } catch (NumberFormatException e) {
                        page = 1;
                    }
                }
                // Mặc định sắp xếp theo orderTime
                if (sortBy == null || sortBy.trim().isEmpty()) {
                    sortBy = "orderTime";
                }
                if (sortOrder == null || sortOrder.trim().isEmpty()) {
                    sortOrder = "desc";
                }

                List<Order> orders = dao.sortOrders(sortBy, sortOrder, page, pageSize);
                int totalOrders = dao.getTotalOrders();
                int totalPages = (int) Math.ceil((double) totalOrders / pageSize);

                if (orders.isEmpty()) {
                    request.setAttribute("message", "No orders found.");
                }
                request.setAttribute("orders", orders);
                request.setAttribute("sortBy", sortBy);
                request.setAttribute("sortOrder", sortOrder);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.getRequestDispatcher("/WEB-INF/views/Order-List.jsp").forward(request, response);
            }

            if (service.equals("orderFilter")) {
                String buyerName = request.getParameter("buyerName");
                buyerName = (buyerName != null && !buyerName.trim().isEmpty()) ? buyerName.trim() : null;

                String paymentStatus = request.getParameter("paymentStatus");
                paymentStatus = (paymentStatus != null && !paymentStatus.trim().isEmpty()) ? paymentStatus.trim() : null;

                String paymentMethod = request.getParameter("paymentMethod");
                paymentMethod = (paymentMethod != null && !paymentMethod.trim().isEmpty()) ? paymentMethod.trim() : null;

                String orderStatus = request.getParameter("orderStatus");
                orderStatus = (orderStatus != null && !orderStatus.trim().isEmpty()) ? orderStatus.trim() : null;

                String pageStr = request.getParameter("page");
                int page = (pageStr != null && !pageStr.trim().isEmpty()) ? Integer.parseInt(pageStr.trim()) : 1;
                int pageSize = 5;

                // Lấy danh sách đơn hàng lọc
                List<Order> orders = dao.getFilteredOrders(page, pageSize, buyerName, paymentStatus, paymentMethod, orderStatus);
                int totalOrders = dao.getTotalFilteredOrders(buyerName, paymentStatus, paymentMethod, orderStatus);
                int totalPages = (int) Math.ceil((double) totalOrders / pageSize);

                request.setAttribute("orders", orders);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("buyerName", buyerName);
                request.setAttribute("paymentStatus", paymentStatus);
                request.setAttribute("paymentMethod", paymentMethod);
                request.setAttribute("orderStatus", orderStatus);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/Order-List.jsp");
                dispatcher.forward(request, response);
            }

            if (service.equals("search")) {
                String query = request.getParameter("query");
                String pageStr = request.getParameter("page");
                int page = 1;
                int pageSize = 5;
                System.out.println("Query received: " + query);
                System.out.println("Total orders found: " + pageStr);

                if (query == null || query.trim().isEmpty()) {
                    request.setAttribute("error", "Please enter a search term!");
                    response.sendRedirect("SaleOrderController?service=listAllOrder");
                    return;
                }

                query = query.toLowerCase();

                try {
                    if (pageStr != null && !pageStr.isEmpty()) {
                        pageStr = pageStr.trim();
                        page = Integer.parseInt(pageStr);
                    }
                } catch (NumberFormatException e) {
                    page = 1;
                }

                List<Order> orders = dao.SalesearchOrders(query, page, pageSize);
                int totalOrders = dao.SalecountTotalOrdersForSearch(query);
                int totalPages = (int) Math.ceil((double) totalOrders / pageSize);

                if (orders.isEmpty()) {
                    request.setAttribute("message", "No orders found.");
                }

                request.setAttribute("orders", orders);
                request.setAttribute("query", query);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                request.getRequestDispatcher("/WEB-INF/views/Order-List.jsp").forward(request, response);
            }
            if (service.equals("ShipperAssignment")) {
                String submit = request.getParameter("submit");
                if (submit == null) {
                    // Lấy danh sách shipper từ Users role=4 và isDisabled=0
                    ResultSet rsShippers = dao.getData("SELECT id, name FROM Users WHERE roleId = 4 AND isDisabled = 0");

                    // Lấy orderID từ request
                    String orderIDStr = request.getParameter("orderID");
                    request.setAttribute("orderID", orderIDStr);
                    request.setAttribute("rsShippers", rsShippers);

                    // Forward sang form assign shipper
                    request.getRequestDispatcher("/WEB-INF/views/InsertShipping.jsp").forward(request, response);

                } else {
                    String orderIDStr = request.getParameter("orderID");
                    String shipperIDStr = request.getParameter("shipperID");
                    String estimatedArrivalStr = request.getParameter("estimatedArrival");
                    String shippingStatus = "Shipping";

                    try {
                        int orderID = Integer.parseInt(orderIDStr);
                        int shipperID = Integer.parseInt(shipperIDStr);

                        java.time.LocalDate now = java.time.LocalDate.now();
                        String shippingDateStr = now.toString();  // "2025-03-19"

                        List<Order> orders = dao.getAllOrders();
                        request.setAttribute("orders", orders);
                        boolean isAssigned = dao.isOrderAlreadyAssigned(orderID);
                        if (isAssigned) {
                            response.sendRedirect("SaleOrderController?service=listAllOrder&message=This+order+has+already+been+assigned+to+a+shipper.");
                            return;
                        }

                        boolean isInserted = dao.insertShipping(orderID, shipperID, shippingStatus, estimatedArrivalStr, null, shippingDateStr);
                        System.out.println("isInserted : " + isInserted);
                        if (isInserted) {
                            response.sendRedirect("SaleOrderController?service=listAllOrder&message=Order+assigned+to+shipper+successfully.");
                        } else {
                            response.sendRedirect("SaleOrderController?service=listAllOrder&message=Failed+to+assign+order+to+shipper.");
                        }

                    } catch (NumberFormatException e) {
                        response.sendRedirect("SaleOrderController?service=listAllOrder&message=Invalid+OrderID+or+ShipperID.");
                    } catch (Exception e) {
                        response.sendRedirect("SaleOrderController?service=listAllOrder&message=Error+processing+request.");
                    }
                }
            }

            if (service.equals("ViewBillWithID")) {
                String orderIDStr = request.getParameter("orderID");
                System.out.println("Received orderID: " + orderIDStr);

                if (orderIDStr == null || orderIDStr.trim().isEmpty()) {
                    request.setAttribute("error", "Order ID is missing!");
                    response.sendRedirect("SaleOrderController?service=listAllOrder");
                    return;
                }

                int orderID = 0;
                try {
                    orderIDStr = orderIDStr.trim();
                    orderID = Integer.parseInt(orderIDStr);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Order ID format.");
                    request.setAttribute("error", "Invalid Order ID!");
                    response.sendRedirect("SaleOrderController?service=listAllOrder");
                    return;
                }

                // Lấy danh sách chi tiết đơn hàng từ DAO
                List<OrderDetail> orderDetails = daoDetail.SalegetOrderDetailsByOrderId(orderID);
                System.out.println("Total order details found: " + (orderDetails != null ? orderDetails.size() : 0));

                if (orderDetails == null || orderDetails.isEmpty()) {
                    request.setAttribute("message", "No bill details found for Order ID: " + orderID);
                    response.sendRedirect("SaleOrderController?service=listAllOrder");
                    return;
                }

                // Lấy thông tin người nhận từ chi tiết đầu tiên
                OrderDetail firstDetail = orderDetails.get(0);
                request.setAttribute("orderDetails", orderDetails);
                request.setAttribute("recipientName", firstDetail.getRecipientName());
                request.setAttribute("recipientPhone", firstDetail.getRecipientPhone());
                request.setAttribute("shippingAddress", firstDetail.getShippingAddress());
                request.setAttribute("orderTime", firstDetail.getOrderTime());
                // Gửi orderID để mở modal bill
                request.setAttribute("showBillModal", true);
                request.setAttribute("selectedOrderID", orderID);

                // Forward về Order-List.jsp để hiển thị hóa đơn
                request.getRequestDispatcher("/WEB-INF/views/ViewBill.jsp").forward(request, response);
            }

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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SaleOrderController.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SaleOrderController.class.getName()).log(Level.SEVERE, null, ex);
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
