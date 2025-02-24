package controller;

import entity.Brand;
import entity.Product;
import entity.User;
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
import model.DAOBrand;
import model.DAOProduct;

/**
 * ProductController Servlet
 */
@WebServlet(name = "ProductController", urlPatterns = {"/ProductController"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (!Authorize.isAccepted(user, "/ProductController")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }

            DAOProduct dao = new DAOProduct();
        DAOBrand daoBrand = new DAOBrand();

        // Lấy danh sách filter từ database
        Vector<Brand> brandList = daoBrand.getAllBrands();
        Vector<String> osList = dao.getDistinctOS();
        Vector<String> connectivityList = dao.getDistinctConnectivity();
        Vector<Integer> ramList = dao.getDistinctRAM();
        Vector<String> screenTypeList = dao.getDistinctScreenType();
        Vector<Integer> batteryCapacityList = dao.getDistinctBatteryCapacity();
        Vector<Double> screenSizeList = dao.getDistinctScreenSize();

        // Lấy tham số từ request

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

        // Chuyển đổi giá trị
        int brandID = (brandIDStr != null && !brandIDStr.isEmpty()) ? Integer.parseInt(brandIDStr) : 0;
        double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : 0.0;
        double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : Double.MAX_VALUE;
        int currentPage = (pageStr != null && !pageStr.isEmpty()) ? Integer.parseInt(pageStr) : 1;
        int ram = (ramStr != null && !ramStr.isEmpty()) ? Integer.parseInt(ramStr) : 0;
        int batteryCapacity = (batteryCapacityStr != null && !batteryCapacityStr.isEmpty()) ? Integer.parseInt(batteryCapacityStr) : 0;
        double screenSize = (screenSizeStr != null && !screenSizeStr.isEmpty()) ? Double.parseDouble(screenSizeStr) : 0.0;
        int itemsPerPage = 6;

        // Lấy tổng số sản phẩm
        int totalProducts = dao.getTotalProductsByFilters(brandID, searchQuery, minPrice, maxPrice, os, screenSize, batteryCapacity, connectivity, ram, screenType);
        int totalPages = Math.max((int) Math.ceil((double) totalProducts / itemsPerPage), 1);

        // Lấy danh sách sản phẩm theo bộ lọc
        Vector<Product> productList = dao.getProductsByFilters(brandID, searchQuery, minPrice, maxPrice, os, screenSize, batteryCapacity, connectivity, ram, screenType, currentPage, itemsPerPage);
        Product latestProduct = dao.getLatestProduct();

        // Gán giá trị tối thiểu cho từng sản phẩm
        for (Product product : productList) {
            double productMinPrice = dao.getMinPriceForProduct(product.getId());
            request.setAttribute("minPrice_" + product.getId(), productMinPrice);
        }

        // Truyền dữ liệu vào JSP
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


        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/shop.jsp");
        rd.forward(request, response);
    }
}
