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
@WebServlet(name = "ProductController", urlPatterns = {"/ProductController"})
public class ProductController extends HttpServlet {

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
            out.println("<title>Servlet ProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductController at " + request.getContextPath() + "</h1>");
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
        if (!Authorize.isAccepted(user, "/ProductController")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }
        
        DAOProduct dao = new DAOProduct();
        DAOBrand daoBrand = new DAOBrand();
        String brandIDStr = request.getParameter("brandID");
        String searchQuery = request.getParameter("search");
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");

        double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : 0;
        double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : 2000;

        int itemsPerPage = 7;
        int currentPage = 1;
        int brandID = -1;
        if (brandIDStr != null && !brandIDStr.isEmpty()) {
            try {
                brandID = Integer.parseInt(brandIDStr);
            } catch (NumberFormatException e) {
                brandID = -1;
            }
        }
        if (brandID == -1) {
            brandID = 0;
        }

        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                currentPage = Integer.parseInt(pageStr);
                if (currentPage < 1) {
                    currentPage = 1;
                }
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        Vector<Product> productList = new Vector<>();

//    
//        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
//            productList = dao.searchProductsByName(searchQuery);
//        } else {
//            productList = dao.getProductsSortedByDate(currentPage, itemsPerPage);
//        }
        Vector<Product> productListBrand = new Vector<>();
        if (brandID != -1) {
            productListBrand = dao.getProductsByBrand(brandID, currentPage, itemsPerPage);
        }
        if (minPriceStr != null && maxPriceStr != null) {
            productList = dao.getProductsByPriceRange(minPrice, maxPrice, currentPage, itemsPerPage);
        } else if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            productList = dao.searchProductsByName(searchQuery);
        } else {
            productList = dao.getProductsSortedByDate(currentPage, itemsPerPage);
        }

        Vector<Brand> brandList = daoBrand.getAllBrands();

        int totalProducts = dao.getTotalProducts();
        int totalPages = (int) Math.ceil((double) totalProducts / itemsPerPage);
        totalPages = Math.max(totalPages, 1); // Đảm bảo totalPages >= 1
        Product latestProduct = dao.getLatestProduct();
        request.setAttribute("latestProduct", latestProduct);
        if (productListBrand != null) {
            request.setAttribute("productList", productListBrand);
        } else {
            request.setAttribute("productList", productList);
        }
        if (brandID == 0) {
            request.setAttribute("productList", productList);
        }

        request.setAttribute("currentPage", currentPage);
        request.setAttribute("brands", brandList);
        request.setAttribute("totalPages", totalPages);

        Vector<Product> latestProducts = dao.getProductsWithPaginationAndSorting(1, 3);
        request.setAttribute("latestProducts", latestProducts);

        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/shop.jsp");
        rd.forward(request, response);

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
