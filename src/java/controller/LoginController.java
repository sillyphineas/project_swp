/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.User;
import helper.Validate;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DAOOrder;
import model.DAOUser;

/**
 *
 * @author HP
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        if (user != null) {
            response.sendRedirect("HomePageController");
            return;
        }
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/login.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        int loginStatus = Validate.checkLoginStatus(email, password);

        switch (loginStatus) {
            case -1:
                response.getWriter().write("Email does not exist in the system!");
                break;
            case -2:
                response.getWriter().write("Your account is disabled!");
                break;
            case -3:
                response.getWriter().write("Incorrect password!");
                break;
            default:
                DAOUser daoUser = new DAOUser();
                User user = daoUser.getUserByEmail(email);

                HttpSession session = request.getSession(true);
                session.setAttribute("isLoggedIn", true);
                session.setAttribute("user", user);
                session.setAttribute("userID", user.getId());
                response.getWriter().write("success:" + loginStatus);
                break;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
