<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.math.BigDecimal" %>

<%@page import="java.util.List,entity.Cart,entity.CartItem,entity.Product,jakarta.servlet.http.HttpSession,entity.User,entity.ProductVariant" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Cart | E-Shopper</title>
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
        .pagination-container {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }

        .pagination-btn {
            background-color: #f0f0f0;
            border: 1px solid #ddd;
            width: 40px;
            height: 40px;
            border-radius: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 0 5px;
            text-decoration: none;
            color: #333;
            font-size: 16px;
            transition: background-color 0.3s ease, color 0.3s ease;
        }

        .pagination-btn:hover {
            background-color: #007bff;
            color: white;
        }

        .active-page {
            background-color: #007bff;
            color: white;
            font-weight: bold;
        }

        .pagination-btn:focus {
            outline: none;
        }

        .pagination-btn:disabled {
            background-color: #ccc;
            color: #999;
            pointer-events: none;
        }
        .pagination-container {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }

        .pagination-btn {
            background-color: #f0f0f0;
            border: 1px solid #ddd;
            width: 30px; /* Đặt lại kích thước nhỏ hơn */
            height: 30px; /* Đặt lại kích thước nhỏ hơn */
            border-radius: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 0 5px;
            text-decoration: none;
            color: #333;
            font-size: 14px; /* Giảm kích thước chữ */
            transition: background-color 0.3s ease, color 0.3s ease;
        }

        .pagination-btn:hover {
            background-color: #ff6600; /* Màu cam khi hover */
            color: white;
        }

        .active-page {
            background-color: #ff6600; /* Màu cam khi ở trang hiện tại */
            color: white;
            font-weight: bold;
        }

        .pagination-btn:focus {
            outline: none;
        }

        .pagination-btn:disabled {
            background-color: #ccc;
            color: #999;
            pointer-events: none;
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
                                    <li><a href="UserProfileServlet"><i class="fa fa-user"></i> Account</a></li>
                                    <li><a href="#"><i class="fa fa-star"></i> Wishlist</a></li>
                                    <li><a href="OrderController"><i class="fa fa-crosshairs"></i> Checkout</a></li>
                                    <li><a href="${pageContext.request.contextPath}/CartURL"><i class="fa fa-shopping-cart"></i> Cart</a></li>
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
                                            <li><a href="CartURL?service=checkOut">Checkout</a></li> 
                                            <li><a href="CartURL" class="active">Cart</a></li> 
                                        </ul>
                                    </li> 
                                    <li class="dropdown"><a href="BlogURL?service=listAllBlogs">Blog<i class="fa fa-angle-down"></i></a>
                                        <ul role="menu" class="sub-menu">
                                            <li><a href="BlogURL?service=listAllBlogs">Blog List</a></li>
                                        </ul>
                                    </li> 
                                    <li><a href="404.html">404</a></li>
                                    <li><a href="contact-us.html">Contact</a></li>
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

        <section id="shopping_cart">
            <div class="cart-container">
                <div class="breadcrumbs">
                    <ol class="breadcrumb">
                        <li><a href="HomePageController">Home</a></li>
                        <li class="active">Shopping Cart</li>
                    </ol>
                </div>
                <div class="cart-header">
                    <h3>🛒 Your Shopping Cart</h3>
                </div>
                <div class="cart-content">
                    <form id="checkoutForm" action="CartURL" method="POST">
                        <input type="hidden" name="service" value="checkOut">
                        <table class="cart-table">
                            <thead>
                                <tr>
                                    <th><input type="checkbox" id="select_all"></th>
                                    <th>Item</th>
                                    <th>Price</th>
                                    <th>Quantity</th>
                                    <th>Total</th>
                                    <th>Remove</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% 
                                    List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
                                    int currentPage = (int) request.getAttribute("currentPage");
                                    int totalPages = (int) request.getAttribute("totalPages");
                                    if (cartItems != null && !cartItems.isEmpty()) { 
                                        for (CartItem item : cartItems) {
                                            Product product = item.getProduct();
                                            ProductVariant productVariant = item.getProductVariant();
                                %>
                                <tr class="cart-row" id="row_<%= item.getCartItemID() %>">
                                    <td><input type="checkbox" class="product-checkbox" name="selectedItems" value="<%= item.getCartItemID() %>"></td>
                                    <td class="cart-product">
                                        <div class="cart-product-content">
                                            <img src="<%= product.getImageURL() %>" class="product-img">
                                            <div class="product-info">
                                                <span class="product-name" style="display: block; font-size: 16px; font-weight: bold; text-align: left;">
                                                    <%= product.getName() %>
                                                </span>
                                                <br>
                                                <span class="product-variant" style="display: block; font-size: 14px; text-align: left; margin-top: -20px;">
                                                    <%= productVariant.getColor() %> / <%= productVariant.getStorage() %>GB
                                                </span>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="cart-price">₫<%= String.format("%,.0f", productVariant.getPrice()) %></td>
                                    <td class="cart-quantity">
                                        <button type="button" class="quantity-btn minus-btn" onclick="updateQuantity(-1, <%= item.getCartItemID() %>, event)">−</button>
                                        <input class="cart-quantity-input" id="quantity_<%= item.getCartItemID() %>" type="number" value="<%= item.getQuantity() %>" min="1" max ="<%=productVariant.getStock()%>" readonly>
                                        <button type="button" class="quantity-btn plus-btn" onclick="updateQuantity(+1, <%= item.getCartItemID() %>, event)">+</button>
                                    </td>
                                    <td class="cart-total-price" id="total_<%= item.getCartItemID() %>">
                                        ₫<%= String.format("%,.0f", item.getTotalPrice().doubleValue()) %>
                                    </td>
                                    <td class="cart-action">
                                        <a class="cart-delete" href="javascript:void(0)" onclick="removeItem(<%= item.getCartItemID() %>, event)">🗑️</a>
                                    </td>
                            <input type="hidden" name="cartItemId_<%= item.getCartItemID() %>" value="<%= item.getCartItemID() %>">

                            <input type="hidden" name="quantity_<%= item.getCartItemID() %>" value="<%= item.getQuantity() %>">
                            </tr>
                            <% } } else { %>
                            <tr><td colspan="6">Your cart is empty!</td></tr>
                            <% } %>
                            </tbody>
                        </table>

                        <!-- Pagination -->
                        <div class="pagination-container">
                            <% if (totalPages > 1) { %>
                            <% for (int i = 1; i <= totalPages; i++) { %>
                            <a href="CartURL?service=showCart&page=<%= i %>" class="pagination-btn <%= (i == currentPage) ? "active-page" : "" %>">
                                <%= i %>
                            </a>
                            <% } %>
                            <% } %>
                        </div>

                        <div class="cart-footer">
                            <div class="cart-options">
                                <label><input type="checkbox" id="select_all_footer"> Select All</label>
                            </div>
                            <span id="total-price"> Total Price: ₫<%= String.format("%,.0f", ((BigDecimal) request.getAttribute("totalOrderPrice")).doubleValue()) %>
                            </span>
                            <button class="btn-cart-checkout" type="submit">Checkout</button>
                        </div>
                    </form>
                </div>
            </div>
        </section>





        <div class="recommended-products" style="margin-top: 20px;">
            <div class="header" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px;">
                <h2 style="font-size: 22px; font-weight: bold;">You May Also Like</h2>
                <a href="ProductController" class="view-all" style="color: #e67e22; font-weight: bold; text-decoration: none;">View ALL ></a>
            </div>

            <div class="product-grid" style="display: flex; flex-wrap: wrap; gap: 15px; justify-content: center;">
                <c:forEach var="product" items="${listpro}" varStatus="status">
                    <div class="product-item" style="width: 220px; background: white; padding: 12px; border-radius: 8px; box-shadow: 0px 2px 6px rgba(0,0,0,0.1); text-align: center;">
                        <div class="product-image" style="width: 100%; height: 200px; display: flex; align-items: center; justify-content: center; overflow: hidden;">
                            <img src="${product.imageURL}" alt="${product.name}" style="width: 100%; height: 100%; object-fit: contain;">
                        </div>
                        <div class="product-info" style="margin-top: 10px;">
                            <p class="product-name" style="font-weight: bold; font-size: 16px; color: #333; margin-bottom: 5px;">${product.name}</p>
                            <p class="product-price" style="font-weight: bold; color: #e74c3c; font-size: 16px; margin-bottom: 10px;">₫${String.format("%,.0f", product.variantPrice)}</p>
                            <a href="javascript:void(0);" onclick="addToCart(${product.id})"
                               style="display: block; background-color: #e67e22; color: white; padding: 8px; border-radius: 5px; text-decoration: none; font-weight: bold;">
                                <i class="fa fa-shopping-cart"></i> Add to Cart
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>


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

        <div id="cart-notification" class="hidden">
            <span class="checkmark">✔</span>
            <p>Product added to cart successfully!</p>
        </div>
        <script>

        </script>
        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
        <script src="js/cart.js"></script>
        <script src="js/updateQuantity.js"></script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
    const checkboxes = document.querySelectorAll(".product-checkbox");
    const checkoutForm = document.getElementById("checkoutForm");
    const totalPriceElement = document.getElementById("total-price");

    function updateTotal() {
        let total = 0;
        let selectedItems = document.querySelectorAll(".product-checkbox:checked");

        selectedItems.forEach(checkbox => {
            let row = checkbox.closest("tr");
            let totalCell = row.querySelector(".cart-total-price");
            let price = parseFloat(totalCell.textContent.replace(/₫|,/g, "")) || 0;
            total += price;
        });

        totalPriceElement.textContent = `Total Price: ₫${total.toLocaleString()}`;
    }

    window.updateQuantity = function (amount, cartItemId, event) {
        event.preventDefault();

        let quantityInput = document.getElementById(`quantity_${cartItemId}`);
        let totalPriceCell = document.getElementById(`total_${cartItemId}`);
        let currentQuantity = parseInt(quantityInput.value);
        let maxQuantity = parseInt(quantityInput.max);

        let newQuantity = currentQuantity + amount;

        if (newQuantity >= 1 && newQuantity <= maxQuantity) {
            quantityInput.value = newQuantity;

            fetch(`CartURL?service=updateQuantity&cartItemId=${cartItemId}&newQuantity=${newQuantity}`, {
                method: 'POST',
            })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success') {
                    let unitPrice = parseFloat(document.getElementById(`row_${cartItemId}`).querySelector(".cart-price").textContent.replace(/₫|,/g, ""));
                    totalPriceCell.textContent = `₫${(unitPrice * newQuantity).toLocaleString()}`;

                    updateTotal(); 
                } else {
                    showAlert("An error occurred while updating the quantity.");
                }
            })
            .catch(error => {
                console.error("Error updating quantity:", error);
                showAlert("Error updating quantity.");
            });
        } else {
            showAlert(`Quantity must be greater than 0 and less than or equal to ${maxQuantity}.`);
        }
    };

    window.checkQuantity = function (cartItemId, event) {
        let quantityInput = document.getElementById(`quantity_${cartItemId}`);
        let currentQuantity = parseInt(quantityInput.value);
        let maxQuantity = parseInt(quantityInput.max);

        if (currentQuantity < 1) {
            quantityInput.value = 1;
        } else if (currentQuantity > maxQuantity) {
            quantityInput.value = maxQuantity;
        }

        fetch(`CartURL?service=updateQuantity&cartItemId=${cartItemId}&newQuantity=${currentQuantity}`, {
            method: 'POST',
        })
        .then(response => response.json())
        .then(data => {
            if (data.status === 'success') {
                document.getElementById(`total_${cartItemId}`).textContent = `₫${data.totalOrderPrice}`;
            } else {
                showAlert("An error occurred while updating the quantity.");
            }
        })
        .catch(error => {
            console.error("Error updating quantity:", error);
            showAlert("Error updating quantity.");
        });
    };

    window.removeItem = function (cartItemId, event) {
        event.preventDefault();

        fetch(`CartURL?service=removeItem&cartItemId=${cartItemId}`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({cartItemId: cartItemId})
        })
        .then(response => response.json())
        .then(data => {
            if (data.status === "success") {
                const row = document.getElementById(`row_${cartItemId}`);
                if (row) {
                    row.remove();
                }
                updateTotal();
                if (document.querySelectorAll(".cart-row").length === 0) {
                    window.location.href = "CartURL?service=showCart&page=1";
                }
            } else {
                showAlert("An error occurred while removing the item.");
            }
        })
        .catch(error => {
            console.error("Error removing item:", error);
            showAlert("Error removing item.");
        });
    };

    function showAlert(message) {
        const alertBox = document.createElement("div");
        alertBox.classList.add("alert-box-error");
        alertBox.innerHTML = `
            <div class="alert-message-error">
                <div class="alert-icon">❌</div>
                <span>${message}</span>
            </div>
        `;
        document.body.appendChild(alertBox);

        setTimeout(() => alertBox.remove(), 3000);
    }

    checkboxes.forEach(checkbox => {
        checkbox.addEventListener("change", updateTotal);
    });

    document.getElementById("select_all").addEventListener("change", function () {
        let isChecked = this.checked;
        checkboxes.forEach(cb => cb.checked = isChecked);
        document.getElementById("select_all_footer").checked = isChecked;
        updateTotal();
    });

    document.getElementById("select_all_footer").addEventListener("change", function () {
        let isChecked = this.checked;
        checkboxes.forEach(cb => cb.checked = isChecked);
        document.getElementById("select_all").checked = isChecked;
        updateTotal();
    });

    checkoutForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const selectedItems = document.querySelectorAll('.product-checkbox:checked');
        if (selectedItems.length === 0) {
            showAlert("You haven't selected any products.");
            return;
        }

        selectedItems.forEach(checkbox => {
            let cartItemId = checkbox.value;
            let quantityInput = document.getElementById(`quantity_${cartItemId}`);
            let newQuantity = quantityInput.value;
            let hiddenQuantityInput = document.querySelector(`input[name="quantity_${cartItemId}"]`);
            
            if (hiddenQuantityInput) {
                hiddenQuantityInput.value = newQuantity; 
            }
        });

        checkoutForm.submit();
    });

    updateTotal();
});

        </script>
    </body>
</html>