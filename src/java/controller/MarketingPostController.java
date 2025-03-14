/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Blog;
import entity.Category;
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
import java.sql.*;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAOBlog;
import model.DAOCategory;

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
        //Authorize
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        if (!Authorize.isAccepted(user, "/MarketingPostController")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }
        response.setContentType("text/html;charset=UTF-8");
        DAOBlog dao = new DAOBlog();
        DAOCategory daoCate = new DAOCategory();
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String service = request.getParameter("service");
            List<Category> categories = daoCate.getAllCategories();
            request.setAttribute("categories", categories);
            if (service == null) {
                service = "listAllBlogs";
            }
            if (service.equals("listAllBlogs")) {
                String pageParam = request.getParameter("page");
                int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
                int pageSize = 5;
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
                int pageSize = 5;
                query = query.toLowerCase();
                if (query == null || query.trim().isEmpty()) {
                    request.setAttribute("error", "Please enter a search!!");
                    response.sendRedirect("MarketingPostController?service=listAllBlogs");
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

                List<Blog> blogs = dao.MaketingSearchBlogs(query, page, pageSize);
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
                            response.getWriter().write("{\"status\":\"error\", \"message\":\"Blog not found or could not be deleted\"}");
                        }
                    } catch (NumberFormatException e) {
                        response.setContentType("application/json");
                        response.getWriter().write("{\"status\":\"error\", \"message\":\"Invalid blog ID format\"}");
                    }
                } else {
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
                System.out.println("page:" + pageStr);
                int page = (pageStr != null && !pageStr.isEmpty()) ? Integer.parseInt(pageStr.trim()) : 1;

                int pageSize = 5;

                List<Blog> blogs = dao.getFilteredBlogs(page, pageSize, id, authorID, isDisabled);

                int totalBlogs = dao.getTotalBlogs(id, authorID, isDisabled);
                int totalPages = (int) Math.ceil((double) totalBlogs / pageSize);

                request.setAttribute("blogs", blogs);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("id", id);
                request.setAttribute("authorID", authorID);
                request.setAttribute("status", statusStr);
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

                String statusStr = request.getParameter("status");
                boolean status = statusStr.equals("true");

                if (id != null) {
                    boolean success = dao.updateBlogStatus(id, status);

                    response.setContentType("text/plain");
                    response.getWriter().write(success ? "Status updated" : "Failed to update status");
                }
            }
            if (service.equals("view")) {
                String idStr = request.getParameter("id");
                Integer id = (idStr != null && !idStr.trim().isEmpty()) ? Integer.parseInt(idStr) : null;

                if (id != null) {
                    Blog blog = dao.getBlogDetails(id);
                    if (blog != null) {
                        request.setAttribute("blog", blog);
                        request.getRequestDispatcher("/WEB-INF/views/blog-single.jsp").forward(request, response);
                    } else {
                        request.setAttribute("message", "Blog not found ");
                        request.getRequestDispatcher("/WEB-INF/views/blog-single.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("message", "Invalid blog ID");
                    request.getRequestDispatcher("/WEB-INF/views/blog-single.jsp").forward(request, response);
                }
            }
            if (service.equals("updateBlog")) {
                String submit = request.getParameter("submit");
                if (submit == null) {
                    int blogId = Integer.parseInt(request.getParameter("id"));
                    System.out.println("id blog" + blogId);
                    Blog blog = dao.getBlogDetails(blogId);
                    request.setAttribute("blog", blog);
                    ResultSet rsCategory = dao.getData("SELECT id, categoryName FROM categoryblog");
                    ResultSet rsAuthor = dao.getData("SELECT id, name FROM Users");
                    request.setAttribute("rsAuthor", rsAuthor);
                    request.setAttribute("rsCategory", rsCategory);
                    request.getRequestDispatcher("/WEB-INF/views/UpdateBlog.jsp").forward(request, response);
                } else {
                    int BlogID = Integer.parseInt(request.getParameter("id"));
                    String title = request.getParameter("title");
                    String content = request.getParameter("content");
                    String authorID = request.getParameter("authorID");
                    String postTime = request.getParameter("postTime");
                    String imageURL = request.getParameter("imageURL");
                    String backlinks = request.getParameter("backlinks");
                    boolean status = Boolean.parseBoolean(request.getParameter("isDisabled"));
                    System.out.println(status);
                    int authorId = Integer.parseInt(authorID);
                    Blog blog = new Blog(BlogID, authorId, postTime, title, content, backlinks, imageURL, status);
                    System.out.println("blog sau khi truyền từ form" + blog);
                    int n = dao.updateBlog(blog);

                    if (n > 0) {
                        response.sendRedirect("MarketingPostController?service=view&id=" + BlogID + "&message=Blog+updated+successfully!");
                    } else {
                        response.sendRedirect("MarketingPostController?service=view&id=" + BlogID + "&message=Failed+to+update+blog.");
                    }
                }
            }

            if (service.equals("addBlog")) {
                String submit = request.getParameter("submit");
                if (submit == null) {
                    Integer adminId = (Integer) request.getSession().getAttribute("userID");
                    ResultSet rsAuthor = dao.getData("SELECT id, name FROM Users WHERE id=" + adminId);
                    ResultSet rsCategory = dao.getData("SELECT id, categoryName FROM categoryblog");

                    request.setAttribute("rsAuthor", rsAuthor);
                    request.setAttribute("rsCategory", rsCategory);
                    request.getRequestDispatcher("/WEB-INF/views/AddBlog.jsp").forward(request, response);
                } else {
                    String title = request.getParameter("title");
                    String content = request.getParameter("content");
                    String authorID = request.getParameter("authorID");
                    String categoryID = request.getParameter("categoryID"); // Nhận category từ form
                    String postTime = request.getParameter("postTime");
                    String imageURL = request.getParameter("imageURL");
                    String backlinks = request.getParameter("backlinks");
                    String status = request.getParameter("status");
                    if (status == null || status.isEmpty()) {
                        status = "Active";
                    }
                    String isSlider = request.getParameter("isSlider");
                    String isDisabled = request.getParameter("isDisabled");

                    int authorId = Integer.parseInt(authorID);
                    int categoryId = Integer.parseInt(categoryID); // Convert categoryID sang int
                    boolean slider = (isSlider != null && isSlider.equals("on"));
                    boolean disabled = Boolean.parseBoolean(isDisabled);

                    Blog blog = new Blog(0, authorId, categoryId, postTime, title, content, backlinks, imageURL, status, slider, disabled, authorID);

                    int n = dao.addBlog(blog);

                    if (n > 0) {
                        response.sendRedirect("MarketingPostController?service=listAllBlogs&message=Blog+added+successfully!");
                    } else {
                        response.sendRedirect("MarketingPostController?service=listAllBlogs&message=Failed+to+add+blog.");
                    }
                }
            }

            if (service.equals("CatewithID")) {
                String categoryIdParam = request.getParameter("categoryID");
                System.out.println("id: " + categoryIdParam);

                String pageParam = request.getParameter("page");
                int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
                int pageSize = 5;

                int categoryID = 0;
                if (categoryIdParam != null && !categoryIdParam.trim().isEmpty()) {
                    try {
                        categoryID = Integer.parseInt(categoryIdParam);
                    } catch (NumberFormatException e) {
                        request.setAttribute("error", "Invalid Category ID format.");
                        request.getRequestDispatcher("/WEB-INF/views/Marketing-PostList.jsp").forward(request, response);
                        return;
                    }
                }

                List<Blog> blogs;
                int totalBlogs;
                if (categoryID == 0) {
                    blogs = dao.MaketingBlogs(page, pageSize);
                    totalBlogs = dao.getTotalBlogs();
                } else {
                    blogs = dao.MaketinggetPaginatedBlogsByCategory(categoryID, page, pageSize);
                    totalBlogs = dao.getTotalBlogsByCategory(categoryID);
                }

                int totalPages = (int) Math.ceil((double) totalBlogs / pageSize);

                if (blogs.isEmpty()) {
                    request.setAttribute("message", "No blogs found for this category.");
                }
                request.setAttribute("blogs", blogs);
                request.setAttribute("categoryID", categoryID);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                request.getRequestDispatcher("/WEB-INF/views/Marketing-PostList.jsp").forward(request, response);
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
