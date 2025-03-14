/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author ASUS
 */
public class Category {

    private int id;
    private String categoryName;
    private String status;
    private boolean isDisabled;

    public Category(int id, String categoryName, String status, boolean isDisabled) {
        this.id = id;
        this.categoryName = categoryName;
        this.status = status;
        this.isDisabled = isDisabled;
    }

    public Category(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
    

    public Category() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }
    
    

}
