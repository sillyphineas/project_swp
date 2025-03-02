/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Vector;
import model.DAOUser;

/**
 *
 * @author DUC MINH
 */
@WebServlet(name = "EditCustomerController", urlPatterns = {"/EditCustomerController"})
public class EditCustomerController extends HttpServlet {

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
            out.println("<title>Servlet EditcustomerController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditcustomerController at " + request.getContextPath() + "</h1>");
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
        int customerId = Integer.parseInt(request.getParameter("id"));

        DAOUser dao = new DAOUser();
        User customer = dao.getUserById(customerId);

        // Đưa thông tin customer vào request để hiển thị trong form
        request.setAttribute("customer", customer);
        request.getRequestDispatcher("/WEB-INF/views/update-customer.jsp").forward(request, response);
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
        int id = Integer.parseInt(request.getParameter("id"));
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        boolean isDisabled = Boolean.parseBoolean(request.getParameter("status"));

        // Tạo đối tượng User và cập nhật thông tin
        DAOUser dao = new DAOUser();
        User customer = dao.getUserById(id);

        if (customer != null) {
            customer.setEmail(email);
            customer.setPhoneNumber(phoneNumber);
            customer.setDisabled(isDisabled);

            // Cập nhật thông tin người dùng
            int result = dao.updateCustomer(customer);

            // Kiểm tra kết quả và thông báo
            if (result > 0) {
                String filterStatus = request.getParameter("status");  // "true" or "false"
                String searchQuery = request.getParameter("search");
                String pageParam = request.getParameter("page");

                int page = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
                int pageSize = 10;

                // Lấy tổng số khách hàng và danh sách khách hàng
                int totalCustomers = dao.getTotalCustomers(filterStatus, searchQuery);
                int totalPages = (int) Math.ceil((double) totalCustomers / pageSize);
                Vector<User> customers = dao.getCustomers(page, pageSize, filterStatus, searchQuery);

                // Set các attribute cho JSP
                request.setAttribute("customers", customers);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("currentPage", page);
                request.setAttribute("mess", "Update customer successfully.");
                request.getRequestDispatcher("/WEB-INF/views/customer-list.jsp").forward(request, response);
            } else {
                request.setAttribute("mess", "Failed to update customer.");
                request.getRequestDispatcher("/WEB-INF/views/update-customer.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("/WEB-INF/views/404.jsp");
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

}
