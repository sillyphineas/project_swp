/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Admin
 */
public class VoucherType {
    private int VoucherTypeID;
    private String TypeName;
    private String Description;

    public VoucherType() {
    }

    public VoucherType(int VoucherTypeID, String TypeName, String Description) {
        this.VoucherTypeID = VoucherTypeID;
        this.TypeName = TypeName;
        this.Description = Description;
    }

    public int getVoucherTypeID() {
        return VoucherTypeID;
    }

    public void setVoucherTypeID(int VoucherTypeID) {
        this.VoucherTypeID = VoucherTypeID;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    @Override
    public String toString() {
        return "VoucherType{" + "VoucherTypeID=" + VoucherTypeID + ", TypeName=" + TypeName + ", Description=" + Description + '}';
    }
    
    
}
