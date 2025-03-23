<%-- 
    Document   : checkout
    Created on : Jan 18, 2025, 1:13:09 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.List,entity.Cart,entity.CartItem,entity.Product,jakarta.servlet.http.HttpSession,entity.User,entity.ProductVariant,entity.Address,entity.Storage,entity.Color" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Checkout | E-Shopper</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <link href="css/prettyPhoto.css" rel="stylesheet">
        <link href="css/price-range.css" rel="stylesheet">
        <link href="css/animate.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        <link href="css/responsive.css" rel="stylesheet">
        <link href="css/cart.css" rel="stylesheet">
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
    <style>

        /* General styles for the form */
        .shipping-address {
            margin-bottom: 20px;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
            max-width: 600px;
            margin: 0 auto;
            font-family: Arial, sans-serif;
        }

        h3 {
            text-align: center;
        }

        #currentAddress p {
            margin: 0;
            font-size: 16px;
            color: #333;
        }

        #currentAddress {
            margin-bottom: 20px;
        }

        #addressOptions {
            margin-top: 20px;
        }

        #defaultAddress input {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border-radius: 4px;
            border: 1px solid #ccc;
        }

        #newAddress select, #newAddress input {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border-radius: 4px;
            border: 1px solid #ccc;
            margin-bottom: 10px;
        }

        #changeAddressBtn, #confirmAddressBtn {
            display: inline-block;
            margin-top: 10px;
            padding: 8px 16px;
            background-color: #ff7f00;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        #changeAddressBtn:hover, #confirmAddressBtn:hover {
            background-color: #e67000;
        }

        .submit-container {
            text-align: center;
            margin-top: 30px;
        }

        .submit-btn {
            background-color: #4CAF50;
            color: white;
            padding: 15px 32px;
            text-align: center;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
            border: none;
        }

        .submit-btn:hover {
            background-color: #45a049;
        }


    </style>

    <body>
        <header id="header"><!--header-->
            <div class="header_top"><!--header_top-->
                <div class="container">
                    <div class="row">
                        <div class="col-sm-6">
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
                                    <li><a href="#"><i class="fa fa-user"></i> Account</a></li>
                                    <li><a href="${pageContext.request.contextPath}/CartURL"><i class="fa fa-shopping-cart"></i> Cart</a></li>
                                    <li><a href="CustomerOrderController"><i class="fa fa-shopping-cart"></i> My Orders</a></li>
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
                                    <li><a href="HomePageController">Home</a></li>
                                    <li class="dropdown"><a href="#">Shop<i class="fa fa-angle-down"></i></a>
                                        <ul role="menu" class="sub-menu">
                                            <li><a href="ProductController">Products</a></li>
                                            <li><a href="CartURL?service=checkOut" class="active">Checkout</a></li> 
                                            <li><a href="CartURL">Cart</a></li> 
                                        </ul>
                                    </li> 
                                    <li class="dropdown"><a href="BlogURL?service=listAllBlogs">Blog<i class="fa fa-angle-down"></i></a>
                                        <ul role="menu" class="sub-menu">
                                            <li><a href="BlogURL?service=listAllBlogs">Blog List</a></li>
                                        </ul>
                                    </li> 
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
        <section id="checkout_section">
            <div class="container" style="max-width: 800px; margin: auto;">
                <div class="shipping-address">
                    <h3 style="font-size: 24px; font-weight: bold; color: #333;">Shipping Information</h3>
                    <form id="orderForm" action="OrderController" method="POST">
                        <input type="hidden" name="service" value="createOrder">
                        <label for="addressSelect" style="font-weight: bold;">Select Shipping Address:</label>
                        <select name="addressSelect" id="addressSelect" required style="width: 100%; padding: 8px; margin-bottom: 10px;">
                            <% 
                                List<Address> addresses = (List<Address>) request.getAttribute("userAddresses"); 
                            %>
                            <% if (addresses != null && !addresses.isEmpty()) { %>
                            <% for (Address addr : addresses) { %>
                            <option value="<%= addr.getId() %>">
                                <%= addr.getAddress() %>, <%= addr.getDistrict() %>, <%= addr.getCity() %>
                            </option>
                            <% } %>
                            <% } else { %>
                            <option value="">No address available. Please add a new one!</option>
                            <% } %>
                        </select>

                        <h4 style="font-size: 20px; font-weight: bold; color: #555;">Recipient Information</h4>
                        <input type="text" name="newFullName" placeholder="Full Name *" required style="width: 100%; padding: 8px; margin-bottom: 10px;">
                        <input type="text" name="newPhone" placeholder="Phone Number *" required style="width: 100%; padding: 8px; margin-bottom: 10px;">

                        <div class="review-payment">
                            <h3 style="font-size: 22px; font-weight: bold; color: #333;">Review & Payment</h3>
                        </div>

                        <div class="cart_info">
                            <% 
                                List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems"); 
                                if (cartItems != null && !cartItems.isEmpty()) { 
                                    for (CartItem item : cartItems) {
                                        Product product = item.getProduct();
                                        ProductVariant productVariant = item.getProductVariant();
                                        Color color = item.getColor();
                                        Storage storage = item.getStorage();
                            %>
                            <div style="display: flex; align-items: center; margin-bottom: 15px; border-bottom: 1px solid #ddd; padding-bottom: 15px;">
                                <img src="<%= product.getImageURL() %>" alt="Product" style="width: 80px; height: 80px; border-radius: 5px; margin-right: 15px;">
                                <div>
                                    <p style="margin: 0; font-weight: bold; font-size: 16px; color: #333;"><%= product.getName() %></p>
                                    <p style="margin: 2px 0; color: #666; font-size: 14px;">
                                        Color: <%= item.getColor().getColorName() %> / Storage: <%= item.getStorage().getCapacity() %>GB
                                    </p>
                                    <p style="margin: 2px 0; font-weight: bold; color: #e74c3c;">
                                        ₫<%= String.format("%,.0f", item.getPrice()) %> x <%= item.getQuantity() %>
                                    </p>
                                    <p style="margin: 2px 0; font-weight: bold; color: #e74c3c;">
                                        Total: ₫<%= String.format("%,.0f", item.getTotalPrice().doubleValue()) %>
                                    </p>
                                </div>
                            </div>
                            <% } } else { %>
                            <p style="text-align: center; font-size: 16px; color: #777;">Your cart is empty!</p>
                            <% } %>
                        </div>


                        <div class="payment-options" style="margin-top: 10px;">
                            <h3 style="font-size: 18px; font-weight: bold;">Select Payment Method</h3>
                            <label><input type="radio" name="paymentMethod" value="1" required> Cash on Delivery (COD)</label><br>
                            <label><input type="radio" name="paymentMethod" value="2"> Pay with VNPay</label><br>
                        </div>
                        <div class="submit-container" style="margin-top: 10px; text-align: center;">
                            <button type="submit" class="submit-btn" 
                                    style="width: 100%; background-color: #e67e22; color: white; padding: 10px; border: none; cursor: pointer; font-size: 16px;">
                                Place Order
                            </button>
                        </div>
                    </form>            
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
                        <p class="pull-left">Copyright © 2013 E-SHOPPER Inc. All rights reserved.</p>
                        <p class="pull-right">Designed by <span><a target="_blank" href="http://www.themeum.com">Themeum</a></span></p>
                    </div>
                </div>
            </div>

        </footer><!--/Footer-->



        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
        <script src="js/updateQuantity.js"></script>
        <!--        <script src="js/checkOut.js"></script>-->
    </body>
</html>