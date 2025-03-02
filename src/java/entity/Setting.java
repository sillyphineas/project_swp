package entity;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Setting {

    private int id;
    private int typeId;
    private String typeName;
    private String keyName;
    private String value;
    private String description;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private Integer roleId;  // Liên kết với Roles
    private Integer brandId; // Liên kết với Brands

    public Setting() {
    }

    public Setting(int id, int typeId, String typeName, String keyName, String value,
            String description, String status, Date createdAt, Date updatedAt,
            Integer roleId, Integer brandId) {
        this.id = id;
        this.typeId = typeId;
        this.typeName = typeName;
        this.keyName = keyName;
        this.value = value;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.roleId = roleId;
        this.brandId = brandId;
    }
    
    public Setting(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.typeId = rs.getInt("type_id");
        this.typeName = rs.getString("type_name");
        this.keyName = rs.getString("key_name");
        this.value = rs.getString("value");
        this.description = rs.getString("description");
        this.status = rs.getString("status");
        this.createdAt = rs.getTimestamp("created_at");
        this.updatedAt = rs.getTimestamp("updated_at");
        this.roleId = (Integer) rs.getObject("role_id");
        this.brandId = (Integer) rs.getObject("brand_id");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }
}
