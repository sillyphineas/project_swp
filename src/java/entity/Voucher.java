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
public class Voucher {
    private int VoucherID;
    private String VoucherCode;
    private int voucherTypeID;
    private String Description;
    private int Point;
    private Date StartDate;
    private Date ExpiredDate;
    private int UsageLimit;
    private boolean isDisabled;
    private int Value;


    public Voucher() {
    }

    public Voucher(int VoucherID, String VoucherCode, int voucherTypeID, String Description, int Point, Date StartDate, Date ExpiredDate, int UsageLimit, boolean isDisabled, int Value) {
        this.VoucherID = VoucherID;
        this.VoucherCode = VoucherCode;
        this.voucherTypeID = voucherTypeID;
        this.Description = Description;
        this.Point = Point;
        this.StartDate = StartDate;
        this.ExpiredDate = ExpiredDate;
        this.UsageLimit = UsageLimit;
        this.isDisabled = isDisabled;
        this.Value = Value;
    }

    public int getVoucherID() {
        return VoucherID;
    }

    public void setVoucherID(int VoucherID) {
        this.VoucherID = VoucherID;
    }

    public String getVoucherCode() {
        return VoucherCode;
    }

    public void setVoucherCode(String VoucherCode) {
        this.VoucherCode = VoucherCode;
    }

    public int getvoucherTypeID() {
        return voucherTypeID;
    }
    
    public int getValue() {
    return Value;
}

public void setValue(int Value) {
    this.Value = Value;
}


    public void setvoucherTypeID(int voucherTypeID) {
        this.voucherTypeID = voucherTypeID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int Point) {
        this.Point = Point;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
    }

    public Date getExpiredDate() {
        return ExpiredDate;
    }

    public void setExpiredDate(Date ExpiredDate) {
        this.ExpiredDate = ExpiredDate;
    }

    public int getUsageLimit() {
        return UsageLimit;
    }

    public void setUsageLimit(int UsageLimit) {
        this.UsageLimit = UsageLimit;
    }

    public boolean isIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    @Override
    public String toString() {
        return "Voucher{" + "VoucherID=" + VoucherID + ", VoucherCode=" + VoucherCode + ", VoucherType=" + voucherTypeID + ", Description=" + Description + ", DiscountAmount=" + Point + ", StartDate=" + StartDate + ", ExpiredDate=" + ExpiredDate + ", UsageLimit=" + UsageLimit + ", isDisabled=" + isDisabled + '}';
    }
    
    
}
