package model;

import entity.Color;
import entity.OrderDetail;
import entity.Payment;
import entity.PaymentMethod;
import entity.Product;
import entity.ProductVariant;
import entity.Storage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOOrderDetail extends DBConnection {

    public int addOrderDetail(OrderDetail orderDetail) {
        int result = 0;
        String sql = "INSERT INTO orderdetails (orderId, productVariantID, quantity) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, orderDetail.getOrderId());
            ps.setInt(2, orderDetail.getProductVariantID());
            ps.setInt(3, orderDetail.getQuantity());

            result = ps.executeUpdate();
            if (result > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    orderDetail.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<OrderDetail> getAllOrderDetails() {
        List<OrderDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM orderdetails";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int orderId = rs.getInt("orderId");
                int productVariantID = rs.getInt("productVariantID");
                int quantity = rs.getInt("quantity");

                OrderDetail od = new OrderDetail(id, orderId, productVariantID, quantity);
                list.add(od);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public OrderDetail getOrderDetailById(int idParam) {
        OrderDetail od = null;
        String sql = "SELECT * FROM orderdetails WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idParam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                int orderId = rs.getInt("orderId");
                int productVariantID = rs.getInt("productVariantID");
                int quantity = rs.getInt("quantity");

                od = new OrderDetail(id, orderId, productVariantID, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return od;
    }

    public List<OrderDetail> getOrderDetailsByOrderId(int orderIdParam) {
        List<OrderDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM orderdetails WHERE orderId = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderIdParam);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int orderId = rs.getInt("orderId");
                int productVariantID = rs.getInt("productVariantID");
                int quantity = rs.getInt("quantity");

                OrderDetail od = new OrderDetail(id, orderId, productVariantID, quantity);
                list.add(od);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int updateOrderDetail(OrderDetail orderDetail) {
        int result = 0;
        String sql = "UPDATE orderdetails SET orderId = ?, productVariantID = ?, quantity = ? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderDetail.getOrderId());
            ps.setInt(2, orderDetail.getProductVariantID());
            ps.setInt(3, orderDetail.getQuantity());
            ps.setInt(4, orderDetail.getId());

            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int deleteOrderDetail(int idParam) {
        int result = 0;
        String sql = "DELETE FROM orderdetails WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idParam);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<OrderDetail> SalegetOrderDetailsByOrderId(int orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "SELECT p.name AS product_name, "
                + "       p.imageURL AS image, "
                + "       c.colorName AS color, "
                + "       s.capacity AS storage, "
                + "       od.quantity, "
                + "       (pv.price * od.quantity) AS total_price, "
                + "       pm.paymentStatus, "
                + "       pm.amount, "
                + "       pm.createdDate, "
                + "       pmt.paymentName AS payment_method, "
                + "       o.recipientName, o.recipientPhone, ad.address, o.orderTime "
                + "FROM orderdetails od "
                + "JOIN orders o ON od.orderId = o.id "
                + "JOIN addresses ad ON o.ShippingAddress = ad.id "
                + "JOIN productVariants pv ON od.productVariantID = pv.id "
                + "JOIN products p ON pv.product_id = p.id "
                + "JOIN colors c ON pv.color_id = c.id "
                + "JOIN storages s ON pv.storage_id = s.id "
                + "LEFT JOIN payment pm ON pm.orderId = od.orderId "
                + "LEFT JOIN paymentmethod pmt ON pm.paymentMethodId = pmt.id "
                + "WHERE od.orderId = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Tạo đối tượng Product
                    Product product = new Product();
                    product.setName(rs.getString("product_name"));
                    product.setImageURL(rs.getString("image"));

                    // Tạo đối tượng Color
                    Color color = new Color();
                    color.setColorName(rs.getString("color"));

                    // Tạo đối tượng Storage
                    Storage storage = new Storage();
                    storage.setCapacity(rs.getString("storage"));

                    // Tạo đối tượng ProductVariant (có giá)
                    ProductVariant productVariant = new ProductVariant();
                    productVariant.setProduct(product);
                    productVariant.setColor(color);
                    productVariant.setStorage(storage);
                    productVariant.setPrice(rs.getDouble("total_price")); // Tổng tiền sản phẩm

                    // Tạo đối tượng Payment
                    Payment payment = new Payment();
                    payment.setPaymentStatus(rs.getString("paymentStatus"));
                    payment.setAmount(rs.getDouble("amount"));
                    payment.setCreatedDate(rs.getTimestamp("createdDate"));

                    // Tạo đối tượng PaymentMethod
                    PaymentMethod paymentMethod = new PaymentMethod();
                    paymentMethod.setName(rs.getString("payment_method"));

                    // Tạo đối tượng OrderDetail (chỉ có quantity)
                    OrderDetail detail = new OrderDetail();
                    detail.setProductVariant(productVariant);
                    detail.setQuantity(rs.getInt("quantity"));
                    detail.setPayment(payment);
                    detail.setPaymentMethod(paymentMethod);

                    // Gán thông tin người nhận
                    detail.setRecipientName(rs.getString("recipientName"));
                    detail.setRecipientPhone(rs.getString("recipientPhone"));
                    detail.setShippingAddress(rs.getString("address"));
                    detail.setOrderTime(rs.getString("orderTime"));
                    // Thêm vào danh sách kết quả
                    orderDetails.add(detail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }

    public static void main(String[] args) {
        DAOOrderDetail dao = new DAOOrderDetail();
        System.out.println(dao.SalegetOrderDetailsByOrderId(4));
    }
}
