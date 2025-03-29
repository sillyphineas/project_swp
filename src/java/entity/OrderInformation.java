package entity;

import java.util.Date;

public class OrderInformation {
    private int id;
    private Date orderTime;
    private String orderStatus;
    private double totalPrice;
    private double discountPrice;
    private String paymentName;
    private String paymentStatus;
    private String recipientName;
    private String recipientPhone;
    private int quantity;
    private String shippingStatus;
    private Date shippingDate;
    private Date estimatedArrival;
    private Date actualArrival;
    private String colorName;
    private String capacity;
    private double price;
    private String productName;
    private String imageURL;
    private String address;
    private String district;
    private String city;
    private int productId;
    private int orderDetailID;

    public OrderInformation() {
    }

    public OrderInformation(int id, Date orderTime, String orderStatus, double totalPrice, double discountPrice, String paymentName, String paymentStatus, String recipientName, String recipientPhone, int quantity, String shippingStatus, Date shippingDate, Date estimatedArrival, Date actualArrival, String colorName, String capacity, double price, String productName, String imageURL, String address, String district, String city, int productId,int orderDetailID) {
        this.id = id;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
        this.paymentName = paymentName;
        this.paymentStatus = paymentStatus;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.quantity = quantity;
        this.shippingStatus = shippingStatus;
        this.shippingDate = shippingDate;
        this.estimatedArrival = estimatedArrival;
        this.actualArrival = actualArrival;
        this.colorName = colorName;
        this.capacity = capacity;
        this.price = price;
        this.productName = productName;
        this.imageURL = imageURL;
        this.address = address;
        this.district = district;
        this.city = city;
        this.productId = productId;
        this.orderDetailID =orderDetailID;
    }
    
    public OrderInformation(
            int id,
            Date orderTime,
            String orderStatus,
            double totalPrice,
            double discountPrice,
            String paymentName,
            String paymentStatus,
            String recipientName,
            String recipientPhone,
            int quantity,
            String shippingStatus,
            Date shippingDate,
            Date estimatedArrival,
            Date actualArrival,
            String colorName,
            String capacity,
            double price,
            String productName,
            String imageURL,
            String address,
            String district,
            String city
    ) {
        this.id = id;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
        this.paymentName = paymentName;
        this.paymentStatus = paymentStatus;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.quantity = quantity;
        this.shippingStatus = shippingStatus;
        this.shippingDate = shippingDate;
        this.estimatedArrival = estimatedArrival;
        this.actualArrival = actualArrival;
        this.colorName = colorName;
        this.capacity = capacity;
        this.price = price;
        this.productName = productName;
        this.imageURL = imageURL;
        this.address = address;
        this.district = district;
        this.city = city;
    }

    public OrderInformation(int id, Date orderTime, String orderStatus, double totalPrice, double discountPrice, String paymentName, String recipientName, String recipientPhone, int quantity, String shippingStatus, Date shippingDate, Date estimatedArrival, Date actualArrival, String colorName, String capacity, double price, String productName, String imageURL, String address, String district, String city, int productId,int orderDetailID) {
        this.id = id;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
        this.paymentName = paymentName;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.quantity = quantity;
        this.shippingStatus = shippingStatus;
        this.shippingDate = shippingDate;
        this.estimatedArrival = estimatedArrival;
        this.actualArrival = actualArrival;
        this.colorName = colorName;
        this.capacity = capacity;
        this.price = price;
        this.productName = productName;
        this.imageURL = imageURL;
        this.address = address;
        this.district = district;
        this.city = city;
        this.productId = productId;
        this.orderDetailID =orderDetailID;
    }
    
    public OrderInformation(
            int id,
            Date orderTime,
            String orderStatus,
            double totalPrice,
            double discountPrice,
            String paymentName,
            String recipientName,
            String recipientPhone,
            int quantity,
            String shippingStatus,
            Date shippingDate,
            Date estimatedArrival,
            Date actualArrival,
            String colorName,
            String capacity,
            double price,
            String productName,
            String imageURL,
            String address,
            String district,
            String city
    ) {
        this.id = id;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
        this.paymentName = paymentName;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.quantity = quantity;
        this.shippingStatus = shippingStatus;
        this.shippingDate = shippingDate;
        this.estimatedArrival = estimatedArrival;
        this.actualArrival = actualArrival;
        this.colorName = colorName;
        this.capacity = capacity;
        this.price = price;
        this.productName = productName;
        this.imageURL = imageURL;
        this.address = address;
        this.district = district;
        this.city = city;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public double getDiscountPrice() {
        return discountPrice;
    }
    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getPaymentName() {
        return paymentName;
    }
    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getRecipientName() {
        return recipientName;
    }
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }
    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getShippingStatus() {
        return shippingStatus;
    }
    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public Date getShippingDate() {
        return shippingDate;
    }
    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
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

    public String getColorName() {
        return colorName;
    }
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getCapacity() {
        return capacity;
    }
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageURL() {
        return imageURL;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }
    
    
}