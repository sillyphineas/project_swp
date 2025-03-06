<%@ page import="java.util.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="entity.User"%>
<html>
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

            <!-- Direct CSS for Style Enhancements -->
            <style>
                /* Section Styling */
                #settings-section {
                    margin-top: 20px;
                    margin-bottom: 40px;
                    background-color: #fff;
                    padding: 20px;
                    border-radius: 8px;
                    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                }

                /* Search and Filter Form */
                .form-group {
                    margin-bottom: 15px;
                }

                form {
                    display: flex;
                    align-items: center;
                    gap: 10px;
                    margin-bottom: 20px;
                }

                /* Input Field and Select Box Styling */
                form input, form select {
                    padding: 10px;
                    font-size: 16px;
                    border: 1px solid #ccc;
                    border-radius: 4px;
                    width: 200px;
                }

                /* Button Styling */
                form button {
                    padding: 10px 20px;
                    font-size: 16px;
                    border: none;
                    background-color: #f39c12;
                    color: white;
                    border-radius: 4px;
                    cursor: pointer;
                }

                form button:hover {
                    background-color: #e67e22;
                }

                /* Table Styling */
                table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 30px;
                }

                table th, table td {
                    padding: 12px;
                    text-align: left;
                    border: 1px solid #ddd;
                    background-color: #f9f9f9;
                }

                table th {
                    background-color: #f1c40f;
                    color: white;
                    font-weight: bold;
                }

                table tr:nth-child(even) {
                    background-color: #f2f2f2;
                }

                table tr:hover {
                    background-color: #f0e68c;
                }

                /* Pagination Styling */
                .pagination {
                    text-align: center;
                    margin-top: 20px;
                }

                .pagination a {
                    margin: 0 5px;
                    padding: 8px 16px;
                    background-color: #f39c12;
                    color: white;
                    text-decoration: none;
                    border-radius: 4px;
                    transition: background-color 0.3s;
                }

                .pagination a:hover {
                    background-color: #e67e22;
                }

                /* Responsive Styling for smaller screens */
                @media screen and (max-width: 768px) {
                    form {
                        flex-direction: column;
                        align-items: flex-start;
                    }

                    form input, form select {
                        width: 100%;
                    }

                    form button {
                        width: 100%;
                        margin-top: 10px;
                    }

                    table {
                        width: 100%;
                        margin-top: 10px;
                    }

                    .pagination a {
                        margin: 5px;
                    }
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
                                            USA
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
                                        <li><a href="#"><i class="fa fa-user"></i> Account</a></li>
                                        <li><a href="#"><i class="fa fa-star"></i> Wishlist</a></li>
                                        <li><a href="checkout.html"><i class="fa fa-crosshairs"></i> Checkout</a></li>
                                        <li><a href="${pageContext.request.contextPath}/CartController"><i class="fa fa-shopping-cart"></i> Cart</a></li>
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
                                        <li><a href="ShipperDashboardController" class="active">Home</a></li>
                                        <li><a href="ShipperOrderController">Order List</a></li>
                                    </ul>
                                </div>
                            </div>

                        </div>
                    </div>
                </div><!--/header-bottom-->
            </header><!--/header-->
            <h2>Order List</h2>
            <!-- Bộ lọc tìm kiếm và trạng thái -->
            <!-- Form filter và search (chỉ search theo Buyer Name) -->
            <form action="ShipperOrderController" method="get">
                <label for="status">Shipping Status:</label>
                <select name="status" id="status">
                    <option value="" ${statusFilter == "" ? 'selected' : ''}>All</option>
                    <option value="Pending" ${statusFilter eq 'Pending' ? 'selected' : ''}>Pending</option>
                    <option value="Shipped" ${statusFilter eq 'Shipped' ? 'selected' : ''}>Shipped</option>
                    <option value="Delivered" ${statusFilter eq 'Delivered' ? 'selected' : ''}>Delivered</option>
                </select>
                &nbsp;&nbsp;
                <label for="search">Order id:</label>
                <input type="text" name="search" id="search" value="${searchQuery}" placeholder="Enter order id" />
                &nbsp;&nbsp;
                <button type="submit">Filter</button>
            </form>

            <!-- Bảng hiển thị danh sách đơn hàng -->
            <table>
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Buyer Name</th>
                        <th>Order Time</th>
                        <th>Order Status</th>
                        <th>Shipping Address</th>
                        <th>Total Price</th>
                        <th>Recipient Name</th>
                        <th>Recipient Phone</th>
                        <th>Shipping Status</th>
                        <th>Estimated Arrival</th>
                        <th>Actual Arrival</th>
                        <th>Shipping Date</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="osv" items="${orderShippingList}">
                        <tr>
                            <td>${osv.orderId}</td>
                            <td>${osv.buyerName}</td>
                            <td>${osv.orderTime}</td>
                            <td>${osv.orderStatus}</td>
                            <td>${osv.shippingAddress}</td>
                            <td>${osv.totalPrice}</td>
                            <td>${osv.recipientName}</td>
                            <td>${osv.recipientPhone}</td>
                            <td>${osv.shippingStatus}</td>
                            <td>${osv.estimatedArrival}</td>
                            <td>${osv.actualArrival}</td>
                            <td>${osv.shippingDate}</td>
                            <td>
                                <!-- Form cập nhật ShippingStatus -->
                                <form action="ShipperOrderController" method="post">
                                    <input type="hidden" name="orderId" value="${osv.orderId}" />
                                    <!-- Duy trì các tham số filter/search -->
                                    <input type="hidden" name="statusFilter" value="${statusFilter}" />
                                    <input type="hidden" name="searchQuery" value="${searchQuery}" />
                                    <input type="hidden" name="page" value="${currentPage}" />
                                    <input type="hidden" name="pageSize" value="10" />

                                    <select name="status">
                                        <option value="Pending" ${osv.shippingStatus eq 'Pending' ? 'selected' : ''}>Pending</option>
                                        <option value="Shipped" ${osv.shippingStatus eq 'Shipped' ? 'selected' : ''}>Shipped</option>
                                        <option value="Delivered" ${osv.shippingStatus eq 'Delivered' ? 'selected' : ''}>Delivered</option>
                                    </select>
                                    <button type="submit">Update</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>


            <!-- Phân trang -->
            <div class="pagination">
                <c:forEach begin="1" end="${totalPages}" var="p">
                    <a href="ShipperOrderController?page=${p}&status=${statusFilter}&search=${searchQuery}">${p}</a>
                </c:forEach>
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



            <script src="js/jquery.js"></script>
            <script src="js/bootstrap.min.js"></script>
            <script src="js/jquery.scrollUp.min.js"></script>
            <script src="js/price-range.js"></script>
            <script src="js/jquery.prettyPhoto.js"></script>
            <script src="js/main.js"></script>
        </body>
    </html>
