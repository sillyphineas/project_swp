/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
import entity.Color;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOColor extends DBConnection {

    // Thêm màu mới
    public int addColor(Color color) {
        int n = 0;
        String sql = "INSERT INTO Color (colorName, status) VALUES (?, ?)";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, color.getColorName());
            pre.setString(2, color.getStatus());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    // Lấy danh sách tất cả màu
    public Vector<Color> getColors(String sql) {
        Vector<Color> vector = new Vector<>();
        try (Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Color color = new Color(
                        rs.getInt("id"),
                        rs.getString("colorName"),
                        rs.getString("status")
                );
                vector.add(color);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOColor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public Vector<Color> getColorById(int id) {
        Vector<Color> colors = new Vector<>();
        String sql = "SELECT * FROM Color WHERE id = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Color color = new Color(
                        rs.getInt("id"),
                        rs.getString("colorName"),
                        rs.getString("status")
                );
                colors.add(color);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return colors;
    }

    // Cập nhật thông tin màu
    public int updateColor(Color color) {
        int n = 0;
        String sql = "UPDATE Color SET colorName = ?, status = ? WHERE id = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, color.getColorName());
            pre.setString(2, color.getStatus());
            pre.setInt(3, color.getId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    // Xóa màu
    public int deleteColor(int id) {
        int n = 0;
        String sql = "DELETE FROM Color WHERE id = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, id);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    // Lấy danh sách màu có phân trang
    public List<Color> getPaginatedColors(int page, int pageSize) throws SQLException {
        String sql = "SELECT * FROM Color ORDER BY id ASC LIMIT ? OFFSET ?";
        List<Color> colors = new ArrayList<>();
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, pageSize);
            pre.setInt(2, (page - 1) * pageSize);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                colors.add(new Color(rs.getInt("id"),
                        rs.getString("colorName"),
                        rs.getString("status")));
            }
        }
        return colors;
    }

    public int getColorIDByName(String colorName) {
        String query = "SELECT id FROM colors WHERE colorName = ? AND status = 'Active'";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, colorName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Color getColorById1 (int colorId) {
        String sql = "SELECT id, colorName, status FROM colors WHERE id = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, colorId);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) { 
                return new Color(
                        rs.getInt("id"),
                        rs.getString("colorName"),
                        rs.getString("status")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null; 
    }
    
    public Vector<Color> getAllColors() {
        Vector<Color> colors = new Vector<>();
        String sql = "SELECT * FROM Colors WHERE status = 'Active'"; // Truy vấn lấy tất cả màu sắc có trạng thái 'Active'
        
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // Tạo đối tượng Color và thêm vào vector
                Color color = new Color(
                        rs.getInt("id"),
                        rs.getString("colorName"),
                        rs.getString("status") 
                );
                colors.add(color);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return colors;
    }

    public static void main(String[] args) {
        DAOColor dao = new DAOColor();
//        System.out.println(dao.getColorIDByName("Đen"));
        System.out.println(dao.getColorById1(1));
    }
}