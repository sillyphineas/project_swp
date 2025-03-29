package model;

import entity.Voucher;
import java.sql.*;
import java.util.Vector;

public class DAOUserVoucher extends DBConnection {

    // Kiểm tra user đã từng đổi voucher này chưa
    public boolean hasRedeemed(int userId, int voucherId) {
        String sql = "SELECT * FROM UserVouchers WHERE user_id = ? AND voucher_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, voucherId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // nếu có record => đã đổi
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Ghi nhận user đã đổi voucher
    public boolean insertUserVoucher(int userId, int voucherId) {
        String sql = "INSERT INTO UserVouchers (user_id, voucher_id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, voucherId);
            int n = ps.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy danh sách voucher đã đổi của một user
    public Vector<Voucher> getRedeemedVouchers(int userId) {
        Vector<Voucher> redeemedList = new Vector<>();
        String sql = "SELECT v.* FROM Vouchers v JOIN UserVouchers uv ON v.voucherID = uv.voucher_id WHERE uv.user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Voucher voucher = new Voucher(
                            rs.getInt("voucherID"),
                            rs.getString("voucherCode"),
                            rs.getInt("voucherTypeID"),
                            rs.getString("description"),
                            rs.getInt("point"),
                            rs.getDate("startDate"),
                            rs.getDate("expiredDate"),
                            rs.getInt("usageLimit"),
                            rs.getBoolean("isDisabled"),
                            rs.getInt("value")
                    );
                    redeemedList.add(voucher);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return redeemedList;
    }

    // Transaction: trừ điểm và insert vào UserVouchers
    public boolean redeemVoucherTransaction(int userId, int voucherId, int pointsNeeded) {
        DAOUser daoUser = new DAOUser();
        try {
            conn.setAutoCommit(false);
            int currentPoints = daoUser.getUserPoints(userId);
            int newPoints = currentPoints - pointsNeeded;
            daoUser.updateUserPoint(userId, newPoints);
            String sql = "INSERT INTO UserVouchers (user_id, voucher_id) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ps.setInt(2, voucherId);
                int inserted = ps.executeUpdate();
                if (inserted <= 0) {
                    conn.rollback();
                    return false;
                }
            }
            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Lấy danh sách voucher đã đổi của một user có voucherTypeID = 1
    public Vector<Voucher> getRedeemedVouchersByType(int userId, int voucherTypeID) {
        Vector<Voucher> redeemedList = new Vector<>();
        String sql = "SELECT v.* FROM Vouchers v "
                + "JOIN UserVouchers uv ON v.voucherID = uv.voucher_id "
                + "WHERE uv.user_id = ? AND v.voucherTypeID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, voucherTypeID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Voucher voucher = new Voucher(
                            rs.getInt("voucherID"),
                            rs.getString("voucherCode"),
                            rs.getInt("voucherTypeID"),
                            rs.getString("description"),
                            rs.getInt("point"),
                            rs.getDate("startDate"),
                            rs.getDate("expiredDate"),
                            rs.getInt("usageLimit"),
                            rs.getBoolean("isDisabled"),
                            rs.getInt("value")
                    );
                    redeemedList.add(voucher);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return redeemedList;
    }

    public boolean deleteUserVoucher(int userId, int voucherId) {
        String sql = "DELETE FROM UserVouchers WHERE user_id = ? AND voucher_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, voucherId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
