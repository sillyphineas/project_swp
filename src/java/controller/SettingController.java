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
import entity.SettingType;
import java.util.Vector;
import model.DAOBrand;
import model.DAORole;
import model.DAOSettingType;

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
        DAOSetting daoSetting = new DAOSetting();
        String service = request.getParameter("service");
        DAOSettingType daoSettingType = new DAOSettingType();

        if (service == null) {
            service = "displaySettings";
        }

        if (service.equals("displaySettings")) {
            int page = 1;
            int itemsPerPage = 10;

            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }

            Vector<Setting> settingList = daoSetting.getSettingsWithPagination(page, itemsPerPage);
            Vector<SettingType> settingTypes = daoSettingType.getAllSettingTypes();

            int totalSettings = daoSetting.getTotalSettingsCount();
            int totalPages = (int) Math.ceil((double) totalSettings / itemsPerPage);

            request.setAttribute("settingList", settingList);
            request.setAttribute("settingTypes", settingTypes);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            request.getRequestDispatcher("/WEB-INF/views/setting-list.jsp").forward(request, response);

        }

        if (service.equals("activate")) {
            int id = Integer.parseInt(request.getParameter("settingId"));
            Setting setting = daoSetting.getSettingById(id);
            setting.setStatus("Active");
            daoSetting.updateSetting(setting);
            response.sendRedirect("SettingController?service=displaySettings");
        }

        if (service.equals("deactivate")) {
            int id = Integer.parseInt(request.getParameter("settingId"));
            Setting setting = daoSetting.getSettingById(id);
            setting.setStatus("Inactive");
            daoSetting.updateSetting(setting);
            response.sendRedirect("SettingController?service=displaySettings");
        }

        if (service.equals("addSetting")) {
            Vector<SettingType> settingTypes = daoSettingType.getAllSettingTypes();
            request.setAttribute("settingTypes", settingTypes);
            request.getRequestDispatcher("/WEB-INF/views/add-setting.jsp").forward(request, response);
        }

        if (service.equals("updateSetting")) {
            int id = Integer.parseInt(request.getParameter("settingId"));
            Setting setting = daoSetting.getSettingById(id);
            Vector<SettingType> settingTypes = daoSettingType.getAllSettingTypes();
            request.setAttribute("setting", setting);
            request.setAttribute("settingTypes", settingTypes);
            request.getRequestDispatcher("/WEB-INF/views/update-setting.jsp").forward(request, response);
        }

        if (service.equals("deleteSetting")) {
            int id = Integer.parseInt(request.getParameter("settingId"));
            daoSetting.deleteSetting(id);
            response.sendRedirect("SettingController?service=displaySettings");
        }

        if (service.equals("searchSettings")) {
            String keyword = request.getParameter("keyword");
            int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
            int itemsPerPage = 10;

            Vector<Setting> settingList = daoSetting.searchSettings(keyword, page, itemsPerPage);
            int totalSettings = daoSetting.getTotalSearchCount(keyword);
            int totalPages = (int) Math.ceil((double) totalSettings / itemsPerPage);

            request.setAttribute("settingList", settingList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("keyword", keyword);

            request.getRequestDispatcher("/WEB-INF/views/setting-list.jsp").forward(request, response);
        }

        if (service.equals("filterSettings")) {
            String status = request.getParameter("status");
            String type = request.getParameter("type");
            String createdAt = request.getParameter("createdAt");

            if (createdAt == null || createdAt.trim().isEmpty()) {
                createdAt = null;
            }

            int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
            int itemsPerPage = 10;

            Vector<Setting> settingList = daoSetting.filterSettings(status, type, createdAt, page, itemsPerPage);
            int totalSettings = daoSetting.getTotalFilterCount(status, type, createdAt);
            int totalPages = (int) Math.ceil((double) totalSettings / itemsPerPage);

            request.setAttribute("settingList", settingList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("status", status);
            request.setAttribute("type", type);
            request.setAttribute("createdAt", createdAt);

            request.getRequestDispatcher("/WEB-INF/views/setting-list.jsp").forward(request, response);
        }

        if (service.equals("sortSettings")) {
            String sortBy = request.getParameter("sortBy");
            String sortOrder = request.getParameter("sortOrder");

            if (!"id".equals(sortBy) && !"key_name".equals(sortBy)) {
                sortBy = "id";
            }
            if (!"asc".equalsIgnoreCase(sortOrder) && !"desc".equalsIgnoreCase(sortOrder)) {
                sortOrder = "asc";
            }

            int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
            int itemsPerPage = 10;

            Vector<Setting> settingList = daoSetting.sortSettings(sortBy, sortOrder, page, itemsPerPage);
            int totalSettings = daoSetting.getTotalSettingsCount();
            int totalPages = (int) Math.ceil((double) totalSettings / itemsPerPage);

            request.setAttribute("settingList", settingList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("sortBy", sortBy);
            request.setAttribute("sortOrder", sortOrder);

            request.getRequestDispatcher("/WEB-INF/views/setting-list.jsp").forward(request, response);
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
        DAOSetting daoSetting = new DAOSetting();
        DAOSettingType daoSettingType = new DAOSettingType();
        DAORole daoRole = new DAORole();
        DAOBrand daoBrand = new DAOBrand();

        String service = request.getParameter("service");

        if (service.equals("addSetting")) {
            String typeName = request.getParameter("type");
            String keyName = request.getParameter("keyName");
            String value = request.getParameter("value");
            String description = request.getParameter("description");
            String status = request.getParameter("status");

            int typeId = daoSettingType.getTypeIdByName(typeName);
            if (typeId == -1) {
                typeId = daoSettingType.addNewSettingType(typeName);
            }

            Integer roleId = null, brandId = null;

            if ("User Role Management".equals(typeName)) {
                roleId = daoRole.addRoleAndReturnId(keyName);
            }

            if ("Brand Management".equals(typeName)) {
                brandId = daoBrand.addBrandAndReturnId(keyName);
            }

            Setting setting = new Setting();
            setting.setTypeId(typeId);
            setting.setTypeName(typeName);
            setting.setKeyName(keyName);
            setting.setValue(value);
            setting.setDescription(description);
            setting.setStatus(status);
            setting.setRoleId(roleId);
            setting.setBrandId(brandId);

            daoSetting.addSetting(setting);
            response.sendRedirect("SettingController?service=displaySettings");
        }

        if (service.equals("updateSetting")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String keyName = request.getParameter("keyName");
            String value = request.getParameter("value");
            String description = request.getParameter("description");
            String status = request.getParameter("status");

            Setting setting = daoSetting.getSettingById(id);
            setting.setKeyName(keyName);
            setting.setValue(value);
            setting.setDescription(description);
            setting.setStatus(status);

            daoSetting.updateSetting(setting);
            response.sendRedirect("SettingController?service=displaySettings");
        }
    }

}
