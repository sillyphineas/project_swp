
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="entity.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>User Profile</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <link href="css/prettyPhoto.css" rel="stylesheet">
        <link href="css/price-range.css" rel="stylesheet">
        <link href="css/animate.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        <link href="css/responsive.css" rel="stylesheet">
        <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->       
        <link rel="shortcut icon" href="images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">

        <style>
            .profile-container {
                display: flex;
                justify-content: space-between;
                margin-top: 40px;
            }

            .profile-left {
                text-align: center;
                width: 30%;
                background-color: #f9f9f9;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            .profile-right {
                width: 65%;
                background-color: #f9f9f9;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            .profile-left h3 {
                font-size: 24px;
                color: #333;
            }

            .profile-left p {
                font-size: 16px;
                color: #777;
            }

            table {
                width: 100%;
                margin-bottom: 20px;
                border-collapse: collapse;
            }

            th {
                text-align: left;
                font-weight: bold;
                color: #555;
            }

            td {
                padding: 12px;
                color: #777;
            }

            .button-group a {
                display: inline-block;
                padding: 10px 20px;
                font-size: 14px;
                text-decoration: none;
                border-radius: 5px;
                transition: background 0.3s ease;
            }

            .btn {
                background-color: #007bff;
                color: #fff;
            }

            .btn:hover {
                background-color: #0056b3;
            }

            .btn-back {
                background-color: #6c757d;
            }

            .btn-back:hover {
                background-color: #495057;
            }
        </style>

    </head><!--/head-->

    <body>
        <header id="header"><!--header-->
            <div class="header_top"><!--header_top-->
                <div class="container">
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="contactinfo">
                                <ul class="nav nav-pills">
                                    <li><a href="#"><i class="fa fa-phone"></i> +2 95 01 88 821</a></li>
                                    <li><a href="#"><i class="fa fa-envelope"></i> info@domain.com</a></li>
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
                                <a href="HomePageController"><img src="images/home/logo.png" alt="" /></a>
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
                                    <li><a href="${pageContext.request.contextPath}/UserProfileServlet"><i class="fa fa-user"></i> Account</a></li>
                                    <!--                                    <li><a href="#"><i class="fa fa-star"></i> Wishlist</a></li>
                                    
                                                                        <li><a href="checkout.jsp"><i class="fa fa-crosshairs"></i> Checkout</a></li>-->

                                    <li><a href="${pageContext.request.contextPath}/CartURL"><i class="fa fa-shopping-cart"></i> Cart</a></li>    
                                    <li><a href="CustomerOrderController"><i class="fa fa-shopping-cart"></i> My Orders</a></li>
                                    <li><a style="font-weight: bold"><img src="UserAvatarController" alt="Profile Image" class="img-thumbnail" style="height: 25px; width: 25px; border-radius: 50%;border: none"/> Hello, <%=user.getEmail()%></a></li>
                                    <li><a href="${pageContext.request.contextPath}/LogoutController"><i class="fa fa-power-off"></i> Logout</a></li>
                                        <% } else { %>
                                    <li><a href="${pageContext.request.contextPath}/LoginController"><i class="fa fa-lock"></i> Login</a></li>
                                        <% } %>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </header><!--/header-->

        <div class="container">
            <h1>User Profile</h1>
            <div class="profile-container">
                <!-- Profile Image and Name -->
                <div class="profile-left">
                    <div class="profile-image" style="text-align: center; margin-bottom: 20px;">
                        <img src="UserAvatarController" alt="Profile Image" class="img-thumbnail" style="border-radius: 50%; border: none" />
                    </div>
                    <h3 style="text-align: center; font-size: 24px; color: #333;">${user.name}</h3>

                </div>

                <!-- Profile Details and Buttons -->
                <div class="profile-right" style="padding-left: 30px;">
                    <table style="width: 100%; margin-bottom: 20px;">
                        <tr>
                            <th>Email:</th>
                            <td>${user.email}</td>
                        </tr>
                        <tr>
                            <th>Phone:</th>
                            <td>${user.phoneNumber}</td>
                        </tr>
                        <tr>
                            <th>Gender:</th>
                            <td>${user.gender ? 'Male' : 'Female'}</td>
                        </tr>
                        <tr>
                            <th>Date of Birth:</th>
                            <td>${user.dateOfBirth}</td>
                        </tr>
                    </table>

                    <!-- Buttons -->
                    <div class="button-group" style="text-align: center;">
                        <a class="btn" id="openPasswordFormBtn" style="margin: 10px;">
                            Update Profile
                        </a>

                        <!-- Overlay background, tương tự như khi add Address ở trang checkout -->
                        <div id="overlay" style="display:none;
                             position: fixed;
                             top: 0; left: 0;
                             width: 100%; height: 100%;
                             background-color: rgba(0, 0, 0, 0.6);
                             z-index: 999;">
                        </div>

                        <!-- Form để nhập lại mật khẩu, có thể thiết kế giống với form "Add Address" -->
                        <div id="passwordForm" style="display:none;
                             position: fixed;
                             top: 20%; left: 50%;
                             transform: translateX(-50%);
                             background: #fff;
                             padding: 20px;
                             border-radius: 8px;
                             width: 90%;
                             max-width: 400px;
                             z-index: 1000;">

                            <!-- Nút đóng form -->
                            <span id="closeForm" 
                                  style="cursor: pointer; position: absolute; top: 10px; right: 10px; font-size: 24px; color: #999;">
                                &times;
                            </span>                            
                            <h3 style="text-align: center; color: #333;">Please enter password</h3>
                            <div id="errorDiv" style="display:none; color: red; margin-bottom: 10px;"></div>
                            <form id="checkPasswordForm" action="PasswordCheckController" method="post">

                                <input type="hidden" name="userId" value="${user.id}" />


                                <label for="currentPassword">Current password:</label>
                                <input type="password" 
                                       id="currentPassword" 
                                       name="currentPassword" 
                                       required 
                                       style="width: 100%; padding: 10px; margin-bottom: 10px;" />

                                <!-- Submit form -->
                                <button type="submit" 
                                        style="width: 100%; background-color: #4CAF50; color: #fff; padding: 12px; border: none;
                                        border-radius: 5px; cursor: pointer; font-size: 16px;">
                                    Confirm
                                </button>
                            </form>
                        </div>
                        <c:if test="${not empty errorMsg}">
                            <script>
                                document.addEventListener('DOMContentLoaded', function () {
                                    openForm();  // Gọi hàm mở form
                                    // Nhét errorMsg vào errorDiv
                                    var errorDiv = document.getElementById("errorDiv");
                                    errorDiv.textContent = "${errorMsg}";
                                    errorDiv.style.display = "block";
                                });
                            </script>
                        </c:if>
                        <a class="btn" href="${pageContext.request.contextPath}/ChangePasswordController?id=${user.id}" style="margin: 10px;">Change Password</a>                       
                        <a class="btn btn-back" href="HomePageController" style="margin: 10px; background-color: #6c757d; color: white;">Back to Home</a>
                    </div>
                </div>
            </div>
        </div>





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



        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
        <script>
                                var openPasswordFormBtn = document.getElementById("openPasswordFormBtn");
                                var overlay = document.getElementById("overlay");
                                var passwordForm = document.getElementById("passwordForm");
                                var closeFormBtn = document.getElementById("closeForm");
                                var errorDiv = document.getElementById("errorDiv"); // div hiển thị lỗi

                                function openForm() {
                                    overlay.style.display = "block";
                                    passwordForm.style.display = "block";
                                }

                                function closeForm() {
                                    overlay.style.display = "none";
                                    passwordForm.style.display = "none";

                                    // --- Xoá nội dung và ẩn errorDiv ---
                                    errorDiv.textContent = "";
                                    errorDiv.style.display = "none";
                                }

                                openPasswordFormBtn.addEventListener("click", openForm);

                                closeFormBtn.addEventListener("click", closeForm);

                                overlay.addEventListener("click", function (e) {
                                    if (e.target === overlay) {
                                        closeForm();
                                    }
                                });
        </script>

    </body>
</html>