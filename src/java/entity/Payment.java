package entity;

import java.util.Date;

public class Payment {
    private int paymentId;
    private int orderId;             // Mới thêm
    private String paymentStatus;
    private Date paymentTime;
    private int paymentMethodId;
    private double amount;
    private String note;
    private int confirmBy;
    private Date createdDate;
    private Date updatedDate;
    private boolean isDisabled;

    // Constructor không đối số
    public Payment() {
    }

    // Constructor đầy đủ
    public Payment(int paymentId, int orderId, String paymentStatus, Date paymentTime,
                   int paymentMethodId, double amount, String note, int confirmBy,
                   Date createdDate, Date updatedDate, boolean isDisabled) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.paymentStatus = paymentStatus;
        this.paymentTime = paymentTime;
        this.paymentMethodId = paymentMethodId;
        this.amount = amount;
        this.note = note;
        this.confirmBy = confirmBy;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.isDisabled = isDisabled;
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

    public int getConfirmBy() {
        return confirmBy;
    }

    public void setConfirmBy(int confirmBy) {
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

    public boolean isIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
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
                ", isDisabled=" + isDisabled +
                '}';
    }
}
