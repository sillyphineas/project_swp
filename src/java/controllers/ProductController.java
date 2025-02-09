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
import java.util.Vector;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.DAOBrand;
import models.DAOProduct;

/**
 * ProductController Servlet
 */
@WebServlet(name = "ProductController", urlPatterns = {"/ProductController"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Kiểm tra quyền truy cập
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (!Authorize.isAccepted(user, "/ProductController")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }

        DAOProduct dao = new DAOProduct();
        DAOBrand daoBrand = new DAOBrand();

        // Lấy tham số từ request
        String brandIDStr = request.getParameter("brandID");
        String searchQuery = request.getParameter("search");
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        String pageStr = request.getParameter("page");

        // Mặc định nếu không có giá trị
        int brandID = (brandIDStr != null && !brandIDStr.isEmpty()) ? Integer.parseInt(brandIDStr) : 0;
        double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : 0;
        double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : Double.MAX_VALUE;
        int currentPage = (pageStr != null && !pageStr.isEmpty()) ? Integer.parseInt(pageStr) : 1;
        int itemsPerPage = 9;

        // Lấy tổng số sản phẩm theo brand hoặc toàn bộ
        int totalProducts = (brandID > 0) ? dao.getTotalProductsByBrand(brandID) : dao.getTotalProducts();
        int totalPages = (int) Math.ceil((double) totalProducts / itemsPerPage);
        totalPages = Math.max(totalPages, 1);

        // Lọc sản phẩm dựa trên bộ lọc
        Vector<Product> productList;
        if (brandID > 0) {
            productList = dao.getProductsByBrand(brandID, currentPage, itemsPerPage);
        } else if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            productList = dao.searchProductsByName(searchQuery);
        } else if (minPriceStr != null && maxPriceStr != null) {
            productList = dao.getProductsByPriceRange(minPrice, maxPrice, currentPage, itemsPerPage);
        } else {
            productList = dao.getProductsSortedByDate(currentPage, itemsPerPage);
        }

        // Lấy danh sách thương hiệu
        Vector<Brand> brandList = daoBrand.getAllBrands();
        Product latestProduct = dao.getLatestProduct();

        // Đưa dữ liệu vào request
        request.setAttribute("productList", productList);
        request.setAttribute("brands", brandList);
        request.setAttribute("latestProduct", latestProduct);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("brandID", brandID);

        // Chuyển hướng tới trang shop.jsp
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/shop.jsp");
        rd.forward(request, response);
    }
}
