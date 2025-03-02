/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class VNPayConfig {

    public static final String VNP_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String VNP_TMNCODE = "WPS98X5O"; // Mã Website (TmnCode) từ VNPay
    public static final String VNP_HASH_SECRET = "VEAKJI66P9KKZ05RQSSUN6WTSYQR2ZKW"; // Secret Key từ VNPay
    public static final String VNP_RETURNURL = "http://localhost:8080/project_swp/vnpay_return";
    public static final String VNP_CALLBACK_URL = "http://localhost:8080/project_swp/vnpay_callback";

    /**
     * Tạo chuỗi bảo mật HMAC SHA512
     *
     * @param key Secret Key của VNPay
     * @param data Chuỗi cần mã hóa
     * @return Mã hash HMAC-SHA512
     */
    public static String hmacSHA512(String key, String data) {
        try {
            Mac hmacSHA512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmacSHA512.init(secretKey);
            byte[] hash = hmacSHA512.doFinal(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexHash = new StringBuilder();
            for (byte b : hash) {
                hexHash.append(String.format("%02x", b));
            }
            return hexHash.toString();
        } catch (Exception e) {
            return "";
        }
    }
}
