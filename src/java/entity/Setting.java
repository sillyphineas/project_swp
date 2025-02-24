package entity;

import java.util.Date;

public class Setting {
    private int id;
    private String type;
    private String keyName;
    private String value;
    private String description;
    private String status;  // Chuyển từ boolean sang String
    private Date createdAt;
    private Date updatedAt;

    // Constructor
    public Setting() {
    }

    public Setting(int id, String type, String keyName, String value, String description, String status, Date createdAt, Date updatedAt) {
        this.id = id;
        this.type = type;
        this.keyName = keyName;
        this.value = value;
        this.description = description;
        this.status = status;  // Cập nhật status là String
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getStatus() {  // Thay đổi từ boolean thành String
        return status;
    }

    public void setStatus(String status) {  // Cập nhật phương thức setter
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

    @Override
    public String toString() {
        return "Setting{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", keyName='" + keyName + '\'' +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +  // Hiển thị status dưới dạng String
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
