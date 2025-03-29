package model;

import entity.Voucher;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOVoucher extends DBConnection {

    // Thêm voucher mới
    public int addVoucher(Voucher voucher) {
        int n = 0;
        String sql = "INSERT INTO Vouchers (voucherCode, voucherTypeID, description, point, startDate, expiredDate, usageLimit, isDisabled, value) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, voucher.getVoucherCode());
            pre.setInt(2, voucher.getvoucherTypeID());
            pre.setString(3, voucher.getDescription());
            pre.setInt(4, voucher.getPoint());
            pre.setDate(5, (Date) voucher.getStartDate());
            pre.setDate(6, (Date) voucher.getExpiredDate());
            pre.setInt(7, voucher.getUsageLimit());
            pre.setBoolean(8, voucher.isIsDisabled());
            pre.setInt(9, voucher.getValue());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    // Lấy danh sách voucher với câu SQL tuỳ ý
    public Vector<Voucher> getVouchers(String sql) {
        Vector<Voucher> vector = new Vector<>();
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
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
                vector.add(voucher);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOVoucher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public Vector<Voucher> getRedeemedVouchers(int userId) {
        Vector<Voucher> redeemedList = new Vector<>();
        String sql = "SELECT v.* FROM Vouchers v " +
                     "JOIN UserVouchers uv ON v.voucherID = uv.voucher_id " +
                     "WHERE uv.user_id = ?";
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

    // Lấy 1 voucher theo ID
    public Voucher getVoucherByVoucherId(int voucherId) {
        String sql = "SELECT * FROM Vouchers WHERE voucherID = ?";
        Voucher voucher = null;
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, voucherId);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    voucher = new Voucher(
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
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return voucher;
    }

    // Cập nhật voucher
    public int updateVoucher(Voucher voucher) {
        int n = 0;
        String sql = "UPDATE Vouchers "
                + "SET voucherCode = ?, voucherTypeID = ?, description = ?, point = ?, "
                + "startDate = ?, expiredDate = ?, usageLimit = ?, isDisabled = ?, value = ? "
                + "WHERE voucherID = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, voucher.getVoucherCode());
            pre.setInt(2, voucher.getvoucherTypeID());
            pre.setString(3, voucher.getDescription());
            pre.setInt(4, voucher.getPoint());
            pre.setDate(5, (Date) voucher.getStartDate());
            pre.setDate(6, (Date) voucher.getExpiredDate());
            pre.setInt(7, voucher.getUsageLimit());
            pre.setBoolean(8, voucher.isIsDisabled());
            pre.setInt(9, voucher.getValue());
            pre.setInt(10, voucher.getVoucherID());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    // Xoá voucher
    public int deleteVoucher(int voucherId) {
        int n = 0;
        String sql = "DELETE FROM Vouchers WHERE voucherID = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, voucherId);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    // (Tuỳ ý) Lấy voucherCode từ order
    public String getVoucherCodeByOrderId(int orderId) {
        // Tạm để trống nếu chưa cần
        return null;
    }
    
    public int getTotalVouchersCount() {
        String sql = "SELECT COUNT(*) FROM Vouchers";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public Vector<Voucher> searchVouchers(String keyword) {
        Vector<Voucher> voucherList = new Vector<>();
        String sql = "SELECT * FROM Vouchers WHERE voucherCode LIKE ? OR description LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
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
                    voucherList.add(voucher);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voucherList;
    }
    
    public int getTotalSearchCount(String keyword) {
        String sql = "SELECT COUNT(*) FROM Vouchers WHERE voucherCode LIKE ? OR description LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
