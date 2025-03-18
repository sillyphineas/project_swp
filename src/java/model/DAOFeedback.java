/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

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

/**
 *
 * @author HP
 */
public class DAOFeedback extends DBConnection {

    public List<Feedback> getPaginatedFeedbacks(int page, int pageSize) {
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
                + "JOIN Products p ON pv.product_id = p.id "
                + "JOIN Colors c ON pv.color_id = c.id "
                + "JOIN Storages s ON pv.storage_id = s.id "
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

    public List<Feedback> getLatestFeedbacksByProductId(int productId) {
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
                + "s.id AS storageID, s.capacity "
                + "FROM Feedbacks f "
                + "JOIN Users u ON f.reviewerID = u.id "
                + "JOIN OrderDetails od ON f.orderDetailID = od.id "
                + "JOIN ProductVariants pv ON od.productVariantID = pv.id "
                + "JOIN Products p ON f.product_id = p.id "
                + "JOIN Colors c ON pv.color_id = c.id "
                + "JOIN Storages s ON pv.storage_id = s.id "
                + "WHERE p.id = ? "
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


    public static void main(String[] args) {
        DAOFeedback dao = new DAOFeedback();
        System.out.println(dao.getPaginatedFeedbacksByStar(1, 4, 1, 10));
    }
}
