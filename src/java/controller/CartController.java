/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Address;

import entity.Cart;
import entity.CartItem;
import entity.Color;
import entity.Order;
import entity.OrderDetail;
import entity.Product;
import entity.ProductVariant;
import entity.Storage;
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
import model.DAOColor;
import model.DAOStorage;

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
        DAOColor daoColor = new DAOColor();
        DAOStorage daoStorage = new DAOStorage();
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
                        BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
                        BigDecimal price = BigDecimal.valueOf(item.getPrice());
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
                    System.out.println("Vào đến bc 1");
                    Cart cart = dao.getCartByCustomerID(customerID);
                    if (cart != null) {
                        System.out.println("Vào đến bc 2");
                        List<CartItem> cartItems = dao.getCartItemsByCartID1(cart.getCartID());
  
                        request.setAttribute("cartItems", cartItems);
                        request.setAttribute("error", "Bạn chưa chọn sản phẩm nào để thanh toán.");
                        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
                    }
                } else {
                    System.out.println("Vào đến bc 3");
                    BigDecimal totalOrderPrice = BigDecimal.ZERO;
                    List<CartItem> selectedCartItems = new ArrayList<>();
                    Cart cart = dao.getCartByCustomerID(customerID);

                    if (cart != null) {
                        List<CartItem> cartItems = dao.getCartItemsByCartID1(cart.getCartID());
                        System.out.println("Vào đến bc 4");
                        System.out.println("Vào đến bc 4"+cartItems);
                        for (CartItem item : cartItems) {
                             System.out.println("CartItemID: " + item.getCartItemID() + ", ProductVariantID: " + item.getProductVariantID());
                            for (String selectedItemID : selectedItems) {
                                if (Integer.toString(item.getCartItemID()).equals(selectedItemID)) {
                                    System.out.println("in ra "+Integer.toString(item.getCartItemID()).equals(selectedItemID));
                                    
                                    ProductVariant productVariant = daoProVariant.getProductVariantById(item.getProductVariantID());
                                    System.out.println("Product variants"+item.getProductVariantID());
                                    System.out.println("Product variants"+productVariant);
                                    if (productVariant == null) {
                                        System.out.println("Vào đến bc productVariant");
                                        request.setAttribute("error", "Sản phẩm không còn tồn tại.");
                                        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
                                        return;
                                    }

                                    // Kiểm tra tồn kho
                                    String quantityParam = request.getParameter("quantity_" + item.getCartItemID());
                                    System.out.println("quantity"+quantityParam);
                                    int newQuantity = (quantityParam != null) ? Integer.parseInt(quantityParam) : item.getQuantity();

                                    if (newQuantity > productVariant.getStock()) {
                                        System.out.println("Vào đến bc getStock");
                                        request.setAttribute("error", "Sản phẩm " + item.getProduct().getName() + " không đủ hàng trong kho.");
                                        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
                                        return;
                                    }

                                    // Lấy Product
                                    Product product = daoPro.getProductById(productVariant.getProduct_id());
                                    System.out.println("Product"+product);
                                    if (product == null) {
                                        System.out.println("Vào đến bc product");
                                        request.setAttribute("error", "Thông tin sản phẩm không hợp lệ.");
                                        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
                                        return;
                                    }

                                    // Lấy Color
                                    Color color = daoColor.getColorById1(productVariant.getColor_id());
                                    System.out.println("color"+color);
                                    if (color == null) {
                                        System.out.println("Vào đến bc color");
                                        request.setAttribute("error", "Không lấy được thông tin màu sắc.");
                                        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
                                        return;
                                    }

                                    // Lấy Storage
                                    Storage storage = daoStorage.getStorageById1(productVariant.getStorage_id());
                                    if (storage == null) {
                                        System.out.println("Vào đến bc storage");
                                        request.setAttribute("error", "Không lấy được thông tin dung lượng.");
                                        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
                                        return;
                                    }

                                    // Cập nhật thông tin sản phẩm trong cartItem
                                    item.setQuantity(newQuantity);
                                    item.setTotalPrice(BigDecimal.valueOf(item.getPrice()).multiply(BigDecimal.valueOf(newQuantity)));
                                    item.setProduct(product);
                                    item.setProductVariant(productVariant);
                                    item.setColor(color);
                                    item.setStorage(storage);


                                    productVariant.setStock(productVariant.getStock() - newQuantity);
                                    daoProVariant.updateProductVariantStock(productVariant);


                                    totalOrderPrice = totalOrderPrice.add(item.getTotalPrice()).setScale(2, RoundingMode.HALF_UP);
                                    selectedCartItems.add(item);
                                }
                            }
                        }
                    }

                    if (!selectedCartItems.isEmpty()) {
                        request.setAttribute("cartItems", selectedCartItems);
                        request.setAttribute("totalOrderPrice", totalOrderPrice);
                        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
                        session.setAttribute("selectedCartItems", selectedCartItems);
                        System.out.println("Selected CartItem"+selectedCartItems);
                    } else {
                        System.out.println("CartItem Null");
                        request.setAttribute("error", "Bạn chưa chọn sản phẩm nào để thanh toán.");
                        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
                    }
                }
            }

            if (service.equals("add2cart")) {
                int productID = Integer.parseInt(request.getParameter("productID"));
                String colorName = request.getParameter("color");
                String storageCapacity = request.getParameter("storage");
                int quantity = Integer.parseInt(request.getParameter("quantity"));


                int colorID = daoColor.getColorIDByName(colorName);
                if (colorID == -1) {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"status\": \"error\", \"message\": \"Color not found!\"}");
                    return;
                }

                int storageID = daoStorage.getStorageIDByCapacity(storageCapacity);
                if (storageID == -1) {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"status\": \"error\", \"message\": \"Storage capacity not found!\"}");
                    return;
                }

                ProductVariant productVariant = daoProVariant.getProductVariantByDetails(productID, colorID, storageID);
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
                    BigDecimal newQty = BigDecimal.valueOf(newQuantity);
                    BigDecimal totalPrice = price.multiply(newQty).setScale(2, RoundingMode.HALF_UP);

                    existingItem.setQuantity(newQuantity);
                    existingItem.setTotalPrice(totalPrice);
                    daoItem.updateCartItem(existingItem);
                } else {
                    CartItem newItem = new CartItem();
                    newItem.setCartID(cart.getCartID());
                    newItem.setProductVariantID(productVariant.getId());
                    newItem.setQuantity(quantity);
                    newItem.setPrice(productVariant.getPrice());

                    BigDecimal price = BigDecimal.valueOf(productVariant.getPrice());
                    BigDecimal qty = BigDecimal.valueOf(quantity);
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

//            if (service.equals("submitOrder")) {
//                String selectedAddressId = request.getParameter("addressSelect");
//                String recipientName = request.getParameter("newFullName");
//                String recipientPhone = request.getParameter("newPhone");
//                int paymentMethod = Integer.parseInt(request.getParameter("paymentMethod"));
//
//                if (selectedAddressId == null || selectedAddressId.isEmpty()) {
//                    response.sendRedirect("checkout.jsp?error=NoAddressSelected");
//                    return;
//                }
//
//                int addressId = Integer.parseInt(selectedAddressId);
//                Address address = daoAdd.getAddressById(addressId);
//                if (address == null) {
//                    response.sendRedirect("checkout.jsp?error=InvalidAddress");
//                    return;
//                }
//
//                List<CartItem> cartItems = (List<CartItem>) session.getAttribute("selectedCartItems");
//                System.out.println(cartItems);
//                if (cartItems == null || cartItems.isEmpty()) {
//                    response.sendRedirect("checkout.jsp?error=EmptyCart");
//                    return;
//                }
//
//                double totalPrice = (double) session.getAttribute("totalOrderPrice");
//
//                Order order = new Order();
//                order.setBuyerID(customerID);
//                order.setShippingAddress(address.getFullAddress());
//                order.setTotalPrice(totalPrice);
//                order.setDiscountedPrice(0);
//                order.setPaymentMethod(paymentMethod);
//                order.setRecipientName(recipientName);
//                order.setRecipientPhone(recipientPhone);
//                order.setOrderStatus("Pending");
//
//                int orderId = daoOrder.addOrder2(order, cartItems);
//
//                if (orderId > 0) {
//                    daoItem.clearCart(customerID);
//                    session.removeAttribute("selectedCartItems");
//                    session.removeAttribute("totalOrderPrice");
//                    response.sendRedirect("order-success.jsp");
//                } else {
//                    response.sendRedirect("order-fail.jsp");
//                }
//            }
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
