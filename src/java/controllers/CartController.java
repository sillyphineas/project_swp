/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import entities.Cart;
import entities.CartItem;
import entities.Order;
import entities.OrderDetail;
import entities.Product;
import entities.User;
import helper.Authorize;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.DAOCart;
import models.DAOCartItem;
import models.DAOOrder;
import models.DAOOrderDetail;
import models.DAOProduct;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "CartController", urlPatterns = {"/CartURL"})
public class CartController extends HttpServlet {

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
        //Authorize
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        if (!Authorize.isAccepted(user, "/CartURL")) {
            request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        response.setContentType("text/html;charset=UTF-8");
        Integer customerID = (Integer) session.getAttribute("userID");
        DAOCart dao = new DAOCart();
        DAOCartItem daoItem = new DAOCartItem();
        DAOProduct daoPro = new DAOProduct();
        DAOOrder daoOrder = new DAOOrder();
        DAOOrderDetail daoOD = new DAOOrderDetail();

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String service = request.getParameter("service");
            Vector<Product> listpro = daoPro.getLatestProducts();
            request.setAttribute("listpro", listpro);
//            request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
            if (service == null) {
                service = "showCart";
            }
//            if (customerID == null || customerID == 0) {
//                request.setAttribute("error", "You need to be logged in to view your cart.");
//                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
//                return;
//            }
//            if (service.equals("search")) {
//                String query = request.getParameter("query");
//
//                if (query == null || query.trim().isEmpty()) {
//                    request.setAttribute("error", "Please enter a search term!");
//                    request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
//                    return;
//                }
//
//                Cart cart = dao.getCartByCustomerID(customerID);
//
//                if (cart == null) {
//                    request.setAttribute("error", "Your cart is empty!");
//                    request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
//                    return;
//                }
//
//                List<CartItem> cartItems = dao.getCartItemsByCartID(cart.getCartID());
//
//                // Kiểm tra nếu cartItems không phải null và không trống
//                if (cartItems == null || cartItems.isEmpty()) {
//                    request.setAttribute("error", "No items found in your cart!");
//                    request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
//                    return;
//                }
//
//                // Lọc các sản phẩm theo từ khóa
//                List<CartItem> filteredCartItems = cartItems.stream()
//                        .filter(item -> {
//                            Product product = item.getProduct();
//                            return product != null
//                                    && (product.getName().toLowerCase().contains(query.toLowerCase())
//                                    || String.valueOf(product.getPrice()).contains(query));
//                        })
//                        .collect(Collectors.toList()); // Sử dụng collect thay vì toList để đảm bảo tương thích
//
//                if (filteredCartItems.isEmpty()) {
//                    request.setAttribute("message", "No results found.");
//                }
//
//                // Tính tổng giá trị sau tìm kiếm
//                double totalOrderPrice = filteredCartItems.stream()
//                        .mapToDouble(CartItem::getTotalPrice)
//                        .sum();
//
//                // Truyền danh sách sản phẩm và tổng giá trị vào request
//                request.setAttribute("cartItems", filteredCartItems);
//                request.setAttribute("totalOrderPrice", totalOrderPrice);
//                request.setAttribute("query", query);
//
//                request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
//            }
//            if (service != null && service.equals("updateQuantity")) {
//                try {
//                    int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
//                    int newQuantity = Integer.parseInt(request.getParameter("newQuantity"));
//
//                    // Cập nhật số lượng nếu số lượng mới hợp lệ
//                    if (newQuantity > 0) {
//                        CartItem cartItem = daoItem.getCartItemById(cartItemId);
//                        cartItem.setQuantity(newQuantity);
//                        double newTotalPrice = cartItem.getPrice() * newQuantity;  // Tính lại tổng giá trị của sản phẩm
//                        cartItem.setTotalPrice(newTotalPrice);
//
//                        daoItem.updateCartItem(cartItem);  // Cập nhật trong cơ sở dữ liệu
//                    }
//
//                    // Lấy tất cả các item trong giỏ hàng của khách hàng
//                    Cart cart = dao.getCartByCustomerID(1);  // Giả sử bạn đã có customerID
//                    List<CartItem> cartItems = dao.getCartItemsByCartID(cart.getCartID());
//
//                    // Tính tổng giá trị của giỏ hàng
//                    double totalOrderPrice = 0.0;
//                    for (CartItem item : cartItems) {
//                        totalOrderPrice += item.getTotalPrice();
//                    }
//
//                    // Lưu tổng giá của giỏ hàng vào request
//                    request.setAttribute("totalOrderPrice", totalOrderPrice);
//                    request.setAttribute("cartItems", cartItems);
//
//                    // Forward đến trang giỏ hàng để cập nhật thông tin giỏ hàng mới
//                    RequestDispatcher dispatcher = request.getRequestDispatcher("cart.jsp");
//                    dispatcher.forward(request, response);
//
//                } catch (Exception e) {
//                    response.getWriter().write("Error: " + e.getMessage());
//                }
//            }
            if (service.equals("updateQuantity")) {
                int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                CartItem cartItem = daoItem.getCartItemById(cartItemId);
                try {
                    dao.updateCartItemQuantity(cartItem, quantity);
                    response.setStatus(HttpServletResponse.SC_OK);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
            if (service.equals("showCart")) {
                Cart cart = dao.getCartByCustomerID(customerID);
                if (cart != null) {
                    List<CartItem> cartItems = dao.getCartItemsByCartID(cart.getCartID());
                    Double totalOrderPrice = 0.0;
                    for (CartItem item : cartItems) {
                        Product product = daoPro.getProductById(item.getProductVariantID());
                        item.setProduct(product);
                        totalOrderPrice += item.getTotalPrice();
                    }

                    request.setAttribute("cartItems", cartItems);
                    request.setAttribute("totalOrderPrice", totalOrderPrice);
                } else {
                    request.setAttribute("showMessage", "Your cart is empty!");
                }
                request.setAttribute("cart", cart);
                request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
            }
            if (service.equals("updateQuantity")) {

                try {
                    int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                    int newQuantity = Integer.parseInt(request.getParameter("newQuantity"));

                    if (newQuantity > 0) {
                        CartItem cartItem = daoItem.getCartItemById(cartItemId);

                        if (cartItem != null) {
                            cartItem.setQuantity(newQuantity);
                            double newTotalPrice = cartItem.getPrice() * newQuantity;
                            cartItem.setTotalPrice(newTotalPrice);

                            daoItem.updateCartItem(cartItem);
                            response.sendRedirect("CartURL?service=showCart");
                        }
                    }

                    Cart cart = dao.getCartByCustomerID(customerID);

                    List<CartItem> cartItems = dao.getCartItemsByCartID(cart.getCartID());
                    double totalOrderPrice = 0.0;
                    for (CartItem item : cartItems) {
                        totalOrderPrice += item.getTotalPrice();
                    }

                    response.getWriter().write(String.format("%.2f", totalOrderPrice));
                } catch (NumberFormatException e) {
                    response.getWriter().write("Invalid input.");
                } catch (Exception e) {
                    response.getWriter().write("Error updating cart.");
                }
            }
            if (service.equals("removeItem")) {
                int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                try {
                    dao.deleteCartItem(cartItemId);
                    response.sendRedirect("CartURL?service=showCart");
                } catch (Exception e) {
                    e.printStackTrace();
                    response.getWriter().println("Error removing item from cart.");
                }
            }
            if (service.equals("checkOut")) {
                Cart cart = dao.getCartByCustomerID(customerID);
                if (cart != null) {
                    List<CartItem> cartItems = dao.getCartItemsByCartID(cart.getCartID());
                    double totalOrderPrice = 0.0;

                    for (CartItem item : cartItems) {
                        Product product = daoPro.getProductById(item.getProductVariantID());
                        item.setProduct(product);
                        totalOrderPrice += item.getTotalPrice();
                    }

                    String fullName = (String) session.getAttribute("Name");
                    String email = (String) session.getAttribute("Email");
                    String PassHash = (String) session.getAttribute("PassHash");
                    String mobile = (String) session.getAttribute("PhoneNumber");
                    String address = (String) session.getAttribute("Address");

                    request.setAttribute("cartItems", cartItems);
                    request.setAttribute("totalOrderPrice", totalOrderPrice);
                    request.setAttribute("fullName", fullName);
                    request.setAttribute("email", email);
                    request.setAttribute("PassHash", PassHash);
                    request.setAttribute("mobile", mobile);
                    request.setAttribute("address", address);
                } else {
                    request.setAttribute("error", "Your cart is empty!");
                }
                request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
            }

            if (service.equals("add2cart")) {

                int productID = Integer.parseInt(request.getParameter("productID"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                Cart cart = dao.getCartByCustomerID(customerID);
                System.out.println(cart);

                if (cart == null) {
                    System.out.println("Creating new cart for CustomerID: " + customerID);
                    cart = new Cart();
                    cart.setCustomerID(customerID);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String createdAt = sdf.format(new Date());
                    cart.setCreatedAt(createdAt);
                    cart.setCartStatus("active");
                    int cartID = dao.addCart(cart);
                    cart.setCartID(cartID);
                }

                // Kiểm tra xem sản phẩm đã có trong giỏ chưa
                CartItem existingItem = daoItem.getCartItemByCartIDAndProductID(cart.getCartID(), productID);
                int existingQuantity = (existingItem != null) ? existingItem.getQuantity() : 0;
                request.setAttribute("existingQuantity", existingQuantity);
                if (existingItem != null) {

                    int newQuantity = existingItem.getQuantity() + quantity;

                    if (newQuantity > 0) {
                        // Nếu số lượng mới hợp lệ (>= 1), cập nhật giỏ hàng
                        existingItem.setQuantity(newQuantity);
                        existingItem.setTotalPrice(existingItem.getPrice() * newQuantity);
                        daoItem.updateCartItem(existingItem);
                    } else {
                        try {
                            daoItem.removeCartItem(existingItem.getCartItemID());
                        } catch (SQLException ex) {
                            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    Product product = daoPro.getProductById(productID);
                    if (product != null) {
                        CartItem newItem = new CartItem();
                        newItem.setCartID(cart.getCartID());

                        newItem.setProductVariantID(productID);
                        newItem.setQuantity(quantity); 
                        newItem.setPrice(product.getPrice());
                        newItem.setTotalPrice(product.getPrice() * quantity);
                        daoItem.addCartItem(newItem);
                    } else {
                        request.setAttribute("error", "Product not found!");
                        request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                        return;
                    }
                }
                session.setAttribute("cartMessage", "Product added to cart successfully!");
                response.sendRedirect("ProductController");
            }

            if (service.equals("submitOrder")) {
                String fullName = request.getParameter("fullName");
                String email = request.getParameter("email");
                String address1 = request.getParameter("address1");
                String address2 = request.getParameter("address2");
                String message = request.getParameter("message");
                Cart cart = dao.getCartByCustomerID(customerID);
                if (cart == null || dao.getCartItemsByCartID(cart.getCartID()).isEmpty()) {
                    request.setAttribute("error", "Your cart is empty or invalid!");
                    response.sendRedirect("checkout.jsp");
                    return;
                }
                Order order = new Order();
                order.setBuyerID(customerID);
                order.setShippingAddress(address1 + " " + address2);
                order.setMessage(message);
                order.setTotalPrice(cart.getTotalPrice());
                order.setOrderTime(new Date());
                order.setOrderStatus("Pending");
                int orderId = daoOrder.addOrder(order);
                if (orderId == -1) {
                    request.setAttribute("error", "Order submission failed due to a technical error.");
                    response.sendRedirect("checkout.jsp");
                    return;
                }
                session.setAttribute("cart", null); // Clear the cart from session
                session.setAttribute("checkoutMessage", "Checkout Success! Your order has been submitted.");
                response.sendRedirect("HomePageController");
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

}
