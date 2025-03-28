
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Blog;
import entity.Brand;
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
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAOBlog;
import model.DAOBrand;
import model.DAOProduct;

/**
 *
 * @author HP
 */
@WebServlet(name = "HomePageController", urlPatterns = {"/HomePageController"})
public class HomePageController extends HttpServlet {

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
            out.println("<title>Servlet HomePageController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomePageController at " + request.getContextPath() + "</h1>");
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

        if (!Authorize.isAccepted(user, "/HomePageController")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }

        DAOProduct dao = new DAOProduct();
        DAOBrand daoBrand = new DAOBrand();
        DAOBlog daoBlog = new DAOBlog();

        // Số sản phẩm mỗi trang
        int itemsPerPage = 6;

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
        Vector<Product> latestProducts = dao.getNewProductsForHomePage(1, 3);
        // Lấy danh sách thương hiệu
        Vector<Brand> brandList = daoBrand.getAllBrands();

        java.util.List<Blog> latestBlogs = null;
        try {
            latestBlogs = daoBlog.getPaginatedSlider(1, 3);
        } catch (SQLException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.util.List<Blog> recentBlogs = null;
        try {
            recentBlogs = daoBlog.getPaginatedBlogs(1, 3);
        } catch (SQLException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        DecimalFormat df = new DecimalFormat("###,###,###.##");
        // Gửi thông tin vào JSP
        for (Product product : productList) {
            // Lấy giá trị minPrice cho sản phẩm và định dạng nó thành chuỗi
            double productMinPrice = dao.getMinPriceForProduct(product.getId());
            String formattedMinPrice = df.format(productMinPrice);

            // Truyền giá trị đã được định dạng vào request
            request.setAttribute("minPrice_" + product.getId(), formattedMinPrice);
        }

        request.setAttribute("products", productList);
        request.setAttribute("brands", brandList);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("latestBlogs", latestBlogs);
        request.setAttribute("recentBlogs", recentBlogs);
        request.setAttribute("latestProducts", latestProducts);
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/index_1.jsp");
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
