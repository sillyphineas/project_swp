package model;

import entity.Address;
import entity.Order;
import entity.OrderDetail;
import entity.OrderShippingView;
import entity.Payment;
import entity.PaymentMethod;
import entity.ProductVariant;
import entity.Shipping;
import entity.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOOrder extends DBConnection {

    // 1. Thêm một đơn hàng
    public int addOrder(Order order) {
        String sql = "INSERT INTO Orders (buyerID, orderTime, orderStatus, ShippingAddress, "
                + "totalPrice, discountedPrice, isDisabled, voucherID, "
                + "recipientName, recipientPhone, AssignedSaleId) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, order.getBuyerID());
            ps.setTimestamp(2, new Timestamp(order.getOrderTime().getTime()));
            ps.setString(3, order.getOrderStatus());
            ps.setString(4, order.getShippingAddress());
            ps.setDouble(5, order.getTotalPrice());
            ps.setDouble(6, order.getDiscountedPrice());
            ps.setBoolean(7, order.isDisabled());

            if (order.getVoucherID() != null) {
                ps.setInt(8, order.getVoucherID());
            } else {
                ps.setNull(8, java.sql.Types.INTEGER);
            }

            ps.setString(9, order.getRecipientName() != null ? order.getRecipientName() : "Unknown");
            ps.setString(10, order.getRecipientPhone() != null ? order.getRecipientPhone() : "0000000000");
            ps.setInt(11, order.getAssignedSaleId() != null ? order.getAssignedSaleId() : 0);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int orderId = generatedKeys.getInt(1);
                        order.setId(orderId); // Gán lại ID cho đối tượng Order
                        return orderId;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // 2. Lấy tất cả đơn hàng
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders";
        try (Statement statement = conn.createStatement(); ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int buyerID = rs.getInt("buyerID");
                Timestamp orderTime = rs.getTimestamp("orderTime");
                String orderStatus = rs.getString("orderStatus");
                String shippingAddress = rs.getString("shippingAddress");
                double totalPrice = rs.getDouble("totalPrice");
                double discountedPrice = rs.getDouble("discountedPrice");
                boolean isDisabled = rs.getBoolean("isDisabled");
                Integer voucherID = rs.getInt("voucherID");
                if (rs.wasNull()) {
                    voucherID = null;
                }
                String recipientName = rs.getString("RecipientName");
                String recipientPhone = rs.getString("RecipientPhone");
                int assignedSaleId = rs.getInt("AssignedSaleId");

                Order order = new Order(
                        id,
                        buyerID,
                        orderTime,
                        orderStatus,
                        shippingAddress,
                        totalPrice,
                        discountedPrice,
                        isDisabled,
                        voucherID,
                        recipientName,
                        recipientPhone,
                        assignedSaleId
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // 3. Lấy đơn hàng theo ID
    public Order getOrderById(int orderId) {
        Order order = null;
        String sql = "SELECT * FROM Orders WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    int buyerID = rs.getInt("buyerID");
                    Timestamp orderTime = rs.getTimestamp("orderTime");
                    String orderStatus = rs.getString("orderStatus");
                    String shippingAddress = rs.getString("shippingAddress");
                    double totalPrice = rs.getDouble("totalPrice");
                    double discountedPrice = rs.getDouble("discountedPrice");
                    boolean isDisabled = rs.getBoolean("isDisabled");
                    Integer voucherID = rs.getInt("voucherID");
                    if (rs.wasNull()) {
                        voucherID = null;
                    }
                    String recipientName = rs.getString("RecipientName");
                    String recipientPhone = rs.getString("RecipientPhone");
                    int assignedSaleId = rs.getInt("AssignedSaleId");

                    order = new Order(
                            id,
                            buyerID,
                            orderTime,
                            orderStatus,
                            shippingAddress,
                            totalPrice,
                            discountedPrice,
                            isDisabled,
                            voucherID,
                            recipientName,
                            recipientPhone,
                            assignedSaleId
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    // 4. Cập nhật đơn hàng
    public int updateOrder(Order order) {
        int result = 0;
        String sql = "UPDATE Orders "
                + "SET buyerID = ?, orderTime = ?, orderStatus = ?, "
                + "    shippingAddress = ?, totalPrice = ?, discountedPrice = ?, "
                + "    isDisabled = ?, voucherID = ?, recipientName = ?, recipientPhone = ?, AssignedSaleId = ? "
                + "WHERE id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, order.getBuyerID());
            preparedStatement.setTimestamp(2, new Timestamp(order.getOrderTime().getTime()));
            preparedStatement.setString(3, order.getOrderStatus());
            preparedStatement.setString(4, order.getShippingAddress());
            preparedStatement.setDouble(5, order.getTotalPrice());
            preparedStatement.setDouble(6, order.getDiscountedPrice());
            preparedStatement.setBoolean(7, order.isDisabled());

            if (order.getVoucherID() != null) {
                preparedStatement.setInt(8, order.getVoucherID());
            } else {
                preparedStatement.setNull(8, Types.INTEGER);
            }

            preparedStatement.setString(9, order.getRecipientName() != null ? order.getRecipientName() : "Unknown");
            preparedStatement.setString(10, order.getRecipientPhone() != null ? order.getRecipientPhone() : "0000000000");
            preparedStatement.setInt(11, order.getAssignedSaleId() != null ? order.getAssignedSaleId() : 0);

            preparedStatement.setInt(12, order.getId());

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 5. Xóa đơn hàng
    public int deleteOrder(int orderId) {
        int result = 0;
        String sql = "DELETE FROM Orders WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 6. Cập nhật trạng thái đơn hàng (bằng txnRef)
    public void updateOrderStatus(String txnRef, String status) {
        String sql = "UPDATE Orders SET orderStatus = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setLong(2, Long.parseLong(txnRef)); // Chuyển txnRef sang long (ID)
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

    // 7. Lấy danh sách đơn hàng cho shipper (có phân trang, lọc, tìm kiếm)
    public List<Order> getOrdersForShipper(int shipperId, String statusFilter,
            String searchQuery, int page, int pageSize) {
        List<Order> orders = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        String sql = "SELECT o.id AS orderId, o.buyerID, u.name AS buyerName, u.phoneNumber AS buyerPhone, "
                + "       v.VoucherCode, o.orderStatus AS orderStatus, o.shippingAddress, "
                + "       o.orderTime, o.totalPrice, o.discountedPrice, "
                + "       o.shippingDate, o.RecipientName, o.RecipientPhone, o.AssignedSaleId, "
                + "       o.isDisabled, o.voucherID "
                + "FROM Orders o "
                + "JOIN Users u ON o.buyerID = u.id "
                + "LEFT JOIN Vouchers v ON o.voucherID = v.VoucherID "
                + "WHERE o.isDisabled = 0 "
                + "  AND (o.orderStatus LIKE ? OR u.name LIKE ? OR o.id LIKE ?) "
                + "  AND EXISTS (SELECT 1 FROM Shipping s WHERE s.OrderID = o.id AND s.ShipperID = ?) "
                + "LIMIT ? OFFSET ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + statusFilter + "%");
            ps.setString(2, "%" + searchQuery + "%");
            ps.setString(3, "%" + searchQuery + "%");
            ps.setInt(4, shipperId);
            ps.setInt(5, pageSize);
            ps.setInt(6, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt("orderId");
                    int buyerId = rs.getInt("buyerID");
                    String orderStatus = rs.getString("orderStatus");
                    String shippingAddress = rs.getString("shippingAddress");
                    Timestamp orderTime = rs.getTimestamp("orderTime");
                    double totalPrice = rs.getDouble("totalPrice");
                    double discountedPrice = rs.getDouble("discountedPrice");
                    Date shippingDate = rs.getDate("shippingDate");
                    String recipientName = rs.getString("RecipientName");
                    String recipientPhone = rs.getString("RecipientPhone");
                    int assignedSaleId = rs.getInt("AssignedSaleId");
                    boolean isDisabled = rs.getBoolean("isDisabled");

                    Integer voucherID = rs.getInt("voucherID");
                    if (rs.wasNull()) {
                        voucherID = null;
                    }

                    Order order = new Order(
                            orderId,
                            buyerId,
                            orderTime,
                            orderStatus,
                            shippingAddress,
                            totalPrice,
                            discountedPrice,
                            isDisabled,
                            voucherID,
                            recipientName,
                            recipientPhone,
                            assignedSaleId
                    );
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    // 8. Lấy tổng số đơn hàng cho shipper (phục vụ phân trang)
    public int getTotalOrdersForShipper(int shipperId, String statusFilter, String searchQuery) {
        String sql = "SELECT COUNT(*) "
                + "FROM Orders o "
                + "JOIN Users u ON o.buyerID = u.id "
                + "LEFT JOIN Vouchers v ON o.voucherID = v.VoucherID "
                + "WHERE o.isDisabled = 0 "
                + "  AND (o.orderStatus LIKE ? OR u.name LIKE ? OR o.id LIKE ?) "
                + "  AND EXISTS (SELECT 1 FROM Shipping s WHERE s.OrderID = o.id AND s.ShipperID = ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + statusFilter + "%");
            ps.setString(2, "%" + searchQuery + "%");
            ps.setString(3, "%" + searchQuery + "%");
            ps.setInt(4, shipperId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 9. Cập nhật trạng thái đơn hàng bằng orderId
    public boolean updateStatus(int orderId, String newStatus) {
        String sql = "UPDATE Orders SET orderStatus = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, orderId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<OrderShippingView> getOrderShippingView(
            int shipperId, String statusFilter, String searchQuery,
            int page, int pageSize) {

        List<OrderShippingView> list = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        String sql = "SELECT o.id AS orderId, "
                + "       u.name AS buyerName, "
                + "       o.orderTime, "
                + "       o.orderStatus, "
                + "       o.shippingAddress, "
                + "       o.totalPrice, "
                + "       o.recipientName, "
                + "       o.recipientPhone, "
                + "       s.ShippingStatus, "
                + "       s.EstimatedArrival, "
                + "       s.ActualArrival, "
                + "       s.ShippingDate AS shippingDate "
                + "FROM Orders o "
                + "JOIN Users u ON o.buyerID = u.id "
                + "JOIN Shipping s ON s.OrderID = o.id "
                + "WHERE s.ShipperID = ? "
                // Lọc theo ShippingStatus (drop-down filter) và OrderID (search box)
                + "  AND s.ShippingStatus LIKE ? "
                + "  AND CAST(o.id AS CHAR) LIKE ? "
                + "ORDER BY o.orderTime DESC "
                + "LIMIT ? OFFSET ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, shipperId);
            ps.setString(2, "%" + statusFilter + "%");
            ps.setString(3, "%" + searchQuery + "%");
            ps.setInt(4, pageSize);
            ps.setInt(5, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt("orderId");
                    String buyerName = rs.getString("buyerName");
                    Timestamp orderTime = rs.getTimestamp("orderTime");
                    String orderStatus = rs.getString("orderStatus");
                    String shippingAddress = rs.getString("shippingAddress");
                    double totalPrice = rs.getDouble("totalPrice");
                    String recipientName = rs.getString("recipientName");
                    String recipientPhone = rs.getString("recipientPhone");

                    String shippingStatus = rs.getString("ShippingStatus");
                    Date estimatedArrival = rs.getDate("EstimatedArrival");
                    Date actualArrival = rs.getDate("ActualArrival");
                    Date shippingDate = rs.getDate("shippingDate");

                    OrderShippingView osv = new OrderShippingView(
                            orderId,
                            buyerName,
                            orderTime,
                            orderStatus,
                            shippingAddress,
                            totalPrice,
                            recipientName,
                            recipientPhone,
                            shippingStatus,
                            estimatedArrival,
                            actualArrival,
                            shippingDate
                    );
                    list.add(osv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Hàm đếm tổng số bản ghi (phục vụ phân trang)
    public int getTotalOrderShippingCount(int shipperId, String statusFilter, String searchQuery) {
        String sql = "SELECT COUNT(*) AS total "
                + "FROM Orders o "
                + "JOIN Users u ON o.buyerID = u.id "
                + "JOIN Shipping s ON s.OrderID = o.id "
                + "WHERE s.ShipperID = ? "
                + "  AND (s.ShippingStatus LIKE ? OR u.name LIKE ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, shipperId);
            ps.setString(2, "%" + statusFilter + "%");
            ps.setString(3, "%" + searchQuery + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Order> getOrdersWithPagination(int pageSize, int page) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.id AS orderID, o.buyerID,u.name As buyer_Name, o.orderTime, o.orderStatus, o.totalPrice,\n"
                + "                 o.discountedPrice, o.recipientName, o.recipientPhone,\n"
                + "               o.AssignedSaleId, s.ShippingID, s.ShippingStatus, \n"
                + "                 s.EstimatedArrival, s.ActualArrival, a.address AS shippingAddress, a.city, a.district,\n"
                + "                 p.paymentStatus, pm.paymentName\n"
                + "                              FROM Orders o \n"
                + "                           LEFT JOIN users u on o.buyerID = u.id \n"
                + "                               LEFT JOIN Shipping s ON o.id = s.OrderID  \n"
                + "                            LEFT JOIN Addresses a ON o.ShippingAddress = a.id \n"
                + "                            LEFT JOIN payment p ON o.id = p.orderId  -- Thêm JOIN với bảng payment\n"
                + "							LEFT JOIN paymentmethod pm ON p.paymentMethodId = pm.id \n"
                + "                       ORDER BY o.orderTime DESC LIMIT ? OFFSET ? ";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, (page - 1) * pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    String buyerName = rs.getString("Buyer_Name");
                    System.out.println("DEBUG: Buyer_Name = " + buyerName);
                    user.setName(buyerName);
                    Order order = new Order();

                    // Thông tin đơn hàng
                    order.setId(rs.getInt("orderID"));
                    order.setBuyerID(rs.getInt("buyerID"));
                    Timestamp orderTime = rs.getTimestamp("orderTime");
                    if (orderTime == null) {
                        System.out.println("⚠️ orderTime is NULL for orderID: " + rs.getInt("orderID"));
                    } else {
                        order.setOrderTime(orderTime);
                    }
                    order.setOrderStatus(rs.getString("orderStatus"));
                    order.setTotalPrice(rs.getDouble("totalPrice"));
                    order.setDiscountedPrice(rs.getDouble("discountedPrice"));
                    order.setRecipientName(rs.getString("recipientName"));
                    order.setRecipientPhone(rs.getString("recipientPhone"));
                    order.setAssignedSaleId(rs.getInt("AssignedSaleId"));
                    // Thông tin vận chuyển

                    user.setName(rs.getString("buyer_Name"));

                    order.setUser(user);

                    Shipping shipping = new Shipping();
                    shipping.setShippingID(rs.getInt("ShippingID"));
                    shipping.setShippingStatus(rs.getString("ShippingStatus"));
                    shipping.setEstimatedArrival(rs.getDate("EstimatedArrival"));
                    shipping.setActualArrival(rs.getDate("ActualArrival"));
                    order.setShipping(shipping);

                    String fullAddress = rs.getString("shippingAddress") + ", "
                            + rs.getString("district") + ", "
                            + rs.getString("city");
                    order.setShippingAddress(fullAddress);;

                    Payment payment = new Payment();
                    payment.setPaymentStatus(rs.getString("paymentStatus"));
                    order.setPayment(payment);

                    PaymentMethod pm = new PaymentMethod();
                    pm.setName(rs.getString("paymentName"));
                    order.setPaymentMethod(pm);

                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public int getTotalOrders() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Orders";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public List<Order> SalesearchOrders(String query, int page, int pageSize) {
        List<Order> orders = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        String sql = "SELECT o.id AS orderID, o.buyerID, u.name AS buyer_Name, o.orderTime, o.orderStatus, o.totalPrice, "
                + "o.discountedPrice, o.recipientName, o.recipientPhone, o.AssignedSaleId, "
                + "s.ShippingID, s.ShippingStatus, s.EstimatedArrival, s.ActualArrival, "
                + "a.address AS shippingAddress, a.city, a.district, "
                + "p.paymentStatus, pm.paymentName "
                + "FROM Orders o "
                + "LEFT JOIN users u ON o.buyerID = u.id "
                + "LEFT JOIN Shipping s ON o.id = s.OrderID "
                + "LEFT JOIN Addresses a ON o.ShippingAddress = a.id "
                + "LEFT JOIN payment p ON o.id = p.orderId "
                + "LEFT JOIN paymentmethod pm ON p.paymentMethodId = pm.id "
                + "WHERE LOWER(u.name) LIKE ? OR LOWER(o.orderStatus) LIKE ? "
                + "OR LOWER(o.recipientPhone) LIKE ? OR LOWER(s.ShippingStatus) LIKE ? "
                + "OR LOWER(a.address) LIKE ? OR LOWER(pm.paymentName) LIKE ? "
                + "ORDER BY o.orderTime DESC "
                + "LIMIT ? OFFSET ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 1; i <= 6; i++) {
                ps.setString(i, "%" + query.toLowerCase() + "%");
            }
            ps.setInt(7, pageSize);
            ps.setInt(8, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                String buyerName = rs.getString("Buyer_Name");
                System.out.println("DEBUG: Buyer_Name = " + buyerName);
                user.setName(buyerName);
                Order order = new Order();

                // Thông tin đơn hàng
                order.setId(rs.getInt("orderID"));
                order.setBuyerID(rs.getInt("buyerID"));
                Timestamp orderTime = rs.getTimestamp("orderTime");
                if (orderTime == null) {
                    System.out.println("⚠️ orderTime is NULL for orderID: " + rs.getInt("orderID"));
                } else {
                    order.setOrderTime(orderTime);
                }
                order.setOrderStatus(rs.getString("orderStatus"));
                order.setTotalPrice(rs.getDouble("totalPrice"));
                order.setDiscountedPrice(rs.getDouble("discountedPrice"));
                order.setRecipientName(rs.getString("recipientName"));
                order.setRecipientPhone(rs.getString("recipientPhone"));
                order.setAssignedSaleId(rs.getInt("AssignedSaleId"));
                // Thông tin vận chuyển

                user.setName(rs.getString("buyer_Name"));

                order.setUser(user);

                Shipping shipping = new Shipping();
                shipping.setShippingID(rs.getInt("ShippingID"));
                shipping.setShippingStatus(rs.getString("ShippingStatus"));
                shipping.setEstimatedArrival(rs.getDate("EstimatedArrival"));
                shipping.setActualArrival(rs.getDate("ActualArrival"));
                order.setShipping(shipping);

                String fullAddress = rs.getString("shippingAddress") + ", "
                        + rs.getString("district") + ", "
                        + rs.getString("city");
                order.setShippingAddress(fullAddress);;

                Payment payment = new Payment();
                payment.setPaymentStatus(rs.getString("paymentStatus"));
                order.setPayment(payment);

                PaymentMethod pm = new PaymentMethod();
                pm.setName(rs.getString("paymentName"));
                order.setPaymentMethod(pm);

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public int SalecountTotalOrdersForSearch(String query) {
        int totalOrders = 0;
        String sql = "SELECT COUNT(*) FROM Orders o "
                + "LEFT JOIN users u ON o.buyerID = u.id "
                + "LEFT JOIN Shipping s ON o.id = s.OrderID "
                + "LEFT JOIN Addresses a ON o.ShippingAddress = a.id "
                + "LEFT JOIN payment p ON o.id = p.orderId "
                + "LEFT JOIN paymentmethod pm ON p.paymentMethodId = pm.id "
                + "WHERE LOWER(u.name) LIKE ? OR LOWER(o.orderStatus) LIKE ? "
                + "OR LOWER(o.recipientPhone) LIKE ? OR LOWER(s.ShippingStatus) LIKE ? "
                + "OR LOWER(a.address) LIKE ? OR LOWER(pm.paymentName) LIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 1; i <= 6; i++) {
                ps.setString(i, "%" + query.toLowerCase() + "%");
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalOrders = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalOrders;
    }

    public boolean isOrderAlreadyAssigned(int orderID) {
        String sql = "SELECT COUNT(*) FROM Shipping WHERE orderID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertShipping(int orderID, int shipperID, String shippingStatus, Date estimatedArrival, Date actualArrival, Date shippingDate) {
        String sql = "INSERT INTO Shipping (orderID, shipperID, shippingStatus, estimatedArrival, actualArrival, shippingDate) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderID);
            ps.setInt(2, shipperID);
            ps.setString(3, shippingStatus);
            ps.setDate(4, estimatedArrival);
            if (actualArrival != null) {
                ps.setDate(5, actualArrival);
            } else {
                ps.setNull(5, java.sql.Types.DATE);
            }
            ps.setDate(6, shippingDate);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Test DAOOrder
    public static void main(String[] args) {
        DAOOrder daoOrder = new DAOOrder();

        System.out.println(daoOrder.isOrderAlreadyAssigned(4));

//        int shipperId = 2;
//        String statusFilter = "";
//        String searchQuery = "";
//        int page = 1;
//        int pageSize = 10;
//
//        List<Order> orders = daoOrder.getOrdersForShipper(shipperId, statusFilter, searchQuery, page, pageSize);
//        if (orders.isEmpty()) {
//            System.out.println("No orders found for the given criteria.");
//        } else {
//            for (Order order : orders) {
//                System.out.println("Order ID: " + order.getId());
//                System.out.println("Buyer ID: " + order.getBuyerID());
//                System.out.println("Order Status: " + order.getOrderStatus());
//                System.out.println("Shipping Address: " + order.getShippingAddress());
//                System.out.println("Total Price: " + order.getTotalPrice());
//                System.out.println("Discounted Price: " + order.getDiscountedPrice());
//                System.out.println("Recipient Name: " + order.getRecipientName());
//                System.out.println("Recipient Phone: " + order.getRecipientPhone());
//                System.out.println("Assigned Sale ID: " + order.getAssignedSaleId());
//                System.out.println("isDisabled: " + order.isDisabled());
//                System.out.println("-----------------------------");
//            }
    }
}
