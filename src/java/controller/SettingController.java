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
import model.DAOSetting;
import entity.Setting;
import java.util.Vector;

/**
 *
 * @author HP
 */
@WebServlet(name = "SettingController", urlPatterns = {"/SettingController"})
public class SettingController extends HttpServlet {

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
            out.println("<title>Servlet SettingController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SettingController at " + request.getContextPath() + "</h1>");
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
        DAOSetting dao = new DAOSetting();
        String service = request.getParameter("service");
        if (service == null) {
            service = "displaySettings";
        }
        if (service.equals("displaySettings")) {
            Vector<Setting> settingList = dao.getAllSettings();
            request.setAttribute("settingList", settingList);
            request.getRequestDispatcher("/WEB-INF/views/setting-list.jsp").forward(request, response);
        }
        if (service.equals("activate")) {
            int id = Integer.parseInt(request.getParameter("settingId"));
            Setting setting = dao.getSettingById(id);
            setting.setStatus("Active");
            dao.updateSetting(setting);
            response.sendRedirect("SettingController?service=displaySettings");
        }
        if (service.equals("deactivate")) {
            int id = Integer.parseInt(request.getParameter("settingId"));
            Setting setting = dao.getSettingById(id);
            setting.setStatus("Inactive");
            dao.updateSetting(setting);
            response.sendRedirect("SettingController?service=displaySettings");
        }
        if (service.equals("addSetting")) {
            request.getRequestDispatcher("/WEB-INF/views/add-setting.jsp").forward(request, response);
        }
        if (service.equals("updateSetting")) {
            int id = Integer.parseInt(request.getParameter("settingId"));
            Setting setting = dao.getSettingById(id);
            request.setAttribute("setting", setting);
            request.getRequestDispatcher("/WEB-INF/views/update-setting.jsp").forward(request, response);
        }
        if (service.equals("deleteSetting")) {
            int id = Integer.parseInt(request.getParameter("settingId"));
            dao.deleteSetting(id);
            response.sendRedirect("SettingController?service=displaySettings");
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
        String service = request.getParameter("service");
        DAOSetting dao = new DAOSetting();

        if (service != null && service.equals("updateSetting")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String type = request.getParameter("type");
            String keyName = request.getParameter("key_name");
            String value = request.getParameter("value");
            String description = request.getParameter("description");
            String status = request.getParameter("status");

            Setting setting = new Setting();
            setting.setId(id);
            setting.setType(type);
            setting.setKeyName(keyName);
            setting.setValue(value);
            setting.setDescription(description);
            setting.setStatus(status);

            dao.updateSetting(setting);
            response.sendRedirect("SettingController?service=displaySettings");
        }
        if (service != null && service.equals("addSetting")) {
            String type = request.getParameter("type");
            String keyName = request.getParameter("keyName");
            String value = request.getParameter("value");
            String description = request.getParameter("description");
            String status = request.getParameter("status");
//            System.out.println(type);
//            System.out.println(keyName);
//            System.out.println(value);
//            System.out.println(description);
//            System.out.println(status);

            Setting setting = new Setting();
            setting.setType(type);
            setting.setKeyName(keyName);
            setting.setValue(value);
            setting.setDescription(description);
            setting.setStatus(status);

            dao.addSetting(setting);
            response.sendRedirect("SettingController?service=displaySettings");
        }

    }
}
