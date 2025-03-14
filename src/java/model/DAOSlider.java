/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import entity.Blog;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DUC MINH
 */
public class DAOSlider extends DBConnection {

    public List<Blog> getPaginatedSlider(int page, int pageSize) throws SQLException {
        String sql = "SELECT * FROM Blogs WHERE isSlider = 1 ORDER BY postTime DESC LIMIT ? OFFSET ?";
        List<Blog> sliders = new ArrayList<>();
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, pageSize);
            pre.setInt(2, (page - 1) * pageSize);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                sliders.add(new Blog(
                        rs.getInt("id"),
                        rs.getInt("authorID"),
                        rs.getString("postTime"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("backlinks"),
                        rs.getString("imageURL"),
                        rs.getString("status"),
                        rs.getBoolean("isSlider"),
                        rs.getBoolean("isDisabled")
                ));
            }
        }
        return sliders;
    }

    public int getTotalSliders() throws SQLException {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM Blogs WHERE isSlider = 1"; // Lọc theo isSlider để đếm đúng số slider

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        }
        return total;
    }

    public int addSlider(Blog blog) {
        int result = 0;
        String sql = "INSERT INTO Blogs (authorID, postTime, title, content, imageURL, backlinks, status, isSlider, isDisabled) VALUES (?, ?, ?, ?, ?, ?, ?, 1, 0)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, blog.getAuthorID());
            ps.setString(2, blog.getPostTime());
            ps.setString(3, blog.getTitle());
            ps.setString(4, blog.getContent());
            ps.setString(5, blog.getImageURL());
            ps.setString(6, blog.getBacklinks());
            ps.setString(7, blog.getStatus());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public int UpdateSlider(Blog other) {
        int n = 0;
        // Cập nhật câu lệnh SQL, bỏ qua content và status
        String sql = "UPDATE Blogs SET authorID = ?, postTime = ?, title = ?, imageURL = ?, backlinks = ?, isDisabled = ? WHERE id = ?";

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, other.getAuthorID());
            pre.setString(2, other.getPostTime());
            pre.setString(3, other.getTitle());
            pre.setString(4, other.getImageURL());
            pre.setString(5, other.getBacklinks());
            pre.setBoolean(6, other.isIsDisabled());  // Giữ giá trị của isDisabled
            pre.setInt(7, other.getId());  // Lọc theo ID của slider cần cập nhật
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public int deleteSlider(int id) {
        int n = 0;
        String sql = "UPDATE [dbo].[Blogs] SET isSlider = 0 WHERE id = ?";

        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, id);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return n;
    }

    public List<Blog> searchSliders(String query) throws SQLException {
        String sql = "SELECT * FROM Blogs WHERE (title LIKE ? OR backlinks LIKE ?) AND isSlider = 1";
        List<Blog> sliders = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + query + "%");
            ps.setString(2, "%" + query + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sliders.add(new Blog(
                        rs.getInt("id"),
                        rs.getInt("authorID"),
                        rs.getString("postTime"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("backlinks"),
                        rs.getString("imageURL"),
                        rs.getString("status"),
                        rs.getBoolean("isSlider"),
                        rs.getBoolean("isDisabled")
                ));
            }
        }
        return sliders;
    }

    public int toggleSliderStatus(int id, int isDisabled) {
        int n = 0;
        String sql = "UPDATE Blogs SET isDisabled = ? WHERE id = ?";

        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, isDisabled); // Dùng 0 cho ẩn, 1 cho hiển thị
            pre.setInt(2, id);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return n;
    }

    public List<Blog> getSlidersByStatus(String status) throws SQLException {
        String sql = "SELECT * FROM Blogs WHERE isDisabled = ? AND isSlider = 1";
        List<Blog> sliders = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sliders.add(new Blog(
                        rs.getInt("id"),
                        rs.getInt("authorID"),
                        rs.getString("postTime"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("backlinks"),
                        rs.getString("imageURL"),
                        rs.getString("status"),
                        rs.getBoolean("isSlider"),
                        rs.getBoolean("isDisabled")
                ));
            }
        }
        return sliders;
    }

    public Blog getSliderById(int id) throws SQLException {
        String sql = "SELECT * FROM Blogs WHERE id = ? AND isSlider = 1";
        Blog slider = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                slider = new Blog(
                        rs.getInt("id"),
                        rs.getInt("authorID"),
                        rs.getString("postTime"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("backlinks"),
                        rs.getString("imageURL"),
                        rs.getString("status"),
                        rs.getBoolean("isSlider"),
                        rs.getBoolean("isDisabled")
                );
            }
        }
        return slider;
    }

    public static void main(String[] args) {
        // Tạo đối tượng DAOSlider
        DAOSlider dao = new DAOSlider();

        // Tạo một đối tượng Blog để cập nhật
        Blog sliderToUpdate = new Blog();
        sliderToUpdate.setId(1);  // Chọn ID của blog cần cập nhật
        sliderToUpdate.setTitle("Updated Slider Title");
        sliderToUpdate.setContent("This is the updated content of the slider.");
        sliderToUpdate.setImageURL("https://example.com/updated-image.jpg");
        sliderToUpdate.setBacklinks("https://example.com/updated-backlink");
        sliderToUpdate.setStatus("visible");  // Cập nhật trạng thái
        sliderToUpdate.setIsSlider(true);     // Giữ trạng thái là slider
        sliderToUpdate.setIsDisabled(false);  // Giữ trạng thái là không bị ẩn

        // Gọi hàm updateSlider để cập nhật slider
        int result = dao.UpdateSlider(sliderToUpdate);

        // Kiểm tra kết quả
        if (result > 0) {
            System.out.println("Slider updated successfully.");
        } else {
            System.out.println("Failed to update slider.");
        }
    }

}
