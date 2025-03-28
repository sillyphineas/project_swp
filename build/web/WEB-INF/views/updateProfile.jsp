
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
            /* Đặt lại phần mặc định, cơ bản cho toàn trang */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: Arial, sans-serif;
                background: #f9f9f9;
                color: #333;
            }

            /* Container bao quanh nội dung chính */
            /*        .container {
                        max-width: 100%;
                        margin: 50px auto;
                        background: #fff;
                        padding: 30px;
                        border-radius: 8px;
                        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
                    }*/

            h3 {
                margin-bottom: 20px;
                font-size: 28px;
                text-align: center;
                color: #444;
            }

            /* Container for the two tables */
            .table-container {
                display: flex;
                justify-content: space-between; /* Aligns tables with space in between */
                /*                gap: 20px;  Space between the two tables */
                margin-top: 20px; /* Optional, adjust spacing from previous content */
            }

            /* Style for the tables */
            table {
                width: 48%; /* Adjust width so that both tables fit side by side */
                /*                border-collapse: collapse;*/
                /*                background: #fff;*/
                /*                border-radius: 8px;
                                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);*/
                padding: 10px;
            }

            /* Table headers */
            th {
                width: 30%;
                text-align: left;
                /*                background-color: #f0f0f0;*/
            }

            /* Table cells */
            td {
                padding: 12px;
                /*                border-bottom: 1px solid #ddd;*/
                vertical-align: middle;
            }


            /* Chỉnh sửa nút */
            .button-group {
                margin-top: 20px;
                text-align: center;
            }

            .btn {
                display: inline-block;
                margin: 0 10px;
                padding: 12px 20px;
                font-size: 14px;
                text-decoration: none;
                border-radius: 5px;
                color: #fff;
                background: #007bff;
                transition: background 0.3s ease;
            }

            .btn:hover {
                background: #0056b3;
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
                                    <li><a href="${pageContext.request.contextPath}/UserProfileServlet"><i class="fa fa-user"></i> Account</a></li>
                                    <!--                                    <li><a href="#"><i class="fa fa-star"></i> Wishlist</a></li>
                                    
                                                                        <li><a href="checkout.jsp"><i class="fa fa-crosshairs"></i> Checkout</a></li>-->

                                    <li><a href="${pageContext.request.contextPath}/CartURL"><i class="fa fa-shopping-cart"></i> Cart</a></li>
                                    <li><a href="CustomerOrderController"><i class="fa fa-shopping-cart"></i> My Orders</a></li>
                                    <li><a style="font-weight: bold"><img src="UserAvatarController" alt="Profile Image" class="img-thumbnail" style="height: 25px; width: 25px; border-radius: 50%;border: none"/> Hello, <%=user.getEmail()%></a></li>
                                    <li><a href="${pageContext.request.contextPath}/LogoutController"><i class="fa fa-power-off"></i> Logout</a></li>
                                        <% } else { %>
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
        </header>

        <div class="container" >
            <h1 style="text-align: center; font-size: 24px; margin-bottom: 20px; color: #333;">Update Profile</h1>

            <c:if test="${not empty errorMessage}">
                <div style="background: #f8d7da; color: #721c24; padding: 10px; border-radius: 5px; margin-bottom: 15px;">
                    ${errorMessage}
                </div>
            </c:if>
            <!--enctype="multipart/form-data"data-->
            <div class="form-container">
                <form action="UpdateProfileController" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="userId" value="${user.id}" />

                    <!-- Dùng flex để chia form thành 2 cột -->
                    <div style="display: flex; flex-wrap: wrap; gap: 20px;">
                        <!-- Cột trái -->
                        <div style="flex: 1; min-width: 300px;">
                            <div style="margin-bottom: 15px;">
                                <label for="name" style="font-weight: bold;">Full Name:</label>
                                <input type="text" name="name" id="name" class="form-control"
                                       style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 5px;"
                                       value="${user.name}" required />
                            </div>

                            <div style="margin-bottom: 15px;">
                                <label for="email" style="font-weight: bold;">Email:</label>
                                <input type="email" name="email" id="email" class="form-control"
                                       style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 5px;"
                                       value="${user.email}" required />
                            </div>

                            <div style="margin-bottom: 15px;">
                                <label for="phoneNumber" style="font-weight: bold;">Phone Number:</label>
                                <input type="text" name="phoneNumber" id="phoneNumber" class="form-control"
                                       style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 5px;"
                                       value="${user.phoneNumber}" required />
                            </div>
                        </div>

                        <!-- Cột phải -->
                        <div style="flex: 1; min-width: 300px;">
                            <div style="margin-bottom: 15px;">
                                <label for="gender" style="font-weight: bold;">Gender:</label>
                                <select name="gender" id="gender" class="form-control"
                                        style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 5px;">
                                    <option value="true" ${user.gender ? 'selected' : ''}>Male</option>
                                    <option value="false" ${!user.gender ? 'selected' : ''}>Female</option>
                                </select>
                            </div>

                            <div style="margin-bottom: 15px;">
                                <label for="dateOfBirth" style="font-weight: bold;">Date of Birth:</label>
                                <input type="date" name="dateOfBirth" class="form-control"
                                       style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 5px;"
                                       value="<%= (user.getDateOfBirth() != null) ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(user.getDateOfBirth()) : ""%>" 
                                       required />
                            </div>

                            <div style="margin-bottom: 15px;">
                                <label for="image" style="font-weight: bold;">Profile Image:</label>
                                <input type="file" name="image" id="image" class="form-control"
                                       style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 5px;" />
                            </div>
                        </div>
                    </div>

                    <!-- Nút cập nhật và hủy -->
                    <div style="text-align: center; margin-top: 20px;">
                        <button type="submit" class="btn btn-success"
                                style="background-color: orange; padding: 12px 20px; border-radius: 5px; border: none; cursor: pointer;">
                            Update
                        </button>
                        <a href="UserProfileServlet" class="btn btn-secondary"
                           style="background-color: #6c757d; padding: 12px 20px; border-radius: 5px; text-decoration: none; color: white;">
                            Cancel
                        </a>
                    </div>
                </form>
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
    </body>
</html>