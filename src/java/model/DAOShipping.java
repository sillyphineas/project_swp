/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import entity.Shipping;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;

/**
 *
 * @author HP
 */
public class DAOShipping extends DBConnection {

    public Vector<Shipping> getShippings(String sql) {
        Vector<Shipping> vector = new Vector<>();
        try {
            Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Shipping shipping = new Shipping(
                        rs.getInt("ShippingID"),
                        rs.getInt("OrderID"),
                        rs.getInt("ShipperID"),
                        rs.getString("ShippingStatus"),
                        rs.getString("EstimatedArrival"),
                        rs.getString("ActualArrival"),
                        rs.getString("ShippingDate")
                );
                vector.add(shipping);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOShipping.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public boolean updateShippingStatus(int orderId, int shipperId, String newStatus) {
        String sql = "UPDATE Shipping SET ShippingStatus = ? WHERE OrderID = ? AND ShipperID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, orderId);
            ps.setInt(3, shipperId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Shipping getShippingById(int shippingId) {
        String sql = "SELECT * FROM Shipping WHERE ShippingID = ?";
        Shipping shipping = null;
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, shippingId);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                shipping = new Shipping(
                        rs.getInt("ShippingID"),
                        rs.getInt("OrderID"),
                        rs.getInt("ShipperID"),
                        rs.getString("ShippingStatus"),
                        rs.getString("EstimatedArrival"),
                        rs.getString("ActualArrival"),
                        rs.getString("ShippingDate")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOShipping.class.getName()).log(Level.SEVERE, null, ex);
        }
        return shipping;
    }

    public Shipping getShippingByOrderId(int orderId) {
        String sql = "SELECT * FROM Shipping WHERE OrderID = ?";
        Shipping shipping = null;
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, orderId);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                shipping = new Shipping(
                        rs.getInt("ShippingID"),
                        rs.getInt("OrderID"),
                        rs.getInt("ShipperID"),
                        rs.getString("ShippingStatus"),
                        rs.getString("EstimatedArrival"),
                        rs.getString("ActualArrival"),
                        rs.getString("ShippingDate")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return shipping;
    }

}
