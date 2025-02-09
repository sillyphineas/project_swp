/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author HP
 */
public class EmailUtil {

    //email: haiductran712@gmail.com
    //password: ojzo ostc hira jnjk
    public static void sendMail(String email, String verificationCode) {
        final String from = "haiductran712@gmail.com";
        final String password = "ojzoostchirajnjk";

        final String to = email;
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };

        Session session = Session.getInstance(props, auth);

        MimeMessage msg = new MimeMessage(session);
        try {
            msg.addHeader("Content-Type", "text/HTML");
            msg.setFrom(from);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject("Welcome to T-Phone Store");
            msg.setSentDate(new Date());
            msg.setText("Your verified code is " + verificationCode);

            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
