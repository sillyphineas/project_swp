/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Brand;
import entity.Product;
import entity.ProductVariant;
import entity.User;
import helper.Authorize;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import model.DAOBrand;
import model.DAOProduct;
import model.DAOProductVariant;

@WebServlet(name = "MarketingProductDetails", urlPatterns = {"/MarketingProductDetails"})
public class MarketingProductDetails extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Authorize
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        if (!Authorize.isAccepted(user, "/MarketingProductDetails")) {
            request.getRequestDispatcher("WEB-INF/views/404.jsp").forward(request, response);
            return;
        }
        response.setContentType("text/html;charset=UTF-8");
        DAOProduct dao = new DAOProduct();
        DAOBrand daoBrand = new DAOBrand();
        DAOProductVariant daoProductVariants = new DAOProductVariant();

        String action = request.getParameter("action");
        if (action == null) {
            action = "listall";
        }

        if ("listall".equals(action)) {
            int productID = Integer.parseInt(request.getParameter("id"));
            Product product = dao.getProductById(productID);
            Vector<Brand> brandList = daoBrand.getAllBrands();
            Vector<ProductVariant> variants = daoProductVariants.getVariantsByProductId(productID);
            double minPrice = daoProductVariants.getMinPriceByProductId(productID);
            Vector<String> distinctColors = daoProductVariants.getDistinctColorsByProductId(productID);
            request.setAttribute("distinctColors", distinctColors);
            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                return;
            }
            request.setAttribute("variants", variants);
            request.setAttribute("minPrice", minPrice);
            request.setAttribute("brands", brandList);
            request.setAttribute("product", product);
            Vector<String> distinctStorages = daoProductVariants.getDistinctStorageByProductId(productID);
            request.setAttribute(" distinctStorages",distinctStorages);

            request.getRequestDispatcher("WEB-INF/views/marketingproduct-details.jsp").forward(request, response);
        }
        if ("editVariant".equals(action)) {
            int productID = Integer.parseInt(request.getParameter("id"));
            Product product = dao.getProductById(productID);
            Vector<Brand> brandList = daoBrand.getAllBrands();
            Vector<ProductVariant> variants = daoProductVariants.getVariantsByProductId(productID);
            double minPrice = daoProductVariants.getMinPriceByProductId(productID);

            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                return;
            }

            request.setAttribute("variants", variants);
            request.setAttribute("minPrice", minPrice);
            request.setAttribute("brands", brandList);
            request.setAttribute("product", product);
            request.getRequestDispatcher("WEB-INF/views/edit_product.jsp").forward(request, response);
        }
        if ("editProduct".equals(action)) {
            int productID = Integer.parseInt(request.getParameter("id"));
            Product product = dao.getProductById(productID);
            Vector<Brand> brandList = daoBrand.getAllBrands();
            Vector<ProductVariant> variants = daoProductVariants.getVariantsByProductId(productID);
            double minPrice = daoProductVariants.getMinPriceByProductId(productID);

            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                return;
            }

            request.setAttribute("variants", variants);
            request.setAttribute("minPrice", minPrice);
            request.setAttribute("brands", brandList);
            request.setAttribute("product", product);
            request.getRequestDispatcher("WEB-INF/views/edit_product.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAOProduct daoProduct = new DAOProduct();
        DAOProductVariant daoVariant = new DAOProductVariant();

        String action = request.getParameter("action");

        if ("editProduct".equals(action)) {
            
            int productId = Integer.parseInt(request.getParameter("id"));
            System.out.println("productid" +productId);
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            int brandID = Integer.parseInt(request.getParameter("brandID"));
            boolean isDisabled = request.getParameter("isDisabled") != null;
            String status = request.getParameter("status");
            String imageURL = request.getParameter("imageURL");
            String chipset = request.getParameter("chipset");
            int ram = Integer.parseInt(request.getParameter("ram"));
            double screenSize = Double.parseDouble(request.getParameter("screenSize"));
            String screenType = request.getParameter("screenType");
            String resolution = request.getParameter("resolution");
            int batteryCapacity = Integer.parseInt(request.getParameter("batteryCapacity"));
            String os = request.getParameter("os");
            String connectivity = request.getParameter("connectivity");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date createAtUtil = new java.util.Date();
            String createAtString = request.getParameter("createAt");
            if (createAtString != null && !createAtString.isEmpty()) {
                try {
                    createAtUtil = sdf.parse(createAtString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // Chuyển đổi java.util.Date sang java.sql.Date
            java.sql.Date createAt = new java.sql.Date(createAtUtil.getTime());

            Product updatedProduct = new Product(
                    productId,
                    brandID,
                    name,
                    description,
                    isDisabled,
                    0,
                    status,
                    imageURL,
                    chipset,
                    ram,
                    screenSize,
                    screenType,
                    resolution,
                    batteryCapacity,
                    "",
                    os,
                    "",
                    connectivity,
                    createAt, // Sử dụng java.sql.Date ở đây
                    2
            );
            daoProduct.UpdateProduct(updatedProduct);
            response.sendRedirect("MarketingProductDetails?id=" + productId);
        }

        if ("editVariant".equals(action)) {
            int variantId = Integer.parseInt(request.getParameter("id"));
            int productID = Integer.parseInt(request.getParameter("productID"));
            String color = request.getParameter("color");
            int storage = Integer.parseInt(request.getParameter("storage"));
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));

            ProductVariant updatedVariant = new ProductVariant(variantId, productID, color, storage, price, stock);
            daoVariant.updateProductVariant(updatedVariant);
            response.sendRedirect("MarketingProductDetails?id=" + productID);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for managing product details and variants";
    }
}
