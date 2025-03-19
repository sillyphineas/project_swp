/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.google.gson.Gson;
import entity.Color;
import entity.Feedback;
import entity.Product;
import entity.ProductVariant;
import entity.Storage;
import entity.User;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class DAOFeedback extends DBConnection {

    public List<Feedback> getPaginatedFeedbacks(int page, int pageSize) {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT f.id, f.orderDetailID, f.reviewerID, f.reviewTime, f.rating, f.status, "
                + "f.content, f.images, u.name, u.email, u.phoneNumber, u.gender, "
                + "p.id AS productID, p.name AS productName, "
                + "pv.id AS productVariantID, c.id AS colorID, c.colorName, "
                + "s.id AS storageID, s.capacity "
                + "FROM Feedbacks f "
                + "JOIN Users u ON f.reviewerID = u.id "
                + "JOIN OrderDetails od ON f.orderDetailID = od.id "
                + "JOIN ProductVariants pv ON od.productVariantID = pv.id "
                + "JOIN Products p ON f.product_id = p.id " // Lấy product_id trực tiếp từ Feedbacks
                + "LEFT JOIN Colors c ON pv.color_id = c.id " // LEFT JOIN để tránh lỗi nếu không có biến thể
                + "LEFT JOIN Storages s ON pv.storage_id = s.id "
                + "ORDER BY f.reviewTime DESC "
                + "LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pageSize);
            stmt.setInt(2, (page - 1) * pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id"));
                feedback.setOrderDetailID(rs.getInt("orderDetailID"));
                feedback.setReviewerID(rs.getInt("reviewerID"));
                feedback.setReviewTime(rs.getString("reviewTime"));
                feedback.setRating(rs.getInt("rating"));
                feedback.setContent(rs.getString("content"));
                feedback.setImages(rs.getString("images"));
                feedback.setStatus(rs.getString("status"));

                // Thông tin người dùng
                User user = new User();
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setGender(rs.getBoolean("gender"));
                feedback.setUser(user);

                // Thông tin sản phẩm
                Product product = new Product();
                product.setId(rs.getInt("productID"));
                product.setName(rs.getString("productName"));
                feedback.setProduct(product);

                // Thông tin biến thể sản phẩm
                ProductVariant variant = new ProductVariant();
                variant.setId(rs.getInt("productVariantID"));

                // Gán Color vào ProductVariant
                Color color = new Color();
                color.setId(rs.getInt("colorID"));
                color.setColorName(rs.getString("colorName"));
                variant.setColor(color);

                // Gán Storage vào ProductVariant
                Storage storage = new Storage();
                storage.setId(rs.getInt("storageID"));
                storage.setCapacity(rs.getString("capacity"));
                variant.setStorage(storage);

                feedback.setProductVariant(variant);

                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    public List<Feedback> getLatestFeedbacksByProductId1(int productId) {
        List<Feedback> feedbacks = new ArrayList<>();
        String query = "SELECT f.*, u.name AS user_name FROM Feedbacks f "
                + "LEFT JOIN Users u ON f.reviewerID = u.id "
                + "WHERE f.product_id = ? ORDER BY f.reviewTime DESC LIMIT 3";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id"));

                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                feedback.setProduct(product);

                User user = new User();
                user.setName(rs.getString("user_name"));
                feedback.setUser(user);

                feedback.setRating(rs.getInt("rating"));
                feedback.setContent(rs.getString("content"));
                feedback.setReviewTime(rs.getString("reviewTime"));

                feedbacks.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbacks;
    }
    public List<Feedback> getLatestFeedbacksByProductId(int productId) {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT f.id, f.orderDetailID, f.reviewerID, f.reviewTime, f.rating, "
                + "f.content, f.images, u.name, u.email, u.phoneNumber, u.gender, "
                + "p.id AS productID, p.name AS productName, "
                + "pv.id AS productVariantID, c.id AS colorID, c.colorName, "
                + "s.id AS storageID, s.capacity "
                + "FROM Feedbacks f "
                + "JOIN Users u ON f.reviewerID = u.id "
                + "JOIN OrderDetails od ON f.orderDetailID = od.id "
                + "JOIN ProductVariants pv ON od.productVariantID = pv.id "
                + "JOIN Products p ON f.product_id = p.id "
                + "JOIN Colors c ON pv.color_id = c.id "
                + "JOIN Storages s ON pv.storage_id = s.id "
                + "WHERE f.product_id = ? AND f.status = 'visible' ORDER BY f.reviewTime DESC LIMIT 3";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id"));
                feedback.setOrderDetailID(rs.getInt("orderDetailID"));
                feedback.setReviewerID(rs.getInt("reviewerID"));
                feedback.setReviewTime(rs.getString("reviewTime"));
                feedback.setRating(rs.getInt("rating"));
                feedback.setContent(rs.getString("content"));
                feedback.setImages(rs.getString("images"));

                // Thông tin người dùng
                User user = new User();
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setGender(rs.getBoolean("gender"));
                feedback.setUser(user);

                // Thông tin sản phẩm
                Product product = new Product();
                product.setId(rs.getInt("productID"));
                product.setName(rs.getString("productName"));
                feedback.setProduct(product);

                // Thông tin biến thể sản phẩm
                ProductVariant variant = new ProductVariant();
                variant.setId(rs.getInt("productVariantID"));

                // Gán Color vào ProductVariant
                Color color = new Color();
                color.setId(rs.getInt("colorID"));
                color.setColorName(rs.getString("colorName"));
                variant.setColor(color);

                // Gán Storage vào ProductVariant
                Storage storage = new Storage();
                storage.setId(rs.getInt("storageID"));
                storage.setCapacity(rs.getString("capacity"));
                variant.setStorage(storage);

                feedback.setProductVariant(variant);

                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }
    public int getTotalFeedbacks() {
        String sql = "SELECT COUNT(*) AS total FROM Feedbacks";
        int totalFeedbacks = 0;

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                totalFeedbacks = rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalFeedbacks;
    }

    public void saveImages(int feedbackId, String jsonImages) {
        String query = "UPDATE Feedbacks SET images = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, jsonImages);
            ps.setInt(2, feedbackId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Feedback> getPaginatedFeedbacksByProductId(int productId, int page, int pageSize) {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT f.id, f.orderDetailID, f.reviewerID, f.reviewTime, f.rating, "
                + "f.content, f.images, u.name, u.email, u.phoneNumber, u.gender, "
                + "p.id AS productID, p.name AS productName, "
                + "pv.id AS productVariantID, c.id AS colorID, c.colorName, "
                + "s.id AS storageID, s.capacity, f.reply "
                + "FROM Feedbacks f "
                + "JOIN Users u ON f.reviewerID = u.id "
                + "JOIN OrderDetails od ON f.orderDetailID = od.id "
                + "JOIN ProductVariants pv ON od.productVariantID = pv.id "
                + "JOIN Products p ON f.product_id = p.id "
                + "JOIN Colors c ON pv.color_id = c.id "
                + "JOIN Storages s ON pv.storage_id = s.id "
                + "WHERE p.id = ? AND f.status = 'visible' "
                + "ORDER BY f.reviewTime DESC "
                + "LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, (page - 1) * pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id"));
                feedback.setOrderDetailID(rs.getInt("orderDetailID"));
                feedback.setReviewerID(rs.getInt("reviewerID"));
                feedback.setReviewTime(rs.getString("reviewTime"));
                feedback.setRating(rs.getInt("rating"));
                feedback.setContent(rs.getString("content"));
                feedback.setImages(rs.getString("images"));
                feedback.setReply(rs.getString("reply"));
                // Thông tin người dùng
                User user = new User();
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setGender(rs.getBoolean("gender"));
                feedback.setUser(user);

                // Thông tin sản phẩm
                Product product = new Product();
                product.setId(rs.getInt("productID"));
                product.setName(rs.getString("productName"));
                feedback.setProduct(product);

                // Thông tin biến thể sản phẩm
                ProductVariant variant = new ProductVariant();
                variant.setId(rs.getInt("productVariantID"));

                // Gán Color vào ProductVariant
                Color color = new Color();
                color.setId(rs.getInt("colorID"));
                color.setColorName(rs.getString("colorName"));
                variant.setColor(color);

                // Gán Storage vào ProductVariant
                Storage storage = new Storage();
                storage.setId(rs.getInt("storageID"));
                storage.setCapacity(rs.getString("capacity"));
                variant.setStorage(storage);

                feedback.setProductVariant(variant);

                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    public int getTotalFeedbacksByProductId(int productId) {
        String sql = "SELECT COUNT(*) FROM Feedbacks WHERE product_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Feedback> getPaginatedFeedbacksByStar(int productId, int star, int page, int pageSize) {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT f.id, f.orderDetailID, f.reviewerID, f.reviewTime, f.rating, "
                + "f.content, f.images, u.name, u.email, u.phoneNumber, u.gender, "
                + "p.id AS productID, p.name AS productName, "
                + "pv.id AS productVariantID, c.id AS colorID, c.colorName, "
                + "s.id AS storageID, s.capacity "
                + "FROM Feedbacks f "
                + "JOIN Users u ON f.reviewerID = u.id "
                + "JOIN OrderDetails od ON f.orderDetailID = od.id "
                + "JOIN ProductVariants pv ON od.productVariantID = pv.id "
                + "JOIN Products p ON f.product_id = p.id "
                + "JOIN Colors c ON pv.color_id = c.id "
                + "JOIN Storages s ON pv.storage_id = s.id "
                + "WHERE p.id = ? AND f.rating = ? "
                + "ORDER BY f.reviewTime DESC "
                + "LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            stmt.setInt(2, star);
            stmt.setInt(3, pageSize);
            stmt.setInt(4, (page - 1) * pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id"));
                feedback.setOrderDetailID(rs.getInt("orderDetailID"));
                feedback.setReviewerID(rs.getInt("reviewerID"));
                feedback.setReviewTime(rs.getString("reviewTime"));
                feedback.setRating(rs.getInt("rating"));
                feedback.setContent(rs.getString("content"));
                feedback.setImages(rs.getString("images"));

                // Thông tin người dùng
                User user = new User();
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setGender(rs.getBoolean("gender"));
                feedback.setUser(user);

                // Thông tin sản phẩm
                Product product = new Product();
                product.setId(rs.getInt("productID"));
                product.setName(rs.getString("productName"));
                feedback.setProduct(product);

                // Thông tin biến thể sản phẩm
                ProductVariant variant = new ProductVariant();
                variant.setId(rs.getInt("productVariantID"));

                // Gán Color vào ProductVariant
                Color color = new Color();
                color.setId(rs.getInt("colorID"));
                color.setColorName(rs.getString("colorName"));
                variant.setColor(color);

                // Gán Storage vào ProductVariant
                Storage storage = new Storage();
                storage.setId(rs.getInt("storageID"));
                storage.setCapacity(rs.getString("capacity"));
                variant.setStorage(storage);

                feedback.setProductVariant(variant);

                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    public List<Feedback> MaketinggetPaginatedFeedbacksByStar(int star, int page, int pageSize) {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT f.id, f.orderDetailID, f.reviewerID, f.reviewTime, f.rating, f.status,"
                + "f.content, f.images, u.name, u.email, u.phoneNumber, u.gender, "
                + "p.id AS productID, p.name AS productName, "
                + "pv.id AS productVariantID, c.id AS colorID, c.colorName, "
                + "s.id AS storageID, s.capacity "
                + "FROM Feedbacks f "
                + "JOIN Users u ON f.reviewerID = u.id "
                + "JOIN OrderDetails od ON f.orderDetailID = od.id "
                + "JOIN ProductVariants pv ON od.productVariantID = pv.id "
                + "JOIN Products p ON f.product_id = p.id "
                + "JOIN Colors c ON pv.color_id = c.id "
                + "JOIN Storages s ON pv.storage_id = s.id "
                + "WHERE f.rating = ? "
                + "ORDER BY f.reviewTime DESC "
                + "LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, star);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, (page - 1) * pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id"));
                feedback.setOrderDetailID(rs.getInt("orderDetailID"));
                feedback.setReviewerID(rs.getInt("reviewerID"));
                feedback.setReviewTime(rs.getString("reviewTime"));
                feedback.setRating(rs.getInt("rating"));
                feedback.setContent(rs.getString("content"));
                feedback.setImages(rs.getString("images"));
                feedback.setStatus(rs.getString("status"));

                // Thông tin người dùng
                User user = new User();
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setGender(rs.getBoolean("gender"));
                feedback.setUser(user);

                // Thông tin sản phẩm
                Product product = new Product();
                product.setId(rs.getInt("productID"));
                product.setName(rs.getString("productName"));
                feedback.setProduct(product);

                // Thông tin biến thể sản phẩm
                ProductVariant variant = new ProductVariant();
                variant.setId(rs.getInt("productVariantID"));

                // Gán Color vào ProductVariant
                Color color = new Color();
                color.setId(rs.getInt("colorID"));
                color.setColorName(rs.getString("colorName"));
                variant.setColor(color);

                // Gán Storage vào ProductVariant
                Storage storage = new Storage();
                storage.setId(rs.getInt("storageID"));
                storage.setCapacity(rs.getString("capacity"));
                variant.setStorage(storage);

                feedback.setProductVariant(variant);

                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    public int getTotalFeedbacksByStar(int productId, int star) {
        String sql = "SELECT COUNT(*) FROM Feedbacks WHERE product_id = ? AND rating = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setInt(2, star);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
      public Feedback getFeedbackById(int feedbackId) {
        Feedback feedback = null;
        String sql = "SELECT f.id, f.orderDetailID, f.reviewerID, f.reviewTime, f.rating, "
                + "f.content, f.images, f.isDisabled, u.name, u.email, u.phoneNumber, u.gender, "
                + "p.id AS productID, p.name AS productName, "
                + "pv.id AS productVariantID, c.id AS colorID, c.colorName, "
                + "s.id AS storageID, s.capacity "
                + "FROM Feedbacks f "
                + "JOIN Users u ON f.reviewerID = u.id "
                + "JOIN OrderDetails od ON f.orderDetailID = od.id "
                + "JOIN ProductVariants pv ON od.productVariantID = pv.id "
                + "JOIN Products p ON pv.product_id = p.id "
                + "JOIN Colors c ON pv.color_id = c.id "
                + "JOIN Storages s ON pv.storage_id = s.id "
                + "WHERE f.id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, feedbackId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                feedback = new Feedback();
                feedback.setId(rs.getInt("id"));
                feedback.setOrderDetailID(rs.getInt("orderDetailID"));
                feedback.setReviewerID(rs.getInt("reviewerID"));
                feedback.setReviewTime(rs.getString("reviewTime"));
                feedback.setRating(rs.getInt("rating"));
                feedback.setContent(rs.getString("content"));
                feedback.setImages(rs.getString("images"));
                feedback.setIsDisabled(rs.getBoolean("isDisabled"));

               
                User user = new User();
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setGender(rs.getBoolean("gender"));
                feedback.setUser(user);

               
                Product product = new Product();
                product.setId(rs.getInt("productID"));
                product.setName(rs.getString("productName"));
                feedback.setProduct(product);

               
                ProductVariant variant = new ProductVariant();
                variant.setId(rs.getInt("productVariantID"));

               
                Color color = new Color();
                color.setId(rs.getInt("colorID"));
                color.setColorName(rs.getString("colorName"));
                variant.setColor(color);

               
                Storage storage = new Storage();
                storage.setId(rs.getInt("storageID"));
                storage.setCapacity(rs.getString("capacity"));
                variant.setStorage(storage);

                feedback.setProductVariant(variant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedback;
    }

    
    public void updateFeedbackStatus(int feedbackId, boolean isDisabled) {
        String sql = "UPDATE Feedbacks SET isDisabled = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, isDisabled);
            stmt.setInt(2, feedbackId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int MaketinggetTotalFeedbacksByStar(int star) {
        String sql = "SELECT COUNT(*) FROM Feedbacks WHERE rating = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, star);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Feedback> MaketingrsearchFeedbacks(String query, int page, int pageSize) {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT f.id, f.orderDetailID, f.reviewerID, f.reviewTime, f.rating, "
                + "f.content, f.images, u.name, u.email, u.phoneNumber, u.gender, "
                + "p.id AS productID, p.name AS productName, "
                + "pv.id AS productVariantID, c.id AS colorID, c.colorName, "
                + "s.id AS storageID, s.capacity "
                + "FROM Feedbacks f "
                + "JOIN Users u ON f.reviewerID = u.id "
                + "JOIN OrderDetails od ON f.orderDetailID = od.id "
                + "JOIN ProductVariants pv ON od.productVariantID = pv.id "
                + "JOIN Products p ON f.product_id = p.id "
                + "JOIN Colors c ON pv.color_id = c.id "
                + "JOIN Storages s ON pv.storage_id = s.id "
                + "WHERE LOWER(u.name) LIKE ? OR LOWER(f.content) LIKE ? "
                + "ORDER BY f.reviewTime DESC "
                + "LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + query.toLowerCase() + "%"); // Tìm theo tên
            stmt.setString(2, "%" + query.toLowerCase() + "%"); // Tìm theo nội dung đánh giá

            stmt.setInt(3, pageSize);
            stmt.setInt(4, (page - 1) * pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id"));
                feedback.setOrderDetailID(rs.getInt("orderDetailID"));
                feedback.setReviewerID(rs.getInt("reviewerID"));
                feedback.setReviewTime(rs.getString("reviewTime"));
                feedback.setRating(rs.getInt("rating"));
                feedback.setContent(rs.getString("content"));
                feedback.setImages(rs.getString("images"));

                // Thông tin người dùng
                User user = new User();
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setGender(rs.getBoolean("gender"));
                feedback.setUser(user);

                // Thông tin sản phẩm
                Product product = new Product();
                product.setId(rs.getInt("productID"));
                product.setName(rs.getString("productName"));
                feedback.setProduct(product);

                // Thông tin biến thể sản phẩm
                ProductVariant variant = new ProductVariant();
                variant.setId(rs.getInt("productVariantID"));

                // Gán Color vào ProductVariant
                Color color = new Color();
                color.setId(rs.getInt("colorID"));
                color.setColorName(rs.getString("colorName"));
                variant.setColor(color);

                // Gán Storage vào ProductVariant
                Storage storage = new Storage();
                storage.setId(rs.getInt("storageID"));
                storage.setCapacity(rs.getString("capacity"));
                variant.setStorage(storage);

                feedback.setProductVariant(variant);

                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    public int MaketingcountTotalFeedbacksForSearch(String query) {
        String sql = "SELECT COUNT(*) FROM Feedbacks f "
                + "JOIN Users u ON f.reviewerID = u.id "
                + "WHERE LOWER(u.name) LIKE ? OR LOWER(f.content) LIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + query.toLowerCase() + "%");
            stmt.setString(2, "%" + query.toLowerCase() + "%");

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu có lỗi xảy ra
    }

    public List<Feedback> sortFeedbacks(String sortBy, String sortOrder, int page, int pageSize) throws SQLException {
        List<Feedback> feedbacks = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT f.id, f.orderDetailID, f.reviewerID, f.reviewTime, f.rating, "
                + "f.content, f.images, f.isDisabled, f.status,"
                + "u.name AS reviewerName, u.email, u.phoneNumber, u.gender, "
                + "p.id AS productID, p.name AS productName, "
                + "pv.id AS productVariantID, c.id AS colorID, c.colorName, "
                + "s.id AS storageID, s.capacity "
                + "FROM Feedbacks f "
                + "JOIN Users u ON f.reviewerID = u.id "
                + "JOIN OrderDetails od ON f.orderDetailID = od.id "
                + "JOIN ProductVariants pv ON od.productVariantID = pv.id "
                + "JOIN Products p ON f.product_id = p.id "
                + "JOIN Colors c ON pv.color_id = c.id "
                + "JOIN Storages s ON pv.storage_id = s.id "
        );

        // Kiểm tra và xử lý `sortBy`
        if ("name".equals(sortBy)) {
            sortBy = "u.name";  // Sắp xếp theo tên người đánh giá
        } else if ("productName".equals(sortBy)) {
            sortBy = "p.name";  // Sắp xếp theo tên sản phẩm
        } else if ("rating".equals(sortBy)) {
            sortBy = "f.rating"; // Sắp xếp theo đánh giá
        } else if ("reviewTime".equals(sortBy)) {
            sortBy = "f.reviewTime"; // Sắp xếp theo trạng thái (bị vô hiệu hóa hay không)
        } else if ("status".equals(sortBy)) {
            sortBy = "f.status"; // Sắp xếp theo trạng thái (bị vô hiệu hóa hay không)
        } else {
            sortBy = "f.id";  // Mặc định sắp xếp theo ID
        }

        // Kiểm tra `sortOrder` để tránh SQL Injection
        sortOrder = "desc".equalsIgnoreCase(sortOrder) ? "DESC" : "ASC";

        sql.append(" ORDER BY ").append(sortBy).append(" ").append(sortOrder);
        sql.append(" LIMIT ? OFFSET ?");

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            stmt.setInt(1, pageSize);
            stmt.setInt(2, (page - 1) * pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback(
                        rs.getInt("id"),
                        rs.getInt("orderDetailID"),
                        rs.getInt("reviewerID"),
                        rs.getString("reviewTime"),
                        rs.getInt("rating"),
                        rs.getString("content"),
                        rs.getString("images"),
                        rs.getString("status")
                );

                // Thông tin người dùng
                User user = new User();
                user.setName(rs.getString("reviewerName"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setGender(rs.getBoolean("gender"));
                feedback.setUser(user);

                // Thông tin sản phẩm
                Product product = new Product();
                product.setId(rs.getInt("productID"));
                product.setName(rs.getString("productName"));
                feedback.setProduct(product);

                // Thông tin biến thể sản phẩm
                ProductVariant variant = new ProductVariant();
                variant.setId(rs.getInt("productVariantID"));

                // Gán Color vào ProductVariant
                Color color = new Color();
                color.setId(rs.getInt("colorID"));
                color.setColorName(rs.getString("colorName"));
                variant.setColor(color);

                // Gán Storage vào ProductVariant
                Storage storage = new Storage();
                storage.setId(rs.getInt("storageID"));
                storage.setCapacity(rs.getString("capacity"));
                variant.setStorage(storage);

                feedback.setProductVariant(variant);

                feedbacks.add(feedback);
            }
        }
        return feedbacks;
    }

    public boolean insertFeedback(Feedback feedback) {
        String sql = "INSERT INTO Feedbacks (orderDetailID, reviewerID, reviewTime, rating, content, images, isDisabled, product_id, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, feedback.getOrderDetailID());
            stmt.setInt(2, feedback.getReviewerID());
            stmt.setString(3, feedback.getReviewTime());
            stmt.setInt(4, feedback.getRating());
            stmt.setString(5, feedback.getContent());

            String imagesJson = new Gson().toJson(feedback.getImages());
            stmt.setString(6, imagesJson);

            stmt.setBoolean(7, feedback.isIsDisabled());
            stmt.setInt(8, feedback.getProduct_id());

            stmt.setString(9, feedback.getStatus());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateFeedbackStatus(int id, String status) {
        String sql = "UPDATE Feedbacks SET status = ? WHERE id = ?";
        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Integer getUserIdByFeedbackId(int feedbackId) {
        Integer userId = null;
        String query = "SELECT reviewerID FROM feedbacks WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, feedbackId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("reviewerID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public boolean isFeedbackExists(int orderdetailID) {
        String sql = "SELECT COUNT(*) FROM feedbacks WHERE orderdetailID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderdetailID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
     public Feedback getFeedbackDetailById(int feedbackId) {
    Feedback feedback = null;
    String sql = "SELECT f.id, f.orderDetailID, f.reviewerID, f.reviewTime, f.rating, f.status, "
            + "f.content, f.images, f.reply, u.name, u.email, u.phoneNumber, u.gender, "
            + "p.id AS productID, p.name AS productName, "
            + "pv.id AS productVariantID, c.id AS colorID, c.colorName, "
            + "s.id AS storageID, s.capacity "
            + "FROM Feedbacks f "
            + "JOIN Users u ON f.reviewerID = u.id "
            + "JOIN OrderDetails od ON f.orderDetailID = od.id "
            + "JOIN ProductVariants pv ON od.productVariantID = pv.id "
            + "JOIN Products p ON f.product_id = p.id "
            + "LEFT JOIN Colors c ON pv.color_id = c.id "
            + "LEFT JOIN Storages s ON pv.storage_id = s.id "
            + "WHERE f.id = ?";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, feedbackId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            feedback = new Feedback();
            feedback.setId(rs.getInt("id"));
            feedback.setOrderDetailID(rs.getInt("orderDetailID"));
            feedback.setReviewerID(rs.getInt("reviewerID"));
            feedback.setReviewTime(rs.getString("reviewTime"));
            feedback.setRating(rs.getInt("rating"));
            feedback.setContent(rs.getString("content"));
            feedback.setImages(rs.getString("images"));
            feedback.setStatus(rs.getString("status"));
            feedback.setReply(rs.getString("reply"));

            // Thông tin người dùng
            User user = new User();
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPhoneNumber(rs.getString("phoneNumber"));
            user.setGender(rs.getBoolean("gender"));
            feedback.setUser(user);

            // Thông tin sản phẩm
            Product product = new Product();
            product.setId(rs.getInt("productID"));
            product.setName(rs.getString("productName"));
            feedback.setProduct(product);

            // Thông tin biến thể sản phẩm
            ProductVariant variant = new ProductVariant();
            variant.setId(rs.getInt("productVariantID"));

            // Gán Color vào ProductVariant
            Color color = new Color();
            color.setId(rs.getInt("colorID"));
            color.setColorName(rs.getString("colorName"));
            variant.setColor(color);

            // Gán Storage vào ProductVariant
            Storage storage = new Storage();
            storage.setId(rs.getInt("storageID"));
            storage.setCapacity(rs.getString("capacity"));
            variant.setStorage(storage);

            feedback.setProductVariant(variant);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return feedback;
     }
     
    public int updateFeedbackReply(int feedbackId, String reply) {
    int n = 0;
    String sql = "UPDATE Feedbacks SET reply = ? WHERE id = ? ";
    try (PreparedStatement pre = conn.prepareStatement(sql)) {
        pre.setString(1, reply);
        pre.setInt(2, feedbackId);
        n = pre.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return n;
}
    

    public static void main(String[] args) {
        DAOFeedback dao = new DAOFeedback();
        Feedback feedback = new Feedback(
                5,
                3,
                6, // product_id
                "2025-03-15 18:15:54",
                4,
                "Great product! Highly recommended.",
                "[\"image1.jpg\", \"image2.jpg\"]", // JSON dạng chuỗi
                false,
                "visible"
        );
        System.out.println(dao.getLatestFeedbacksByProductId(1));
    }
}
