/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import entity.User;
import model.DAOUser;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author HP
 */
public class Validate {
    public static boolean checkLoginValidUser(String email, String password) {
        DAOUser daouser = new DAOUser();
        User user = daouser.getUserByEmail(email);
        if (user != null && !user.isIsDisabled()) {
            if (BCrypt.checkpw(password, user.getPassHash())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    
    public static boolean checkRegisterExistedEmail(String email) {
        DAOUser daouser = new DAOUser();
        User user = daouser.getUserByEmail(email);
        if (user != null) {
            return false;
        }
        return true;
    }
    
    public static boolean checkRegisterPasswordLength(String password){
        if (password.length() < 6) {
            return false;
        }
        return true;
    }
    
    public static boolean checkRegisterEqualPassword(String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            return true;
        }
        return false;
    }
    
    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }
}
