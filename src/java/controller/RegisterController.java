/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.User;
import helper.EmailUtil;
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
import java.util.Random;
import model.DAOSetting;
import model.DAOUser;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author HP
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/RegisterController"})
public class RegisterController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* Sample output */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Authorize: kiểm tra người dùng có được phép truy cập trang này không
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        if (user != null) {
            // Nếu đã đăng nhập, chuyển hướng về trang chủ
            response.sendRedirect("HomePageController");
            return;
        }
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/login.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DAOUser daoUser = new DAOUser();

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String name = request.getParameter("name");
        String genderParam = request.getParameter("gender");
        String phoneNumber = request.getParameter("phoneNumber");
        String dateOfBirthStr = request.getParameter("dateOfBirth");
        
        
        response.setContentType("text/plain");

        DAOSetting daoSetting = new DAOSetting();
        if ("Inactive".equals(daoSetting.getSettingById(1).getStatus())) {
            response.getWriter().write("cancel");
            return;
        }

        if (!Validate.isValidEmail(email)) {
            response.getWriter().write("Invalid email format!");
            return;
        }

        if (!Validate.checkRegisterExistedEmail(email)) {
            response.getWriter().write("Email was existed!");
            return;
        }

        if (!Validate.checkRegisterPasswordLength(password)) {
            response.getWriter().write("You need to enter a password at least 6 characters!");
            return;
        }

        if (!Validate.checkRegisterEqualPassword(password, confirmPassword)) {
            response.getWriter().write("Passwords do not match!");
            return;
        }

        if (!Validate.isValidName(name)) {
            response.getWriter().write("Name must contain only letters and spaces!");
            return;
        }

        if (!Validate.isValidPhoneNumber(phoneNumber)) {
            response.getWriter().write("Invalid phone number format!");
            return;
        }

        if (!Validate.isValidDateOfBirth(dateOfBirthStr)) {
            response.getWriter().write("Date of birth cannot be in the future and must be in YYYY-MM-DD format!");
            return;
        }

        String verificationCode = String.format("%06d", new Random().nextInt(999999));
        EmailUtil.sendRegisterMail(email, verificationCode);

        HttpSession session = request.getSession();

        System.out.println("name" + name);
        System.out.println("genderParam" + genderParam);
        System.out.println("phoneNumber" + phoneNumber);
        System.out.println("dateOfBirthStr" + dateOfBirthStr);

        session.setAttribute("email", email);
        session.setAttribute("password", BCrypt.hashpw(password, BCrypt.gensalt()));
        session.setAttribute("verificationCode", verificationCode);

        session.setAttribute("name", name);
        session.setAttribute("gender", genderParam);
        session.setAttribute("phoneNumber", phoneNumber);
        session.setAttribute("dateOfBirth", dateOfBirthStr);

        response.getWriter().write("redirect");
    }

    @Override
    public String getServletInfo() {
        return "RegisterController handles user registration with extended validations";
    }
}
