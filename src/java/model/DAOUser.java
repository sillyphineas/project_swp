package model;

import entity.User;
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
public class DAOUser extends DBConnection {

    public int addUser(User user) {
        int n = 0;
        String sql = "INSERT INTO Users (email, passHash, roleId, isDisabled)\n"
                + "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, user.getEmail());
            pre.setString(2, user.getPassHash());
            pre.setInt(3, user.getRoleId());
            pre.setBoolean(4, user.isDisabled());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
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
                        rs.getDate("resetTokenExpired"),
                        rs.getDate("DateOfBirth"),
                        rs.getInt("roleId"),
                        rs.getBoolean("isDisabled")
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
                        rs.getDate("resetTokenExpired"),
                        rs.getDate("DateOfBirth"),
                        rs.getInt("roleId"),
                        rs.getBoolean("isDisabled")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
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
                        rs.getDate("resetTokenExpired"),
                        rs.getDate("DateOfBirth"),
                        rs.getInt("roleId"),
                        rs.getBoolean("isDisabled")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public int updateUser(User user) {
        int n = 0;
        String sql = "UPDATE Users "
                + "SET name = ?, email = ?, passHash = ?, gender = ?, phoneNumber = ?, resetToken = ?, resetTokenExpired = ?, Address = ?, DateOfBirth = ?, roleId = ?, isDisabled = ? "
                + "WHERE id = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, user.getName());
            pre.setString(2, user.getEmail());
            pre.setString(3, user.getPassHash());
            pre.setBoolean(4, user.isGender());
            pre.setString(5, user.getPhoneNumber());
            pre.setString(6, user.getResetToken());
            pre.setDate(7, user.getResetTokenExpired());
            pre.setDate(8, user.getDateOfBirth());
            pre.setInt(9, user.getRoleId());
            pre.setBoolean(10, user.isDisabled());
            pre.setInt(11, user.getId());

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
                        rs.getDate("resetTokenExpired"),
                        rs.getDate("dateOfBirth"),
                        rs.getInt("roleId"),
                        rs.getBoolean("isDisabled")
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
        String sql = "UPDATE Users SET email = ?, phoneNumber = ?, isDisabled = ? WHERE id = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, user.getEmail());
            pre.setString(2, user.getPhoneNumber());
            pre.setBoolean(3, user.isDisabled());
            pre.setInt(4, user.getId());

            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public static void main(String[] args) {

        DAOUser dao = new DAOUser();

        // Tạo đối tượng User cần cập nhật
        User user = new User();
        user.setId(1); // Giả sử người dùng có ID là 1
        user.setEmail("newemail@example.com"); // Cập nhật email
        user.setPhoneNumber("1234567890"); // Cập nhật số điện thoại
        user.setDisabled(false); // Cập nhật trạng thái (false = chưa bị khóa)

        // Gọi phương thức updateCustomer
        int result = dao.updateCustomer(user);

        // In ra kết quả cập nhật
        if (result > 0) {
            System.out.println("Cập nhật thông tin người dùng thành công.");
        } else {
            System.out.println("Cập nhật thông tin người dùng thất bại.");
        }
    }
}
