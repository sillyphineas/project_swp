/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Blog;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAOSlider;

/**
 *
 * @author DUC MINH
 */
@WebServlet(name = "SliderController", urlPatterns = {"/SliderController"})
public class SliderController extends HttpServlet {

    DAOSlider daoSlider = new DAOSlider();

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
            throws ServletException, IOException, SQLException {
        try {

            int page = 1;
            int pageSize = 5;

            if (request.getParameter("page") != null) {
                try {
                    page = Integer.parseInt(request.getParameter("page"));
                } catch (NumberFormatException e) {
                    page = 1;
                }
            }

            List<Blog> sliders = daoSlider.getPaginatedSlider(page, pageSize);
            request.setAttribute("sliders", sliders);
            int totalSliders = daoSlider.getTotalSliders();
            request.setAttribute("totalSliders", totalSliders);
            int totalPages = (int) Math.ceil((double) totalSliders / pageSize);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);
            request.getRequestDispatcher("WEB-INF/views/slider-list.jsp").forward(request, response);
        } catch (SQLException ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Error");
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
        String service = request.getParameter("service");

        if ("addSlider".equals(service)) {
            // Chuyển hướng tới trang add-slider.jsp
            request.getRequestDispatcher("WEB-INF/views/add-slider.jsp").forward(request, response);
        } else if ("viewDetail".equals(service)) {
            // Lấy ID slider từ request
            int sliderId = Integer.parseInt(request.getParameter("id"));
            Blog slider = null;
            try {
                slider = daoSlider.getSliderById(sliderId); // Thêm phương thức lấy slider theo ID
            } catch (SQLException ex) {
                Logger.getLogger(SliderController.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("slider", slider);
            request.getRequestDispatcher("WEB-INF/views/slider-detail.jsp").forward(request, response);
        } else if ("editSlider".equals(service)) {
            int sliderId = Integer.parseInt(request.getParameter("id"));
            Blog slider = null;
            try {
                slider = daoSlider.getSliderById(sliderId);
            } catch (SQLException ex) {
                Logger.getLogger(SliderController.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("slider", slider);
            request.getRequestDispatcher("WEB-INF/views/edit-slider.jsp").forward(request, response);
        } else {
            String status = request.getParameter("status");
            
            if (status != null) {
                List<Blog> filteredSliders = null;
                try {
                    filteredSliders = daoSlider.getSlidersByStatus(status);
                } catch (SQLException ex) {
                    Logger.getLogger(SliderController.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.setAttribute("sliders", filteredSliders);
                request.getRequestDispatcher("WEB-INF/views/slider-list.jsp").forward(request, response);
            } else {
                try {
                    processRequest(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(SliderController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
        HttpSession session = request.getSession(false);
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            // Lấy dữ liệu từ form
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String imageURL = request.getParameter("imageURL");
            String backlinks = request.getParameter("backlinks");
            String status = request.getParameter("status");
            Integer authorID = (Integer) session.getAttribute("userID");

            if (authorID == null) {
                request.setAttribute("message", "You must be logged in to add a slider.");
                request.setAttribute("messageType", "error");
                request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
                return;
            }
            String postTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            Blog blog = new Blog();
            blog.setTitle(title);
            blog.setContent(content);
            blog.setImageURL(imageURL);
            blog.setBacklinks(backlinks);
            blog.setStatus(status);
            blog.setPostTime(postTime);
            blog.setAuthorID(authorID);
            int result = daoSlider.addSlider(blog);
            if (result > 0) {
                request.setAttribute("message", "Slider added successfully.");
                request.setAttribute("messageType", "success");
            } else {
                request.setAttribute("message", "Failed to add slider.");
                request.setAttribute("messageType", "error");
            }
            response.sendRedirect(request.getContextPath() + "/SliderController");

        } else if ("search".equals(action)) {
            String query = request.getParameter("query");
            List<Blog> searchResults = null;
            try {
                searchResults = daoSlider.searchSliders(query);
            } catch (SQLException ex) {
                Logger.getLogger(SliderController.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("sliders", searchResults);
            request.getRequestDispatcher("WEB-INF/views/slider-list.jsp").forward(request, response);
        } else if ("toggleStatus".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            int isDisabled = "true".equals(request.getParameter("isDisabled")) ? 1 : 0;

            daoSlider.toggleSliderStatus(id, isDisabled);
            response.sendRedirect(request.getContextPath() + "/SliderController");
        } else if ("updateSlider".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String imageURL = request.getParameter("imageURL");
            String backlinks = request.getParameter("backlinks");
            String status = request.getParameter("status");
            Integer authorID = (Integer) session.getAttribute("userID");

            if (authorID == null) {
                request.setAttribute("message", "You must be logged in to add a slider.");
                request.setAttribute("messageType", "error");
                request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
                return;
            }
            String postTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            Blog slider = new Blog();
            slider.setId(id);
            slider.setTitle(title);
            slider.setContent(content);
            slider.setImageURL(imageURL);
            slider.setBacklinks(backlinks);
            slider.setStatus(status);
            slider.setPostTime(postTime);
            slider.setAuthorID(authorID);
            int result = daoSlider.UpdateSlider(slider);
            if (result > 0) {
                request.setAttribute("message", "Slider updated successfully.");
                request.setAttribute("messageType", "success");
            } else {
                request.setAttribute("message", "Failed to update slider.");
                request.setAttribute("messageType", "error");
            }
            response.sendRedirect(request.getContextPath() + "/SliderController");
        } else {
            try {
                processRequest(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(SliderController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
