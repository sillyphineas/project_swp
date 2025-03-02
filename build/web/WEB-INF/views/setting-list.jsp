<%-- 
    Document   : index
    Created on : Jan 18, 2025, 1:13:24 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.User"%>
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
            /* Cập nhật cho trạng thái Inactive và Active */
            .inactive-status {
                color: red;
                font-weight: bold;
            }

            .active-status {
                color: green;
                font-weight: bold;
            }

            /* Cập nhật cho phần d-flex */
            /* Cập nhật để căn chỉnh nút Add Setting với lề phải */
            .d-flex {
                display: flex;
                align-items: center;
                justify-content: space-between;
            }

            /* Sửa cho nút Add Setting thẳng với lề phải */
            .add-setting-button {
                display: flex;
                justify-content: flex-end;
                margin-top: 20px;
            }
            /* Cập nhật chiều cao của dropdown để hiển thị đầy đủ */
            .form-control {
                width: 300px; /* Đảm bảo dropdown đủ rộng */
                font-size: 16px; /* Tăng kích thước font chữ */
                padding: 10px; /* Thêm padding để dropdown rộng hơn */
                height: 40px; /* Điều chỉnh chiều cao của dropdown để hiển thị đầy đủ chữ */
            }

            /* Cập nhật cho các tùy chọn trong dropdown */
            .form-control option {
                white-space: nowrap; /* Đảm bảo nội dung không bị xuống dòng */
                height: auto; /* Đảm bảo chiều cao của các tùy chọn đủ để hiển thị */
            }

            /* Giảm khoảng cách giữa các thành phần trong Sort, Search, Filter */
            .form-row {
                display: flex;
                align-items: center; /* Căn giữa theo chiều dọc */
                justify-content: flex-start; /* Các phần tử nằm gần nhau */
                gap: 10px; /* Tạo khoảng cách vừa phải */
                margin-bottom: 10px !important; /* Giảm khoảng cách giữa các dòng */
            }

            /* Giảm khoảng cách giữa các phần tử bên trong mỗi dòng */
            .form-group {
                margin-bottom: 0px !important; /* Xóa khoảng cách thừa */
                display: flex;
                align-items: center;
            }

            /* Định dạng input và select cho đồng đều */
            .form-control {
                width: auto !important; /* Giữ kích thước tự nhiên */
                min-width: 180px; /* Đảm bảo không quá nhỏ */
                padding: 5px;
                height: 38px; /* Đồng bộ với button */
            }

            /* Tinh chỉnh button để không bị cách xa nhau */
            .btn {
                height: 38px; /* Giữ cùng chiều cao với input */
                padding: 5px 12px; /* Tạo kích thước cân đối */
            }

            /* Giữ khoảng cách hợp lý giữa button */
            .btn-warning,
            .btn-success,
            .btn-link {
                margin-left: 5px !important;
            }


            /* Đảm bảo các nút trong Actions luôn nằm cùng 1 dòng */
            .btn-group {
                display: flex !important;  /* Sử dụng flexbox để giữ các nút trên cùng 1 dòng */
                flex-wrap: nowrap !important; /* Ngăn các nút xuống dòng */
                gap: 5px; /* Tạo khoảng cách nhỏ giữa các nút */
            }

            /* Giữ kích thước các button đồng nhất */
            .btn-group .btn {
                white-space: nowrap; /* Ngăn chặn xuống dòng trong button */
                min-width: 80px; /* Giữ các nút có cùng chiều rộng */
                padding: 6px 12px; /* Đồng bộ padding */
            }

            /* Đảm bảo table không bị co lại làm ảnh hưởng */
            .table td {
                white-space: nowrap; /* Ngăn chặn text bị xuống dòng */
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
                            <div class="search_box pull-right">
                                <input type="text" placeholder="Search"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!--/header-bottom-->
        </header><!--/header-->

        <section id="settings-section">
            <div class="container">
                <div class="row">
                    <div class="col-sm-12">
                        <h2>Settings List</h2>
                        <hr>
                        <div class="container">
                            <!-- 🔹 Sort Form -->
                            <form action="SettingController" method="GET" class="form-row d-flex justify-content-between align-items-center mb-3">
                                <input type="hidden" name="service" value="sortSettings">

                                <div class="form-group mb-0 mr-2">
                                    <label for="sortBy" class="mr-2">Sort By:</label>
                                    <select id="sortBy" name="sortBy" class="form-control">
                                        <option value="id">ID</option>
                                        <option value="key_name">Key Name</option>
                                    </select>
                                </div>

                                <div class="form-group mb-0 mr-2">
                                    <label for="sortOrder" class="mr-2">Order:</label>
                                    <select id="sortOrder" name="sortOrder" class="form-control">
                                        <option value="asc">Ascending</option>
                                        <option value="desc">Descending</option>
                                    </select>
                                </div>

                                <button type="submit" class="btn btn-warning">Sort</button>
                                <a href="SettingController?service=addSetting" class="btn btn-success">Add Setting</a>
                            </form>

                            <hr>

                            <!-- 🔹 Search Form -->
                            <form action="SettingController" method="GET" class="form-row d-flex justify-content-between align-items-center mb-3">
                                <input type="hidden" name="service" value="searchSettings">
                                <div class="form-group mb-0 flex-grow-1">
                                    <label for="searchKeyword" class="mr-2">Search:</label>
                                    <input type="text" id="searchKeyword" name="keyword" value="${keyword}" class="form-control" placeholder="Enter key name or value">
                                </div>
                                <button type="submit" class="btn btn-warning">Search</button>
                                <a href="SettingController?service=displaySettings" class="btn btn-link">Reset</a>
                            </form>

                            <hr>

                            <!-- 🔹 Filter Form -->
                            <form action="SettingController" method="GET" class="form-row d-flex justify-content-between align-items-center mb-3">
                                <input type="hidden" name="service" value="filterSettings">
                                <div class="form-group mb-0 mr-2">
                                    <label for="statusFilter" class="mr-2">Status:</label>
                                    <select id="statusFilter" name="status" class="form-control">
                                        <option value="All">All</option>
                                        <option value="Active">Active</option>
                                        <option value="Inactive">Inactive</option>
                                    </select>
                                </div>
                                <div class="form-group mb-0 mr-2">
                                    <label for="typeFilter" class="mr-2">Type:</label>
                                    <select id="typeFilter" name="type" class="form-control">
                                        <option value="All">All Types</option>
                                        <c:forEach var="type" items="${settingTypes}">
                                            <option value="${type.typeName}">${type.typeName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-warning">Filter</button>
                                <a href="SettingController?service=displaySettings" class="btn btn-link">Reset</a>
                            </form>
                        </div>

                        <hr>
                        <table class="table table-bordered">
                            <thead class="thead-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Type</th>
                                    <th>Key Name</th>
                                    <th>Value</th>
                                    <th>Description</th>
                                    <th>Status</th>
                                    <th>Created At</th>
                                    <th>Updated At</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>

                                <c:forEach var="setting" items="${settingList}">
                                    <tr>
                                        <td>${setting.id}</td>
                                        <td>${setting.typeName}</td>
                                        <td>${setting.keyName}</td>
                                        <td>${setting.value}</td>
                                        <td>${setting.description}</td>
                                        <td class="${setting.status == 'Inactive' ? 'inactive-status' : 'active-status'}">
                                            ${setting.status == 'Inactive' ? 'Inactive' : 'Active'}
                                        </td>
                                        <td>${setting.createdAt}</td>
                                        <td>${setting.updatedAt}</td>
                                        <td>
                                            <div class="btn-group">
                                                <a href="SettingController?service=activate&settingId=${setting.id}" 
                                                   class="btn btn-success btn-sm" ${setting.status == 'Active' ? 'disabled' : ''}>Activate</a>

                                                <a href="SettingController?service=deactivate&settingId=${setting.id}" 
                                                   class="btn btn-warning btn-sm" ${setting.status == 'Inactive' ? 'disabled' : ''}>Deactivate</a>

                                                <a href="SettingController?service=updateSetting&settingId=${setting.id}" 
                                                   class="btn btn-info btn-sm">Edit</a>

                                                <a href="SettingController?service=deleteSetting&settingId=${setting.id}" 
                                                   class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?');">Delete</a>
                                            </div>
                                        </td>

                                    </tr>
                                </c:forEach>

                            </tbody>
                        </table>
                        <div class="pagination">
                            <ul class="pagination">
                                <c:if test="${currentPage > 1}">
                                    <li><a href="SettingController?service=displaySettings&page=${currentPage - 1}">&laquo; Prev</a></li>
                                    </c:if>

                                <c:forEach begin="1" end="${totalPages}" var="i">
                                    <li class="${i == currentPage ? 'active' : ''}">
                                        <a href="SettingController?service=displaySettings&page=${i}">${i}</a>
                                    </li>
                                </c:forEach>

                                <c:if test="${currentPage < totalPages}">
                                    <li><a href="SettingController?service=displaySettings&page=${currentPage + 1}">Next &raquo;</a></li>
                                    </c:if>
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
    </body>
</html>