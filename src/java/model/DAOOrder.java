/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import entity.CartItem;
import entity.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class DAOOrder extends DBConnection {

    public int addOrder(Order order) {
        String sql = "INSERT INTO Orders (buyerID, status, orderTime, orderStatus, ShippingDate, ShippingAddress, "
                + "totalPrice, discountedPrice, paymentMethod, isDisabled, voucherID) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getBuyerID());
            ps.setByte(2, order.getStatus());
            ps.setTimestamp(3, new Timestamp(order.getOrderTime().getTime())); // Chuyển đổi `java.util.Date` thành `Timestamp`
            ps.setString(4, order.getOrderStatus());

            if (order.getShippingDate() != null) {
                ps.setDate(5, new java.sql.Date(order.getShippingDate().getTime()));
            } else {
                ps.setNull(5, java.sql.Types.DATE);
            }

            ps.setString(6, order.getShippingAddress());
            ps.setDouble(7, order.getTotalPrice());
            ps.setDouble(8, order.getDiscountedPrice());
            ps.setInt(9, order.getPaymentMethod());
            ps.setBoolean(10, order.isDisabled());

            // Xử lý voucherID (nếu có)
            if (order.getVoucherID() != null) {
                ps.setInt(11, order.getVoucherID());
            } else {
                ps.setNull(11, java.sql.Types.INTEGER);
            }

            // Thực thi truy vấn
            int affectedRows = ps.executeUpdate();

            // Lấy ID của đơn hàng vừa tạo
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Trả về -1 nếu có lỗi
    }

    public int addOrder(Order order, List<CartItem> cartItems) {
        int orderId = -1;
        String sqlOrder = "INSERT INTO Orders (buyerID, status, orderTime, orderStatus, ShippingAddress, totalPrice, discountedPrice, paymentMethod, isDisabled) "
                + "VALUES (?, ?, NOW(), ?, ?, ?, ?, ?, 0)";
        String sqlOrderDetails = "INSERT INTO OrderDetails (orderID, productID, quantity, productPrice) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmtOrder = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement stmtOrderDetails = conn.prepareStatement(sqlOrderDetails)) {
            conn.setAutoCommit(false); 
            stmtOrder.setInt(1, order.getBuyerID());
            stmtOrder.setInt(2, 1);
            stmtOrder.setString(3, "Pending");
            stmtOrder.setString(4, order.getShippingAddress());
            stmtOrder.setDouble(5, order.getTotalPrice());
            stmtOrder.setDouble(6, order.getDiscountedPrice());
            stmtOrder.setInt(7, order.getPaymentMethod());

            if (stmtOrder.executeUpdate() > 0) {
                ResultSet rs = stmtOrder.getGeneratedKeys();
                if (rs.next()) {
                    orderId = rs.getInt(1);
                }
            }
            if (orderId > 0) {
                for (CartItem item : cartItems) {
                    stmtOrderDetails.setInt(1, orderId);
                    stmtOrderDetails.setInt(2, item.getProductVariant().getProductID());
                    stmtOrderDetails.setInt(3, item.getQuantity());
                    stmtOrderDetails.setDouble(4, item.getProductVariant().getPrice());
                    stmtOrderDetails.addBatch();
                }
                stmtOrderDetails.executeBatch();
                conn.commit();
            } else {
                conn.rollback();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderId;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                int buyerID = rs.getInt("buyerID");
                byte status = rs.getByte("status");
                Timestamp orderTime = rs.getTimestamp("orderTime");
                String orderStatus = rs.getString("orderStatus");
                Date shippingDate = rs.getDate("shippingDate");
                String shippingAddress = rs.getString("shippingAddress");
                double totalPrice = rs.getDouble("totalPrice");
                double discountedPrice = rs.getDouble("discountedPrice");
                int paymentMethod = rs.getByte("paymentMethod");
                boolean isDisabled = rs.getBoolean("isDisabled");
                String message = rs.getString("message");
                String RecipientName = rs.getString("RecipientName");
                String RecipientPhone = rs.getString("RecipientPhone");
                Integer voucherID = rs.getInt("voucherID");

                orders.add(new Order(id, buyerID, status, orderTime, orderStatus, shippingDate, shippingAddress, totalPrice, discountedPrice, paymentMethod, isDisabled, voucherID, message, RecipientName, RecipientPhone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Get order by ID
    public Order getOrderById(int orderId) {
        Order order = null;
        String sql = "SELECT * FROM Orders WHERE id = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, orderId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                int buyerID = rs.getInt("buyerID");
                byte status = rs.getByte("status");
                Timestamp orderTime = rs.getTimestamp("orderTime");
                String orderStatus = rs.getString("orderStatus");
                Date shippingDate = rs.getDate("shippingDate");
                String shippingAddress = rs.getString("shippingAddress");
                double totalPrice = rs.getDouble("totalPrice");
                double discountedPrice = rs.getDouble("discountedPrice");
                int paymentMethod = rs.getByte("paymentMethod");
                boolean isDisabled = rs.getBoolean("isDisabled");
                Integer voucherID = rs.getInt("voucherID");
                String message = rs.getString("message");
                String RecipientName = rs.getString("RecipientName");
                String RecipientPhone = rs.getString("RecipientPhone");

                order = new Order(id, buyerID, status, orderTime, orderStatus, shippingDate, shippingAddress, totalPrice, discountedPrice, paymentMethod, isDisabled, voucherID, message, RecipientName, RecipientPhone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    // Update an order
    public int updateOrder(Order order) {
        int result = 0;
        String sql = "UPDATE Orders SET buyerID = ?, status = ?, orderTime = ?, orderStatus = ?, shippingDate = ?, "
                + "shippingAddress = ?, totalPrice = ?, discountedPrice = ?, paymentMethod = ?, isDisabled = ?, voucherID = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, order.getBuyerID());
            preparedStatement.setByte(2, order.getStatus());
            preparedStatement.setTimestamp(3, new Timestamp(order.getOrderTime().getTime()));
            preparedStatement.setString(4, order.getOrderStatus());
            preparedStatement.setDate(5, order.getShippingDate() != null ? new java.sql.Date(order.getShippingDate().getTime()) : null);
            preparedStatement.setString(6, order.getShippingAddress());
            preparedStatement.setDouble(7, order.getTotalPrice());
            preparedStatement.setDouble(8, order.getDiscountedPrice());
            preparedStatement.setInt(9, order.getPaymentMethod());
            preparedStatement.setBoolean(10, order.isDisabled());
            preparedStatement.setInt(11, order.getVoucherID() != null ? order.getVoucherID() : null);
            preparedStatement.setInt(12, order.getId());

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Delete an order
    public int deleteOrder(int orderId) {
        int result = 0;
        String sql = "DELETE FROM Orders WHERE id = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, orderId);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Test the DAOOrder
    public static void main(String[] args) {
//        DAOOrder daoOrder = new DAOOrder();
//        Date cuDate = new Date(2025, 12, 11);
//        int x = daoOrder.addOrder(order);
//        System.out.println(x);

//        // Add a new order
//        Order order = new Order(0, 1, (byte) 1, new Date(), "Processing", null, "123 Street, City", 500.00, 450.00, (byte) 1, false, null);
//        daoOrder.addOrder(order);
//
////        // Get all orders
//        List<Order> orders = daoOrder.getAllOrders();
//        for (Order o : orders) {
//            System.out.println(o);
//        }
//
//        // Update an order
//        order.setStatus((byte) 2);
//        daoOrder.updateOrder(order);
//
//        // Get order by ID
//        Order retrievedOrder = daoOrder.getOrderById(1);
//        System.out.println("Retrieved Order: " + retrievedOrder);
//
//        // Delete an order
//        daoOrder.deleteOrder(1);
    }
}
