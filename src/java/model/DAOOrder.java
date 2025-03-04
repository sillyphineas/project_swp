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
        String sql = "INSERT INTO Orders (buyerID, orderTime, orderStatus, ShippingDate, ShippingAddress, "
                + "totalPrice, discountedPrice, paymentMethod, isDisabled, voucherID, "
                + "recipientName, recipientPhone, AssignedSaleId) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getBuyerID());
            ps.setTimestamp(2, new Timestamp(order.getOrderTime().getTime()));
            ps.setString(3, order.getOrderStatus());

            if (order.getShippingDate() != null) {
                ps.setDate(4, new java.sql.Date(order.getShippingDate().getTime()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }

            ps.setString(5, order.getShippingAddress());
            ps.setDouble(6, order.getTotalPrice());
            ps.setDouble(7, order.getDiscountedPrice());
            ps.setInt(8, order.getPaymentMethod());
            ps.setBoolean(9, order.isDisabled());
            if (order.getVoucherID() != null) {
                ps.setInt(10, order.getVoucherID());
            } else {
                ps.setNull(10, java.sql.Types.INTEGER);
            }

            ps.setString(11, order.getRecipientName() != null ? order.getRecipientName() : "Unknown");
            ps.setString(12, order.getRecipientPhone() != null ? order.getRecipientPhone() : "0000000000");
            ps.setInt(13, order.getAssignedSaleId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int orderId = generatedKeys.getInt(1);
                        order.setId(orderId);  // Gán OrderID vừa sinh cho đối tượng order
                        return orderId;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
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
                int assignedSaleId = rs.getInt("AssignedSaleId");
                orders.add(new Order(id, buyerID, orderTime, orderStatus, shippingDate, shippingAddress, totalPrice, discountedPrice, paymentMethod, isDisabled, voucherID, message, RecipientName, RecipientPhone, assignedSaleId));
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
                int assignedSaleId = rs.getInt("AssignedSaleId");
                order = new Order(id, buyerID, orderTime, orderStatus, shippingDate, shippingAddress, totalPrice, discountedPrice, paymentMethod, isDisabled, voucherID, message, RecipientName, RecipientPhone, assignedSaleId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    // Update an order
    public int updateOrder(Order order) {
        int result = 0;
        String sql = "UPDATE Orders SET buyerID = ?, orderTime = ?, orderStatus = ?, shippingDate = ?, "
                + "shippingAddress = ?, totalPrice = ?, discountedPrice = ?, paymentMethod = ?, isDisabled = ?, voucherID = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, order.getBuyerID());
            preparedStatement.setTimestamp(2, new Timestamp(order.getOrderTime().getTime()));
            preparedStatement.setString(3, order.getOrderStatus());
            preparedStatement.setDate(4, order.getShippingDate() != null ? new java.sql.Date(order.getShippingDate().getTime()) : null);
            preparedStatement.setString(5, order.getShippingAddress());
            preparedStatement.setDouble(6, order.getTotalPrice());
            preparedStatement.setDouble(7, order.getDiscountedPrice());
            preparedStatement.setInt(8, order.getPaymentMethod());
            preparedStatement.setBoolean(9, order.isDisabled());
            preparedStatement.setInt(10, order.getVoucherID() != null ? order.getVoucherID() : null);
            preparedStatement.setInt(11, order.getId());

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

    public void updateOrderStatus(String txnRef, String status) {
        String sql = "UPDATE Orders SET orderStatus = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setLong(2, Long.parseLong(txnRef)); // Chuyển đổi txnRef sang long

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Order " + txnRef + " updated to status: " + status);
            } else {
                System.out.println("Failed to update order status for txnRef: " + txnRef);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Lỗi chuyển đổi số: " + e.getMessage());
        }
    }

    // Test the DAOOrder
    public static void main(String[] args) {

    }
}
