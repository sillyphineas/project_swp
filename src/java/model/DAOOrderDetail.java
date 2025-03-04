package model;

import entity.OrderDetail;
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


    public static void main(String[] args) {

    }
}
