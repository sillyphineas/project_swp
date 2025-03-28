package entity;

import java.sql.Date;
import java.sql.Timestamp;

public class User {

    private int id;
    private String name;
    private String email;
    private String passHash;
    private boolean gender;
    private String phoneNumber;
    private String resetToken;
    private Timestamp resetTokenExpired;
    private Date dateOfBirth;
    private int roleId;
    private boolean isDisabled;
    private int updatedBy;
    private Date updatedAt;
    private byte[] image;
    private Date registeredAt;
    private int point; // ✅ Trường mới

    public User() {
    }

    // ✅ Constructor đầy đủ có thêm point
    public User(int id, String name, String email, String passHash, boolean gender,
            String phoneNumber, String resetToken, Timestamp resetTokenExpired,
            Date dateOfBirth, int roleId, boolean isDisabled,
            int updatedBy, Date updatedAt, byte[] image, Date registeredAt, int point) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passHash = passHash;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.resetToken = resetToken;
        this.resetTokenExpired = resetTokenExpired;
        this.dateOfBirth = dateOfBirth;
        this.roleId = roleId;
        this.isDisabled = isDisabled;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.image = image;
        this.registeredAt = registeredAt;
        this.point = point;
    }

    // ✅ Getter & Setter cho point
    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    // Các getter/setter còn lại
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
                return "Unknown";
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }

    public String getStatus() {
        return this.isDisabled ? "Disabled" : "Active";
    }

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
                + ", point=" + point
                + ", image=" + (image != null ? "[image data]" : "null")
                + '}';
    }
}
