/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import entity.SettingType;
import java.sql.*;
import java.util.Vector;

public class DAOSettingType extends DBConnection {

    // Lấy typeId từ typeName
    public int getTypeIdByName(String typeName) {
        int typeId = -1;
        String sql = "SELECT id FROM SettingType WHERE type_name = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, typeName);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                typeId = rs.getInt("id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return typeId;
    }

    // Thêm mới một loại SettingType
    public int addNewSettingType(String typeName) {
        int typeId = -1;
        String sql = "INSERT INTO SettingType (type_name) VALUES (?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pre.setString(1, typeName);
            pre.executeUpdate();
            ResultSet rs = pre.getGeneratedKeys();
            if (rs.next()) {
                typeId = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return typeId;
    }

    // Lấy danh sách tất cả các SettingType
        public Vector<SettingType> getAllSettingTypes() {
        Vector<SettingType> settingTypes = new Vector<>();
        String sql = "SELECT * FROM SettingType";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                SettingType settingType = new SettingType(
                        rs.getInt("id"),
                        rs.getString("type_name")
                );
                settingTypes.add(settingType);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return settingTypes;
    }

    // Kiểm tra hoạt động của DAOSettingType
    public static void main(String[] args) {
        DAOSettingType dao = new DAOSettingType();

        // Thử thêm một loại setting mới
        int newTypeId = dao.addNewSettingType("System Management");
        System.out.println("New Type ID: " + newTypeId);

        // Lấy danh sách tất cả các loại setting
        Vector<SettingType> types = dao.getAllSettingTypes();
        for (SettingType type : types) {
            System.out.println(type);
        }
    }
}

