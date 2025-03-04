/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Role;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entity.User;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.DAORole;
import model.DAOUser;

/**
 *
 * @author Admin
 */
@WebServlet(name = "UserDetailController", urlPatterns = {"/UserDetailController"})
public class UserDetailController extends HttpServlet {

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
            out.println("<title>Servlet UserDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserDetailController at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("action");
        if (action == null) {
            action = "display";
        }
        if (action.equals("addUser")) {
            // Truyền danh sách các vai trò từ DB vào JSP
            DAORole daoRole = new DAORole();
            List<Role> roles = daoRole.getAllRoles();  // Lấy tất cả các vai trò
            request.setAttribute("roles", roles);
            request.getRequestDispatcher("/WEB-INF/views/add_user.jsp").forward(request, response);
        }
          if (action.equals("updateUser")) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            System.out.println("Get     "+userId);
            DAOUser daoUser = new DAOUser();
            User user = daoUser.getUserById(userId);
            System.out.println("Get         "+user.toString());

            request.setAttribute("user", user);
            DAORole daoRole = new DAORole();
            List<Role> roles = daoRole.getAllRoles();
            request.setAttribute("roles", roles);
            request.getRequestDispatcher("/WEB-INF/views/edit_user.jsp").forward(request, response);
        }
         if (action.equals("details")) {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            DAOUser daoUser = new DAOUser();
            User user = daoUser.getUserById(userId);
            request.setAttribute("user", user);

            DAORole daoRole = new DAORole();
            List<Role> roles = daoRole.getAllRoles();
            request.setAttribute("roles", roles);

            request.getRequestDispatcher("/WEB-INF/views/user_details.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("UserController"); // Chuyển hướng nếu có lỗi
        
    }
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

        String action = request.getParameter("action");
        System.out.println("Post       " + action);
        DAOUser dao = new DAOUser();
         if (action != null && action.equals("addUser")) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
            String phoneNumber = request.getParameter("phoneNumber");
            String dateOfBirth = request.getParameter("dateOfBirth");
            boolean isDisabled = Boolean.parseBoolean(request.getParameter("isDisabled"));
            int roleId = Integer.parseInt(request.getParameter("roleId"));  // Lấy roleId từ form

            // Mã hóa mật khẩu (nếu cần)
            String passHash = password; // Thực tế cần mã hóa mật khẩu, đây là ví dụ đơn giản.

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassHash(passHash);
            user.setGender(gender);
            user.setPhoneNumber(phoneNumber);
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate = sdf.parse(dateOfBirth);
                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
                user.setDateOfBirth(sqlDate);
            } catch (Exception e) {
                // Handle error if needed
            }
            user.setDisabled(isDisabled);
            user.setRoleId(roleId);  // Set roleId

            dao.addUser(user);
            response.sendRedirect("UserController");
        }

//        if (action != null && action.equals("updateUser")) {
//            int userId = Integer.parseInt(request.getParameter("userId"));
//            String name = request.getParameter("name");
//            String email = request.getParameter("email");
//            String password = request.getParameter("password");
//            boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
//            String phoneNumber = request.getParameter("phoneNumber");
//            String dateOfBirth = request.getParameter("dateOfBirth");
//            boolean isDisabled = Boolean.parseBoolean(request.getParameter("isDisabled"));
//
//            // Mã hóa mật khẩu (nếu cần)
//            String passHash = password; // Thực tế cần mã hóa mật khẩu, đây là ví dụ đơn giản.
//
//            User user = new User();
//            user.setId(userId);
//            user.setName(name);
//            user.setEmail(email);
//            user.setPassHash(passHash);
//            user.setGender(gender);
//            user.setPhoneNumber(phoneNumber);
//            try {
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng ngày tháng
//                java.util.Date parsedDate = sdf.parse(dateOfBirth); // Chuyển chuỗi thành java.util.Date
//                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime()); // Chuyển java.util.Date thành java.sql.Date
//                user.setDateOfBirth(sqlDate); // Gán giá trị cho user
//            } catch (Exception e) {
//            }
//            user.setDisabled(isDisabled);
//            
//            System.out.println("Post           " + user.toString());
//            dao.updateUser(user);
//            response.sendRedirect("UserController");
//        }
         

  

   if (action != null && action.equals("updateUser")) {
        // Lấy tất cả các giá trị từ form
        int userId = Integer.parseInt(request.getParameter("userId"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
        String phoneNumber = request.getParameter("phoneNumber");
        String resetToken = request.getParameter("resetToken");

        // Chuyển đổi resetTokenExpired từ chuỗi thành Date
        String resetTokenExpiredStr = request.getParameter("resetTokenExpired");
        java.sql.Date resetTokenExpired = null;
        try {
            if (resetTokenExpiredStr != null && !resetTokenExpiredStr.isEmpty()) {
                // Sử dụng SimpleDateFormat để kiểm tra và chuyển đổi ngày tháng
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate = sdf.parse(resetTokenExpiredStr);
                resetTokenExpired = new java.sql.Date(parsedDate.getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi nếu cần thiết
        }

        // Chuyển đổi dateOfBirth từ chuỗi thành Date
        String dateOfBirthStr = request.getParameter("dateOfBirth");
        java.sql.Date dateOfBirth = null;
        try {
            if (dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
                // Sử dụng SimpleDateFormat để chuyển đổi ngày tháng từ chuỗi
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate = sdf.parse(dateOfBirthStr);
                dateOfBirth = new java.sql.Date(parsedDate.getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi nếu cần thiết
        }

        boolean isDisabled = Boolean.parseBoolean(request.getParameter("isDisabled"));
        int roleId = Integer.parseInt(request.getParameter("roleId"));

        // Kiểm tra xem roleId có tồn tại trong bảng roles không
        DAORole daoRole = new DAORole();
        Role role = daoRole.getRoleById(roleId); // Giả sử có phương thức getRoleById trong DAORole
        if (role == null) {
            // Nếu không tồn tại roleId trong bảng roles, trả về lỗi hoặc thông báo
            request.setAttribute("error", "Invalid role ID!");
            request.getRequestDispatcher("/WEB-INF/views/edit_user.jsp").forward(request, response);
            return;
        }

        // Mã hóa mật khẩu (nếu cần)
        String passHash = password; // Thực tế cần mã hóa mật khẩu, đây là ví dụ đơn giản.

        // Tạo đối tượng User và set tất cả các trường
        User user = new User();
        user.setId(userId);
        user.setName(name);
        user.setEmail(email);
        user.setPassHash(passHash);
        user.setGender(gender);
        user.setPhoneNumber(phoneNumber);
        user.setResetToken(resetToken);
        user.setResetTokenExpired(resetTokenExpired);
        user.setDateOfBirth(dateOfBirth);  // Set ngày sinh
        user.setDisabled(isDisabled);
        user.setRoleId(roleId);

        // Cập nhật người dùng trong cơ sở dữ liệu
        dao.updateUser(user);
        response.sendRedirect("UserController");
    
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
