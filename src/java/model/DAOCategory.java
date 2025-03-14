/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import entity.Category;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author ASUS
 */
public class DAOCategory extends DBConnection{
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        
        String sql = "SELECT id, categoryName FROM categoryblog"; 
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("categoryName");
                Category category = new Category(id, name);
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        
        return categories;
    }
    
    public static void main(String[] args) {
        DAOCategory dao= new DAOCategory();
        System.out.println(dao.getAllCategories());
    }
}
