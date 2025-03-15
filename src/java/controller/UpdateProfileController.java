/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.DAOUser;

@WebServlet(name = "UpdateProfileController", urlPatterns = {"/UpdateProfileController"})
public class UpdateProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        DAOUser userDao = new DAOUser();
        User user = userDao.getUserById(userId);

        request.setAttribute("user", user);  // Đưa đối tượng user vào request để hiển thị trong JSP
        request.getRequestDispatcher("WEB-INF/views/updateProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin người dùng từ session
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        // Lấy thông tin từ form
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
        String dateOfBirth = request.getParameter("dateOfBirth");
        
        if (dateOfBirth == null || dateOfBirth.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Date of birth cannot be empty!");
            request.getRequestDispatcher("WEB-INF/views/updateProfile.jsp").forward(request, response);
            return;
        }
        // Kiểm tra định dạng ngày tháng
        // Kiểm tra định dạng ngày tháng và tạo đối tượng java.util.Date từ chuỗi
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = sdf.parse(dateOfBirth);

            if (birthDate.after(new Date())) {
                request.setAttribute("errorMessage", "Date of birth cannot be in the future!");
                request.getRequestDispatcher("WEB-INF/views/updateProfile.jsp").forward(request, response);
                return;
            }

            // Cập nhật ngày sinh vào user hiện tại
            currentUser.setDateOfBirth(new java.sql.Date(birthDate.getTime()));
        } catch (ParseException e) {
            request.setAttribute("errorMessage", "Invalid date of birth format!");
            request.getRequestDispatcher("WEB-INF/views/updateProfile.jsp").forward(request, response);
            return;
        }

        // Kiểm tra email và số điện thoại không trùng
        if (isEmailOrPhoneTaken(email, phoneNumber, currentUser.getId())) {
            request.setAttribute("errorMessage", "Email or phone number is already taken");
            request.getRequestDispatcher("WEB-INF/views/updateProfile.jsp").forward(request, response);
            return;
        }

        // Xử lý upload hình ảnh (nếu có)
        Part imagePart = request.getPart("image");
        byte[] imageBytes = null;

        if (imagePart != null && imagePart.getSize() > 0) {
            // Đọc dữ liệu của ảnh vào byte array
            imageBytes = new byte[(int) imagePart.getSize()];
            InputStream inputStream = imagePart.getInputStream();
            inputStream.read(imageBytes);
            inputStream.close();
        } else {
            // Nếu không có ảnh mới, giữ lại ảnh cũ
            imageBytes = currentUser.getImage();
        }

        // Cập nhật thông tin vào đối tượng User
        User updatedUser = new User();
        updatedUser.setId(currentUser.getId());
        updatedUser.setName(name);
        updatedUser.setEmail(email);
        updatedUser.setPhoneNumber(phoneNumber);
        updatedUser.setGender(gender);

        updatedUser.setImage(imageBytes);

        // Cập nhật dữ liệu vào cơ sở dữ liệu
        DAOUser userDao = new DAOUser();
        int result = userDao.updateUser2(updatedUser);

        // Kiểm tra kết quả
        if (result > 0) {
            session.setAttribute("user", updatedUser); // Cập nhật thông tin người dùng trong session
            response.sendRedirect("UserProfileServlet");
        } else {
            request.setAttribute("errorMessage", "Failed to update profile.");
            request.getRequestDispatcher("WEB-INF/views/updateProfile.jsp").forward(request, response);
        }
    }

    private boolean isEmailOrPhoneTaken(String email, String phoneNumber, int userId) {
        DAOUser userDao = new DAOUser();
        User existingUserByEmail = userDao.getUserByEmail(email);
        User existingUserByPhone = userDao.getUserByPhoneNumber(phoneNumber);

        if ((existingUserByEmail != null && existingUserByEmail.getId() != userId)
                || (existingUserByPhone != null && existingUserByPhone.getId() != userId)) {
            return true;
        }
        return false;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
