<%-- 
    Document   : Feedback-Detail
    Created on : Mar 17, 2025, 2:17:34 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.User,java.util.List,jakarta.servlet.http.HttpSession,model.DAOUser"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="java.util.List,entity.Blog,jakarta.servlet.http.HttpSession,entity.User,model.DAOBlog,entity.Category,entity.Feedback" %>

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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <style>

            /* Chỉnh sửa kiểu cho các danh sách */

            /* Cải thiện các nút Cancel */
            .btn-danger {
                font-size: 16px;
                padding: 10px 20px;
                border-radius: 5px;
                background-color: #e74c3c;
                color: white;
                border: none;
                text-align: center;
                cursor: pointer;
            }

            .btn-danger:hover {
                background-color: #c0392b;
            }

            /* Chỉnh sửa phần thông tin chi tiết */
            .detail-container {
                max-width: 800px;
                margin: 20px auto;
                padding: 30px;
                background-color: #f9f9f9;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            .detail-container h2 {
                font-size: 24px;
                margin-bottom: 20px;
                text-align: center;
                font-weight: bold;
            }

            .detail-row {
                margin-bottom: 15px;
            }

            .detail-label {
                font-weight: bold;
                font-size: 18px;
                margin-right: 10px;
            }

            .detail-row img {
                border-radius: 5px;
                max-width: 100px;
                margin-top: 10px;
            }

            .detail-container .detail-row {
                font-size: 18px; /* Chỉnh kích thước chữ cho tất cả các dòng */
                line-height: 1.6;
            }

            .detail-container .detail-row span.detail-label {
                font-weight: bold;
            }

            .detail-container .detail-row span {
                font-size: 18px; /* Đảm bảo rằng phần nội dung sẽ có kích thước chữ giống nhau */
                font-weight: normal;
            }
            .detail-container .detail-row img {
                width: 100px;  /* Chiều rộng cố định */
                height: 100px; /* Chiều cao cố định */
                object-fit: cover; /* Đảm bảo ảnh không bị méo */
                border-radius: 5px; /* Thêm bo góc cho ảnh nếu cần */
                margin-right: 10px; /* Khoảng cách giữa các ảnh */
            }
            .error {
                color: red;
            }
            .message{
                color: red;
            }

        </style>
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
                                        USAd
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
                                    <li><a href="MarketingDashboardController" class="active">Home</a></li>
                                    <li><a href="MarketingPostController?service=listAllBlogs">Post List</a></li>
                                    <li><a href="SliderController">Slider List</a></li>
                                    <li><a href="CustomerController">Customer List</a></li>
                                    <li><a href="MarketingProductController">Product List</a></li>
                                    <li><a href="MaketingFeedBackController">Feedback List</a></li>
                                </ul>
                            </div>
                        </div>

                    </div>
                </div><!--/header-bottom-->
        </header><!--/header-->
        <div class="detail-container">
            <h2>Feedback Details</h2>
            <br>
            <c:if test="${feedback != null}">
                <b>
                    <c:if test="${not empty message}">
                        <div class="message">${message}</div>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div class="error">${error}</div>
                    </c:if>
                    <br>
                    <div class="detail-row">
                        <div class="col-md-5">
                            <span class="detail-label">Full Name:</span> ${feedback.user.name}
                        </div>
                        <div class="col-md-7">
                            <span class="detail-label">Email:</span> ${feedback.user.email}
                        </div>
                    </div>
                    <br>
                    <div class="detail-row">
                        <div class="col-md-5">
                            <span class="detail-label">Mobile:</span> ${feedback.user.phoneNumber}
                        </div>
                        <div class="col-md-7">
                            <span class="detail-label">Status:</span>
                            <span style="${feedback.status != null && feedback.status.equalsIgnoreCase('visible') ? 'color: green; font-weight: bold;' : 'color: red; font-weight: bold;'}">
                                ${feedback.status != null && feedback.status.equalsIgnoreCase('visible') ? 'Visible' : 'Hidden'}
                            </span>
                        </div>
                    </div>
                    <br>
                    <div class="detail-row">
                        <span class="detail-label">Rated Star:</span>
                        <span style="white-space: nowrap;">
                            <c:forEach var="i" begin="1" end="5">
                                <i class="${i <= feedback.rating ? 'fas' : 'far'} fa-star" style="color: gold;"></i>
                            </c:forEach>
                        </span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Product:</span> ${feedback.product.name}
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Feedback:</span> ${feedback.content}
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Images:</span>
                        <c:if test="${not empty feedback.images}">
                            <c:forEach var="image" items="${feedback.getImages()}">
                                <img src="${image}" alt="Feedback Image" style="max-width: 100px; margin-right: 10px;" />
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty feedback.images}">
                            No images available
                        </c:if>
                    </div>


                    <div class="detail-row">
                        <span class="detail-label">Your Reply:</span>
                        <c:choose>
                            <c:when test="${feedback.reply != null && !empty feedback.reply}">

                                <div style="margin-bottom: 15px;">
                                    ${feedback.reply}
                                </div>
                                <form action="MarketingFeedbackDetails" method="post">
                                    <textarea name="reply" rows="4" placeholder="Update your reply here...">${feedback.reply}</textarea>
                                    <input type="hidden" name="service" value="replyfeedback" />
                                    <input type="hidden" name="id" value="${feedback.id}" />
                                    <button type="submit" class="btn btn-success">Update Reply</button>
                                </form>
                            </c:when>
                            <c:otherwise>

                                <form action="MarketingFeedbackDetails" method="post">
                                    <textarea name="reply" rows="4" placeholder="Write your reply here..."></textarea>
                                    <input type="hidden" name="service" value="replyfeedback" />
                                    <input type="hidden" name="id" value="${feedback.id}" />
                                    <button type="submit" class="btn btn-success">Send Reply</button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </b>
            </c:if>
            <c:if test="${feedback == null}">
                <p>Feedback not found.</p>
                <a href="MaketingFeedBackController" class="btn btn-danger">Cancel</a>
            </c:if>
            <a href="MaketingFeedBackController" class="btn btn-danger">Cancel</a>
        </div>
        <br>
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

        </footer><!--/Footer--er-->



        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    </body>
</html>


