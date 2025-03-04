/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.CartItem;
import entity.User;
import entity.Cart;
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
import jakarta.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.DAOCart;
import model.DAOCartItem;
import model.DAOOrder;
import model.DAOOrderDetail;
import model.DAOProductVariant;

/**
 *
 * @author HP
 */
@WebServlet(name = "OrderController", urlPatterns = {"/OrderController"})
public class OrderController extends HttpServlet {

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
            out.println("<title>Servlet OrderController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderController at " + request.getContextPath() + "</h1>");
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
        String service = request.getParameter("service");

        if (service.equals("orderSuccess")) {
            request.getRequestDispatcher("/WEB-INF/views/order-success.jsp").forward(request, response);
        }
        if (service.equals("cancelOrder")) {
            request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
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
        String service = request.getParameter("service");
        HttpSession session = request.getSession(false);

        if (service.equals("createOrder")) {
            String paymentMethod = request.getParameter("paymentMethod");
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            int customerID = user.getId();
            DAOCartItem daoCartItem = new DAOCartItem();
            DAOOrder daoOrder = new DAOOrder();
            DAOOrderDetail daoOrderDetail = new DAOOrderDetail();
            

            List<CartItem> selectedCartItems = (List<CartItem>) session.getAttribute("selectedCartItems");
            System.out.println(selectedCartItems);
            if (selectedCartItems == null || selectedCartItems.isEmpty()) {
                response.sendRedirect("checkout.jsp?error=EmptyCart");
                return;
            }
            if ("1".equals(paymentMethod)) {
                Date orderTime = new Date();
                double totalAmount = 0.0;

                if (selectedCartItems != null) {
                    for (CartItem item : selectedCartItems) {
                        totalAmount += item.getTotalPrice().doubleValue();
                    }
                }

                Order newOrder = new Order();
                newOrder.setBuyerID(customerID);
                newOrder.setOrderTime(orderTime);
                newOrder.setOrderStatus("Pending");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(orderTime);
                calendar.add(Calendar.DATE, 3);
                newOrder.setShippingDate(calendar.getTime());
                newOrder.setShippingAddress(request.getParameter("addressSelect"));
                newOrder.setTotalPrice(totalAmount);
                newOrder.setDiscountedPrice(0.0);
                newOrder.setPaymentMethod(1);
                newOrder.setDisabled(false);
                newOrder.setVoucherID(null);
                newOrder.setRecipientName(request.getParameter("newFullName"));
                newOrder.setRecipientPhone(request.getParameter("newPhone"));
                newOrder.setAssignedSaleId(3);

  
                int OrderId = daoOrder.addOrder(newOrder);
                
                for (CartItem item : selectedCartItems) {
                    OrderDetail newOrderDetail = new OrderDetail();
                    newOrderDetail.setOrderId(OrderId);
                    newOrderDetail.setProductVariantID(item.getProductVariantID());
                    newOrderDetail.setQuantity(item.getQuantity());
                    System.out.println("OrderId" + newOrderDetail.getOrderId());
                    System.out.println("ProductVariantID" + newOrderDetail.getProductVariantID());
                    System.out.println("Quantity" + newOrderDetail.getQuantity());
                    daoOrderDetail.addOrderDetail(newOrderDetail);
                }
                if (OrderId > 0) {
                    DAOProductVariant daoProductVariant = new DAOProductVariant();
                    for (CartItem item : selectedCartItems) {
                        int variantId = item.getProductVariantID();
                        int quantity = item.getQuantity();
                        daoProductVariant.reduceStock(variantId, quantity);
                    }
                    daoCartItem.clearCart(customerID);
                    response.sendRedirect("OrderController?service=orderSuccess");
                } else {
                    response.sendRedirect("order-fail.jsp");
                }
            } else if ("2".equals(paymentMethod)) {
                double totalAmount = 0.0;

                if (selectedCartItems != null) {
                    for (CartItem item : selectedCartItems) {
                        totalAmount += item.getTotalPrice().doubleValue();
                    }
                }
                request.setAttribute("amount", totalAmount);
                session.setAttribute("addressSelect", request.getParameter("addressSelect"));
                session.setAttribute("newFullName", request.getParameter("newFullName"));
                session.setAttribute("newPhone", request.getParameter("newPhone"));
                RequestDispatcher rd = request.getRequestDispatcher("VNPayPaymentServlet");
                rd.forward(request, response);

            } else {

                response.sendRedirect("checkout.jsp?error=NoPaymentMethodSelected");
            }
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
