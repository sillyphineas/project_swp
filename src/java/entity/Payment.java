package entity;

import java.util.Date;

public class Payment {
    private int paymentId;
    private int orderId;
    private String paymentStatus;
    private Date paymentTime;
    private int paymentMethodId;
    private double amount;
    private String note;
    private Integer confirmBy; // Dùng kiểu Integer, cho phép null
    private Date createdDate;
    private Date updatedDate;

    public Payment() {
    }

    public Payment(int paymentId, int orderId, String paymentStatus, Date paymentTime,
                   int paymentMethodId, double amount, String note, Integer confirmBy,
                   Date createdDate, Date updatedDate) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.paymentStatus = paymentStatus;
        this.paymentTime = paymentTime;
        this.paymentMethodId = paymentMethodId;
        this.amount = amount;
        this.note = note;
        this.confirmBy = confirmBy; // Integer
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    // Getter & Setter
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getConfirmBy() {
        return confirmBy;
    }

    public void setConfirmBy(Integer confirmBy) {
        this.confirmBy = confirmBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", orderId=" + orderId +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", paymentTime=" + paymentTime +
                ", paymentMethodId=" + paymentMethodId +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                ", confirmBy=" + confirmBy +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
