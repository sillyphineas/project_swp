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
        String sql = "SELECT COUNT(*) FROM Blogs";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        }
        return total;
    }

    public int addSliders(Blog other) {
        int n = 0;
        String sql = "INSERT INTO [dbo].[Blogs]\n"
                + "           ([authorID]\n"
                + "           ,[postTime]\n"
                + "           ,[title]\n"
                + "           ,[content]\n"
                + "           ,[imageURL]\n"
                + "           ,[backlinks]\n"
                + "           ,[status]\n"
                + "           ,[isSlider])\n"
                + "           ,[isDisabled])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,1,0)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, other.getAuthorID());
            pre.setString(2, other.getPostTime());
            pre.setString(3, other.getTitle());
            pre.setString(4, other.getContent());
            pre.setString(5, other.getImageURL());
            pre.setString(6, other.getBacklinks());
            pre.setString(7, other.getStatus());
            pre.setBoolean(8, other.isIsSlider());
            pre.setBoolean(9, other.isIsDisabled());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return n;
    }

    public int UpdateSlider(Blog other) {
        int n = 0;
        String sql = "UPDATE [dbo].[Blogs]\n"
                + "   SET [authorID] = ?\n"
                + "      ,[postTime] = ?\n"
                + "      ,[title] = ?\n"
                + "      ,[content] = ?\n"
                + "      ,[imageURL] = ?\n"
                + "      ,[backlinks] = ?\n"
                + "      ,[status] = ?\n"
                + "      ,[isSlider] = ?\n"
                + "      ,[isDisabled] = ?\n"
                + " WHERE <id = ?>";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, other.getAuthorID());
            pre.setString(2, other.getPostTime());
            pre.setString(3, other.getTitle());
            pre.setString(4, other.getContent());
            pre.setString(5, other.getImageURL());
            pre.setString(6, other.getBacklinks());
            pre.setString(7, other.getStatus());
            pre.setBoolean(8, other.isIsSlider());
            pre.setBoolean(9, other.isIsDisabled());
            pre.setInt(10, other.getId());
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
        String sql = "SELECT * FROM Blogs WHERE (title LIKE ? OR content LIKE ?) AND isDisabled = 0 AND isSlider = 1;";
        List<Blog> blogs = new ArrayList<>();
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, "%" + query + "%");
            pre.setString(2, "%" + query + "%");
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                blogs.add(new Blog(
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
        return blogs;
    }

    public int toggleSliderStatus(int id, int isDisabled) {
        int n = 0;
        String sql = "UPDATE [dbo].[Blogs] SET isDisabled = ? WHERE id = ?";

        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, isDisabled); // Dùng 0 cho ẩn, 1 cho hiển thị
            pre.setInt(2, id);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return n;
    }

}
