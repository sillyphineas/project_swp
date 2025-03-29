/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import entity.User;

/**
 *
 * @author HP
 */
public class Authorize {
    private static final String[] PUBLIC_ENDPOINTS = {"/HomePageController", "/ResetController", "/FeedBackController" , "/ProductController", "/BlogURL", "/BlogDetailServlet", "/LoginController", "/ProductDetailController", "/RegisterController", "/VerifyAccountController"};
    private static final String[] CUSTOMER_ENDPOINTS = {"/HomePageController", "/UpdateProfileController", "/CustomerOrderDetailController", "/FeedBackController", "/CustomerOrderController", "/ChangePasswordController", "/CartURL", "/LogoutController", "/ProductController", "/ProductDetailController", "/UserProfileServlet", "/BlogURL", "/BlogDetailServlet", "/OrderController" , "/VNPayPaymentServlet", "/VNPayReturnServlet"};
    private static final String[] ADMIN_ENDPOINTS = {"/LogoutController" , "/FeedBackController", "/UpdateProfileController","/ChangePasswordController", "/UserProfileServlet", "/AdminDashboardController", "/AdminUserController", "/SettingController" , "/UserDetailController", "/UserController"};
    private static final String[] SALES_ENDPOINTS = {"/LogoutController", "/FeedBackController","/UpdateProfileController", "/SaleOrderController", "/ChangePasswordController", "/UserProfileServlet","/salesDashboardController"};
    private static final String[] MARKETING_ENDPOINTS = {"/LogoutController", "/FeedBackController","/UpdateProfileController", "/MaketingFeedBackController", "/MarketingFeedbackDetails", "/ChangePasswordController", "/UserProfileServlet", "/AddProductController", "/CustomerController", "/CustomerDetailController", "/EditCustomerController", "/MarketingDashboardController", "/MarketingPostController", "/MarketingProductController" , "/MarketingProductDetails", "/ProductController" , "/ProductDetailController", "/SliderController"};
    private static final String[] SHIPPER_ENDPOINTS = {"/LogoutController", "/FeedBackController","/UpdateProfileController","/ChangePasswordController", "/UserProfileServlet", "/ShipperDashboardController", "/ShipperOrderController"};

    public static boolean isAccepted(User user, String link) {
        if (user == null) {
            return isLinkAllowed(link, PUBLIC_ENDPOINTS);
        }
        switch (user.getRoleId()) {
            case 1: // Admin
                return isLinkAllowed(link, ADMIN_ENDPOINTS);
            case 5: // Customer
                return isLinkAllowed(link, CUSTOMER_ENDPOINTS);
            case 4: // Shippers
                return isLinkAllowed(link, SHIPPER_ENDPOINTS);
            case 3: // Sales
                return isLinkAllowed(link, SALES_ENDPOINTS);
            case 2: // Marketing
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

