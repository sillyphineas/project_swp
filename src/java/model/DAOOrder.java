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

    public List<Order> getOrdersForShipper(int shipperId, String statusFilter, String searchQuery, int page, int pageSize) {
        List<Order> orders = new ArrayList<>();
        int offset = (page - 1) * pageSize; // Tính toán offset cho phân trang

        String sql = "SELECT o.id AS orderId, o.buyerID, u.name AS buyerName, u.phoneNumber AS buyerPhone, v.VoucherCode, "
                + "o.status AS orderStatus, o.shippingAddress, o.orderTime, o.totalPrice, o.discountedPrice, o.paymentMethod, "
                + "o.shippingDate, o.RecipientName, o.RecipientPhone, o.AssignedSaleId "
                + "FROM Orders o "
                + "JOIN Users u ON o.buyerID = u.id "
                + "LEFT JOIN Vouchers v ON o.voucherID = v.VoucherID "
                + "WHERE o.isDisabled = 0 "
                + "AND (o.status LIKE ? OR u.name LIKE ? OR o.id LIKE ?) "
                + "AND EXISTS (SELECT 1 FROM Shipping s WHERE s.OrderID = o.id AND s.ShipperID = ?) "
                + "LIMIT ? OFFSET ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + statusFilter + "%"); // Lọc theo trạng thái
            ps.setString(2, "%" + searchQuery + "%"); // Tìm kiếm theo tên khách hàng hoặc mã đơn hàng
            ps.setString(3, "%" + searchQuery + "%"); // Tìm kiếm theo mã đơn hàng
            ps.setInt(4, shipperId); // Thông tin shipper
            ps.setInt(5, pageSize);  // Số lượng đơn hàng mỗi trang
            ps.setInt(6, offset);     // Offset cho phân trang

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("orderId");
                int buyerId = rs.getInt("buyerID");
                String buyerName = rs.getString("buyerName");
                String voucherCode = rs.getString("VoucherCode");
                String orderStatus = rs.getString("orderStatus");
                String shippingAddress = rs.getString("shippingAddress");
                Timestamp orderTime = rs.getTimestamp("orderTime");
                double totalPrice = rs.getDouble("totalPrice");
                double discountedPrice = rs.getDouble("discountedPrice");
                int paymentMethod = rs.getInt("paymentMethod");
                Date shippingDate = rs.getDate("shippingDate");
                String recipientName = rs.getString("RecipientName");
                String recipientPhone = rs.getString("RecipientPhone");
                int assignedSaleId = rs.getInt("AssignedSaleId");
                // Tạo đối tượng Order từ dữ liệu trong ResultSet và thêm vào danh sách
                Order order = new Order(orderId, buyerId, orderTime, orderStatus, shippingDate, shippingAddress, totalPrice, discountedPrice, paymentMethod, false, null, recipientName, recipientPhone, assignedSaleId);
                // Bạn có thể thêm logic để xử lý voucherCode nếu cần
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public int getTotalOrdersForShipper(int shipperId, String statusFilter, String searchQuery) {
        String sql = "SELECT COUNT(*) FROM Orders o "
                + "JOIN Users u ON o.buyerID = u.id "
                + "LEFT JOIN Vouchers v ON o.voucherID = v.VoucherID "
                + "WHERE o.isDisabled = 0 "
                + "AND (o.status LIKE ? OR u.name LIKE ? OR o.id LIKE ?) "
                + "AND EXISTS (SELECT 1 FROM Shipping s WHERE s.OrderID = o.id AND s.ShipperID = ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + statusFilter + "%");
            ps.setString(2, "%" + searchQuery + "%");
            ps.setString(3, "%" + searchQuery + "%");
            ps.setInt(4, shipperId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean updateStatus(int orderId, int newStatus) {
        String sql = "UPDATE Orders SET status = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newStatus); // Sử dụng giá trị trạng thái kiểu int
            ps.setInt(2, orderId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Test the DAOOrder
    public static void main(String[] args) {
        // Khởi tạo DAOOrder
        DAOOrder daoOrder = new DAOOrder();

        // Các tham số cần thiết
        int shipperId = 2; // Thử với shipper ID đã có trong cơ sở dữ liệu
        String statusFilter = ""; // Không lọc trạng thái, lấy tất cả
        String searchQuery = ""; // Tìm kiếm tất cả đơn hàng
        int page = 1; // Trang đầu tiên
        int pageSize = 10; // Lấy 10 đơn hàng mỗi trang

        // Lấy danh sách đơn hàng cho shipper
        List<Order> orders = daoOrder.getOrdersForShipper(shipperId, statusFilter, searchQuery, page, pageSize);

        // Kiểm tra nếu không có đơn hàng
        if (orders.isEmpty()) {
            System.out.println("No orders found for the given criteria.");
        } else {
            // In ra kết quả
            for (Order order : orders) {
                System.out.println("Order ID: " + order.getId());
                System.out.println("Buyer ID: " + order.getBuyerID());
                System.out.println("Order Status: " + order.getOrderStatus());
                System.out.println("Shipping Address: " + order.getShippingAddress());
                System.out.println("Total Price: " + order.getTotalPrice());
                System.out.println("Discounted Price: " + order.getDiscountedPrice());
                System.out.println("Payment Method: " + order.getPaymentMethod());
                System.out.println("Shipping Date: " + order.getShippingDate());
                System.out.println("Recipient Name: " + order.getRecipientName());
                System.out.println("Recipient Phone: " + order.getRecipientPhone());
                System.out.println("-----------------------------");
            }
        }
    }

}
