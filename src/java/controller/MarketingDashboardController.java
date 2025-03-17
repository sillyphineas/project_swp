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
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.DAOBlog;
import model.DAOFeedback;
import model.DAOProduct;
import model.DAOUser;

/**
 *
 * @author HP
 */
@WebServlet(name="MarketingDashboardController", urlPatterns={"/MarketingDashboardController"})
public class MarketingDashboardController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet MarketingDashboardController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MarketingDashboardController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
        if (!Authorize.isAccepted(user, "/MarketingDashboardController")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        // Nếu không có ngày, gán ngày mặc định
        if (startDate == null || endDate == null) {
            startDate = getFormattedDate(-7); // 7 ngày trước
            endDate = getFormattedDate(0);   // Ngày hôm nay
        }

        // Đưa ngày vào request để hiển thị lại trong form
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);

        // Lấy thống kê từ cơ sở dữ liệu
        Map<String, Object> statistics = getStatistics(startDate, endDate);

        // Đưa số liệu vào request để hiển thị
        request.setAttribute("statistics", statistics);
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/marketingDashboard.jsp");
        rd.forward(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    private Map<String, Object> getStatistics(String startDate, String endDate) {
        Map<String, Object> statistics = new HashMap<>();

        try {
            // Tạo các DAO tương ứng
            DAOUser daoUser = new DAOUser();
            DAOProduct daoProduct = new DAOProduct();
            DAOBlog daoBlog = new DAOBlog();
            DAOFeedback daoFeedback = new DAOFeedback();

            // Lấy thống kê từ các bảng trong cơ sở dữ liệu
            List<Map<String, Object>> userStats = daoUser.getUserStatsByDate(startDate, endDate);
            List<Map<String, Object>> productStats = daoProduct.getProductStatsByDate(startDate, endDate);
            List<Map<String, Object>> blogStats = daoBlog.getBlogStatsByDate(startDate, endDate);
            List<Map<String, Object>> feedbackStats = daoFeedback.getFeedbackStatsByDate(startDate, endDate);

            statistics.put("userStats", userStats);
            statistics.put("productStats", productStats);
            statistics.put("blogStats", blogStats);
            statistics.put("feedbackStats", feedbackStats);
            System.out.println("User Stats: " + statistics.get("userStats"));
            System.out.println("Product Stats: " + statistics.get("productStats"));
            System.out.println("Blog Stats: " + statistics.get("blogStats"));
            System.out.println("Feedback Stats: " + statistics.get("feedbackStats"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statistics;
    }
    private String getFormattedDate(int daysAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, daysAgo);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }
}
