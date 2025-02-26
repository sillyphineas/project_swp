/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;


import entity.Address;

import entity.Cart;
import entity.CartItem;
import entity.Order;
import entity.OrderDetail;
import entity.Product;
import entity.ProductVariant;
import entity.User;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAOAddress;
import model.DAOCart;
import model.DAOCartItem;
import model.DAOOrder;
import model.DAOOrderDetail;
import model.DAOProduct;
import model.DAOProductVariant;
import java.math.BigDecimal;
import java.math.RoundingMode;


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

            throws ServletException, IOException, SQLException {
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

        DAOProductVariant daoProVariant = new DAOProductVariant();
        DAOOrder daoOrder = new DAOOrder();
        DAOOrderDetail daoOD = new DAOOrderDetail();
        DAOAddress daoAdd = new DAOAddress();
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String service = request.getParameter("service");
            Vector<Product> listpro = daoPro.getLatestProducts();
            request.setAttribute("listpro", listpro);
            List<Address> addresses = daoAdd.getAddressesByUserId(customerID);
            request.setAttribute("userAddresses", addresses);
            if (service == null) {
                service = "showCart";
            }
            if (service.equals("showCart")) {
                Cart cart = dao.getCartByCustomerID(customerID);
                int pageSize = 4;
                int page = 1;
                String pageParam = request.getParameter("page");

                if (pageParam != null && !pageParam.isEmpty()) {
                    try {
                        page = Integer.parseInt(pageParam);
                    } catch (NumberFormatException e) {
                        page = 1;
                    }
                }
                if (cart != null) {
                    List<CartItem> allCartItems = dao.getCartItemsByCartID1(cart.getCartID());
                    int totalItems = allCartItems.size();
                    int totalPages = (int) Math.ceil((double) totalItems / pageSize);

                    List<CartItem> cartItems = dao.getCartItemsByCartID(cart.getCartID(), page, pageSize);

                    BigDecimal totalOrderPrice = BigDecimal.ZERO;
                    for (CartItem item : cartItems) {
                        ProductVariant productVariant = daoProVariant.getProductVariantById(item.getProductVariantID());
                        if (productVariant == null) {
                            continue;
                        }

                        Product product = daoPro.getProductById(productVariant.getProductID());
                        if (product == null) {
                            continue;
                        }

                        item.setProduct(product);
                        item.setProductVariant(productVariant);
                        BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
                        BigDecimal price = BigDecimal.valueOf(productVariant.getPrice());
                        BigDecimal totalPrice = quantity.multiply(price).setScale(2, RoundingMode.HALF_UP);
                        item.setTotalPrice(totalPrice);
                        totalOrderPrice = totalOrderPrice.add(totalPrice);
                    }

                    request.setAttribute("cartItems", cartItems);
                    request.setAttribute("totalOrderPrice", totalOrderPrice);

                    request.setAttribute("currentPage", page);
                    request.setAttribute("totalPages", totalPages);
                } else {
                    request.setAttribute("showMessage", "Your cart is empty!");
                }

                request.setAttribute("cart", cart);
                request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
            }

            if (service.equals("updateQuantity")) {
                try {
                    Cart cart = dao.getCartByCustomerID(customerID);
                    int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                    int newQuantity = Integer.parseInt(request.getParameter("newQuantity"));

                    if (newQuantity <= 0) {
                        response.setContentType("application/json");
                        response.getWriter().write("{\"status\": \"error\", \"message\": \"Quantity must be a positive integer\"}");
                        return;
                    }

                    CartItem cartItem = daoItem.getCartItemById(cartItemId);
                    if (cartItem != null) {
                        cartItem.setQuantity(newQuantity);
                        BigDecimal price = BigDecimal.valueOf(cartItem.getPrice());
                        BigDecimal quantity = BigDecimal.valueOf(newQuantity);
                        BigDecimal newTotalPrice = price.multiply(quantity).setScale(2, RoundingMode.HALF_UP); // Nhân và làm tròn
                        daoItem.updateCartItem(cartItem);

                        BigDecimal totalOrderPrice = BigDecimal.ZERO;
                        List<CartItem> cartItems = dao.getCartItemsByCartID1(cart.getCartID());
                        for (CartItem item : cartItems) {
                            totalOrderPrice = totalOrderPrice.add(item.getTotalPrice()).setScale(2, RoundingMode.HALF_UP);
                        }

                        response.setContentType("application/json");
                        response.getWriter().write("{\"status\": \"success\", \"totalOrderPrice\": \"" + totalOrderPrice + "\"}");
                    } else {
                        response.setContentType("application/json");
                        response.getWriter().write("{\"status\": \"error\", \"message\": \"Product not found in cart\"}");
                    }
                } catch (NumberFormatException e) {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"status\": \"error\", \"message\": \"Invalid input format\"}");
                } catch (Exception e) {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"status\": \"error\", \"message\": \"Error updating cart\"}");
                }
            }

            if (service.equals("removeItem")) {
                try {
                    int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));

                    CartItem cartItem = daoItem.getCartItemById(cartItemId);
                    if (cartItem != null) {

                        int rowsAffected = daoItem.delete(cartItem.getCartItemID());

                        if (rowsAffected > 0) {

                            response.setContentType("application/json");
                            response.getWriter().write("{\"status\": \"success\"}");
                        } else {

                            response.setContentType("application/json");
                            response.getWriter().write("{\"status\": \"error\", \"message\": \"Failed to delete product\"}");
                        }
                    } else {
                        response.setContentType("application/json");
                        response.getWriter().write("{\"status\": \"error\", \"message\": \"Product not found\"}");
                    }
                } catch (Exception e) {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"status\": \"error\", \"message\": \"Error processing request: " + e.getMessage() + "\"}");
                }
            }

            if (service.equals("checkOut")) {
                String[] selectedItems = request.getParameterValues("selectedItems");
                if (selectedItems == null || selectedItems.length == 0) {
                    Cart cart = dao.getCartByCustomerID(customerID);
                    if (cart != null) {
                        List<CartItem> cartItems = dao.getCartItemsByCartID1(cart.getCartID());
                        request.setAttribute("cartItems", cartItems);
                        request.setAttribute("error", "Bạn chưa chọn sản phẩm nào để thanh toán.");
                        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
                    }
                } else {
                    for (String selectedItemID : selectedItems) {
                        int cartItemId = Integer.parseInt(selectedItemID);
                        String quantityParam = request.getParameter("quantity_" + cartItemId);

                        if (quantityParam != null) {
                            int newQuantity = Integer.parseInt(quantityParam);
                            CartItem cartItem = daoItem.getCartItemById(cartItemId);
                            if (cartItem != null) {
                                cartItem.setQuantity(newQuantity);
                                BigDecimal price = BigDecimal.valueOf(cartItem.getPrice()); // Chuyển từ double sang BigDecimal
                                BigDecimal newquantity = BigDecimal.valueOf(newQuantity); // Chuyển newQuantity thành BigDecimal
                                BigDecimal totalPrice = price.multiply(newquantity).setScale(2, RoundingMode.HALF_UP); // Nhân và làm tròn
                                cartItem.setTotalPrice(totalPrice);
                                daoItem.updateCartItem(cartItem);
                            }
                        }
                    }
                    BigDecimal totalOrderPrice = BigDecimal.ZERO;
                    List<CartItem> selectedCartItems = new ArrayList<>();
                    Cart cart = dao.getCartByCustomerID(customerID);
                    if (cart != null) {
                        List<CartItem> cartItems = dao.getCartItemsByCartID1(cart.getCartID());
                        for (CartItem item : cartItems) {
                            for (String selectedItemID : selectedItems) {
                                if (Integer.toString(item.getCartItemID()).equals(selectedItemID)) {
                                    ProductVariant productVariant = daoProVariant.getProductVariantById(item.getProductVariantID());
                                    if (productVariant != null) {
                                        Product product = daoPro.getProductById(productVariant.getProductID());
                                        if (product != null) {
                                            item.setProduct(product);
                                            item.setProductVariant(productVariant);
                                            totalOrderPrice = totalOrderPrice.add(item.getTotalPrice()).setScale(2, RoundingMode.HALF_UP);
                                            selectedCartItems.add(item);
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }

                    if (!selectedCartItems.isEmpty()) {
                        request.setAttribute("cartItems", selectedCartItems);
                        request.setAttribute("totalOrderPrice", totalOrderPrice);
                        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
                    } else {
                        request.setAttribute("error", "Bạn chưa chọn sản phẩm nào để thanh toán.");
                        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
                    }
                }
            }

            if (service.equals("add2cart")) {
                int productID = Integer.parseInt(request.getParameter("productID"));
                String color = request.getParameter("color");
                int storage = Integer.parseInt(request.getParameter("storage"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                System.out.println(productID + "," + color + "," + storage + "," + quantity);
                ProductVariant productVariant = daoProVariant.getProductVariantByDetails(productID, color, storage);

                if (productVariant == null) {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"status\": \"error\", \"message\": \"Product variant not found!\"}");
                    return;
                }
                if (productVariant.getStock() < quantity) {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"status\": \"error\", \"message\": \"Not enough stock available! Only " + productVariant.getStock() + " units left.\"}");
                    return;
                }
                Cart cart = dao.getCartByCustomerID(customerID);
                if (cart == null) {

                    cart = new Cart();
                    cart.setCustomerID(customerID);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String createdAt = sdf.format(new Date());
                    cart.setCreatedAt(createdAt);

                    cart.setCartStatus("active");
                    int cartID = dao.addCart(cart);
                    cart.setCartID(cartID);
                }
                CartItem existingItem = daoItem.getCartItemByCartIDAndProductVariantID(cart.getCartID(), productVariant.getId());
                if (existingItem != null) {
                    int newQuantity = existingItem.getQuantity() + quantity;
                    if (productVariant.getStock() < newQuantity) {
                        response.setContentType("application/json");
                        response.getWriter().write("{\"status\": \"error\", \"message\": \"Not enough stock available! Only " + productVariant.getStock() + " units left.\"}");
                        return;
                    }
                    BigDecimal price = BigDecimal.valueOf(productVariant.getPrice());
                    BigDecimal newquantity = BigDecimal.valueOf(newQuantity);
                    BigDecimal totalPrice = price.multiply(newquantity).setScale(2, RoundingMode.HALF_UP);
                    existingItem.setQuantity(newQuantity);
                    System.out.println(totalPrice);
                    existingItem.setTotalPrice(totalPrice);
                    System.out.println("sau" + existingItem.getQuantity());
                    System.out.println(daoItem.updateCartItem(existingItem));
                } else {
                    CartItem newItem = new CartItem();
                    newItem.setCartID(cart.getCartID());
                    newItem.setProductVariantID(productVariant.getId());
                    newItem.setQuantity(quantity);
                    newItem.setPrice(productVariant.getPrice());
                    BigDecimal price = BigDecimal.valueOf(productVariant.getPrice()); // Chuyển từ double sang BigDecimal
                    BigDecimal qty = BigDecimal.valueOf(quantity); // Chuyển quantity thành BigDecimal
                    BigDecimal totalPrice = price.multiply(qty).setScale(2, RoundingMode.HALF_UP);
                    newItem.setTotalPrice(totalPrice);
                    daoItem.addCartItem(newItem);
                }

                double totalCartPrice = daoItem.calculateTotalCartPrice(cart.getCartID());
                cart.setTotalPrice(totalCartPrice);
                dao.updateCart(cart);

                response.setContentType("application/json");
                response.getWriter().write("{\"status\": \"success\", \"message\": \"Product added to cart successfully!\"}");
            }

            if (service.equals("submitOrder")) {
                String selectedAddressId = request.getParameter("addressSelect");
                String recipientName = request.getParameter("newFullName");
                String recipientPhone = request.getParameter("newPhone");
                int paymentMethod = Integer.parseInt(request.getParameter("paymentMethod"));

                if (selectedAddressId == null || selectedAddressId.isEmpty()) {
                    response.sendRedirect("checkout.jsp?error=NoAddressSelected");
                    return;
                }

                int addressId = Integer.parseInt(selectedAddressId);
                Address address = daoAdd.getAddressById(addressId);
                if (address == null) {
                    response.sendRedirect("checkout.jsp?error=InvalidAddress");
                    return;
                }

                List<CartItem> cartItems = (List<CartItem>) session.getAttribute("selectedCartItems");
                if (cartItems == null || cartItems.isEmpty()) {
                    response.sendRedirect("checkout.jsp?error=EmptyCart");
                    return;
                }

                double totalPrice = (double) session.getAttribute("totalOrderPrice");

                Order order = new Order();
                order.setBuyerID(customerID);
                order.setShippingAddress(address.getFullAddress());
                order.setTotalPrice(totalPrice);
                order.setDiscountedPrice(0);
                order.setPaymentMethod(paymentMethod);
                order.setRecipientName(recipientName);
                order.setRecipientPhone(recipientPhone);
                order.setOrderStatus("Pending");

                int orderId = daoOrder.addOrder(order, cartItems);

                if (orderId > 0) {
                    daoItem.clearCart(customerID);
                    session.removeAttribute("selectedCartItems");
                    session.removeAttribute("totalOrderPrice");
                    response.sendRedirect("order-success.jsp");
                } else {
                    response.sendRedirect("order-fail.jsp");
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

        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
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
