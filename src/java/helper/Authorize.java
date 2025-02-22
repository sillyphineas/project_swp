/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import entities.User;

/**
 *
 * @author HP
 */
public class Authorize {
    private static final String[] PUBLIC_ENDPOINTS = {"/HomePageController", "/ProductController", "/BlogURL", "/BlogDetailServlet", "/LoginController", "/ProductDetailController", "/RegisterController", "/VerifyAccountController"};
    private static final String[] CUSTOMER_ENDPOINTS = {"/HomePageController", "/CartURL", "/LogoutController", "/ProductController", "/ProductDetailController", "/UserProfileServlet", "/BlogURL", "/BlogDetailServlet",};
    private static final String[] ADMIN_ENDPOINTS = {"/LogoutController", "/UserProfileServlet"};
    private static final String[] SALES_ENDPOINTS = {"/LogoutController", "/UserProfileServlet"};
    private static final String[] MARKETING_ENDPOINTS = {"/LogoutController", "/UserProfileServlet"};
    private static final String[] SHIPPER_ENDPOINTS = {"/LogoutController", "/UserProfileServlet"};

    public static boolean isAccepted(User user, String link) {
        if (user == null) {
            return isLinkAllowed(link, PUBLIC_ENDPOINTS);
        }
        switch (user.getRoleId()) {
            case 1: // Admin
                return isLinkAllowed(link, ADMIN_ENDPOINTS);
            case 2: // Customer
                return isLinkAllowed(link, CUSTOMER_ENDPOINTS);
            case 3: // Shippers
                return isLinkAllowed(link, SHIPPER_ENDPOINTS);
            case 4: // Sales
                return isLinkAllowed(link, SALES_ENDPOINTS);
            case 5: // Marketing
                return isLinkAllowed(link, MARKETING_ENDPOINTS);
            default: // Public access
                return isLinkAllowed(link, PUBLIC_ENDPOINTS);
        }
    }

    private static boolean isLinkAllowed(String link, String[] endpoints) {
        for (String endpoint : endpoints) {
            if (endpoint.equals(link)) {
                return true;
            }
        }
        return false;
    }
}

