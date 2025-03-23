/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.User;
import helper.Authorize;
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
import java.util.List;
import java.util.Map;
import model.DAOOrder;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author HP
 */
@WebServlet(name = "AdminDashboardController", urlPatterns = {"/AdminDashboardController"})
public class AdminDashboardController extends HttpServlet {

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
            out.println("<title>Servlet AdminDashboardController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminDashboardController at " + request.getContextPath() + "</h1>");
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
        if (!Authorize.isAccepted(user, "/AdminDashboardController")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }

        DAOOrder daoOrder = new DAOOrder();
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        if (startDate == null || startDate.isEmpty()) {
            startDate = getFormattedDate(-7);
        }
        if (endDate == null || endDate.isEmpty()) {
            endDate = getFormattedDate(0);
        }
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        Map<String, Integer> orderStatusCounts = daoOrder.getOrderStatusCountsByDateRange(startDate, endDate);
        if (orderStatusCounts == null) {
            orderStatusCounts = new HashMap<>();
        }

        double totalRevenue = daoOrder.getTotalRevenueByDateRange(startDate, endDate);
        int newCustomersCount = daoOrder.getNewCustomersCountByDateRange(startDate, endDate);
        int newBuyersCount = daoOrder.getNewBuyersCountByDateRange(startDate, endDate);
        double averageRating = daoOrder.getAverageRatingByDateRange(startDate, endDate);
        // Lấy dữ liệu xu hướng đơn hàng từ database
        List<Object[]> orderTrends = daoOrder.getOrderTrendsByDateRange(startDate, endDate);

// Chuyển đổi mảng Object[] thành các đối tượng Map với key "date" và "count"
        List<Map<String, Object>> trendList = new ArrayList<>();
        for (Object[] trend : orderTrends) {
            // Giả sử trend[0] là ngày (date) và trend[1] là số lượng đơn hàng (count)
            Map<String, Object> trendMap = new HashMap<>();
            trendMap.put("date", trend[0].toString());   // trend[0] chứa ngày
            trendMap.put("count", trend[1]);  // trend[1] chứa số lượng đơn hàng
            trendList.add(trendMap);
        }

        String orderTrendsJson = new Gson().toJson(trendList);  // Chuyển đổi dữ liệu thành chuỗi JSON
        request.setAttribute("orderTrendsJson", orderTrendsJson);  // Truyền chuỗi JSON vào request

        request.setAttribute("orderStatusCounts", orderStatusCounts);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("newCustomersCount", newCustomersCount);
        request.setAttribute("newBuyersCount", newBuyersCount);
        request.setAttribute("averageRating", averageRating);

        System.out.println(orderTrendsJson);
        System.out.println(orderStatusCounts);
// Chuyển tiếp request đến JSP
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/adminDashboard.jsp");
        rd.forward(request, response);

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

    private String getFormattedDate(int daysAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, daysAgo);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }
}
