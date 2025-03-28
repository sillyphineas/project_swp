/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAOUser;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author DUC MINH
 */
@WebServlet(name = "ChangePasswordController", urlPatterns = {"/ChangePasswordController"})
public class ChangePasswordController extends HttpServlet {

    private DAOUser daoUser = new DAOUser();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String userIdParam = request.getParameter("id");
        if (userIdParam == null) {
            response.sendRedirect("HomePageController");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdParam);
            User user = daoUser.getUserById(userId);
            if (user == null) {
                response.sendRedirect("HomePageController");
                return;
            }
            request.getRequestDispatcher("WEB-INF/views/change_password.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // Gửi lỗi khi không thể chuyển đổi userId
            request.setAttribute("errorMessage", "Invalid user ID format.");
            request.getRequestDispatcher("WEB-INF/views/change_password.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy các tham số từ form

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        Integer userId = (Integer) request.getSession().getAttribute("userID");

        if (userId == null) {
            request.setAttribute("errorMessage", "User ID is missing or session has expired.");
            request.getRequestDispatcher("WEB-INF/views/change_password.jsp").forward(request, response);
            return;
        }
        try {

            User user = daoUser.getUserById(userId);

            if (user == null) {
                request.setAttribute("errorMessage", "User does not exist!");
                request.getRequestDispatcher("WEB-INF/views/change_password.jsp").forward(request, response);
                return;
            }

            // Kiểm tra mật khẩu cũ
            if (!BCrypt.checkpw(currentPassword, user.getPassHash())) {
                request.setAttribute("errorMessage", "Current password is incorrect!");
                request.getRequestDispatcher("WEB-INF/views/change_password.jsp").forward(request, response);
                return;
            }

            // Kiểm tra mật khẩu mới (độ dài và xác nhận)
            if (newPassword == null || newPassword.length() < 6) {
                request.setAttribute("errorMessage", "New password must be at least 6 characters!");
                request.getRequestDispatcher("WEB-INF/views/change_password.jsp").forward(request, response);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("errorMessage", "Confirm password does not match!");
                request.getRequestDispatcher("WEB-INF/views/change_password.jsp").forward(request, response);
                return;
            }

            // Mã hóa mật khẩu mới
            String newHashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            // Cập nhật mật khẩu mới trong cơ sở dữ liệu
            int updateCount = daoUser.updatePassword(userId, newHashedPassword);

            if (updateCount > 0) {
                // Đổi mật khẩu thành công, chuyển hướng đến trang login
                request.setAttribute("successMessage", "Password changed successfully! Please login again.");
                request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
            } else {
                // Đổi mật khẩu thất bại
                request.setAttribute("errorMessage", "Password change failed! Please try again.");
                request.getRequestDispatcher("WEB-INF/views/change_password.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid user ID format.");
            request.getRequestDispatcher("WEB-INF/views/change_password.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Change Password Controller";
    }
}
