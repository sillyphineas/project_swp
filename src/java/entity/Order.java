package entity;

import java.util.Date;

public class Order {
    private int id;                 // INT(11) PK
    private int buyerID;            // INT(11) FK -> Users(id)
    private Date orderTime;         // DATETIME
    private String orderStatus;     // VARCHAR(50)
    private Date shippingDate;      // DATE
    private String shippingAddress; // VARCHAR(255)
    private double totalPrice;      // DECIMAL(15,2)
    private double discountedPrice; // DECIMAL(15,2)
    private int paymentMethod;      // TINYINT(1)
    private boolean isDisabled;     // TINYINT(1) -> boolean
    private Integer voucherID;      // INT(11) FK -> Vouchers(id)
    private String recipientName;   // VARCHAR(255)
    private String recipientPhone;  // VARCHAR(15)
    private Integer assignedSaleId; // INT(11)

    public Order() {
    }

    // Constructor đầy đủ, khớp với cột DB
    public Order(int id, int buyerID, Date orderTime, String orderStatus,
                 Date shippingDate, String shippingAddress,
                 double totalPrice, double discountedPrice,
                 int paymentMethod, boolean isDisabled,
                 Integer voucherID, String recipientName,
                 String recipientPhone, Integer assignedSaleId) {
        this.id = id;
        this.buyerID = buyerID;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.shippingDate = shippingDate;
        this.shippingAddress = shippingAddress;
        this.totalPrice = totalPrice;
        this.discountedPrice = discountedPrice;
        this.paymentMethod = paymentMethod;
        this.isDisabled = isDisabled;
        this.voucherID = voucherID;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.assignedSaleId = assignedSaleId;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getBuyerID() {
        return buyerID;
    }
    public void setBuyerID(int buyerID) {
        this.buyerID = buyerID;
    }

    public Date getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getShippingDate() {
        return shippingDate;
    }
    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }
    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isDisabled() {
        return isDisabled;
    }
    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public Integer getVoucherID() {
        return voucherID;
    }
    public void setVoucherID(Integer voucherID) {
        this.voucherID = voucherID;
    }

    public String getRecipientName() {
        return recipientName;
    }
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }
    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public Integer getAssignedSaleId() {
        return assignedSaleId;
    }
    public void setAssignedSaleId(Integer assignedSaleId) {
        this.assignedSaleId = assignedSaleId;
    }

    @Override
    public String toString() {
        return "Order{"
                + "id=" + id
                + ", buyerID=" + buyerID
                + ", orderTime=" + orderTime
                + ", orderStatus='" + orderStatus + '\''
                + ", shippingDate=" + shippingDate
                + ", shippingAddress='" + shippingAddress + '\''
                + ", totalPrice=" + totalPrice
                + ", discountedPrice=" + discountedPrice
                + ", paymentMethod=" + paymentMethod
                + ", isDisabled=" + isDisabled
                + ", voucherID=" + voucherID
                + ", recipientName=" + recipientName
                + ", recipientPhone=" + recipientPhone
                + ", assignedSaleId=" + assignedSaleId
                + '}';
    }
}
