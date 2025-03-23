/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class OrderTrend {
    private Date date;
    private int totalOrders;
    private int successfulOrders;

    public OrderTrend() {
    }

    public OrderTrend(Date date, int totalOrders, int successfulOrders) {
        this.date = date;
        this.totalOrders = totalOrders;
        this.successfulOrders = successfulOrders;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public int getSuccessfulOrders() {
        return successfulOrders;
    }

    public void setSuccessfulOrders(int successfulOrders) {
        this.successfulOrders = successfulOrders;
    }

    @Override
    public String toString() {
        return "OrderTrend{" +
                "date=" + date +
                ", totalOrders=" + totalOrders +
                ", successfulOrders=" + successfulOrders +
                '}';
    }
    
}
