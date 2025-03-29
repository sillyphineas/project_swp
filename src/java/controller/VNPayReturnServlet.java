/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Cart;
import entity.CartItem;
import entity.Order;
import entity.OrderDetail;
import entity.Payment;
import entity.User;
import helper.Authorize;
import helper.EmailUtil;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAOCart;
import model.DAOCartItem;
import model.DAOOrder;
import model.DAOOrderDetail;
import model.DAOPayment;
import model.DAOProduct;
import model.DAOProductVariant;
import model.DAOUserVoucher;

/**
 *
 * @author HP
 */
@WebServlet(name = "VNPayReturnServlet", urlPatterns = {"/vnpay_return"})
public class VNPayReturnServlet extends HttpServlet {

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
            out.println("<title>Servlet VNPayReturnServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VNPayReturnServlet at " + request.getContextPath() + "</h1>");
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
        //Authorize
        HttpSession session = request.getSession(false);
//        User user = null;
//        if (session != null) {
//            user = (User) session.getAttribute("user");
//        }
//        if (!Authorize.isAccepted(user, "/vnpay_return")) {
//            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
//            return;
//        }
//        session = request.getSession();

        Map<String, String[]> paramMap = request.getParameterMap();
        Map<String, String> vnp_Params = new HashMap<>();
        for (String key : paramMap.keySet()) {
            vnp_Params.put(key, paramMap.get(key)[0]);
        }

        String vnp_TxnRef = vnp_Params.get("vnp_TxnRef");
        String vnp_ResponseCode = vnp_Params.get("vnp_ResponseCode");

        DAOOrder daoOrder = new DAOOrder();
        DAOOrderDetail daoOrderDetail = new DAOOrderDetail();

        if ("00".equals(vnp_ResponseCode)) {
            daoOrder.updateOrderStatus(vnp_TxnRef, "Paid");
            session.setAttribute("paymentStatus", "success");

            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect("LoginController");
                return;
            }
            int customerID = user.getId();
            DAOCart daoCart = new DAOCart();
            DAOUserVoucher daoUV = new DAOUserVoucher();
            DAOCartItem daoCartItem = new DAOCartItem();
            DAOPayment daoPayment = new DAOPayment();
            double totalAmount = 0.0;

            List<CartItem> selectedCartItems = (List<CartItem>) session.getAttribute("selectedCartItems");
            if (selectedCartItems == null || selectedCartItems.isEmpty()) {
                response.sendRedirect("checkout.jsp?error=EmptyCart");
                return;
            }
            if (selectedCartItems != null) {
                for (CartItem item : selectedCartItems) {
                    totalAmount += item.getTotalPrice().doubleValue();
                }
            }

            BigDecimal discountedTotalValue = (BigDecimal) session.getAttribute("discountedTotal");
            if (discountedTotalValue == null) {
                discountedTotalValue = BigDecimal.ZERO;
            }

            Integer intVoucherId = (Integer) session.getAttribute("voucherId");

            if (intVoucherId == null) {
                discountedTotalValue = BigDecimal.valueOf(totalAmount);
            }  else {
                daoUV.deleteUserVoucher(user.getId(), intVoucherId);
            }
            Date time = new Date();

            Order newOrder = new Order();
            newOrder.setBuyerID(customerID);
            newOrder.setOrderTime(time);
            newOrder.setOrderStatus("Awaiting Pickup");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            calendar.add(Calendar.DATE, 3);
            newOrder.setShippingAddress((String) session.getAttribute("addressSelect"));
            newOrder.setTotalPrice(totalAmount);
            newOrder.setDiscountedPrice(discountedTotalValue.doubleValue());
            newOrder.setDisabled(false);
            newOrder.setVoucherID(intVoucherId);
            newOrder.setRecipientName((String) session.getAttribute("newFullName"));
            newOrder.setRecipientPhone((String) session.getAttribute("newPhone"));
            newOrder.setAssignedSaleId(3);
            Payment newPayment = new Payment();

            newPayment.setPaymentStatus("Paid");
            newPayment.setPaymentTime(time);
            newPayment.setPaymentMethodId(2);
            calendar.setTime(time);
            calendar.add(Calendar.DATE, 3);
            newPayment.setAmount(discountedTotalValue.doubleValue());
            newPayment.setNote("");
            newPayment.setConfirmBy(null);

            int OrderId = daoOrder.addOrder(newOrder);

            newPayment.setOrderId(OrderId);
            daoPayment.addPayment(newPayment);

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
                DAOProduct daoProduct = new DAOProduct();
                Map<Integer, String> variantNames = new HashMap<>();
                for (CartItem item : selectedCartItems) {
                    int variantId = item.getProductVariantID();
                    int quantity = item.getQuantity();
                    int productId = daoProductVariant.getProductVariantById(variantId).getProduct_id();
                    String variantName = daoProduct.getProductById(productId).getName();
                    variantNames.put(variantId, variantName);
                    daoProductVariant.reduceStock(variantId, quantity);
                }
                for (CartItem item : selectedCartItems) {
                    try {
                        daoCartItem.removeCartItem(item.getCartItemID());
                    } catch (SQLException ex) {
                        Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                List<OrderDetail> details = daoOrderDetail.getOrderDetailsByOrderId(OrderId);

                EmailUtil.sendOrderMail(user.getEmail(), newOrder, details, variantNames);
                response.sendRedirect("OrderController?service=orderSuccess");
            } else {
                response.sendRedirect("order-fail.jsp");
            }
        } else {
            daoOrder.updateOrderStatus(vnp_TxnRef, "Failed");
            session.setAttribute("paymentStatus", "failed");

            if ("24".equals(vnp_ResponseCode)) {
                session.setAttribute("cancelMessage", "Bạn đã hủy giao dịch.");
                response.sendRedirect("CartURL");
            } else {
                response.sendRedirect("order-fail.jsp");
            }
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
        processRequest(request, response);
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
