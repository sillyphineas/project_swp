/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import entity.Voucher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import model.DAOVoucher;

/**
 *
 * @author Admin
 */
@WebServlet(name="MarketingVoucherCotroller", urlPatterns={"/MarketingVoucherCotroller"})
public class MarketingVoucherCotroller extends HttpServlet {
   
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
            out.println("<title>Servlet MarketingVoucherCotroller</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MarketingVoucherCotroller at " + request.getContextPath () + "</h1>");
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
           // DAO instance for interacting with the database
        DAOVoucher daoVoucher = new DAOVoucher();
        String service = request.getParameter("service");
        if (service == null) {
            service = "displayVouchers";
        }

        // Display Vouchers - Default service
        if (service.equals("displayVouchers")) {
            int page = 1;
            int itemsPerPage = 10;

            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }

            // Fetch the list of vouchers for pagination
            Vector<Voucher> voucherList = daoVoucher.getVouchers("SELECT * FROM Vouchers LIMIT " + ((page - 1) * itemsPerPage) + ", " + itemsPerPage);
            int totalVouchers = daoVoucher.getTotalVouchersCount();
            int totalPages = (int) Math.ceil((double) totalVouchers / itemsPerPage);

            // Set attributes for displaying in the JSP
            request.setAttribute("voucherList", voucherList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            // Forward to the voucher list JSP
            request.getRequestDispatcher("/WEB-INF/views/voucher-list.jsp").forward(request, response);
        }

        // Search Vouchers - Search by voucher code or description
        if (service.equals("searchVouchers")) {
            String keyword = request.getParameter("keyword");

            // Search the vouchers based on the keyword
            Vector<Voucher> voucherList = daoVoucher.searchVouchers(keyword);
            int totalVouchers = daoVoucher.getTotalSearchCount(keyword);
            int totalPages = (int) Math.ceil((double) totalVouchers / 10);

            // Set attributes for displaying in the JSP
            request.setAttribute("voucherList", voucherList);
            request.setAttribute("keyword", keyword);
            request.setAttribute("currentPage", 1);
            request.setAttribute("totalPages", totalPages);

            // Forward to the voucher list JSP
            request.getRequestDispatcher("/WEB-INF/views/voucher-list.jsp").forward(request, response);
        }

        // Add Voucher - Show the form for adding a new voucher
        if (service.equals("addVoucher")) {
            request.getRequestDispatcher("/WEB-INF/views/add-voucher.jsp").forward(request, response);
        }

        // Update Voucher - Show the form for updating an existing voucher
        if (service.equals("updateVoucher")) {
            int voucherId = Integer.parseInt(request.getParameter("voucherId"));
            Voucher voucher = daoVoucher.getVoucherByVoucherId(voucherId);
            request.setAttribute("voucher", voucher);
            request.getRequestDispatcher("/WEB-INF/views/update-voucher.jsp").forward(request, response);
        }

        // Delete Voucher - Delete a voucher
        if (service.equals("deleteVoucher")) {
            int voucherId = Integer.parseInt(request.getParameter("voucherId"));
            daoVoucher.deleteVoucher(voucherId);
            response.sendRedirect("MarketingVoucherCotroller?service=displayVouchers");
        }

    }

    // Handle POST requests (for adding and updating vouchers)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // DAO instance for interacting with the database
        DAOVoucher daoVoucher = new DAOVoucher();
        String service = request.getParameter("service");

        // Add Voucher - Add a new voucher
         if (service.equals("addVoucher")) {
            try {
                String voucherCode = request.getParameter("voucherCode");
                int voucherTypeID = Integer.parseInt(request.getParameter("voucherTypeID"));
                String description = request.getParameter("description");
                int point = Integer.parseInt(request.getParameter("point"));
                
                // Convert startDate and expiredDate using SimpleDateFormat
                Date startDate = convertStringToDate(request.getParameter("startDate"));
                Date expiredDate = convertStringToDate(request.getParameter("expiredDate"));
                
                int usageLimit = Integer.parseInt(request.getParameter("usageLimit"));
                boolean isDisabled = Boolean.parseBoolean(request.getParameter("isDisabled"));
                int value = Integer.parseInt(request.getParameter("value"));

                // Create a new voucher object
                Voucher newVoucher = new Voucher();
                newVoucher.setVoucherCode(voucherCode);
                newVoucher.setvoucherTypeID(voucherTypeID);
                newVoucher.setDescription(description);
                newVoucher.setPoint(point);
                newVoucher.setStartDate(startDate);
                newVoucher.setExpiredDate(expiredDate);
                newVoucher.setUsageLimit(usageLimit);
                newVoucher.setIsDisabled(isDisabled);
                newVoucher.setValue(value);

                // Insert the new voucher into the database
                daoVoucher.addVoucher(newVoucher);

                // Redirect to the voucher list page
                response.sendRedirect("MarketingVoucherCotroller?service=displayVouchers");
            } catch (ParseException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format.");
            }
        }
        // Update Voucher - Update an existing voucher
          if (service.equals("updateVoucher")) {
            try {
                int voucherId = Integer.parseInt(request.getParameter("voucherId"));
                String voucherCode = request.getParameter("voucherCode");
                int voucherTypeID = Integer.parseInt(request.getParameter("voucherTypeID"));
                String description = request.getParameter("description");
                int point = Integer.parseInt(request.getParameter("point"));
                
                // Convert startDate and expiredDate using SimpleDateFormat
                Date startDate = convertStringToDate(request.getParameter("startDate"));
                Date expiredDate = convertStringToDate(request.getParameter("expiredDate"));
                
                int usageLimit = Integer.parseInt(request.getParameter("usageLimit"));
                boolean isDisabled = Boolean.parseBoolean(request.getParameter("isDisabled"));
                int value = Integer.parseInt(request.getParameter("value"));

                // Create a voucher object with the updated values
                Voucher updatedVoucher = new Voucher();
                updatedVoucher.setVoucherID(voucherId);
                updatedVoucher.setVoucherCode(voucherCode);
                updatedVoucher.setvoucherTypeID(voucherTypeID);
                updatedVoucher.setDescription(description);
                updatedVoucher.setPoint(point);
                updatedVoucher.setStartDate(startDate);
                updatedVoucher.setExpiredDate(expiredDate);
                updatedVoucher.setUsageLimit(usageLimit);
                updatedVoucher.setIsDisabled(isDisabled);
                updatedVoucher.setValue(value);

                // Update the voucher in the database
                daoVoucher.updateVoucher(updatedVoucher);

                // Redirect to the voucher list page
                response.sendRedirect("MarketingVoucherCotroller?service=displayVouchers");
            } catch (ParseException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format.");
            }
          }
    }
    private Date convertStringToDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(dateStr);
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
