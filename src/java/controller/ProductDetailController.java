
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Brand;
import entity.Feedback;

import entity.Product;
import entity.ProductVariant;
import entity.User;
import entity.ProductVariant;

import helper.Authorize;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;
import model.DAOBrand;
import model.DAOFeedback;
import model.DAOProduct;

import model.DAOProductVariant;

/**
 *
 * @author HP
 */
@WebServlet(name = "ProductDetailController", urlPatterns = {"/ProductDetailController"})
public class ProductDetailController extends HttpServlet {

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
            out.println("<title>Servlet ProductDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductDetailController at " + request.getContextPath() + "</h1>");
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
        DAOProduct dao = new DAOProduct();
        DAOBrand daoBrand = new DAOBrand();
        DAOProductVariant daoProductVariants = new DAOProductVariant();
        DAOFeedback daoFeedback = new DAOFeedback(); // Thêm DAOFeedback

        int productID = Integer.parseInt(request.getParameter("id"));
        List<Feedback> feedbacks = daoFeedback.getLatestFeedbacksByProductId(productID);

        Product product = dao.getProductById(productID);
        if (product == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            return;
        }

        Vector<Brand> brandList = daoBrand.getAllBrands();
        Vector<ProductVariant> variants = daoProductVariants.getVariantsByProductId(productID);
        double minPrice = daoProductVariants.getMinPriceByProductId(productID);
        Vector<String> colors = daoProductVariants.getDistinctColorsByProductId1(productID);
        Vector<String> storages = daoProductVariants.getDistinctStorageByProductId1(productID);
        List<Product> relatedProducts = dao.getThreeProductsByBrand(product.getBrandID(), productID);
        request.setAttribute("relatedProducts", relatedProducts);

        String color = request.getParameter("color");
        String storage = request.getParameter("storage");

        if (color != null && storage != null) {
            ProductVariant productVariant = daoProductVariants.getProductVariantDetails1(productID, color, storage);
            if (productVariant != null) {
                response.setContentType("application/json");
                DecimalFormat df = new DecimalFormat("###,###,###.##");
                String formattedPrice = df.format(productVariant.getPrice());
                PrintWriter out = response.getWriter();
                out.print("{\"price\":\"" + formattedPrice + "\", \"stock\":\"" + productVariant.getStock() + "\"}");
                out.flush();
                return;
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }
        request.setAttribute("colors", colors);
        request.setAttribute("storages", storages);
        request.setAttribute("variants", variants);
        request.setAttribute("minPrice", minPrice);
        request.setAttribute("brands", brandList);
        request.setAttribute("product", product);
        request.setAttribute("feedbacks", feedbacks);
        request.getRequestDispatcher("WEB-INF/views/product-details.jsp").forward(request, response);
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
