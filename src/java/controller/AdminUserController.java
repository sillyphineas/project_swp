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
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAOUser;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class AdminUserController extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        DAOUser dao = new DAOUser();
        try (PrintWriter out = response.getWriter()) {
            //Authorize
            HttpSession session = request.getSession(false);
            User user = null;
            if (session != null) {
                user = (User) session.getAttribute("user");
            }
            if (!Authorize.isAccepted(user, "/UserController")) {
                request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
                return;
            }

            String service = request.getParameter("service");
            if (service == null) {
                service = "listAllUser";
            }
            if (service.equals("listAllUser")) {
                String pageStr = request.getParameter("page");
                int page = (pageStr != null && !pageStr.isEmpty()) ? Integer.parseInt(pageStr) : 1;
                int pageSize = 10;
                List<User> users = dao.getPaginatedUsers(page, pageSize);
                int totalUsers = dao.getTotalUsers();
                int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

                request.setAttribute("users", users);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user-list.jsp");
                dispatcher.forward(request, response);
            }
            if (service.equals("userFilter")) {
                String genderStr = request.getParameter("gender");
                Boolean gender = (genderStr != null && !genderStr.isEmpty()) ? Boolean.valueOf(genderStr) : null;

                String roleStr = request.getParameter("role");
                Integer roleId = (roleStr != null && !roleStr.isEmpty()) ? Integer.parseInt(roleStr) : null;

                String statusStr = request.getParameter("status");
                Boolean isDisabled = (statusStr != null && !statusStr.isEmpty()) ? Boolean.valueOf(statusStr) : null;

                String pageStr = request.getParameter("page");
                int page = (pageStr != null && !pageStr.isEmpty()) ? Integer.parseInt(pageStr) : 1;
                int pageSize = 10;

                List<User> users = dao.getFilteredUsers(page, pageSize, gender, roleId, isDisabled);

                int totalUsers = dao.getTotalUsers(gender, roleId, isDisabled);

                int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

                request.setAttribute("users", users);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user-list.jsp");
                dispatcher.forward(request, response);
            }
            if (service.equals("search")) {
                String query = request.getParameter("query");
                String pageStr = request.getParameter("page");
                int page = 1;
                int pageSize = 10;

                if (query == null || query.trim().isEmpty()) {
                    request.setAttribute("error", "Please enter a search term!");
                    request.getRequestDispatcher("/WEB-INF/views/user-list.jsp").forward(request, response);
                    return;
                }

                try {
                    if (pageStr != null && !pageStr.isEmpty()) {
                        page = Integer.parseInt(pageStr);
                    }
                } catch (NumberFormatException e) {
                    page = 1;
                }

                List<User> users = dao.searchUsers(query, page, pageSize);
                int totalUsers = dao.countTotalUsers(query);
                int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

                if (users.isEmpty()) {
                    request.setAttribute("message", "No results found.");
                }

                request.setAttribute("users", users);
                request.setAttribute("query", query);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                request.getRequestDispatcher("/WEB-INF/views/user-list.jsp").forward(request, response);
            }

            if (service.equals("sort")) {
                String sortBy = request.getParameter("sortBy");
                String sortOrder = request.getParameter("sortOrder");
                String pageStr = request.getParameter("page");
                int page = 1;
                int pageSize = 10;

                if (pageStr != null && !pageStr.isEmpty()) {
                    try {
                        page = Integer.parseInt(pageStr);
                    } catch (NumberFormatException e) {
                        page = 1;
                    }
                }
                if (sortBy == null || sortBy.trim().isEmpty()) {
                    sortBy = "id";
                }
                if (sortOrder == null || sortOrder.trim().isEmpty()) {
                    sortOrder = "asc";
                }

                List<User> users = dao.sortUsers(sortBy, sortOrder, page, pageSize);
                int totalUsers = dao.countTotalUsers("");  // Total users count, no search filter
                int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

                if (users.isEmpty()) {
                    request.setAttribute("message", "No results found.");
                }

                request.setAttribute("users", users);
                request.setAttribute("sortBy", sortBy);
                request.setAttribute("sortOrder", sortOrder);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                request.getRequestDispatcher("/WEB-INF/views/user-list.jsp").forward(request, response);
            }

            if (service.equals("changeStatus")) {
                String idStr = request.getParameter("id");
                Integer id = (idStr != null && !idStr.isEmpty()) ? Integer.parseInt(idStr) : null;

                String statusStr = request.getParameter("status");
                boolean status = statusStr.equals("true");  // true means active, false means disabled

                if (id != null) {
                    boolean success = dao.updateUserStatus(id, status);

                    response.setContentType("text/plain");
                    response.getWriter().write(success ? "Status updated" : "Failed to update status");
                }
            }

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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AdminUserController.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AdminUserController.class.getName()).log(Level.SEVERE, null, ex);
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
