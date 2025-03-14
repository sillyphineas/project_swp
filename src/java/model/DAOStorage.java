package model;

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

    
    public int addStorage(Storage storage) {
        int n = 0;
        String sql = "INSERT INTO Storages (capacity, status) VALUES (?, ?)";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, storage.getCapacity());
            pre.setString(2, storage.getStatus());  
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

  
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

    // Lấy tất cả dung lượng lưu trữ có trạng thái 'Active'
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
}
