/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author HP
 */
public class ProductVariant {
    private int id;
    private int productID;
    private String color;
    private int storage; // Dung lượng bộ nhớ trong GB
    private double price;
    private int stock;

    public ProductVariant() {
    }

    public ProductVariant(int id, int productID, String color, int storage, double price, int stock) {
        this.id = id;
        this.productID = productID;
        this.color = color;
        this.storage = storage;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ProductVariant{" +
                "id=" + id +
                ", productID=" + productID +
                ", color='" + color + '\'' +
                ", storage=" + storage +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}

