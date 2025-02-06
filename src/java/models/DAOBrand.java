/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import entities.Brand;
import entities.Feedback;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class DAOBrand extends DBConnection {

    public int addBlogs(Brand other) {
        int n = 0;
        String sql = "INSERT INTO [dbo].[Brands]\n"
                + "           ([Name]\n"
                + "           ,[Description]\n"
                + "           ,[Country])\n"
                + "     VALUES\n"
                + "           (?,?,?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, other.getName());
            pre.setString(2, other.getDescription());
            pre.setString(3, other.getCountry());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return n;
    }

    public int updateBrand(Brand other) {
        int n = 0;
        String sql = "UPDATE [dbo].[Brands]\n"
                + "   SET [Id] = ?\n"
                + "      ,[Name] = ?\n"
                + "      ,[Description] = ?\n"
                + "      ,[Country] = ?\n"
                + " WHERE <Id = ?>";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, other.getId());
            pre.setString(2, other.getName());
            pre.setString(3, other.getDescription());
            pre.setString(4, other.getCountry());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public Brand getBrandById(int id) {
        String sql = "Select * From Brands where Id = ?";
        Brand brand = null;
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                brand = new Brand(
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getString("Country")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return brand;
    }

    public Vector<Brand> getBrands(String sql) {
        Vector<Brand> vector = new Vector<>();
        try {
            Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Brand brand = new Brand(
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getString("Country")
                );
                vector.add(brand);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public int delete(int id) {
        int n = 0;
        String sql = "DELETE FROM [dbo].[Brands]\n"
                + "      WHERE id = ? ";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public Vector<Brand> getAllBrands() {
        Vector<Brand> brands = new Vector<>();
        String sql = "SELECT * FROM Brands"; // Thay đổi truy vấn SQL tùy vào cấu trúc bảng của bạn
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Brand brand = new Brand(
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getString("Country")
                );
                brands.add(brand);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return brands;
    }

    public static void main(String[] args) {
// Tạo đối tượng DAOBrand
        DAOBrand daoBrand = new DAOBrand();

        // Gọi phương thức getAllBrands() để lấy danh sách tất cả các thương hiệu
        Vector<Brand> brands = daoBrand.getAllBrands();

        // Kiểm tra kết quả trả về
        if (brands.isEmpty()) {
            System.out.println("Không tìm thấy thương hiệu nào trong cơ sở dữ liệu.");
        } else {
            System.out.println("Danh sách các thương hiệu:");
            for (Brand brand : brands) {
                System.out.println("ID: " + brand.getId());
                System.out.println("Tên: " + brand.getName());
                System.out.println("Mô tả: " + brand.getDescription());
                System.out.println("Quốc gia: " + brand.getCountry());
                System.out.println("----------------------------");
            }
        }
    }
}