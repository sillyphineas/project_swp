<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="entity.Product, entity.ProductVariant, java.util.Vector, entity.Brand" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="entity.User"%>

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
            .inactive-status {
                color: red; /* Màu đỏ cho trạng thái Inactive */
                font-weight: bold;
            }

            .active-status {
                color: green; /* Màu xanh cho trạng thái Active */
                font-weight: bold;
            }
            .add{
                text-align: center;
                border: 2px;
                color: blue;
            }
            /* Tùy chỉnh cho nút "Add Product" màu xanh */
            .btn-custom-blue {
                background-color: #4CAF50;  /* Màu xanh lá cây */
                color: white;
                font-weight: bold;
                border-radius: 5px;
                padding: 10px 20px;
                font-size: 16px;
                text-transform: uppercase;
                border: none;
                transition: background-color 0.3s ease;
            }

            .btn-custom-blue:hover {
                background-color: #45a049;  /* Màu xanh đậm hơn khi hover */
                cursor: pointer;
            }

            /* Tùy chỉnh cho nút "Back to List" màu đỏ */
            .btn-custom-red {
                background-color: #f44336;  /* Màu đỏ */
                color: white;
                font-weight: bold;
                border-radius: 5px;
                padding: 10px 20px;
                font-size: 16px;
                text-transform: uppercase;
                border: none;
                transition: background-color 0.3s ease;
            }

            .btn-custom-red:hover {
                background-color: #d32f2f;  /* Màu đỏ đậm khi hover */
                cursor: pointer;
            }
            .form-control {
                width: 85%;  /* Giảm chiều rộng xuống còn 80% so với kích thước mặc định */
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
                                <a href="index.html"><img src="images/home/logo.png" alt="" /></a>
                            </div>
                            <div class="btn-group pull-right">
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle usa" data-toggle="dropdown">
                                        USA
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a href="#">Canada</a></li>
                                        <li><a href="#">UK</a></li>
                                    </ul>
                                </div>

                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle usa" data-toggle="dropdown">
                                        DOLLAR
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a href="#">Canadian Dollar</a></li>
                                        <li><a href="#">Pound</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-8">
                            <div class="shop-menu pull-right">
                                <ul class="nav navbar-nav">
                                    <!--                                    <li><a href="#"><i class="fa fa-user"></i> Account</a></li>
                                                                        <li><a href="${pageContext.request.contextPath}/CartController"><i class="fa fa-shopping-cart"></i> Cart</a></li>-->
                                    <% 
                                        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
                                        User user = (User) session.getAttribute("user");
                                        if (isLoggedIn != null && isLoggedIn) {
                                    %>
                                    <li><a style="font-weight: bold"><i class="fa fa-hand-o-up"></i> Hello, <%=user.getEmail()%></a></li>
                                    <li><a href="${pageContext.request.contextPath}/LogoutController"><i class="fa fa-power-off"></i> Logout</a></li>
                                        <% } else { %>
                                    <li><a href="${pageContext.request.contextPath}/LoginController"><i class="fa fa-lock"></i> Login</a></li>
                                        <% } %>
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
                                   <li><a href="MarketingDashboardController" class="active">Home</a></li>
                                <li><a href="MarketingPostController?service=listAllBlogs">Post List</a></li>
                                <li><a href="SliderController">Slider List</a></li>
                                <li><a href="CustomerController">Customer List</a></li>
                                <li><a href="MarketingProductController">Product List</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="search_box pull-right">
                                <input type="text" placeholder="Search"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!--/header-bottom-->
        </header>


        <br>
        <div class="header-middle"><!--header-middle-->
            <div class="container">
                <div class="row">
                    <div class="col-sm-4">
                        <div class="logo pull-left">
                            <a href="index.html"><img src="images/home/logo.png" alt="" /></a>
                        </div>
                    </div>
                </div>
            </div>
        </div><!--/header-middle-->
    </header>

    <div class="container">
        <h2 class="add">Add Product</h2>

        <form action="AddProductController?action=addProduct" method="post">

            <!-- Row 1: Product Name, Description -->
            <div class="form-row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="name">Product Name:</label>
                        <input type="text" class="form-control" id="name" name="name" required>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label for="description">Description:</label>
                        <textarea class="form-control" id="description" name="description" required></textarea>
                    </div>
                </div>
            </div>

            <!-- Row 2: Brand, Chipset -->
            <div class="form-row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="brandID">Brand:</label>
                        <select class="form-control" id="brandID" name="brandID">
                            <c:forEach var="brand" items="${brands}">
                                <option value="${brand.id}">${brand.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label for="chipset">Chipset:</label>
                        <input type="text" class="form-control" id="chipset" name="chipset">
                    </div>
                </div>
            </div>

            <!-- Row 3: RAM, Screen Size -->
            <div class="form-row">

                <div class="col-md-6">
                    <div class="form-group">
                        <label for="os">Operating System:</label>
                        <input type="text" class="form-control" id="os" name="os">
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label for="screenSize">Screen Size (inches):</label>
                        <input type="number" class="form-control" id="screenSize" name="screenSize" step="any">
                    </div>
                </div>
            </div>

            <!-- Row 4: Screen Type, Camera Specs -->
            <div class="form-row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="screenType">Screen Type:</label>
                        <input type="text" class="form-control" id="screenType" name="screenType">
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label for="cameraSpecs">Camera Specs:</label>
                        <input type="text" class="form-control" id="cameraSpecs" name="cameraSpecs">
                    </div>
                </div>
            </div>

            <!-- Row 5: Feedback Count, Sim Type -->
            <div class="form-row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="simType">Sim Type:</label>
                        <input type="text" class="form-control" id="simType" name="simType">
                    </div>
                </div>
            </div>

            <!-- Row 6: Resolution, Battery Capacity -->
            <div class="form-row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="resolution">Resolution:</label>
                        <input type="text" class="form-control" id="resolution" name="resolution">
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label for="batteryCapacity">Battery Capacity (mAh):</label>
                        <input type="number" class="form-control" id="batteryCapacity" name="batteryCapacity" min="0">
                    </div>
                </div>
            </div>

            <!-- Row 7: OS, Connectivity -->
            <div class="form-row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label>Status:</label>
                        <select class="form-control" name="status">
                            <option value="Available">Available</option>
                            <option value="Out of Stock">Out of Stock</option>
                        </select>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label for="connectivity">Connectivity:</label>
                        <input type="text" class="form-control" id="connectivity" name="connectivity">
                    </div>
                </div>
            </div>

            <!-- Row 8: Creation Date, Image URL -->
            <div class="form-row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="createAt">Creation Date:</label>
                        <input type="date" class="form-control" id="createAt" name="createAt"
                                max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" />
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label for="imageURL">Image URL:</label>
                        <input type="text" class="form-control" id="imageURL" name="imageURL">
                    </div>
                </div>
            </div>

            <!-- Row 9: Status, Disable Product -->
             <div class="col-md-6">
                    <div class="form-group">
                        <label>Disable Product:</label>
                        <input type="checkbox" name="isDisabled">
                    </div>
                </div>
            <div class="form-row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="feedbackCount"></label>
                        <input type="hidden" class="form-control" id="feedbackCount" name="feedbackCount">
                    </div>
                </div>
            <div class="form-row">
                <div class="col-md-6">
                    <div class="form-group">

                        <input type="hidden" class="form-control" id="ram" name="ram" min="0">
                    </div>
                </div>

            </div>

            <button type="submit" class="btn btn-custom-blue">Add Product</button>

            <!-- Nút "Back to List" màu đỏ -->
            <a href="MarketingProductController" class="btn btn-custom-red">Cancel</a>
        </form>
    </div>

    <br>
    <br>


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