package entity;

public class OrderDetail {
    private int id;
    private int orderId;
    private int productVariantID; 
    private int quantity;
    
    private ProductVariant productVariant;
    private  Payment payment;
    private  PaymentMethod paymentMethod;
    private String RecipientPhone, RecipientName, ShippingAddress;
    private String orderTime;

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    
    
    
    
    public String getRecipientPhone() {
        return RecipientPhone;
    }

    public void setRecipientPhone(String RecipientPhone) {
        this.RecipientPhone = RecipientPhone;
    }

    public String getRecipientName() {
        return RecipientName;
    }

    public void setRecipientName(String RecipientName) {
        this.RecipientName = RecipientName;
    }

    public String getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(String ShippingAddress) {
        this.ShippingAddress = ShippingAddress;
    }
    
    
    
    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    
    

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
    }
    

    public OrderDetail() {
    }

    public OrderDetail(int id, int orderId, int productVariantID, int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.productVariantID = productVariantID;
        this.quantity = quantity;
    }

    public OrderDetail(int orderId, int productVariantID, int quantity) {
        this.orderId = orderId;
        this.productVariantID = productVariantID;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductVariantID() {
        return productVariantID;
    }

    public void setProductVariantID(int productVariantID) {
        this.productVariantID = productVariantID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productVariantID=" + productVariantID +
                ", quantity=" + quantity +
                '}';
    }

}
