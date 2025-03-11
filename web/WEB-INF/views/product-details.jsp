<%-- 
    Document   : product-details
    Created on : Jan 18, 2025, 1:15:02 PM
    Author     : HP
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.User"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Product Details | E-Shopper</title>
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
        <style>
            .card {
                border: 1px solid #ddd;
                border-radius: 10px;
                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
                background-color: #fff;
            }

            .card-title {
                color: #007bff;
                font-weight: bold;
                margin-bottom: 15px;
            }

            .list-group-item {
                font-size: 14px;
                padding: 8px 15px;
            }

            .list-group-item b {
                color: #333;
            }
            .nav-tabs {
                display: flex;
                justify-content: center;
                background-color: white;
                padding: 10px 0;
                border-bottom: 2px solid #ddd;
            }

            .nav-tabs li {
                list-style: none;
                margin: 0 15px;
            }

            .nav-tabs a {
                text-decoration: none;
                color: black;
                font-weight: bold;
                padding: 10px 20px;
                display: inline-block;
                text-align: center;
            }

            .nav-tabs a:hover {
                color: #007bff;
            }

            .tab-content {
                text-align: center;
                padding: 20px;
            }

        </style>

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
                                    <li class="dropdown"><a href="ProductController">Shop<i class="fa fa-angle-down"></i></a>
                                        <ul role="menu" class="sub-menu">
                                            <li><a href="ProductController">Products</a></li>
                                            <li><a href="checkout.html">Checkout</a></li> 
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
                                        <label for="minPrice">Min Price ($)</label>
                                        <input type="number" id="minPrice" name="minPrice" value="${param.minPrice}" min="0" max="500000" step="10" class="form-control">

                                        <label for="maxPrice">Max Price ($)</label>
                                        <input type="number" id="maxPrice" name="maxPrice" value="${param.maxPrice}" min="0" max="500000" step="10" class="form-control">

                                        <button type="submit" class="btn btn-primary" style="margin-top:10px;">Check Price</button>
                                    </form>
                                </div>
                            </div>


                            <div class="latest-products">

                                <c:if test="${not empty latestProduct}">
                                    <div class="latest-product">
                                        <h2>Newest Product</h2>
                                        <div class="product-item">
                                            <img src="${latestProduct.imageURL}" alt="${latestProduct.name}" style="width: 100%; height: auto;">
                                            <h3>${latestProduct.name}</h3>
                                            <p>${latestProduct.description}</p>

<!--                                            <a href="product-details.jsp?id=${latestProduct.id}" class="btn btn-primary">View Details</a>-->
                                        </div>
                                    </div>
                                </c:if>
                            </div>

                        </div>
                    </div>

                    <div class="col-sm-9 padding-right">
                        <div class="product-details"><!--product-details-->
                            <div class="col-sm-5">
                                <div class="view-product">
                                    <img src="${product.imageURL}" alt="" />

                                </div>
                                <div id="similar-product" class="carousel slide" data-ride="carousel">

                                    <!--                                     Wrapper for slides -->
                                    <!--                                    <div class="carousel-inner">
                                                                            <div class="item active">
                                                                                <a href=""><img src="images/product-details/similar1.jpg" alt=""></a>
                                                                                <a href=""><img src="images/product-details/similar2.jpg" alt=""></a>
                                                                                <a href=""><img src="images/product-details/similar3.jpg" alt=""></a>
                                                                            </div>
                                                                            <div class="item">
                                                                                <a href=""><img src="images/product-details/similar1.jpg" alt=""></a>
                                                                                <a href=""><img src="images/product-details/similar2.jpg" alt=""></a>
                                                                                <a href=""><img src="images/product-details/similar3.jpg" alt=""></a>
                                                                            </div>
                                                                            <div class="item">
                                                                                <a href=""><img src="images/product-details/similar1.jpg" alt=""></a>
                                                                                <a href=""><img src="images/product-details/similar2.jpg" alt=""></a>
                                                                                <a href=""><img src="images/product-details/similar3.jpg" alt=""></a>
                                                                            </div>
                                    
                                                                        </div>-->

                                    <!--                                     Controls -->
                                    <!--                                    <a class="left item-control" href="#similar-product" data-slide="prev">
                                                                            <i class="fa fa-angle-left"></i>
                                                                        </a>
                                                                        <a class="right item-control" href="#similar-product" data-slide="next">
                                                                            <i class="fa fa-angle-right"></i>
                                                                        </a>-->
                                </div>

                            </div>
                            <div class="col-sm-7">

                                <!--                                <form action="CartURL" method="POST" onsubmit="event.preventDefault(); addToCart();">
                                                                    <input type="hidden" id="productID" name="productID" value="${product.id}">
                                                                    <input type="hidden" name="service" value="add2cart">
                                
                                                                     Thông tin sản phẩm 
                                                                    <div class="product-information">
                                                                        <h2>${product.name}</h2>
                                                                        <p><b>Price:</b> <span id="productPrice">₫${String.format("%,.0f", minPrice)}</span></p>
                    
                                 Nút "Add to cart" 
                                <button type="submit" id="addToCartBtn" class="btn btn-default cart">
                                    <i class="fa fa-shopping-cart"></i>
                                    Add to cart
                                </button>

                            </div>
                                                        </form>-->
                                <form id="productForm">
                                    <input type="hidden" name="id" value="${product.id}"> 
                                                                <div class="product-information">
                                                                        <h2>${product.name}</h2>
                                                                        <p><b>Price:</b> <span id="productPrice">₫${String.format("%,.0f", minPrice)}</span></p>
                                   
                                    <p><b>Color:</b>
                                        <select id="color" name="color" class="form-control">
                                            <c:forEach var="color" items="${colors}">
                                                <option value="${color}" ${color == param.color ? 'selected' : ''}>${color}</option>
                                            </c:forEach>
                                        </select>
                                    </p>

                                    
                                    <p><b>Storage:</b>
                                        <select id="storage" name="storage" class="form-control">
                                            <c:forEach var="storage" items="${storages}">
                                                <option value="${storage}" ${storage == param.storage ? 'selected' : ''}>${storage}</option>
                                            </c:forEach>
                                        </select>
                                    </p>
                                        <p><b>Description:</b> ${product.description}</p>
                                   
                                 
                                    <p><b>Stock:</b> <span id="productStock">${stock}</span></p>

                                         <c:forEach var="brand" items="${brands}">
                                    <c:if test="${brand.id == product.brandID}">
                                        <p><b>Brand: </b>${brand.name}</p>
                                    </c:if>
                                </c:forEach>
                                    
                                    <p><b>Quantity:</b>
                                        <input type="number" id="quantity" name="quantity" value="1" min="1" class="form-control" required>
                                    </p>

                                  
                                    <button type="button" class="btn btn-default cart" onclick="addToCart()">
                                        <i class="fa fa-shopping-cart"></i> Add to cart
                                    </button>
                                                                </div>
                                </form>

                            </div>

                        </div><!--/product-details-->

                        <div class="category-tab shop-details-tab"><!--category-tab-->
                            <div class="col-sm-12">
                                <ul class="nav nav-tabs">
                                    <li class="col-md-6" ><a href="#">Details</a></li>
                                    <li class="col-md-6"><a href="#">Reviews</a></li>
                                </ul>
                            </div>

                            <div class="tab-content">
                                <div id="details">
                                    <div class="col-sm-6">
                                        <div class="card">
                                            <div class="card-body">
                                                <h4 class="card-title text-center">Product Specifications</h4>
                                                <div class="row">
                                                    <div class="col-sm-6">
                                                        <ul class="list-group list-group-flush">

                                                            <li class="list-group-item"><b>Availability:</b></li>

                                                            <li class="list-group-item"><b>Chipset:</b> ${product.chipset}</li>
                                                            <li class="list-group-item"><b>RAM:</b> ${product.ram}</li>

                                                            <li class="list-group-item"><b>Screen Size:</b> ${product.screenSize}</li>
                                                            <li class="list-group-item"><b>Screen Type:</b> ${product.screenType}</li>
                                                            <li class="list-group-item"><b>Resolution:</b> ${product.resolution}</li>

                                                        </ul>
                                                    </div>
                                                    <div class="col-sm-6">
                                                        <ul class="list-group list-group-flush">

                                                            <li class="list-group-item"><b>Resolution:</b> ${product.resolution}</li>

                                                            <li class="list-group-item"><b>Battery Capacity:</b> ${product.batteryCapacity}</li>
                                                            <li class="list-group-item"><b>Operating System:</b> ${product.os}</li>
                                                            <li class="list-group-item"><b>SIM Type:</b> ${product.simType}</li>
                                                            <li class="list-group-item"><b>Connectivity:</b> ${product.connectivity}</li>
                                                            <li class="list-group-item"><b>Created At:</b> ${product.createAt}</li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div id="reviews">
                                    <div class="col-sm-6">
                                        <h4><b>${product.description}</b></h4>

                                        <img src="${product.imageURL}" alt="alt"/>
                                    </div>
                                </div>
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
        <!--        <div id="cart-notification" class="hidden" style="position: fixed; bottom: 20px; right: 20px; background-color: #4CAF50; color: white; padding: 15px; border-radius: 5px; display: none;">
                    <span class="checkmark" style="font-size: 20px; margin-right: 5px;">✔</span>
                    <p style="display: inline; margin: 0;">Product added to cart successfully!</p>
                </div>-->
        <div id="cart-notification" class="hidden">
            <span class="checkmark">✔</span>
            <p>Product added to cart successfully!</p>
        </div>
        <script>
        <script src="js/jquery.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>

        <script src="js/cart.js"></script>
    <script>

        
        function updateProductInfo() {
                    var color = document.getElementById("color").value;
            var storage = document.getElementById("storage").value;
            var productId = '${product.id}';
            var xhr = new XMLHttpRequest();
            xhr.open('GET', '${pageContext.request.contextPath}/ProductDetailController?id=' + productId + '&color=' + color + '&storage=' + storage, true);
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.onload = function() {
            if (xhr.status == 200) {
            var data = JSON.parse(xhr.responseText);
            // Cập nhật giá và số lượng
            document.getElementById("productPrice").innerText = '₫' + data.price;
            document.getElementById("productStock").innerText = data.stock;
            } else {

            alert("Không tìm thấy sản phẩm với lựa chọn này.");
            }
            };
                xhr.send();
        }
        
                // Gọi hàm `updateProductInfo` khi thay đổi lựa chọn màu sắc hoặc dung lượng lưu trữ
        document.getElementById("color").addEventListener("change", updateProductInfo);
        document.getElementById("storage").addEventListener("change", updateProductInfo);
        
        </script>
    </body>
</html>

