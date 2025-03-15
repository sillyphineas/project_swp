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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOStorage extends DBConnection {

    public int addStorage(Storage storage) {
        int n = 0;
        String sql = "INSERT INTO Storage (capacity, status) VALUES (?, ?)";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, storage.getCapacity());
            pre.setString(2, storage.getStatus());  
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

    // Lấy Storage theo id
    public Vector<Storage> getStorageById(int id) {
        Vector<Storage> storages = new Vector<>();
        String sql = "SELECT * FROM Storages WHERE id = ?";
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
        String sql = "UPDATE Storages SET capacity = ?, status = ? WHERE id = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, storage.getCapacity());
            pre.setString(2, storage.getStatus());
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
        String sql = "DELETE FROM Storages WHERE id = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, id);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    // Lấy Storage ID theo Capacity
    public int getStorageIDByCapacity(String capacity) {
        String query = "SELECT id FROM Storages WHERE capacity = ? AND status = 'Active'";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
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

    // Lấy Storage theo id với 1 đối tượng Storage
    public Storage getStorageById1(int storageId) {
        String sql = "SELECT id, capacity, status FROM Storages WHERE id = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, storageId);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return new Storage(
                        rs.getInt("id"),
                        rs.getString("capacity"),
                        rs.getString("status")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public Vector<Storage> getAllStorages() {
        Vector<Storage> storages = new Vector<>();
        String sql = "SELECT * FROM Storages WHERE status = 'Active'";  

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // Tạo đối tượng Storage và thêm vào vector
                Storage storage = new Storage(
                        rs.getInt("id"),
                        rs.getString("capacity"),
                        rs.getString("status"));
                storages.add(storage);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return storages;
    }
    
    public static void main(String[] args) {
        DAOStorage dao = new DAOStorage();
        System.out.println(dao.getStorageIDByCapacity("128GB"));
    }

}