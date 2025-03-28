/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Order;
import entity.User;
import helper.Authorize;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import helper.VNPayConfig;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.math.BigDecimal;
import model.DAOOrder;

/**
 *
 * @author HP
 */
@WebServlet(name = "VNPayPaymentServlet", urlPatterns = {"/VNPayPaymentServlet"})
public class VNPayPaymentServlet extends HttpServlet {

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
            out.println("<title>Servlet VNPayPaymentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VNPayPaymentServlet at " + request.getContextPath() + "</h1>");
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
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        if (!Authorize.isAccepted(user, "/VNPayPaymentServlet")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }
        doPost(request, response);
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
        long amount;
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("checkout.jsp?error=SessionExpired");
            return;
        }
        Object amountObj = session.getAttribute("discountedTotal");
        if (amountObj == null) {
            response.sendRedirect("checkout.jsp?error=InvalidAmount");
            return;
        }
        double amountDouble;
        if (amountObj instanceof BigDecimal) {
            amountDouble = ((BigDecimal) amountObj).doubleValue();
        } else if (amountObj instanceof Double) {
            amountDouble = (Double) amountObj;
        } else {
            response.sendRedirect("checkout.jsp?error=InvalidAmountType");
            return;
        }
        amount = (long) (amountDouble * 100);

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderInfo = "Thanh toán đơn hàng";
        String orderType = "billpayment";
        String txnRef = String.valueOf(System.currentTimeMillis()); // Mã giao dịch ngẫu nhiên
        String ipAddr = request.getRemoteAddr();

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", VNPayConfig.VNP_TMNCODE);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", txnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.VNP_RETURNURL);
        vnp_Params.put("vnp_IpAddr", ipAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8)).append('&');
                query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8)).append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8)).append('&');
            }
        }
        hashData.setLength(hashData.length() - 1);
        query.setLength(query.length() - 1);

        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.VNP_HASH_SECRET, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);

        String paymentUrl = VNPayConfig.VNP_URL + "?" + query.toString();
        response.sendRedirect(paymentUrl);
    }

    //vui lòng tham khảo thêm tại code demo
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
