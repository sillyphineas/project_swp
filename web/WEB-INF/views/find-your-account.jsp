<%-- 
    Document   : index
    Created on : Jan 18, 2025, 1:13:24 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Home | E-Shopper</title>
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

            /* Chỉ áp dụng cho vùng có class .find-account-container */
            .find-account-container {
                max-width: 400px;         /* Độ rộng tối đa */
                margin: 60px auto;        /* Canh giữa trang, tạo khoảng trống phía trên/dưới */
                padding: 20px;            /* Khoảng trống bên trong */
                background: #fff;         /* Nền trắng */
                border: 1px solid #ddd;   /* Viền nhạt */
                border-radius: 6px;       /* Bo góc nhẹ */
            }

            /* Tiêu đề (Find Your Account) */
            .find-account-container h2 {
                margin: 0 0 10px;
                font-size: 20px;
                font-weight: normal;      /* Hoặc bold tuỳ ý */
            }

            /* Đoạn mô tả (Please enter your email...) */
            .find-account-container p {
                margin: 0 0 20px;
                font-size: 14px;
                color: #555;
            }

            /* Nhóm form-group (label + input) */
            .find-account-container .form-group {
                margin-bottom: 15px;
            }

            /* Label cho input */
            .find-account-container label {
                display: block;
                margin-bottom: 5px;
                font-size: 14px;
                font-weight: 600;
            }

            /* Ô nhập Email/Phone */
            .find-account-container input[type="text"] {
                width: 100%;
                padding: 10px;
                font-size: 14px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
            }

            /* Nhóm nút Cancel / Search */
            .find-account-container .btn-group {
                display: flex;
                justify-content: flex-end; /* Căn nút về bên phải */
                gap: 10px;                 /* Khoảng cách giữa hai nút */
            }

            /* Định dạng nút */
            .find-account-container .btn-cancel,
            .find-account-container .btn-search {
                padding: 8px 14px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 14px;
            }

            /* Nút Cancel (xám nhạt) */
            .find-account-container .btn-cancel {
                background-color: #e4e6eb;
                color: #050505;
            }
            .find-account-container .btn-cancel:hover {
                background-color: #d8dadd;
            }

            /* Nút Search (xanh Facebook) */
            .find-account-container .btn-search {
                background-color: #1877f2;
                color: #fff;
            }
            .find-account-container .btn-search:hover {
                background-color: #165dd2;
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
                                    <li><a href="ProductController">Shop</a>
                                        <!--                                        <ul role="menu" class="sub-menu">
                                                                                    <li><a href="ProductController">Products</a></li>
                                                                                    <li><a href="CartURL?service=checkOut">Checkout</a></li> 
                                                                                    <li><a href="CartURL?service=showCart">Cart</a></li> 
                                                                                </ul>-->
                                    </li> 
                                    <li><a href="BlogURL?service=listAllBlogs">Blog</a></li>
                                    <li><a href="#about-us">About Us</a></li>
                                    <li><a href="ContactForward">Contact Us</a></li>
                                    <!--                                    <li><a href="404.html">404</a></li>
                                                                        <li><a href="contact-us.html">Contact</a></li>-->
                                </ul>
                            </div>
                        </div>
                        <!--                        <div class="col-sm-3">
                                                    <div class="pull-right">
                                                        <form action="${pageContext.request.contextPath}/ProductController" method="get">
                                                            <input type="text" name="search" value="${param.search}" />
                        
                                                            <button type="submit" class="btn btn-default"><i class="fa fa-search"></i></button>
                                                        </form>
                                                    </div>
                                                </div>-->
                    </div>
                </div>
            </div><!--/header-bottom-->
        </header><!--/header-->

        <section>
            <div class="container">
                <!-- Thêm lớp .find-account-container để scope CSS -->
                <div class="find-account-container">
                    <h2>Find Your Account</h2>
                    <p>Please enter your email address to search for your account.</p>
                    <form action="ResetController" method="POST">
                        <input type="hidden" name="service" value="findYourAccount"/>
                        <div class="form-group">
                            <label for="email">Enter your email address</label>
                            <input type="text" id="email" name="email" placeholder="Email address" required>
                        </div>
                        <c:if test="${not empty error}">
                            <div style="color: red; margin-bottom: 10px;">
                                <c:out value="${error}" />
                            </div>
                        </c:if>
                        <c:if test="${not empty success}">
                            <div style="color: green; margin-bottom: 10px;">
                                <c:out value="${success}" />
                            </div>
                        </c:if>

                        <div class="btn-group">
                            <a href="HomePageController" class="btn-cancel">Cancel</a>
                            <button type="submit" class="btn-search">Reset</button>
                        </div>
                    </form>
                </div>
            </div>
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



        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
    </body>
</html>