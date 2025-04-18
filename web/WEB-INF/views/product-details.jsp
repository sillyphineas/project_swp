<%--
    Document   : product-details
    Created on : Jan 18, 2025, 1:15:02 PM
    Author     : HP
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.User,entity.Feedback,java.util.List"%>


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
                padding: 30px; /* Increase padding to make the card appear larger */
                margin: 0; /* Remove margins to maximize width */
            }


            .list-group-item {
                font-size: 16px; /* Increase font size for better readability and height */
                padding: 15px 20px; /* Increase padding to make each item taller */
                /*                margin-bottom: 10px;  Add spacing between items to elongate the card */
                border-bottom: 0.5px solid #ddd !important; /* Thinner line, lighter color */
            }


            .card-title {
                color: #007bff;
                font-weight: bold;
                font-size: 24px; /* Increase the title font size */
                margin-bottom: 25px; /* Add more space below the title */
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


            .related-products .product-item {
                transition: all 0.3s ease;
            }


            .related-products .product-item:hover {
                background-color: #f9f9f9;
                transform: translateY(-5px);
            }


            .related-products h2 {
                font-size: 18px;
                color: #333;
                border-bottom: 2px solid #ff8c00;
                padding-bottom: 5px;
                margin-bottom: 15px;
            }


            .related-products p {
                color: #888;
                font-size: 14px;
            }

        </style>


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
                                        <label for="minPrice">Min Price</label>
                                        <input type="number" id="minPrice" name="minPrice" value="${param.minPrice}" min="" max="" step="10" class="form-control">


                                        <label for="maxPrice">Max Price </label>
                                        <input type="number" id="maxPrice" name="maxPrice" value="${param.maxPrice}" min="" max="" step="10" class="form-control">


                                        <button type="submit" class="btn btn-primary" style="margin-top:10px;">Check Price</button>
                                    </form>
                                </div>
                            </div>
                            <div class="related-products">
                                <h2>Related Product</h2>
                                <c:if test="${not empty relatedProducts}">
                                    <c:forEach var="relatedProduct" items="${relatedProducts}">
                                        <div class="product-item" style="margin-bottom: 20px; border-bottom: 1px solid #ddd; padding-bottom: 10px;">
                                            <a href="${pageContext.request.contextPath}/ProductDetailController?id=${relatedProduct.id}">
                                                <img src="${relatedProduct.imageURL}" alt="${relatedProduct.name}" style="width: 100%; height: auto; object-fit: cover;">
                                                <h3 style="font-size: 19px; margin: 10px 0; text-align: center">${relatedProduct.name}</h3>


                                            </a>
                                        </div>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${empty relatedProducts}">
                                    <p>No related products available.</p>
                                </c:if>
                            </div>


                        </div>
                    </div>


                    <div class="col-sm-9 padding-right">
                        <div class="product-details"><!--product-details-->
                            <div class="col-sm-5">
                                <div class="view-product">
                                    <img src="${product.imageURL}" alt=""  style="width: 100%; height: auto; object-fit: cover; " />


                                </div>


                            </div>
                            <div class="col-sm-7">








                                <form id="productForm" action="CartURL" method="POST" onsubmit="event.preventDefault(); addToCart();">
                                    <input type="hidden" id="productID" name="productID" value="${product.id}">
                                    <input type="hidden" name="service" value="add2cart">
                                    <div class="product-information">
                                        <h1>${product.name}</h1>


                                        <p><b>Color:</b>
                                            <select id="colorSelector" name="color" class="form-control">
                                                <c:forEach var="color" items="${colors}">
                                                    <option value="${color}" ${color == param.color ? 'selected' : ''}>${color}</option>
                                                </c:forEach>
                                            </select>
                                        </p>


                                        <p><b>Storage:</b>
                                            <select id="storageSelector" name="storage" class="form-control">
                                                <c:forEach var="storage" items="${storages}">
                                                    <option value="${storage}" ${storage == param.storage ? 'selected' : ''}>${storage}</option>
                                                </c:forEach>
                                            </select>
                                        </p>
                                        <p><b>Description:</b> ${product.description}</p>






                                        <c:forEach var="brand" items="${brands}">
                                            <c:if test="${brand.id == product.brandID}">
                                                <p><b>Brand: </b>${brand.name}</p>
                                            </c:if>
                                        </c:forEach>


                                        <p><b>Quantity:</b>
                                            <input type="number" id="quantity" name="quantity" value="1" min="1" class="form-control" required>
                                        </p>
                                        <p><b>Availability:</b> <span id="productStock">Loading...</span></p>
                                        <h2><b>Price: </b> <span id="productPrice">${price != null ? String.format("%,.0f", price) : 'Loading...'} ₫</span></h2>


                                        <button type="submit" id="addToCartBtn" class="btn btn-default cart">
                                            <i class="fa fa-shopping-cart"></i>
                                            Add to cart
                                        </button>
                                    </div>
                                </form>


                            </div>


                        </div><!--/product-details-->


                        <div class="category-tab shop-details-tab"><!--category-tab-->
                            <div class="col-sm-12">
                                <ul class="nav nav-tabs">
                                    <li class="col-md-12" ><a>Details</a></li>


                                </ul>
                            </div>


                            <div class="tab-content">
                                <div id="details">
                                    <div class="col-md-12">
                                        <div class="card">
                                            <div class="card-body">
                                                <h4 class="card-title text-center">Product Specifications</h4>
                                                <div class="row">
                                                    <div class="col-sm-6">
                                                        <ul class="list-group list-group-flush">
                                                            <li class="list-group-item"><b>Ram: </b>${product.ram} GB</li>
                                                            <li class="list-group-item"><b>Chipset:</b> ${product.chipset}</li>
                                                            <li class="list-group-item"><b>Screen Size:</b> ${product.screenSize} inch</li>
                                                            <li class="list-group-item"><b>Screen Type:</b> ${product.screenType}</li>
                                                            <li class="list-group-item"><b>Resolution:</b> ${product.resolution}</li>
                                                        </ul>
                                                    </div>
                                                    <div class="col-sm-6">
                                                        <ul class="list-group list-group-flush">
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
                            </div>
                        </div>
                        <div style="width: 100%; margin: auto; font-family: Arial, sans-serif;">  
                            <h2 style="color: #ff8c00; text-transform: uppercase; border-bottom: 3px solid #ff8c00; display: inline-block; padding-bottom: 5px;">
                                Product Reviews
                            </h2>
                            <%
                                List<Feedback> feedbacks = (List<Feedback>) request.getAttribute("feedbacks");
                            %>
                            <%
                                if (feedbacks != null && feedbacks.size() > 2) {
                            %>
                            <div style="text-align: center; margin-top: 20px;">
                                <a href="FeedBackController?service=ListFeedbackWithId&productId=<%= request.getParameter("id")%>"
                                   style="color: #ff8c00; font-size: 16px; text-decoration: none; font-weight: bold;">
                                    View All Reviews
                                </a>
                            </div>
                            <%
                                }
                            %>
                            <% if (feedbacks == null || feedbacks.isEmpty()) { %>
                            <p style="color: #888; font-size: 16px; text-align: center;">No reviews yet. Be the first to review this product!</p>
                            <% } else { %>
                            <div style="margin-top: 20px;">
                                <% for (Feedback feedback : feedbacks) {%>
                                <div style="border: 1px solid #ddd; border-radius: 10px; padding: 15px; margin-bottom: 15px; background: #fff; box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);">
                                    <div style="display: flex; align-items: center;">
                                        <img src="avatar.png" alt="User Avatar"
                                             style="width: 45px; height: 45px; border-radius: 50%; margin-right: 10px;">
                                        <div>
                                            <span style="font-weight: bold; font-size: 16px;">
                                                <%= feedback.getUser() != null ? feedback.getUser().getName().replaceAll("(?<=.{2}).", "*") : "Anonymous"%>
                                            </span>
                                            <br>
                                            <span style="color: #888; font-size: 14px;"><%= feedback.getReviewTime()%></span>
                                        </div>
                                    </div>
                                    <p style="color: #666; font-size: 14px;">
                                        Product: <%= feedback.getProduct().getName()%>,
                                        <%= feedback.getProductVariant().getColor().getColorName()%>,
                                        <%= feedback.getProductVariant().getStorage().getCapacity()%>
                                    </p>


                                    <div style="margin: 10px 0;">
                                        <% for (int i = 1; i <= 5; i++) {%>
                                        <span style="font-size: 20px; color: <%= i <= feedback.getRating() ? "#ffcc00" : "#ccc"%>;">★</span>
                                        <% }%>
                                    </div>


                                    <p style="margin: 5px 0; font-size: 15px; line-height: 1.5; color: #333;">
                                        <%= feedback.getContent()%>
                                    </p>


                                    <% if (feedback.getImages() != null && !feedback.getImages().isEmpty()) {
                                            int imageCount = feedback.getImages().size();
                                    %>
                                    <div style="display: flex; gap: 8px; align-items: center;">
                                        <% for (int i = 0; i < Math.min(2, imageCount); i++) {%>
                                        <img src="<%= feedback.getImages().get(i)%>"
                                             alt="Review Image"
                                             style="width: 80px; height: 80px; object-fit: cover; border-radius: 8px; cursor: pointer;"
                                             onclick="openLightbox('<%= feedback.getImages().get(i)%>', <%= i%>)">
                                        <% } %>


                                        <% if (imageCount > 2) {%>
                                        <div onclick="openLightbox('<%= feedback.getImages().get(0)%>', 0)"
                                             style="width: 80px; height: 80px; display: flex; justify-content: center; align-items: center;
                                             background: rgba(0, 0, 0, 0.6); color: white; font-size: 16px; font-weight: bold;
                                             border-radius: 8px; cursor: pointer;">
                                            +<%= imageCount - 2%>
                                        </div>
                                        <% } %>
                                    </div>


                                    <!-- Lightbox -->
                                    <div id="lightbox" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%;
                                         background: rgba(0, 0, 0, 0.8); justify-content: center; align-items: center;
                                         flex-direction: column; z-index: 1000;">
                                        <span onclick="closeLightbox()"
                                              style="position: absolute; top: 20px; right: 30px; font-size: 30px; color: white; cursor: pointer;">&times;</span>
                                        <img id="lightbox-img" style="max-width: 90%; max-height: 80%; border-radius: 8px;">


                                    </div>


                                    <script>
                                        function openLightbox(imageSrc, index) {
                                        document.getElementById("lightbox-img").src = imageSrc;
                                        document.getElementById("lightbox").style.display = "flex";
                                        }
                                        function nextImage() {
                                        currentIndex = (currentIndex + 1) % images.length;
                                        updateLightbox();
                                        }


                                        function prevImage() {
                                        currentIndex = (currentIndex - 1 + images.length) % images.length;
                                        updateLightbox();
                                        }


                                        function closeLightbox() {
                                        document.getElementById("lightbox").style.display = "none";
                                        }
                                    </script>
                                    <% } %>
                                    <% if (feedback.getReply() != null && !feedback.getReply().trim().isEmpty()) {%>
                                    <div style="margin-top: 15px;">


                                        <!-- Toggle Button -->
                                        <div style="display: flex; justify-content: flex-end;">
                                            <button onclick="toggleReply('reply-<%= feedback.getId()%>', this)"
                                                    style="background-color: #007bff; color: white; padding: 8px 14px;
                                                    border: none; border-radius: 6px; cursor: pointer;
                                                    font-size: 14px; font-weight: 500; display: flex; align-items: center; gap: 8px;
                                                    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1); transition: background-color 0.3s;">
                                                <span>View feedback from Admin</span>
                                                <span class="arrow" style="transition: transform 0.3s;">&#x25BC;</span> <!-- Mũi tên -->
                                            </button>
                                        </div>


                                        <!-- Reply Content -->
                                        <div id="reply-<%= feedback.getId()%>"
                                             style="display: none; margin-top: 10px; padding: 12px 16px;
                                             border-left: 4px solid #007bff; background-color: #f8f9fa;
                                             border-radius: 6px; box-shadow: 0 2px 6px rgba(0,0,0,0.05);">
                                            <p style="margin: 0; font-size: 14px; color: #333;">
                                                <strong style="color: #007bff;">Admin:</strong> <%= feedback.getReply()%>
                                            </p>
                                        </div>
                                    </div>
                                    <% } %>


                                </div>
                                <% } %>
                            </div>
                            <% }%>
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
        <div id="error-notification" class="hidden">
            <span class="error-icon">X</span>
            <p>Product added to cart Unsuccess!</p>
        </div>
        <script>
        <script src="js/jquery.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>


        <script src="js/cart.js"></script>
        <script src="js/jquery.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
        <script src="js/cart.js"></script>


                <script>
                document.addEventListener("DOMC ontentLoaded", function() {
                    var colorSelector = document.getElementById("colorSelector");
            var storageSelector = document.getElementById("storageSelector");
            var defaultColor = colorSelector.options[0].value;
            var defaultStorage = storageSelector.options[0].value;
            updateProductInfo(defaultColor, defaultStorage);
            colorSelector.addEventListener("change", function() {
            var selectedColor = colorSelector.value;
            var selectedStorage = storageSelector.value;
            updateProductInfo(selectedColor, selectedStorage);
            });
            storageSelector.addEventListener("change", function() {
            var selectedColor = colorSelector.value;
            var selectedStorage = storageSelector.value;
updateProductInfo(selectedColor, selectedStorage);
            });
                });
                
                
                function updateProductInfo(color, storage) {
                    var productId = '${product.id}';
            var xhr = new XMLHttpRequest();
            xhr.open('GET', '${pageContext.request.contextPath}/ProductDetailController?id=' + productId + '&color=' + encodeURIComponent(color) + '&storage=' + encodeURIComponent(storage), true);
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.onload = function() {
            if (xhr.status === 200) {
            var data = JSON.parse(xhr.responseText);
            document.getElementById("productPrice").innerText = formatPrice(data.price) + ' ₫';
            var stockElement = document.getElementById("productStock");
            if (data.stock > 0) {
            stockElement.innerText = "In stock";
            } else if (data.stock == 0) {
            stockElement.innerText = "Out of stock";
            }


            document.getElementById("addToCartBtn").disabled = (data.stock <= 0);
            } else {
            console.error("Error fetching product info: " + xhr.status);
            document.getElementById("productPrice").innerText = "Error";
            document.getElementById("productStock").innerText = "Error";
            }
            };
            xhr.onerror = function() {
            console.error("Request failed");
            document.getElementById("productPrice").innerText = "Error";
                document.getElementById("productStock").innerText = "Error";
            };
            xhr.send();
                }
                            
                            
                                function formatPrice(price) {
                    return price.toLocaleString('vi-VN');
                                    }
</script>
                                    <script>
                                    function         toggleReply(replyId, buttonElement) {
                    const replyDiv = document.getElementById(replyId);
            const ar row = buttonElement.querySelector('.arrow');
            if (replyDiv.style.display === 'none') {
            replyDiv.style.display = 'block';
                                        arrow.style.transform = 'rotate(180deg)'; // Xoay mũi tên lên
            } else {
            replyDiv.style.display = 'none';
                                        arrow.style.transform = 'rotate(0deg)'; // Mũi tên xuống
            }
                                     }
                                    </script>
                                    <script>
                                    let isLoggedIn = <%= (isLoggedIn != null && isLoggedIn) ? "true" : "false"%>;
                                    console.log("không nhận được",isLoggedIn);
</script>            
</html>
