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
public class Shipping {
    private int ShippingID;
    private int OrderID;
    private int ShipperID;
    private String ShippingStatus;
    private String EstimatedArrival;     
    private String ActualArrival;      
    private String ShippingDate;       

    public Shipping() {
    }

    public Shipping(int ShippingID, int OrderID, int ShipperID, String ShippingStatus, String EstimatedArrival, String ActualArrival, String ShippingDate) {
        this.ShippingID = ShippingID;
        this.OrderID = OrderID;
        this.ShipperID = ShipperID;
        this.ShippingStatus = ShippingStatus;
        this.EstimatedArrival = EstimatedArrival;
        this.ActualArrival = ActualArrival;
        this.ShippingDate = ShippingDate;
    }
    

    public int getShippingID() {
        return ShippingID;
    }

    public void setShippingID(int ShippingID) {
        this.ShippingID = ShippingID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }

    public int getShipperID() {
        return ShipperID;
    }

    public void setShipperID(int ShipperID) {
        this.ShipperID = ShipperID;
    }

    public String getShippingStatus() {
        return ShippingStatus;
    }

    public void setShippingStatus(String ShippingStatus) {
        this.ShippingStatus = ShippingStatus;
    }

    public String getEstimatedArrival() {
        return EstimatedArrival;
    }

    public void setEstimatedArrival(String EstimatedArrival) {
        this.EstimatedArrival = EstimatedArrival;
    }

    public String getActualArrival() {
        return ActualArrival;
    }

    public void setActualArrival(String ActualArrival) {
        this.ActualArrival = ActualArrival;
    }

    public String getShippingDate() {
        return ShippingDate;
    }

    public void setShippingDate(String ShippingDate) {
        this.ShippingDate = ShippingDate;
    }

    @Override
    public String toString() {
        return "Shipping{" + "ShippingID=" + ShippingID + ", OrderID=" + OrderID + ", ShipperID=" + ShipperID + ", ShippingStatus=" + ShippingStatus + ", EstimatedArrival=" + EstimatedArrival + ", ActualArrival=" + ActualArrival + ", ShippingDate=" + ShippingDate + '}';
    }
            
            
}
