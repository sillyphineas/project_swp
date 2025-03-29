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
import model.DAOUser;

/**
 * Xử lý xác thực mã đăng ký, tạo tài khoản mới (bao gồm set point = 0).
 */
@WebServlet(name = "VerifyAccountController", urlPatterns = {"/VerifyAccountController"})
public class VerifyAccountController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Authorize
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

        if (service.equals("cancel")) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/404.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String enteredCode = request.getParameter("code");
        String storedCode = (String) session.getAttribute("verificationCode");

        String email = (String) session.getAttribute("email");
        String hashedPassword = (String) session.getAttribute("password");

        String name = (String) session.getAttribute("name");
        String genderParam = (String) session.getAttribute("gender");
        String phoneNumber = (String) session.getAttribute("phoneNumber");
        String dateOfBirthStr = (String) session.getAttribute("dateOfBirth");
        
        System.out.println("name" + name);
        System.out.println("genderParam" + genderParam);
        System.out.println("phoneNumber" + phoneNumber);
        System.out.println("dateOfBirthStr" + dateOfBirthStr);

        response.setContentType("text/plain");

        if (enteredCode != null && enteredCode.equals(storedCode)) {
            DAOUser daoUser = new DAOUser();
            User newUser = new User();

            newUser.setEmail(email);
            newUser.setPassHash(hashedPassword);
            newUser.setRoleId(5);
            newUser.setIsDisabled(false);
            newUser.setUpdatedBy(1);
            newUser.setName(name);
            boolean gender = "1".equals(genderParam);
            newUser.setGender(gender);
            newUser.setPhoneNumber(phoneNumber);

            if (dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
                try {
                    newUser.setDateOfBirth(java.sql.Date.valueOf(dateOfBirthStr));
                } catch (Exception e) {
                    newUser.setDateOfBirth(null);
                }
            }
            newUser.setPoint(0);

            if (daoUser.addUser2(newUser) != 0) {
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
