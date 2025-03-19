

<%-- 
    Document   : index
    Created on : Jan 18, 2025, 1:13:24 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.User,java.util.List,jakarta.servlet.http.HttpSession,model.DAOUser, model.DAOOrder, entity.OrderDetail,model.DAOOrder"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.text.NumberFormat, java.util.Locale" %>
<%@page import="java.util.List,entity.Blog,jakarta.servlet.http.HttpSession,entity.User,model.DAOBlog,entity.Category,entity.Order" %>

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
                color: red;
                font-weight: bold;
            }

            .active-status {
                color: green;
                font-weight: bold;
            }


            .d-flex {
                display: flex;
                align-items: center;
                justify-content: space-between;
            }


            .add-setting-button {
                display: flex;
                justify-content: flex-end;
                margin-top: 20px;
            }

            .form-control {
                width: 300px;
                font-size: 16px;
                padding: 10px;
                height: 40px;
            }

            .form-control option {
                white-space: nowrap;
                height: auto;
            }


            .new-pagination-area {
                text-align: center;
                margin-bottom: 20px;
            }

            .new-pagination {
                list-style: none;
                padding: 0;
                margin: 0;
                display: inline-flex;
            }

            .new-pagination li {
                margin: 0 5px;
            }

            .new-pagination li a {
                display: inline-block;
                width: 35px;
                height: 35px;
                text-align: center;
                line-height: 35px;
                border-radius: 50%;
                background-color: #f1f1f1;
                color: #333;
                text-decoration: none;
                font-weight: bold;
                font-size: 14px;
                transition: 0.3s;
            }

            /* Cập nhật lại phần active */
            .new-pagination li.new-active a {
                background-color: #ff8c00 !important;
                color: white !important;
            }

            .new-pagination li a:hover {
                background-color: #ddd;
            }

            .footer {
                margin-top: 50px; /* khoảng cách từ phân trang tới footer */
                text-align: center;
                font-size: 14px;
                color: #777;
            }

        </style>
        <style>
            #notification {
                position: fixed;
                top: 20px;
                left: 50%;
                transform: translateX(-50%);
                background-color: #cce5ff;
                color: #004085;
                padding: 15px 30px;
                border-radius: 5px;
                display: none;
                opacity: 0;
                transition: opacity 0.5s ease-in-out;
                z-index: 9999;
            }

            #notification.show {
                display: block;
                opacity: 1;
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
                                    <li><a href="salesDashboardController" class="active">Home</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <form action="SaleOrderController" method="get" onsubmit="return validateSearch()">
                                <input type="hidden" name="service" value="search">
                                <div class="search_box pull-right" 
                                     style="position: relative; display: flex; flex-direction: column; align-items: start; border: 1px solid #ccc;
                                     border-radius: 20px; padding: 5px 10px; background-color: #f8f8f8; width: 100%;">
                                    <div style="display: flex; align-items: center; width: 100%;">
                                        <input type="text" id="searchQuery" name="query" placeholder="Search orders..."
                                               value="${param.query}"
                                               style="border: none; outline: none; background: transparent; flex-grow: 1; font-size: 14px; padding: 5px 10px; border-radius: 20px;">
                                        <button type="submit" 
                                                style="border: none; background: transparent; cursor: pointer; font-size: 16px; color: #aaa; margin-left: 5px;">
                                            <i class="fa fa-search"></i> 
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div><!--/header-bottom-->
        </header><!--/header-->

        <section id="settings-section">
            <div class="container">
                <div class="row">
                    <div class="col-sm-12">
                        <h2 style="color: red">Order List</h2>
                        <div class="container">
                            <div style="display: flex; align-items: center; gap: 8px;">
                                <form action="MarketingPostController" method="get" style="display: flex; align-items: center; gap: 8px;">
                                    <input type="hidden" value="sort" name="service">
                                    <div class="form-group mb-0" style="margin-bottom: 0;">
                                        <label for="sortBy" style="font-size: 13px; margin-right: 8px; color: #555;">Sort By:</label>
                                        <select id="sortBy" name="sortBy" style="width: 130px; font-size: 13px; padding: 6px; margin-right: 8px; border-radius: 4px; border: 1px solid #ccc;">
                                            <option value="b.id" <%= "id".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>ID</option>
                                            <option value="title" <%= "title".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>Title</option>
                                            <option value="postTime" <%= "postTime".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>Post Time</option>
                                            <option value="author" <%= "author".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>Author</option>
                                            <option value="status" <%= "status".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>Status</option>
                                        </select>
                                    </div>

                                    <div class="form-group mb-0" style="margin-bottom: 0;">
                                        <label for="sortOrder" style="font-size: 13px; margin-right: 8px; color: #555;">Order:</label>
                                        <select id="sortOrder" name="sortOrder" style="width: 130px; font-size: 13px; padding: 6px; margin-right: 8px; border-radius: 4px; border: 1px solid #ccc;">
                                            <option value="asc" <%= "asc".equals(request.getAttribute("sortOrder")) ? "selected" : "" %>>Ascending</option>
                                            <option value="desc" <%= "desc".equals(request.getAttribute("sortOrder")) ? "selected" : "" %>>Descending</option>
                                        </select>
                                    </div>

                                    <button type="submit" class="btn btn-warning custom-btn" style="padding: 6px 15px; font-size: 13px; display: flex; align-items: center; justify-content: center; border-radius: 4px;">
                                        <i style="margin-right: 5px;"></i> Sort
                                    </button>
                                </form>

                            </div>
                        </div>
                        <br>
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th><a href="MarketingPostController?service=sort&sortBy=b.id&sortOrder=asc">ID</a></th>
                                    <th><a href="MarketingPostController?service=sort&sortBy=author&sortOrder=asc">Buyer Name</a></th>
                                    <th><a href="MarketingPostController?service=sort&sortBy=postTime&sortOrder=asc">Order time</a></th>
                                    <th><a href="MarketingPostController?service=sort&sortBy=title&sortOrder=asc">Shipping Adress</a></th>
                                    <th>Delivery status</th>
                                    <th>Payment status</th>
                                    <th>Payment Method</th>
                                    <th>ToTal Price</th>
                                    <th>View Bill Detail</th>
                                    <th>Shipper assignment</th>
                                </tr>
                            </thead>
                            <tbody id="blog-list">
                                <%
                                    List<Order> orders = (List<Order>) request.getAttribute("orders");
                                    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                                    if (orders != null && !orders.isEmpty()) {
                                        for (Order order : orders) {
                                            int orderId = order.getId();
                                            Boolean isAssigned = (Boolean) request.getAttribute("isAssigned_" + orderId);
                                            if (isAssigned == null) isAssigned = false;  // Tránh null pointer
                                %>
                                <tr id="blog-<%= orderId %>">
                                    <td>
                                        <a href="MarketingPostController?service=blogFilter&id=<%= orderId %>"><%= orderId %></a>
                                    </td>
                                    <td><%= order.getUser().getName() %></td>
                                    <td><%= order.getOrderTime() %></td>
                                    <td><%= order.getShippingAddress() %></td>
                                    <td><%= order.getOrderStatus() %></td>
                                    <td><%= order.getPayment().getPaymentStatus() %></td>
                                    <td><%= order.getPaymentMethod().getName() %></td>
                                    <td><%= currencyFormatter.format(order.getTotalPrice()) %></td>

                                    <!-- View Bill -->
                                    <td>
                                        <a href="SaleOrderController?service=ViewBillWithID&orderID=<%= orderId %>" 
                                           style="display: inline-block; padding: 8px 16px; background-color: #4CAF50; color: white;
                                           text-decoration: none; border-radius: 5px; font-weight: bold;
                                           transition: background-color 0.3s ease;"
                                           onmouseover="this.style.backgroundColor = '#45a049';" 
                                           onmouseout="this.style.backgroundColor = '#4CAF50';">
                                            View Bill
                                        </a>
                                    </td>

                                    <!-- Assign / Assigned Button -->
                                    <td>
                                        <% if (isAssigned) { %>
                                        <button style="background-color: grey; color: white; border: none; padding: 8px 16px;
                                                cursor: not-allowed; opacity: 0.6; border-radius: 5px;" disabled>
                                            Assigned
                                        </button>
                                        <% } else { %>
                                        <button onclick="location.href = 'SaleOrderController?service=ShipperAssignment&orderID=<%= orderId %>'"
                                                style="background-color: #17a2b8; color: white; border: none; padding: 8px 16px;
                                                cursor: pointer; border-radius: 5px;">
                                            Assign
                                        </button>
                                        <% } %>
                                    </td>
                                </tr>
                                <%
                                        } // end for
                                    } else {
                                %>
                                <tr>
                                    <td colspan="11" style="text-align: center; color: red">No Orders found.</td>
                                </tr>
                                <% } %>
                            </tbody>

                        </table>

                        <div class="new-pagination-area">
                            <ul class="new-pagination">
                                <%
                                Integer totalPages = (Integer) request.getAttribute("totalPages");
                                Integer currentPage = (Integer) request.getAttribute("currentPage");
                                String service = request.getParameter("service");
                                if (service == null) {
                                    service = "listAllBlogs";
                                }

                                // Lấy các tham số để duy trì filter
                                String sortBy = (String) request.getAttribute("sortBy");
                                String sortOrder = (String) request.getAttribute("sortOrder");
                                String query = (String) request.getAttribute("query");

                                String filterId = (request.getAttribute("id") != null) ? request.getAttribute("id").toString() : "";
                                String filterAuthorId = (request.getAttribute("authorID") != null) ? request.getAttribute("authorID").toString() : "";
                                String filterStatus = (request.getAttribute("status") != null) ? request.getAttribute("status").toString() : "";

                                // Lấy categoryID nếu service là CateWithID
                                String categoryID = (request.getAttribute("categoryID") != null) ? request.getAttribute("categoryID").toString() : "";

                                // Tạo chuỗi query string filter
                                StringBuilder filterParams = new StringBuilder();
                                if (query != null && !query.isEmpty()) {
                                    filterParams.append("&query=").append(query);
                                }
                                if (sortBy != null && !sortBy.isEmpty()) {
                                    filterParams.append("&sortBy=").append(sortBy);
                                }
                                if (sortOrder != null && !sortOrder.isEmpty()) {
                                    filterParams.append("&sortOrder=").append(sortOrder);
                                }
                                if (!filterId.isEmpty()) filterParams.append("&id=").append(filterId);
                                if (!filterAuthorId.isEmpty()) filterParams.append("&authorID=").append(filterAuthorId);
                                if (!filterStatus.isEmpty()) filterParams.append("&status=").append(filterStatus);
                                if (!categoryID.isEmpty()) filterParams.append("&categoryID=").append(categoryID); // Thêm categoryID vào URL

                                if (totalPages != null && totalPages > 0) {
                                %>
                                <!-- Nút Previous -->
                                <% if (currentPage > 1) { %>
                                <li>
                                    <a href="MarketingPostController?service=<%= service %>&page=<%= currentPage - 1 %><%= filterParams.toString() %>"><<</a>
                                </li>
                                <% } %>

                                <!-- Hiển thị các trang -->
                                <% for (int i = 1; i <= totalPages; i++) { %>
                                <li class="<%= (i == currentPage) ? "new-active" : "" %>">
                                    <a href="MarketingPostController?service=<%= service %>&page=<%= i %><%= filterParams.toString() %>"><%= i %></a>
                                </li>
                                <% } %>

                                <!-- Nút Next -->
                                <% if (currentPage < totalPages) { %>
                                <li>
                                    <a href="MarketingPostController?service=<%= service %>&page=<%= currentPage + 1 %><%= filterParams.toString() %>">>></a>
                                </li>
                                <% } %>
                                <% } %>

                            </ul>
                        </div>
                    </div>
                </div>
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

    <!-- Modal hiển thị hóa đơn -->
    <!-- Background overlay -->
    <div id="modalOverlay" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%;
         background: rgba(0, 0, 0, 0.5); z-index: 999;">
    </div>

    <!-- Modal hóa đơn -->


    <%
        List<OrderDetail> orderDetails = (List<OrderDetail>) request.getAttribute("orderDetails");
        String recipientName = (String) request.getAttribute("recipientName");
        String recipientPhone = (String) request.getAttribute("recipientPhone");
        String shippingAddress = (String) request.getAttribute("shippingAddress");
    %>

    <div id="billModal" style="display: none; position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%);
         background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 15px rgba(0,0,0,0.5);
         width: 60%; z-index: 1000;">
        <button onclick="closeBillModal()" style="position: absolute; top: 10px; right: 10px;
                background: none; border: none; font-size: 24px; cursor: pointer; color: #555;">
            ❌
        </button>

        <h2 style="text-align: center; margin-bottom: 20px;">Bill Details</h2>

        <!-- Thông tin người nhận -->
        <p><b>Recipient Name:</b> <span id="recipientName"><%= recipientName %></span></p>
        <p><b>Phone Number:</b> <span id="recipientPhone"><%= recipientPhone %></span></p>
        <p><b>Shipping Address:</b> <span id="shippingAddress"><%= shippingAddress %></span></p>

        <!-- Bảng chi tiết sản phẩm -->
        <table style="width: 100%; border-collapse: collapse; margin-top: 10px;">
            <tr>
                <th style="border: 1px solid #ddd; padding: 10px; background-color: #eee;">Product Name</th>
                <th style="border: 1px solid #ddd; padding: 10px; background-color: #eee;">Image</th>
                <th style="border: 1px solid #ddd; padding: 10px; background-color: #eee;">Color</th>
                <th style="border: 1px solid #ddd; padding: 10px; background-color: #eee;">Storage</th>
                <th style="border: 1px solid #ddd; padding: 10px; background-color: #eee;">Quantity</th>
                <th style="border: 1px solid #ddd; padding: 10px; background-color: #eee;">Total Price</th>
            </tr>
            <%
                if (orderDetails != null) {
                    for (OrderDetail detail : orderDetails) {
            %>
            <tr>
                <td style="border: 1px solid #ddd; padding: 10px;"><%= detail.getProductVariant().getProduct().getName() %></td>
                <td style="border: 1px solid #ddd; padding: 10px;">
                    <img src="<%= detail.getProductVariant().getProduct().getImageURL() %>" width="50">
                </td>
                <td style="border: 1px solid #ddd; padding: 10px;"><%= detail.getProductVariant().getColor().getColorName() %></td>
                <td style="border: 1px solid #ddd; padding: 10px;"><%= detail.getProductVariant().getStorage().getCapacity() %></td>
                <td style="border: 1px solid #ddd; padding: 10px;"><%= detail.getQuantity() %></td>
                <td style="border: 1px solid #ddd; padding: 10px;">
                    <%= detail.getProductVariant().getPrice() * detail.getQuantity() %> VND
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>

        <!-- Thông tin thanh toán và vận chuyển -->
        <div style="display: flex; justify-content: flex-end; margin-top: 15px;">
            <table style="border-collapse: collapse;">
                <tr>
                    <td style="padding: 5px; font-weight: bold; text-align: right; width: 180px;">Payment Status:</td>
                    <td style="padding: 5px; color: green; text-align: left;">
                        <%= orderDetails != null && !orderDetails.isEmpty() ? orderDetails.get(0).getPayment().getPaymentStatus() : "N/A" %>
                    </td>
                </tr>
                <tr>
                    <td style="padding: 5px; font-weight: bold; text-align: right; width: 180px;">Shipping Status:</td>
                    <td style="padding: 5px; color: blue; text-align: left;">In Transit</td> 
                </tr>
                <tr>
                    <td style="padding: 5px; font-weight: bold; text-align: right; width: 180px;">Payment Method:</td>
                    <td style="padding: 5px; color: red; text-align: left;">
                        <%= orderDetails != null && !orderDetails.isEmpty() ? orderDetails.get(0).getPaymentMethod().getName() : "N/A" %>
                    </td>
                </tr>
            </table>
        </div>
    </div>




    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.scrollUp.min.js"></script>
    <script src="js/price-range.js"></script>
    <script src="js/jquery.prettyPhoto.js"></script>
    <script src="js/main.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
            function deleteBlog(blogId) {
                if (confirm('Are you sure you want to delete this blog?')) {
                    $.ajax({
                        url: "MarketingPostController?service=removeBlog&blogId=" + blogId,
                        type: "GET",
                        dataType: "json",
                        success: function (response) {
                            if (response.status === "success") {
                                alert(response.message);
                                $("#blog-" + blogId).remove();

                                if ($("#blog-list").children().length === 0) {
                                    window.history.back();
                                } else {
                                    window.location.reload();
                                }
                            } else {
                                alert(response.message);
                            }
                        },
                        error: function (xhr, status, error) {
                            alert("Error while deleting blog. Please try again.");
                        }
                    });
                }
            }


    </script>
    <script>
        function changeStatus(blogId, currentStatus) {
            var newStatus = (currentStatus === true) ? false : true;
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "MarketingPostController?service=changeStatus", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.send("id=" + blogId + "&status=" + newStatus);

            xhr.onload = function () {
                if (xhr.status === 200) {
                    var newStatusText = newStatus ? "Inactive" : "Active";
                    document.getElementById("status_" + blogId).innerHTML =
                            '<a href="MarketingPostController?service=blogFilter&status=' + newStatus + '">' + newStatusText + '</a>';
                    var changeButton = document.getElementById("changeButton_" + blogId);
                    changeButton.setAttribute("onclick", "changeStatus(" + blogId + ", " + newStatus + ")");
                    changeButton.innerHTML = "Change";
                } else {
                    alert("Lỗi khi cập nhật trạng thái.");
                }
            };
        }

    </script>
    <script>
        function showNotification(message) {
            var notification = document.getElementById('notification');
            var messageElement = document.getElementById('notification-message');

            messageElement.textContent = message;

            notification.classList.add('show');

            setTimeout(function () {
                notification.classList.remove('show');
            }, 1000);
        }

        window.onload = function () {
            var urlParams = new URLSearchParams(window.location.search);
            var message = urlParams.get('message');
            if (message) {
                showNotification(message);
            }
        };
    </script>
    <script>
        function filterByCategory() {
            var selectedCategory = document.getElementById("categoryID").value;
            window.location.href = "MarketingPostController?service=CatewithID&categoryID=" + selectedCategory;
        }
    </script>
    <script>
// Mở modal và gọi dữ liệu AJAX
        function viewBillDetail(orderId) {
            // Hiển thị modal và overlay
            document.getElementById("modalOverlay").style.display = "block";
            document.getElementById("billModal").style.display = "block";

            // Hiển thị thông báo đang tải
            document.getElementById("billTable").innerHTML = `
        <tr><td colspan="6" style="text-align:center;">Đang tải dữ liệu...</td></tr>`;

            // Gọi AJAX để lấy dữ liệu đơn hàng
            fetch('SaleOrderController?service=billWithOrderID&orderId=' + orderId + '&ajax=true')
                    .then(response => response.json())
                    .then(data => {
                        updateBillModal(data);
                    })
                    .catch(error => {
                        console.error('Lỗi khi lấy dữ liệu: ', error);
                        document.getElementById("billTable").innerHTML = `
                <tr><td colspan="6" style="text-align:center;">Có lỗi xảy ra khi tải dữ liệu.</td></tr>`;
                    });
        }

// Cập nhật nội dung modal sau khi có dữ liệu
        function updateBillModal(data) {
            // Cập nhật thông tin người nhận
            document.getElementById("recipientName").textContent = data.recipientName || "N/A";
            document.getElementById("recipientPhone").textContent = data.recipientPhone || "N/A";
            document.getElementById("shippingAddress").textContent = data.shippingAddress || "N/A";

            // Tạo bảng sản phẩm
            let tableHTML = `
        <tr>
            <th style="border: 1px solid #ddd; padding: 10px; background-color: #eee;">Product Name</th>
            <th style="border: 1px solid #ddd; padding: 10px; background-color: #eee;">Image</th>
            <th style="border: 1px solid #ddd; padding: 10px; background-color: #eee;">Color</th>
            <th style="border: 1px solid #ddd; padding: 10px; background-color: #eee;">Storage</th>
            <th style="border: 1px solid #ddd; padding: 10px; background-color: #eee;">Quantity</th>
            <th style="border: 1px solid #ddd; padding: 10px; background-color: #eee;">Total Price</th>
        </tr>`;

            data.orderDetails.forEach(detail => {
                const totalPrice = detail.productVariant.price * detail.quantity;
                tableHTML += `
        <tr>
            <td style="border: 1px solid #ddd; padding: 10px;">${detail.productVariant.product.name}</td>
            <td style="border: 1px solid #ddd; padding: 10px;">
                <img src="${detail.productVariant.product.imageURL}" width="50">
            </td>
            <td style="border: 1px solid #ddd; padding: 10px;">${detail.productVariant.color.colorName}</td>
            <td style="border: 1px solid #ddd; padding: 10px;">${detail.productVariant.storage.capacity}</td>
            <td style="border: 1px solid #ddd; padding: 10px;">${detail.quantity}</td>
            <td style="border: 1px solid #ddd; padding: 10px;">${totalPrice} VND</td>
        </tr>`;
            });

            document.getElementById("billTable").innerHTML = tableHTML;

            // Cập nhật thông tin thanh toán và vận chuyển
            document.querySelector("#billModal .payment-status").textContent = data.paymentStatus || "N/A";
            document.querySelector("#billModal .shipping-status").textContent = "In Transit";
            document.querySelector("#billModal .payment-method").textContent = data.paymentMethod || "N/A";
        }

// Đóng modal khi bấm ❌ hoặc overlay
        function closeBillModal() {
            document.getElementById("modalOverlay").style.display = "none";
            document.getElementById("billModal").style.display = "none";
        }

// Đóng modal khi bấm vào overlay
        document.getElementById("modalOverlay").addEventListener("click", closeBillModal);

    </script>
    <div id="notification" class="alert alert-info" style="display: none;">
        <p id="notification-message">Blog Added successfully!</p>
    </div>
</body>
</html>