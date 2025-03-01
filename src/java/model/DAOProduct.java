/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import entity.Product;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class DAOProduct extends DBConnection {

    public int addProduct(Product other) {
        int n = 0;
        String sql = "INSERT INTO [dbo].[Products]\n"
                + "           ([brandID]\n"
                + "           ,[name]\n"
                + "           ,[price]\n"
                + "           ,[stock]\n"
                + "           ,[description]\n"
                + "           ,[isDisabled]\n"
                + "           ,[feedbackCount]\n"
                + "           ,[status]\n"
                + "           ,[imageURL]\n"
                + "           ,[chipset]\n"
                + "           ,[ram]\n"
                + "           ,[storage]\n"
                + "           ,[screenSize]\n"
                + "           ,[screenType]\n"
                + "           ,[resolution]\n"
                + "           ,[batteryCapacity]\n"
                + "           ,[cameraSpecs]\n"
                + "           ,[os]\n"
                + "           ,[simType]\n"
                + "           ,[connectivity])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, other.getBrandID());
            pre.setString(2, other.getName());
            pre.setString(3, other.getDescription());
            pre.setBoolean(4, other.isIsDisabled());
            pre.setInt(5, other.getFeedbackCount());
            pre.setString(6, other.getStatus());
            pre.setString(7, other.getImageURL());
            pre.setString(8, other.getChipset());
            pre.setInt(9, other.getRam());
            pre.setDouble(10, other.getScreenSize());
            pre.setString(11, other.getScreenType());
            pre.setString(12, other.getResolution());
            pre.setInt(13, other.getBatteryCapacity());
            pre.setString(14, other.getCameraSpecs());
            pre.setString(15, other.getOs());
            pre.setString(16, other.getSimType());
            pre.setString(17, other.getConnectivity());
            pre.setDate(18, (Date) other.getCreateAt());
            pre.setInt(19, other.getCreatedBy());

            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return n;
    }

    public int UpdateProduct(Product other) {
        int n = 0;
        String sql = "UPDATE [dbo].[Products]\n"
                + "   SET [id] = ?\n"
                + "      ,[brandID] = ?\n"
                + "      ,[name] = ?\n"
                + "      ,[price] = ?\n"
                + "      ,[stock] = ?\n"
                + "      ,[description] = ?\n"
                + "      ,[feedbackCount] = ?\n"
                + "      ,[status] = ?\n"
                + "      ,[imageURL] = ?\n"
                + "      ,[chipset] = ?\n"
                + "      ,[ram] = ?\n"
                + "      ,[storage] = ?\n"
                + "      ,[screenSize] = ?\n"
                + "      ,[screenType] = ?\n"
                + "      ,[resolution] = ?\n"
                + "      ,[batteryCapacity] = ?\n"
                + "      ,[cameraSpecs] = ?\n"
                + "      ,[os] = ?\n"
                + "      ,[simType] = ?\n"
                + "      ,[connectivity] = ?\n"
                + "      ,[createAt] = ?\n"
                + "      ,[CreatedBy] = ?\n"
                + " WHERE <id = ?>";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, other.getBrandID());
            pre.setString(2, other.getName());
            pre.setString(3, other.getDescription());
            pre.setBoolean(4, other.isIsDisabled());
            pre.setInt(5, other.getFeedbackCount());
            pre.setString(6, other.getStatus());
            pre.setString(7, other.getImageURL());
            pre.setString(8, other.getChipset());
            pre.setInt(9, other.getRam());
            pre.setDouble(10, other.getScreenSize());
            pre.setString(11, other.getScreenType());
            pre.setString(12, other.getResolution());
            pre.setInt(13, other.getBatteryCapacity());
            pre.setString(14, other.getCameraSpecs());
            pre.setString(15, other.getOs());
            pre.setString(16, other.getSimType());
            pre.setString(17, other.getConnectivity());
            pre.setInt(18, other.getId());
            pre.setDate(19, (Date) other.getCreateAt());
            pre.setInt(20, other.getCreatedBy());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public Product getProductById(int id) {
        String sql = "SELECT p.*, MIN(v.price) as minPrice "
                + "FROM Products p "
                + "LEFT JOIN ProductVariants v ON p.id = v.productID "
                + "WHERE p.id = ? "
                + "GROUP BY p.id";
        Product product = null;
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                product = new Product(
                        rs.getInt("id"),
                        rs.getInt("brandID"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBoolean("isDisabled"),
                        rs.getInt("feedbackCount"),
                        rs.getString("status"),
                        rs.getString("imageURL"),
                        rs.getString("chipset"),
                        rs.getInt("ram"),
                        rs.getDouble("screenSize"),
                        rs.getString("screenType"),
                        rs.getString("resolution"),
                        rs.getInt("batteryCapacity"),
                        rs.getString("cameraSpecs"),
                        rs.getString("os"),
                        rs.getString("simType"),
                        rs.getString("connectivity"),
                        rs.getDate("createAt"),
                        rs.getInt("createdBy")
                );
                // Không còn `price` trong Product nữa
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return product;
    }

    public double getMinPriceForProduct(int productId) {
        double minPrice = 0;
        String sql = "SELECT MIN(price) as minPrice FROM ProductVariants WHERE productID = ?";
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

    public Vector<Product> getProducts(String sql) {
        Vector<Product> vector = new Vector<>();
        try {
            Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getInt("brandID"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBoolean("isDisabled"),
                        rs.getInt("feedbackCount"),
                        rs.getString("status"),
                        rs.getString("imageURL"),
                        rs.getString("chipset"),
                        rs.getInt("ram"),
                        rs.getDouble("screenSize"),
                        rs.getString("screenType"),
                        rs.getString("resolution"),
                        rs.getInt("batteryCapacity"),
                        rs.getString("cameraSpecs"),
                        rs.getString("os"),
                        rs.getString("simType"),
                        rs.getString("connectivity"),
                        rs.getDate("createAt"),
                        rs.getInt("createdBy")
                );
                vector.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

   

    public Vector<Product> getProductsWithPagination(int page, int itemsPerPage) {
        Vector<Product> productList = new Vector<>();

        // Tính toán chỉ mục bắt đầu cho phân trang
        int startIndex = (page - 1) * itemsPerPage;

        // SQL để lấy sản phẩm theo phân trang
        String sql = "SELECT * FROM Products WHERE isDisabled = 0 LIMIT ?, ?";

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, startIndex);  // Chỉ mục bắt đầu
            pre.setInt(2, itemsPerPage); // Số lượng sản phẩm mỗi trang

            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getInt("brandID"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBoolean("isDisabled"),
                        rs.getInt("feedbackCount"),
                        rs.getString("status"),
                        rs.getString("imageURL"),
                        rs.getString("chipset"),
                        rs.getInt("ram"),
                        rs.getDouble("screenSize"),
                        rs.getString("screenType"),
                        rs.getString("resolution"),
                        rs.getInt("batteryCapacity"),
                        rs.getString("cameraSpecs"),
                        rs.getString("os"),
                        rs.getString("simType"),
                        rs.getString("connectivity"),
                        rs.getDate("createAt"),
                        rs.getInt("createdBy")
                );
                productList.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return productList;
    }

    public int getTotalProducts() {
        int totalItems = 0;

        // SQL để lấy tổng số sản phẩm không bị vô hiệu hóa
        String sql = "SELECT COUNT(*) FROM Products WHERE isDisabled = 0";

        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);

            if (rs.next()) {
                totalItems = rs.getInt(1);  // Lấy tổng số sản phẩm
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return totalItems;
    }

    public Vector<Product> getProductsWithPaginationAndSorting(int page, int itemsPerPage) {
        Vector<Product> productList = new Vector<>();
        int startIndex = (page - 1) * itemsPerPage;
        String sql = "SELECT * FROM Products WHERE isDisabled = 0 ORDER BY createAt DESC LIMIT ?, ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, startIndex);
            pre.setInt(2, itemsPerPage);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getInt("brandID"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBoolean("isDisabled"),
                        rs.getInt("feedbackCount"),
                        rs.getString("status"),
                        rs.getString("imageURL"),
                        rs.getString("chipset"),
                        rs.getInt("ram"),
                        rs.getDouble("screenSize"),
                        rs.getString("screenType"),
                        rs.getString("resolution"),
                        rs.getInt("batteryCapacity"),
                        rs.getString("cameraSpecs"),
                        rs.getString("os"),
                        rs.getString("simType"),
                        rs.getString("connectivity"),
                        rs.getDate("createAt"),
                        rs.getInt("createdBy")
                );
                productList.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productList;
    }

    public int getTotalProductsByBrand(int brandID) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM Products WHERE brandID = ? AND isDisabled = 0";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, brandID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public int getTotalProductsBySearch(String searchQuery) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM Product WHERE name LIKE ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + searchQuery + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public int getTotalProductsByPriceRange(double minPrice, double maxPrice) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM Product WHERE price BETWEEN ? AND ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, minPrice);
            ps.setDouble(2, maxPrice);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
public int delete(int id) {
        int n = 0;

        
        String sqlDeleteProductVariants = "DELETE FROM ProductVariants WHERE productID = ?";
        String sqlDeleteOrderDetails = "DELETE FROM OrderDetails WHERE productID = ?";
        String sqlDeleteFeedbacks = "DELETE FROM Feedbacks WHERE productID = ?";
        String sqlDeleteProduct = "DELETE FROM Products WHERE id = ?";

        
        PreparedStatement psDeleteProductVariants = null;
        PreparedStatement psDeleteOrderDetails = null;
        PreparedStatement psDeleteFeedbacks = null;
        PreparedStatement psDeleteProduct = null;

        try {
            
            conn.setAutoCommit(false); 

            
            psDeleteProductVariants = conn.prepareStatement(sqlDeleteProductVariants);
            psDeleteProductVariants.setInt(1, id);
            psDeleteProductVariants.executeUpdate();

            
            psDeleteOrderDetails = conn.prepareStatement(sqlDeleteOrderDetails);
            psDeleteOrderDetails.setInt(1, id);
            psDeleteOrderDetails.executeUpdate();

           
            psDeleteFeedbacks = conn.prepareStatement(sqlDeleteFeedbacks);
            psDeleteFeedbacks.setInt(1, id);
            psDeleteFeedbacks.executeUpdate();

            
            psDeleteProduct = conn.prepareStatement(sqlDeleteProduct);
            psDeleteProduct.setInt(1, id);
            n = psDeleteProduct.executeUpdate();

            
            conn.commit();
            System.out.println("Sản phẩm xóa thành công với ID: " + id);
        } catch (SQLException ex) {
            // Nếu có lỗi, rollback transaction
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
            System.out.println("Lỗi khi xóa sản phẩm: " + ex.getMessage());
        } finally {
            
            try {
                if (psDeleteProductVariants != null) psDeleteProductVariants.close();
                if (psDeleteOrderDetails != null) psDeleteOrderDetails.close();
                if (psDeleteFeedbacks != null) psDeleteFeedbacks.close();
                if (psDeleteProduct != null) psDeleteProduct.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return n;
    }
    public Vector<Product> searchProductsByName(String searchQuery, int currentPage, int itemsPerPage) {
        Vector<Product> productList = new Vector<>();
        String sql = "SELECT * FROM Products WHERE name LIKE ? AND isDisabled = 0 LIMIT ?, ?";

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, "%" + searchQuery + "%");
            pre.setInt(2, (currentPage - 1) * itemsPerPage); // Tính offset
            pre.setInt(3, itemsPerPage); // Số lượng sản phẩm mỗi trang

            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getInt("brandID"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBoolean("isDisabled"),
                        rs.getInt("feedbackCount"),
                        rs.getString("status"),
                        rs.getString("imageURL"),
                        rs.getString("chipset"),
                        rs.getInt("ram"),
                        rs.getDouble("screenSize"),
                        rs.getString("screenType"),
                        rs.getString("resolution"),
                        rs.getInt("batteryCapacity"),
                        rs.getString("cameraSpecs"),
                        rs.getString("os"),
                        rs.getString("simType"),
                        rs.getString("connectivity"),
                        rs.getDate("createAt"),
                        rs.getInt("createdBy")
                );
                productList.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productList;
    }

    public Product getLatestProduct() {
        String sql = "SELECT * FROM Products WHERE isDisabled = 0 ORDER BY createAt DESC LIMIT 1";
        Product latestProduct = null;
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                latestProduct = new Product(
                        rs.getInt("id"),
                        rs.getInt("brandID"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBoolean("isDisabled"),
                        rs.getInt("feedbackCount"),
                        rs.getString("status"),
                        rs.getString("imageURL"),
                        rs.getString("chipset"),
                        rs.getInt("ram"),
                        rs.getDouble("screenSize"),
                        rs.getString("screenType"),
                        rs.getString("resolution"),
                        rs.getInt("batteryCapacity"),
                        rs.getString("cameraSpecs"),
                        rs.getString("os"),
                        rs.getString("simType"),
                        rs.getString("connectivity"),
                        rs.getDate("createAt"),
                        rs.getInt("createdBy")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return latestProduct;
    }

    public Vector<Product> getProductsSortedByDate(int page, int itemsPerPage) {
        Vector<Product> productList = new Vector<>();
        int startIndex = (page - 1) * itemsPerPage;
        String sql = "SELECT * FROM Products WHERE isDisabled = 0 ORDER BY createAt DESC LIMIT ?, ?";

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, startIndex);
            pre.setInt(2, itemsPerPage);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getInt("brandID"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBoolean("isDisabled"),
                        rs.getInt("feedbackCount"),
                        rs.getString("status"),
                        rs.getString("imageURL"),
                        rs.getString("chipset"),
                        rs.getInt("ram"),
                        rs.getDouble("screenSize"),
                        rs.getString("screenType"),
                        rs.getString("resolution"),
                        rs.getInt("batteryCapacity"),
                        rs.getString("cameraSpecs"),
                        rs.getString("os"),
                        rs.getString("simType"),
                        rs.getString("connectivity"),
                        rs.getDate("createAt"),
                        rs.getInt("createdBy")
                );
                productList.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productList;
    }

    public Vector<Product> getProductsByPriceRange(double minPrice, double maxPrice, int page, int itemsPerPage) {
        Vector<Product> productList = new Vector<>();
        int startIndex = (page - 1) * itemsPerPage;
        String sql = "SELECT * FROM Products WHERE isDisabled = 0 AND price BETWEEN ? AND ? ORDER BY price ASC LIMIT ?, ?";

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setDouble(1, minPrice);
            pre.setDouble(2, maxPrice);
            pre.setInt(3, startIndex);
            pre.setInt(4, itemsPerPage);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getInt("brandID"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBoolean("isDisabled"),
                        rs.getInt("feedbackCount"),
                        rs.getString("status"),
                        rs.getString("imageURL"),
                        rs.getString("chipset"),
                        rs.getInt("ram"),
                        rs.getDouble("screenSize"),
                        rs.getString("screenType"),
                        rs.getString("resolution"),
                        rs.getInt("batteryCapacity"),
                        rs.getString("cameraSpecs"),
                        rs.getString("os"),
                        rs.getString("simType"),
                        rs.getString("connectivity"),
                        rs.getDate("createAt"),
                        rs.getInt("createdBy")
                );
                productList.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productList;
    }

    public Vector<Product> getProductsByBrand(int brandID, int currentPage, int itemsPerPage) {
        Vector<Product> productList = new Vector<>();
        int startIndex = (currentPage - 1) * itemsPerPage;

        String sql = "SELECT * FROM Products WHERE brandID = ? AND isDisabled = 0 ORDER BY createAt DESC LIMIT ? OFFSET ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, brandID);
            ps.setInt(2, itemsPerPage);
            ps.setInt(3, startIndex);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                productList.add(new Product(
                        rs.getInt("id"),
                        rs.getInt("brandID"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBoolean("isDisabled"),
                        rs.getInt("feedbackCount"),
                        rs.getString("status"),
                        rs.getString("imageURL"),
                        rs.getString("chipset"),
                        rs.getInt("ram"),
                        rs.getDouble("screenSize"),
                        rs.getString("screenType"),
                        rs.getString("resolution"),
                        rs.getInt("batteryCapacity"),
                        rs.getString("cameraSpecs"),
                        rs.getString("os"),
                        rs.getString("simType"),
                        rs.getString("connectivity"),
                        rs.getDate("createAt"),
                        rs.getInt("createdBy")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());

            e.printStackTrace();
        }
        return productList;
    }
    public int getTotalProductsByFilters(int brandID, String searchQuery, double minPrice, double maxPrice, String os,double screenSize, int batteryCapacity,
            String connectivity, int ram, String screenType) {
        String sql = "SELECT COUNT(*) FROM Products WHERE isDisabled = 0";

        if (brandID > 0) {
            sql += " AND brandID = " + brandID;
        }
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            sql += " AND name LIKE '%" + searchQuery + "%'";
        }
        if (minPrice >= 0 && maxPrice < Double.MAX_VALUE) {
            sql += " AND price BETWEEN " + minPrice + " AND " + maxPrice;
        }
        if (os != null && !os.isEmpty()) {
            sql += " AND os = '" + os + "'";
        }
        if (connectivity != null && !connectivity.isEmpty()) {
            sql += " AND connectivity = '" + connectivity + "'";
        }
        if (ram > 0) {
            sql += " AND ram = " + ram;
        }
        if (screenType != null && !screenType.isEmpty()) {
            sql += " AND screenType = '" + screenType + "'";
        }
        if (screenSize >0) {
            sql += " AND screenSize = " + screenSize ;
        }
        if (batteryCapacity > 0) {
            sql += " AND batteryCapacity = " + batteryCapacity;
        }

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Vector<Product> getProductsByFilters(int brandID, String searchQuery, double minPrice, double maxPrice, String os,double screenSize, int batteryCapacity,
            String connectivity, int ram, String screenType, int currentPage, int itemsPerPage) {
        Vector<Product> productList = new Vector<>();
        int startIndex = (currentPage - 1) * itemsPerPage;

        String sql = "SELECT * FROM Products WHERE isDisabled = 0";
        if (brandID > 0) {
            sql += " AND brandID = " + brandID;
        }
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            sql += " AND name LIKE '%" + searchQuery + "%'";
        }
        if (minPrice >= 0 && maxPrice < Double.MAX_VALUE) {
            sql += " AND price BETWEEN " + minPrice + " AND " + maxPrice;
        }
        if (os != null && !os.isEmpty()) {
            sql += " AND os = '" + os + "'";
        }
        if (connectivity != null && !connectivity.isEmpty()) {
            sql += " AND connectivity = '" + connectivity + "'";
        }
        if (ram > 0) {
            sql += " AND ram = " + ram;
        }
        if (screenType != null && !screenType.isEmpty()) {
            sql += " AND screenType = '" + screenType + "'";
        }
        if (screenSize >0) {
            sql += " AND screenSize = " + screenSize ;
        }
        if (batteryCapacity > 0) {
            sql += " AND batteryCapacity = " + batteryCapacity;
        }
        sql += " ORDER BY createAt DESC LIMIT " + itemsPerPage + " OFFSET " + startIndex;

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                productList.add(new Product(
                        rs.getInt("id"),
                        rs.getInt("brandID"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBoolean("isDisabled"),
                        rs.getInt("feedbackCount"),
                        rs.getString("status"),
                        rs.getString("imageURL"),
                        rs.getString("chipset"),
                        rs.getInt("ram"),
                        rs.getDouble("screenSize"),
                        rs.getString("screenType"),
                        rs.getString("resolution"),
                        rs.getInt("batteryCapacity"),
                        rs.getString("cameraSpecs"),
                        rs.getString("os"),
                        rs.getString("simType"),
                        rs.getString("connectivity"),
                        rs.getDate("createAt"),
                        rs.getInt("createdBy")
                ));
            };
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    
}
    
    public Vector<String> getDistinctOS() {
        Vector<String> osList = new Vector<>();
        String sql = "SELECT DISTINCT os FROM Products WHERE os IS NOT NULL AND os <> ''";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                osList.add(rs.getString("os"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return osList;
    }

    public Vector<String> getDistinctConnectivity() {
        Vector<String> connectivityList = new Vector<>();
        String sql = "SELECT DISTINCT connectivity FROM Products WHERE connectivity IS NOT NULL AND connectivity <> ''";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                connectivityList.add(rs.getString("connectivity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connectivityList;
    }

    public Vector<Integer> getDistinctRAM() {
        Vector<Integer> ramList = new Vector<>();
        String sql = "SELECT DISTINCT ram FROM Products WHERE ram IS NOT NULL ORDER BY ram ASC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ramList.add(rs.getInt("ram"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ramList;
    }

    public Vector<String> getDistinctScreenType() {
        Vector<String> screenTypeList = new Vector<>();
        String sql = "SELECT DISTINCT screenType FROM Products WHERE screenType IS NOT NULL AND screenType <> ''";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                screenTypeList.add(rs.getString("screenType"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return screenTypeList;
    }
   

    public Vector<Integer> getDistinctBatteryCapacity() {
        Vector<Integer> batteryCapacityList = new Vector<>();
        String sql = "SELECT DISTINCT batteryCapacity FROM Products WHERE batteryCapacity IS NOT NULL ORDER BY batteryCapacity ASC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                batteryCapacityList.add(rs.getInt("batteryCapacity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return batteryCapacityList;
    }

    public Vector<Double> getDistinctScreenSize() {
        Vector<Double> screenSizeList = new Vector<>();
        String sql = "SELECT DISTINCT screenSize FROM Products WHERE screenSize IS NOT NULL ORDER BY screenSize ASC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                screenSizeList.add(rs.getDouble("screenSize"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return screenSizeList;
    }

    public Vector<Product> getLatestProducts() {
        Vector<Product> products = new Vector<>();
        String sql = "SELECT p.*, MIN(v.price) AS variantPrice "
                + "FROM Products p "
                + "JOIN ProductVariants v ON p.id = v.productID "
                + "WHERE p.isDisabled = FALSE "
                + "GROUP BY p.id "
                + "ORDER BY p.createAt DESC LIMIT 10";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setBrandID(rs.getInt("brandID"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setImageURL(rs.getString("imageURL"));
                product.setCreateAt(rs.getDate("createAt"));
                product.setChipset(rs.getString("chipset"));
                product.setRam(rs.getInt("ram"));
                product.setScreenSize(rs.getDouble("screenSize"));
                product.setScreenType(rs.getString("screenType"));
                product.setResolution(rs.getString("resolution"));
                product.setBatteryCapacity(rs.getInt("batteryCapacity"));
                product.setCameraSpecs(rs.getString("cameraSpecs"));
                product.setOs(rs.getString("os"));
                product.setSimType(rs.getString("simType"));
                product.setConnectivity(rs.getString("connectivity"));
                product.setVariantPrice(rs.getDouble("variantPrice"));

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static void main(String[] args) {
        String password = "123456";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(hashedPassword);
    }
    

}
