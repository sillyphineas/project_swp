/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import entity.Address;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


/**
 *
 * @author ASUS
 */
public class DAOAddress extends DBConnection{
    
     public List<Address> getAddressesByUserId(int customerID) {
        List<Address> addressList = new ArrayList<>();
        String sql = "SELECT id, address, city, district, isDefault FROM Addresses WHERE userId = ?";

        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Address address = new Address();
                address.setId(rs.getInt("id"));
                address.setUserId(customerID);
                address.setAddress(rs.getString("address"));
                address.setCity(rs.getString("city"));
                address.setDistrict(rs.getString("district"));
                address.setDefault(rs.getBoolean("isDefault"));
                addressList.add(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressList;
    }
     public Address getAddressById(int addressId) {
        Address address = null;
        String sql = "SELECT id, address, district, city FROM Addresses WHERE id = ?";

        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, addressId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                address = new Address(rs.getInt("id"), 
                        rs.getString("address"), 
                        rs.getString("district"),
                        rs.getString("city"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address;
    }
     public int addAddress(Address address) throws SQLException {
        String sql = "INSERT INTO Addresses (userId, address, district, city) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, address.getUserId());
            ps.setString(2, address.getAddress());
            ps.setString(3, address.getDistrict());
            ps.setString(4, address.getCity());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Thêm địa chỉ thất bại, không có hàng nào được chèn.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Trả về ID của bản ghi vừa chèn
                } else {
                    throw new SQLException("Thêm địa chỉ thành công nhưng không lấy được ID.");
                }
            }
        }
    }
     
     public static void main(String[] args) {
        DAOAddress dao = new DAOAddress();
         System.out.println(dao.getAddressesByUserId(4));
    }



}
