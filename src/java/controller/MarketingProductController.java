/*
 * Click nbfs: nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs: nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Brand;
import entity.Product;
import entity.ProductVariant;
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
import java.util.Date;
import java.util.Vector;
import model.DAOBrand;
import model.DAOProduct;
import model.DAOProductVariant;

/**
 *
 * @author Admin
 */
@WebServlet(name = "MarketingProductController", urlPatterns = {"/MarketingProductController"})
public class MarketingProductController extends HttpServlet {

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
            out.println("<title>Servlet MarketingProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MarketingProductController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        if (!Authorize.isAccepted(user, "/MarketingProductController")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }
        DAOProduct dao = new DAOProduct();
        DAOBrand daoBrand = new DAOBrand();

        Vector<Brand> brandList = daoBrand.getAllBrands();
        Vector<String> osList = dao.getDistinctOS();
        Vector<String> connectivityList = dao.getDistinctConnectivity();
        Vector<Integer> ramList = dao.getDistinctRAM();
        Vector<String> screenTypeList = dao.getDistinctScreenType();
        Vector<Integer> batteryCapacityList = dao.getDistinctBatteryCapacity();
        Vector<Double> screenSizeList = dao.getDistinctScreenSize();

        String brandIDStr = request.getParameter("brandID");
        String searchQuery = request.getParameter("search");
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        String pageStr = request.getParameter("page");
        String os = request.getParameter("os");
        String connectivity = request.getParameter("connectivity");
        String ramStr = request.getParameter("ram");
        String screenType = request.getParameter("screenType");
        String batteryCapacityStr = request.getParameter("batteryCapacity");
        String screenSizeStr = request.getParameter("screenSize");

        int brandID = (brandIDStr != null && !brandIDStr.isEmpty()) ? Integer.parseInt(brandIDStr) : 0;
        double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : 0.0;
        double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : Double.MAX_VALUE;
        int currentPage = (pageStr != null && !pageStr.isEmpty()) ? Integer.parseInt(pageStr) : 1;
        int ram = (ramStr != null && !ramStr.isEmpty()) ? Integer.parseInt(ramStr) : 0;
        int batteryCapacity = (batteryCapacityStr != null && !batteryCapacityStr.isEmpty()) ? Integer.parseInt(batteryCapacityStr) : 0;
        double screenSize = (screenSizeStr != null && !screenSizeStr.isEmpty()) ? Double.parseDouble(screenSizeStr) : 0.0;
        int itemsPerPage = 6;

        int totalProducts = dao.getTotalProductsByFiltersbyAdmin(brandID, searchQuery, minPrice, maxPrice, os, screenSize, batteryCapacity, connectivity, ram, screenType);
        int totalPages = Math.max((int) Math.ceil((double) totalProducts / itemsPerPage), 1);

        Vector<Product> productList = dao.getProductsByFilterbyAdmin(brandID, searchQuery, minPrice, maxPrice, os, screenSize, batteryCapacity, connectivity, ram, screenType, currentPage, itemsPerPage);
        Product latestProduct = dao.getLatestProduct();

        for (Product product : productList) {
            double productMinPrice = dao.getMinPriceForProduct(product.getId());
            request.setAttribute("minPrice_" + product.getId(), productMinPrice);
        }
        session = request.getSession(false);
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("id"));
            int result = dao.delete(productId);

            if (result > 0) {
                session.setAttribute("deleteMessage", "Sản phẩm đã được xóa thành công.");

            } else {
                session.setAttribute("deleteMessage", "Không thể xóa sản phẩm.");
            }
            response.sendRedirect("MarketingProductController");
        } else if ("hide".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("id"));
            int result = dao.hideProduct(productId);

            if (result > 0) {
                session.setAttribute("productStatusMessage", "Sản phẩm đã được ẩn.");
            } else {
                session.setAttribute("productStatusMessage", "Không thể ẩn sản phẩm.");
            }
            response.sendRedirect("MarketingProductController");
        } else if ("show".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("id"));
            int result = dao.showProduct(productId);

            if (result > 0) {
                session.setAttribute("productStatusMessage", "Sản phẩm đã được hiển thị.");
            } else {
                session.setAttribute("productStatusMessage", "Không thể hiển thị sản phẩm.");
            }
            response.sendRedirect("MarketingProductController");
        } else {

            request.setAttribute("productList", productList);
            request.setAttribute("brands", brandList);
            request.setAttribute("osList", osList);
            request.setAttribute("connectivityList", connectivityList);
            request.setAttribute("ramList", ramList);
            request.setAttribute("screenTypeList", screenTypeList);
            request.setAttribute("batteryCapacityList", batteryCapacityList);
            request.setAttribute("screenSizeList", screenSizeList);

            request.setAttribute("latestProduct", latestProduct);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("brandID", brandID);
            request.setAttribute("os", os);
            request.setAttribute("connectivity", connectivity);
            request.setAttribute("ram", ram);
            request.setAttribute("screenType", screenType);
            request.setAttribute("batteryCapacity", batteryCapacity);
            request.setAttribute("screenSize", screenSize);
            request.setAttribute("minPrice", minPrice);
            request.setAttribute("maxPrice", maxPrice);

            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/marketingshop.jsp");
            rd.forward(request, response);

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
    }

}
