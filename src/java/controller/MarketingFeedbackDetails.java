/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import entity.Feedback;
import helper.EmailUtil;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAOFeedback;
import model.DAOUser;

/**
 *
 * @author Admin
 */
@WebServlet(name="MarketingFeedbackDetails", urlPatterns={"/MarketingFeedbackDetails"})
public class MarketingFeedbackDetails extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAOFeedback dao = new DAOFeedback();
        String service = request.getParameter("service");
        if (service == null) {
            service = "showDetail";
        }

        if (service.equals("showDetail")) {
            try {
                String feedbackIdStr = request.getParameter("id");
                if (feedbackIdStr == null || feedbackIdStr.trim().isEmpty()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Feedback ID is required");
                    return;
                }

                int feedbackId = Integer.parseInt(feedbackIdStr);
                Feedback feedback = dao.getFeedbackDetailById(feedbackId);

                if (feedback != null) {
                    request.setAttribute("feedback", feedback);
                    request.getRequestDispatcher("/WEB-INF/views/Feedback-Detail.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Feedback not found");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid feedback ID");
            }
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
        DAOFeedback dao = new DAOFeedback();
        String service = request.getParameter("service");

        if (service != null && service.equals("replyfeedback")) {
            try {
                int feedbackId = Integer.parseInt(request.getParameter("id"));
                String reply = request.getParameter("reply");

                // Lấy thông tin feedback hiện tại
                Feedback feedback = dao.getFeedbackDetailById(feedbackId);

                if (feedback == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Feedback not found");
                    return;
                }

                // Kiểm tra reply từ form
                if (reply == null || reply.trim().isEmpty()) {
                    request.setAttribute("error", "Reply cannot be empty");
                    request.setAttribute("feedback", feedback);
                    request.getRequestDispatcher("/WEB-INF/views/Feedback-Detail.jsp").forward(request, response);
                    return;
                }

                // Cập nhật reply (bất kể đã có reply trước đó hay chưa)
                int updated = dao.updateFeedbackReply(feedbackId, reply);
                feedback = dao.getFeedbackDetailById(feedbackId); // Cập nhật lại feedback sau khi reply

                if (updated > 0) {
                    request.setAttribute("message", "Reply updated successfully!");
                } else {
                    request.setAttribute("error", "Failed to update reply.");
                }

                request.setAttribute("feedback", feedback);
                request.getRequestDispatcher("/WEB-INF/views/Feedback-Detail.jsp").forward(request, response);

            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid feedback ID");
            }
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
