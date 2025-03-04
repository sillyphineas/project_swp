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
        String sql = "INSERT INTO ProductVariants (productID, color, storage, price, stock) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, variant.getProductID());
            pre.setString(2, variant.getColor());
            pre.setInt(3, variant.getStorage());
            pre.setDouble(4, variant.getPrice());
            pre.setInt(5, variant.getStock());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

 public int updateProductVariant(ProductVariant variant) {
    int n = 0;
    String sql = "UPDATE ProductVariants SET color = ?, storage = ?, price = ?, stock = ? WHERE id = ?";

    try (PreparedStatement pre = conn.prepareStatement(sql)) {
        pre.setString(1, variant.getColor());
        pre.setInt(2, variant.getStorage());
        pre.setDouble(3, variant.getPrice());
        pre.setInt(4, variant.getStock());
        pre.setInt(5, variant.getId());
        
        n = pre.executeUpdate();
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
        String sql = "SELECT MIN(price) AS minPrice FROM ProductVariants WHERE productID = ?";
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

    public static void main(String[] args) {
        DAOProductVariant variant = new DAOProductVariant();
//        System.out.println(variant.getProductVariantById(58));
        System.out.println(variant.getProductVariantByDetails(1, "Đen", 128));
    }

    public Vector<ProductVariant> getVariantsByProductId(int productId, String color) {
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


}
