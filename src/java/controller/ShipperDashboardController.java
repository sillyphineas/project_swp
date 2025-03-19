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
import model.DAOShipping;
import entity.Shipping;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.DAOOrder;
/**
 *
 * @author HP
 */
@WebServlet(name="ShipperDashboardController", urlPatterns={"/ShipperDashboardController"})
public class ShipperDashboardController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Authorize
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        if (!Authorize.isAccepted(user, "/ShipperDashboardController")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String shippingStatus = request.getParameter("shippingStatus");

        // Nếu không có startDate hoặc endDate, mặc định là 7 ngày trước đến hôm nay
        if (startDate == null || endDate == null) {
            startDate = getFormattedDate(-7); // 7 ngày trước
            endDate = getFormattedDate(0);    // Hôm nay
        }

        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("shippingStatus", shippingStatus != null ? shippingStatus : "");

        try {
            DAOShipping dao = new DAOShipping();
            List<Map<String, Object>> shippingStats = dao.getShippingStatsByDate(startDate, endDate, shippingStatus);
            request.setAttribute("shippingStats", shippingStats);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("shippingStats", new ArrayList<>());
        }

        request.getRequestDispatcher("WEB-INF/views/shipperDashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Chuyển POST sang GET nếu cần
    }

    private String getFormattedDate(int daysAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, daysAgo);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng phù hợp với SQL
        return sdf.format(calendar.getTime());
    }

}
