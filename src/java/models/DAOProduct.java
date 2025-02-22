/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import entities.Product;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public int delete(int id) {
        int n = 0;
        String sql = "DELETE FROM [dbo].[Products]\n"
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

    public static void main(String[] args) {

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
            e.printStackTrace();
        }
        return productList;
    }

}
