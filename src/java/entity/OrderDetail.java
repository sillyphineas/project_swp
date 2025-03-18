package entity;

public class OrderDetail {
    private int id;
    private int orderId;
    private int productVariantID; 
    private int quantity;

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
