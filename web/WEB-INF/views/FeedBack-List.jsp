<%-- 
    Document   : index
    Created on : Jan 18, 2025, 1:13:24 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.User,java.util.List,jakarta.servlet.http.HttpSession,model.DAOUser"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="java.util.List,entity.Blog,jakarta.servlet.http.HttpSession,entity.User,model.DAOBlog,entity.Category,entity.Feedback" %>

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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
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
                                    <li><a href="MarketingDashboardController" class="active">Home</a></li>
                                    <li><a href="MarketingPostController?service=listAllBlogs">Post List</a></li>
                                    <li><a href="SliderController">Slider List</a></li>
                                    <li><a href="CustomerController">Customer List</a></li>
                                    <li><a href="MarketingProductController">Product List</a></li>
                                    <li><a href="MaketingFeedBackController">Feedback List</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <form action="MaketingFeedBackController" method="get" ">
                                <input type="hidden" value="search" name="service">
                                <div class="search_box pull-right" style="position: relative; display: flex; flex-direction: column; align-items: start; border: 1px solid #ccc; border-radius: 20px; padding: 5px 10px; background-color: #f8f8f8;">
                                    <div style="display: flex; align-items: center; width: 100%;">
                                        <input type="text" id="searchQuery" name="query" placeholder="Search Feedbacks..." value="${param.query}" 
                                               style="border: none; outline: none; background: transparent; flex-grow: 1; font-size: 14px; padding: 5px 10px; border-radius: 20px;">
                                        <button type="submit" style="border: none; background: transparent; cursor: pointer; font-size: 16px; color: #aaa; margin-left: 5px;">
                                            <i class="fa fa-search"></i> 
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div
                    </div>
                </div>
            </div><!--/header-bottom-->
        </header><!--/header-->

        <section id="settings-section">
            <div class="container">
                <div class="row">
                    <div class="col-sm-12">
                        <h2 style="color: red">FeedBack List</h2>
                        <div class="container">
                            <div style="display: flex; align-items: center; gap: 8px;">
                                <form action="MaketingFeedBackController" method="get" style="display: flex; align-items: center; gap: 8px;">
                                    <input type="hidden" value="sort" name="service">

                                    <!-- Sort By -->
                                    <div class="form-group mb-0" style="margin-bottom: 0;">
                                        <label for="sortBy" style="font-size: 13px; margin-right: 8px; color: #555;">Sort By:</label>
                                        <select id="sortBy" name="sortBy" style="width: 150px; font-size: 13px; padding: 6px; margin-right: 8px; border-radius: 4px; border: 1px solid #ccc;">
                                            <option value="name" <%= "name".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>Reviewer Name</option>
                                            <option value="productName" <%= "productName".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>Product Name</option>
                                            <option value="reviewTime" <%= "reviewTime".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>Review Time</option>
                                            <option value="rating" <%= "rating".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>Rating</option>
                                            <option value="status" <%= "status".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>Status</option>
                                        </select>
                                    </div>

                                    <!-- Sort Order -->
                                    <div class="form-group mb-0" style="margin-bottom: 0;">
                                        <label for="sortOrder" style="font-size: 13px; margin-right: 8px; color: #555;">Order:</label>
                                        <select id="sortOrder" name="sortOrder" style="width: 130px; font-size: 13px; padding: 6px; margin-right: 8px; border-radius: 4px; border: 1px solid #ccc;">
                                            <option value="asc" <%= "asc".equals(request.getAttribute("sortOrder")) ? "selected" : "" %>>Ascending</option>
                                            <option value="desc" <%= "desc".equals(request.getAttribute("sortOrder")) ? "selected" : "" %>>Descending</option>
                                        </select>
                                    </div>

                                    <!-- Sort Button -->
                                    <button type="submit" class="btn btn-warning custom-btn" style="padding: 6px 15px; font-size: 13px; display: flex; align-items: center; justify-content: center; border-radius: 4px;">
                                        <i class="fa fa-sort" style="margin-right: 5px;"></i> Sort
                                    </button>
                                </form>

                                <label for="star" style="font-size: 13px; margin-right: 8px; color: #555;">Rating:</label>
                                <%
                                    int selectedStar = request.getAttribute("selectedStar") != null ? (int) request.getAttribute("selectedStar") : 0;
                                %>
                                <select id="star" name="star" onchange="filterByStar()" 
                                        style="width: 150px; font-size: 13px; padding: 6px; border-radius: 4px; border: 1px solid #ccc;">
                                    <option value="">All Ratings</option>
                                    <option value="1" <%= (selectedStar == 1) ? "selected" : "" %>>1 Star</option>
                                    <option value="2" <%= (selectedStar == 2) ? "selected" : "" %>>2 Stars</option>
                                    <option value="3" <%= (selectedStar == 3) ? "selected" : "" %>>3 Stars</option>
                                    <option value="4" <%= (selectedStar == 4) ? "selected" : "" %>>4 Stars</option>
                                    <option value="5" <%= (selectedStar == 5) ? "selected" : "" %>>5 Stars</option>
                                </select>
                            </div>
                        </div>
                        <br>
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>ID</a></th>
                                    <th>Category</th>
                                    <th>Image</th>
                                    <th><a href="MaketingFeedBackController?service=sort&sortBy=name&sortOrder=asc">User Name</a></th>
                                    <th><a href="MaketingFeedBackController?service=sort&sortBy=reviewTime&sortOrder=asc">Review Time</a></th>
                                    <th>Content</th>
                                    <th><a href="MaketingFeedBackController?service=sort&sortBy=rating&sortOrder=asc">Rate</a></th>    
                                    <th><a href="MaketingFeedBackController?service=sort&sortBy=status&sortOrder=asc">Status</a></th>
                                    <th>Change Status</th>
                                    <th>View</th>
                                    
                                </tr>
                            </thead>
                            <tbody id="blog-list">
                                <%
                                    List<Feedback> feedbacks = (List<Feedback>) request.getAttribute("feedbacks");

                                    if (feedbacks != null && !feedbacks.isEmpty()) {
                                        for (Feedback feedback : feedbacks) {
                                            
                                %>
                                <tr id="blog-<%= feedback.getId() %>">
                                    <td>
                                        <%= feedback.getId() %>
                                    </td>
                                    <td>
                                        <%= feedback.getProduct().getName() %>, 
                                        <%= feedback.getProductVariant().getColor().getColorName() %>, 
                                        <%= feedback.getProductVariant().getStorage().getCapacity() %>
                                    </td>
                                    <td>
                                        <% 
                                            List<String> imageList = feedback.getImages();
                                            if (imageList != null) { 
                                        %>
                                        <div style="display: flex; gap: 10px;"> <!-- Sắp xếp ảnh theo chiều ngang -->
                                            <% for (String imageUrl : imageList) { %>
                                            <a href="<%= imageUrl %>" target="_blank">
                                                <img src="<%= imageUrl %>" alt="ảnh lỗi" style="width: 100px; height: 100px; object-fit: cover; border-radius: 5px;">
                                            </a>
                                            <% } %>
                                        </div>
                                        <% } else { %>
                                        Không có ảnh
                                        <% } %>
                                    </td>
                                    <td>
                                        <%= feedback.getUser().getName() %>
                                    </td>
                                    <td><%= feedback.getReviewTime() %></td>
                                    <td><%= feedback.getSubContent() %></td>
                                    <td>
                                        <span style="white-space: nowrap;">
                                            <% 
                                                Integer rating = feedback.getRating(); // Kiểm tra null
                                                int starCount = (rating != null) ? rating : 0; // Nếu null thì gán 0
                                                for (int i = 0; i < starCount; i++) { 
                                            %>
                                            <i class="fas fa-star" style="color: gold;"></i>
                                            <% } %>
                                            <% for (int i = starCount; i < 5; i++) { %>
                                            <i class="far fa-star" style="color: gold;"></i>
                                            <% } %>
                                        </span>
                                    </td>
                                    <td id="status_<%= feedback.getId() %>">
                                        <span style="<%= (feedback.getStatus() != null && feedback.getStatus().equalsIgnoreCase("visible")) ? "color: green; font-weight: bold;" : "color: red; font-weight: bold;" %>">
                                            <%= (feedback.getStatus() != null && feedback.getStatus().equalsIgnoreCase("visible")) ? "Visible" : "Hidden" %>
                                        </span>
                                    </td>
                                    <td>
                                        <button id="changeButton_<%= feedback.getId() %>" class="btn btn-info"
                                                onclick="changeFeedbackStatus(<%= feedback.getId() %>, '<%= feedback.getStatus() %>')">
                                            <%= feedback.getStatus().equals("visible") ? "Hide (Violation)" : "Make Visible" %>
                                        </button>
                                    </td>
                                    <td>
                                        <a href="MarketingFeedbackDetails?service=showDetail&id=<%= feedback.getId() %>" 
                                           class="btn" 
                                           style="display: inline-block; padding: 7px 20px; background-color: #4CAF50; color: white; text-align: center; text-decoration: none; border-radius: 5px; font-size: 13px; font-weight: bold; cursor: pointer; border: none;">
                                            View
                                        </a>
                                    </td>
                                   
                                </tr>
                                <% 
                                    }
                                } else { 
                                %>
                                <tr>
                                    <td colspan="8" style="text-align: center;">No Feedback found.</td>
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
                                        service = "listAllfeedBack";
                                    }

                                    // Lấy các tham số để duy trì bộ lọc khi chuyển trang
                                    String query = (String) request.getAttribute("query");
                                    String sortBy = (String) request.getAttribute("sortBy");
                                    String sortOrder = (String) request.getAttribute("sortOrder");
                                    

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
                                    if (selectedStar > 0) {
                                        filterParams.append("&star=").append(selectedStar);
                                    }

                                    if (totalPages != null && totalPages > 0) {
                                %>

                                <!-- Nút Previous -->
                                <% if (currentPage > 1) { %>
                                <li>
                                    <a href="MaketingFeedBackController?service=<%= service %>&page=<%= currentPage - 1 %><%= filterParams.toString() %>"><<</a>
                                </li>
                                <% } %>

                                <!-- Hiển thị các trang -->
                                <% for (int i = 1; i <= totalPages; i++) { %>
                                <li class="<%= (i == currentPage) ? "new-active" : "" %>">
                                    <a href="MaketingFeedBackController?service=<%= service %>&page=<%= i %><%= filterParams.toString() %>"><%= i %></a>
                                </li>
                                <% } %>

                                <!-- Nút Next -->
                                <% if (currentPage < totalPages) { %>
                                <li>
                                    <a href="MaketingFeedBackController?service=<%= service %>&page=<%= currentPage + 1 %><%= filterParams.toString() %>">>></a>
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
        function changeFeedbackStatus(feedbackId, currentStatus) {
            var newStatus = (currentStatus === "visible") ? "hidden_due_to_violation" : "visible";

            var xhr = new XMLHttpRequest();
            xhr.open("POST", "MaketingFeedBackController?service=changeFeedbackStatus", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.send("id=" + feedbackId + "&status=" + newStatus);

            xhr.onload = function () {
                if (xhr.status === 200) {
                    // Cập nhật trạng thái
                    var statusSpan = document.querySelector("#status_" + feedbackId + " span");
                    statusSpan.textContent = (newStatus === "visible") ? "Visible" : "Hidden ";
                    statusSpan.style.color = (newStatus === "visible") ? "green" : "red"; // Cập nhật màu sắc

                    // Cập nhật nút bấm
                    var changeButton = document.getElementById("changeButton_" + feedbackId);
                    changeButton.innerHTML = (newStatus === "visible") ? "Hide (Violation)" : "Make Visible";
                    changeButton.setAttribute("onclick", "changeFeedbackStatus(" + feedbackId + ", '" + newStatus + "')");
                } else {
                    alert("Error updating feedback status.");
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
        function filterByStar() {
            var star = document.getElementById("star").value;
            // Nếu không chọn (All Ratings) thì star rỗng, chuyển hướng về danh sách feedback mặc định
            if (!star) {
                window.location.href = "MaketingFeedBackController?service=listAllfeedBack";
            } else {
                window.location.href = "MaketingFeedBackController?service=FilterByStar&star=" + star;
            }
        }
    </script>
    <div id="notification" class="alert alert-info" style="display: none;">
        <p id="notification-message">Blog Added successfully!</p>
    </div>
</body>
</html>
