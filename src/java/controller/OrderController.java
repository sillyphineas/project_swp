package controller;

import entity.Address;
import entity.CartItem;
import entity.User;
import entity.Order;
import entity.OrderDetail;
import entity.Payment;
import entity.Voucher;
import helper.Authorize;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import model.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

@WebServlet(name = "OrderController", urlPatterns = {"/OrderController"})
public class OrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (!Authorize.isAccepted(user, "/OrderController")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }

        String service = request.getParameter("service");
        if ("orderSuccess".equals(service)) {
            request.getRequestDispatcher("/WEB-INF/views/order-success.jsp").forward(request, response);
        } else if ("cancelOrder".equals(service)) {
            request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String service = request.getParameter("service");
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if ("createOrder".equals(service)) {

            if (user == null) {
                response.sendRedirect("LoginController");
                return;
            }

            String fullName = request.getParameter("newFullName");
            String phone = request.getParameter("newPhone");
            String address = request.getParameter("addressSelect");
            String paymentMethod = request.getParameter("paymentMethod");

            if (fullName == null || fullName.trim().isEmpty()) {
                request.setAttribute("error", "Vui lòng nhập Họ tên người nhận");
                forwardToCheckout(request, response);
                return;
            }
            if (phone == null || phone.trim().isEmpty()) {
                request.setAttribute("error", "Vui lòng nhập Số điện thoại");
                forwardToCheckout(request, response);
                return;
            }
            if (!phone.matches("\\d{9,11}")) {
                request.setAttribute("error", "Số điện thoại không hợp lệ");
                forwardToCheckout(request, response);
                return;
            }
            if (paymentMethod == null || (!paymentMethod.equals("1") && !paymentMethod.equals("2"))) {
                request.setAttribute("error", "Vui lòng chọn phương thức thanh toán");
                forwardToCheckout(request, response);
                return;
            }

            List<CartItem> selectedCartItems = (List<CartItem>) session.getAttribute("selectedCartItems");
            if (selectedCartItems == null || selectedCartItems.isEmpty()) {
                request.setAttribute("error", "EmptyCart");
                forwardToCheckout(request, response);
                return;
            }

            String discountedTotalParam = request.getParameter("discountedTotal");
            BigDecimal discountedTotal = BigDecimal.ZERO;
            if (discountedTotalParam != null && !discountedTotalParam.isEmpty()) {
                try {
                    discountedTotal = new BigDecimal(discountedTotalParam);
                } catch (NumberFormatException e) {
                    discountedTotal = BigDecimal.ZERO;
                }
            }

            String voucherIdParam = request.getParameter("voucherId");
            Integer intVoucherId = null;
            if (voucherIdParam != null && !voucherIdParam.trim().isEmpty()) {
                try {
                    intVoucherId = Integer.valueOf(voucherIdParam.trim());
                } catch (NumberFormatException e) {
                    
                }
            }

            DAOOrder daoOrder = new DAOOrder();
            DAOOrderDetail daoOrderDetail = new DAOOrderDetail();
            DAOPayment daoPayment = new DAOPayment();
            DAOCartItem daoCartItem = new DAOCartItem();
            DAOUserVoucher daoUV = new DAOUserVoucher();
            DAOProductVariant daoProductVariant = new DAOProductVariant();
            double totalAmount = 0;
            for (CartItem ci : selectedCartItems) {
                totalAmount += ci.getTotalPrice().doubleValue();
            }
            if (intVoucherId == null) {
                discountedTotal = BigDecimal.valueOf(totalAmount);
            } else {
                daoUV.deleteUserVoucher(user.getId(), intVoucherId);
            }
            Date now = new Date();
            Order newOrder = new Order();
            newOrder.setBuyerID(user.getId());
            newOrder.setOrderTime(now);
            newOrder.setOrderStatus("Awaiting Pickup");
            newOrder.setShippingAddress(address);
            newOrder.setTotalPrice(totalAmount);
            newOrder.setDiscountedPrice(discountedTotal.doubleValue());
            newOrder.setVoucherID(intVoucherId);
            newOrder.setRecipientName(fullName);
            newOrder.setRecipientPhone(phone);
            newOrder.setAssignedSaleId(3);

            if ("1".equals(paymentMethod)) {
                int orderId = daoOrder.addOrder(newOrder);
                if (orderId > 0) {
                    Payment newPayment = new Payment();
                    newPayment.setPaymentStatus("Pending");
                    newPayment.setPaymentMethodId(1); 
                    newPayment.setAmount(discountedTotal.doubleValue());
                    newPayment.setNote("");
                    newPayment.setConfirmBy(null);
                    newPayment.setOrderId(orderId);
                    daoPayment.addPayment(newPayment);

                    for (CartItem ci : selectedCartItems) {
                        OrderDetail detail = new OrderDetail();
                        detail.setOrderId(orderId);
                        detail.setProductVariantID(ci.getProductVariantID());
                        detail.setQuantity(ci.getQuantity());
                        daoOrderDetail.addOrderDetail(detail);
                        daoProductVariant.reduceStock(ci.getProductVariantID(), ci.getQuantity());

                        daoCartItem.delete(ci.getCartItemID());
                    }

                    session.removeAttribute("selectedCartItems");
                    session.removeAttribute("totalOrderPrice");
                    response.sendRedirect("OrderController?service=orderSuccess");
                } else {
                    response.sendRedirect("order-fail.jsp");
                }

            } else if ("2".equals(paymentMethod)) {
                session.setAttribute("addressSelect", address);
                session.setAttribute("newFullName", fullName);
                session.setAttribute("newPhone", phone);
                session.setAttribute("voucherId", intVoucherId);
                session.setAttribute("discountedTotal", discountedTotal);

                request.getRequestDispatcher("VNPayPaymentServlet").forward(request, response);
            }
        }
    }

    private void forwardToCheckout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            List<CartItem> selectedCartItems = (List<CartItem>) session.getAttribute("selectedCartItems");
            if (selectedCartItems != null) {
                request.setAttribute("cartItems", selectedCartItems);
            }
            BigDecimal totalOrderPrice = (BigDecimal) session.getAttribute("totalOrderPrice");
            if (totalOrderPrice != null) {
                request.setAttribute("totalOrderPrice", totalOrderPrice);
            }
            Vector<Voucher> vouchers = (Vector<Voucher>) session.getAttribute("vouchers");
            if (vouchers != null) {
                request.setAttribute("vouchers", vouchers);
            }
            List<Address> addresses = (List<Address>) session.getAttribute("userAddresses");
            if (addresses != null) {
                request.setAttribute("userAddresses", addresses);
            }
        }
        request.getRequestDispatcher("WEB-INF/views/checkout.jsp").forward(request, response);
    }
}
