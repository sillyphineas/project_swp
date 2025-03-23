/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Feedback;
import helper.EmailUtil;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAOFeedback;
import model.DAOUser;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "MaketingFeedBackController", urlPatterns = {"/MaketingFeedBackController"})
public class MaketingFeedBackController extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        DAOFeedback dao = new DAOFeedback();
        DAOUser dAOUser = new DAOUser();
        Integer customerID = (Integer) session.getAttribute("userID");
        try (PrintWriter out = response.getWriter()) {
            String service = request.getParameter("service");
            if (service == null) {
                service = "listAllfeedBack";
            }
            if (service.equals("listAllfeedBack")) {
                try {
                    String pageStr = request.getParameter("page");
                    int page = 1;
                    if (pageStr != null && pageStr.matches("\\d+")) {
                        page = Integer.parseInt(pageStr);
                    }
                    int pageSize = 5;

                    List<Feedback> feedbacks = dao.getPaginatedFeedbacks(page, pageSize);
                    int totalFeedbacks = dao.getTotalFeedbacks();

                    int totalPages = (totalFeedbacks + pageSize - 1) / pageSize;

                    request.setAttribute("feedbacks", feedbacks);
                    request.setAttribute("currentPage", page);
                    request.setAttribute("totalPages", totalPages);

                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/FeedBack-List.jsp");
                    dispatcher.forward(request, response);

                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid page number");
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing feedbacks");
                }
            }
            if (service.equals("search")) {
                String query = request.getParameter("query");
                String pageStr = request.getParameter("page");
                int page = 1;
                int pageSize = 5;

                if (query == null || query.trim().isEmpty()) {
                    request.setAttribute("error", "Please enter a search term!!");
                    response.sendRedirect("MaketingFeedBackController?service=listAllfeedBack");
                    return;
                }

                try {
                    if (pageStr != null && !pageStr.isEmpty()) {
                        page = Integer.parseInt(pageStr.trim());
                    }
                } catch (NumberFormatException e) {
                    page = 1;
                }

                List<Feedback> feedbacks = dao.MaketingrsearchFeedbacks(query, page, pageSize);
                int totalFeedbacks = dao.MaketingcountTotalFeedbacksForSearch(query);
                int totalPages = (int) Math.ceil((double) totalFeedbacks / pageSize);

                if (feedbacks.isEmpty()) {
                    request.setAttribute("message", "No results found.");
                }

                request.setAttribute("feedbacks", feedbacks);
                request.setAttribute("query", query);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                request.getRequestDispatcher("/WEB-INF/views/FeedBack-List.jsp").forward(request, response);
            }
            if (service.equals("sort")) {
                String sortBy = request.getParameter("sortBy");
                String sortOrder = request.getParameter("sortOrder");
                System.out.println("sortBy: " + sortBy + " | sortOrder: " + sortOrder);
                String pageStr = request.getParameter("page");
                int page = 1;
                int pageSize = 5;

                if (pageStr != null && !pageStr.trim().isEmpty()) {
                    try {
                        page = Integer.parseInt(pageStr.trim());
                    } catch (NumberFormatException e) {
                        page = 1;
                    }
                }

                if (sortBy == null || sortBy.trim().isEmpty()) {
                    sortBy = "id";
                }
                if (sortOrder == null || sortOrder.trim().isEmpty()) {
                    sortOrder = "asc";
                }

                List<Feedback> feedbacks = dao.sortFeedbacks(sortBy, sortOrder, page, pageSize);
                int totalFeedbacks = dao.getTotalFeedbacks();
                int totalPages = (int) Math.ceil((double) totalFeedbacks / pageSize);

                if (feedbacks.isEmpty()) {
                    request.setAttribute("message", "No feedbacks found.");
                }

                request.setAttribute("feedbacks", feedbacks);
                request.setAttribute("sortBy", sortBy);
                request.setAttribute("sortOrder", sortOrder);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                request.getRequestDispatcher("/WEB-INF/views/FeedBack-List.jsp").forward(request, response);
            }
            if (service.equals("changeFeedbackStatus")) {
                String idStr = request.getParameter("id");
                Integer id = (idStr != null && !idStr.isEmpty()) ? Integer.parseInt(idStr) : null;
                String status = request.getParameter("status");
                System.out.println("id"+idStr+"status"+status);
                if (id != null && (status.equals("visible") || status.equals("hidden_due_to_violation"))) {
                    boolean success = dao.updateFeedbackStatus(id, status);

                    if (success) {
                        if (status.equals("hidden_due_to_violation")) { // Retrieve userId from feedbackId
                            String email = dAOUser.getUserEmailByUserID(dao.getUserIdByFeedbackId(id)); // Get user email by userId
                            System.out.println("email"+email);
                            if (email != null) {
                                EmailUtil.sendFeedbackHiddenMail(email, id);
                            }
                        }
                        response.setContentType("text/plain");
                        response.getWriter().write("Feedback status updated");
                    } else {
                        response.setContentType("text/plain");
                        response.getWriter().write("Failed to update feedback status");
                    }
                } else {
                    response.setContentType("text/plain");
                    response.getWriter().write("Invalid status or feedback ID");
                }
                
            }
            if (service.equals("FilterByStar")) {
                try {
                    String starStr = request.getParameter("star");
                    String pageStr = request.getParameter("page");
                    int star = 0;
                    int page = 1;
                    int pageSize = 10; // Số feedback trên mỗi trang
                    System.out.println("star"+star);
                    if (starStr != null && starStr.matches("\\d+")) {
                        star = Integer.parseInt(starStr);
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid star rating");
                        return;
                    }

                    if (pageStr != null && pageStr.matches("\\d+")) {
                        page = Integer.parseInt(pageStr);
                    }

                    List<Feedback> feedbacks = dao.MaketinggetPaginatedFeedbacksByStar(star, page, pageSize);
                    int totalFeedbacks = dao.MaketinggetTotalFeedbacksByStar(star);
                    int totalPages = (totalFeedbacks + pageSize - 1) / pageSize;

                    request.setAttribute("feedbacks", feedbacks);
                    request.setAttribute("currentPage", page);
                    request.setAttribute("totalPages", totalPages);
                    request.setAttribute("selectedStar", star);

                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/FeedBack-List.jsp");
                    dispatcher.forward(request, response);

                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid page number");
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing feedbacks");
                }
            }

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
        System.out.println("Servlet is running!");
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(MaketingFeedBackController.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(MaketingFeedBackController.class.getName()).log(Level.SEVERE, null, ex);
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
