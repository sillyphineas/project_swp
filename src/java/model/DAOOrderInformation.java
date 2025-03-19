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

    public List<OrderInformation> getAllOrderInformation() {
        List<OrderInformation> list = new ArrayList<>();
        int count = 0;
        // Câu lệnh SELECT như bạn đã đưa ra
        String sql = """
                     SELECT o.id, o.orderTime, o.orderStatus, 
                     o.totalPrice, pt.paymentName, pm.paymentStatus ,o.recipientName,
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
                     ORDER BY o.id ASC
                     """;

        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                // Lấy dữ liệu từ ResultSet
                int id = rs.getInt("id");
                Timestamp orderTime = rs.getTimestamp("orderTime");
                String orderStatus = rs.getString("orderStatus");
                double totalPrice = rs.getDouble("totalPrice");
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
                // Tạo đối tượng OrderInformation (dùng constructor đầy đủ 20 tham số)
                OrderInformation oi = new OrderInformation(
                        id,
                        orderTime,
                        orderStatus,
                        totalPrice,
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(count);
        return list;
    }

}
