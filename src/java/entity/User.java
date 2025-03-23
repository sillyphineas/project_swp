package entity;

import java.sql.Date;
import java.sql.Timestamp;

public class User {

    private int id;                // ID của user
    private String name;           // Tên user
    private String email;          // Email user
    private String passHash;       // Mật khẩu (hash)
    private boolean gender;        // Giới tính (true = Nam, false = Nữ)
    private String phoneNumber;    // Số điện thoại
    private String resetToken;     // Mã reset mật khẩu
    private Timestamp resetTokenExpired;// Ngày hết hạn mã reset
    private Date dateOfBirth;      // Ngày sinh
    private int roleId;            // Vai trò (role ID)
    private boolean isDisabled;    // Trạng thái vô hiệu hóa
    private int updatedBy;         // Người cập nhật (tham chiếu đến id)
    private Date updatedAt;        // Thời gian cập nhật
    private byte[] image;          // Avatar người dùng (dữ liệu hình ảnh dưới dạng byte)
    private Date registeredAt; // Ngày đăng ký
    // Constructor không tham số
    public User() {
    }

    // Constructor đầy đủ tham số
    public User(int id, String name, String email, String passHash, boolean gender,
            String phoneNumber, String resetToken, Timestamp resetTokenExpired,
            Date dateOfBirth, int roleId, boolean isDisabled,
            int updatedBy, Date updatedAt, byte[] image, Date registeredAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passHash = passHash;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.resetToken = resetToken;
        this.resetTokenExpired = resetTokenExpired; // bây giờ là Timestamp
        this.dateOfBirth = dateOfBirth; // Có thể giữ nguyên kiểu Date cho ngày sinh
        this.roleId = roleId;
        this.isDisabled = isDisabled;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.image = image;
        this.registeredAt = registeredAt;
    }

    // Getters và Setters cho từng thuộc tính
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public Timestamp getResetTokenExpired() {
        return resetTokenExpired;
    }

    public void setResetTokenExpired(Timestamp resetTokenExpired) {
        this.resetTokenExpired = resetTokenExpired;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        switch (this.roleId) {
            case 1:
                return "Admin";
            case 2:
                return "Marketing";
            case 3:
                return "Sale";
            case 4:
                return "Shipper";
            case 5:
                return "Customer";
            default:
                throw new AssertionError();
        }
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public boolean isIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public void setDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Getter và Setter cho trường image (avatar)
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getStatus() {
        if (this.isDisabled == true) {
            return "Disabled";
        }
        return "Active";
    }
    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }
    // Override toString() để dễ dàng hiển thị thông tin user
    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", email='" + email + '\''
                + ", passHash='" + passHash + '\''
                + ", gender=" + gender
                + ", phoneNumber='" + phoneNumber + '\''
                + ", resetToken='" + resetToken + '\''
                + ", resetTokenExpired=" + resetTokenExpired
                + ", dateOfBirth=" + dateOfBirth
                + ", roleId=" + roleId
                + ", isDisabled=" + isDisabled
                + ", updatedBy=" + updatedBy
                + ", updatedAt=" + updatedAt
                + ", registeredAt=" + registeredAt
                + ", image=" + (image != null ? "[image data]" : "null")
                + '}';
    }
}
