/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Blog;
import entity.Product;
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
import model.DAOBlog;
import model.DAOProduct;

/**
 *
 * @author DUC MINH
 */
@WebServlet(name = "BlogDetailServlet", urlPatterns = {"/BlogDetailServlet"})
public class BlogDetailServlet extends HttpServlet {

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
            out.println("<title>Servlet BlogDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BlogDetailServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        if (!Authorize.isAccepted(user, "/BlogDetailServlet")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }

        String blogIdParam = request.getParameter("id");
        if (blogIdParam != null) {
            try {
                int blogId = Integer.parseInt(blogIdParam);
                DAOBlog daoBlog = new DAOBlog();
                Blog blog = daoBlog.getBlogById(blogId);

                if (blog != null) {
                    String authorName = daoBlog.getAuthorNameById(blog.getAuthorID());
                    request.setAttribute("blog", blog);
                    request.setAttribute("authorName", authorName);

                    // Lấy các bài blog mới nhất
                    List<Blog> newBlogs = daoBlog.getPaginatedBlogs(1, 5);  // Lấy 5 bài blog mới nhất
                    request.setAttribute("newBlogs", newBlogs);

                    // Lấy các sản phẩm mới nhất
                    DAOProduct daoProduct = new DAOProduct();
                    List<Product> newProducts = daoProduct.getProductsWithPagination(1, 5);  // Lấy 5 sản phẩm mới nhất
                    request.setAttribute("newProducts", newProducts);
                    
                } else {
                    request.setAttribute("errorMessage", "Bài viết không tồn tại.");
                }

                request.getRequestDispatcher("WEB-INF/views/blog-detail.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "ID không hợp lệ.");
                request.getRequestDispatcher("WEB-INF/views/blog-detail.jsp").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(BlogDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            request.setAttribute("errorMessage", "Không tìm thấy bài viết.");
            request.getRequestDispatcher("WEB-INF/views/blog-detail.jsp").forward(request, response);
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
        processRequest(request, response);
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
