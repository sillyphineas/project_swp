/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import entities.Blog;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.DAOBlog;

/**
 *
 * @author DUC MINH
 */
@WebServlet(name="BlogDetailServlet", urlPatterns={"/BlogDetailServlet"})
public class BlogDetailServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<h1>Servlet BlogDetailServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Lấy ID bài viết từ tham số URL
    String blogIdParam = request.getParameter("id");
    if (blogIdParam != null) {
        try {
            int blogId = Integer.parseInt(blogIdParam);
            DAOBlog daoBlog = new DAOBlog();
            
            // Lấy thông tin bài viết từ cơ sở dữ liệu
            Blog blog = daoBlog.getBlogById(blogId);
            
            if (blog != null) {
                // Lấy tên tác giả từ authorID
                String authorName = daoBlog.getAuthorNameById(blog.getAuthorID());
                
                // Chuyển thông tin bài viết và tên tác giả đến trang JSP
                
                request.setAttribute("blog", blog);
                request.setAttribute("authorName", authorName);
            } else {
                // Nếu không tìm thấy bài viết, tạo thông báo lỗi
                request.setAttribute("errorMessage", "Bài viết không tồn tại.");
            }
            
            // Chuyển tiếp đến trang JSP hiển thị chi tiết bài viết
            request.getRequestDispatcher("WEB-INF/views/blog-detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "ID không hợp lệ.");
            request.getRequestDispatcher("WEB-INF/views/blog-detail.jsp").forward(request, response);
        }
    } else {
        request.setAttribute("errorMessage", "Không tìm thấy bài viết.");
        request.getRequestDispatcher("WEB-INF/views/blog-detail.jsp").forward(request, response);
    }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}