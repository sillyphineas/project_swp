/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.OrderInformation;
import entity.User;
import helper.Authorize;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import model.DAOOrderInformation;

@WebServlet(name = "CustomerOrderDetailController", urlPatterns = {"/CustomerOrderDetailController"})
public class CustomerOrderDetailController extends HttpServlet {

    // Giả sử bạn bổ sung phương thức sau trong DAOOrderInformation
    // Nếu chưa có, bạn có thể lọc từ danh sách getAllOrderInformation
    private List<OrderInformation> getOrderInformationByOrderId(int orderId) {
        DAOOrderInformation daoOI = new DAOOrderInformation();
        List<OrderInformation> allInfo = daoOI.getAllOrderInformation();
        List<OrderInformation> details = new ArrayList<>();
        for (OrderInformation info : allInfo) {
            if (info.getId() == orderId) {
                details.add(info);
            }
        }
        return details;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        if (!Authorize.isAccepted(user, "/CustomerOrderDetailController")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }
        String orderIdStr = request.getParameter("orderID");
        int orderID = 0;
        try {
            orderID = Integer.parseInt(orderIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Order ID.");
            return;
        }
        
        // Lấy danh sách OrderInformation cho đơn hàng theo orderID
        List<OrderInformation> orderDetails = getOrderInformationByOrderId(orderID);
        if (orderDetails.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found.");
            return;
        }
        
        // Set các attribute cần thiết, dùng thông tin từ dòng đầu tiên
        OrderInformation first = orderDetails.get(0);
        request.setAttribute("selectedOrderID", orderID);
        request.setAttribute("orderDetails", orderDetails);
        request.setAttribute("recipientName", first.getRecipientName());
        request.setAttribute("recipientPhone", first.getRecipientPhone());
        // Giả sử shippingAddress được lưu trong field "address" (hoặc bạn dùng các field khác)
        request.setAttribute("shippingAddress", first.getAddress() + ", " + first.getDistrict() + ", " + first.getCity());
        request.setAttribute("showBillModal", true);
        
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/my-orderDetail.jsp");
        rd.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
