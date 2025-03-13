/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import entity.Cart;
import entity.CartItem;
import entity.Color;
import entity.Order;
import entity.OrderDetail;
import entity.Product;
import entity.ProductVariant;
import entity.Storage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class DAOCart extends DBConnection {

    public int addCart(Cart other) {
        int n = 0;
        String sql = "INSERT INTO Cart (CustomerID, CartStatus, TotalPrice, CreatedAt, UpdatedAt) VALUES (?, ?, ?, NOW(), NOW())";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, other.getCustomerID());
            pre.setString(2, other.getCartStatus());
            pre.setDouble(3, other.getTotalPrice());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public int updateCart(Cart other) {
        int n = 0;
        String sql = "UPDATE Cart "
                + "SET CustomerID = ?, "
                + "    CartStatus = ?, "
                + "    TotalPrice = ?, "
                + "    CreatedAt = ?, "
                + "    UpdatedAt = ? "
                + "WHERE CartID = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, other.getCustomerID());
            pre.setString(2, other.getCartStatus());
            pre.setDouble(3, other.getTotalPrice());
            pre.setString(4, other.getCreatedAt());
            pre.setString(5, other.getUpdatedAt());
            pre.setInt(6, other.getCartID());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public Cart getCartById(int id) {
        String sql = "Select * From Cart where CartID = ?";
        Cart cart = null;
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                cart = new Cart(rs.getInt("CartID"),
                        rs.getInt("CustomerID"),
                        rs.getString("CartStatus"),
                        rs.getDouble("TotalPrice"),
                        rs.getString("CreatedAt"),
                        rs.getString("UpdatedAt"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cart;
    }

    public Vector<Cart> getCart(String sql) {
        Vector<Cart> vector = new Vector<>();
        try {
            Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Cart cart = new Cart(rs.getInt("CartID"),
                        rs.getInt("CustomerID"),
                        rs.getString("CartStatus"),
                        rs.getDouble("TotalPrice"),
                        rs.getString("CreatedAt"),
                        rs.getString("UpdatedAt"));
                vector.add(cart);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public void changeStatus(String newvalue, int id) {
        String sql = "UPDATE [dbo].[Cart] SET [CartStatus] = " + newvalue + " WHERE CartID =" + id;
        try {
            Statement state = conn.createStatement();
            state.execute(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int deleteCart(int id) {
        int n = 0;
        String sqlCheck = "SELECT * FROM CartItem WHERE CartID = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(sqlCheck)) {
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                changeStatus("Null", id);
                return 0;
            }

            String sql = "DELETE FROM Cart WHERE CartID = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(sql)) {
                deleteStmt.setInt(1, id);
                n = deleteStmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public int delete(int id) {
        int n = 0;
        String sql = "DELETE FROM [dbo].[Cart]\n"
                + "      WHERE CartID = ? ";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public Cart getCartByCustomerID(int customerID) {
        Cart cart = null;
        String sql = "SELECT * FROM Cart WHERE CustomerID = ? AND CartStatus = 'Active'";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, customerID);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                cart = new Cart();
                cart.setCartID(rs.getInt("CartID"));
                cart.setCustomerID(rs.getInt("CustomerID"));
                cart.setCartStatus(rs.getString("CartStatus"));
                cart.setTotalPrice(rs.getDouble("TotalPrice"));
                cart.setCreatedAt(rs.getString("CreatedAt"));
                cart.setUpdatedAt(rs.getString("UpdatedAt"));
                cart.setCartItems(getCartItemsByCartID1(cart.getCartID()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cart;
    }

    public List<CartItem> getCartItemsByCartID1(int cartID) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT ci.CartItemID AS CartItemID, ci.CartID, ci.ProductVariantID, ci.Quantity, ci.Price, ci.TotalPrice, "
                + "pv.product_id, p.name AS productName, p.imageURL, "
                + "pv.color_id, c.colorName, pv.storage_id, pv.stock, s.capacity "
                + "FROM cartitem ci "
                + "JOIN productvariants pv ON ci.ProductVariantID = pv.id "
                + "JOIN products p ON pv.product_id = p.id "
                + "JOIN colors c ON pv.color_id = c.id "
                + "JOIN storages s ON pv.storage_id = s.id "
                + "WHERE ci.CartID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCartItemID(rs.getInt("CartItemID"));
                item.setCartID(rs.getInt("CartID"));
                item.setProductVariantID(rs.getInt("ProductVariantID")); 
                item.setQuantity(rs.getInt("Quantity"));
                item.setPrice(rs.getDouble("Price"));
                item.setTotalPrice(rs.getBigDecimal("TotalPrice"));

                
                ProductVariant prova = new ProductVariant();
                prova.setId(rs.getInt("ProductVariantID")); 
                prova.setStock(rs.getInt("stock"));
                item.setProductVariant(prova);

                
                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("productName"));
                product.setImageURL(rs.getString("imageURL"));
                item.setProduct(product);

                
                Color color = new Color();
                color.setId(rs.getInt("color_id"));
                color.setColorName(rs.getString("colorName"));
                item.setColor(color);

                
                Storage storage = new Storage();
                storage.setId(rs.getInt("storage_id"));
                storage.setCapacity(rs.getString("capacity"));
                item.setStorage(storage);

                cartItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    public List<CartItem> getCartItemsByCartID(int cartID, int page, int pageSize) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT ci.CartItemID AS CartItemID, ci.CartID, ci.ProductVariantID, ci.Quantity, ci.Price, ci.TotalPrice, "
                + "pv.product_id, p.name AS productName, p.imageURL, "
                + // Thêm p.imageURL
                "pv.color_id, c.colorName, pv.storage_id,pv.stock, s.capacity "
                + "FROM cartitem ci "
                + "JOIN productvariants pv ON ci.ProductVariantID = pv.id "
                + "JOIN products p ON pv.product_id = p.id "
                + "JOIN colors c ON pv.color_id = c.id "
                + "JOIN storages s ON pv.storage_id = s.id "
                + "WHERE CartID = ? LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartID);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, (page - 1) * pageSize);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCartItemID(rs.getInt("CartItemID"));
                item.setQuantity(rs.getInt("Quantity"));
                item.setPrice(rs.getDouble("Price"));
                item.setTotalPrice(rs.getBigDecimal("TotalPrice"));

                ProductVariant prova = new ProductVariant();
                prova.setId(rs.getInt("ProductVariantID"));
                prova.setStock(rs.getInt("stock"));
                item.setProductVariant(prova);
                // Set product
                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("productName"));
                product.setImageURL(rs.getString("imageURL")); // Gán imageURL
                item.setProduct(product);

                // Set color
                Color color = new Color();
                color.setId(rs.getInt("color_id"));
                color.setColorName(rs.getString("colorName"));
                item.setColor(color);

                // Set storage
                Storage storage = new Storage();
                storage.setId(rs.getInt("storage_id"));
                storage.setCapacity(rs.getString("capacity"));
                item.setStorage(storage);

                cartItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    public void deleteCartItem(int cartItemID) {
        String sql = "UPDATE CartItem SET isDisabled = true WHERE CartItemID = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql);) {
            pre.setInt(1, cartItemID);
            pre.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("true");
    }

    public void updateCartItemQuantity(CartItem cartItem, int newQuantity) {
        String sql = "UPDATE CartItem SET Quantity = ?, TotalPrice = ? WHERE CartItemID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            double updatedTotalPrice = cartItem.getPrice() * newQuantity;

            stmt.setInt(1, newQuantity);
            stmt.setDouble(2, updatedTotalPrice);
            stmt.setInt(3, cartItem.getCartItemID());

            stmt.executeUpdate();  // Thực thi câu lệnh UPDATE
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCartItemsByCartID(int cartID) {
        String sql = "DELETE FROM CartItems WHERE cartID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCartStatus(int cartID, String status) {
        String query = "UPDATE Cart SET status = ? WHERE cartID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, cartID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DAOCart dao = new DAOCart();
        System.out.println(dao.getCartItemsByCartID1(1));
//        Cart cart = new Cart(1, 4, "active", 0.00, "2025-02-23", "2025-02-23");
//        System.out.println(dao.updateCart(cart));
//        System.out.println(dao.getCartByCustomerID(4));
//        Cart cart = dao.getCartByCustomerID(8);
//        System.out.println(cart);
//        List<CartItem> list = dao.getCartItemsByCartID(cart.getCartID());
//        for (CartItem cartItem : list) {
//            System.out.println(cartItem);
//        }
//  
//        Cart cart = new Cart();
//        cart.setCartID(1); // ID của giỏ hàng
//        cart.setCustomerID(4); // ID của khách hàng
//        cart.setCartStatus("Active"); // Trạng thái của giỏ hàng
//        cart.setTotalPrice(250.75); // Tổng giá trị giỏ hàng
//        cart.setCreatedAt("2025-02-05 10:30:00"); // Thời gian tạo giỏ hàng
//        cart.setUpdatedAt("2025-02-05 11:00:00"); // Thời gia
//        System.out.println(dao.addCart(cart));

    }
}
