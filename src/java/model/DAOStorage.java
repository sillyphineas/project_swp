/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
import entity.Storage;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOStorage extends DBConnection {

    // Thêm Storage mới
    public int addStorage(Storage storage) {
        int n = 0;
        String sql = "INSERT INTO Storage (capacity, status) VALUES (?, ?)";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, storage.getCapacity());
            pre.setString(2, storage.isStatus());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    // Lấy danh sách tất cả Storage
    public Vector<Storage> getStorages(String sql) {
        Vector<Storage> vector = new Vector<>();
        try (Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Storage storage = new Storage(
                        rs.getInt("id"),
                        rs.getString("capacity"),
                        rs.getString("status")
                );
                vector.add(storage);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public Vector<Storage> getStorageById(int id) {
        Vector<Storage> storages = new Vector<>();
        String sql = "SELECT * FROM Storage WHERE id = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Storage storage = new Storage(
                        rs.getInt("id"),
                        rs.getString("capacity"),
                        rs.getString("status")
                );
                storages.add(storage);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return storages;
    }

    // Cập nhật thông tin Storage
    public int updateStorage(Storage storage) {
        int n = 0;
        String sql = "UPDATE Storage SET capacity = ?, status = ? WHERE id = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, storage.getCapacity());
            pre.setString(2, storage.isStatus());
            pre.setInt(3, storage.getId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    // Xóa Storage
    public int deleteStorage(int id) {
        int n = 0;
        String sql = "DELETE FROM Storage WHERE id = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, id);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public int getStorageIDByCapacity(String capacity) {
        String query = "SELECT id FROM storages WHERE capacity = ? AND status = 'Active'";
        try (
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, capacity);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public Storage getStorageById1(int storageId) {
        String sql = "SELECT id, capacity, status FROM storages WHERE id = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, storageId);
            ResultSet rs = pre.executeQuery();

            if (rs.next()) {
                return new Storage(
                        rs.getInt("id"),
                        rs.getString("capacity"),
                        rs.getString("status") // Nếu status là ENUM
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args) {
        DAOStorage dao = new DAOStorage();
        System.out.println(dao.getStorageIDByCapacity("128GB"));
    }

}
