package model;

import entity.User;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author HP
 */
public class DAOUser extends DBConnection {

    public int addUser2(User user) {
        int n = 0;
        String sql = "INSERT INTO Users (email, passHash, roleId, isDisabled, updatedBy, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, user.getEmail());
            pre.setString(2, user.getPassHash());
            pre.setInt(3, user.getRoleId());
            pre.setBoolean(4, user.isIsDisabled());
            pre.setInt(5, user.getUpdatedBy());
            pre.setDate(6, user.getUpdatedAt()); // Sửa thành updatedAt
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public int addUser(User user) {
        int n = 0;
        String sql = "INSERT INTO Users (name, email, passHash, gender, phoneNumber, resetToken, resetTokenExpired, dateOfBirth, roleId, isDisabled, updatedBy, updated_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, user.getName());
            pre.setString(2, user.getEmail());
            pre.setString(3, user.getPassHash());
            pre.setBoolean(4, user.isGender());
            pre.setString(5, user.getPhoneNumber());
            pre.setString(6, user.getResetToken());
            pre.setTimestamp(7, user.getResetTokenExpired());
            pre.setDate(8, user.getDateOfBirth());
            pre.setInt(9, user.getRoleId());
            pre.setBoolean(10, user.isDisabled());
            pre.setInt(11, user.getUpdatedBy());
            pre.setDate(12, user.getUpdatedAt());

            n = pre.executeUpdate();  // 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public void updateResetToken(int userId, String token, Timestamp expiry) {
        String sql = "UPDATE Users SET resetToken = ?, resetTokenExpired = ? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, token);
            ps.setTimestamp(2, expiry);
            ps.setInt(3, userId);

            int rows = ps.executeUpdate();
            System.out.println("Rows updated = " + rows); // <-- In ra số dòng bị ảnh hưởng

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vector<User> getUsers(String sql) {
        Vector<User> vector = new Vector<>();
        try {
            Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("passHash"),
                        rs.getBoolean("gender"),
                        rs.getString("phoneNumber"),
                        rs.getString("resetToken"),
                        rs.getTimestamp("resetTokenExpired"),
                        rs.getDate("DateOfBirth"),
                        rs.getInt("roleId"),
                        rs.getBoolean("isDisabled"),
                        rs.getInt("updatedBy"), // Lấy giá trị updatedBy
                        rs.getDate("updated_at"), // Lấy giá trị updatedDate
                        rs.getBytes("image")
                );
                vector.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM Users WHERE id = ?";
        User user = null;
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("passHash"),
                        rs.getBoolean("gender"),
                        rs.getString("phoneNumber"),
                        rs.getString("resetToken"),
                        rs.getTimestamp("resetTokenExpired"),
                        rs.getDate("DateOfBirth"),
                        rs.getInt("roleId"),
                        rs.getBoolean("isDisabled"),
                        rs.getInt("updatedBy"),
                        rs.getDate("updated_at"), // Thay "updatedDate" bằng "updated_at"
                        rs.getBytes("image")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        User user = null;
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, email);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("passHash"),
                        rs.getBoolean("gender"),
                        rs.getString("phoneNumber"),
                        rs.getString("resetToken"),
                        rs.getTimestamp("resetTokenExpired"),
                        rs.getDate("DateOfBirth"),
                        rs.getInt("roleId"),
                        rs.getBoolean("isDisabled"),
                        rs.getInt("updatedBy"), // Lấy giá trị updatedBy
                        rs.getDate("updated_at"),
                        rs.getBytes("image")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public int updatePassword(int userId, String newPassHash) {
        int n = 0;
        String sql = "UPDATE Users SET passHash = ? WHERE id = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, newPassHash);
            pre.setInt(2, userId);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }
    
    public User getUserByResetToken(String token) {
        User user = null;
        String sql = "SELECT * FROM users WHERE resetToken = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, token);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassHash(rs.getString("passHash"));
                    user.setGender(rs.getInt("gender") == 1);
                    user.setPhoneNumber(rs.getString("phoneNumber"));
                    user.setResetToken(rs.getString("resetToken"));
                    java.sql.Timestamp tsReset = rs.getTimestamp("resetTokenExpired");
                    if (tsReset != null) {
                        user.setResetTokenExpired(tsReset);
                    }
                    java.sql.Timestamp tsDOB = rs.getTimestamp("dateOfBirth");
                    if (tsDOB != null) {
                        user.setDateOfBirth(new java.sql.Date(tsDOB.getTime()));
                    }
                    user.setRoleId(rs.getInt("roleId"));
                    user.setIsDisabled(rs.getInt("isDisabled") == 1);

                    user.setUpdatedBy(rs.getInt("updatedBy"));
                    java.sql.Timestamp tsUpdated = rs.getTimestamp("updated_at");
                    if (tsUpdated != null) {
                        user.setUpdatedAt(new java.sql.Date(tsUpdated.getTime()));
                    }
                    user.setImage(rs.getBytes("image"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void updatePasswordAndClearToken(int userId, String hashedPassword) {
        String sql = "UPDATE Users "
                + "SET passHash = ?, resetToken = NULL, resetTokenExpired = NULL "
                + "WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hashedPassword);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM Users WHERE phoneNumber = ?";
        User user = null;
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, phoneNumber);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("passHash"),
                        rs.getBoolean("gender"),
                        rs.getString("phoneNumber"),
                        rs.getString("resetToken"),
                        rs.getTimestamp("resetTokenExpired"),
                        rs.getDate("DateOfBirth"),
                        rs.getInt("roleId"),
                        rs.getBoolean("isDisabled"),
                        rs.getInt("updatedBy"), // Lấy giá trị updatedBy
                        rs.getDate("updated_at"),
                        rs.getBytes("image")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public int updateUser(User user) {
        int n = 0;
        String sql = "UPDATE Users SET name = ?, email = ?, passHash = ?, gender = ?,"
                + " phoneNumber = ?, resetToken = ?, resetTokenExpired = ?,"
                + " DateOfBirth = ?, roleId = ?, isDisabled = ?, updatedBy = ?, updated_at = ? WHERE id = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, user.getName());
            pre.setString(2, user.getEmail());
            pre.setString(3, user.getPassHash());
            pre.setBoolean(4, user.isGender());
            pre.setString(5, user.getPhoneNumber());
            pre.setString(6, user.getResetToken());
            pre.setTimestamp(7, user.getResetTokenExpired());
            pre.setDate(8, user.getDateOfBirth());
            pre.setInt(9, user.getRoleId());
            pre.setBoolean(10, user.isIsDisabled());
            pre.setInt(11, user.getUpdatedBy());
            pre.setDate(12, user.getUpdatedAt());
            pre.setInt(13, user.getId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;

    }

    public int updateUser2(User user) {
        int n = 0;
        String sql = "UPDATE Users SET name = ?, email = ?, passHash = ?, gender = ?, "
                + "phoneNumber = ?, resetToken = ?, resetTokenExpired = ?, "
                + "DateOfBirth = ?, roleId = ?, isDisabled = ?, updatedBy = ?, updated_at = ?, image = ? WHERE id = ?";

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, user.getName());
            pre.setString(2, user.getEmail());
            pre.setString(3, user.getPassHash());
            pre.setBoolean(4, user.isGender());
            pre.setString(5, user.getPhoneNumber());
            pre.setString(6, user.getResetToken());
            pre.setTimestamp(7, user.getResetTokenExpired());
            pre.setDate(8, user.getDateOfBirth());
            pre.setInt(9, user.getRoleId());
            pre.setBoolean(10, user.isIsDisabled());
            pre.setInt(11, user.getUpdatedBy());
            pre.setDate(12, user.getUpdatedAt());

            // Lưu trữ ảnh dạng BLOB
            pre.setBytes(13, user.getImage());
            pre.setInt(14, user.getId());

            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public int deleteUser(int id) {
        int n = 0;
        String sql = "DELETE FROM Users WHERE id = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public List<User> getPaginatedUsers(int page, int pageSize) throws SQLException {
        String sql = "SELECT * FROM Users WHERE roleId <> 1 ORDER BY id ASC LIMIT ? OFFSET ?";
        List<User> users = new ArrayList<>();

        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, pageSize);
            pre.setInt(2, (page - 1) * pageSize);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                users.add(new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("passHash"),
                        rs.getBoolean("gender"),
                        rs.getString("phoneNumber"),
                        rs.getString("resetToken"),
                        rs.getTimestamp("resetTokenExpired"),
                        rs.getDate("DateOfBirth"),
                        rs.getInt("roleId"),
                        rs.getBoolean("isDisabled"),
                        rs.getInt("updatedBy"), // Lấy giá trị updatedBy
                        rs.getDate("updated_at"),
                        rs.getBytes("image")));

            }
        }
        return users;
    }

    public int getTotalUsers() {
        int totalUsers = 0;
        String sql = "SELECT COUNT(*) FROM Users";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql); ResultSet rs = preparedStatement.executeQuery()) {
            if (rs.next()) {
                totalUsers = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalUsers;
    }

    public List<User> getFilteredUsers(int page, int pageSize, Boolean gender, Integer roleId, Boolean isDisabled) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM Users WHERE 1 = 1 ");

        if (gender != null) {
            sql.append("AND gender = ? ");
        }
        if (roleId != null) {
            sql.append("AND roleId = ? ");
        }
        if (isDisabled != null) {
            sql.append("AND isDisabled = ? ");
        }

        sql.append("ORDER BY id ASC LIMIT ? OFFSET ?");

        List<User> users = new ArrayList<>();

        try (PreparedStatement pre = conn.prepareStatement(sql.toString())) {
            int index = 1;

            if (gender != null) {
                pre.setBoolean(index++, gender);
            }
            if (roleId != null) {
                pre.setInt(index++, roleId);
            }
            if (isDisabled != null) {
                pre.setBoolean(index++, isDisabled);
            }

            pre.setInt(index++, pageSize);
            pre.setInt(index++, (page - 1) * pageSize);

            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                users.add(new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("passHash"),
                        rs.getBoolean("gender"),
                        rs.getString("phoneNumber"),
                        rs.getString("resetToken"),
                        rs.getTimestamp("resetTokenExpired"),
                        rs.getDate("DateOfBirth"),
                        rs.getInt("roleId"),
                        rs.getBoolean("isDisabled"),
                        rs.getInt("updatedBy"),
                        rs.getDate("updated_at"),
                        rs.getBytes("image")));
            }
        }

        return users;
    }

    public boolean updateUserStatus(int userId, boolean status) {
        String query = "UPDATE Users SET isDisabled = ? WHERE id = ? AND roleId <> 1";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setBoolean(1, status);
            pst.setInt(2, userId);
            int rowsUpdated = pst.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getTotalUsers(Boolean gender, Integer roleId, Boolean isDisabled) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Users WHERE 1 = 1 ");

        if (gender != null) {
            sql.append("AND gender = ? ");
        }
        if (roleId != null) {
            sql.append("AND roleId = ? ");
        }
        if (isDisabled != null) {
            sql.append("AND isDisabled = ? ");
        }

        try (PreparedStatement pre = conn.prepareStatement(sql.toString())) {
            int index = 1;

            if (gender != null) {
                pre.setBoolean(index++, gender);
            }
            if (roleId != null) {
                pre.setInt(index++, roleId);
            }
            if (isDisabled != null) {
                pre.setBoolean(index++, isDisabled);
            }

            ResultSet rs = pre.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }

    public List<User> searchUsers(String query, int page, int pageSize) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE name LIKE ? OR email LIKE ? OR phoneNumber LIKE ? LIMIT ?, ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchQuery = "%" + query + "%";
            stmt.setString(1, searchQuery);
            stmt.setString(2, searchQuery);
            stmt.setString(3, searchQuery);
            stmt.setInt(4, (page - 1) * pageSize);
            stmt.setInt(5, pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("passHash"),
                        rs.getBoolean("gender"),
                        rs.getString("phoneNumber"),
                        rs.getString("resetToken"),
                        rs.getTimestamp("resetTokenExpired"),
                        rs.getDate("DateOfBirth"),
                        rs.getInt("roleId"),
                        rs.getBoolean("isDisabled"),
                        rs.getInt("updatedBy"), // Lấy giá trị updatedBy
                        rs.getDate("updated_at"),
                        rs.getBytes("image")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public String getUserEmailByUserID(int userId) {
        String sql = "SELECT email FROM Users WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    
    public int countTotalUsers(String query) {
        int totalUsers = 0;
        String sql = "SELECT COUNT(*) FROM Users WHERE name LIKE ? OR email LIKE ? OR phoneNumber LIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchQuery = "%" + query + "%";
            stmt.setString(1, searchQuery);
            stmt.setString(2, searchQuery);
            stmt.setString(3, searchQuery);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalUsers = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalUsers;
    }

    public List<User> sortUsers(String sortBy, String sortOrder, int page, int pageSize) {
        String sql = "SELECT * FROM Users ORDER BY " + sortBy + " " + sortOrder
                + " LIMIT ? OFFSET ?";
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pageSize);
            stmt.setInt(2, (page - 1) * pageSize);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("passHash"),
                            rs.getBoolean("gender"),
                            rs.getString("phoneNumber"),
                            rs.getString("resetToken"),
                            rs.getTimestamp("resetTokenExpired"),
                            rs.getDate("DateOfBirth"),
                            rs.getInt("roleId"),
                            rs.getBoolean("isDisabled"),
                            rs.getInt("updatedBy"), // Lấy giá trị updatedBy
                            rs.getDate("updated_at"),
                            rs.getBytes("image")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public Vector<User> getCustomers(int page, int pageSize, String filterStatus, String searchQuery) {
        Vector<User> customers = new Vector<>();
        String sql = "SELECT * FROM Users WHERE roleId = 5 ";

        if (filterStatus != null && !filterStatus.isEmpty()) {
            sql += "AND isDisabled = ? ";
        }
        if (searchQuery != null && !searchQuery.isEmpty()) {
            sql += "AND (name LIKE ? OR email LIKE ? OR phoneNumber LIKE ?) ";
        }

        sql += "LIMIT ? OFFSET ?"; // phân trang

        try {
            PreparedStatement pre = conn.prepareStatement(sql);

            int paramIndex = 1;

            // Thêm filter trạng thái
            if (filterStatus != null && !filterStatus.isEmpty()) {
                pre.setBoolean(paramIndex++, Boolean.parseBoolean(filterStatus));
            }

            // Thêm tìm kiếm
            if (searchQuery != null && !searchQuery.isEmpty()) {
                String searchPattern = "%" + searchQuery + "%";
                pre.setString(paramIndex++, searchPattern);
                pre.setString(paramIndex++, searchPattern);
                pre.setString(paramIndex++, searchPattern);
            }

            // Thêm phân trang
            pre.setInt(paramIndex++, pageSize);
            pre.setInt(paramIndex, (page - 1) * pageSize); // OFFSET

            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("passHash"),
                        rs.getBoolean("gender"),
                        rs.getString("phoneNumber"),
                        rs.getString("resetToken"),
                        rs.getTimestamp("resetTokenExpired"),
                        rs.getDate("DateOfBirth"),
                        rs.getInt("roleId"),
                        rs.getBoolean("isDisabled"),
                        rs.getInt("updatedBy"), // Lấy giá trị updatedBy
                        rs.getDate("updated_at"),
                        rs.getBytes("image")
                );
                customers.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return customers;
    }

    public int getTotalCustomers(String filterStatus, String searchQuery) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM Users WHERE roleId = 5 ";

        if (filterStatus != null && !filterStatus.isEmpty()) {
            sql += "AND isDisabled = ? ";
        }
        if (searchQuery != null && !searchQuery.isEmpty()) {
            sql += "AND (name LIKE ? OR email LIKE ? OR phoneNumber LIKE ?) ";
        }

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            int paramIndex = 1;

            if (filterStatus != null && !filterStatus.isEmpty()) {
                pre.setBoolean(paramIndex++, Boolean.parseBoolean(filterStatus));
            }

            if (searchQuery != null && !searchQuery.isEmpty()) {
                String searchPattern = "%" + searchQuery + "%";
                pre.setString(paramIndex++, searchPattern);
                pre.setString(paramIndex++, searchPattern);
                pre.setString(paramIndex++, searchPattern);
            }

            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return total;
    }

    public int updateCustomer(User user) {
        int n = 0;
        String sql = "UPDATE Users SET name = ?, email = ?, passHash = ?, gender = ?, phoneNumber = ?, resetToken = ?, resetTokenExpired = ?, DateOfBirth = ?, roleId = ?, isDisabled = ?, updatedBy = ?, updated_at = ? WHERE id = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, user.getName());
            pre.setString(2, user.getEmail());
            pre.setString(3, user.getPassHash());
            pre.setBoolean(4, user.isGender());
            pre.setString(5, user.getPhoneNumber());
            pre.setString(6, user.getResetToken());
            pre.setTimestamp(7, user.getResetTokenExpired());
            pre.setDate(8, user.getDateOfBirth());
            pre.setInt(9, user.getRoleId());
            pre.setBoolean(10, user.isIsDisabled());
            pre.setInt(11, user.getUpdatedBy());  // Đặt giá trị updatedBy
            pre.setDate(12, user.getUpdatedAt()); // Đặt giá trị updatedDate
            pre.setInt(13, user.getId());

            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public int changeStatus(int customerId) {
        int result = 0;
        String sql = "UPDATE users SET isDisabled = CASE WHEN isDisabled = 0 THEN 1 ELSE 0 END WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Map<String, Object>> getCustomerChangeHistory(int customerId) {
        List<Map<String, Object>> changeHistory = new ArrayList<>();
        String sql = "SELECT u.email, u.name, u.gender, u.phoneNumber, u.updatedBy, u.updated_at, "
                + "   u2.name AS updatedByName "
                + "FROM Users u "
                + "LEFT JOIN Users u2 ON u.updatedBy = u2.id "
                + "WHERE u.id = ? "
                + "ORDER BY u.updated_at DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> history = new HashMap<>();
                history.put("email", rs.getString("email"));
                history.put("name", rs.getString("name"));
                history.put("gender", rs.getBoolean("gender"));
                history.put("phoneNumber", rs.getString("phoneNumber"));
                history.put("updatedByName", rs.getString("updatedByName"));
                history.put("updatedAt", rs.getDate("updated_at"));

                changeHistory.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return changeHistory;
    }

    public boolean isEmailExists(String email) {
        boolean exists = false;
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public static void main(String[] args) {

    }
}
