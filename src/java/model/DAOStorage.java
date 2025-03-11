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
            pre.setInt(1, storage.getCapacity());
            pre.setBoolean(2, storage.isStatus());
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
                        rs.getInt("capacity"),
                        rs.getBoolean("status")
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
                    rs.getInt("capacity"),
                    rs.getBoolean("status")
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
            pre.setInt(1, storage.getCapacity());
            pre.setBoolean(2, storage.isStatus());
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

}
