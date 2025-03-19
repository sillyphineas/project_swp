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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ShipperDashboardController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ShipperDashboardController at " + request.getContextPath () + "</h1>");
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
        if (!Authorize.isAccepted(user, "/ShipperDashboardController")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String shippingStatus = request.getParameter("shippingStatus");

        if (startDate == null || endDate == null) {
            startDate = getFormattedDate(-7);
            endDate = getFormattedDate(0);
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
    private String getFormattedDate(int daysAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, daysAgo);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

}
