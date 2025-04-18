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

            .pagination {
                margin-top: 20px;
                text-align: center;
            }
            .pagination a {
                margin: 0 5px;
                padding: 5px 10px;
                text-decoration: none;
                border: 1px solid #ddd;
                color: #007bff;
                border-radius: 3px;
            }
            .pagination a.active {
                background-color: #007bff;
                color: white;
                border-color: #007bff;
            }
            .pagination a:hover {
                background-color: #0056b3;
                color: white;
            }
            .brand-filter {
                margin-bottom: 20px;
            }

            .brand-list {
                display: flex;
                justify-content: center;
                flex-wrap: wrap;
                gap: 10px;
            }

            .brand-list a {
                padding: 10px 15px;
                font-size: 15px;
                text-decoration: none;
                border-radius: 5px;
                font-weight: bold;
                text-align: center;
                width: 100px; /* Đảm bảo tất cả có cùng chiều rộng */
                display: inline-block;
                background-color: orange;
                color: white;
                border: none;
            }

            .brand-list a:hover {
                background-color: #ff8c00;
                color: white;
            }


        </style>
    </head><!--/head-->
</head><!--/head-->
<% if (session.getAttribute("cartMessage") != null) {%>
<script type="text/javascript">
    alert("<%= session.getAttribute("cartMessage")%>");
</script>
<%
    session.removeAttribute("cartMessage");
%>
<% } %>

<body>
    <header id="header"><!--header-->
            <div class="header_top"><!--header_top-->
                <div class="container">
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="contactinfo">
                                <ul class="nav nav-pills">
                                    <li><a href="#"><i class="fa fa-phone"></i> +84 373 335 357</a></li>
                                    <li><a href="#"><i class="fa fa-envelope"></i> haiductran712@gmail.com</a></li>
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
                                <a href="HomePageController">
                                    <a href="HomePageController"><img src="images/home/logo.png" alt="E-Shopper Logo" /></a>
                                </a>
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

                                    <li><a href="${pageContext.request.contextPath}/CartURL"><i class="fa fa-shopping-cart"></i> Cart</a></li>    
                                    <li><a href="CustomerOrderController"><i class="fa fa-shopping-cart"></i> My Orders</a></li>

                                    <!-- Dropdown for User -->
                                    <li class="dropdown" style="position: relative">
                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="font-weight: bold">
                                            <img src="UserAvatarController" alt="Profile Image" class="img-thumbnail" style="height: 25px; width: 25px; border-radius: 50%; border: none;"/>
                                            Hello, <%=user.getEmail()%> <b class="caret"></b>
                                        </a>
                                        <ul class="dropdown-menu" style="right: 0; left: auto;">
                                            <li><a href="${pageContext.request.contextPath}/UserProfileServlet"><i class="fa fa-user"></i> Account</a></li>
                                            <li><a href="CustomerPointController"><i class="fa fa-star"></i> My Points</a></li>
                                            <li class="divider"></li>
                                            <li><a href="${pageContext.request.contextPath}/LogoutController"><i class="fa fa-power-off"></i> Logout</a></li>
                                        </ul>
                                    </li>


                                    <%
                                    } else {
                                    %>
                                    <li><a href="${pageContext.request.contextPath}/LoginController"><i class="fa fa-lock"></i> Login</a></li>
                                        <% }%>
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
                                    <li><a href="HomePageController" class="active">Home</a></li>
                                    <li><a href="ProductController">Shop</a>
                                        <!--                                        <ul role="menu" class="sub-menu">
                                                                                    <li><a href="ProductController">Products</a></li>
                                                                                    <li><a href="CartURL?service=checkOut">Checkout</a></li> 
                                                                                    <li><a href="CartURL?service=showCart">Cart</a></li> 
                                                                                </ul>-->
                                    </li> 
                                    <li><a href="BlogURL?service=listAllBlogs">Blog</a></li>
                                    <li><a href="#about-us">About Us</a></li>
                                    <li><a href="ContactForward">Contact Us</a></li>
                                    <!--                                    <li><a href="404.html">404</a></li>
                                                                        <li><a href="contact-us.html">Contact</a></li>-->
                                </ul>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="pull-right">
                                <form action="${pageContext.request.contextPath}/ProductController" method="get">
                                    <input type="text" name="search" value="${param.search}" />

                                    <button type="submit" class="btn btn-default"><i class="fa fa-search"></i></button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!--/header-bottom-->
        </header><!--/header-->

    <section id="advertisement">
        <div class="container">
            <img src="images/shop/advertisement.jpg" alt="" />
        </div>
    </section>

    <section>
        <div class="container">
            <div class="row">
                <div class="col-sm-3">
                    <div class="left-sidebar">

                        <div class="filter-box">
                            <h2>Filter Products</h2>
                            <form action="${pageContext.request.contextPath}/ProductController" method="get">

                                <label for="brandID">Brand</label>
                                <select id="brandID" name="brandID" class="form-control">
                                    <option value="0">All Brands</option>
                                    <c:forEach var="brand" items="${brands}">
                                        <option value="${brand.id}" ${brandID == brand.id ? 'selected' : ''}>${brand.name}</option>
                                    </c:forEach>
                                </select>

                                <!-- Lọc theo hệ điều hành -->
                                <label for="os">Operating System</label>
                                <select id="os" name="os" class="form-control">
                                    <option value="">All</option>
                                    <c:forEach var="osItem" items="${osList}">
                                        <option value="${osItem}" ${os == osItem ? 'selected' : ''}>${osItem}</option>
                                    </c:forEach>
                                </select>

                                <!-- Lọc theo kết nối -->
                                <label for="connectivity">Connectivity</label>
                                <select id="connectivity" name="connectivity" class="form-control">
                                    <option value="">All</option>
                                    <c:forEach var="connect" items="${connectivityList}">
                                        <option value="${connect}" ${connectivity == connect ? 'selected' : ''}>${connect}</option>
                                    </c:forEach>
                                </select>


                                <label for="batteryCapacity">Battery Capacity (mAh)</label>
                                <select id="batteryCapacity" name="batteryCapacity" class="form-control">
                                    <option value="0">All</option>
                                    <c:forEach var="capacity" items="${batteryCapacityList}">
                                        <option value="${capacity}" ${batteryCapacity == capacity ? 'selected' : ''}>${capacity} mAh</option>
                                    </c:forEach>
                                </select>

                                <!-- Lọc theo Screen Size -->
                                <label for="screenSize">Screen Size (inches)</label>
                                <select id="screenSize" name="screenSize" class="form-control">
                                    <option value="0">All</option>
                                    <c:forEach var="size" items="${screenSizeList}">
                                        <option value="${size}" ${screenSize == size ? 'selected' : ''}>${size} inches</option>
                                    </c:forEach>
                                </select>
                                
                                        <label for="minPrice">Min Price</label>
                                        <input type="number" id="minPrice" name="minPrice" value="${param.minPrice}" min="0" max="50000000" step="500000" class="form-control">

                                        <label for="maxPrice">Max Price </label>
                                        <input type="number" id="maxPrice" name="maxPrice" value="${param.maxPrice}" min="0" max="50000000" step="500000" class="form-control">

                                <!-- Nút Lọc -->
                                <button type="submit" class="btn btn-primary" style="margin-top:10px;">Filter</button>
                            </form>
                        </div>



                        <br>
                        <div class="latest-products">
                            <c:if test="${not empty latestProduct}">
                                <div class="latest-product">
                                    <h2>Newest Product</h2>
                                    <div class="product-item">
                                        <a href="${pageContext.request.contextPath}/ProductDetailController?id=${latestProduct.id}">
                                            <img src="${latestProduct.imageURL}" alt="${latestProduct.name}" style="width: 100%; height: auto;">
                                            <h3 style="text-align: center;">${latestProduct.name}</h3>
                                            <p style="text-align: center;">${latestProduct.description}</p>
                                           </a>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="col-sm-9 padding-right">
                    <div class="brand-filter text-center">

                        <div class="brand-list">
                            <a href="${pageContext.request.contextPath}/ProductController?brandID=0" class="btn btn-primary">All Brands</a>
                            <c:forEach var="brand" items="${brands}">
                                <a href="${pageContext.request.contextPath}/ProductController?brandID=${brand.id}" class="btn btn-primary">${brand.name}</a>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="features_items"><!--features_items-->
                        <h2 class="title text-center">Features Items</h2>
                        <c:forEach var="product" items="${productList}">
                            <div class="col-sm-4">
                                <div class="product-image-wrapper">
                                    <div class="single-products">
                                        <div class="productinfo text-center">
                                            <img href="ProductDetailController?id=${product.id}" src="${product.imageURL}" alt="${product.name}" />
                                            <p>${product.name}</p>
                                            <c:set var="minPriceKey" value="minPrice_${product.id}"   />
                                            <h2>₫<c:out value="${requestScope[minPriceKey]}" /></h2>

                                            <a href="ProductDetailController?id=${product.id}" class="btn btn-default add-to-cart">Detail</a>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                    </div>

                    <div class ="text-center">
                        <div class="pagination">
                            <c:if test="${currentPage > 1}">
                                <a href="?page=${currentPage-1}&brandID=${brandID}&os=${os}&connectivity=${connectivity}&ram=${ram}&screenType=${screenType}&batteryCapacity=${batteryCapacity}&screenSize=${screenSize}&minPrice=${minPrice}&maxPrice=${maxPrice}" class="pre">Previous</a>
                            </c:if>

                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <a href="?page=${i}&brandID=${brandID}&os=${os}&connectivity=${connectivity}&ram=${ram}&screenType=${screenType}&batteryCapacity=${batteryCapacity}&screenSize=${screenSize}&minPrice=${minPrice}&maxPrice=${maxPrice}" class="${i == currentPage ? 'active' : ''}">${i}</a>
                            </c:forEach>

                            <c:if test="${currentPage < totalPages}">
                                <a href="?page=${currentPage+1}&brandID=${brandID}&os=${os}&connectivity=${connectivity}&ram=${ram}&screenType=${screenType}&batteryCapacity=${batteryCapacity}&screenSize=${screenSize}&minPrice=${minPrice}&maxPrice=${maxPrice}" class="next">Next</a>
                            </c:if>
                        </div>

                    </div>
                    <!--                            </div>-->
                </div><!--features_items-->
            </div>
        </div>
    </div>
</section>
<br>
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
</body>
</html>
