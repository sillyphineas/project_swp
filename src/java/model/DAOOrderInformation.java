/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import entity.OrderInformation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOOrderInformation extends DBConnection {

    public List<OrderInformation> getAllOrderInformation(int buyerId) {
        List<OrderInformation> list = new ArrayList<>();
        int count = 0;
        // Câu lệnh SELECT với placeholder cho buyerId
        String sql = """
                 SELECT o.id, o.orderTime, o.orderStatus, 
                        o.totalPrice, o.discountedPrice, pt.paymentName, pm.paymentStatus, o.recipientName,
                        o.recipientPhone, od.quantity, s.ShippingStatus, 
                        s.ShippingDate, s.EstimatedArrival, s.ActualArrival,
                        cl.colorName, str.capacity, pv.price, p.name,
                        p.imageURL, ad.address, ad.district, ad.city, p.id as ProductID, od.id as orderDetailID
                 FROM proj_swp391_update1.orders as o 
                 LEFT JOIN orderdetails as od on o.id = od.orderId 
                 LEFT JOIN shipping as s on o.id = s.OrderID
                 LEFT JOIN productvariants as pv on od.productVariantID = pv.id
                 LEFT JOIN products as p on pv.product_id = p.id
                 LEFT JOIN payment as pm on pm.orderId = o.id
                 LEFT JOIN paymentmethod as pt on pm.paymentMethodId = pt.id
                 LEFT JOIN addresses as ad on o.ShippingAddress = ad.id
                 LEFT JOIN colors as cl on pv.color_id = cl.id
                 LEFT JOIN storages as str on pv.storage_id = str.id
                 LEFT JOIN users as us on o.buyerID = us.id
                 WHERE o.buyerID = ?
                 ORDER BY o.id ASC
                 """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Gán giá trị buyerId vào vị trí dấu ?
            ps.setInt(1, buyerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Lấy dữ liệu từ ResultSet
                    int id = rs.getInt("id");
                    Timestamp orderTime = rs.getTimestamp("orderTime");
                    String orderStatus = rs.getString("orderStatus");
                    double totalPrice = rs.getDouble("totalPrice");
                    double discountPrice = rs.getDouble("discountedPrice");
                    String paymentName = rs.getString("paymentName");
                    String paymentStatus = rs.getString("paymentStatus");
                    String recipientName = rs.getString("recipientName");
                    String recipientPhone = rs.getString("recipientPhone");
                    int quantity = rs.getInt("quantity");
                    String shippingStatus = rs.getString("ShippingStatus");
                    Timestamp shippingDate = rs.getTimestamp("ShippingDate");
                    Timestamp estimatedArrival = rs.getTimestamp("EstimatedArrival");
                    Timestamp actualArrival = rs.getTimestamp("ActualArrival");
                    String colorName = rs.getString("colorName");
                    String capacity = rs.getString("capacity");
                    double price = rs.getDouble("price");
                    String productName = rs.getString("name");
                    String imageURL = rs.getString("imageURL");
                    String address = rs.getString("address");
                    String district = rs.getString("district");
                    String city = rs.getString("city");
                    int productId = rs.getInt("ProductID");
                    int orderDetailID = rs.getInt("orderDetailID");
                    count++;

                    // Tạo đối tượng OrderInformation (sử dụng constructor đầy đủ các tham số)
                    OrderInformation oi = new OrderInformation(
                            id,
                            orderTime,
                            orderStatus,
                            totalPrice,
                            discountPrice,
                            paymentName,
                            paymentStatus,
                            recipientName,
                            recipientPhone,
                            quantity,
                            shippingStatus,
                            shippingDate,
                            estimatedArrival,
                            actualArrival,
                            colorName,
                            capacity,
                            price,
                            productName,
                            imageURL,
                            address,
                            district,
                            city,
                            productId,
                            orderDetailID
                    );

                    list.add(oi);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(count);
        return list;
    }

    public List<OrderInformation> getAllForShipper(int shipperId,
            String shippingStatusFilter,
            String searchOrderId,
            int page,
            int pageSize) {
        List<OrderInformation> list = new ArrayList<>();

        // Bắt đầu từ shipping s, JOIN orders o, LEFT JOIN payment, LEFT JOIN addresses...
        // Tên bảng "orders", "shipping", "payment"... bạn điều chỉnh đúng schema nếu cần
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT ")
                .append("   s.ShippingID, ")
                .append("   s.OrderID, ")
                .append("   s.ShipperID, ")
                .append("   s.ShippingStatus, ")
                .append("   s.ShippingDate, ")
                .append("   s.EstimatedArrival, ")
                .append("   s.ActualArrival, ")
                .append("   o.orderTime, ")
                .append("   o.orderStatus, ")
                .append("   o.totalPrice, ")
                .append("   o.recipientName, ")
                .append("   o.recipientPhone, ")
                .append("   pm.paymentStatus, ")
                .append("   ad.address, ")
                .append("   ad.district, ")
                .append("   ad.city ")
                .append(" FROM shipping s ")
                .append(" JOIN orders o ON s.OrderID = o.id ")
                .append(" LEFT JOIN payment pm ON pm.orderId = o.id ")
                .append(" LEFT JOIN addresses ad ON o.ShippingAddress = ad.id ")
                .append(" WHERE s.ShipperID = ? ");

        // Nếu filter shippingStatus
        if (shippingStatusFilter != null && !shippingStatusFilter.isEmpty()) {
            sb.append(" AND s.ShippingStatus = ? ");
        }

        // Nếu searchOrderId
        if (searchOrderId != null && !searchOrderId.isEmpty()) {
            sb.append(" AND s.OrderID = ? ");
        }

        sb.append(" ORDER BY s.ShippingID DESC ");
        sb.append(" LIMIT ? OFFSET ? ");

        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            int idx = 1;
            ps.setInt(idx++, shipperId);

            if (shippingStatusFilter != null && !shippingStatusFilter.isEmpty()) {
                ps.setString(idx++, shippingStatusFilter);
            }

            if (searchOrderId != null && !searchOrderId.isEmpty()) {
                try {
                    int orderId = Integer.parseInt(searchOrderId);
                    ps.setInt(idx++, orderId);
                } catch (NumberFormatException e) {
                    ps.setInt(idx++, 0); // nếu không phải số
                }
            }

            ps.setInt(idx++, pageSize);
            ps.setInt(idx++, (page - 1) * pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderInformation oi = new OrderInformation();

                // Gán các trường
                // Lấy từ shipping
                int shippingID = rs.getInt("ShippingID"); // nếu bạn cần hiển thị shippingID
                oi.setId(rs.getInt("OrderID")); // ta dùng field "id" làm OrderID
                oi.setShippingStatus(rs.getString("ShippingStatus"));
                oi.setShippingDate(rs.getTimestamp("ShippingDate"));
                oi.setEstimatedArrival(rs.getTimestamp("EstimatedArrival"));
                oi.setActualArrival(rs.getTimestamp("ActualArrival"));

                // Từ orders
                oi.setOrderTime(rs.getTimestamp("orderTime"));
                oi.setOrderStatus(rs.getString("orderStatus"));
                oi.setTotalPrice(rs.getDouble("totalPrice"));
                oi.setRecipientName(rs.getString("recipientName"));
                oi.setRecipientPhone(rs.getString("recipientPhone"));

                // Từ payment
                oi.setPaymentStatus(rs.getString("paymentStatus"));

                // Từ addresses
                oi.setAddress(rs.getString("address"));
                oi.setDistrict(rs.getString("district"));
                oi.setCity(rs.getString("city"));

                list.add(oi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Đếm tổng số dòng shipping (cho shipper) để phân trang.
     */
    public int countAllForShipper(int shipperId,
            String shippingStatusFilter,
            String searchOrderId) {
        int count = 0;
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT COUNT(*) AS cnt ")
                .append(" FROM shipping s ")
                .append(" JOIN orders o ON s.OrderID = o.id ")
                .append(" LEFT JOIN payment pm ON pm.orderId = o.id ")
                .append(" WHERE s.ShipperID = ? ");

        if (shippingStatusFilter != null && !shippingStatusFilter.isEmpty()) {
            sb.append(" AND s.ShippingStatus = ? ");
        }
        if (searchOrderId != null && !searchOrderId.isEmpty()) {
            sb.append(" AND s.OrderID = ? ");
        }

        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            int idx = 1;
            ps.setInt(idx++, shipperId);

            if (shippingStatusFilter != null && !shippingStatusFilter.isEmpty()) {
                ps.setString(idx++, shippingStatusFilter);
            }
            if (searchOrderId != null && !searchOrderId.isEmpty()) {
                try {
                    int orderId = Integer.parseInt(searchOrderId);
                    ps.setInt(idx++, orderId);
                } catch (NumberFormatException e) {
                    ps.setInt(idx++, 0);
                }
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("cnt");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

}
