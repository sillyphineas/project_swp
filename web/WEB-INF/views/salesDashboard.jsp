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
        <title>Home | E-Shopper</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <link href="css/prettyPhoto.css" rel="stylesheet">
        <link href="css/price-range.css" rel="stylesheet">
        <link href="css/animate.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        <link href="css/responsive.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            .report-form {
                margin: 20px 0;
                display: flex;
                justify-content: flex-start;
                align-items: center;
                gap: 10px; /* Giảm khoảng cách giữa các phần tử */
                flex-wrap: wrap; /* Cho phép phần tử xuống dòng nếu cần */
            }

            .report-form label {
                margin: 0 5px;
                font-weight: bold;
                font-size: 14px; /* Giảm kích thước font của label */
            }

            .report-form input,
            .report-form select {
                margin: 0 5px;
                font-size: 14px; /* Giảm kích thước font của input và select */
                width: 150px; /* Điều chỉnh chiều rộng của input và select */
            }

            .report-form button {
                margin-left: 10px;
                padding: 5px 15px; /* Giảm padding để nút nhỏ hơn */
                background-color: #ff9f00;
                color: white;
                border: none;
                cursor: pointer;
                font-size: 14px; /* Giảm kích thước font của nút */
            }

            .report-form button:hover {
                background-color: #ff7f00;
            }
            .chart-container {
                width: 100%; /* Sử dụng 100% chiều rộng của container */
                max-width: 1200px; /* Giới hạn chiều rộng tối đa */
                text-align: center;
                padding: 20px;
                background-color: #fff;
                border-radius: 10px;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            }

            #orderTrendChart, #revenueChart {
                max-width: 100%;
                height: 300px; /* Tăng chiều cao biểu đồ */
                border: 1px solid #ddd;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                background-color: #fff;
                border-radius: 8px;
                padding: 15px;
                margin-bottom: 30px;
            }
            .row1 {
                display: flex;
                justify-content: center; /* Căn giữa các phần tử trong mỗi dòng */
                margin-bottom: 30px; /* Thêm khoảng cách giữa các biểu đồ */
            }

            .chart-title {
                font-size: 18px;
                font-weight: bold;
                margin-bottom: 15px;
            }

            canvas {
                width: 100% !important; /* Đảm bảo canvas sử dụng toàn bộ chiều rộng container */
                height: auto !important; /* Giữ tỷ lệ hình ảnh */
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
    </head>
    <body>
        <header id="header">
            <!-- Giữ nguyên phần header như mã gốc -->
            <div class="header_top">
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
            </div>
            <div class="header-middle">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-4">
                            <div class="logo pull-left">
                                <a href="index.html"><img src="images/home/logo.png" alt="" /></a>
                            </div>
                            <div class="btn-group pull-right">
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle usa" data-toggle="dropdown">
                                        USA <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a href="#">Canada</a></li>
                                        <li><a href="#">UK</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle usa" data-toggle="dropdown">
                                        DOLLAR <span class="caret"></span>
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
            </div>
            <div class="header-bottom">
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
                                    <li><a href="salesDashboardController" class="active">Home</a></li>
                                    <li><a href="SaleOrderController">Order List</a></li>
                                </ul>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </header>

        <%
            List<Map<String, Object>> orderStats = (List<Map<String, Object>>) request.getAttribute("orderStats");
            String orderStatsJson = "[]"; 
            if (orderStats != null) {
                orderStatsJson = new Gson().toJson(orderStats);
            }
        %>

        <div class="container">
            <div class="report-form">
                <form action="salesDashboardController" method="get">
                    <label for="startDate">Start Date:</label>
                    <input type="date" id="startDate" name="startDate"
                           value="<%= request.getAttribute("startDate") != null ? request.getAttribute("startDate") : "" %>">

                    <label for="endDate">End Date:</label>
                    <input type="date" id="endDate" name="endDate"
                           value="<%= request.getAttribute("endDate") != null ? request.getAttribute("endDate") : "" %>">

                    <label for="assignedSaleId">Salesperson ID:</label>
                    <input type="number" id="assignedSaleId" name="assignedSaleId"
                           value="<%= request.getAttribute("assignedSaleId") != null ? request.getAttribute("assignedSaleId") : "" %>">

                    <label for="orderStatus">Order Status:</label>
                    <select id="orderStatus" name="orderStatus">
                        <option value="" <%= "".equals(request.getAttribute("orderStatus")) ? "selected" : "" %>>All</option>
                        <option value="awaiting pickup" <%= "awaiting pickup".equals(request.getAttribute("orderStatus")) ? "selected" : "" %>>Awaiting Pickup</option>
                        <option value="shipping" <%= "shipping".equals(request.getAttribute("orderStatus")) ? "selected" : "" %>>Shipping</option>
                        <option value="delivered" <%= "delivered".equals(request.getAttribute("orderStatus")) ? "selected" : "" %>>Delivered</option>
                        <option value="cancel" <%= "cancel".equals(request.getAttribute("orderStatus")) ? "selected" : "" %>>Cancel</option>
                        <option value="refund" <%= "refund".equals(request.getAttribute("orderStatus")) ? "selected" : "" %>>Refund</option>
                    </select>

                    <button type="submit" class="btn btn-warning" style="margin-left: 10px;">Generate Report</button>

                    <div class="total-stats" style="margin: 20px 0; font-size: 16px;">

                        <p><strong>Total Orders:</strong> <span id="totalOrders">0</span></p>
                        <p><strong>Awaiting Pickup:</strong> <span id="totalAwaitingPickup">0</span></p>
                        <p><strong>Shipping:</strong> <span id="totalShipping">0</span></p>
                        <p><strong>Delivered:</strong> <span id="totalDelivered">0</span></p>
                        <p><strong>Cancel:</strong> <span id="totalCancel">0</span></p>
                        <p><strong>Refund:</strong> <span id="totalRefund">0</span></p>
                        <p><strong>Total Revenue:</strong> <span id="totalRevenue">đ0</span></p>
                    </div>
                </form>
            </div>
        </div>
        <div class="container">
            <div class="row1">
                <div class="col-md-12">
                    <div class="chart-title">Success/Total Orders Trend</div>
                    <canvas id="orderTrendChart" width="700" height="250"></canvas> <!-- Tăng kích thước biểu đồ -->
                </div>
            </div>

            <div class="row1">
                <div class="col-md-12">
                    <div class="chart-title">Revenue Trend</div>
                    <canvas id="revenueChart" width="700" height="250"></canvas> <!-- Tăng kích thước biểu đồ -->
                </div>
            </div>
        </div>




        <footer id="footer">
            <!-- Giữ nguyên phần footer như mã gốc -->
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
                                    <a href="#"><div class="iframe-img"><img src="images/home/iframe1.png" alt="" /></div><div class="overlay-icon"><i class="fa fa-play-circle-o"></i></div></a>
                                    <p>Circle of Hands</p><h2>24 DEC 2014</h2>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="video-gallery text-center">
                                    <a href="#"><div class="iframe-img"><img src="images/home/iframe2.png" alt="" /></div><div class="overlay-icon"><i class="fa fa-play-circle-o"></i></div></a>
                                    <p>Circle of Hands</p><h2>24 DEC 2014</h2>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="video-gallery text-center">
                                    <a href="#"><div class="iframe-img"><img src="images/home/iframe3.png" alt="" /></div><div class="overlay-icon"><i class="fa fa-play-circle-o"></i></div></a>
                                    <p>Circle of Hands</p><h2>24 DEC 2014</h2>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="video-gallery text-center">
                                    <a href="#"><div class="iframe-img"><img src="images/home/iframe4.png" alt="" /></div><div class="overlay-icon"><i class="fa fa-play-circle-o"></i></div></a>
                                    <p>Circle of Hands</p><h2>24 DEC 2014</h2>
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
                                    <button type="submit" class="btn btn-default"><i class="fa fa-arrow-circle-o-right"></i></button>
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
        </footer>

        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
       <script type="text/javascript">
    var orderStats = <%= orderStatsJson %>;
    var orderLabels = orderStats.map(stat => stat.date);
    var totalOrdersData = orderStats.map(stat => stat.totalOrders);
    var successOrdersData = orderStats.map(stat => stat.successOrders);
    var revenueData = orderStats.map(stat => stat.revenue);

    var totalOrders = totalOrdersData.reduce((sum, value) => sum + value, 0);
    var totalRevenue = revenueData.reduce((sum, value) => sum + value, 0);
    var totalAwaitingPickup = orderStats.reduce((sum, stat) => sum + (stat.awaitingPickup || 0), 0);
    var totalShipping = orderStats.reduce((sum, stat) => sum + (stat.shipping || 0), 0);
    var totalDelivered = orderStats.reduce((sum, stat) => sum + (stat.delivered || 0), 0);
    var totalCancel = orderStats.reduce((sum, stat) => sum + (stat.cancel || 0), 0);
    var totalRefund = orderStats.reduce((sum, stat) => sum + (stat.refund || 0), 0);

    document.getElementById('totalOrders').textContent = totalOrders;
    document.getElementById('totalAwaitingPickup').textContent = totalAwaitingPickup;
    document.getElementById('totalShipping').textContent = totalShipping;
    document.getElementById('totalDelivered').textContent = totalDelivered;
    document.getElementById('totalCancel').textContent = totalCancel;
    document.getElementById('totalRefund').textContent = totalRefund;
    document.getElementById('totalRevenue').textContent = totalRevenue.toLocaleString('vi-VN') + ' đ';

    var ctxOrder = document.getElementById('orderTrendChart').getContext('2d');
    var orderTrendChart = new Chart(ctxOrder, {
        type: 'bar',
        data: {
            labels: orderLabels,
            datasets: [
                {
                    label: 'Total Orders',
                    data: totalOrdersData,
                    backgroundColor: 'rgba(255, 99, 132, 0.5)', 
                    borderColor: 'rgb(255, 99, 132)', 
                    borderWidth: 1
                },
                {
                    label: 'Success Orders',
                    data: successOrdersData,
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

    var ctxRevenue = document.getElementById('revenueChart').getContext('2d');
    var revenueChart = new Chart(ctxRevenue, {
        type: 'line',
        data: {
            labels: orderLabels,
            datasets: [{
                label: 'Revenue (đ)',
                data: revenueData,
                borderColor: 'rgb(255, 99, 132)',
                tension: 0.2
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
</script>
    </body>
</html>

