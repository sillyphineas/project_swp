package model;

import entity.Setting;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOSetting extends DBConnection {

    // Thêm một cài đặt mới
    public int addSetting(Setting setting) {
        int n = 0;
        String sql = "INSERT INTO Settings (type, key_name, value, description, status) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, setting.getType());
            pre.setString(2, setting.getKeyName());
            pre.setString(3, setting.getValue());
            pre.setString(4, setting.getDescription());
            pre.setString(5, setting.getStatus());  // Chuyển từ boolean thành String
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    // Cập nhật cài đặt
    public int updateSetting(Setting setting) {
        int n = 0;
        // Chỉnh sửa cú pháp SQL cho MariaDB
        String sql = "UPDATE Settings SET type = ?, key_name = ?, value = ?, description = ?, status = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, setting.getType());
            pre.setString(2, setting.getKeyName());
            pre.setString(3, setting.getValue());
            pre.setString(4, setting.getDescription());
            pre.setString(5, setting.getStatus());  // Đảm bảo status là String
            pre.setInt(6, setting.getId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    // Lấy cài đặt theo ID
    public Setting getSettingById(int id) {
        String sql = "SELECT * FROM Settings WHERE id = ?";
        Setting setting = null;
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                setting = new Setting(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("key_name"),
                        rs.getString("value"),
                        rs.getString("description"),
                        rs.getString("status"), // Cập nhật status là String
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSetting.class.getName()).log(Level.SEVERE, null, ex);
        }
        return setting;
    }

    // Lấy tất cả các cài đặt
    public Vector<Setting> getAllSettings() {
        Vector<Setting> settings = new Vector<>();
        String sql = "SELECT * FROM Settings";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Setting setting = new Setting(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("key_name"),
                        rs.getString("value"),
                        rs.getString("description"),
                        rs.getString("status"), // Cập nhật status là String
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
                settings.add(setting);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return settings;
    }

    // Lấy cài đặt theo key_name
    public Setting getSettingByKeyName(String keyName) {
        String sql = "SELECT * FROM Settings WHERE key_name = ?";
        Setting setting = null;
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, keyName);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                setting = new Setting(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("key_name"),
                        rs.getString("value"),
                        rs.getString("description"),
                        rs.getString("status"), // Cập nhật status là String
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSetting.class.getName()).log(Level.SEVERE, null, ex);
        }
        return setting;
    }

    // Xóa một cài đặt theo ID
    public int deleteSetting(int id) {
        int n = 0;
        String sql = "DELETE FROM Settings WHERE id = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    // Main method để thử nghiệm
    public static void main(String[] args) {
        // Tạo đối tượng DAOSetting
        DAOSetting daoSetting = new DAOSetting();

        // Lấy tất cả các cài đặt từ cơ sở dữ liệu
        Vector<Setting> settings = daoSetting.getAllSettings();

        // Kiểm tra và in ra thông tin cài đặt
        if (settings.isEmpty()) {
            System.out.println("Không tìm thấy cài đặt nào trong cơ sở dữ liệu.");
        } else {
            System.out.println("Danh sách các cài đặt:");
            // Lặp qua từng cài đặt và in ra thông tin
            for (Setting setting : settings) {
                System.out.println("ID: " + setting.getId());
                System.out.println("Type: " + setting.getType());
                System.out.println("Key Name: " + setting.getKeyName());
                System.out.println("Value: " + setting.getValue());
                System.out.println("Description: " + setting.getDescription());
                System.out.println("Status: " + setting.getStatus());
                System.out.println("Created At: " + setting.getCreatedAt());
                System.out.println("Updated At: " + setting.getUpdatedAt());
                System.out.println("----------------------------");
            }
        }
    }

}
