package model;

import entity.Payment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

/**
 * Giả sử DBConnection đã có sẵn biến 'conn' kiểu Connection
 * và các phương thức kết nối cơ bản.
 */
public class DAOPayment extends DBConnection {

    /**
     * Thêm một Payment mới vào bảng Payment.
     * @param payment đối tượng Payment cần thêm
     * @return số dòng bị ảnh hưởng (thành công > 0)
     */
    public int addPayment(Payment payment) {
        int n = 0;
        String sql = "INSERT INTO Payment(orderId, paymentStatus, paymentTime, paymentMethodId, "
                   + "amount, note, confirmBy, createdDate, updatedDate, isDisabled) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            // orderId
            ps.setInt(1, payment.getOrderId());
            
            // paymentStatus
            ps.setString(2, payment.getPaymentStatus());
            
            // paymentTime -> Timestamp
            if (payment.getPaymentTime() != null) {
                ps.setTimestamp(3, new Timestamp(payment.getPaymentTime().getTime()));
            } else {
                ps.setTimestamp(3, null);
            }
            
            // paymentMethodId
            ps.setInt(4, payment.getPaymentMethodId());
            
            // amount
            ps.setDouble(5, payment.getAmount());
            
            // note
            ps.setString(6, payment.getNote());
            
            // confirmBy
            ps.setInt(7, payment.getConfirmBy());
            
            // createdDate
            if (payment.getCreatedDate() != null) {
                ps.setTimestamp(8, new Timestamp(payment.getCreatedDate().getTime()));
            } else {
                ps.setTimestamp(8, null);
            }
            
            // updatedDate
            if (payment.getUpdatedDate() != null) {
                ps.setTimestamp(9, new Timestamp(payment.getUpdatedDate().getTime()));
            } else {
                ps.setTimestamp(9, null);
            }
            
            // isDisabled
            ps.setBoolean(10, payment.isIsDisabled());
            
            n = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    /**
     * Cập nhật thông tin một Payment dựa trên paymentId.
     * @param payment đối tượng Payment (chứa paymentId)
     * @return số dòng bị ảnh hưởng (thành công > 0)
     */
    public int updatePayment(Payment payment) {
        int n = 0;
        String sql = "UPDATE Payment SET "
                   + "orderId=?, paymentStatus=?, paymentTime=?, paymentMethodId=?, "
                   + "amount=?, note=?, confirmBy=?, createdDate=?, updatedDate=?, "
                   + "isDisabled=? "
                   + "WHERE paymentId=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            // orderId
            ps.setInt(1, payment.getOrderId());
            
            // paymentStatus
            ps.setString(2, payment.getPaymentStatus());
            
            // paymentTime
            if (payment.getPaymentTime() != null) {
                ps.setTimestamp(3, new Timestamp(payment.getPaymentTime().getTime()));
            } else {
                ps.setTimestamp(3, null);
            }
            
            // paymentMethodId
            ps.setInt(4, payment.getPaymentMethodId());
            
            // amount
            ps.setDouble(5, payment.getAmount());
            
            // note
            ps.setString(6, payment.getNote());
            
            // confirmBy
            ps.setInt(7, payment.getConfirmBy());
            
            // createdDate
            if (payment.getCreatedDate() != null) {
                ps.setTimestamp(8, new Timestamp(payment.getCreatedDate().getTime()));
            } else {
                ps.setTimestamp(8, null);
            }
            
            // updatedDate
            if (payment.getUpdatedDate() != null) {
                ps.setTimestamp(9, new Timestamp(payment.getUpdatedDate().getTime()));
            } else {
                ps.setTimestamp(9, null);
            }
            
            // isDisabled
            ps.setBoolean(10, payment.isIsDisabled());
            
            // WHERE paymentId=?
            ps.setInt(11, payment.getPaymentId());
            
            n = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    /**
     * Xóa một Payment theo paymentId.
     * @param paymentId ID của payment cần xóa
     * @return số dòng bị ảnh hưởng (thành công > 0)
     */
    public int deletePayment(int paymentId) {
        int n = 0;
        String sql = "DELETE FROM Payment WHERE paymentId = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, paymentId);
            n = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    /**
     * Lấy danh sách Payment (có thể truyền vào điều kiện tùy ý).
     * @param sql câu lệnh SELECT tùy chỉnh
     * @return danh sách Payment
     */
    public Vector<Payment> getPayments(String sql) {
        Vector<Payment> vector = new Vector<>();
        try {
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Payment payment = new Payment(
                        rs.getInt("paymentId"),
                        rs.getInt("orderId"),
                        rs.getString("paymentStatus"),
                        rs.getTimestamp("paymentTime"),
                        rs.getInt("paymentMethodId"),
                        rs.getDouble("amount"),
                        rs.getString("note"),
                        rs.getInt("confirmBy"),
                        rs.getTimestamp("createdDate"),
                        rs.getTimestamp("updatedDate"),
                        rs.getBoolean("isDisabled")
                );
                vector.add(payment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    /**
     * Lấy một Payment theo paymentId.
     * @param paymentId ID của Payment
     * @return Payment nếu tìm thấy, null nếu không
     */
    public Payment getPaymentById(int paymentId) {
        Payment payment = null;
        String sql = "SELECT * FROM Payment WHERE paymentId = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, paymentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                payment = new Payment(
                        rs.getInt("paymentId"),
                        rs.getInt("orderId"),
                        rs.getString("paymentStatus"),
                        rs.getTimestamp("paymentTime"),
                        rs.getInt("paymentMethodId"),
                        rs.getDouble("amount"),
                        rs.getString("note"),
                        rs.getInt("confirmBy"),
                        rs.getTimestamp("createdDate"),
                        rs.getTimestamp("updatedDate"),
                        rs.getBoolean("isDisabled")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return payment;
    }

    /**
     * Lấy danh sách Payment theo orderId.
     * @param orderId ID của Order
     * @return danh sách Payment (Vector)
     */
    public Vector<Payment> getPaymentsByOrderId(int orderId) {
        Vector<Payment> vector = new Vector<>();
        String sql = "SELECT * FROM Payment WHERE orderId = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Payment payment = new Payment(
                        rs.getInt("paymentId"),
                        rs.getInt("orderId"),
                        rs.getString("paymentStatus"),
                        rs.getTimestamp("paymentTime"),
                        rs.getInt("paymentMethodId"),
                        rs.getDouble("amount"),
                        rs.getString("note"),
                        rs.getInt("confirmBy"),
                        rs.getTimestamp("createdDate"),
                        rs.getTimestamp("updatedDate"),
                        rs.getBoolean("isDisabled")
                );
                vector.add(payment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
}
