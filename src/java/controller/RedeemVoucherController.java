package controller;

import entity.User;
import entity.Voucher;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import model.DAOUser;
import model.DAOVoucher;
import model.DAOUserVoucher;

@WebServlet(name = "RedeemVoucherController", urlPatterns = {"/RedeemVoucherController"})
public class RedeemVoucherController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // DEBUG: in ra thông tin voucherIdRaw
        String voucherIdRaw = request.getParameter("voucherId");
        System.out.println("RedeemVoucherController: voucherIdRaw = " + voucherIdRaw);

        // 1) Kiểm tra user đăng nhập
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            out.write("Bạn cần đăng nhập để đổi voucher.");
            return;
        }

        if (voucherIdRaw == null) {
            out.write("Thiếu voucherId!");
            return;
        }

        int voucherId;
        try {
            voucherId = Integer.parseInt(voucherIdRaw);
        } catch (NumberFormatException e) {
            System.out.println("RedeemVoucherController: voucherId không hợp lệ: " + voucherIdRaw);
            out.write("voucherId không hợp lệ!");
            return;
        }

        // 2) Lấy thông tin voucher
        DAOVoucher daoV = new DAOVoucher();
        Voucher v = daoV.getVoucherByVoucherId(voucherId);
        if (v == null) {
            System.out.println("RedeemVoucherController: Voucher không tồn tại, voucherId = " + voucherId);
            out.write("Voucher không tồn tại!");
            return;
        }

        // 3) Kiểm tra usage limit: Nếu voucher chỉ cho đổi 1 lần và user đã đổi rồi
        DAOUserVoucher daoUV = new DAOUserVoucher();
        if (v.getUsageLimit() == 1 && daoUV.hasRedeemed(user.getId(), voucherId)) {
            System.out.println("RedeemVoucherController: User " + user.getId() + " đã đổi voucher " + voucherId);
            out.write("Bạn đã đổi voucher này rồi!");
            return;
        }

        // 4) Lấy số điểm cần từ cột 'point' của voucher
        int pointsNeeded = v.getPoint();
        System.out.println("RedeemVoucherController: pointsNeeded = " + pointsNeeded);

        // 5) Kiểm tra user đủ điểm chưa
        DAOUser daoU = new DAOUser();
        int userPoints = daoU.getUserPoints(user.getId());
        System.out.println("RedeemVoucherController: userPoints = " + userPoints);
        if (userPoints < pointsNeeded) {
            out.write("Bạn không đủ điểm để đổi voucher này!");
            return;
        }

        // 6) Thực hiện giao dịch đổi voucher
        boolean success = daoUV.redeemVoucherTransaction(user.getId(), voucherId, pointsNeeded);
        System.out.println("RedeemVoucherController: redeemVoucherTransaction = " + success);
        if (success) {
            // Cập nhật lại điểm cho user trong session
            user.setPoint(userPoints - pointsNeeded);
            session.setAttribute("user", user);
            out.write("success");
        } else {
            out.write("Đổi voucher thất bại, vui lòng thử lại!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.getWriter().write("Use POST method to redeem voucher!");
    }
}
