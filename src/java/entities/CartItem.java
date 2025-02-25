/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.math.BigDecimal;

/**
 *
 * @author HP
 */
public class CartItem {
    	private int CartItemID  ,
	CartID                  ,
	ProductVariantID;
	private Double Price    ;
	private int Quantity;
	private double DiscountAmount;
	private BigDecimal TotalPrice  ;
        private boolean  isDisabled  ;
    private Product product;
    private ProductVariant productVariant;
            

    public CartItem(int CartItemID, int CartID, int ProductVariantID, Double Price, int Quantity, double DiscountAmount, BigDecimal TotalPrice, boolean isDisabled) {
        this.CartItemID = CartItemID;
        this.CartID = CartID;
        this.ProductVariantID = ProductVariantID;
        this.Price = Price;
        this.Quantity = Quantity;
        this.DiscountAmount = DiscountAmount;
        this.TotalPrice = TotalPrice;
        this.isDisabled = isDisabled;
    }

    public CartItem(int CartItemID, int ProductVariantID, Double Price, int Quantity, BigDecimal TotalPrice) {
        this.CartItemID = CartItemID;
        this.ProductVariantID = ProductVariantID;
        this.Price = Price;
        this.Quantity = Quantity;
        this.TotalPrice = TotalPrice;
    }

    public CartItem() {
    }
    
    
    public int getCartItemID() {
        return CartItemID;
    }

    public void setCartItemID(int CartItemID) {
        this.CartItemID = CartItemID;
    }

    public int getCartID() {
        return CartID;
    }

    public void setCartID(int CartID) {
        this.CartID = CartID;
    }

    public int getProductVariantID() {
        return ProductVariantID;
    }

    public void setProductVariantID(int ProductVariantID) {
        this.ProductVariantID = ProductVariantID;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double Price) {
        this.Price = Price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public double getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(double DiscountAmount) {
        this.DiscountAmount = DiscountAmount;
    }

    public BigDecimal getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(BigDecimal TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

    public boolean isIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
    @Override
    public String toString() {
        return "CartItem{" + "CartItemID=" + CartItemID + ", CartID=" + CartID + ", ProductVariantID=" + ProductVariantID + ", Price=" + Price + ", Quantity=" + Quantity + ", DiscountAmount=" + DiscountAmount + ", TotalPrice=" + TotalPrice + ", isDisabled=" + isDisabled + '}';
    }

    public void setDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
    }

        
        
}