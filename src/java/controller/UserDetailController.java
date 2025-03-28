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
import helper.Authorize;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.DAORole;
import model.DAOUser;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Admin
 */
@WebServlet(name = "UserDetailController", urlPatterns = {"/UserDetailController"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 5, // 5 MB
        maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
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
        //Authorize
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        if (!Authorize.isAccepted(user, "/UserDetailController")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }
        String action = request.getParameter("action");
        if (action == null) {
            action = "display";
        }
        if (action.equals("addUser")) {

            DAORole daoRole = new DAORole();
            List<Role> roles = daoRole.getAllRoles();
            request.setAttribute("roles", roles);
            request.getRequestDispatcher("/WEB-INF/views/add_user.jsp").forward(request, response);
        }
        if (action.equals("updateUser")) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            System.out.println("Get     " + userId);
            DAOUser daoUser = new DAOUser();
            user = daoUser.getUserById(userId);
            System.out.println("Get         " + user.toString());

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
                user = daoUser.getUserById(userId);
                request.setAttribute("user", user);

                DAORole daoRole = new DAORole();
                List<Role> roles = daoRole.getAllRoles();
                request.setAttribute("roles", roles);

                request.getRequestDispatcher("/WEB-INF/views/user_details.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("UserController");

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
    DAORole daoRole = new DAORole();  // Khai báo DAORole ở đây để dùng chung

    if (action != null && action.equals("addUser")) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
        String phoneNumber = request.getParameter("phoneNumber");
        String dateOfBirth = request.getParameter("dateOfBirth");
        boolean isDisabled = Boolean.parseBoolean(request.getParameter("isDisabled"));
        int roleId = Integer.parseInt(request.getParameter("roleId"));

        String passHashed = BCrypt.hashpw(password, BCrypt.gensalt());
        if (dao.isEmailExists(email)) {
            // Lấy danh sách roles để hiển thị lại trong form
            List<Role> roles = daoRole.getAllRoles();
            request.setAttribute("roles", roles);
            // Set lại các giá trị đã nhập để người dùng không phải nhập lại
            User user = new User();
            user.setId(userId);
            user.setName(name);
            user.setEmail(email);
            user.setGender(gender);
            user.setPhoneNumber(phoneNumber);
            user.setIsDisabled(isDisabled);
            user.setRoleId(roleId);
            request.setAttribute("user", user);
            
            request.setAttribute("error", "Email already exists, please re-enter!");
            request.getRequestDispatcher("/WEB-INF/views/add_user.jsp").forward(request, response);
            return;
        }
        
        byte[] image = null;
        Part imagePart = request.getPart("image");
        if (imagePart != null && imagePart.getSize() > 0) {
            // Kiểm tra kích thước file
            if (imagePart.getSize() > 1024 * 1024 * 5) { // 5MB
                List<Role> roles = daoRole.getAllRoles();
                request.setAttribute("roles", roles);
                request.setAttribute("error", "File size exceeds 5MB limit!");
                request.getRequestDispatcher("/WEB-INF/views/add_user.jsp").forward(request, response);
                return;
            }
            try (InputStream inputStream = imagePart.getInputStream()) {
                image = inputStream.readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
                List<Role> roles = daoRole.getAllRoles();
                request.setAttribute("roles", roles);
                request.setAttribute("error", "Error uploading image!");
                request.getRequestDispatcher("/WEB-INF/views/add_user.jsp").forward(request, response);
                return;
            }
        }

        User user = new User();
        user.setId(userId);
        user.setName(name);
        user.setEmail(email);
        user.setPassHash(passHashed);
        user.setGender(gender);
        user.setPhoneNumber(phoneNumber);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsedDate = null;
        try {
            parsedDate = sdf.parse(dateOfBirth);
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

            if (sqlDate.after(currentDate)) {
                List<Role> roles = daoRole.getAllRoles();
                request.setAttribute("roles", roles);
                request.setAttribute("error", "Ngày sinh không thể lớn hơn ngày hiện tại.");
                request.getRequestDispatcher("/WEB-INF/views/add_user.jsp").forward(request, response);
                return;
            }

            user.setDateOfBirth(sqlDate);
        } catch (Exception e) {
            // Xử lý exception nếu cần
        }
        user.setIsDisabled(isDisabled);
        user.setRoleId(roleId);
        user.setUpdatedBy(userId);
        user.setImage(image);

        dao.addUser(user);
        response.sendRedirect("UserController");
    }

        if (action != null && action.equals("updateUser")) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String passHashed = BCrypt.hashpw(password, BCrypt.gensalt());
            boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
            String phoneNumber = request.getParameter("phoneNumber");
            String resetToken = request.getParameter("resetToken");
            String resetTokenExpiredStr = request.getParameter("resetTokenExpired");
            java.sql.Timestamp resetTokenExpired = null;
            try {
                if (resetTokenExpiredStr != null && !resetTokenExpiredStr.isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedDate = sdf.parse(resetTokenExpiredStr);
                    resetTokenExpired = new java.sql.Timestamp(parsedDate.getTime());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String dateOfBirthStr = request.getParameter("dateOfBirth");
            java.sql.Date dateOfBirth = null;
            try {
                if (dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedDate = sdf.parse(dateOfBirthStr);
                    dateOfBirth = new java.sql.Date(parsedDate.getTime());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            boolean isDisabled = Boolean.parseBoolean(request.getParameter("isDisabled"));
            int roleId = Integer.parseInt(request.getParameter("roleId"));
            
            Role role = daoRole.getRoleById(roleId);
            if (role == null) {
                request.setAttribute("error", "Invalid role ID!");
                request.getRequestDispatcher("/WEB-INF/views/edit_user.jsp").forward(request, response);
                return;

            }
          byte[] image = null;
    Part imagePart = request.getPart("image");
    DAOUser daoUser = new DAOUser();
    User existingUser = daoUser.getUserById(userId); // Lấy thông tin user hiện tại

    if (imagePart != null && imagePart.getSize() > 0) {
        
        if (imagePart.getSize() > 1024 * 1024 * 5) { 
            request.setAttribute("error", "File size exceeds 5MB limit!");
            request.setAttribute("user", existingUser);
            List<Role> roles = daoRole.getAllRoles();
            request.setAttribute("roles", roles);
            request.getRequestDispatcher("/WEB-INF/views/edit_user.jsp").forward(request, response);
            return;
        }
        try (InputStream inputStream = imagePart.getInputStream()) {
            image = inputStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error uploading image!");
            request.setAttribute("user", existingUser);
            List<Role> roles = daoRole.getAllRoles();
            request.setAttribute("roles", roles);
            request.getRequestDispatcher("/WEB-INF/views/edit_user.jsp").forward(request, response);
            return;
        }
    } else {
        image = existingUser.getImage();
    }
            User user = new User();
            user.setId(userId);
            user.setName(name);
            user.setEmail(email);
            user.setPassHash(passHashed);
            user.setGender(gender);
            user.setPhoneNumber(phoneNumber);
            user.setResetToken(resetToken);
            user.setResetTokenExpired(resetTokenExpired);
            user.setDateOfBirth(dateOfBirth);  
            user.setIsDisabled(isDisabled);
            user.setRoleId(roleId);
            user.setUpdatedBy(userId);  
            user.setUpdatedAt(new java.sql.Date(System.currentTimeMillis()));
            if (image != null) {
                user.setImage(image);
            }
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
