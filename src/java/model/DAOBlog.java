
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

    public int updateBlog(Blog blog) {
        String sql = "UPDATE Blogs SET title = ?, authorID = ?, postTime = ?, content = ?, imageURL = ?, backlinks = ?, isDisabled = ? WHERE id = ?";
        int n = 0;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, blog.getTitle());
            stmt.setInt(2, blog.getAuthorID());
            stmt.setString(3, blog.getPostTime());
            stmt.setString(4, blog.getContent());
            stmt.setString(5, blog.getImageURL());
            stmt.setString(6, blog.getBacklinks());
            stmt.setBoolean(7, blog.isIsDisabled());
            stmt.setInt(8, blog.getId());

            n = stmt.executeUpdate();
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

    public List<Blog> MaketingBlogs(int page, int pageSize) throws SQLException {
        String sql = "SELECT * FROM Blogs LIMIT ? OFFSET ?";
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
        String sql = "SELECT COUNT(*) FROM Blogs WHERE isDisabled = 0 ";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        }
        return total;
    }

    public int getMaketingTotalBlogs() throws SQLException {
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

        String sql = "SELECT * FROM Blogs WHERE (LOWER(title) LIKE LOWER(?) OR LOWER(content) LIKE LOWER(?)) AND isDisabled = 0 ORDER BY postTime DESC LIMIT ? OFFSET ?;";

        List<Blog> blogs = new ArrayList<>();

        try (PreparedStatement pre = conn.prepareStatement(sql)) {

            pre.setString(1, "%" + query.toLowerCase() + "%");
            pre.setString(2, "%" + query.toLowerCase() + "%");
            pre.setInt(3, pageSize);
            pre.setInt(4, (page - 1) * pageSize);

            // Thực thi truy vấn
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
    
     public List<Blog> MaketingSearchBlogs(String query, int page, int pageSize) throws SQLException {

        String sql = "SELECT * FROM Blogs WHERE (LOWER(title) LIKE LOWER(?) OR LOWER(content) LIKE LOWER(?)) LIMIT ? OFFSET ?;";

        List<Blog> blogs = new ArrayList<>();

        try (PreparedStatement pre = conn.prepareStatement(sql)) {

            pre.setString(1, "%" + query.toLowerCase() + "%");
            pre.setString(2, "%" + query.toLowerCase() + "%");
            pre.setInt(3, pageSize);
            pre.setInt(4, (page - 1) * pageSize);

            // Thực thi truy vấn
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
    public int countTotalBlogsForSearch(String query) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Blogs WHERE (LOWER(title) LIKE LOWER(?) OR LOWER(content) LIKE LOWER(?)) AND isDisabled = 0;";

        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, "%" + query + "%");
            pre.setString(2, "%" + query + "%");

            ResultSet rs = pre.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0; // Nếu không có bài viết nào, trả về 0
    }

    public int delete(int blogId) throws SQLException {
        String sql = "DELETE FROM Blogs WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, blogId);
            return stmt.executeUpdate();
        }
    }

    public List<Blog> getFilteredBlogs(int page, int pageSize, Integer id, Integer authorID, Boolean isDisabled) throws SQLException {
        List<Blog> blogs = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Blogs WHERE 1=1");

        if (id != null) {
            sql.append(" AND id = ?");
        }
        if (authorID != null) {
            sql.append(" AND authorID = ?");
        }
        if (isDisabled != null) {
            sql.append(" AND isDisabled = ?");
        }

        sql.append(" LIMIT ? OFFSET ?");

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int index = 1;

            if (id != null) {
                stmt.setInt(index++, id);
            }
            if (authorID != null) {
                stmt.setInt(index++, authorID);
            }
            if (isDisabled != null) {
                stmt.setBoolean(index++, isDisabled);
            }

            stmt.setInt(index++, pageSize);
            stmt.setInt(index, (page - 1) * pageSize);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Blog blog = new Blog(
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
                blogs.add(blog);
            }
        }

        return blogs;
    }

    public int getTotalBlogs(Integer id, Integer authorID, Boolean isDisabled) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Blogs WHERE 1=1");

        if (id != null) {
            sql.append(" AND id = ?");
        }
        if (authorID != null) {
            sql.append(" AND authorID = ?");
        }
        if (isDisabled != null) {
            sql.append(" AND isDisabled = ?");
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int index = 1;

            if (id != null) {
                stmt.setInt(index++, id);
            }
            if (authorID != null) {
                stmt.setInt(index++, authorID);
            }
            if (isDisabled != null) {
                stmt.setBoolean(index++, isDisabled);
            }

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }

    public List<Blog> sortBlogs(String sortBy, String sortOrder, int page, int pageSize) throws SQLException {
        List<Blog> blogs = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM Blogs b ");
        sql.append("JOIN Users u ON b.authorID = u.id ");

        sql.append("WHERE 1=1 ");

        if (sortBy.equals("author")) {
            sortBy = "u.name";
        }

        sql.append("ORDER BY ").append(sortBy).append(" ").append(sortOrder);
        sql.append(" LIMIT ? OFFSET ?");

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            stmt.setInt(1, pageSize);
            stmt.setInt(2, (page - 1) * pageSize);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Blog blog = new Blog(
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
                blogs.add(blog);
            }
        }

        return blogs;
    }

    public boolean updateBlogStatus(int blogId, boolean isDisabled) {
        String query = "UPDATE Blogs SET isDisabled = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, isDisabled ? 1 : 0);
            stmt.setInt(2, blogId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Blog getBlogDetails(int id) {
        Blog blog = null;
        String sql = "SELECT b.*, a.name AS authorName "
                + "FROM Blogs b "
                + "JOIN Users a ON b.authorID = a.id "
                + "WHERE b.id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                blog = new Blog();
                blog.setId(rs.getInt("id"));
                blog.setAuthorID(rs.getInt("authorID"));
                blog.setPostTime(rs.getString("postTime"));
                blog.setTitle(rs.getString("title"));
                blog.setContent(rs.getString("content"));
                blog.setBacklinks(rs.getString("backlinks"));
                blog.setImageURL(rs.getString("imageURL"));
                blog.setStatus(rs.getString("status"));
                blog.setIsSlider(rs.getBoolean("isSlider"));
                blog.setIsDisabled(rs.getBoolean("isDisabled"));
                blog.setAuthorName(rs.getString("authorName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blog;
    }

    public int addBlog(Blog blog) {
        String sql = "INSERT INTO Blogs (authorID, postTime, title, content, backlinks, imageURL, status, isSlider, isDisabled) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, blog.getAuthorID());
            stmt.setString(2, blog.getPostTime());
            stmt.setString(3, blog.getTitle());
            stmt.setString(4, blog.getContent());
            stmt.setString(5, blog.getBacklinks());
            stmt.setString(6, blog.getImageURL());
            stmt.setString(7, blog.getStatus());
            stmt.setBoolean(8, blog.isIsDisabled());
            stmt.setBoolean(9, blog.isIsSlider());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public List<Blog> getPaginatedSlider(int page, int pageSize) throws SQLException {
        String sql = "SELECT * FROM Blogs WHERE isDisabled = 0 and isSlider = 1 ORDER BY postTime DESC LIMIT ? OFFSET ?";
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

    public static void main(String[] args) {
        DAOBlog dao = new DAOBlog();
        try {
            System.out.println(dao.getFilteredBlogs(1, 5, 1, 0, true));
        } catch (SQLException ex) {
            Logger.getLogger(DAOBlog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
