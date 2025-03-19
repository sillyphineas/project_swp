/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import helper.EmailUtil;
import helper.Validate;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.UUID;
import java.sql.Timestamp;
import model.DAOUser;
import entity.User;
import java.sql.Date;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author HP
 */
@WebServlet(name = "ResetController", urlPatterns = {"/ResetController"})
public class ResetController extends HttpServlet {

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
            out.println("<title>Servlet ResetController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ResetController at " + request.getContextPath() + "</h1>");
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
        if (service.equals("findYourAccount")) {
            request.getRequestDispatcher("/WEB-INF/views/find-your-account.jsp").forward(request, response);
        }
        if (service.equals("resetPassword")) {
            String token = request.getParameter("token");
            if (token == null || token.trim().isEmpty()) {
                System.out.println("Hhehe");
                request.setAttribute("error", "Invalid token.");
                request.getRequestDispatcher("/WEB-INF/views/404.jsp").forward(request, response);
                return;
            }

            DAOUser daoUser = new DAOUser();
            User user = daoUser.getUserByResetToken(token);
            System.out.println("user token" + user.getResetTokenExpired());
            if (user == null) {
                System.out.println("Hoho");
                request.setAttribute("error", "Invalid or expired token.");
                request.getRequestDispatcher("/WEB-INF/views/404.jsp").forward(request, response);
                return;
            }

            Timestamp expiryDate = user.getResetTokenExpired();
            System.out.println("expiryDate.getTime() = " + expiryDate.getTime());
            if (expiryDate == null) {
                request.setAttribute("error", "Invalid or expired token.");
                request.getRequestDispatcher("/WEB-INF/views/404.jsp").forward(request, response);
                return;
            }

            if (expiryDate.getTime() < System.currentTimeMillis()) {
                daoUser.updateResetToken(user.getId(), null, null);
                request.setAttribute("error", "Token has expired.");
                request.getRequestDispatcher("/WEB-INF/views/404.jsp").forward(request, response);
                return;
            }

            request.setAttribute("token", token);
            request.getRequestDispatcher("/WEB-INF/views/set-newPassword.jsp").forward(request, response);
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
        if (service.equals("findYourAccount")) {
            String email = request.getParameter("email");
            if (email == null || email.trim().isEmpty()) {
                request.setAttribute("error", "Email cannot be empty.");
                request.getRequestDispatcher("/WEB-INF/views/find-your-account.jsp")
                        .forward(request, response);
                return;
            }

            if (!Validate.isValidEmail(email)) {
                request.setAttribute("error", "Invalid email format.");
                request.getRequestDispatcher("/WEB-INF/views/find-your-account.jsp")
                        .forward(request, response);
                return;
            }
            DAOUser daoUser = new DAOUser();
            entity.User user = daoUser.getUserByEmail(email);
            if (user == null) {
                request.setAttribute("error", "Email does not exist.");
                request.getRequestDispatcher("/WEB-INF/views/find-your-account.jsp")
                        .forward(request, response);
                return;
            }

            String token = generateResetToken();

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expiryTime = now.plusMinutes(30);
            Timestamp expiryTimestamp = Timestamp.valueOf(expiryTime);

            daoUser.updateResetToken(user.getId(), token, expiryTimestamp);
            String resetLink = "http://localhost:8080/project_swp/ResetController?service=resetPassword&token=" + token;

            EmailUtil.sendResetPasswordMail(email, resetLink);

            request.setAttribute("success", "A reset link has been sent to your email.");
            request.getRequestDispatcher("/WEB-INF/views/find-your-account.jsp").forward(request, response);
        }
        if (service.equals("setNewPassword")) {
            String token = request.getParameter("token");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            if (newPassword == null || newPassword.trim().isEmpty()
                    || confirmPassword == null || confirmPassword.trim().isEmpty()) {
                request.setAttribute("error", "Password fields cannot be empty.");
                request.setAttribute("token", token);
                request.getRequestDispatcher("/WEB-INF/views/set-newPassword.jsp")
                        .forward(request, response);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("error", "Passwords do not match.");
                request.setAttribute("token", token);
                request.getRequestDispatcher("/WEB-INF/views/set-newPassword.jsp")
                        .forward(request, response);
                return;
            }

            DAOUser daoUser = new DAOUser();
            User user = daoUser.getUserByResetToken(token);
            if (user == null) {
                request.setAttribute("error", "Invalid or expired token.");
                request.getRequestDispatcher("/WEB-INF/views/404.jsp")
                        .forward(request, response);
                return;
            }

            Timestamp expiryDate = user.getResetTokenExpired();
            if (expiryDate == null || expiryDate.getTime() < System.currentTimeMillis()) {
                request.setAttribute("error", "Token has expired.");
                request.getRequestDispatcher("/WEB-INF/views/404.jsp")
                        .forward(request, response);
                return;
            }

            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            daoUser.updatePasswordAndClearToken(user.getId(), hashedPassword);

            request.setAttribute("success", "Password changed successfully. Please log in.");
            request.getRequestDispatcher("/WEB-INF/views/changePasswordSuccess.jsp").forward(request, response);
        }
    }

    private String generateResetToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String getServletInfo() {
        return "Customer Order Management Controller with reload after Cancel/Refund and newly canceled item on top.";
    }

}
