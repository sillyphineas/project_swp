<%@ page import="java.util.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="entity.User"%>
<html>
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

            <!-- Direct CSS for Style Enhancements -->
            <style>
                /* Global Styles */
                body {
                    font-family: Arial, sans-serif;
                    background-color: #f4f4f4;
                    margin: 0;
                    padding: 0;
                }

                /* Main Container */


                /* Heading Styles */
                h2 {
                    text-align: center;
                    color: #333;
                    margin-bottom: 30px;
                }

                h3 {
                    text-align: center;
                    color: #333;
                    margin-bottom: 10px;
                }

                /* Message Box */
                .message {
                    color: green;
                    font-weight: bold;
                    text-align: center;
                    margin-bottom: 20px;
                }

                /* Form Styling */
                .form-group {
                    margin-bottom: 20px;
                }

                label {
                    font-weight: bold;
                    margin-bottom: 8px;
                    display: block;
                    color: #333;
                }

                input.form-control, select.form-control {
                    width: 100%;
                    padding: 12px;
                    border: 1px solid #ccc;
                    border-radius: 4px;
                    box-sizing: border-box;
                    font-size: 16px;
                    color: #333;
                }

                input.form-control:focus, select.form-control:focus {
                    border-color: #f39c12;
                    outline: none;
                }

                /* Edit Link Styling */
                a {
                    display: inline-block;

                    color: white;
                    padding: 10px 20px;
                    text-decoration: none;
                    border-radius: 4px;
                    margin-top: 20px;
                    transition: background-color 0.3s;
                }

                a:hover {
                    background-color: #e67e22;
                }

                /* Table Styling */
                table {
                    width: 100%;
                    margin-top: 30px;
                    border-collapse: collapse;
                }

                table th, table td {
                    padding: 12px;
                    text-align: left;
                    border: 1px solid #ddd;
                    background-color: #f9f9f9;
                }

/*                table th {
                    background-color: #f1c40f;
                    color: white;
                    font-weight: bold;
                }*/

                table tr:nth-child(even) {
                    background-color: #f2f2f2;
                }

                table tr:hover {
                    background-color: #f0e68c;
                }

                /* Responsive Design */
                @media screen and (max-width: 768px) {
                    .container {
                        padding: 15px;
                    }

                    h2 {
                        font-size: 18px;
                    }

                    .form-group {
                        margin-bottom: 15px;
                    }

                    input.form-control, select.form-control {
                        padding: 10px;
                        font-size: 14px;
                    }

                    table th, table td {
                        font-size: 14px;
                        padding: 10px;
                    }
                }

                /* CSS cho phần Customer Detail */


                /* Tiêu đề Customer Detail */
                h2 {
                    text-align: center;
                    color: #333;
                    margin-bottom: 30px;
                    font-size: 28px;
                    font-weight: bold;
                }

                /* Phần thông báo */
                .message {
                    color: green;
                    font-weight: bold;
                    text-align: center;
                    margin-bottom: 20px;
                    font-size: 16px;
                }

                /* Các nhóm form */
                .form-group {
                    margin-bottom: 20px;
                }

                /* Định dạng nhãn */
                label {
                    font-weight: bold;
                    color: #333;
                    font-size: 16px;
                    margin-bottom: 8px;
                    display: block;
                }

                /* Phần hiển thị thông tin */
                p {
                    background-color: #f9f9f9;
                    border: 1px solid #ddd;
                    padding: 12px;
                    border-radius: 4px;
                    font-size: 16px;
                    color: #333;
                    margin: 0;
                }

                /* Định dạng liên kết "Edit" */
                .edit-link {
                    display: inline-block;
                    padding: 12px 20px;
                    background-color: #f39c12; /* Màu nền */
                    color: #fff; /* Màu chữ */
                    text-decoration: none;
                    font-size: 16px;
                    border-radius: 4px;
                    text-align: center;
                    transition: background-color 0.3s;
                }

                .edit-link:hover {
                    background-color: #e67e22; /* Màu nền khi hover */
                }

                /* Table Styling */
                table {
                    width: 100%;
                    margin-top: 30px;
                    border-collapse: collapse;
                }

                table th, table td {
                    padding: 12px;
                    text-align: left;
                    border: 1px solid #ddd;
                    background-color: #f9f9f9;
                }

/*                table th {
                    background-color: #f1c40f;
                    color: white;
                    font-weight: bold;
                }*/

                table tr:nth-child(even) {
                    background-color: #f2f2f2;
                }

                table tr:hover {
                    background-color: #f0e68c;
                }

                /* Responsive Design */
                @media screen and (max-width: 768px) {
                    .container {
                        padding: 15px;
                    }

                    h2 {
                        font-size: 22px;
                    }

                    label {
                        font-size: 14px;
                    }

                    p {
                        font-size: 14px;
                    }

                    table th, table td {
                        font-size: 14px;
                        padding: 10px;
                    }
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
<!--                                        <li><a href="#"><i class="fa fa-user"></i> Account</a></li>
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
                                        <li><a href="MarketingDashboardController">Home</a></li>
                                        <li><a href="MarketingPostController?service=listAllBlogs">Post List</a></li>
                                        <li><a href="SliderController">Slider List</a></li>
                                        <li><a href="CustomerController" class="active">Customer List</a></li>
                                        <li><a href="MarketingProductController">Product List</a></li>
                                    </ul>
                                </div>
                            </div>

                        </div>
                    </div>
                </div><!--/header-bottom-->
            </header><!--/header-->


            <div class="container">
                <h2>Customer Detail</h2>
                <c:if test="${not empty mess}">
                    <div style="color: green; font-weight: bold;">${mess}</div>
                </c:if>

                <input type="hidden" name="id" value="${customer.id}"/>

                <div class="form-group">
                    <label for="name">Name:</label>
                    <p>${customer.name}</p>
                </div>

                <div class="form-group">
                    <label for="gender">Gender:</label>
                    <p>${customer.gender == true ? 'Male' : 'Female'}</p>
                </div>

                <div class="form-group">
                    <label for="email">Email:</label>
                    <p>${customer.email}</p>
                </div>

                <div class="form-group">
                    <label for="phoneNumber">Phone Number:</label>
                    <p>${customer.phoneNumber}</p>
                </div>

                <div class="form-group">
                    <label for="status">Status:</label>
                    <p>${customer.isDisabled == true ? 'Inactive' : 'Active'}</p>
                </div>
            </div>


            <a href="EditCustomerController?id=${customer.id}" class="edit-link">Edit</a>



            <h3>Change History</h3>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Email</th>
                        <th>Name</th>
                        <th>Gender</th>
                        <th>Phone Number</th>
                        <th>Updated By</th>
                        <th>Updated At</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="history" items="${changeHistory}">
                        <tr>
                            <td>${history['email']}</td>
                            <td>${history['name']}</td>
                            <td>${history['gender'] ? 'Male' : 'Female'}</td>
                            <td>${history['phoneNumber']}</td>
                            <td>${history['updatedByName']}</td>
                            <td>${history['updatedAt']}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>


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
