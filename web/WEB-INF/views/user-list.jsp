<%-- 
    Document   : index
    Created on : Jan 18, 2025, 1:13:24 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.User,java.util.List,jakarta.servlet.http.HttpSession,model.DAOUser"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Home | E-Shopper</title>
        <link href="/css/bootstrap.min.css" rel="stylesheet">
        <link href="/css/font-awesome.min.css" rel="stylesheet">
        <link href="/css/prettyPhoto.css" rel="stylesheet">
        <link href="/css/price-range.css" rel="stylesheet">
        <link href="/css/animate.css" rel="stylesheet">
        <link href="/css/main.css" rel="stylesheet">
        <link href="/css/responsive.css" rel="stylesheet">
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
                margin-bottom: 20px; /* khoảng cách giữa phân trang và footer */
            }

            .new-pagination {
                list-style: none;
                padding: 0;
                margin: 0;
                display: inline-flex;
            }

            .new-pagination li {
                margin: 0 3px; /* giảm khoảng cách giữa các trang */
            }

            .new-pagination li a {
                display: inline-block;
                width: 30px; /* giảm kích thước nút phân trang */
                height: 30px;
                text-align: center;
                line-height: 30px; /* căn giữa số trang */
                border-radius: 50%; /* tạo hình tròn */
                background-color: #f1f1f1;
                color: #333;
                text-decoration: none;
                font-weight: bold;
                font-size: 14px; /* giảm kích thước chữ */
            }

            .new-pagination li a.new-active {
                background-color: #ff8c00; /* màu sắc cho nút active */
                color: white;
            }

            .new-pagination li a.new-disabled {
                background-color: #ddd;
                color: #aaa;
                pointer-events: none;
            }

            /* Tách footer */
            .footer {
                margin-top: 50px; /* khoảng cách từ phân trang tới footer */
                text-align: center;
                font-size: 14px;
                color: #777;
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
                                    <li><a href="#"><i class="fa fa-user"></i> Account</a></li>
                                    <li><a href="#"><i class="fa fa-star"></i> Wishlist</a></li>
                                    <li><a href="checkout.html"><i class="fa fa-crosshairs"></i> Checkout</a></li>
                                    <li><a href="${pageContext.request.contextPath}/CartController"><i class="fa fa-shopping-cart"></i> Cart</a></li>
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
                            <form action="UserController" method="get">
                                <input type="hidden" value="search" name="service">
                                <div class="search_box pull-right" style="position: relative; display: flex; align-items: center; border: 1px solid #ccc; border-radius: 20px; padding: 5px 10px; background-color: #f8f8f8;">
                                    <input type="text" name="query" placeholder="Search" value="${param.query}" style="border: none; outline: none; background: transparent; flex-grow: 1; font-size: 14px; padding: 5px 10px; border-radius: 20px;">
                                    <button type="submit" style="border: none; background: transparent; cursor: pointer; font-size: 16px; color: #aaa; margin-left: 5px;">
                                        <i class="fa fa-search"></i> 
                                    </button>
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
                        <h2 style="color: red">Users List</h2>
                        <div class="container">
                            <form action="UserController" method="get" style="display: flex; flex-wrap: nowrap; align-items: center; gap: 8px;">

                                <input type="hidden" value="sort" name="service">

                                <!-- Sort By Dropdown -->
                                <div class="form-group mb-0" style="margin-bottom: 0;">
                                    <label for="sortBy" style="font-size: 13px; margin-right: 8px; color: #555;">Sort By:</label>
                                    <select id="sortBy" name="sortBy" style="width: 130px; font-size: 13px; padding: 6px; margin-right: 8px; border-radius: 4px; border: 1px solid #ccc;">
                                        <option value="id" <%= "id".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>ID</option>
                                        <option value="name" <%= "name".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>Full Name</option>
                                        <option value="email" <%= "email".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>Email</option>
                                        <option value="phoneNumber" <%= "phoneNumber".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>Phone</option>
                                        <option value="gender" <%= "gender".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>Gender</option>
                                        <option value="dateOfBirth" <%= "dateOfBirth".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>Date of Birth</option>
                                        <option value="roleId" <%= "roleId".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>Role</option>
                                        <option value="isDisabled" <%= "isDisabled".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>Status</option>
                                    </select>
                                </div>

                                <!-- Sort Order Dropdown -->
                                <div class="form-group mb-0" style="margin-bottom: 0;">
                                    <label for="sortOrder" style="font-size: 13px; margin-right: 8px; color: #555;">Order:</label>
                                    <select id="sortOrder" name="sortOrder" style="width: 130px; font-size: 13px; padding: 6px; margin-right: 8px; border-radius: 4px; border: 1px solid #ccc;">
                                        <option value="asc" <%= "asc".equals(request.getAttribute("sortOrder")) ? "selected" : "" %>>Ascending</option>
                                        <option value="desc" <%= "desc".equals(request.getAttribute("sortOrder")) ? "selected" : "" %>>Descending</option>
                                    </select>
                                </div>

                                <!-- Sort Button -->
                                <button type="submit" class="btn btn-warning custom-btn" style="padding: 6px 15px; font-size: 13px; display: flex; align-items: center; justify-content: center; border-radius: 4px;">
                                    <i  style="margin-right: 5px;"></i> Sort
                                </button>
                            </form>
                        </div>
                                    <br>
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th><a href="UserController?service=sort&sortBy=id&sortOrder=asc">ID</a></th>
                                    <th><a href="UserController?service=sort&sortBy=name&sortOrder=asc">Name</a></th>
                                    <th><a href="UserController?service=sort&sortBy=email&sortOrder=asc">Email</a></th>
                                    <th><a href="UserController?service=sort&sortBy=phoneNumber&sortOrder=asc">Phone</a></th>
                                    <th><a href="UserController?service=sort&sortBy=gender&sortOrder=asc">Gender</a></th>
                                    <th><a href="UserController?service=sort&sortBy=dateOfBirth&sortOrder=asc">Date of Birth</a></th>
                                    <th><a href="UserController?service=sort&sortBy=roleId&sortOrder=asc">Role</a></th>
                                    <th><a href="UserController?service=sort&sortBy=isDisabled&sortOrder=asc">Status</a></th>
                                    <th>Update</th>
                                    <th>Add new User</th>
                                    <th>Delete</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% 
                                    List<User> users = (List<User>) request.getAttribute("users");
                                    if (users != null && !users.isEmpty()) {
                                        for (User item : users) {
                                %>
                                <tr id="user-<%= user.getId() %>">
                                    <td><%= item.getId() %></td>
                                    <td><%= item.getName() %></td>
                                    <td><%= item.getEmail() %></td>
                                    <td><%= item.getPhoneNumber() %></td>
                                    <td>
                                        <a href="UserController?service=userFilter&gender=<%= item.isGender()?"0" : "1"%>"><%= item.isGender() ? "Male" : "Female" %></a>
                                    </td>
                                    <td><%= item.getDateOfBirth() %></td>
                                    <td>
                                        <a href="UserController?service=userFilter&role=<%= item.getRoleId() %>"><%= item.getRoleName() %></a>
                                    </td>
                                    <td>
                                        <a href="UserController?service=userFilter&status=<%= item.isDisabled()? "0" : "1" %>"><%= item.isDisabled() ? "Disabled" : "Active" %></a>
                                    </td>
                                    <td>
                                        <a href="UserController?service=TuDienthem&TuDienthem=<%= item.getId() %>" class="btn btn-primary">Update</a>
                                    </td>
                                    <td>
                                        <a href="UserController?service=TuDienthem&TuDienthem=<%= item.getId() %>" class="btn btn-primary">Update</a>
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-danger" onclick="deleteUser(<%= item.getId() %>)">Delete</button>
                                    </td>
                                </tr>
                                <% 
                                    }
                                } else { 
                                %>
                                <tr>
                                    <td colspan="8" style="text-align: center;">No users found.</td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>

                        <!-- Pagination moved outside tbody -->
                        <div class="new-pagination-area">
                            <ul class="new-pagination">
                                <% 
                                    Integer totalPages = (Integer) request.getAttribute("totalPages");
                                    Integer currentPage = (Integer) request.getAttribute("currentPage");
                                    String service = request.getParameter("service");
                                    if (service == null) {
                                        service = "listAllUser";
                                    }
                                    String sortBy = (String) request.getAttribute("sortBy");
                                    String sortOrder = (String) request.getAttribute("sortOrder");
                                    String query = (String) request.getAttribute("query");
                                    Integer filterId = (Integer) request.getAttribute("role");
                                    Integer filterAuthorId = (Integer) request.getAttribute("gender");
                                    Boolean filterStatus = (Boolean) request.getAttribute("status");
                                    
                                    if (totalPages != null && totalPages > 0) {
                                        for (int i = 1; i <= totalPages; i++) {    
                                %>
                                <li>
                                    <a href="UserController?service=<%= service %>&page=<%= i %> 
                                       <%= (service.equals("sort") ? "&sortBy=" + sortBy + "&sortOrder=" + sortOrder : "") %>
                                       <%= (service.equals("search") ? "&query=" + query : "") %>
                                       <%= (service.equals("userFilter") ? "&filterId=" + filterId + "&filterAuthorId=" + filterAuthorId + "&filterStatus=" + filterStatus : "") %>">
                                        <%= i %>
                                    </a>
                                </li>
                                <% 
                                        } 
                                %>
                                <li>
                                    <a href="UserController?service=<%= service %>&page=<%= currentPage + 1 %>
                                       <%= (service.equals("sort") ? "&sortBy=" + sortBy + "&sortOrder=" + sortOrder : "") %>
                                       <%= (service.equals("search") ? "&query=" + query : "") %>
                                       <%= (service.equals("userFilter") ? "&filterId=" + filterId + "&filterAuthorId=" + filterAuthorId + "&filterStatus=" + filterStatus : "") %>">
                                        Next
                                    </a>
                                </li>
                                <% 
                                    } 
                                %>
                            </ul>
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
                function deleteUser(userId) {
    if (confirm('Are you sure you want to delete this user?')) {
        $.ajax({
            url: "UserController?service=removeUser&userId=" + userId,
            type: "GET",
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    alert(response.message);
                    $("#user-" + userId).remove();
                } else {
                    alert(response.message);
                }
            },
            error: function () {
                alert("Error while deleting user. Please try again.");
            }
        });
    }
}

        </script>
    </body>
</html>