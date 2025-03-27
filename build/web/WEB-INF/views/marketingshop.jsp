
<%-- 
    Document   : shop
    Created on : Jan 18, 2025, 1:15:10 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="entity.User"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Shop | E-Shopper</title>
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
            .form-group label {
                margin-bottom: 5px;  /* Giảm khoảng cách giữa nhãn và trường chọn */
            }

            .form-group select {
                margin-top: 0;  /* Đảm bảo không có khoảng cách thừa trên select */
                padding: 5px;  /* Điều chỉnh padding để không bị lệch */
                font-size: 16px;  /* Đảm bảo font đồng nhất */
            }

        </style>
    </head><!--/head-->
</head><!--/head-->
<% if (session.getAttribute("cartMessage") != null) { %>
<script type="text/javascript">
    alert("<%= session.getAttribute("cartMessage") %>");</script>
    <% 
        session.removeAttribute("cartMessage"); 
    %>
    <% } %>

<body>
    <header id="header"><!--header-->
        <div class="header_top"><!--header_top-->
            <div class="container">
                <div class="row">
                    <div class="col-sm-6 ">
                        <div class="contactinfo">
                            <ul class="nav nav-pills">
                                <li><a href=""><i class="fa fa-phone"></i> +2 95 01 88 821</a></li>
                                <li><a href=""><i class="fa fa-envelope"></i> info@domain.com</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="social-icons pull-right">
                            <ul class="nav navbar-nav">
                                <li><a href=""><i class="fa fa-facebook"></i></a></li>
                                <li><a href=""><i class="fa fa-twitter"></i></a></li>
                                <li><a href=""><i class="fa fa-linkedin"></i></a></li>
                                <li><a href=""><i class="fa fa-dribbble"></i></a></li>
                                <li><a href=""><i class="fa fa-google-plus"></i></a></li>
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
                            <a href="HomePageController"><img src="images/home/logo.png" alt="" /></a>
                        </div>
                        <div class="btn-group pull-right">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default dropdown-toggle usa" data-toggle="dropdown">
                                    USA
                                    <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu">
                                    <li><a href="">Canada</a></li>
                                    <li><a href="">UK</a></li>
                                </ul>
                            </div>

                            <div class="btn-group">
                                <button type="button" class="btn btn-default dropdown-toggle usa" data-toggle="dropdown">
                                    DOLLAR
                                    <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu">
                                    <li><a href="">Canadian Dollar</a></li>
                                    <li><a href="">Pound</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-8">
                        <div class="shop-menu pull-right">
                            <ul class="nav navbar-nav">
                                <!--                                <li><a href="UserProfileServlet"><i class="fa fa-user"></i> Account</a></li>
                                                                <li><a href="${pageContext.request.contextPath}/CartURL"><i class="fa fa-shopping-cart"></i> Cart</a></li>-->
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

                </div>
            </div>
        </div>
    </header>

    <section id="settings-section">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <h2>Product List</h2>
                    <hr>
                    <div class="container">



                        <form action="MarketingProductController" method="GET" class="form-row d-flex justify-content-between align-items-center mb-3">
                            <div class="form-group">
                                <a href="AddProductController?action=addProduct" class="btn btn-success" onclick="confirmAdd(event, 'addProduct')">Add Product</a>
                                <a href="AddProductController?action=addProductVariant" class="btn btn-success" onclick="confirmAdd(event, 'addProductVariant')">Add Product Variant</a>
                                <a href="AddProductController?action=addColor" class="btn btn-success" onclick="confirmAdd(event, 'addColor')">Add Color</a>
                                <a href="AddProductController?action=addStorage" class="btn btn-success" onclick="confirmAdd(event, 'addStorage')">Add Storage</a>
                            </div>       
                        </form>

                        <hr>
                        <form action="${pageContext.request.contextPath}/MarketingProductController" method="GET">
                            <div class="d-flex align-items-center">
                                <input type="text" name="search" value="${param.search}" class="form-control" placeholder="Search by product name" style="margin-right: 5px;" />
                                <button type="submit" class="btn btn-default"><i class="fa fa-search"></i> Search</button>
                            </div>
                        </form>
                        <hr>


                        <form action="${pageContext.request.contextPath}/MarketingProductController" method="GET">
                            <div class="row">
                                <!-- Cột 1 -->
                                <div class="row">
                                    <!-- Cột 1 -->
                                    <div class="col-md-4 form-group d-flex flex-column">
                                        <label for="brandID" class="d-block">Brand:</label>
                                        <select id="brandID" name="brandID" class="form-control">
                                            <option value="0">All Brands</option>
                                            <c:forEach var="brand" items="${brands}">
                                                <option value="${brand.id}" ${param.brandID == brand.id ? 'selected' : ''}>${brand.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <!-- Cột 2 -->
                                    <div class="col-md-4 form-group d-flex flex-column">
                                        <label for="os" class="d-block">Operating System:</label>
                                        <select id="os" name="os" class="form-control">
                                            <option value="">All</option>
                                            <c:forEach var="osItem" items="${osList}">
                                                <option value="${osItem}" ${param.os == osItem ? 'selected' : ''}>${osItem}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <!-- Cột 3 -->
                                    <div class="col-md-4 form-group d-flex flex-column">
                                        <label for="connectivity" class="d-block">Connectivity:</label>
                                        <select id="connectivity" name="connectivity" class="form-control">
                                            <option value="">All</option>
                                            <c:forEach var="connect" items="${connectivityList}">
                                                <option value="${connect}" ${param.connectivity == connect ? 'selected' : ''}>${connect}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div class="row">
                                    <!-- Cột 1 -->
                                    <div class="col-md-4 form-group d-flex flex-column">
                                        <label for="statusFilter" class="d-block">Status:</label>
                                        <select id="statusFilter" name="statusFilter" class="form-control">
                                            <option value="">All</option>
                                            <option value="Show" ${param.statusFilter == 'Show' ? 'selected' : ''}>Show</option>
                                            <option value="Hide" ${param.statusFilter == 'Hide' ? 'selected' : ''}>Hide</option>
                                        </select>
                                    </div>

                                    <!-- Cột 2 -->
                                    <div class="col-md-4 form-group d-flex flex-column">
                                        <label for="screenSize" class="d-block">Screen Size (inches):</label>
                                        <select id="screenSize" name="screenSize" class="form-control">
                                            <option value="0">All</option>
                                            <c:forEach var="size" items="${screenSizeList}">
                                                <option value="${size}" ${screenSize == size ? 'selected' : ''}>${size} inches</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <!-- Cột 3 -->
                                    <div class="col-md-4 form-group d-flex flex-column">
                                        <label for="batteryCapacity" class="d-block">Battery Capacity:</label>
                                        <select id="batteryCapacity" name="batteryCapacity" class="form-control">
                                            <option value="0">All</option>
                                            <c:forEach var="capacity" items="${batteryCapacityList}">
                                                <option value="${capacity}" ${batteryCapacity == capacity ? 'selected' : ''}>${capacity} mAh</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>


                                <button type="submit" class="btn btn-primary">Filter</button>
                        </form>
                        <br>

                    </div>

                </div>

                <hr>
                <table class="table table-bordered">
                    <thead class="thead-dark">
                        <tr>
                            <th>ID</th>

                            <th>Product Name</th>
                            <th>Brand</th>
                            <th>Image</th>
                            <th>Os</th>
                            <th>Screen Type</th>
                            <th>Connectivity</th>
                            <th>Delete</th>
                            <th>Status</th>
                            <th style="margin-left: 30px">Details</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="product" items="${productList}">
                            <tr>
                                <td>${product.id}</td>
                                <td>${product.name}</td>
                                <td>  <c:forEach var="brand" items="${brands}">
                                        <c:if test="${brand.id == product.brandID}">
                                            <p>${brand.name}</p>
                                        </c:if>
                                    </c:forEach></td>
                                <td> <img src="${product.imageURL}" alt="" style="width: 100px; height: 100px; object-fit: cover;"/></td>
                                <td>${product.os}</td>
                                <td>${product.connectivity}</td>
                                <td>${product.screenSize}</td>
                                <td>
                                    <a href="MarketingProductController?action=delete&id=${product.id}" onclick="confirmDelete(event)" class="btn btn-danger">Delete</a>
                                </td> 
                                <td>
                                    <c:if test="${product.isDisabled}">
                                        <a href="${pageContext.request.contextPath}/MarketingProductController?action=show&id=${product.id}" 
                                           class="btn btn-warning" 
                                           onclick="confirmAction(event, 'show')">Hide</a>
                                    </c:if>
                                    <c:if test="${!product.isDisabled}">
                                        <a href="${pageContext.request.contextPath}/MarketingProductController?action=hide&id=${product.id}" 
                                           class="btn btn-warning" 
                                           onclick="confirmAction(event, 'hide')">Show</a>
                                    </c:if>
                                <td>
                                    <a href="MarketingProductDetails?id=${product.id}" class="btn btn-success">Detail</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="pagination">
                    <ul class="pagination">
                        <!-- Previous Page Link -->
                        <c:if test="${currentPage > 1}">
                            <li><a href="MarketingProductController?page=${currentPage - 1}&brandID=${param.brandID}&search=${param.search}&os=${param.os}&connectivity=${param.connectivity}&statusFilter=${param.statusFilter}&screenSize=${param.screenSize}&batteryCapacity=${param.batteryCapacity}&sortby=${param.sortby}&sortOrder=${param.sortOrder}">&laquo; Prev</a></li>
                            </c:if>

                        <!-- Page Number Links -->
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li class="${i == currentPage ? 'active' : ''}">
                                <a href="MarketingProductController?page=${i}&brandID=${param.brandID}&search=${param.search}&os=${param.os}&connectivity=${param.connectivity}&statusFilter=${param.statusFilter}&screenSize=${param.screenSize}&batteryCapacity=${param.batteryCapacity}&sortby=${param.sortby}&sortOrder=${param.sortOrder}">${i}</a>
                            </li>
                        </c:forEach>

                        <!-- Next Page Link -->
                        <c:if test="${currentPage < totalPages}">
                            <li><a href="MarketingProductController?page=${currentPage + 1}&brandID=${param.brandID}&search=${param.search}&os=${param.os}&connectivity=${param.connectivity}&statusFilter=${param.statusFilter}&screenSize=${param.screenSize}&batteryCapacity=${param.batteryCapacity}&sortby=${param.sortby}&sortOrder=${param.sortOrder}">Next &raquo;</a></li>
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
                            <li><a href="">Online Help</a></li>
                            <li><a href="">Contact Us</a></li>
                            <li><a href="">Order Status</a></li>
                            <li><a href="">Change Location</a></li>
                            <li><a href="">FAQ’s</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-sm-2">
                    <div class="single-widget">
                        <h2>Quock Shop</h2>
                        <ul class="nav nav-pills nav-stacked">
                            <li><a href="">T-Shirt</a></li>
                            <li><a href="">Mens</a></li>
                            <li><a href="">Womens</a></li>
                            <li><a href="">Gift Cards</a></li>
                            <li><a href="">Shoes</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-sm-2">
                    <div class="single-widget">
                        <h2>Policies</h2>
                        <ul class="nav nav-pills nav-stacked">
                            <li><a href="">Terms of Use</a></li>
                            <li><a href="">Privecy Policy</a></li>
                            <li><a href="">Refund Policy</a></li>
                            <li><a href="">Billing System</a></li>
                            <li><a href="">Ticket System</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-sm-2">
                    <div class="single-widget">
                        <h2>About Shopper</h2>
                        <ul class="nav nav-pills nav-stacked">
                            <li><a href="">Company Information</a></li>
                            <li><a href="">Careers</a></li>
                            <li><a href="">Store Location</a></li>
                            <li><a href="">Affillate Program</a></li>
                            <li><a href="">Copyright</a></li>
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
                <p class="pull-left">Copyright © 2013 E-Shopper. All rights reserved.</p>
                <p class="pull-right">Designed by <span><a target="_blank" href="http://www.themeum.com">Themeum</a></span></p>
            </div>
        </div>
    </div>

</footer><!--/Footer-->



<script src="js/jquery.js"></script>
<script src="js/price-range.js"></script>
<script src="js/jquery.scrollUp.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.prettyPhoto.js"></script>
<script src="js/main.js"></script>
<script type="text/javascript">

                                               function confirmDelete(event) {
                                                   var result = confirm("Do you sure you want to delete this product?");
                                                   if (!result) {
                                                       event.preventDefault();
                                                   }
                                               }

                                               function confirmAction(event, action) {
                                                   var message = (action === 'hide') ? "Do you sure you want to hide this product?" : "Do you sure you want to show this product?";
                                                   var result = confirm(message);
                                                   if (!result) {
                                                       event.preventDefault();
                                                   }
                                               }

                                               function confirmAdd(event, action) {
                                                   var message = "";
                                                   if (action === 'addProduct') {
                                                       message = "Do you sure you want to add this product?";
                                                   } else if (action === 'addProductVariant') {
                                                       message = "Do you sure you want to add this product variant?";
                                                   } else if (action === 'addColor') {
                                                       message = "Do you sure you want to add this color?";
                                                   } else if (action === 'addStorage') {
                                                       message = "Do you sure you want to add this storage?";
                                                   }

                                                   var result = confirm(message);
                                                   if (!result) {
                                                       event.preventDefault(); // Ngừng hành động nếu người dùng không xác nhận
                                                   }
                                               }
                                               $('#addProductVariantModal').on('shown.bs.modal', function () {
                                                   $('#myInput').trigger('focus');
                                               });
</script>

</body>
</html>
