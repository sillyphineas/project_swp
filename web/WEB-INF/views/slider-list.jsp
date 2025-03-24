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
            /* Search and Filter Form */
            /* Cập nhật lại form để các phần tử căn chỉnh đúng */
            .form-section {
                display: flex;
                justify-content: flex-start;  /* Căn trái các phần tử */
                gap: 10px;  /* Giảm khoảng cách giữa các phần tử */
                align-items: center;  /* Căn giữa theo chiều dọc */
                margin-bottom: 20px;
            }

            form {
                display: flex;
                align-items: center;
                justify-content: flex-start;
                gap: 10px;
                margin-bottom: 20px;
            }

            form input, form select {
                padding: 10px;
                font-size: 16px;
                border: 1px solid #ccc;
                border-radius: 4px;
                width: 200px;  /* Giữ chiều rộng đồng nhất */
                height: 40px;  /* Căn chỉnh chiều cao cho đồng nhất */
            }

            /* Cập nhật các button trong form để có kích thước bằng nhau */
            /* Đảm bảo các button có chiều cao và chiều rộng đồng nhất */
            button, .btn {
                padding: 10px 20px;
                font-size: 16px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                height: 35px;  /* Đảm bảo chiều cao giống nhau */
                width: 100px;  /* Chiều rộng đồng nhất cho các button */
                text-align: center;  /* Căn giữa chữ trong button */
            }

            button:hover, .btn:hover {
                opacity: 0.8; /* Thêm hiệu ứng hover */
            }

            /* Responsive cho các button trên màn hình nhỏ */
            @media screen and (max-width: 768px) {
                button, .btn {
                    width: 100%;  /* Button chiếm toàn bộ chiều rộng trên thiết bị nhỏ */
                }
            }

            /* Đảm bảo button trong bảng có cùng kích thước */
            .table button {
                width: 100px;
                height: 35px;
            }

            /* Thêm một chút padding cho các button bên ngoài bảng (form) */
            .form-section button {
                width: 100px;
                height: 35px;
            }




            /* Pagination Styling */
            .pagination {
                text-align: center; /* Căn giữa các liên kết phân trang */
                margin-top: 20px;
                display: flex;
                justify-content: center; /* Căn giữa phần tử con (nút phân trang) */
                align-items: center; /* Đảm bảo rằng phần tử được căn giữa theo chiều dọc */
                width: 100%; /* Chiếm toàn bộ chiều rộng của phần tử cha */
            }

            .pagination a {
                margin: 0 5px;
                padding: 8px 16px;
                background-color: #f39c12;
                color: white;
                text-decoration: none;
                border-radius: 4px;
                transition: background-color 0.3s;
            }

            .pagination a:hover {
                background-color: #e67e22;
            }


            /* Table Styling */
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 30px;
            }

            table th, table td {
                padding: 12px;
                text-align: left;
                border: 1px solid #ddd;
                background-color: #f9f9f9;
            }

            /*            table th {
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

            /* Responsive Styling */
            @media screen and (max-width: 768px) {
                form {
                    flex-direction: column;
                    align-items: flex-start;
                }

                form input, form select {
                    width: 100%;
                }

                form button {
                    width: 100%;
                    margin-top: 10px;
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

        <section id="settings-section">
            <div class="container">
                <div class="row">
                    <div class="col-sm-12">
                        <h2>Slider List</h2>                        
                        <!-- Search and Filter Form -->
                        <div class="form-section">
                            <form action="${pageContext.request.contextPath}/SliderController" method="get" class="filter-form">
                                <select name="status"  style="width: 130px; font-size: 13px; padding: 6px; margin-right: 8px; border-radius: 4px; border: 1px solid #ccc; height: 35px">
                                    <option value="">All</option>
                                    <option value="0">Visible</option>
                                    <option value="1">Hidden</option>
                                </select>
                                <button type="submit" class="btn btn-warning" style="padding: 6px 15px; font-size: 13px; display: flex; align-items: center; justify-content: center; border-radius: 4px;">Filter</button>
                                <div class="add-setting-button">
                                    <a href="${pageContext.request.contextPath}/SliderController?service=addSlider" class="btn btn-success" style="padding: 6px 15px; font-size: 13px; display: flex; align-items: center; justify-content: center; border-radius: 4px;">Add Slider</a>
                                </div>
                            </form>
                        </div>
                        <c:if test="${not empty message}">
                            <div class="alert alert-${messageType}">
                                ${message}
                            </div>
                        </c:if>
                        <!-- Slider Table -->
                        <table class="table table-bordered">
                            <thead class="thead-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Title</th>
                                    <th>Image</th>
                                    <th>Backlinks</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="slider" items="${sliders}">
                                    <tr>
                                        <td>${slider.id}</td>
                                        <td>${slider.title}</td>
                                        <td><img src="${slider.imageURL}" alt="Slider Image" width="100" height="100" /></td>
                                        <td>${slider.backlinks}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${slider.isDisabled}">
                                                    Inactive
                                                </c:when>
                                                <c:otherwise>
                                                    Active
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <form action="SliderController" method="post">
                                                <input type="hidden" name="id" value="${slider.id}" />
                                                <c:choose>
                                                    <c:when test="${slider.isDisabled == true}">
                                                        <input type="hidden" name="isDisabled" value="false" />
                                                        <button type="submit" name="action" value="toggleStatus" class="btn btn-success " style="padding: 6px 15px; font-size: 13px; display: flex; align-items: center; justify-content: center; border-radius: 4px;">Activate</button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="hidden" name="isDisabled" value="true" />
                                                        <button type="submit" name="action" value="toggleStatus" class="btn btn-warning btn-sm" style="padding: 6px 15px; font-size: 13px; display: flex; align-items: center; justify-content: center; border-radius: 4px;">Deactivate</button>
                                                    </c:otherwise>
                                                </c:choose>
                                                <a href="${pageContext.request.contextPath}/SliderController?service=viewDetail&id=${slider.id}" class="btn btn-info btn-sm" style="padding: 6px 15px; font-size: 13px; display: flex; align-items: center; justify-content: center; border-radius: 4px;">View Detail</a>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                        <!-- Pagination -->
                        <div>
                            <div class="pagination">
                                <!-- Nút Previous -->
                                <c:if test="${currentPage > 1}">
                                    <a href="SliderController?page=${currentPage - 1}" aria-label="Previous">&laquo; Previous</a>
                                </c:if>

                                <!-- Các số trang -->
                                <c:forEach begin="1" end="${totalPages}" var="i">
                                    <a href="SliderController?page=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
                                </c:forEach>

                                <!-- Nút Next -->
                                <c:if test="${currentPage < totalPages}">
                                    <a href="SliderController?page=${currentPage + 1}" aria-label="Next">Next &raquo;</a>
                                </c:if>
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
    </body>
</html>