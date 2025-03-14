<%-- 
    Document   : my-orders
    Created on : Mar 11, 2025, 8:41:15 AM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.User"%>
<!DOCTYPE html>
<html lang="vi">

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
        <title>Quản lý đơn hàng</title>
        <style>
            /* Chỉ áp dụng trong vùng #my-orders-section */
            #my-orders-section {
                background: #f9f9f9; /* Nền xám nhạt toàn khu vực */
                padding: 15px;
                border-radius: 4px;
            }

            /* Thanh tabs */
            #my-orders-section .my-order-tabs button {
                padding: 8px 12px;
                border: 1px solid #ddd;
                background: #fff;
                margin-right: 5px;
                cursor: pointer;
                border-radius: 4px;
            }
            #my-orders-section .my-order-tabs button.active {
                border-bottom: 2px solid #e74c3c;
                font-weight: bold;
            }

            /* Card đơn hàng */
            #my-orders-section .my-order-card {
                background: #fff;
                border: 1px solid #ddd;
                border-radius: 5px;
                margin-bottom: 20px;
                padding: 15px;
            }

            /* Header đơn hàng */
            #my-orders-section .order-header {
                display: flex;
                justify-content: space-between;
                margin-bottom: 10px;
            }
            #my-orders-section .shop-info {
                display: flex;
                align-items: center;
                gap: 10px;
            }
            #my-orders-section .shop-name {
                font-weight: bold;
            }
            #my-orders-section .order-status {
                font-weight: bold;
                color: #e74c3c;
            }
            #my-orders-section .order-status.completed {
                color: #27ae60;
            }
            #my-orders-section .order-status.canceled {
                color: #e74c3c;
            }

            /* Body đơn hàng */
            #my-orders-section .order-body {
                display: flex;
                gap: 15px;
                margin-bottom: 10px;
            }
            #my-orders-section .product-image img {
                width: 80px;
                height: 80px;
                object-fit: cover;
            }
            #my-orders-section .product-info {
                flex: 1;
            }
            #my-orders-section .product-name {
                font-weight: bold;
                margin-bottom: 5px;
            }
            #my-orders-section .product-price {
                color: #e67e22;
                margin-bottom: 5px;
            }
            #my-orders-section .product-quantity {
                font-size: 14px;
                color: #777;
            }

            /* Footer đơn hàng */
            #my-orders-section .order-footer {
                display: flex;
                align-items: center;
                justify-content: flex-end;
                gap: 10px;
            }
            #my-orders-section .total-label {
                font-weight: bold;
            }
            #my-orders-section .total-price {
                font-weight: bold;
                color: #e67e22;
            }
            #my-orders-section button {
                padding: 8px 12px;
                border: 1px solid #ddd;
                background: #fff;
                border-radius: 4px;
                cursor: pointer;
            }
            #my-orders-section button:hover {
                background: #f3f3f3;
            }
            #my-orders-section .btn-link {
                color: #3498db;
                text-decoration: none;
            }
            #my-orders-section .btn-link:hover {
                text-decoration: underline;
            }


        </style>
    </head>
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
                                <a href="HomePageController"><img src="images/home/logo.png" alt="" /></a>
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
                                    <li><a href="${pageContext.request.contextPath}/UserProfileServlet"><i class="fa fa-user"></i> Account</a></li>
                                    <!--                                    <li><a href="#"><i class="fa fa-star"></i> Wishlist</a></li>
                                    
                                                                        <li><a href="checkout.jsp"><i class="fa fa-crosshairs"></i> Checkout</a></li>-->
                                    <li><a href="CustomerOrderController"><i class="fa fa-shopping-cart"></i> My Orders</a></li>
                                    <li><a href="${pageContext.request.contextPath}/CartURL"><i class="fa fa-shopping-cart"></i> Cart</a></li>
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
                                    <li><a href="HomePageController" class="active">Home</a></li>
                                    <li class="dropdown"><a href="#">Shop<i class="fa fa-angle-down"></i></a>
                                        <ul role="menu" class="sub-menu">
                                            <li><a href="ProductController">Products</a></li>
                                            <li><a href="CartURL?service=checkOut">Checkout</a></li> 
                                            <li><a href="CartURL">Cart</a></li> 
                                        </ul>
                                    </li> 
                                    <li class="dropdown"><a href="BlogURL?service=listAllBlogs">Blog<i class="fa fa-angle-down"></i></a>
                                        <ul role="menu" class="sub-menu">
                                            <li><a href="BlogURL?service=listAllBlogs">Blog List</a></li>
                                        </ul>
                                    </li> 
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
        <section id="my-orders-section" class="container" style="margin-top: 30px;">
            <!-- Thanh tab -->
            <div class="my-order-tabs row" style="margin-bottom: 20px;">
                <div class="col-sm-12">
                    <button class="active">Tất cả</button>
                    <button>Chờ thanh toán</button>
                    <button>Đang giao hàng</button>
                    <button>Đã giao hàng</button>
                    <button>Hủy</button>
                    <button>Trả hàng/Hoàn tiền</button>
                </div>
            </div>

            <!-- Đơn hàng 1 -->
            <div class="my-order-card">
                <div class="order-header">
                    <div class="shop-info">
                        <span class="shop-name">Ladies shoe store</span>
                        <a href="#">Xem Shop</a>
                    </div>
                    <div class="order-status canceled">ĐÃ HỦY</div>
                </div>
                <div class="order-body">
                    <div class="product-image">
                        <img src="https://via.placeholder.com/80x80?text=Shoes" alt="Product Image">
                    </div>
                    <div class="product-info">
                        <div class="product-name">
                            Giày Sandal Dễ Dây Retro Nữ Mùa Hè 2024 ...
                        </div>
                        <div class="product-price">95.015₫</div>
                        <div class="product-quantity">x1</div>
                    </div>
                </div>
                <div class="order-footer">
                    <span class="total-label">Tổng tiền:</span>
                    <span class="total-price">95.015₫</span>
                    <button>Mua Lại</button>
                    <a href="#" class="btn-link">Xem Chi Tiết Hủy Đơn</a>
                </div>
            </div>

            <!-- Đơn hàng 2 -->
            <div class="my-order-card">
                <div class="order-header">
                    <div class="shop-info">
                        <span class="shop-name">Tổng kho sỉ Dũng đẹp</span>
                        <a href="#">Xem Shop</a>
                    </div>
                    <div class="order-status completed">HOÀN THÀNH</div>
                </div>
                <div class="order-body">
                    <div class="product-image">
                        <img src="https://via.placeholder.com/80x80?text=Boxes" alt="Product Image">
                    </div>
                    <div class="product-info">
                        <div class="product-name">
                            Combo 6 túi giấy T3 1g? TGi1 bao ở, lót đựng cho nhà giày...
                        </div>
                        <div class="product-price">35.000₫</div>
                        <div class="product-quantity">x1</div>
                    </div>
                </div>
                <div class="order-footer">
                    <span class="total-label">Tổng tiền:</span>
                    <span class="total-price">35.000₫</span>
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
                        <p class="pull-left">Copyright © 2013s E-SHOPPER Inc. All rights reserved.</p>
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
        <script src="js/cart.js"></script>
    </body>
</html>



