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
import entity.Voucher;
import helper.Authorize;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;

/**
 *
 * @author
 */
@WebServlet(name = "CartController", urlPatterns = {"/CartURL"})
public class CartController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

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

        // Các DAO cần
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
            String service = request.getParameter("service");
            Vector<Product> listpro = daoPro.getLatestProducts();
            request.setAttribute("listpro", listpro);

            List<Address> addresses = daoAdd.getAddressesByUserId(customerID);
            session.setAttribute("userAddresses", addresses);

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

                    // Kiểm tra nếu số lượng mới không hợp lệ
                    if (newQuantity <= 0) {
                        response.setContentType("application/json");
                        response.getWriter().write("{\"status\": \"error\", \"message\": \"Quantity must be a positive integer\"}");
                        return;
                    }

                    // Lấy thông tin CartItem từ cơ sở dữ liệu
                    CartItem cartItem = daoItem.getCartItemById(cartItemId);
                    if (cartItem != null) {
                        // Cập nhật số lượng của CartItem trong giỏ hàng
                        cartItem.setQuantity(newQuantity);

                        // Tính lại giá trị tổng cho sản phẩm này
                        BigDecimal price = BigDecimal.valueOf(cartItem.getPrice());
                        BigDecimal quantity = BigDecimal.valueOf(newQuantity);
                        BigDecimal newTotalPrice = price.multiply(quantity).setScale(2, RoundingMode.HALF_UP); // Nhân và làm tròn

                        // Cập nhật thông tin CartItem trong cơ sở dữ liệu
                        cartItem.setTotalPrice(newTotalPrice);
                        daoItem.updateCartItem(cartItem);

                        // Tính lại tổng giá trị đơn hàng
                        BigDecimal totalOrderPrice = BigDecimal.ZERO;
                        List<CartItem> cartItems = dao.getCartItemsByCartID1(cart.getCartID());
                        for (CartItem item : cartItems) {
                            totalOrderPrice = totalOrderPrice.add(item.getTotalPrice()).setScale(2, RoundingMode.HALF_UP);
                        }

                        // Trả về kết quả thành công với tổng giá trị đơn hàng
                        response.setContentType("application/json");
                        response.getWriter().write("{\"status\": \"success\", \"totalOrderPrice\": \"" + totalOrderPrice + "\"}");
                    } else {
                        // Nếu không tìm thấy CartItem trong giỏ hàng
                        response.setContentType("application/json");
                        response.getWriter().write("{\"status\": \"error\", \"message\": \"Product not found in cart\"}");
                    }
                } catch (NumberFormatException e) {
                    // Xử lý lỗi khi giá trị đầu vào không hợp lệ
                    response.setContentType("application/json");
                    response.getWriter().write("{\"status\": \"error\", \"message\": \"Invalid input format\"}");
                } catch (Exception e) {
                    // Xử lý lỗi bất kỳ trong quá trình cập nhật
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
                    }
                    request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
                    return;
                } else {
                    BigDecimal totalOrderPrice = BigDecimal.ZERO;
                    List<CartItem> selectedCartItems = new ArrayList<>();

                    Cart cart = dao.getCartByCustomerID(customerID);
                    if (cart != null) {
                        List<CartItem> cartItems = dao.getCartItemsByCartID1(cart.getCartID());
                        for (CartItem item : cartItems) {
                            for (String selectedItemID : selectedItems) {
                                if (String.valueOf(item.getCartItemID()).equals(selectedItemID)) {
                                    ProductVariant productVariant = daoProVariant.getProductVariantById(item.getProductVariantID());
                                    if (productVariant == null) {
                                        request.setAttribute("cartItems", selectedCartItems);
                                        request.setAttribute("error", "Sản phẩm không còn tồn tại.");
                                        request.setAttribute("totalOrderPrice", totalOrderPrice);
                                        DAOUserVoucher daoUV = new DAOUserVoucher();
                                        Vector<Voucher> vouchers = daoUV.getRedeemedVouchersByType(user.getId(), 1);
                                        session.setAttribute("vouchers", vouchers);

                                        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
                                        return;
                                    }
                                    String quantityParam = request.getParameter("quantity_" + item.getCartItemID());
                                    int newQuantity = (quantityParam != null)
                                            ? Integer.parseInt(quantityParam)
                                            : item.getQuantity();
                                    if (newQuantity > productVariant.getStock()) {
                                        request.setAttribute("cartItems", selectedCartItems);
                                        request.setAttribute("error", "Sản phẩm " + item.getProduct().getName() + " không đủ hàng trong kho.");
                                        request.setAttribute("totalOrderPrice", totalOrderPrice);

                                        DAOUserVoucher daoUV = new DAOUserVoucher();
                                        Vector<Voucher> vouchers = daoUV.getRedeemedVouchersByType(user.getId(), 1);
                                        session.setAttribute("vouchers", vouchers);

                                        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
                                        return;
                                    }

                                    Product product = daoPro.getProductById(productVariant.getProduct_id());
                                    if (product == null) {
                                        request.setAttribute("cartItems", selectedCartItems);
                                        request.setAttribute("error", "Thông tin sản phẩm không hợp lệ.");
                                        request.setAttribute("totalOrderPrice", totalOrderPrice);
                                        DAOUserVoucher daoUV = new DAOUserVoucher();
                                        Vector<Voucher> vouchers = daoUV.getRedeemedVouchersByType(user.getId(), 1);
                                        session.setAttribute("vouchers", vouchers);

                                        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
                                        return;
                                    }
                                    Color color = daoColor.getColorById1(productVariant.getColor_id());
                                    if (color == null) {
                                        request.setAttribute("cartItems", selectedCartItems);
                                        request.setAttribute("error", "Không lấy được thông tin màu sắc.");
                                        request.setAttribute("totalOrderPrice", totalOrderPrice);

                                        DAOUserVoucher daoUV = new DAOUserVoucher();
                                        Vector<Voucher> vouchers = daoUV.getRedeemedVouchersByType(user.getId(), 1);
                                        session.setAttribute("vouchers", vouchers);

                                        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
                                        return;
                                    }
                                    Storage storage = daoStorage.getStorageById1(productVariant.getStorage_id());
                                    if (storage == null) {
                                        request.setAttribute("cartItems", selectedCartItems);
                                        request.setAttribute("error", "Không lấy được thông tin dung lượng.");
                                        request.setAttribute("totalOrderPrice", totalOrderPrice);

                                        DAOUserVoucher daoUV = new DAOUserVoucher();
                                        Vector<Voucher> vouchers = daoUV.getRedeemedVouchersByType(user.getId(), 1);
                                        session.setAttribute("vouchers", vouchers);

                                        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
                                        return;
                                    }
                                    item.setQuantity(newQuantity);
                                    BigDecimal itemPrice = BigDecimal.valueOf(item.getPrice())
                                            .multiply(BigDecimal.valueOf(newQuantity))
                                            .setScale(2, RoundingMode.HALF_UP);
                                    item.setTotalPrice(itemPrice);
                                    item.setProduct(product);
                                    item.setProductVariant(productVariant);
                                    item.setColor(color);
                                    item.setStorage(storage);
                                    totalOrderPrice = totalOrderPrice.add(itemPrice);
                                    selectedCartItems.add(item);
                                }
                            }
                        }
                    }

                    if (!selectedCartItems.isEmpty()) {
                        session.setAttribute("selectedCartItems", selectedCartItems);
                        session.setAttribute("totalOrderPrice", totalOrderPrice);
                        request.setAttribute("cartItems", selectedCartItems);
                        request.setAttribute("totalOrderPrice", totalOrderPrice);
                        DAOUserVoucher daoUV = new DAOUserVoucher();
                        Vector<Voucher> vouchers = daoUV.getRedeemedVouchersByType(user.getId(), 1);
                        session.setAttribute("vouchers", vouchers);
                        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
                        return;
                    } else {
                        request.setAttribute("error", "Bạn chưa chọn sản phẩm nào để thanh toán.");
                        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
                        return;
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
                    BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(newQuantity)).setScale(2, RoundingMode.HALF_UP);

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
                    BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);

                    newItem.setTotalPrice(totalPrice);
                    daoItem.addCartItem(newItem);
                }
                double totalCartPrice = daoItem.calculateTotalCartPrice(cart.getCartID());
                cart.setTotalPrice(totalCartPrice);
                dao.updateCart(cart);

                response.setContentType("application/json");
                response.getWriter().write("{\"status\": \"success\", \"message\": \"Product added to cart successfully!\"}");
                return;
            }
            
            if (service.equals("addAddress")) {
                try {
                    String address = request.getParameter("address");
                    String district = request.getParameter("district");
                    String city = request.getParameter("city");

                    if (address == null || district == null || city == null
                            || address.trim().isEmpty() || district.trim().isEmpty() || city.trim().isEmpty()) {
                        response.setContentType("application/json");
                        response.getWriter().write("{\"status\": \"error\", \"message\": \"Vui lòng điền đầy đủ thông tin địa chỉ\"}");
                        return;
                    }

                    Address newAddress = new Address();
                    newAddress.setUserId(customerID);
                    newAddress.setAddress(address);
                    newAddress.setDistrict(district);
                    newAddress.setCity(city);

                    int addressId = daoAdd.addAddress(newAddress);

                    if (addressId > 0) {
                        response.setContentType("application/json");
                        response.getWriter().write("{\"status\": \"success\", \"addressId\": \"" + addressId + "\"}");
                    } else {
                        response.setContentType("application/json");
                        response.getWriter().write("{\"status\": \"error\", \"message\": \"Không thể thêm địa chỉ\"}");
                    }
                } catch (Exception e) {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"status\": \"error\", \"message\": \"Lỗi: " + e.getMessage() + "\"}");
                }
                return;
            }
        }
    }

  

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
