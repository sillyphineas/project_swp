
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import entity.Cart;
import entity.CartItem;
import entity.Product;
import java.math.BigDecimal;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class DAOCartItem extends DBConnection {

    public int addCartItem(CartItem cartItem) {
        int n = 0;
        String sql = "INSERT INTO CartItem (CartID, ProductVariantID, Price, Quantity, TotalPrice, isDisabled) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, cartItem.getCartID());
            pre.setInt(2, cartItem.getProductVariantID());
            pre.setDouble(3, cartItem.getPrice());
            pre.setInt(4, cartItem.getQuantity());
            pre.setBigDecimal(5, cartItem.getTotalPrice());
            pre.setBoolean(6, cartItem.isIsDisabled());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public CartItem getCartItemById(int id) {
        String sql = "SELECT * FROM CartItem WHERE CartItemID = ?";
        CartItem cartItem = null;
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                cartItem = new CartItem(
                        rs.getInt("CartItemID"),
                        rs.getInt("CartID"),
                        rs.getInt("ProductVariantID"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"),
                        rs.getDouble("DiscountAmount"),
                        rs.getBigDecimal("TotalPrice"),
                        rs.getBoolean("isDisabled")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cartItem;
    }

    public Vector<CartItem> getCartItem(String sql) {
        Vector<CartItem> vector = new Vector<>();
        try {
            Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                CartItem cartItem = new CartItem(rs.getInt("CartItemID"),
                        rs.getInt("CartID"),
                        rs.getInt("ProductID"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"),
                        rs.getDouble("DiscountAmount"),
                        rs.getBigDecimal("TotalPrice"),
                        rs.getBoolean("isDisabled"));
                vector.add(cartItem);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public int delete(int id) {
        int n = 0;
        String sql = "DELETE FROM CartItem WHERE CartItemID = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, id);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public List<CartItem> getCartDetails(int customerId) throws SQLException {
        String sql = """
        SELECT ci.CartItemID, ci.ProductID, p.Title AS ProductTitle, ci.Price, 
               ci.Quantity, ci.TotalPrice, c.TotalPrice AS CartTotal, c.CartStatus
        FROM CartItem ci
        JOIN Cart c ON ci.CartID = c.CartID
        JOIN Products p ON ci.ProductID = p.id
        WHERE c.CustomerID = ? AND ci.isDisabled = 0 AND c.CartStatus = 'Pending'
    """;
        List<CartItem> cartItems = new ArrayList<>();
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, customerId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                cartItems.add(new CartItem(
                        rs.getInt("CartItemID"),
                        rs.getInt("ProductID"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"),
                        rs.getBigDecimal("TotalPrice")
                ));
            }
        }
        return cartItems;
    }

    public void removeCartItem(int cartItemId) throws SQLException {
        String sql = "DELETE FROM CartItem WHERE CartItemID = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, cartItemId);
            pre.executeUpdate();
        }
    }

    public boolean updateCartItemQuantity(CartItem cartItem, int newQuantity) {
        String sql = "UPDATE CartItems SET quantity = ? WHERE id = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {

            pre.setInt(1, newQuantity);
            pre.setInt(2, cartItem.getCartItemID());
            return pre.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public CartItem getCartItemByCartIDAndProductID(int cartID, int productID) {
        CartItem cartItem = null;

        String sql = "SELECT "
                + "    ci.CartItemID, "
                + "    ci.CartID, "
                + "    ci.ProductID, "
                + "    ci.Quantity, "
                + "    ci.Price AS CartItemPrice, "
                + "    ci.DiscountAmount, "
                + "    ci.TotalPrice, "
                + "    ci.isDisabled, "
                + "    p.id, "
                + "    p.brandID, "
                + "    p.name, "
                + "    p.price AS ProductPrice, "
                + "    p.stock, "
                + "    p.description, "
                + "    p.isDisabled AS ProductDisabled, "
                + "    p.feedbackCount, "
                + "    p.status, "
                + "    p.imageURL, "
                + "    p.chipset, "
                + "    p.ram, "
                + "    p.storage, "
                + "    p.screenSize, "
                + "    p.screenType, "
                + "    p.resolution, "
                + "    p.batteryCapacity, "
                + "    p.cameraSpecs, "
                + "    p.os, "
                + "    p.simType, "
                + "    p.connectivity "
                + "FROM CartItem ci "
                + "INNER JOIN Products p ON ci.ProductID = p.id "
                + "WHERE ci.CartID = ? AND ci.ProductID = ? AND ci.isDisabled = 0";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartID);
            ps.setInt(2, productID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cartItem = new CartItem();

                    // Thiết lập thông tin CartItem
                    cartItem.setCartItemID(rs.getInt("CartItemID"));
                    cartItem.setCartID(rs.getInt("CartID"));
                    cartItem.setProductVariantID(rs.getInt("ProductID"));
                    cartItem.setQuantity(rs.getInt("Quantity"));
                    cartItem.setPrice(rs.getDouble("CartItemPrice"));
                    cartItem.setDiscountAmount(rs.getDouble("DiscountAmount"));
                    cartItem.setTotalPrice(rs.getBigDecimal("TotalPrice"));
                    cartItem.setDisabled(rs.getBoolean("isDisabled"));

                    // Tạo và thiết lập thông tin Product
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setBrandID(rs.getInt("brandID"));
                    product.setName(rs.getString("name"));

                    product.setDescription(rs.getString("description"));
                    product.setIsDisabled(rs.getBoolean("ProductDisabled"));
                    product.setFeedbackCount(rs.getInt("feedbackCount"));
                    product.setStatus(rs.getString("status"));
                    product.setImageURL(rs.getString("imageURL"));
                    product.setChipset(rs.getString("chipset"));
                    product.setRam(rs.getInt("ram"));

                    product.setScreenSize(rs.getDouble("screenSize"));
                    product.setScreenType(rs.getString("screenType"));
                    product.setResolution(rs.getString("resolution"));
                    product.setBatteryCapacity(rs.getInt("batteryCapacity"));
                    product.setCameraSpecs(rs.getString("cameraSpecs"));
                    product.setOs(rs.getString("os"));
                    product.setSimType(rs.getString("simType"));
                    product.setConnectivity(rs.getString("connectivity"));

                    // Gán product vào cartItem
                    cartItem.setProduct(product);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thông tin CartItem và Product: " + e.getMessage());
            e.printStackTrace();
        }

        return cartItem;
    }

    public int deleteCartItemByCustomerIdAndVariantId(int customerId, int productVariantId) {
        int result = 0;
        String sql = "DELETE FROM CartItem WHERE customer_id = ? AND product_variant_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.setInt(2, productVariantId);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public CartItem getCartItemByCartIDAndProductVariantID(int cartID, int productVariantID) {
        CartItem cartItem = null;
        String sql = "SELECT * FROM CartItem WHERE CartID = ? AND ProductVariantID = ? AND isDisabled = 0 LIMIT 1";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, cartID);
            statement.setInt(2, productVariantID);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cartItem = new CartItem();
                    cartItem.setCartItemID(resultSet.getInt("CartItemID"));
                    cartItem.setCartID(resultSet.getInt("CartID"));
                    cartItem.setProductVariantID(resultSet.getInt("ProductVariantID"));
                    cartItem.setPrice(resultSet.getDouble("Price"));
                    cartItem.setQuantity(resultSet.getInt("Quantity"));
                    cartItem.setTotalPrice(resultSet.getBigDecimal("TotalPrice"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return cartItem;
    }

    public double calculateTotalCartPrice(int cartID) {
        double total = 0.0;
        String query = "SELECT COALESCE(SUM(TotalPrice), 0) FROM CartItem WHERE CartID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, cartID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    public double getTotalPrice(int userId) {
        double totalPrice = 0;
        String sql = "SELECT SUM(price * quantity) FROM CartItem WHERE CartID IN (SELECT CartID FROM Cart WHERE CustomerID = ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalPrice = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalPrice;
    }

    public List<CartItem> getCartItemsByUserId(int userId) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT ci.CartItemID, ci.CartID, ci.ProductVariantID, ci.Price, ci.Quantity, ci.DiscountAmount, ci.TotalPrice, ci.isDisabled "
                + "FROM CartItem ci "
                + "JOIN Cart c ON ci.CartID = c.CartID "
                + "WHERE c.CustomerID = ?";

        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CartItem item = new CartItem(
                        rs.getInt("CartItemID"),
                        rs.getInt("CartID"),
                        rs.getInt("ProductVariantID"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"),
                        rs.getDouble("DiscountAmount"),
                        rs.getBigDecimal("TotalPrice"),
                        rs.getBoolean("isDisabled")
                );
                cartItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    public void clearCart(int userId) {
        String sql = "DELETE FROM CartItem WHERE CartID IN (SELECT CartID FROM Cart WHERE CustomerID = ?)";

        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int updateCartItem(CartItem other) {
        int n = 0;
        String sql = "UPDATE CartItem "
                + "SET CartID = ?, "
                + "    ProductVariantID = ?, "
                + "    Price = ?, "
                + "    Quantity = ?, "
                + "    DiscountAmount = ?, "
                + "    TotalPrice = ?, "
                + "    isDisabled = ? "
                + "WHERE CartItemID = ?";

        try (PreparedStatement pre = conn.prepareStatement(sql)) {

            pre.setInt(1, other.getCartID());
            pre.setInt(2, other.getProductVariantID());
            pre.setDouble(3, other.getPrice());
            pre.setInt(4, other.getQuantity());
            pre.setDouble(5, other.getDiscountAmount());
            pre.setBigDecimal(6, other.getTotalPrice());
            pre.setBoolean(7, other.isIsDisabled());
            pre.setInt(8, other.getCartItemID());

            // Thực thi câu lệnh SQL
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public static void main(String[] args) {
        DAOCartItem dao = new DAOCartItem();
        DAOCart dAOCart = new DAOCart();
//        System.out.println(dao.calculateTotalCartPrice(1));
//        System.out.println(dao.delete(30));
        Cart cart = dAOCart.getCartByCustomerID(4);
//       CartItem cartItem = new CartItem(54, 1, 1, 29990000.00, 5, 0.00, 89970000.00, false);
//        System.out.println(dao.addCartItem(cartItem));
//      System.out.println(dao.updateCartItem(cartItem));
//        System.out.println(dao.getCartItemByCartIDAndProductVariantID(1, 10));
        //      System.out.println(dao.getCartItemById(13));
//       System.out.println(dao.updateCartItem(cartItem));
//        System.out.println(dao.getCartItemById(1));
//        CartItem cartItem = new CartItem();
//        cartItem.setCartItemID(1);
//        cartItem.setCartID(5);
//        cartItem.setProductID(1);
//        cartItem.setPrice(200.0);
//        cartItem.setQuantity(2);
//        cartItem.setDiscountAmount(10.0);
//        cartItem.setTotalPrice(390.0);
//        cartItem.setDisabled(false);
//        cartItem.setProduct(new Product(1, // id
//                1, // brandID
//                "Galaxy S23 Ultra", // name
//                999, // price
//                100, // stock
//                "Latest iPhone model", // description
//                false, // isDisabled
//                100, // feedbackCount
//                "Available", // status
//                "https://example.com/iphone14.jpg", // imageURL
//                "A15 Bionic", // chipset
//                6, // ram
//                128, // storage
//                6.1, // screenSize
//                "OLED", // screenType
//                "1170 x 2532 pixels", // resolution
//                3240, // batteryCapacity
//                "12MP Dual Camera", // cameraSpecs
//                "iOS", // os
//                "Nano-SIM", // simType
//                "5G"));
//
        //       System.out.println(dao.addCartItem(cartItem));
//        System.out.println(dao.updateCartItem(cartItem));
//        System.out.println(dao.getCartItemById(1));
//        Cart cart = dAOCart.getCartByCustomerID(4);
//        System.out.println(dao.calculateTotalCartPrice(cart.getCartID()));
//        System.out.println(dao.delete(18));

    }
}
