package model;

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
        String sql = "INSERT INTO Colors (colorName, status) VALUES (?, ?)";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, color.getColorName());
            pre.setString(2, color.getStatus());  // Dùng String cho status
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
                        rs.getString("status")  // Dùng String cho status
                );
                vector.add(color);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOColor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    // Lấy màu theo ID
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
                        rs.getString("status")  // Dùng String cho status
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
            pre.setString(2, color.getStatus());  // Dùng String cho status
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
                        rs.getString("status")));  // Dùng String cho status
            }
        }
        return colors;
    }

    // Lấy tất cả màu sắc có trạng thái 'Active'
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
}
