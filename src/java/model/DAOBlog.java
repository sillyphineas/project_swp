/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import entity.Blog;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class DAOBlog extends DBConnection {

    public int addBlogs(Blog other) {
        int n = 0;
        String sql = "INSERT INTO [dbo].[Blogs]\n"
                + "           ([authorID]\n"
                + "           ,[postTime]\n"
                + "           ,[title]\n"
                + "           ,[content]\n"
                + "           ,[imageURL]\n"
                + "           ,[backlinks]\n"
                + "           ,[status]\n"
                + "           ,[isDisabled])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, other.getAuthorID());
            pre.setString(2, other.getPostTime());
            pre.setString(3, other.getTitle());
            pre.setString(4, other.getContent());
            pre.setString(5, other.getImageURL());
            pre.setString(6, other.getBacklinks());
            pre.setString(7, other.getStatus());
            pre.setBoolean(8, other.isIsDisabled());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return n;
    }

    public String getAuthorNameById(int authorId) {
        String authorName = "";
        String query = "SELECT name FROM Users WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, authorId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    authorName = rs.getString("name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authorName;
    }

    public int UpdateBlogs(Blog other) {
        int n = 0;
        String sql = "UPDATE [dbo].[Blogs]\n"
                + "   SET [authorID] = ?\n"
                + "      ,[postTime] = ?\n"
                + "      ,[title] = ?\n"
                + "      ,[content] = ?\n"
                + "      ,[imageURL] = ?\n"
                + "      ,[backlinks] = ?\n"
                + "      ,[status] = ?\n"
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
            pre.setBoolean(8, other.isIsDisabled());
            pre.setInt(9, other.getId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public Blog getBlogById(int id) {
        String sql = "Select * From Blogs where id = ?";
        Blog blog = null;
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                blog = new Blog(
                        rs.getInt("id"),
                        rs.getInt("authorID"),
                        rs.getString("postTime"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("imageURL"),
                        rs.getString("backlinks"),
                        rs.getString("status"),
                        rs.getBoolean("isSlider"),
                        rs.getBoolean("isDisabled")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blog;
    }

    public Vector<Blog> getBlogs(String sql) {
        Vector<Blog> vector = new Vector<>();
        try {
            Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Blog blog = new Blog(
                        rs.getInt("id"),
                        rs.getInt("authorID"),
                        rs.getString("postTime"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("imageURL"),
                        rs.getString("backlinks"),
                        rs.getString("status"),
                        rs.getBoolean("isSlider"),
                        rs.getBoolean("isDisabled")
                );
                vector.add(blog);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public List<Blog> getPaginatedBlogs(int page, int pageSize) throws SQLException {
        String sql = "SELECT * FROM Blogs WHERE isDisabled = 0 ORDER BY postTime DESC LIMIT ? OFFSET ?";
        List<Blog> blogs = new ArrayList<>();
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, pageSize);
            pre.setInt(2, (page - 1) * pageSize);
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

    public int getTotalBlogs() throws SQLException {
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

    public List<Blog> searchBlogs(String query, int page, int pageSize) throws SQLException {
        String sql = "SELECT * FROM Blogs WHERE (title LIKE ? OR content LIKE ?) AND isDisabled = 0 ORDER BY postTime DESC LIMIT ? OFFSET ?;";
        List<Blog> blogs = new ArrayList<>();
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, "%" + query + "%");
            pre.setString(2, "%" + query + "%");
            pre.setInt(3, pageSize);
            pre.setInt(4, (page - 1) * pageSize);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                blogs.add(new Blog(
                        rs.getInt("id"),
                        rs.getInt("authorID"),
                        rs.getString("postTime"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("imageURL"),
                        rs.getString("backlinks"),
                        rs.getString("status"),
                        rs.getBoolean("isSlider"),
                        rs.getBoolean("isDisabled")
                ));
            }
        }
        return blogs;
    }

    public int countTotalBlogs(String query) throws SQLException {
    String sql = "SELECT COUNT(*) FROM Blogs WHERE (title LIKE ? OR content LIKE ?) AND isDisabled = 0;";
    try (PreparedStatement pre = conn.prepareStatement(sql)) {
        pre.setString(1, "%" + query + "%");
        pre.setString(2, "%" + query + "%");
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
    }
    return 0;
}
    public int delete(int id) {
        int n = 0;
        String sql = "DELETE FROM [dbo].[Blogs]\n"
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

    public static void main(String[] args) {
        DAOBlog dao = new DAOBlog();

        // Test function getBlogById with a specific id
        int testBlogId = 1;  // Bạn có thể thay đổi ID blog tại đây
        Blog blog = dao.getBlogById(testBlogId);

        // Kiểm tra nếu blog tồn tại và in ra thông tin của blog
        if (blog != null) {
            System.out.println("Blog found: ");
            System.out.println("ID: " + blog.getId());
            System.out.println("Author ID: " + blog.getAuthorID());
            System.out.println("Post Time: " + blog.getPostTime());
            System.out.println("Title: " + blog.getTitle());
            System.out.println("Content: " + blog.getContent());
            System.out.println("Image URL: " + blog.getImageURL());
            System.out.println("Backlinks: " + blog.getBacklinks());
            System.out.println("Status: " + blog.getStatus());
            System.out.println("Is Disabled: " + blog.isIsDisabled());
        } else {
            System.out.println("Blog with ID " + testBlogId + " not found.");
        }
    }

}
