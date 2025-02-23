/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author HP
 */
public class OrderDetail {
    private int orderID;               // Foreign Key to Orders table
    private int ProductVariantID;             // Foreign Key to Products table
    private int quantity;              // Quantity of the product in the order
    private double productPrice;       // Price of the product at the time of order

    public OrderDetail() {
    }

    public OrderDetail(int orderID, int ProductVariantID, int quantity, double productPrice) {
        this.orderID = orderID;
        this.ProductVariantID = ProductVariantID;
        this.quantity = quantity;
        this.productPrice = productPrice;
    }

    // Getters and Setters
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductVariantID() {
        return ProductVariantID;
    }

    public void setProductVariantID(int ProductVariantID) {
        this.ProductVariantID = ProductVariantID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderID=" + orderID +
                ", ProductVariantID=" + ProductVariantID +
                ", quantity=" + quantity +
                ", productPrice=" + productPrice +
                '}';
    }
}
