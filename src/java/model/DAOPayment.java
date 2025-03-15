package model;

import entity.Payment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.sql.Types;


public class DAOPayment extends DBConnection {

    /**
     * Thêm một Payment mới vào bảng Payment.
     *
     * @param payment đối tượng Payment cần thêm
     * @return số dòng bị ảnh hưởng (thành công > 0)
     */
    public int addPayment(Payment payment) {
        int n = 0;
        // Bảng Payment hiện có 9 cột, ta chỉ insert 7 cột (không đụng đến createdDate, updatedDate):
        String sql = "INSERT INTO Payment("
                + "orderId, paymentStatus, paymentTime, paymentMethodId, "
                + "amount, note, confirmBy"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            // 1) orderId
            ps.setInt(1, payment.getOrderId());

            // 2) paymentStatus
            ps.setString(2, payment.getPaymentStatus());

            // 3) paymentTime
            if (payment.getPaymentTime() != null) {
                ps.setTimestamp(3, new Timestamp(payment.getPaymentTime().getTime()));
            } else {
                ps.setTimestamp(3, null);
            }

            // 4) paymentMethodId
            ps.setInt(4, payment.getPaymentMethodId());

            // 5) amount
            ps.setDouble(5, payment.getAmount());

            // 6) note
            ps.setString(6, payment.getNote());

            // 7) confirmBy
            if (payment.getConfirmBy() == null) {
                ps.setNull(7, Types.INTEGER);
            } else {
                ps.setInt(7, payment.getConfirmBy());
            }

            n = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int updatePayment(Payment payment) {
        int n = 0;
        String sql = "UPDATE Payment SET "
                + "orderId=?, paymentStatus=?, paymentTime=?, paymentMethodId=?, "
                + "amount=?, note=?, confirmBy=?, createdDate=?, updatedDate=? "
                + "WHERE paymentId=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, payment.getOrderId());
            ps.setString(2, payment.getPaymentStatus());
            if (payment.getPaymentTime() != null) {
                ps.setTimestamp(3, new Timestamp(payment.getPaymentTime().getTime()));
            } else {
                ps.setTimestamp(3, null);
            }
            ps.setInt(4, payment.getPaymentMethodId());
            ps.setDouble(5, payment.getAmount());
            ps.setString(6, payment.getNote());

            // confirmBy
            if (payment.getConfirmBy() == null) {
                ps.setNull(7, Types.INTEGER);
            } else {
                ps.setInt(7, payment.getConfirmBy());
            }

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

            ps.setInt(10, payment.getPaymentId());

            n = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    /**
     * Xóa một Payment theo paymentId.
     *
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
     *
     * @param sql câu lệnh SELECT tùy chỉnh
     * @return danh sách Payment (Vector)
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
                        rs.getTimestamp("updatedDate")
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
     *
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
                        rs.getTimestamp("updatedDate")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return payment;
    }

    /**
     * Lấy danh sách Payment theo orderId.
     *
     * @param orderId ID của Order
     * @return danh sách Payment (Vector)
     */
    public Payment getPaymentByOrderId(int orderId) {
        Payment payment = null;
        String sql = "SELECT * FROM Payment WHERE orderId = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
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
                        rs.getTimestamp("updatedDate")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return payment;
    }
}
