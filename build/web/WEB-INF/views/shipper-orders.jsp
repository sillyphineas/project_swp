<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="entity.OrderInformation" %>
<%@ page import="java.util.List" %>
<%@page import="entity.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Shipper Orders</title>
        <!-- Bootstrap & FontAwesome & main.css -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">

        <style>
            /* Nếu muốn y chang trước, bỏ hết style bên dưới. 
               Hoặc giữ minimal style (ví dụ, bỏ table-responsive). */

            /* Nếu bạn KHÔNG muốn table-responsive => bỏ .table-responsive wrapper */
            /* Nếu bạn vẫn muốn responsive => bọc bảng trong <div class="table-responsive">... */

            /* Dưới đây là style minimal; có thể xóa nếu muốn "như trước" hoàn toàn */
            /* .table th, .table td {
                white-space: nowrap; // nếu muốn text không xuống dòng
            } */
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
                                    <li><a href="ShipperDashboardController" class="active">Home</a></li>
                                    <li><a href="ShipperOrderController">Order List</a></li>
                                </ul>
                            </div>
                        </div>

                    </div>
                </div>
            </div><!--/header-bottom-->
        </header><!--/header-->

        <section class="container" style="margin-top: 30px;">
            <h2>Order List for Shipper</h2>
            <!-- Filter Form -->
            <form action="ShipperOrderController" method="get" class="form-inline" style="margin-bottom: 15px;">
                <label for="status" class="mr-2">Shipping Status:</label>
                <select name="status" id="status" class="form-control mr-3">
                    <option value="" <c:if test="${statusFilter == ''}">selected</c:if>>All</option>
                    <option value="Shipping" <c:if test="${statusFilter == 'Shipping'}">selected</c:if>>Shipping</option>
                    <option value="Delivered" <c:if test="${statusFilter == 'Delivered'}">selected</c:if>>Delivered</option>
                    </select>

                    <label for="search" class="mr-2">Order ID:</label>
                    <input type="text" name="search" id="search" value="${searchQuery}" placeholder="Enter order ID" class="form-control mr-3" />

                <button type="submit" class="btn btn-primary">Filter</button>
            </form>

            <!-- Bảng hiển thị OrderInformation -->
            <!-- Nếu muốn responsive => bọc trong <div class="table-responsive"> -->
            <!-- <div class="table-responsive"> -->
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Order Time</th>
                        <th>Payment Status</th>
                        <th>Shipping Status</th>
                        <th>Shipping Date</th>
                        <th>Estimated Arrival</th>
                        <th>Actual Arrival</th>
                        <th>Total Price</th>
                        <th>Recipient</th>
                        <th>Address</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="oi" items="${orderList}">
                        <tr>
                            <td>${oi.id}</td>
                            <td>${oi.orderTime}</td>
                            <td>${oi.paymentStatus}</td>
                            <td>${oi.shippingStatus}</td>
                            <td>${oi.shippingDate}</td>
                            <td>${oi.estimatedArrival}</td>
                            <td>${oi.actualArrival}</td>
                            <td>${oi.totalPrice}</td>
                            <td>${oi.recipientName} (${oi.recipientPhone})</td>
                            <td>${oi.address}, ${oi.district}, ${oi.city}</td>
                            <td>
                                <!-- Update Shipping -->
                                <form action="ShipperOrderController" method="post" style="margin-bottom: 5px;">
                                    <input type="hidden" name="orderId" value="${oi.id}" />
                                    <input type="hidden" name="statusFilter" value="${statusFilter}" />
                                    <input type="hidden" name="searchQuery" value="${searchQuery}" />
                                    <input type="hidden" name="page" value="${currentPage}" />
                                    <input type="hidden" name="pageSize" value="10" />
                                    <input type="hidden" name="action" value="updateShipping" />
                                    <select name="newShippingStatus" class="form-control" style="display:inline-block; width:auto;">
                                        <option value="Shipping" <c:if test="${oi.shippingStatus == 'Shipping'}">selected</c:if>>Shipping</option>
                                        <option value="Delivered" <c:if test="${oi.shippingStatus == 'Delivered'}">selected</c:if>>Delivered</option>
                                        </select>
                                        <button type="submit" class="btn btn-info btn-sm">Update Ship</button>
                                    </form>

                                    <!-- Update Payment -->
                                    <form action="ShipperOrderController" method="post">
                                        <input type="hidden" name="orderId" value="${oi.id}" />
                                    <input type="hidden" name="statusFilter" value="${statusFilter}" />
                                    <input type="hidden" name="searchQuery" value="${searchQuery}" />
                                    <input type="hidden" name="page" value="${currentPage}" />
                                    <input type="hidden" name="pageSize" value="10" />
                                    <input type="hidden" name="action" value="updatePayment" />
                                    <select name="newPaymentStatus" class="form-control" style="display:inline-block; width:auto;">
                                        <option value="Pending" <c:if test="${oi.paymentStatus == 'Pending'}">selected</c:if>>Pending</option>
                                        <option value="Paid" <c:if test="${oi.paymentStatus == 'Paid'}">selected</c:if>>Paid</option>
                                        <option value="Refund" <c:if test="${oi.paymentStatus == 'Refund'}">selected</c:if>>Refund</option>
                                        </select>
                                        <button type="submit" class="btn btn-warning btn-sm">Update Pay</button>
                                    </form>
                                </td>
                            </tr>
                    </c:forEach>
                </tbody>
            </table>
            <!-- </div> -->

            <!-- Pagination -->
            <div style="text-align: center;">
                <c:if test="${currentPage > 1}">
                    <a href="ShipperOrderController?page=${currentPage - 1}&status=${statusFilter}&search=${searchQuery}" 
                       class="btn btn-default">&laquo; Prev</a>
                </c:if>
                <c:forEach begin="1" end="${totalPages}" var="p">
                    <a href="ShipperOrderController?page=${p}&status=${statusFilter}&search=${searchQuery}" 
                       class="btn <c:if test='${p == currentPage}'>btn-primary</c:if> btn-default"
                           style="margin: 0 3px;">
                       ${p}
                    </a>
                </c:forEach>
                <c:if test="${currentPage < totalPages}">
                    <a href="ShipperOrderController?page=${currentPage + 1}&status=${statusFilter}&search=${searchQuery}" 
                       class="btn btn-default">Next &raquo;</a>
                </c:if>
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

        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
