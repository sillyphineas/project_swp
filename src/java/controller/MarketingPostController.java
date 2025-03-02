/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Blog;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAOBlog;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "MarketingPostController", urlPatterns = {"/MarketingPostController"})
public class MarketingPostController extends HttpServlet {

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
        DAOBlog dao = new DAOBlog();
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String service = request.getParameter("service");
            if (service == null) {
                service = "listAllBlogs";
            }
            if (service.equals("listAllBlogs")) {
                String pageParam = request.getParameter("page");
                int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
                int pageSize = 10;
                List<Blog> blogs = dao.MaketingBlogs(page, pageSize);
                int totalBlogs = dao.getMaketingTotalBlogs();
                int totalPages = (int) Math.ceil((double) totalBlogs / pageSize);

                request.setAttribute("blogs", blogs);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("currentPage", page);
                request.getRequestDispatcher("/WEB-INF/views/Marketing-PostList.jsp").forward(request, response);
            }
            if (service.equals("search")) {
                String query = request.getParameter("query");
                String pageStr = request.getParameter("page");
                int page = 1;
                int pageSize = 10;
                query = query.toLowerCase();
                if (query == null || query.trim().isEmpty()) {
                    request.setAttribute("error", "Please enter a search!!");
                    request.getRequestDispatcher("/WEB-INF/views/Marketing-PostList.jsp").forward(request, response);
                    return;
                }

                try {
                    if (pageStr != null && !pageStr.isEmpty()) {
                        pageStr = pageStr.trim();
                        page = Integer.parseInt(pageStr);
                    }
                } catch (NumberFormatException e) {
                    page = 1;
                }

                List<Blog> blogs = dao.searchBlogs(query, page, pageSize);
                int totalBlogs = dao.countTotalBlogsForSearch(query);
                int totalPages = (int) Math.ceil((double) totalBlogs / pageSize);

                if (blogs.isEmpty()) {
                    request.setAttribute("message", "No results found.");
                }

                request.setAttribute("blogs", blogs);
                request.setAttribute("query", query);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                request.getRequestDispatcher("/WEB-INF/views/Marketing-PostList.jsp").forward(request, response);
            }
            if (service.equals("removeBlog")) {
                String blogIdStr = request.getParameter("blogId");

                if (blogIdStr != null && !blogIdStr.isEmpty()) {
                    try {
                        int blogId = Integer.parseInt(blogIdStr);

                        int checked = dao.delete(blogId);

                        response.setContentType("application/json");

                        if (checked > 0) {
                            response.getWriter().write("{\"status\":\"success\", \"message\":\"Blog deleted successfully\"}");
                        } else {
                            response.getWriter().write("{\"status\":\"error\", \"message\":\"Error deleting blog\"}");
                        }
                    } catch (NumberFormatException e) {
                        // Nếu blogId không phải là số hợp lệ
                        response.setContentType("application/json");
                        response.getWriter().write("{\"status\":\"error\", \"message\":\"Invalid blog ID\"}");
                    }
                } else {
                    // Nếu không có blogId trong request
                    response.setContentType("application/json");
                    response.getWriter().write("{\"status\":\"error\", \"message\":\"Blog ID not provided\"}");
                }
            }

            if (service.equals("blogFilter")) {
                String idStr = request.getParameter("id");
                Integer id = (idStr != null && !idStr.isEmpty()) ? Integer.parseInt(idStr) : null;

                String authorIdStr = request.getParameter("authorID");
                Integer authorID = (authorIdStr != null && !authorIdStr.isEmpty()) ? Integer.parseInt(authorIdStr) : null;

                String statusStr = request.getParameter("status");
                Boolean isDisabled = (statusStr != null && !statusStr.isEmpty()) ? Boolean.valueOf(statusStr) : null;

                String pageStr = request.getParameter("page");
                int page = (pageStr != null && !pageStr.isEmpty()) ? Integer.parseInt(pageStr) : 1;

                int pageSize = 10;

                List<Blog> blogs = dao.getFilteredBlogs(page, pageSize, id, authorID, isDisabled);

                int totalBlogs = dao.getTotalBlogs(id, authorID, isDisabled);
                int totalPages = (int) Math.ceil((double) totalBlogs / pageSize);

                request.setAttribute("blogs", blogs);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/Marketing-PostList.jsp");
                dispatcher.forward(request, response);
            }
            if (service.equals("sort")) {
                String sortBy = request.getParameter("sortBy");
                String sortOrder = request.getParameter("sortOrder");
                String pageStr = request.getParameter("page");
                int page = 1;
                int pageSize = 5;

                if (pageStr != null && !pageStr.trim().isEmpty()) {
                    pageStr = pageStr.trim();
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

                List<Blog> blogs = dao.sortBlogs(sortBy, sortOrder, page, pageSize);

                int totalBlogs = dao.getTotalBlogs();
                int totalPages = (int) Math.ceil((double) totalBlogs / pageSize);

                if (blogs.isEmpty()) {
                    request.setAttribute("message", "No results found.");
                }

                request.setAttribute("blogs", blogs);
                request.setAttribute("sortBy", sortBy);
                request.setAttribute("sortOrder", sortOrder);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.getRequestDispatcher("/WEB-INF/views/Marketing-PostList.jsp").forward(request, response);
            }

            if (service.equals("changeStatus")) {
                String idStr = request.getParameter("id");
                Integer id = (idStr != null && !idStr.isEmpty()) ? Integer.parseInt(idStr) : null;

                String statusStr = request.getParameter("status"); // Nhận giá trị true/false từ JavaScript
                boolean status = "true".equals(statusStr);  // Chuyển "true" thành true, "false" thành false

                if (id != null) {
                    boolean success = dao.updateBlogStatus(id, status);  // Gọi DAO để thay đổi trạng thái

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
            Logger.getLogger(MarketingPostController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MarketingPostController.class.getName()).log(Level.SEVERE, null, ex);
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
