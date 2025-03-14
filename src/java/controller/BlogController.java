/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Blog;
import entity.Brand;
import entity.Category;
import entity.User;
import helper.Authorize;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import java.util.List;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAOBlog;
import model.DAOBrand;
import model.DAOCart;
import model.DAOCategory;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "BlogController", urlPatterns = {"/BlogURL"})
public class BlogController extends HttpServlet {

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
        if (!Authorize.isAccepted(user, "/BlogURL")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        DAOBlog dao = new DAOBlog();
        DAOBrand daoBrand = new DAOBrand();
        DAOCategory daoCate = new DAOCategory();
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String service = request.getParameter("service");
            Vector<Brand> brandList = daoBrand.getAllBrands();
            request.setAttribute("brands", brandList);
            List<Category> categories = daoCate.getAllCategories();
            request.setAttribute("categories", categories);

            if (service == null) {
                service = "listAllBlogs";
            }
            if (service.equals("listAllBlogs")) {
                String pageParam = request.getParameter("page");
                int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
                int pageSize = 3;
                List<Blog> blogs = dao.getPaginatedBlogs(page, pageSize);
                int totalBlogs = dao.getTotalBlogs();
                int totalPages = (int) Math.ceil((double) totalBlogs / pageSize);

                request.setAttribute("blogs", blogs);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("currentPage", page);
                request.getRequestDispatcher("/WEB-INF/views/blog.jsp").forward(request, response);
            }
            if (service.equals("search")) {
                String query = request.getParameter("query");
                String pageStr = request.getParameter("page");
                int page = 1;
                int pageSize = 10;

                if (query == null || query.trim().isEmpty()) {
                    response.sendRedirect("BlogURL?service=listAllBlogs");
                    return;
                }

                query = query.toLowerCase();

                try {
                    if (pageStr != null && !pageStr.isEmpty()) {
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

                request.getRequestDispatcher("/WEB-INF/views/blog.jsp").forward(request, response);
            }

            if (service.equals("CatewithID")) {
                String categoryIdParam = request.getParameter("categoryID");
                System.out.println("id" + categoryIdParam);
                String pageParam = request.getParameter("page");
                int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
                int pageSize = 3;

                if (categoryIdParam == null || categoryIdParam.trim().isEmpty()) {
                    request.setAttribute("error", "Category ID is required!");
                    request.getRequestDispatcher("/WEB-INF/views/blog.jsp").forward(request, response);
                    return;
                }

                int categoryID = 0;
                try {
                    categoryID = Integer.parseInt(categoryIdParam);
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Invalid Category ID format.");
                    request.getRequestDispatcher("/WEB-INF/views/blog.jsp").forward(request, response);
                    return;
                }

                List<Blog> blogs = dao.getPaginatedBlogsByCategory(categoryID, page, pageSize);
                int totalBlogs = dao.getTotalBlogsByCategory(categoryID);
                int totalPages = (int) Math.ceil((double) totalBlogs / pageSize);

                if (blogs.isEmpty()) {
                    request.setAttribute("message", "No blogs found for this category.");
                }
                request.setAttribute("blogs", blogs);

                request.setAttribute("categoryID", categoryID);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                request.getRequestDispatcher("/WEB-INF/views/blog.jsp").forward(request, response);
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
            Logger.getLogger(BlogController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(BlogController.class.getName()).log(Level.SEVERE, null, ex);
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
