/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import entity.Feedback;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import model.DAOFeedback;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "FeedBackController", urlPatterns = {"/FeedBackController"})
public class FeetBacksController extends HttpServlet {

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
        DAOFeedback dao = new DAOFeedback();
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
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
                    int pageSize = 10;

                    List<Feedback> feedbacks = dao.getPaginatedFeedbacks(page, pageSize);
                    int totalFeedbacks = dao.getTotalFeedbacks();

                    int totalPages = (totalFeedbacks + pageSize - 1) / pageSize;

                    request.setAttribute("feedbacks", feedbacks);
                    request.setAttribute("currentPage", page);
                    request.setAttribute("totalPages", totalPages);

                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/product-details.jsp");
                    dispatcher.forward(request, response);

                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid page number");
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing feedbacks");
                }
            }
            if (service.equals("ListFeedbackWithId")) {
                try {
                    String productIdStr = request.getParameter("productId");
                    String pageStr = request.getParameter("page");

                    int productId = 0;
                    int page = 1;
                    int pageSize = 10; // Số feedback trên mỗi trang

                    if (productIdStr != null && productIdStr.matches("\\d+")) {
                        productId = Integer.parseInt(productIdStr);
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID");
                        return;
                    }

                    if (pageStr != null && pageStr.matches("\\d+")) {
                        page = Integer.parseInt(pageStr);
                    }

                    // Lấy danh sách feedback phân trang theo productId
                    List<Feedback> feedbacks = dao.getPaginatedFeedbacksByProductId(productId, page, pageSize);
                    int totalFeedbacks = dao.getTotalFeedbacksByProductId(productId);

                    int totalPages = (totalFeedbacks + pageSize - 1) / pageSize;

                    request.setAttribute("feedbacks", feedbacks);
                    request.setAttribute("currentPage", page);
                    request.setAttribute("totalPages", totalPages);
                    request.setAttribute("productId", productId); // Giữ lại productId để load tiếp trang khác

                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/list-all-feedback.jsp");
                    dispatcher.forward(request, response);

                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid page number");
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing feedbacks");
                }
            }

            if (service.equals("uploadImages")) {
                try {
                    String feedbackIdStr = request.getParameter("feedbackId");
                    if (feedbackIdStr == null || !feedbackIdStr.matches("\\d+")) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu hoặc sai feedbackId");
                        return;
                    }
                    int feedbackId = Integer.parseInt(feedbackIdStr);

                    Collection<Part> parts = request.getParts();
                    List<String> imagePaths = new ArrayList<>();
                    String uploadDir = getServletContext().getRealPath("") + File.separator + "uploads";
                    File uploadFolder = new File(uploadDir);
                    if (!uploadFolder.exists()) {
                        uploadFolder.mkdir();
                    }

                    for (Part part : parts) {
                        if (part.getName().equals("images") && part.getSize() > 0) {
                            String fileName = extractFileName(part);
                            String filePath = "uploads" + File.separator + fileName;
                            part.write(uploadDir + File.separator + fileName);
                            imagePaths.add(filePath);
                        }
                    }

                    if (!imagePaths.isEmpty()) {
                        String jsonImages = new Gson().toJson(imagePaths);
                        dao.saveImages(feedbackId, jsonImages);  // Lưu ảnh cho đúng feedbackId
                    }

                    request.setAttribute("message", "Upload thành công!");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/product-details.jsp");
                    dispatcher.forward(request, response);

                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi upload ảnh");
                }
            }
            if (service.equals("FilterByStar")) {
                try {
                    String productIdStr = request.getParameter("productId");
                    String starStr = request.getParameter("star");
                    String pageStr = request.getParameter("page");
                    System.out.println("star"+starStr+"ProductId"+productIdStr);
                    int productId = 0;
                    int star = 0;
                    int page = 1;
                    int pageSize = 10; // Số feedback trên mỗi trang

                    if (productIdStr != null && productIdStr.matches("\\d+")) {
                        productId = Integer.parseInt(productIdStr);
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID");
                        return;
                    }

                    if (starStr != null && starStr.matches("\\d+")) {
                        star = Integer.parseInt(starStr);
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid star rating");
                        return;
                    }

                    if (pageStr != null && pageStr.matches("\\d+")) {
                        page = Integer.parseInt(pageStr);
                    }

                    // Lấy danh sách feedback theo số sao với phân trang
                    List<Feedback> feedbacks = dao.getPaginatedFeedbacksByStar(productId, star, page, pageSize);
                    int totalFeedbacks = dao.getTotalFeedbacksByStar(productId, star);
                    int totalPages = (totalFeedbacks + pageSize - 1) / pageSize;

                    request.setAttribute("feedbacks", feedbacks);
                    request.setAttribute("currentPage", page);
                    request.setAttribute("totalPages", totalPages);
                    request.setAttribute("productId", productId);
                    request.setAttribute("selectedStar", star);

                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/list-all-feedback.jsp");
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
        processRequest(request, response);
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

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String content : contentDisp.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return null;
    }

}
