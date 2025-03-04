package model;

import entity.Setting;
import java.sql.*;
import java.util.Vector;

public class DAOSetting extends DBConnection {

    public int addSetting(Setting setting) {
        int n = 0;
        String sql = "INSERT INTO Settings (type_id, key_name, value, description, status, role_id, brand_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, setting.getTypeId());
            pre.setString(2, setting.getKeyName());
            pre.setString(3, setting.getValue());
            pre.setString(4, setting.getDescription());
            pre.setString(5, setting.getStatus());
            pre.setObject(6, setting.getRoleId());
            pre.setObject(7, setting.getBrandId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public int updateSetting(Setting setting) {
        int n = 0;
        String sql = "UPDATE Settings SET key_name = ?, value = ?, description = ?, status = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, setting.getKeyName());
            pre.setString(2, setting.getValue());
            pre.setString(3, setting.getDescription());
            pre.setString(4, setting.getStatus());
            pre.setInt(5, setting.getId());
            n = pre.executeUpdate();

            // Nếu có role_id, cập nhật Roles
            if (setting.getRoleId() != null) {
                PreparedStatement rolePs = conn.prepareStatement("UPDATE Roles SET roleName = ? WHERE roleId = ?");
                rolePs.setString(1, setting.getKeyName());
                rolePs.setInt(2, setting.getRoleId());
                rolePs.executeUpdate();
            }

            // Nếu có brand_id, cập nhật Brands
            if (setting.getBrandId() != null) {
                PreparedStatement brandPs = conn.prepareStatement("UPDATE Brands SET name = ? WHERE id = ?");
                brandPs.setString(1, setting.getKeyName());
                brandPs.setInt(2, setting.getBrandId());
                brandPs.executeUpdate();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

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

    public Vector<Setting> getAllSettings() {
        Vector<Setting> settings = new Vector<>();
        String sql = "SELECT s.*, st.type_name FROM Settings s JOIN SettingType st ON s.type_id = st.id";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Setting setting = new Setting(
                        rs.getInt("id"),
                        rs.getInt("type_id"),
                        rs.getString("type_name"),
                        rs.getString("key_name"),
                        rs.getString("value"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        (Integer) rs.getObject("role_id"),
                        (Integer) rs.getObject("brand_id")
                );
                settings.add(setting);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return settings;
    }

    public Setting getSettingById(int id) {
        String sql = "SELECT s.*, st.type_name FROM Settings s "
                + "JOIN SettingType st ON s.type_id = st.id WHERE s.id = ?";
        Setting setting = null;
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                setting = new Setting(
                        rs.getInt("id"),
                        rs.getInt("type_id"),
                        rs.getString("type_name"),
                        rs.getString("key_name"),
                        rs.getString("value"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        (Integer) rs.getObject("role_id"),
                        (Integer) rs.getObject("brand_id")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return setting;
    }

    public Vector<Setting> getSettingsWithPagination(int page, int itemsPerPage) {
        Vector<Setting> settings = new Vector<>();
        int startIndex = (page - 1) * itemsPerPage;

        String sql = "SELECT s.*, st.type_name FROM Settings s "
                + "JOIN SettingType st ON s.type_id = st.id "
                + "ORDER BY s.id DESC "
                + "LIMIT ? OFFSET ?";

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, itemsPerPage);
            pre.setInt(2, startIndex);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                Setting setting = new Setting(
                        rs.getInt("id"),
                        rs.getInt("type_id"),
                        rs.getString("type_name"),
                        rs.getString("key_name"),
                        rs.getString("value"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        (Integer) rs.getObject("role_id"),
                        (Integer) rs.getObject("brand_id")
                );
                settings.add(setting);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return settings;
    }

    public int getTotalSettingsCount() {
        String sql = "SELECT COUNT(*) FROM Settings";
        int count = 0;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public Vector<Setting> searchSettings(String keyword, int page, int itemsPerPage) {
        Vector<Setting> settings = new Vector<>();
        int startIndex = (page - 1) * itemsPerPage;

        String sql = "SELECT s.*, st.type_name FROM Settings s "
                + "JOIN SettingType st ON s.type_id = st.id "
                + "WHERE s.key_name LIKE ? OR s.value LIKE ? "
                + "ORDER BY s.id DESC LIMIT ? OFFSET ?";

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, "%" + keyword + "%");
            pre.setString(2, "%" + keyword + "%");
            pre.setInt(3, itemsPerPage);
            pre.setInt(4, startIndex);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                settings.add(new Setting(
                        rs.getInt("id"),
                        rs.getInt("type_id"),
                        rs.getString("type_name"),
                        rs.getString("key_name"),
                        rs.getString("value"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        (Integer) rs.getObject("role_id"),
                        (Integer) rs.getObject("brand_id")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return settings;
    }

    public int getTotalSearchCount(String keyword) {
        String sql = "SELECT COUNT(*) FROM Settings "
                + "WHERE key_name LIKE ? OR value LIKE ?";
        int count = 0;
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, "%" + keyword + "%");
            pre.setString(2, "%" + keyword + "%");
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public Vector<Setting> filterSettings(String status, String type, String createdAt, int page, int itemsPerPage) {
        Vector<Setting> settings = new Vector<>();
        int startIndex = (page - 1) * itemsPerPage;

        String sql = "SELECT s.*, st.type_name FROM Settings s "
                + "JOIN SettingType st ON s.type_id = st.id WHERE 1=1 ";

        if (!"All".equals(status)) {
            sql += "AND s.status = ? ";
        }
        if (!"All".equals(type)) {
            sql += "AND st.type_name = ? ";
        }
        if (createdAt != null && !createdAt.isEmpty()) {
            sql += "AND DATE(s.created_at) = ? ";
        }

        sql += "ORDER BY s.id DESC LIMIT ? OFFSET ?";

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            int index = 1;
            if (!"All".equals(status)) {
                pre.setString(index++, status);
            }
            if (!"All".equals(type)) {
                pre.setString(index++, type);
            }
            if (createdAt != null && !createdAt.isEmpty()) {
                pre.setString(index++, createdAt);
            }
            pre.setInt(index++, itemsPerPage);
            pre.setInt(index, startIndex);

            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                settings.add(new Setting(
                        rs.getInt("id"),
                        rs.getInt("type_id"),
                        rs.getString("type_name"),
                        rs.getString("key_name"),
                        rs.getString("value"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        (Integer) rs.getObject("role_id"),
                        (Integer) rs.getObject("brand_id")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return settings;
    }

    public int getTotalFilterCount(String status, String type, String createdAt) {
        String sql = "SELECT COUNT(*) FROM Settings s "
                + "JOIN SettingType st ON s.type_id = st.id WHERE 1=1 ";

        if (!"All".equals(status)) {
            sql += "AND s.status = ? ";
        }
        if (!"All".equals(type)) {
            sql += "AND st.type_name = ? ";
        }
        if (createdAt != null && !createdAt.isEmpty()) {
            sql += "AND DATE(s.created_at) = ? ";
        }

        int count = 0;
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            int index = 1;
            if (!"All".equals(status)) {
                pre.setString(index++, status);
            }
            if (!"All".equals(type)) {
                pre.setString(index++, type);
            }
            if (createdAt != null && !createdAt.isEmpty()) {
                pre.setString(index++, createdAt);
            }
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public Vector<Setting> sortSettings(String sortBy, String sortOrder, int page, int itemsPerPage) {
        Vector<Setting> settings = new Vector<>();
        int startIndex = (page - 1) * itemsPerPage;

        if (!"id".equals(sortBy) && !"key_name".equals(sortBy)) {
            sortBy = "id";
        }
        if (!"asc".equalsIgnoreCase(sortOrder) && !"desc".equalsIgnoreCase(sortOrder)) {
            sortOrder = "asc"; 
        }

        String query = "SELECT s.*, st.type_name FROM Settings s "
                + "JOIN SettingType st ON s.type_id = st.id "
                + "ORDER BY " + sortBy + " " + sortOrder + " LIMIT ? OFFSET ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, itemsPerPage);
            ps.setInt(2, startIndex);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                settings.add(new Setting(
                        rs.getInt("id"),
                        rs.getInt("type_id"),
                        rs.getString("type_name"),
                        rs.getString("key_name"),
                        rs.getString("value"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        (Integer) rs.getObject("role_id"),
                        (Integer) rs.getObject("brand_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return settings;
    }

}
