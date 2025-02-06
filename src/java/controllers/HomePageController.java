/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import entities.Brand;
import entities.Product;
import entities.User;
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
import java.util.Vector;
import models.DAOBrand;
import models.DAOProduct;

/**
 *
 * @author HP
 */
@WebServlet(name="HomePageController", urlPatterns={"/HomePageController"})
public class HomePageController extends HttpServlet {
   
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
            out.println("<title>Servlet HomePageController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomePageController at " + request.getContextPath () + "</h1>");
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
        DAOProduct dao = new DAOProduct();
        DAOBrand daoBrand = new DAOBrand();

        // Số sản phẩm mỗi trang
        int itemsPerPage = 9;

        // Lấy số trang từ request, mặc định là trang 1
        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                page = 1;  // Nếu không phải số hợp lệ thì đặt trang mặc định là 1
            }
        }

        // Lấy danh sách sản phẩm theo phân trang
        Vector<Product> productList = dao.getProductsWithPagination(page, itemsPerPage);
        // Tổng số sản phẩm
        int totalItems = dao.getTotalProducts();
        // Tổng số trang
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        // Lấy danh sách thương hiệu
        Vector<Brand> brandList = daoBrand.getAllBrands();

        // Gửi thông tin vào JSP
        request.setAttribute("products", productList);
        request.setAttribute("brands", brandList);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);

        //Authorize and forward
        HttpSession session = request.getSession(false);
        User user = null;

        if (session != null) {
            user = (User) session.getAttribute("user");
        }

        if (Authorize.isAccepted(user, "/HomePageController")) {
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/index_1.jsp");
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/404.jsp");
            rd.forward(request, response);
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
