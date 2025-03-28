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
                                    <li><a href="MarketingDashboardController">Home</a></li>
                                    <li><a href="MarketingPostController?service=listAllBlogs">Post List</a></li>
                                    <li><a href="SliderController" class="active">Slider List</a></li>
                                    <li><a href="CustomerController">Customer List</a></li>
                                    <li><a href="MarketingProductController">Product List</a></li>
                                    <li><a href="MaketingFeedBackController?service=listAllfeedBack">FeedBack List</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div>
                                <form action="${pageContext.request.contextPath}/SliderController" method="post" class="search-form" style="display: flex; gap: 10px; align-items: center;">
                                    <input type="text" name="query" placeholder="Search by Title or Backlinks" class="form-control d-inline-block" />
                                    <button type="submit" name="action" value="search" class="btn btn-warning" style="padding: 6px 15px; font-size: 13px; display: flex; align-items: center; justify-content: center; border-radius: 4px;">Search</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!--/header-bottom-->
        </header><!--/header-->

        <section id="settings-section">
            <style>
                /* CSS chỉ áp dụng cho phần này */
                #settings-section .form-section {
                    display: flex;
                    justify-content: flex-start;
                    gap: 10px;
                    align-items: center;
                    margin-bottom: 20px;
                }

                #settings-section form {
                    display: flex;
                    align-items: center;
                    justify-content: flex-start;
                    gap: 10px;
                    margin-bottom: 20px;
                }

                #settings-section form input, #settings-section form select {
                    padding: 10px;
                    font-size: 16px;
                    border: 1px solid #ccc;
                    border-radius: 4px;
                    width: 200px;
                    height: 40px;
                }

                #settings-section button, #settings-section .btn {
                    padding: 10px 20px;
                    font-size: 16px;
                    border: none;
                    border-radius: 4px;
                    cursor: pointer;
                    height: 35px;
                    width: 100px;
                    text-align: center;
                }

                #settings-section button:hover, #settings-section .btn:hover {
                    opacity: 0.8;
                }

                #settings-section .pagination {
                    text-align: center;
                    margin-top: 20px;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    width: 100%;
                }

                #settings-section .pagination a {
                    margin: 0 5px;
                    padding: 8px 16px;
                    background-color: #f39c12;
                    color: white;
                    text-decoration: none;
                    border-radius: 4px;
                    transition: background-color 0.3s;
                }

                #settings-section .pagination a:hover {
                    background-color: #e67e22;
                }

                #settings-section table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 30px;
                }

                #settings-section table th, #settings-section table td {
                    padding: 12px;
                    text-align: left;
                    border: 1px solid #ddd;
                    background-color: #f9f9f9;
                }

                #settings-section table tr:nth-child(even) {
                    background-color: #f2f2f2;
                }

                #settings-section table tr:hover {
                    background-color: #f0e68c;
                }

                @media screen and (max-width: 768px) {
                    #settings-section form {
                        flex-direction: column;
                        align-items: flex-start;
                    }

                    #settings-section form input, #settings-section form select {
                        width: 100%;
                    }

                    #settings-section form button {
                        width: 100%;
                        margin-top: 10px;
                    }
                }
            </style>

            <div class="container">
                <div class="row">
                    <div class="col-sm-12">
                        <h2>Slider List</h2>
                        <c:if test="${not empty message}">
                            <div class="alert alert-${messageType}" style="margin-bottom: 20px; font-size: 16px; padding: 15px; border-radius: 5px;">
                                ${message}
                            </div>
                        </c:if>
                        <!-- Search and Filter Form -->
                        <div class="form-section">
                            <form action="${pageContext.request.contextPath}/SliderController" method="get" class="filter-form">
                                <select name="status" style="width: 130px; font-size: 13px; padding: 6px; margin-right: 8px; border-radius: 4px; border: 1px solid #ccc; height: 35px">
                                    <option value="">All</option>
                                    <option value="0">Active</option>
                                    <option value="1">Inactive</option>
                                </select>
                                <button type="submit" class="btn btn-warning" style="padding: 6px 15px; font-size: 13px; display: flex; align-items: center; justify-content: center; border-radius: 4px;">Filter</button>
                                <div class="add-setting-button">
                                    <a href="${pageContext.request.contextPath}/SliderController?service=addSlider" class="btn btn-success" style="padding: 6px 15px; font-size: 13px; display: flex; align-items: center; justify-content: center; border-radius: 4px;">Add Slider</a>
                                </div>
                            </form>
                        </div>

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
                                                        <button type="submit" name="action" value="toggleStatus" class="btn btn-success" style="padding: 6px 15px; font-size: 13px; display: flex; align-items: center; justify-content: center; border-radius: 4px;">Activate</button>
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
                        <div class="pagination" style="text-align: center; margin-top: 20px; display: flex; justify-content: center; align-items: center; width: 100%;">
                            <c:if test="${currentPage > 1}">
                                <a href="SliderController?page=${currentPage - 1}&status=${param.status}" aria-label="Previous" style="margin: 0 5px; padding: 8px 16px; background-color: #f39c12; color: white; text-decoration: none; border-radius: 4px; transition: background-color 0.3s;">&laquo; Previous</a>
                            </c:if>

                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <a href="SliderController?page=${i}&status=${param.status}" class="${i == currentPage ? 'active' : ''}" style="margin: 0 5px; padding: 8px 16px; background-color: #f39c12; color: white; text-decoration: none; border-radius: 4px; transition: background-color 0.3s;">${i}</a>
                            </c:forEach>

                            <c:if test="${currentPage < totalPages}">
                                <a href="SliderController?page=${currentPage + 1}&status=${param.status}" aria-label="Next" style="margin: 0 5px; padding: 8px 16px; background-color: #f39c12; color: white; text-decoration: none; border-radius: 4px; transition: background-color 0.3s;">Next &raquo;</a>
                            </c:if>
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