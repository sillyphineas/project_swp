package entity;

import java.util.Date;

public class OrderShippingView {
    // Thêm trường orderId (khóa chính của Orders)
    private int orderId;

    // Từ Orders
    private String buyerName;
    private Date orderTime;
    private String orderStatus;
    private String shippingAddress;
    private double totalPrice;
    private String recipientName;
    private String recipientPhone;

    // Từ Shipping
    private String shippingStatus;
    private Date estimatedArrival;
    private Date actualArrival;
    private Date shippingDate; // ShippingDate của bảng Shipping

    public OrderShippingView() {
    }

    public OrderShippingView(int orderId, String buyerName, Date orderTime, String orderStatus,
                             String shippingAddress, double totalPrice,
                             String recipientName, String recipientPhone,
                             String shippingStatus, Date estimatedArrival,
                             Date actualArrival, Date shippingDate) {
        this.orderId = orderId;
        this.buyerName = buyerName;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.shippingAddress = shippingAddress;
        this.totalPrice = totalPrice;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.shippingStatus = shippingStatus;
        this.estimatedArrival = estimatedArrival;
        this.actualArrival = actualArrival;
        this.shippingDate = shippingDate;
    }

    // Getters & Setters
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getBuyerName() {
        return buyerName;
    }
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
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

    public String getShippingStatus() {
        return shippingStatus;
    }
    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public Date getEstimatedArrival() {
        return estimatedArrival;
    }
    public void setEstimatedArrival(Date estimatedArrival) {
        this.estimatedArrival = estimatedArrival;
    }

    public Date getActualArrival() {
        return actualArrival;
    }
    public void setActualArrival(Date actualArrival) {
        this.actualArrival = actualArrival;
    }

    public Date getShippingDate() {
        return shippingDate;
    }
    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }
}
