<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="entity.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="java.util.List,entity.Blog,jakarta.servlet.http.HttpSession,entity.User,model.DAOBlog" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>

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
                        <!--                        <div class="col-sm-3">
                                                    <div class="pull-right">
                                                        <form action="${pageContext.request.contextPath}/ProductController" method="get">
                                                            <input type="text" name="search" value="${param.search}" />
                        
                                                            <button type="submit" class="btn btn-default"><i class="fa fa-search"></i></button>
                                                        </form>
                                                    </div>
                                                </div>-->
                    </div>
                </div>
            </div><!--/header-bottom-->
        </header><!--/header-->

        <section id="slider">
            <div class="container">
                <div class="row">
                    <div class="col-sm-12">
                        <div id="slider-carousel" class="carousel slide" data-ride="carousel">
                            <ol class="carousel-indicators">
                                <li data-target="#slider-carousel" data-slide-to="0" class="active"></li>
                                <li data-target="#slider-carousel" data-slide-to="1"></li>
                                <li data-target="#slider-carousel" data-slide-to="2"></li>
                            </ol>

                            <div class="carousel-inner">
                                <!-- Hiển thị 3 bài viết đầu tiên -->
                                <c:forEach var="blog" items="${latestBlogs}" varStatus="status">
                                    <div class="item ${status.index == 0 ? 'active' : ''}">
                                        <div class="col-sm-6">
                                            <h1><span>E</span>-SHOPPER</h1>
                                            <h2>${blog.title}</h2>


                                            <a href="${blog.backlinks}" class="btn btn-warning">
                                                Read More
                                            </a>


                                        </div>
                                        <div class="col-sm-6">
                                            <img style="height: 400px; width: auto" src="${blog.imageURL}" class="girl img-responsive" alt="" />

                                        </div>
                                    </div>
                                </c:forEach>
                            </div>

                            <a href="#slider-carousel" class="left control-carousel hidden-xs" data-slide="prev">
                                <i class="fa fa-angle-left"></i>
                            </a>
                            <a href="#slider-carousel" class="right control-carousel hidden-xs" data-slide="next">
                                <i class="fa fa-angle-right"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </section>


        <section>
            <div class="container">
                <div class="row">
                    <div class="col-sm-3">
                        <div class="left-sidebar">
                            <div class="brands_products">
                                <h2>Brands</h2> 
                                <div class="brands-name">
                                    <ul class="nav nav-pills nav-stacked">
                                        <li> 
                                            <a href="${pageContext.request.contextPath}/ProductController?brandID=0">All Brands</a> 
                                        </li> 
                                        <c:forEach var="brand" items="${brands}">
                                            <li> 
                                                <a href="${pageContext.request.contextPath}/ProductController?brandID=${brand.id}"> ${brand.name} </a>
                                            </li> 
                                        </c:forEach> 
                                    </ul> 
                                </div>
                            </div>

                            <div class="price-range">
                                <h2>Filter by Price</h2>
                                <div class="well">
                                    <form action="${pageContext.request.contextPath}/ProductController" method="get">


                                        <label for="minPrice">Min Price:</label>
                                        <input type="number" name="minPrice" id="minPrice" class="form-control" value="" placeholder="" />

                                        <label for="maxPrice">Max Price:</label>
                                        <input type="number" name="maxPrice" id="maxPrice" class="form-control" value="" placeholder="" />


                                        <!-- Nút Lọc -->
                                        <button type="submit" class="btn btn-primary" style="margin-top:10px;">Filter</button>


                                    </form>
                                </div>
                            </div>
                            <div class="latest-products">
                                <c:if test="${not empty latestProducts}">
                                    <div class="latest-product">
                                        <h2>Newest Products</h2>
                                        <c:forEach var="product" items="${latestProducts}">
                                            <div class="product-item" style="margin-bottom: 10px;">
                                                <!-- Link bao bọc sản phẩm -->
                                                <a href="ProductDetailController?id=${product.id}">
                                                    <img src="${product.imageURL}" alt="${product.name}" style="width: 100%; height: auto;">
                                                    <h3 style="color: black;">${product.name}</h3>
                                                    <p style="color: black;">${product.description}</p>
                                                </a>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-9 padding-right">
                        <div class="features_items"><!--features_items-->
                            <h2 class="title text-center">Features Items</h2>                             
                            <c:forEach var="product" items="${products}">
                                <div class="col-sm-4">
                                    <div class="product-image-wrapper">
                                        <div class="single-products">
                                            <div class="productinfo text-center">
                                                <img src="${product.imageURL}" alt="Product Image" />
                                                <c:set var="minPriceKey" value="minPrice_${product.id}" />
                                                <h2>₫<c:out value="${requestScope[minPriceKey]}" /></h2>  <!-- Display the price here -->
                                                <p>${product.name}</p>
                                                <a href="ProductDetailController?id=${product.id}" class="btn btn-default add-to-cart">Detail</a>  
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>

                        </div><!--features_items-->

                        <!-- Phân trang -->
                        <div class="text-center">
                            <c:if test="${currentPage > 1}">
                                <a href="?page=${currentPage - 1}" class="btn btn-default">Previous</a>
                            </c:if>
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <a href="?page=${i}" class="btn btn-default">${i}</a>
                            </c:forEach>
                            <c:if test="${currentPage < totalPages}">
                                <a href="?page=${currentPage + 1}" class="btn btn-default">Next</a>
                            </c:if>
                        </div>
                        <br/>
                        <br/>
                        <div class="blog_items">
                            <h2 class="title text-center">Blogs</h2>

                            <c:forEach var="blog" items="${recentBlogs}">
                                <div class="single-blog-post" style="margin-bottom: 30px;">

                                    <div class="col-sm-4">
                                        <img src="${blog.imageURL}" alt="Blog Image" class="img-responsive" />
                                    </div>


                                    <div class="col-sm-8">
                                        <h3 style="margin-top: 0;">${blog.title}</h3>

                                        <p>${fn:substring(blog.content, 0, 200)}...</p>

                                        <a href="${pageContext.request.contextPath}/BlogDetailServlet?id=${blog.id}" class="btn btn-warning">
                                            Read More
                                        </a>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <br>
        <br>
        <br>
        <section id="about-us" style="padding: 50px 0; background-color: #f9f9f9;">
            <div class="container">
                <h2 class="title text-center" style="margin-bottom: 30px; font-weight: bold;">About T-Phone Store</h2>
                <div class="row">
                    <div class="col-sm-4">
                        <img src="https://i.pinimg.com/1200x/d3/c9/74/d3c974631604ec4e2f7bc746999de586.jpg" alt="About T-Phone Store" class="img-responsive" style="border-radius: 12px;">
                        <br>
                        <img src="https://www.deco-crystal.com/wp-content/uploads/2023/06/image31.jpg" alt="About T-Phone Store" class="img-responsive" style="border-radius: 12px;">
                        <br>
                        <img src="https://content.jdmagicbox.com/comp/thane/c1/022pxx22.xx22.130426170856.c4c1/catalogue/top-10-mobile-shop-kalyan-city-thane-mobile-phone-dealers-903m6.jpg" alt="About T-Phone Store" class="img-responsive" style="border-radius: 12px;">
                    </div>
                    <div class="col-sm-8">
                        <div style="font-size: 16px; line-height: 1.8; text-align: justify; color: #333;">
                            <p><strong>T-Phone Store</strong> is a trusted destination for mobile phone lovers and technology enthusiasts across Vietnam. Established with the mission to bring genuine, high-quality smartphones closer to the hands of every Vietnamese user, T-Phone Store has become more than just an online store — we are a technology partner in your everyday life.</p>

                            <p>In an ever-changing digital world, smartphones are no longer a luxury; they are a necessity. At T-Phone Store, we understand how essential it is to stay connected — not only through calls and messages but also through apps, information, creativity, and productivity. We offer a wide variety of smartphones and accessories from globally renowned brands such as Apple, Samsung, Xiaomi, Oppo, Vivo, and more — ensuring customers have access to the latest innovations at competitive prices.</p>

                            <p>What truly sets us apart is not just our products, but our service. Every member of the T-Phone Store team is passionate about technology and dedicated to delivering the best customer experience. Whether you’re making your first purchase or your fifth, we’re here to support you — from product consultation, secure ordering, fast nationwide delivery, to after-sales service and warranty support.</p>

                            <p>Our goal is not simply to sell phones. We aim to educate customers, help them make informed decisions, and ensure that every purchase adds value to their daily lives. With detailed product descriptions, honest reviews, and real-time support, we empower shoppers to confidently explore the latest in mobile tech.</p>

                            <p>We believe in transparency. Every product on our platform is authentic, sourced directly from authorized distributors, and comes with clear warranty and return policies. No hidden costs, no confusing fine print — just honest service.</p>

                            <p>We also invest in convenience. With 0% installment plans, multiple payment options, and reliable logistics partners, we make the entire shopping process simple and stress-free. Whether you’re in Hanoi, Ho Chi Minh City, or a rural area, your next smartphone is just a few clicks away.</p>

                            <p>Our long-term vision is to become the leading mobile-specialized e-commerce platform not only in Vietnam but also across Southeast Asia. To achieve this, we constantly upgrade our technology systems, expand our product portfolio, and strengthen our partnerships with manufacturers and financial services.</p>

                            <p>But at the heart of everything we do is our commitment to customers. We listen to feedback, adapt to needs, and never stop improving. Because your satisfaction isn't just important to us — it's our reason for being.</p>

                            <p>Thank you for choosing T-Phone Store. We're proud to accompany you on your digital journey — and we look forward to growing with you in a world where technology keeps moving forward.</p>

                            <p style="font-weight: bold; text-align: right; color: #222;">T-Phone Store — Empowering Your Digital Life.</p>
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


        <div id="cart-notification" class="hidden">
            <span class="checkmark">✔</span>
            <p>Product added to cart successfully!</p>
        </div>
        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
        <script src="js/cart.js"></script>
    </body>
</html>