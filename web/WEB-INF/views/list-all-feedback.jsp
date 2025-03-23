
<%-- 
    Document   : blog-single
    Created on : Jan 18, 2025, 1:12:42 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List,entity.Blog,jakarta.servlet.http.HttpSession,entity.User,model.DAOBlog,java.sql.*,entity.Feedback" %>
<%@ page import="com.google.gson.Gson" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Blog Single | E-Shopper</title>
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
                    <div class="blog-post-area">
                        <h2 class="title text-center">Product Reviews</h2>
                        <div style="width: 60%; margin: auto; font-family: Arial, sans-serif;">
                            <%
                                int productId = (Integer) request.getAttribute("productId");
                            %>
                            <!-- Filter Options -->
                            <div style="text-align: center; margin-bottom: 20px;">
                                <%
                                    String starParam = request.getParameter("star");
                                    if (starParam == null) { 
                                %>
                                <a href="FeedBackController?service=ListFeedbackWithId&productId=<%= productId %>" style="padding: 8px 12px; background-color: #ff8c00; color: white; text-decoration: none; border-radius: 5px;">ALL</a>
                                <%
                                    } else { 
                                %>
                                <a href="FeedBackController?service=ListFeedbackWithId&productId=<%= productId %>" style="padding: 8px 12px; background-color: #ddd; color: black; text-decoration: none; border-radius: 5px;">ALL</a>
                                <%
                                    }
                                    for (int i = 5; i >= 1; i--) {
                                        if (starParam != null && starParam.equals(String.valueOf(i))) {
                                %>
                                <a href="FeedBackController?service=FilterByStar&productId=<%= productId %>&star=<%= i %>" style="padding: 8px 12px; background-color: #ff8c00; color: white; text-decoration: none; border-radius: 5px; margin: 0 5px;"><%= i %> Star</a>
                                <%
                                        } else { 
                                %>
                                <a href="FeedBackController?service=FilterByStar&productId=<%= productId %>&star=<%= i %>" style="padding: 8px 12px; background-color: #ddd; color: black; text-decoration: none; border-radius: 5px; margin: 0 5px;"><%= i %> Star</a>
                                <%
                                        }
                                    }
                                %>
                            </div>

                            <% List<Feedback> feedbacks = (List<Feedback>) request.getAttribute("feedbacks"); %>
                            <% if (feedbacks == null || feedbacks.isEmpty()) { %>
                            <p style="color: #888; font-size: 16px; text-align: center;">No reviews yet. Be the first to review this product!</p>
                            <% } else { %>
                            <div style="margin-top: 20px;">
                                <% for (Feedback feedback : feedbacks) { %>
                                <div style="border: 1px solid #ddd; border-radius: 10px; padding: 15px; margin-bottom: 15px; background: #fff; box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);">
                                    <div style="display: flex; align-items: center;">
                                        <img src="avatar.png" alt="User Avatar" 
                                             style="width: 45px; height: 45px; border-radius: 50%; margin-right: 10px;">
                                        <div>
                                            <span style="font-weight: bold; font-size: 16px;">
                                                <%= feedback.getUser() != null ? feedback.getUser().getName().replaceAll("(?<=.{2}).", "*") : "Anonymous" %>
                                            </span>
                                            <br>
                                            <span style="color: #888; font-size: 14px;"><%= feedback.getReviewTime() %></span>
                                        </div>
                                    </div>
                                    <p style="color: #666; font-size: 14px;">
                                        Product: <%= feedback.getProduct().getName() %>, 
                                        <%= feedback.getProductVariant().getColor().getColorName() %>, 
                                        <%= feedback.getProductVariant().getStorage().getCapacity() %>
                                    </p>

                                    <div style="margin: 10px 0;">
                                        <% for (int i = 1; i <= 5; i++) { %>
                                        <span style="font-size: 20px; color: <%= i <= feedback.getRating() ? "#ffcc00" : "#ccc" %>;">★</span>
                                        <% } %>
                                    </div>

                                    <p style="margin: 5px 0; font-size: 15px; line-height: 1.5; color: #333;">
                                        <%= feedback.getContent() %>
                                    </p>

                                    <% if (feedback.getImages() != null && !feedback.getImages().isEmpty()) { 
                                        int imageCount = feedback.getImages().size();
                                    %>
                                    <div style="display: flex; gap: 8px; align-items: center;">
                                        <% for (int i = 0; i < Math.min(2, imageCount); i++) { %>
                                        <img src="<%= feedback.getImages().get(i) %>" 
                                             alt="Review Image"
                                             style="width: 80px; height: 80px; object-fit: cover; border-radius: 8px; cursor: pointer;"
                                             onclick="openLightbox('<%= feedback.getImages().get(i) %>', <%= i %>)">
                                        <% } %>

                                        <% if (imageCount > 2) { %>
                                        <div onclick="openLightbox('<%= feedback.getImages().get(0) %>', 0)" 
                                             style="width: 80px; height: 80px; display: flex; justify-content: center; align-items: center;
                                             background: rgba(0, 0, 0, 0.6); color: white; font-size: 16px; font-weight: bold;
                                             border-radius: 8px; cursor: pointer;">
                                            +<%= imageCount - 2 %>
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
                                    <% if (feedback.getReply() != null && !feedback.getReply().trim().isEmpty()) { %>
                                    <div style="margin-top: 15px;">

                                        <!-- Toggle Button -->
                                        <div style="display: flex; justify-content: flex-end;">
                                            <button onclick="toggleReply('reply-<%= feedback.getId() %>', this)" 
                                                    style="background-color: #007bff; color: white; padding: 8px 14px;
                                                    border: none; border-radius: 6px; cursor: pointer;
                                                    font-size: 14px; font-weight: 500; display: flex; align-items: center; gap: 8px;
                                                    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1); transition: background-color 0.3s;">
                                                <span>View feedback from Admin</span> 
                                                <span class="arrow" style="transition: transform 0.3s;">&#x25BC;</span> <!-- Mũi tên -->
                                            </button>
                                        </div>

                                        <!-- Reply Content -->
                                        <div id="reply-<%= feedback.getId() %>" 
                                             style="display: none; margin-top: 10px; padding: 12px 16px;
                                             border-left: 4px solid #007bff; background-color: #f8f9fa;
                                             border-radius: 6px; box-shadow: 0 2px 6px rgba(0,0,0,0.05);">
                                            <p style="margin: 0; font-size: 14px; color: #333;">
                                                <strong style="color: #007bff;">Admin:</strong> <%= feedback.getReply() %>
                                            </p>
                                        </div>
                                    </div>
                                    <% } %>

                                </div>
                                <% } %>
                            </div>
                            <% } %>
                        </div>

                        <% 
                            int currentPage = (Integer) request.getAttribute("currentPage");
                            int totalPages = (Integer) request.getAttribute("totalPages");
                            
                             productId = (Integer) request.getAttribute("productId");
                            Integer selectedStar = (Integer) request.getAttribute("selectedStar"); // Lọc theo số sao (nếu có)
                            String service = (selectedStar == null) ? "ListFeedbackWithId" : "FilterByStar";
                        %>
                        <div style="text-align: center; margin-top: 20px; display: flex; align-items: center;">
                            <div style="flex-grow: 1; text-align: center;">
                                <% if (currentPage > 1) { %>
                                <a href="FeedBackController?service=<%= service %>&productId=<%= productId %><%= (selectedStar != null) ? "&star=" + selectedStar : "" %>&page=<%= currentPage - 1 %>" 
                                   style="padding: 8px 12px; background-color: #ff8c00; color: white; text-decoration: none; border-radius: 5px;">← Previous Page</a>
                                <% } %>

                                <% for (int i = 1; i <= totalPages; i++) { %>
                                <a href="FeedBackController?service=<%= service %>&productId=<%= productId %><%= (selectedStar != null) ? "&star=" + selectedStar : "" %>&page=<%= i %>" 
                                   style="padding: 8px 12px; margin: 0 3px; border-radius: 5px; text-decoration: none;
                                   <%= (i == currentPage) ? "background-color: #ff8c00; color: white; font-weight: bold;" : "background-color: #f1f1f1; color: black;" %>">
                                    <%= i %>
                                </a>
                                <% } %>

                                <% if (currentPage < totalPages) { %>
                                <a href="FeedBackController?service=<%= service %>&productId=<%= productId %><%= (selectedStar != null) ? "&star=" + selectedStar : "" %>&page=<%= currentPage + 1 %>" 
                                   style="padding: 8px 12px; background-color: #ff8c00; color: white; text-decoration: none; border-radius: 5px;">Next Page →</a>
                                <% } %>
                            </div>

                            <a href="/ProductDetailController?id=<%= productId %>" 
                               style="padding: 8px 12px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px; margin-left: -200px;">
                                Back to Product Detail
                            </a>
                        </div>


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
                        <p class="pull-left">Copyright © 2013 E-SHOPPER Inc. All rights reserved.</p>
                        <p class="pull-right">Designed by <span><a target="_blank" href="http://www.themeum.com">Themeum</a></span></p>
                    </div>
                </div>
            </div>

        </footer><!--/Footer-->
        <script>
            function toggleReply(replyId, buttonElement) {
                const replyDiv = document.getElementById(replyId);
                const arrow = buttonElement.querySelector('.arrow');

                if (replyDiv.style.display === 'none') {
                    replyDiv.style.display = 'block';
                    arrow.style.transform = 'rotate(180deg)'; // Xoay mũi tên lên
                } else {
                    replyDiv.style.display = 'none';
                    arrow.style.transform = 'rotate(0deg)'; // Mũi tên xuống
                }
            }
        </script>

        <script src="js/jquery.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
    </body>
</html>