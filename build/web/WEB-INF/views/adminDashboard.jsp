<%-- 
    Document   : index
    Created on : Jan 18, 2025, 1:13:24 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.User"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.google.gson.Gson" %>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Home | T-Shopper</title>
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
    </head><!--/head-->
    <style>
        body {
            padding-top: 20px;
        }
        .content {
            margin: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group input {
            margin-right: 10px;
        }
        .stat-list {
            list-style-type: none;
            padding-left: 0;
        }
        .stat-list li {
            margin-bottom: 10px;
        }
        .stat-list h3 {
            margin-top: 20px;
        }
        canvas {
            max-width: 100%;
            margin-top: 30px;
        }
        /* Container cho nội dung */
        .content {
            padding: 30px;
            background-color: #f9f9f9;
            margin: 0 auto;
        }

        /* Thống kê khác sẽ chiếm toàn bộ chiều rộng */
        .statistics-container {
            margin-bottom: 30px;
            width: 100%;
        }

        /* Cập nhật cấu trúc Flexbox để các phần tử con nằm ngang */
        .statistics-item {
            flex: 1;
            margin-right: 15px;
        }

        /* Đảm bảo rằng các phần tử có chiều rộng hợp lý */
        .table-container, .chart-container {
            width: 48%; /* Cả bảng và biểu đồ sẽ chiếm 48% chiều rộng của container */
            display: inline-block; /* Đảm bảo các phần tử sẽ nằm cạnh nhau */
            margin-top: 30px;
        }

        /* Đảm bảo "Other Statistics" nằm trên một hàng riêng */
        /* Cấu hình Flexbox để các phần tử trong 'Other Statistics' nằm trên một hàng ngang */
        .other-statistics {
            display: flex;
            flex-wrap: wrap; /* Cho phép các phần tử xuống dòng nếu không đủ chỗ */
            justify-content: space-between;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            margin-bottom: 30px;
        }

        .other-statistics div {
            flex: 1 1 45%; /* Chiếm 45% chiều rộng của container và co giãn khi cần */
            margin-right: 15px; /* Khoảng cách giữa các phần tử */
        }

        .other-statistics div:last-child {
            margin-right: 0; /* Loại bỏ margin phải của phần tử cuối cùng */
        }

        /* Đảm bảo các tiêu đề và nội dung có khoảng cách phù hợp */
        h3, h4 {
            font-size: 18px;
            color: #333;
            margin-bottom: 10px;
        }


        /* Cập nhật các kiểu cho bảng */
        table {
            width: 100%;
            margin-bottom: 15px;
        }
    </style>
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
                                    <li><a href="AdminDashboardController" class="active">Home</a></li>   
                                    <li><a href="UserController">Users List</a></li>
                                    <li><a href="SettingController">Settings List</a></li>                                   
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
        </header><!--/header-->

        <div class="content" style="padding: 30px; background-color: #f9f9f9;">
            <!-- Phần chọn ngày -->
            <div class="row">
                <div class="col-md-12 text-center" style="margin-bottom: 30px;">
                    <h3 style="font-size: 24px; color: #333; margin-bottom: 20px;">Select Date Range:</h3>
                    <form action="${pageContext.request.contextPath}/AdminDashboardController" method="get" class="form-inline justify-content-center">
                        <div class="form-group">
                            <label for="startDate" style="font-weight: bold; margin-right: 10px;">Start Date:</label>
                            <input type="date" id="startDate" name="startDate" value="<%= request.getAttribute("startDate") != null ? request.getAttribute("startDate") : "" %>" style="padding: 8px; border-radius: 5px; border: 1px solid #ddd; margin-right: 20px;">
                        </div>
                        <div class="form-group">
                            <label for="endDate" style="font-weight: bold; margin-right: 10px;">End Date:</label>
                            <input type="date" id="endDate" name="endDate" value="<%= request.getAttribute("endDate") != null ? request.getAttribute("endDate") : "" %>" style="padding: 8px; border-radius: 5px; border: 1px solid #ddd; margin-right: 20px;">
                        </div>
                        <button type="submit" class="btn btn-warning" style="padding: 10px 20px; background-color: #f39c12; color: white; border: none; border-radius: 5px;">Generate Report</button>
                    </form>
                </div>
            </div>

            <div class="other-statistics">
                <div>
                    <h4>Total Revenue: <%= request.getAttribute("totalRevenue") != null ? request.getAttribute("totalRevenue") : "0" %></h4>
                </div>
                <div>
                    <h4>New Customers: <%= request.getAttribute("newCustomersCount") != null ? request.getAttribute("newCustomersCount") : "0" %></h4>
                </div>
                <div>
                    <h4>New Buyers: <%= request.getAttribute("newBuyersCount") != null ? request.getAttribute("newBuyersCount") : "0" %></h4>
                </div>
                <div>
                    <h4>Average Rating: <%= request.getAttribute("averageRating") != null ? request.getAttribute("averageRating") : "0" %></h4>
                </div>
            </div>


            <div class="statistics-container" style="display: flex; justify-content: space-between; margin-top: 30px;">
                <!-- Bảng Order Status Counts -->
                <div class="table-container" style="width: 48%; box-sizing: border-box; padding-right: 10px;">
                    <h3 style="font-size: 24px; color: #333; margin-bottom: 20px;">Order Status Counts</h3>
                    <table class="table table-bordered" style="width: 100%; margin-bottom: 20px; border-collapse: collapse;">
                        <thead>
                            <tr>
                                <th style="text-align: center; padding: 8px; border: 1px solid #ddd;">Order Status</th>
                                <th style="text-align: center; padding: 8px; border: 1px solid #ddd;">Quantity</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% 
                                Map<String, Integer> orderStatusCounts = (Map<String, Integer>) request.getAttribute("orderStatusCounts");
                            %>
                            <tr>
                                <td style="text-align: center; padding: 8px; border: 1px solid #ddd;">Delivered</td>
                                <td style="text-align: center; padding: 8px; border: 1px solid #ddd;"><%= orderStatusCounts != null ? orderStatusCounts.getOrDefault("Delivered", 0) : 0 %></td>
                            </tr>
                            <tr>
                                <td style="text-align: center; padding: 8px; border: 1px solid #ddd;">Cancelled</td>
                                <td style="text-align: center; padding: 8px; border: 1px solid #ddd;"><%= orderStatusCounts != null ? orderStatusCounts.getOrDefault("Cancel", 0) : 0 %></td>
                            </tr>
                            <tr>
                                <td style="text-align: center; padding: 8px; border: 1px solid #ddd;">Shipping</td>
                                <td style="text-align: center; padding: 8px; border: 1px solid #ddd;"><%= orderStatusCounts != null ? orderStatusCounts.getOrDefault("Shipping", 0) : 0 %></td>
                            </tr>
                            <tr>
                                <td style="text-align: center; padding: 8px; border: 1px solid #ddd;">Pickup</td>
                                <td style="text-align: center; padding: 8px; border: 1px solid #ddd;"><%= orderStatusCounts != null ? orderStatusCounts.getOrDefault("Pickup", 0) : 0 %></td>
                            </tr>
                            <tr>
                                <td style="text-align: center; padding: 8px; border: 1px solid #ddd;">Refund</td>
                                <td style="text-align: center; padding: 8px; border: 1px solid #ddd;"><%= orderStatusCounts != null ? orderStatusCounts.getOrDefault("Refund", 0) : 0 %></td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <!-- Biểu đồ Order Trend -->
                <div class="chart-container" style="width: 48%; box-sizing: border-box; padding-left: 10px;">
                    <h3 style="font-size: 24px; color: #333; margin-bottom: 20px;">Order Trend</h3>
                    <canvas id="orderTrendChart" style="width: 100%; height: 300px;"></canvas>
                    <script>
                        var orderTrendsJson = '<%= request.getAttribute("orderTrendsJson") != null ? request.getAttribute("orderTrendsJson") : "[]" %>';
                        var trends = JSON.parse(orderTrendsJson);

                        if (trends.length > 0) {
                            var labels = [];
                            var data = [];
                            trends.forEach(function (trend) {
                                labels.push(trend.date);
                                data.push(trend.count);
                            });

                            var ctx = document.getElementById('orderTrendChart').getContext('2d');
                            var chart = new Chart(ctx, {
                                type: 'line',
                                data: {
                                    labels: labels,
                                    datasets: [{
                                            label: 'Successful Orders Trend',
                                            data: data,
                                            borderColor: 'rgba(75, 192, 192, 1)',
                                            fill: false
                                        }]
                                },
                                options: {
                                    responsive: true,
                                    scales: {
                                        x: {
                                            title: {
                                                display: true,
                                                text: 'Date'
                                            }
                                        },
                                        y: {
                                            title: {
                                                display: true,
                                                text: 'Number of Orders'
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    </script>
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
    </body>
</html>