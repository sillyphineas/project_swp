package model;

import entity.User;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    public List<User> getPaginatedUsers(int page, int pageSize) throws SQLException {
        String sql = "SELECT * FROM Users ORDER BY id ASC LIMIT ? OFFSET ?";
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
                        rs.getDate("resetTokenExpired"),
                        rs.getDate("dateOfBirth"),
                        rs.getShort("roleId"),
                        rs.getBoolean("isDisabled")));
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
                        rs.getDate("resetTokenExpired"),
                        rs.getDate("dateOfBirth"),
                        rs.getShort("roleId"),
                        rs.getBoolean("isDisabled")));
            }
        }

        return users;
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
                        rs.getDate("resetTokenExpired"),
                        rs.getDate("dateOfBirth"),
                        rs.getShort("roleId"),
                        rs.getBoolean("isDisabled")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
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
        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
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
                            rs.getDate("resetTokenExpired"),
                            rs.getDate("dateOfBirth"),
                            rs.getShort("roleId"),
                            rs.getBoolean("isDisabled")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void main(String[] args) {
        DAOUser dao = new DAOUser();
//        User user = dao.getUserById(2);
//        if (user != null) {
//            System.out.println(user);
//        } else {
//            System.out.println("User not found.");
//        }
        System.out.println(dao.searchUsers("haiductran712@gmail.com", 1, 10));
    }
}
