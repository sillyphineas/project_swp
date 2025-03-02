/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import entity.Brand;
import entity.Product;
import entity.ProductVariant;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.Vector;
import model.DAOBrand;
import model.DAOProduct;
import model.DAOProductVariant;

/**
 *
 * @author Admin
 */
@WebServlet(name="MarketingProductDetails", urlPatterns={"/MarketingProductDetails"})
public class MarketingProductDetails extends HttpServlet {
   
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
            out.println("<title>Servlet MarketingProductDetails</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MarketingProductDetails at " + request.getContextPath () + "</h1>");
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

        DAOProductVariant daoProductVariants = new DAOProductVariant();

        Vector productList = new Vector();
        Product latestProduct = dao.getLatestProduct();
        int productID = Integer.parseInt(request.getParameter("id"));
        Product product = dao.getProductById(productID);
        Vector<Brand> brandList = daoBrand.getAllBrands();
        Vector<ProductVariant> variants = daoProductVariants.getVariantsByProductId(productID);
            double minPrice = daoProductVariants.getMinPriceByProductId(productID);
            
       
        if(product == null){
            response.sendError(HttpServletResponse.SC_NOT_FOUND,"Product not found");
            return;
        }
        String action = request.getParameter("action");
        if("editProduct".equals(action)){
            request.getRequestDispatcher("WEB-INF/views/edit_product.jsp").forward(request, response);
        }
        Vector<ProductVariant> productVariants = daoProductVariants.getVariantsByProductId(productID);
        request.setAttribute("variants", variants);
        request.setAttribute("minPrice", minPrice);
        request.setAttribute("brands", brandList);
        request.setAttribute("product", product);
        request.setAttribute("latestProduct", latestProduct);
        request.getRequestDispatcher("WEB-INF/views/marketingproduct-details.jsp").forward(request, response);
    
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
       DAOProduct dao = new DAOProduct();
        DAOBrand daoBrand = new DAOBrand();
        DAOProductVariant daovariant = new DAOProductVariant();
        String action = request.getParameter("action");

        if ("editProduct".equals(action)) {
            HttpSession session = request.getSession();
            try {
              
                
                int brandID = Integer.parseInt(request.getParameter("brandID"));
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                String imageURL = request.getParameter("imageURL");
                int feedbackCount = Integer.parseInt(request.getParameter("feedbackCount"));
                String chipset = request.getParameter("chipset");
                int ram = Integer.parseInt(request.getParameter("ram"));
                double screenSize = Double.parseDouble(request.getParameter("screenSize"));
                String screenType = request.getParameter("screenType");
                String resolution = request.getParameter("resolution");
               
                int batteryCapacity = Integer.parseInt(request.getParameter("batteryCapacity"));
                String cameraSpecs = request.getParameter("cameraSpecs");
                String os = request.getParameter("os");
                String simType = request.getParameter("simType");
                String connectivity = request.getParameter("connectivity");
                int createBy = Integer.parseInt(request.getParameter("createBy"));

                
Product newProduct = new Product(brandID, brandID, name, description, true, feedbackCount, simType, imageURL, chipset, ram, screenSize, screenType, resolution, batteryCapacity, cameraSpecs, os, simType, connectivity, new java.sql.Date(System.currentTimeMillis()), createBy);
int productID = dao.addProduct(newProduct);                
if (productID > 0) {
                   
                  String color = request.getParameter("color");
                  int storage = Integer.parseInt(request.getParameter("storage"));
                  Double price = Double.parseDouble(request.getParameter("price"));
                  int stock = Integer.parseInt(request.getParameter("stock"));
                  
                        ProductVariant variant = new ProductVariant(ram, productID, color, storage, price, stock);
                        daovariant.addProductVariant(variant);
                    

                    session.setAttribute("message", "Product added successfully!");
                } else {
                    session.setAttribute("message", "Failed to add product.");
                }
            } catch (Exception e) {
                session.setAttribute("message", "Error: " + e.getMessage());
                e.printStackTrace();
            }

            response.sendRedirect("MarketingProductController");
        }
    
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
