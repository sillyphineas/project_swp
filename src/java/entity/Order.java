package entity;
import java.sql.*;
import java.util.Date;
import java.util.List;

public class Order {

    private int id;                 // INT(11) PK
    private int buyerID;            // INT(11) FK -> Users(id)
    private Date orderTime;         // DATETIME
    private String orderStatus;     // VARCHAR(50)
    private String shippingAddress; // VARCHAR(255)
    private double totalPrice;      // DECIMAL(15,2)
    private String ShippingDate;
    private double discountedPrice; // DECIMAL(15,2)
    private boolean isDisabled;     // TINYINT(1) -> boolean
    private Integer voucherID;      // INT(11) FK -> Vouchers(id)
    private String recipientName;   // VARCHAR(255)
    private String recipientPhone;  // VARCHAR(15)
    private Integer assignedSaleId; // INT(11)
    private OrderDetail orderDetails;
    private Shipping shipping;
    private Address address;
    private List<OrderDetail> orderDetails1;
    private User user;
    private Payment payment;
    private PaymentMethod paymentMethod;

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Order(ResultSet rs) throws SQLException {
        this.id = rs.getInt("orderID");
        this.buyerID = rs.getInt("buyerID");
        this.orderTime = rs.getTimestamp("orderTime");
        this.orderStatus = rs.getString("orderStatus");
        this.shippingAddress = rs.getString("shippingAddress");
        this.totalPrice = rs.getDouble("totalPrice");
        this.discountedPrice = rs.getDouble("discountedPrice");
        this.recipientName = rs.getString("recipientName");
        this.recipientPhone = rs.getString("recipientPhone");
        this.assignedSaleId = rs.getInt("assignedSaleId");

        // Các đối tượng khác sẽ cần truy vấn bổ sung hoặc truyền ID để xử lý sau.
        this.user = null; // Phải lấy từ DB hoặc truyền vào sau
        this.payment = null; // Phải lấy từ DB hoặc truyền vào sa
        this.shipping = null; // Phải lấy từ DB hoặc truyền vào sau
       
    }

    // mới thêm
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderDetail getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetail orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getShippingDate() {
        return ShippingDate;
    }

    public void setShippingDate(String ShippingDate) {
        this.ShippingDate = ShippingDate;
    }

    public boolean isIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<OrderDetail> getOrderDetails1() {
        return orderDetails1;
    }

    public void setOrderDetails1(List<OrderDetail> orderDetails1) {
        this.orderDetails1 = orderDetails1;
    }

    public Order() {
    }

    // Constructor đầy đủ, khớp với cột DB
    public Order(int id, int buyerID, Date orderTime, String orderStatus, String shippingAddress,
            double totalPrice, double discountedPrice, boolean isDisabled,
            Integer voucherID, String recipientName,
            String recipientPhone, Integer assignedSaleId) {
        this.id = id;
        this.buyerID = buyerID;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.shippingAddress = shippingAddress;
        this.totalPrice = totalPrice;
        this.discountedPrice = discountedPrice;
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
                + ", shippingAddress='" + shippingAddress + '\''
                + ", totalPrice=" + totalPrice
                + ", discountedPrice=" + discountedPrice
                + ", isDisabled=" + isDisabled
                + ", voucherID=" + voucherID
                + ", recipientName=" + recipientName
                + ", recipientPhone=" + recipientPhone
                + ", assignedSaleId=" + assignedSaleId
                + '}';
    }
}
