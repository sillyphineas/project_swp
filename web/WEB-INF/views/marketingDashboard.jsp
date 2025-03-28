<%-- 
    Document   : index
    Created on : Jan 18, 2025, 1:13:24 PM
    Author     : HP
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="entity.User"%>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="java.util.Map" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Home | E-Shopper</title>

        <!-- Bootstrap CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <link href="css/prettyPhoto.css" rel="stylesheet">
        <link href="css/price-range.css" rel="stylesheet">
        <link href="css/animate.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        <link href="css/responsive.css" rel="stylesheet">

        <!-- Chart.js -->
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

        <!-- CSS tùy chỉnh -->
        <style>
            .report-form {
                margin: 20px 0;
                text-align: center;
            }
            .report-form label {
                margin: 0 10px;
                font-weight: bold;
            }
            /* Mỗi biểu đồ nằm trong cột bootstrap, có thể cho chiều cao/căn giữa tùy ý */
            .chart-container {
                margin-bottom: 30px;
                text-align: center;
            }
            /* Giới hạn kích cỡ canvas, bỏ !important để cho phép co giãn */
            canvas {
                max-width: 100%;
                height: auto;
            }
            .chart-title {
                font-size: 18px;
                margin-bottom: 10px;
                font-weight: bold;
            }
        </style>
    </head>

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
                                    <% 
                                        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
                                        User user = (User) session.getAttribute("user");
                                        if (isLoggedIn != null && isLoggedIn) {
                                    %>
                                    <li>
                                        <a style="font-weight: bold">
                                            <i class="fa fa-hand-o-up"></i> Hello, <%=user.getEmail()%>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/LogoutController">
                                            <i class="fa fa-power-off"></i> Logout
                                        </a>
                                    </li>
                                    <% } else { %>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/LoginController">
                                            <i class="fa fa-lock"></i> Login
                                        </a>
                                    </li>
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
                                    <li><a href="MaketingFeedBackController?service=listAllfeedBack">FeedBack List</a></li>

                                </ul>
                            </div>
                        </div>                        
                    </div>
                </div>
            </div><!--/header-bottom-->
        </header><!--/header-->

        <%
            // Lấy "statistics" do Servlet setAttribute
            Map<String, Object> statisticsMap = (Map<String, Object>) request.getAttribute("statistics");
        %>

        <!-- Form xuất báo cáo -->
        <div class="container">
            <div class="report-form">
                <h1>Marketing Dashboard</h1>
                <form action="MarketingDashboardController" method="get">
                    <label for="startDate">Start Date:</label>
                    <input type="date" id="startDate" name="startDate"
                           value="<%= request.getAttribute("startDate") != null ? request.getAttribute("startDate") : "" %>">

                    <label for="endDate">End Date:</label>
                    <input type="date" id="endDate" name="endDate"
                           value="<%= request.getAttribute("endDate") != null ? request.getAttribute("endDate") : "" %>">

                    <button type="submit" class="btn btn-warning" style="margin-left: 10px;">Generate Report</button>
                </form>
            </div>
        </div>

        <!-- Khu vực hiển thị biểu đồ -->
        <div class="container">
            <div class="row">
                <!-- Biểu đồ số 1: User Statistics -->
                <div class="col-md-12 col-sm-12 chart-container" style="display: block; width: 100%; margin-bottom: 30px;">
                    <div class="chart-title">Customer Statistics</div>
                    <canvas id="userChart"></canvas>
                </div>
            </div>

            <div class="row">
                <!-- Biểu đồ số 2: Product Statistics -->
                <div class="col-md-12 col-sm-12 chart-container" style="display: block; width: 100%; margin-bottom: 30px;">
                    <div class="chart-title">Product Statistics</div>
                    <canvas id="productChart"></canvas>
                </div>
            </div>

            <div class="row">
                <!-- Biểu đồ số 3: Blog Statistics -->
                <div class="col-md-12 col-sm-12 chart-container" style="display: block; width: 100%; margin-bottom: 30px;">
                    <div class="chart-title">Blog Statistics</div>
                    <canvas id="blogChart"></canvas>
                </div>
            </div>

            <div class="row">
                <!-- Biểu đồ số 4: Feedback Statistics -->
                <div class="col-md-12 col-sm-12 chart-container" style="display: block; width: 100%; margin-bottom: 30px;">
                    <div class="chart-title">Feedback Statistics</div>
                    <canvas id="feedbackChart"></canvas>
                </div>
            </div>
        </div>


        <!-- Code ChartJS -->
        <script type="text/javascript">
            // Lấy dữ liệu userStats
            var userStats = <%= new Gson().toJson(statisticsMap.get("userStats")) %>;
            var userLabels = userStats.map(stat => stat.date);
            var userData = userStats.map(stat => stat.userCount);

            var ctxUser = document.getElementById('userChart').getContext('2d');
            var userChart = new Chart(ctxUser, {
                type: 'line',
                data: {
                    labels: userLabels,
                    datasets: [{
                            label: 'Customers Registered',
                            data: userData,
                            borderColor: 'rgb(75, 192, 192)',
                            tension: 0.2
                        }]
                }
            });

            // Lấy dữ liệu productStats
            var productStats = <%= new Gson().toJson(statisticsMap.get("productStats")) %>;
            var productLabels = productStats.map(stat => stat.date);
            var productData = productStats.map(stat => stat.productCount);

            var ctxProduct = document.getElementById('productChart').getContext('2d');
            var productChart = new Chart(ctxProduct, {
                type: 'line',
                data: {
                    labels: productLabels,
                    datasets: [{
                            label: 'Products Sold',
                            data: productData,
                            borderColor: 'rgb(54, 162, 235)',
                            tension: 0.2
                        }]
                }
            });

            // Lấy dữ liệu blogStats
            var blogStats = <%= new Gson().toJson(statisticsMap.get("blogStats")) %>;
            var blogLabels = blogStats.map(stat => stat.date);
            var blogData = blogStats.map(stat => stat.blogCount);

            var ctxBlog = document.getElementById('blogChart').getContext('2d');
            var blogChart = new Chart(ctxBlog, {
                type: 'line',
                data: {
                    labels: blogLabels,
                    datasets: [{
                            label: 'Blogs Created',
                            data: blogData,
                            borderColor: 'green',
                            tension: 0.2
                        }]
                }
            });

            // Lấy dữ liệu feedbackStats
            var feedbackStats = <%= new Gson().toJson(statisticsMap.get("feedbackStats")) %>;
            var feedbackLabels = feedbackStats.map(stat => stat.date);
            var feedbackData = feedbackStats.map(stat => stat.feedbackCount);

            var ctxFeedback = document.getElementById('feedbackChart').getContext('2d');
            var feedbackChart = new Chart(ctxFeedback, {
                type: 'line',
                data: {
                    labels: feedbackLabels,
                    datasets: [{
                            label: 'Feedbacks Received',
                            data: feedbackData,
                            borderColor: 'rgb(153, 102, 255)',
                            tension: 0.2
                        }]
                }
            });
        </script>

        <!-- Footer -->
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
                                <h2>Quick Shop</h2>
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
                                    <li><a href="#">Privacy Policy</a></li>
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
                                    <li><a href="#">Affiliate Program</a></li>
                                    <li><a href="#">Copyright</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-sm-3 col-sm-offset-1">
                            <div class="single-widget">
                                <h2>About Shopper</h2>
                                <form action="#" class="searchform">
                                    <input type="text" placeholder="Your email address" />
                                    <button type="submit" class="btn btn-default">
                                        <i class="fa fa-arrow-circle-o-right"></i>
                                    </button>
                                    <p>Get the most recent updates from <br />our site and be updated yourself...</p>
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
