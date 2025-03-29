/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import entity.User;
import model.DAOUser;
import org.mindrot.jbcrypt.BCrypt;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Validate {
    public static int checkLoginStatus(String email, String password) {
        DAOUser daoUser = new DAOUser();
        User user = daoUser.getUserByEmail(email);
        if (user == null) {
            return -1; 
        }
        if (user.isIsDisabled()) {
            return -2;
        }
        if (!BCrypt.checkpw(password, user.getPassHash())) {
            return -3;
        }
        return user.getRoleId();
    }
    
    
    public static boolean checkLoginValidUser(String email, String password) {
        DAOUser daouser = new DAOUser();
        User user = daouser.getUserByEmail(email);
        if (user != null && !user.isIsDisabled()) {
            return BCrypt.checkpw(password, user.getPassHash());
        }
        return false;
    }

    
    public static boolean checkRegisterExistedEmail(String email) {
        DAOUser daouser = new DAOUser();
        User user = daouser.getUserByEmail(email);
        return (user == null);
    }
    
    
    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }

    
    public static boolean checkRegisterPasswordLength(String password) {
        return password.length() >= 6;
    }
    
    
    public static boolean checkRegisterEqualPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
    
    
    public static boolean isValidName(String name) {
        String regex = "^[\\p{L}\\s]+$";
        return name.matches(regex);
    }

    
    public static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^0\\d{9}$";
        return phoneNumber.matches(regex);
    }
    
    
    public static boolean isValidDateOfBirth(String dateStr) {
        try {
            LocalDate dob = LocalDate.parse(dateStr);
            LocalDate today = LocalDate.now();
            return !dob.isAfter(today);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}


