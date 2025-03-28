<%-- 
    Document   : blog
    Created on : Jan 18, 2025, 1:12:51 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@page import="java.util.List,entity.Blog,jakarta.servlet.http.HttpSession,entity.User,model.DAOBlog,entity.Category" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Blog | T-Shopper</title>
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
    <style>
        .categories_products a:hover {
            background-color: #ff8c00;
            color: #fff;
        }
    </style>
    <style>

        li a:hover {
            font-weight: bold; /* Làm đậm chữ */
            background-color: #f0f0f0; /* Thay đổi màu nền */
            color: #007bff; /* Thay đổi màu chữ */
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

                                    <% 
                                        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
                                        User user = (User) session.getAttribute("user");
                                        if (isLoggedIn != null && isLoggedIn) {
                                    %>
                                    <li><a href="UserProfileServlet"><i class="fa fa-user"></i> Account</a></li>
                                    <li><a href="${pageContext.request.contextPath}/CartURL"><i class="fa fa-shopping-cart"></i> Cart</a></li>
                                    <li><a href="CustomerOrderController"><i class="fa fa-shopping-cart"></i> My Orders</a></li>
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
                                            <li><a href="CartURL?service=checkout">Checkout</a></li> 
                                            <li><a href="CartURL">Cart</a></li>  
                                        </ul>
                                    </li> 
                                    <li class="dropdown"><a href="BlogURL?service=listAllBlogs" class="active">Blog<i class="fa fa-angle-down"></i></a>
                                        <ul role="menu" class="sub-menu">
                                            <li><a href="BlogURL" class="active">Blog List</a></li>
                                        </ul>
                                    </li> 
                                </ul>
                            </div>
                        </div>
                        <%
                            String query = (String) request.getAttribute("query");
                        %>
                         <div class="col-sm-3">
                            <form action="BlogURL" method="get" >
                                <input type="hidden" value="search" name="service">
                                <div class="search_box pull-right" style="position: relative; display: flex; flex-direction: column; align-items: start; border: 1px solid #ccc; border-radius: 20px; padding: 5px 10px; background-color: #f8f8f8;">
                                    <div style="display: flex; align-items: center; width: 100%;">
                                        <input type="search" name="query" placeholder="Search" value="<%= (request.getParameter("query") != null) ? request.getParameter("query") : "" %>"
                                               style="border: none; outline: none; background: transparent; flex-grow: 1; font-size: 14px; padding: 5px 10px; border-radius: 20px;">
                                        <button type="submit" style="border: none; background: transparent; cursor: pointer; font-size: 16px; color: #aaa; margin-left: 5px;">
                                            <i class="fa fa-search"></i> 
                                        </button>
                                    </div>

                                </div>
                            </form>
                        </div>           
                        <script>
                            document.addEventListener('DOMContentLoaded', function () {
                                const queryInput = document.querySelector('input[name="query"]');
                                const maxLength = 25;

                                if (queryInput && queryInput.value.length > maxLength) {
                                    queryInput.value = queryInput.value.substring(0, maxLength) + '...';
                                }
                            });
                        </script>

                    </div>
                </div>
            </div><!--/header-bottom-->
        </header><!--/header-->

        <section>
            <div class="container">
                <div class="row">
                    <div class="col-sm-3">
                        <div class="left-sidebar">  
                            <div style="width: 100%; max-width: 300px; margin: 20px 0; padding: 15px; background-color: #fff; border-radius: 10px; box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);">
                                <h2 style="font-size: 20px; font-weight: 700; color: #333; margin-bottom: 15px; border-bottom: 2px solid #ff8c00; padding-bottom: 5px; text-transform: uppercase;">Categories</h2> 
                                <div style="width: 100%;">
                                    <ul style="padding: 0; list-style: none;">
                                        <li style="margin-bottom: 5px;"> 
                                            <a href="BlogURL?service=listAllBlogs" style="display: block; padding: 10px 15px; font-size: 16px; font-weight: 500; color: #333; text-decoration: none; background-color: #fff; border-radius: 5px; transition: all 0.3s ease;">
                                                All Categories
                                            </a> 
                                        </li> 
                                        <%
                                            List<Category> categories = (List<Category>) request.getAttribute("categories");
                                            if (categories != null && !categories.isEmpty()) {
                                                for (Category category : categories) {
                                        %>
                                        <li style="margin-bottom: 5px;">
                                            <a href="BlogURL?service=CatewithID&categoryID=<%= category.getId() %>" 
                                               style="display: block; padding: 10px 15px; font-size: 16px; font-weight: 500; color: #333; text-decoration: none; background-color: #fff; border-radius: 5px; transition: all 0.3s ease;">
                                                <%= category.getCategoryName() %>
                                            </a>
                                        </li>
                                        <%
                                                }
                                            }
                                        %>
                                    </ul> 
                                </div>
                            </div>                                                  
                        </div>
                    </div>

                    <div class="col-sm-9">
                        <div class="blog-post-area">
                            <h2 class="title text-center">Latest From our Blog</h2>
                            <c:if test="${not empty message}">
                                <div class="alert alert-warning">${message}</div>
                            </c:if> 
                            <%
                                List<Blog> blogs = (List<Blog>) request.getAttribute("blogs");

                                if (blogs != null && !blogs.isEmpty()) {
                                    DAOBlog dao = new DAOBlog();
                                    for (Blog blog : blogs){
                                        String authname = dao.getAuthorNameById(blog.getAuthorID());
                            %>
                            <div style="border: 1px solid #ddd; border-radius: 8px; margin-bottom: 20px; padding: 20px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);">
                                <h3 style="margin-bottom: 10px; font-size: 24px; color: #333;"><%= blog.getTitle() %></h3>
                                <div style="display: flex; align-items: center; margin-bottom: 15px; font-size: 14px; color: #666;">
                                    <ul style="list-style: none; padding: 0; margin: 0; display: flex; align-items: center;">
                                        <li style="display: flex; align-items: center; margin-right: 15px;">
                                            <i class="fa fa-user" style="margin-right: 5px; color: #F09D3A;"></i>
                                            <span style="color: #333; font-size: 14px;"><%= authname %></span>
                                        </li>
                                        <li style="display: flex; align-items: center;">
                                            <i class="fa fa-calendar" style="margin-right: 5px; color: #F09D3A;"></i>
                                            <span style="color: #333; font-size: 14px;"><%= blog.getPostTime() %></span>
                                        </li>
                                    </ul>
                                </div>
                                <div style="display: flex; align-items: flex-start; margin-bottom: 15px;">
                                    <a href="#" style="display: block; max-width: 200px; height: auto; margin-right: 20px; border-radius: 5px;">
                                        <img src="<%= blog.getImageURL() %>" alt="ảnh lỗi" style="max-width: 200px; height: auto; border-radius: 5px;">
                                    </a>
                                    <p style="color: #808080; flex-grow: 1; line-height: 1.6;"><%= blog.getSubContent() %></p>
                                </div>
                                <a href="<%= request.getContextPath() %>/BlogDetailServlet?id=<%= blog.getId() %>" style="display: inline-block; padding: 10px 15px; background-color: #F09D3A; color: white; text-decoration: none; border-radius: 5px; font-weight: bold;">Read More</a>
                            </div>
                            <%
                                    }
                                }
                            %>

                            <div class="pagination-area">
                                <ul class="pagination">
                                    <% 
                                    Integer totalPages = (Integer) request.getAttribute("totalPages");
                                    Integer currentPage = (Integer) request.getAttribute("currentPage");
                                    String service = request.getParameter("service");
                                    String baseURL = "BlogURL?service=" + service;

                                    if (service.equals("search") && request.getParameter("query") != null) {
                                        query = request.getParameter("query");
                                        baseURL += "&query=" + java.net.URLEncoder.encode(query, "UTF-8");
                                    }
                                     if ("CatewithID".equals(service)) {
                                        String categoryIdParam = request.getParameter("categoryID");
                                        if (categoryIdParam != null && !categoryIdParam.trim().isEmpty()) {
                                            baseURL += "&categoryID=" + categoryIdParam;  // Add category ID to the base URL
                                        }
                                    }

                                    if (totalPages != null && totalPages > 0) {
                                        for (int i = 1; i <= totalPages; i++) {
                                    %>
                                    <li>
                                        <a href="<%= baseURL %>&page=<%= i %>" 
                                           class="<%= (i == currentPage) ? "active" : "" %>">
                                            <%= i %>
                                        </a>
                                    </li>
                                    <% 
                                        }
                                    }
                                    %>
                                </ul>
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



        <script src="js/jquery.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
    </body>
</html>