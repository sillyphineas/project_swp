/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import entity.Order;
import entity.OrderDetail;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    public static void sendRegisterMail(String email, String verificationCode) {
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

    public static void sendOrderMail(String email, Order od, List<OrderDetail> details, Map<Integer, String> variantNames) {
        final String from = "haiductran712@gmail.com";
        final String password = "ojzoostchirajnjk";

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

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-Type", "text/HTML; charset=UTF-8");
            msg.setFrom(from);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
            msg.setSubject("Information about your order");
            msg.setSentDate(new Date());

            StringBuilder sb = new StringBuilder();
            sb.append("<h3>Thank you for your order!</h3>");
            sb.append("<p><strong>Order ID:</strong> ").append(od.getId()).append("</p>");
            sb.append("<p><strong>Order Time:</strong> ").append(od.getOrderTime()).append("</p>");
            sb.append("<p><strong>Order Status:</strong> ").append(od.getOrderStatus()).append("</p>");
            sb.append("<p><strong>Shipping Address:</strong> ").append(od.getShippingAddress()).append("</p>");
            sb.append("<p><strong>Recipient Name:</strong> ").append(od.getRecipientName()).append("</p>");
            sb.append("<p><strong>Recipient Phone:</strong> ").append(od.getRecipientPhone()).append("</p>");
            sb.append("<p><strong>Total Price:</strong> ").append(od.getTotalPrice()).append("</p>");

            sb.append("<h4>Order Details:</h4>");
            sb.append("<table border='1' style='border-collapse:collapse;'>");
            sb.append("<thead>");
            sb.append("<tr><th>Product Variant</th><th>Quantity</th></tr>");
            sb.append("</thead><tbody>");

            for (OrderDetail detail : details) {
                String variantName = variantNames.get(detail.getProductVariantID());
                sb.append("<tr>");
                sb.append("<td>").append(variantName != null ? variantName : "Unknown").append("</td>");
                sb.append("<td>").append(detail.getQuantity()).append("</td>");
                sb.append("</tr>");
            }
            
            sb.append("</tbody></table>");
            sb.append("<p>We will process your order soon. Thank you!</p>");
            String content = sb.toString();
            msg.setContent(content, "text/html; charset=UTF-8");
            Transport.send(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
