package model;

import entity.ProductVariant;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOProductVariants extends DBConnection {

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
        String sql = "UPDATE ProductVariants SET productID = ?, color = ?, storage = ?, price = ?, stock = ? WHERE id = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, variant.getProductID());
            pre.setString(2, variant.getColor());
            pre.setInt(3, variant.getStorage());
            pre.setDouble(4, variant.getPrice());
            pre.setInt(5, variant.getStock());
            pre.setInt(6, variant.getId());
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

    public ProductVariant getProductVariantById(int id) {
        String sql = "SELECT * FROM ProductVariants WHERE id = ?";
        ProductVariant variant = null;
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                variant = new ProductVariant(
                        rs.getInt("id"),
                        rs.getInt("productID"),
                        rs.getString("color"),
                        rs.getInt("storage"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return variant;
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

}
