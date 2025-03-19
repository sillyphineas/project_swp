<%-- 
    Document   : index
    Created on : Jan 18, 2025, 1:13:24 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.User"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
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
    </head><!--/head-->
    <style>
        .report-form {
            margin: 20px 0;
            display: flex;
            justify-content: flex-start;
            align-items: center;
            gap: 10px; /* Reduced gap between elements */
            flex-wrap: wrap; /* Allow wrapping if needed */
        }

        .report-form label {
            margin: 0 5px;
            font-weight: bold;
            font-size: 14px; /* Smaller font size for labels */
        }

        .report-form input,
        .report-form select {
            margin: 0 5px;
            font-size: 14px; /* Smaller font size for inputs and selects */
            width: 150px; /* Adjusted width for inputs and selects */
        }

        .report-form button {
            margin-left: 10px;
            padding: 5px 15px; /* Smaller padding for a compact button */
            background-color: #ff9f00;
            color: white;
            border: none;
            cursor: pointer;
            font-size: 14px; /* Smaller font size for button */
        }

        .report-form button:hover {
            background-color: #ff7f00;
        }

        .chart-container {
            width: 100%; /* Full width of the container */
            max-width: 1200px; /* Max width limit */
            text-align: center;
            padding: 20px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        #shippingTrendChart, #totalShipmentsChart {
            max-width: 100%;
            height: 300px; /* Increased chart height */
            border: 1px solid #ddd;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            background-color: #fff;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 30px;
        }

        .row1 {
            display: flex;
            justify-content: center; /* Center the chart containers */
            margin-bottom: 30px; /* Space between rows */
        }

        .chart-title {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 15px;
        }

        canvas {
            width: 100% !important; /* Ensure canvas uses full container width */
            height: auto !important; /* Maintain aspect ratio */
        }
        .total-stats {
            margin: 20px 0;
            font-size: 16px;
            display: flex;
            justify-content: space-between;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .total-stats p {
            margin: 0;
            display: flex;
            flex-direction: column;
        }

        .total-stats p strong {
            font-weight: 600;
            color: #333;
        }

        .total-stats p span {
            color: #4e73df; /* Change color as per your requirement */
            font-weight: 700;
            font-size: 18px;
        }

        .total-stats p span#totalShipping {
            color: #36b9cc; /* Shipping color */
        }

        .total-stats p span#totalDelivered {
            color: #f6c23e; /* Delivered color */
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
                                    <li><a href="ShipperDashboardController" class="active">Home</a></li>
                                    <li><a href="ShipperOrderController">Order List</a></li>
                                </ul>
                            </div>
                        </div>

                    </div>
                </div>
            </div><!--/header-bottom-->
        </header><!--/header-->
        <%
                List<Map<String, Object>> shippingStats = (List<Map<String, Object>>) request.getAttribute("shippingStats");
                String shippingStatsJson = "[]";
                if (shippingStats != null) {
                    shippingStatsJson = new Gson().toJson(shippingStats);
                }
        %>

        <div class="container">
            <div class="report-form">
                <form action="ShipperDashboardController" method="get">
                    <label for="startDate">Start Date:</label>
                    <input type="date" id="startDate" name="startDate"
                           value="<%= request.getAttribute("startDate") %>">

                    <label for="endDate">End Date:</label>
                    <input type="date" id="endDate" name="endDate"
                           value="<%= request.getAttribute("endDate") %>">

                    <label for="shippingStatus">Shipping Status:</label>
                    <select id="shippingStatus" name="shippingStatus">
                        <option value="" <%= "".equals(request.getAttribute("shippingStatus")) ? "selected" : "" %>>All</option>
                        <option value="shipping" <%= "shipping".equals(request.getAttribute("shippingStatus")) ? "selected" : "" %>>Shipping</option>
                        <option value="delivered" <%= "delivered".equals(request.getAttribute("shippingStatus")) ? "selected" : "" %>>Delivered</option>
                    </select>

                    <button type="submit" class="btn btn-warning" style="margin-left: 10px;">Generate Report</button>
                    <div class="total-stats" style="margin: 20px 0; font-size: 16px;">
                        <p><strong>Total Shipping:</strong> <span id="totalShipping">0</span></p>
                        <p><strong>Total Delivered:</strong> <span id="totalDelivered">0</span></p>
                    </div>
                </form>
            </div>

            <div class="row1">
                <div class="col-md-12">
                    <div class="chart-title">Shipping Status Trend</div>
                    <canvas id="shippingTrendChart" width="700" height="250"></canvas>
                </div>
            </div>

            <div class="row1">
                <div class="col-md-12">
                    <div class="chart-title">Total Shipments Trend</div>
                    <canvas id="totalShipmentsChart" width="700" height="250"></canvas>
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
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script type="text/javascript">
         var shippingStats = <%= shippingStatsJson %>;
         var labels = shippingStats.map(stat => stat.date);
         var totalShipmentsData = shippingStats.map(stat => stat.totalShipments);
         var shippingData = shippingStats.map(stat => stat.shippingShipments);
         var deliveredData = shippingStats.map(stat => stat.deliveredShipments);

         // Log the data to debug
         console.log("Shipping Stats:", shippingStats);
         console.log("Labels:", labels);
         console.log("Total Shipments:", totalShipmentsData);
         console.log("Shipping Data:", shippingData);
         console.log("Delivered Data:", deliveredData);

         // Tính tổng Shipping và Delivered
         var totalShipping = shippingData.reduce((sum, value) => sum + value, 0);
         var totalDelivered = deliveredData.reduce((sum, value) => sum + value, 0);

         // Hiển thị tổng lên giao diện
         document.getElementById('totalShipping').textContent = totalShipping;
         document.getElementById('totalDelivered').textContent = totalDelivered;

         // Phần còn lại của mã (vẽ biểu đồ) giữ nguyên
         var ctxShipping = document.getElementById('shippingTrendChart').getContext('2d');
         var shippingTrendChart = new Chart(ctxShipping, {
             type: 'bar',
             data: {
                 labels: labels,
                 datasets: [
                     {
                         label: 'Shipping',
                         data: shippingData,
                         backgroundColor: 'rgba(54, 162, 235, 0.5)',
                         borderColor: 'rgb(54, 162, 235)',
                         borderWidth: 1
                     },
                     {
                         label: 'Delivered',
                         data: deliveredData,
                         backgroundColor: 'rgba(75, 192, 192, 0.5)',
                         borderColor: 'rgb(75, 192, 192)',
                         borderWidth: 1
                     }
                 ]
             },
             options: {
                 scales: {
                     y: {
                         beginAtZero: true,
                         ticks: {
                             stepSize: 1
                         }
                     }
                 }
             }
         });

         var ctxTotal = document.getElementById('totalShipmentsChart').getContext('2d');
         var totalShipmentsChart = new Chart(ctxTotal, {
             type: 'line',
             data: {
                 labels: labels,
                 datasets: [{
                         label: 'Total Shipments',
                         data: totalShipmentsData,
                         borderColor: 'rgb(255, 99, 132)',
                         tension: 0.2
                     }]
             },
             options: {
                 scales: {
                     y: {
                         beginAtZero: true,
                         ticks: {
                             stepSize: 1
                         }
                     }
                 }
             }
         });
    </script>
</body>
</html>