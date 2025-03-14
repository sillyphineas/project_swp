/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Vector;
import model.DAOColor;
import model.DAOStorage;
import model.DAOBrand;
import model.DAOProduct;
import model.DAOProductVariant;
import entity.Brand;
import entity.Product;
import entity.ProductVariant;
import entity.User;
import entity.Color;
import entity.Storage;
import helper.Authorize;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AddProductController", urlPatterns = {"/AddProductController"})
public class AddProductController extends HttpServlet {

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
            out.println("<title>Servlet AddProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddProductController at " + request.getContextPath() + "</h1>");
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
        if (!Authorize.isAccepted(user, "/AddProductController")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }

        DAOBrand daoBrand = new DAOBrand();
        DAOColor daocolor = new DAOColor();
        DAOStorage daostorage = new DAOStorage();
        Vector<Brand> brandList = daoBrand.getAllBrands();
        Vector<Color> colorlist = daocolor.getAllColors();
        Vector<Storage> storagelist = daostorage.getAllStorages();
        String action = request.getParameter("action");
        DAOProduct daoProduct = new DAOProduct();
        
        Vector<Product> productList = daoProduct.getProducts("SELECT * FROM Products WHERE isDisabled = 0");

        request.setAttribute("products", productList);

        if (action == null) {
            action = "display";
        }
        if (action.equals("addProduct")) {
            request.setAttribute("brands", brandList);
            request.getRequestDispatcher("WEB-INF/views/add_product.jsp").forward(request, response);
        }
        if (action.equals("addProductVariant")) {
            request.setAttribute("brands", brandList);
            request.setAttribute("colorlist", colorlist);
            request.setAttribute("storagelist", storagelist);
            request.getRequestDispatcher("WEB-INF/views/add_productvariant.jsp").forward(request, response);

        }
        if (action.equals("addColor")) {
            request.getRequestDispatcher("WEB-INF/views/add_color.jsp").forward(request, response);
        }
        if (action.equals("addStorage")) {
            request.getRequestDispatcher("WEB-INF/views/add_storage.jsp").forward(request, response);
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
        DAOProduct daoProduct = new DAOProduct();
        DAOProductVariant daoVariant = new DAOProductVariant();
        DAOColor daocolor = new DAOColor();
        DAOStorage daostorage = new DAOStorage();

        String action = request.getParameter("action");

        if (action != null && "addProduct".equals(action)) {
            int productId = parseIntSafe(request.getParameter("id"), 0);
            int brandID = parseIntSafe(request.getParameter("brandID"), 0);
            int ram = parseIntSafe(request.getParameter("ram"), 0);
            int batteryCapacity = parseIntSafe(request.getParameter("batteryCapacity"), 0);

            String name = request.getParameter("name");
            String description = request.getParameter("description");

            boolean isDisabled = request.getParameter("isDisabled") != null;
            String status = request.getParameter("status");
            String imageURL = request.getParameter("imageURL");
            String chipset = request.getParameter("chipset");

            double screenSize = Double.parseDouble(request.getParameter("screenSize"));
            String screenType = request.getParameter("screenType");
            String resolution = request.getParameter("resolution");

            String os = request.getParameter("os");
            String connectivity = request.getParameter("connectivity");
            int feedbackCount = parseIntSafe(request.getParameter("feedbackCount"), 0);
            String cameraSpecs = request.getParameter("cameraSpecs");
            String simType = request.getParameter("simType");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date createAt = new Date();
            String createAtString = request.getParameter("createAt");
            if (createAtString != null && !createAtString.isEmpty()) {
                try {
                    createAt = sdf.parse(createAtString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Product addProduct = new Product(productId, brandID, name, description, isDisabled, feedbackCount, status, imageURL, chipset, ram, screenSize, screenType, resolution, batteryCapacity, cameraSpecs, os, simType, connectivity, createAt, 2);
            daoProduct.addProduct(addProduct);
            response.sendRedirect("MarketingProductController");
        }

        if (action != null && "addProductVariant".equals(action)) {
            int variantId = parseIntSafe(request.getParameter("variantId"), 0);
            int productID = parseIntSafe(request.getParameter("productID"), 0);
            int color_id = parseIntSafe(request.getParameter("color_id"), 0);
            String priceStr = request.getParameter("price");
            double price = parseDoubleSafe(request.getParameter("price"), 0.0);
            int storage_id = parseIntSafe(request.getParameter("storage_id"), 0);
            int stock = parseIntSafe(request.getParameter("stock"), 0);
            String status = request.getParameter("status");
            ProductVariant addVariant = new ProductVariant(stock, productID, color_id, storage_id, price, stock, status);
            daoVariant.addProductVariant(addVariant);
            response.sendRedirect("MarketingProductController");
        }
        if (action != null && "addColor".equals(action)) {
            int colorid = parseIntSafe(request.getParameter("colorid"), 0);
            String colorName = request.getParameter("colorName");
            String status = request.getParameter("status");
            Color color = new Color(colorid, colorName, status);
             daocolor.addColor(color);
            response.sendRedirect("MarketingProductController");
        }
        if (action != null && "addStorage".equals(action)) {
            int storageid = parseIntSafe(request.getParameter("storage_id"), 0);
            String capacity = request.getParameter("capacity");
            String status = request.getParameter("status");
            Storage storage = new Storage(storageid, capacity, status);
            daostorage.addStorage(storage);
            response.sendRedirect("MarketingProductController");
           
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

    private int parseIntSafe(String param, int defaultValue) {
        try {
            return (param != null && !param.isEmpty()) ? Integer.parseInt(param) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private double parseDoubleSafe(String param, double defaultValue) {
        try {
            return (param != null && !param.isEmpty()) ? Double.parseDouble(param) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
