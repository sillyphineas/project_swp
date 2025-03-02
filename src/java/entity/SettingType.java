/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

public class SettingType {
    private int id;
    private String typeName;

    // Constructor mặc định
    public SettingType() {}

    // Constructor đầy đủ
    public SettingType(int id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "SettingType{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}

