/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.DAOUser;
import java.io.File;
import java.io.FileOutputStream;

@WebServlet(name = "UpdateProfileController", urlPatterns = {"/UpdateProfileController"})
@MultipartConfig(
        fileSizeThreshold = 2097152, // 2MB
        maxFileSize = 10485760, // 10MB
        maxRequestSize = 52428800 // 50MB
)
public class UpdateProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        DAOUser userDao = new DAOUser();
        User user = userDao.getUserById(userId);
        if (user.getDateOfBirth() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(user.getDateOfBirth());
            request.setAttribute("formattedDate", formattedDate);
        }
        request.setAttribute("user", user);  // Đưa đối tượng user vào request để hiển thị trong JSP
        request.getRequestDispatcher("WEB-INF/views/updateProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy thông tin từ form
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
        String dateOfBirth = request.getParameter("dateOfBirth");

        String phonePattern = "^[0-9]{10,11}$";

        if (phoneNumber == null || !phoneNumber.matches(phonePattern)) {
            request.setAttribute("errorMessage", "Phone number must be 10 or 11 digits.");
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("WEB-INF/views/updateProfile.jsp").forward(request, response);
            return;
        }

        if (dateOfBirth == null || dateOfBirth.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Date of birth cannot be empty!!!");
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("WEB-INF/views/updateProfile.jsp").forward(request, response);
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = sdf.parse(dateOfBirth);

            if (birthDate.after(new Date())) {
                request.setAttribute("errorMessage", "Date of birth cannot be in the future!");
                request.setAttribute("user", currentUser);
                request.getRequestDispatcher("WEB-INF/views/updateProfile.jsp").forward(request, response);
                return;
            }

            String todayStr = sdf.format(new Date());
            String birthDateStr = sdf.format(birthDate);
            if (todayStr.equals(birthDateStr)) {
                request.setAttribute("errorMessage", "Date of birth cannot be today!");
                request.setAttribute("user", currentUser);
                request.getRequestDispatcher("WEB-INF/views/updateProfile.jsp").forward(request, response);
                return;
            }

            currentUser.setDateOfBirth(new java.sql.Date(birthDate.getTime()));
        } catch (ParseException e) {
            request.setAttribute("errorMessage", "Invalid date of birth format!");
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("WEB-INF/views/updateProfile.jsp").forward(request, response);
            return;
        }

        if (isEmailOrPhoneTaken(email, currentUser.getId())) {
            request.setAttribute("errorMessage", "Email is already taken");
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("WEB-INF/views/updateProfile.jsp").forward(request, response);
            return;
        }

        // Cập nhật thông tin
        currentUser.setName(name);
        currentUser.setEmail(email);
        currentUser.setPhoneNumber(phoneNumber);
        currentUser.setGender(gender);

        // Xử lý ảnh đại diện nếu có
        Part filePart = request.getPart("image");
        if (filePart != null && filePart.getSize() > 0) {
            try (InputStream fileContent = filePart.getInputStream(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileContent.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                currentUser.setImage(outputStream.toByteArray());
            }
        }

        currentUser.setUpdatedAt(new java.sql.Date(new Date().getTime()));
        currentUser.setUpdatedBy(currentUser.getId());

        DAOUser userDao = new DAOUser();
        int result = userDao.updateUser2(currentUser);

        if (result > 0) {
            session.setAttribute("user", currentUser); // Đảm bảo không tạo user mới
            response.sendRedirect("UserProfileServlet");
        } else {
            request.setAttribute("errorMessage", "Failed to update profile.");
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("WEB-INF/views/updateProfile.jsp").forward(request, response);
        }
    }

    private boolean isEmailOrPhoneTaken(String email, int userId) {
        DAOUser userDao = new DAOUser();
        User existingUserByEmail = userDao.getUserByEmail(email);

        if ((existingUserByEmail != null && existingUserByEmail.getId() != userId)) {
            return true;
        }
        return false;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
