/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import entities.User;
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
import models.DAOUser;

/**
 *
 * @author HP
 */
    @WebServlet(name = "VerifyAccountController", urlPatterns = {"/VerifyAccountController"})
public class VerifyAccountController extends HttpServlet {

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
            out.println("<title>Servlet VerifyAccountController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VerifyAccountController at " + request.getContextPath() + "</h1>");
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
        if (!Authorize.isAccepted(user, "/VerifyAccountController")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }
        String service = request.getParameter("service");
        if (service.equals("forward")) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/verify-account.jsp");
            rd.forward(request, response);
        }
        
        if (service.equals("confirmRegister")) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/success-registered.jsp");
            rd.forward(request, response);
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
        HttpSession session = request.getSession();
        String enteredCode = request.getParameter("code");
        String storedCode = (String) session.getAttribute("verificationCode");
        String email = (String) session.getAttribute("email");
        String hashedPassword = (String) session.getAttribute("password");

        response.setContentType("text/plain");

        if (enteredCode != null && enteredCode.equals(storedCode)) {
            DAOUser daoUser = new DAOUser();
            User user = new User();
            user.setEmail(email);
            user.setPassHash(hashedPassword);
            user.setRoleId(5);
            user.setDisabled(false);

            if (daoUser.addUser(user) != 0) {
                session.removeAttribute("verificationCode");

                response.sendRedirect("VerifyAccountController?service=confirmRegister");
            } else {
                response.sendRedirect("/WEB-INF/views/404.jsp");
            }
        } else {
            request.setAttribute("error", "Invalid code");
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/verify-account.jsp");
            rd.forward(request, response);
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
