<%-- 
    Document   : my-points
    Created on : Mar 27, 2025, 3:40:55 PM
    Author     : HP
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.Voucher"%>
<%@page import="java.util.Vector"%>
<%@page import="entity.User"%>
<%@page import="model.DAOUserVoucher"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Redeem Points | E-Shopper</title>
        <!-- CSS links -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <link href="css/prettyPhoto.css" rel="stylesheet">
        <link href="css/price-range.css" rel="stylesheet">
        <link href="css/animate.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        <link href="css/responsive.css" rel="stylesheet">
        <!-- Favicon -->
        <link rel="shortcut icon" href="images/ico/favicon.ico">
    </head>
    <body>
        <!-- HEADER -->
        <header id="header"><!--header-->
            <div class="header_top"><!--header_top-->
                <div class="container">
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="contactinfo">
                                <ul class="nav nav-pills">
                                    <li><a href="#"><i class="fa fa-phone"></i> +84 373 335 357</a></li>
                                    <li><a href="#"><i class="fa fa-envelope"></i> haiductran712@gmail.com</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="social-icons pull-right">
                                <ul class="nav navbar-nav">
                                    <li><a href="#"><i class="fa fa-facebook"></i></a></li>
                                    <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                                    <li><a href="#"><i class="fa fa-linkedin"></i></a></li>
                                    <li><a href="#"><i class="fa fa-dribbble"></i></a></li>
                                    <li><a href="#"><i class="fa fa-google-plus"></i></a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!--/header_top-->

            <div class="header-middle"><!--header-middle-->
                <div class="container">
                    <div class="row">
                        <div class="col-sm-4">
                            <div class="logo pull-left">
                                <a href="HomePageController">
                                    <a href="HomePageController"><img src="images/home/logo.png" alt="E-Shopper Logo" /></a>
                                </a>
                            </div>
                        </div>

                        <div class="col-sm-8">
                            <div class="shop-menu pull-right">
                                <ul class="nav navbar-nav">
                                    <%
                                        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
                                        User user = (User) session.getAttribute("user");
                                        if (isLoggedIn != null && isLoggedIn) {
                                    %>
                                    <li><a href="${pageContext.request.contextPath}/CartURL"><i class="fa fa-shopping-cart"></i> Cart</a></li>    
                                    <li><a href="CustomerOrderController"><i class="fa fa-shopping-cart"></i> My Orders</a></li>

                                    <!-- Dropdown for User -->
                                    <li class="dropdown" style="position: relative">
                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="font-weight: bold">
                                            <img src="UserAvatarController" alt="Profile Image" class="img-thumbnail" style="height: 25px; width: 25px; border-radius: 50%; border: none;"/>
                                            Hello, <%=user.getEmail()%> <b class="caret"></b>
                                        </a>
                                        <ul class="dropdown-menu" style="right: 0; left: auto;">
                                            <li><a href="${pageContext.request.contextPath}/UserProfileServlet"><i class="fa fa-user"></i> Account</a></li>
                                            <li><a href="CustomerPointController"><i class="fa fa-star"></i> My Points</a></li>
                                            <li class="divider"></li>
                                            <li><a href="${pageContext.request.contextPath}/LogoutController"><i class="fa fa-power-off"></i> Logout</a></li>
                                        </ul>
                                    </li>
                                    <%
                                    } else {
                                    %>
                                    <li><a href="${pageContext.request.contextPath}/LoginController"><i class="fa fa-lock"></i> Login</a></li>
                                        <% }%>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!--/header-middle-->

            <div class="header-bottom"><!--header-bottom-->
                <div class="container">
                    <div class="row">
                        <div class="col-sm-9">
                            <div class="navbar-header">
                                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                                    <span class="sr-only">Toggle navigation</span>
                                    <span class="icon-bar"></span>
                                    <span class="icon-bar"></span>
                                    <span class="icon-bar"></span>
                                </button>
                            </div>
                            <div class="mainmenu pull-left">
                                <ul class="nav navbar-nav collapse navbar-collapse">
                                    <li><a href="HomePageController" class="active">Home</a></li>
                                    <li><a href="ProductController">Shop</a></li> 
                                    <li><a href="BlogURL?service=listAllBlogs">Blog</a></li>
                                    <li><a href="#about-us">About Us</a></li>
                                    <li><a href="ContactForward">Contact Us</a></li>
                                </ul>
                            </div>
                        </div>
                        <!--
                        <div class="col-sm-3">
                            <div class="pull-right">
                                <form action="${pageContext.request.contextPath}/ProductController" method="get">
                                    <input type="text" name="search" value="${param.search}" />
                                    <button type="submit" class="btn btn-default"><i class="fa fa-search"></i></button>
                                </form>
                            </div>
                        </div>
                        -->
                    </div>
                </div>
            </div><!--/header-bottom-->
        </header><!--/header-->
        <!-- /header -->

        <section>
            <%
                // Lấy dữ liệu từ CustomerPointController
                Integer currentPoints = (Integer) request.getAttribute("currentPoints");
                if (currentPoints == null) {
                    currentPoints = 0;
                }
                Vector<Voucher> voucherList = (Vector<Voucher>) request.getAttribute("voucherList");
                if (voucherList == null) {
                    voucherList = new Vector<>();
                }
                // Tạo đối tượng DAOUserVoucher để kiểm tra voucher đã đổi và lấy danh sách redeemed
                DAOUserVoucher daoUV = new DAOUserVoucher();
                Vector<Voucher> redeemedList = new Vector<>();
                if (user != null) {
                    redeemedList = daoUV.getRedeemedVouchers(user.getId());
                }
            %>
            <div style="text-align: center; flex: 1;">
                <h3 style="color: #555; margin-bottom: 10px;">Current Points</h3>
                <div style="position: relative; width: 200px; height: 200px; margin: 0 auto;">
                    <svg width="200" height="200" style="transform: rotate(-90deg);">
                    <circle cx="100" cy="100" r="90" fill="none" stroke="#e0e0e0" stroke-width="20" />
                    <circle id="points-circle" cx="100" cy="100" r="90" fill="none" stroke="#f39c12" stroke-width="20" stroke-dasharray="0 565.48" />
                    </svg>
                    <div id="points-display" style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); font-size: 24px; font-weight: bold; color: #333;">
                        <%= currentPoints%>
                    </div>
                </div>
                <p style="color: #777; margin-top: 10px;">Maximum Points: 10,000</p>
            </div>

            <div style="max-width: 1000px; margin: 20px auto; background: #fff; padding: 20px; display: flex; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
                <!-- Cột bên trái: Voucher đã đổi -->
                <div style="flex: 1; padding-right: 20px; border-right: 1px solid #ddd;">
                    <h2 style="color: #333;">Voucher Đã Đổi</h2>
                    <%
                        if (redeemedList.isEmpty()) {
                    %>
                    <p>Chưa có voucher nào được đổi.</p>
                    <%
                    } else {
                        for (Voucher r : redeemedList) {
                    %>
                    <div style="padding: 10px; margin-bottom: 10px; border: 1px solid #ccc; border-radius: 5px;">
                        <strong><%= r.getVoucherCode()%></strong>
                        <br/>
                        <span style="font-size: 14px;"><%= r.getDescription()%></span>
                        <br/>
                        <span style="font-size: 12px; color: #888;">Hiệu lực: <%= r.getStartDate()%> - <%= r.getExpiredDate()%></span>
                    </div>
                    <%
                            }
                        }
                    %>
                </div>

                <!-- Cột bên phải: Available Vouchers -->
                <div style="flex: 2; padding-left: 20px;">
                    <h2 style="color: #333;">Available Vouchers</h2>
                    <div style="margin-top: 20px; display: flex; flex-direction: column; gap: 15px;">
                        <%
                            for (Voucher v : voucherList) {
                                // Kiểm tra xem user đã đổi voucher này chưa (nếu usageLimit = 1)
                                boolean alreadyRedeemed = false;
                                if (user != null) {
                                    alreadyRedeemed = daoUV.hasRedeemed(user.getId(), v.getVoucherID());
                                }
                                boolean disableButton = (v.getUsageLimit() == 1 && alreadyRedeemed);
                        %>
                        <div style="display: flex; border-left: 8px solid #f39c12; border: 1px solid #ddd; border-radius: 8px; padding: 15px; background-color: #fff; align-items: center; box-shadow: 0 2px 5px rgba(0,0,0,0.05);">
                            <div style="flex: 1;">
                                <div style="font-weight: bold; color: #f39c12;">
                                    <i class="fa fa-tag"></i> <%= v.getVoucherCode()%>
                                </div>
                                <div style="font-size: 16px;">
                                    <%= v.getDescription()%> (<%= v.getPoint()%> điểm)
                                </div>
                                <div style="font-size: 13px; color: #888;">
                                    Hiệu lực: <%= v.getStartDate()%> ~ <%= v.getExpiredDate()%>
                                </div>
                            </div>
                            <% if (disableButton) { %>
                            <button disabled style="opacity: 0.6; cursor: not-allowed;">Đã đổi</button>
                            <% } else {%>
                            <button onclick="redeemVoucher(<%= v.getPoint()%>, <%= v.getVoucherID()%>)" style="padding: 8px 16px; background-color: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer;">
                                Redeem
                            </button>
                            <% } %>
                        </div>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>

            <script>
                let currentPoints = <%= currentPoints%>;
                const maxPoints = 10000;

                // Hàm cập nhật vòng tròn hiển thị điểm
                function updatePoints(points) {
                    const circle = document.getElementById('points-circle');
                    const display = document.getElementById('points-display');
                    const circumference = 2 * Math.PI * 90;
                    const dashArray = circumference * (points / maxPoints);
                    circle.setAttribute('stroke-dasharray', `${dashArray} ${circumference - dashArray}`);
                            display.textContent = points;
                        }

                        // Sử dụng AJAX jQuery để gọi RedeemVoucherController
                        function redeemVoucher(pointsNeeded, voucherId) {
                            console.log("redeemVoucher() called - voucherId =", voucherId);
                            $.ajax({
                                url: "RedeemVoucherController",
                                type: "POST",
                                data: {voucherId: voucherId},
                                success: function (result) {
                                    console.log("Server response:", result);
                                    if (result === "success") {
                                        alert("Đổi voucher thành công!");
                                        location.reload();
                                    } else {
                                        alert(result);
                                    }
                                },
                                error: function () {
                                    alert("Lỗi khi gọi server!");
                                }
                            });
                        }

                        updatePoints(currentPoints);
            </script>
        </section>

        <footer id="footer"><!--Footer-->
            <div class="footer-top">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-2">
                            <div class="companyinfo">
                                <h2><span>e</span>-shopper</h2>
                                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit,sed do eiusmod tempor</p>
                            </div>
                        </div>
                        <div class="col-sm-7">
                            <div class="col-sm-3">
                                <div class="video-gallery text-center">
                                    <a href="#">
                                        <div class="iframe-img">
                                            <img src="images/home/iframe1.png" alt="" />
                                        </div>
                                        <div class="overlay-icon">
                                            <i class="fa fa-play-circle-o"></i>
                                        </div>
                                    </a>
                                    <p>Circle of Hands</p>
                                    <h2>24 DEC 2014</h2>
                                </div>
                            </div>

                            <div class="col-sm-3">
                                <div class="video-gallery text-center">
                                    <a href="#">
                                        <div class="iframe-img">
                                            <img src="images/home/iframe2.png" alt="" />
                                        </div>
                                        <div class="overlay-icon">
                                            <i class="fa fa-play-circle-o"></i>
                                        </div>
                                    </a>
                                    <p>Circle of Hands</p>
                                    <h2>24 DEC 2014</h2>
                                </div>
                            </div>

                            <div class="col-sm-3">
                                <div class="video-gallery text-center">
                                    <a href="#">
                                        <div class="iframe-img">
                                            <img src="images/home/iframe3.png" alt="" />
                                        </div>
                                        <div class="overlay-icon">
                                            <i class="fa fa-play-circle-o"></i>
                                        </div>
                                    </a>
                                    <p>Circle of Hands</p>
                                    <h2>24 DEC 2014</h2>
                                </div>
                            </div>

                            <div class="col-sm-3">
                                <div class="video-gallery text-center">
                                    <a href="#">
                                        <div class="iframe-img">
                                            <img src="images/home/iframe4.png" alt="" />
                                        </div>
                                        <div class="overlay-icon">
                                            <i class="fa fa-play-circle-o"></i>
                                        </div>
                                    </a>
                                    <p>Circle of Hands</p>
                                    <h2>24 DEC 2014</h2>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="address">
                                <img src="images/home/map.png" alt="" />
                                <p>505 S Atlantic Ave Virginia Beach, VA(Virginia)</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="footer-widget">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-2">
                            <div class="single-widget">
                                <h2>Service</h2>
                                <ul class="nav nav-pills nav-stacked">
                                    <li><a href="#">Online Help</a></li>
                                    <li><a href="#">Contact Us</a></li>
                                    <li><a href="#">Order Status</a></li>
                                    <li><a href="#">Change Location</a></li>
                                    <li><a href="#">FAQ’s</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="single-widget">
                                <h2>Quock Shop</h2>
                                <ul class="nav nav-pills nav-stacked">
                                    <li><a href="#">T-Shirt</a></li>
                                    <li><a href="#">Mens</a></li>
                                    <li><a href="#">Womens</a></li>
                                    <li><a href="#">Gift Cards</a></li>
                                    <li><a href="#">Shoes</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="single-widget">
                                <h2>Policies</h2>
                                <ul class="nav nav-pills nav-stacked">
                                    <li><a href="#">Terms of Use</a></li>
                                    <li><a href="#">Privecy Policy</a></li>
                                    <li><a href="#">Refund Policy</a></li>
                                    <li><a href="#">Billing System</a></li>
                                    <li><a href="#">Ticket System</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="single-widget">
                                <h2>About Shopper</h2>
                                <ul class="nav nav-pills nav-stacked">
                                    <li><a href="#">Company Information</a></li>
                                    <li><a href="#">Careers</a></li>
                                    <li><a href="#">Store Location</a></li>
                                    <li><a href="#">Affillate Program</a></li>
                                    <li><a href="#">Copyright</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-sm-3 col-sm-offset-1">
                            <div class="single-widget">
                                <h2>About Shopper</h2>
                                <form action="#" class="searchform">
                                    <input type="text" placeholder="Your email address" />
                                    <button type="submit" class="btn btn-default"><i class="fa fa-arrow-circle-o-right"></i></button>
                                    <p>Get the most recent updates from <br />our site and be updated your self...</p>
                                </form>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

            <div class="footer-bottom">
                <div class="container">
                    <div class="row">
                        <p class="pull-left">Copyright © 2013 E-SHOPPER Inc. All rights reserved.</p>
                        <p class="pull-right">Designed by <span><a target="_blank" href="http://www.themeum.com">Themeum</a></span></p>
                    </div>
                </div>
            </div>

        </footer><!--/Footer-->

        <!-- JS scripts -->
        <script src="js/jquery.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
    </body>
</html>
