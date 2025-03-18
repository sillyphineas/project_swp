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
import java.util.Date;
import entity.RevenueTrend;
import entity.OrderTrend;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                        rs.getDate("EstimatedArrival"),
                        rs.getDate("ActualArrival"),
                        rs.getDate("ShippingDate")
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
                        rs.getDate("EstimatedArrival"),
                        rs.getDate("ActualArrival"),
                        rs.getDate("ShippingDate")
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
                        rs.getDate("EstimatedArrival"),
                        rs.getDate("ActualArrival"),
                        rs.getDate("ShippingDate")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return shipping;
    }
    public Vector<OrderTrend> getOrderTrends(Date startDate, Date endDate, Integer shipperId, String orderStatus) {
        Vector<OrderTrend> trends = new Vector<>();
        Calendar cal = Calendar.getInstance();

        // Set default start date to 7 days ago if not provided
        if (startDate == null) {
            cal.add(Calendar.DAY_OF_YEAR, -7);
            startDate = cal.getTime();
        }
        // Set default end date to today if not provided
        if (endDate == null) {
            endDate = new Date();
        }

        // SQL query to count total and successful orders per day
        String sql = "SELECT DATE(o.orderTime) as trendDate, "
                + "COUNT(*) as totalOrders, "
                + "SUM(CASE WHEN o.orderStatus = 'Delivered' THEN 1 ELSE 0 END) as successfulOrders "
                + "FROM [Order] o "
                + "LEFT JOIN Shipping s ON o.id = s.OrderID "
                + "WHERE o.orderTime BETWEEN ? AND ? "
                + (shipperId != null ? "AND s.ShipperID = ? " : "")
                + (orderStatus != null && !orderStatus.isEmpty() ? "AND o.orderStatus = ? " : "")
                + "GROUP BY DATE(o.orderTime) "
                + "ORDER BY trendDate";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int paramIndex = 1;
            // Set date parameters
            ps.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
            ps.setDate(paramIndex++, new java.sql.Date(endDate.getTime()));
            // Set shipperId if provided
            if (shipperId != null) {
                ps.setInt(paramIndex++, shipperId);
            }
            // Set orderStatus if provided
            if (orderStatus != null && !orderStatus.isEmpty()) {
                ps.setString(paramIndex++, orderStatus);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Date trendDate = rs.getDate("trendDate");
                int totalOrders = rs.getInt("totalOrders");
                int successfulOrders = rs.getInt("successfulOrders");
                trends.add(new OrderTrend(trendDate, totalOrders, successfulOrders));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOShipping.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trends;
    }

    /**
     * Get revenue trends by day for a specified date range.
     * @param startDate Start date for the trend (null for last 7 days)
     * @param endDate End date for the trend (null for today)
     * @param shipperId Specific shipper ID (null for all shippers)
     * @param orderStatus Specific order status (null for all statuses)
     * @return Vector of RevenueTrend objects
     */
    public Vector<RevenueTrend> getRevenueTrends(Date startDate, Date endDate, Integer shipperId, String orderStatus) {
        Vector<RevenueTrend> trends = new Vector<>();
        Calendar cal = Calendar.getInstance();

        // Set default start date to 7 days ago if not provided
        if (startDate == null) {
            cal.add(Calendar.DAY_OF_YEAR, -7);
            
        }
        // Set default end date to today if not provided
        if (endDate == null) {
            endDate = new Date();
        }

        // SQL query to sum discountedPrice per day
        String sql = "SELECT DATE(o.orderTime) as trendDate, "
                + "COALESCE(SUM(o.discountedPrice), 0) as dailyRevenue "
                + "FROM [Order] o "
                + "LEFT JOIN Shipping s ON o.id = s.OrderID "
                + "WHERE o.orderTime BETWEEN ? AND ? "
                + (shipperId != null ? "AND s.ShipperID = ? " : "")
                + (orderStatus != null && !orderStatus.isEmpty() ? "AND o.orderStatus = ? " : "")
                + "GROUP BY DATE(o.orderTime) "
                + "ORDER BY trendDate";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int paramIndex = 1;
            // Set date parameters
            ps.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
            ps.setDate(paramIndex++, new java.sql.Date(endDate.getTime()));
            // Set shipperId if provided
            if (shipperId != null) {
                ps.setInt(paramIndex++, shipperId);
            }
            // Set orderStatus if provided
            if (orderStatus != null && !orderStatus.isEmpty()) {
                ps.setString(paramIndex++, orderStatus);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Date trendDate = rs.getDate("trendDate");
                double dailyRevenue = rs.getDouble("dailyRevenue");
                trends.add(new RevenueTrend(trendDate, dailyRevenue));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOShipping.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trends;
    }
     public List<Map<String, Object>> getShippingStatsByDate(String startDate, String endDate, String shippingStatus) throws SQLException {
    String sql = "SELECT DATE(ShippingDate) AS date, "
            + "COUNT(*) AS totalShipments, "
            + "COUNT(CASE WHEN ShippingStatus = 'Delivered' AND ActualArrival IS NOT NULL THEN 1 END) AS deliveredShipments, "
            + "COUNT(CASE WHEN ShippingStatus = 'Shipping' AND ActualArrival IS NULL THEN 1 END) AS shippingShipments "
            + "FROM Shipping "
            + "WHERE ShippingDate BETWEEN ? AND ? ";

    if (shippingStatus != null && !shippingStatus.isEmpty()) {
        sql += "AND ShippingStatus = ? ";
    }

    sql += "GROUP BY DATE(ShippingDate)";

    List<Map<String, Object>> shippingStats = new ArrayList<>();

    try (PreparedStatement pre = conn.prepareStatement(sql)) {
        pre.setString(1, startDate);
        pre.setString(2, endDate);

        if (shippingStatus != null && !shippingStatus.isEmpty()) {
            pre.setString(3, shippingStatus);
        }

        try (ResultSet rs = pre.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> stat = new HashMap<>();
                stat.put("date", rs.getString("date"));
                stat.put("totalShipments", rs.getInt("totalShipments"));
                stat.put("deliveredShipments", rs.getInt("deliveredShipments"));
                stat.put("shippingShipments", rs.getInt("shippingShipments"));
                shippingStats.add(stat);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return shippingStats;
}
}


