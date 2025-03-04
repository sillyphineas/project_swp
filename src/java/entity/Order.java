/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author HP
 */
public class Order {

    private int id;                    // IDENTITY(1,1) PRIMARY KEY
    private int buyerID;               // FOREIGN KEY REFERENCES Users(id)
    private Date orderTime;            // DATETIME NOT NULL
    private String orderStatus;        // VARCHAR(50) NOT NULL
    private Date shippingDate;         // DATE
    private String shippingAddress;    // VARCHAR(255) NOT NULL
    private double totalPrice;         // MONEY NOT NULL
    private double discountedPrice;    // MONEY NOT NULL
    private int paymentMethod;        // TINYINT NOT NULL
    private boolean isDisabled;        // BIT NOT NULL
    private Integer voucherID;         // FOREIGN KEY REFERENCES Vouchers(id)
    private String RecipientName,
            RecipientPhone;
    private Integer assignedSaleId;

    public Order() {
    }

    public Order(int id, int buyerID, Date orderTime, String orderStatus, Date shippingDate, String shippingAddress, double totalPrice, double discountedPrice, int paymentMethod, boolean isDisabled, Integer voucherID, String message, String RecipientName, String RecipientPhone, int assignedSaleId) {
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
        this.RecipientName = RecipientName;
        this.RecipientPhone = RecipientPhone;
        this.assignedSaleId = assignedSaleId;
    }
    
    public Order(int id, int buyerID, Date orderTime, String orderStatus, Date shippingDate, String shippingAddress, double totalPrice, double discountedPrice, int paymentMethod, boolean isDisabled, Integer voucherID, String RecipientName, String RecipientPhone, int assignedSaleId) {
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
        this.RecipientName = RecipientName;
        this.RecipientPhone = RecipientPhone;
        this.assignedSaleId = assignedSaleId;
    }

    public boolean isIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getRecipientName() {
        return RecipientName;
    }

    public void setRecipientName(String RecipientName) {
        this.RecipientName = RecipientName;
    }

    public String getRecipientPhone() {
        return RecipientPhone;
    }

    public void setRecipientPhone(String RecipientPhone) {
        this.RecipientPhone = RecipientPhone;
    }

    // Getters and Setters
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
                + '}';
    }
}
