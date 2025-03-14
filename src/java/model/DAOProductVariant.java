package model;

import java.sql.SQLException;
import entity.ProductVariant;
import model.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ASUS
 */
public class DAOProductVariant extends DBConnection {

    public ProductVariant getProductVariantById(int variantId) {
        String sql = "SELECT * FROM ProductVariants WHERE id = ?";
        ProductVariant productVariant = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, variantId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    productVariant = new ProductVariant();
                    productVariant.setId(rs.getInt("id"));
                    productVariant.setProductID(rs.getInt("productID"));
                    productVariant.setColor(rs.getString("color"));
                    productVariant.setStorage(rs.getInt("storage"));
                    productVariant.setPrice(rs.getBigDecimal("price").doubleValue());
                    productVariant.setStock(rs.getInt("stock"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productVariant;
    }

    public ProductVariant getProductVariantByDetails(int productID, String color, int storage) {
        String sql = "SELECT * FROM ProductVariants WHERE productID = ? AND color = ? AND storage = ?";
        ProductVariant productVariant = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productID);
            stmt.setString(2, color);
            stmt.setInt(3, storage);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    productVariant = new ProductVariant();
                    productVariant.setId(rs.getInt("id"));
                    productVariant.setProductID(rs.getInt("productID"));
                    productVariant.setColor(rs.getString("color"));
                    productVariant.setStorage(rs.getInt("storage"));
                    productVariant.setPrice(rs.getBigDecimal("price").doubleValue());
                    productVariant.setStock(rs.getInt("stock"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productVariant;
    }

    public int addProductVariant(ProductVariant variant) {
    int n = 0;
    
    String sql = "INSERT INTO productVariants (product_id, color_id, storage_id, price, stock, status) VALUES (?, ?, ?, ?, ?, ?)";

    try {
        PreparedStatement pre = conn.prepareStatement(sql);
        pre.setInt(1, variant.getProduct_id());  
        pre.setInt(2, variant.getColor_id());    
        pre.setInt(3, variant.getStorage_id());  
        pre.setDouble(4, variant.getPrice());   
        pre.setInt(5, variant.getStock());       
        pre.setString(6, variant.isStatus());   

        n = pre.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return n;
}


   public int updateProductVariant(ProductVariant variant) {
        int n = 0;
        String sql = "UPDATE productVariants SET color_id = ?, storage_id = ?, price = ?, stock = ?, status = ? WHERE id = ?";

        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, variant.getColor_id());
            pre.setInt(2, variant.getStorage_id());
            pre.setDouble(3, variant.getPrice());
            pre.setInt(4, variant.getStock());
            pre.setString(5, variant.isStatus());
            pre.setInt(6, variant.getId());

            int affectedRows = pre.executeUpdate();
            System.out.println("Số lượng bản ghi bị ảnh hưởng: " + affectedRows);
            n = affectedRows;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public int deleteProductVariant(int id) {
        int n = 0;
        String sql = "DELETE FROM ProductVariants WHERE id = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public Vector<ProductVariant> getProductVariantsByProductId(int productId) {
        Vector<ProductVariant> variants = new Vector<>();
        String sql = "SELECT * FROM ProductVariants WHERE productID = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, productId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                ProductVariant variant = new ProductVariant(
                        rs.getInt("id"),
                        rs.getInt("productID"),
                        rs.getString("color"),
                        rs.getInt("storage"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
                variants.add(variant);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return variants;
    }

    public double getMinPriceByProductId(int productId) {
        double minPrice = 0;
        String sql = "SELECT MIN(pv.price) as minPrice "
                + "FROM productVariants pv "
                + "JOIN colors c ON pv.color_id = c.id "
                + "JOIN storages s ON pv.storage_id = s.id "
                + "WHERE pv.product_id = ? AND pv.status = 'Active' AND c.status = 'Active' AND s.status = 'Active'";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, productId);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                minPrice = rs.getDouble("minPrice");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return minPrice;
    }

  public Vector<ProductVariant> getVariantsByProductId(int productId) {
    Vector<ProductVariant> variants = new Vector<>();
    String sql = "SELECT pv.id, pv.product_id, pv.color_id, pv.storage_id, pv.price, pv.stock, c.colorName, s.capacity, pv.status "
               + "FROM productVariants pv "
               + "JOIN colors c ON pv.color_id = c.id "
               + "JOIN storages s ON pv.storage_id = s.id "
               + "WHERE pv.product_id = ? AND c.status = 'Active' AND s.status = 'Active'";

    try (PreparedStatement pre = conn.prepareStatement(sql)) {
        pre.setInt(1, productId);  // Gán giá trị productId vào câu truy vấn
        ResultSet rs = pre.executeQuery();  // Thực thi câu truy vấn

        while (rs.next()) {
            // Tạo đối tượng ProductVariant từ kết quả truy vấn
            ProductVariant variant = new ProductVariant(
                    rs.getInt("id"),           
                    rs.getInt("product_id"),   
                    rs.getInt("color_id"),      
                    rs.getInt("storage_id"),    
                    rs.getDouble("price"),      
                    rs.getInt("stock"),          
                    rs.getString("status")
            );
            variants.add(variant);  
        }
    } catch (SQLException ex) {
        ex.printStackTrace(); 
    }
      System.out.println("" +variants);
    return variants;  
}



    public static void main(String[] args) {
        DAOProductVariant variant = new DAOProductVariant();
//        System.out.println(variant.getProductVariantById(58));
        System.out.println(variant.getProductVariantByDetails(1, "Đen", 128));
    }


    public void reduceStock(int variantId, int quantity) {
        String sql = "UPDATE ProductVariants "
                + "SET stock = stock - ? "
                + "WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, variantId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOProductVariant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
   public ProductVariant getProductVariantDetails1(int productID, String color, String storage) {
    String sql = "SELECT pv.price, pv.stock "
               + "FROM productVariants pv "
               + "JOIN colors c ON pv.color_id = c.id "
               + "JOIN storages s ON pv.storage_id = s.id "
               + "WHERE pv.product_id = ? AND c.colorName = ? AND s.capacity = ?";

    ProductVariant productVariant = null;
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, productID);
        ps.setString(2, color);
        ps.setString(3, storage);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            productVariant = new ProductVariant();
            productVariant.setPrice(rs.getDouble("price"));
            productVariant.setStock(rs.getInt("stock"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return productVariant;
}
   public Vector<String> getDistinctStorageByProductId1(int productID) {
    Vector<String> storages = new Vector<>();
    String sql = "SELECT DISTINCT  s.capacity FROM storages s "
               + "JOIN productVariants pv ON s.id = pv.storage_id "
               + "WHERE pv.product_id = ? AND s.status = 'Active'";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, productID);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            storages.add(rs.getString("capacity"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return storages;
}
   public Vector<String> getDistinctColorsByProductId1(int productID) {
    Vector<String> colors = new Vector<>();
    String sql = "SELECT DISTINCT  c.colorName FROM colors c "
               + "JOIN productVariants pv ON c.id = pv.color_id "
               + "WHERE pv.product_id = ? AND c.status = 'Active'";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, productID);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            colors.add(rs.getString("colorName"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return colors;
}



}
