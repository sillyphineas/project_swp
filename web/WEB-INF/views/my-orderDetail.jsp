<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="entity.OrderInformation" %>
<%@ page import="entity.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title>Order Details | E-Shopper</title>
        <link href="css/bootstrap.min.css" rel="stylesheet" />
        <link href="css/main.css" rel="stylesheet" />
        <link href="css/responsive.css" rel="stylesheet" />
        <style>
            .bill-container {
                background: #fff;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 0 15px rgba(0,0,0,0.2);
                margin: 30px auto;
                max-width: 900px;
            }
            .bill-header h2 {
                text-align: center;
                margin-bottom: 20px;
                color: #FE980F;
            }
            .bill-header table, .bill-body table {
                width: 100%;
            }
            .bill-header th {
                width: 30%;
            }
            .bill-footer {
                text-align: right;
                margin-top: 20px;
            }
            .table-bordered th,
            .table-bordered td {
                border: 1px solid #ddd !important;
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

                                    <li><a href="${pageContext.request.contextPath}/CartURL"><i class="fa fa-shopping-cart"></i> Cart</a></li>
                                    <li><a href="CustomerOrderController"><i class="fa fa-shopping-cart"></i> My Orders</a></li>
                                    <li><a style="font-weight: bold"><img src="UserAvatarController" alt="Profile Image" class="img-thumbnail" style="height: 25px; width: 25px; border-radius: 50%;border: none"/> Hello, <%=user.getEmail()%></a></li>
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
                                            <li><a href="CartURL?service=showCart">Cart</a></li> 
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
                        <!--                        <div class="col-sm-3">
                                                    <div class="pull-right">
                                                        <form action="${pageContext.request.contextPath}/ProductController" method="get">
                                                            <input type="text" name="search" value="${param.search}" />
                        
                                                            <button type="submit" class="btn btn-default"><i class="fa fa-search"></i></button>
                                                        </form>
                                                    </div>
                                                </div>-->
                    </div>
                </div>
            </div><!--/header-bottom-->
        </header><!--/header-->
        <section class="container">
            <div class="bill-container" id="billDetails">
                <div class="bill-header">
                    <h2>Order #<c:out value="${selectedOrderID}" /></h2>
                    <div class="row">
                        <div class="col-md-6">
                            <h4>Customer Information</h4>
                            <table class="table table-bordered">
                                <tr>
                                    <th>Recipient Name:</th>
                                    <td><c:out value="${orderDetails[0].recipientName}" /></td>
                                </tr>
                                <tr>
                                    <th>Phone Number:</th>
                                    <td><c:out value="${orderDetails[0].recipientPhone}" /></td>
                                </tr>
                                <tr>
                                    <th>Shipping Address:</th>
                                    <td>
                                        <c:out value="${orderDetails[0].address}" /> - 
                                        <c:out value="${orderDetails[0].district}" /> - 
                                        <c:out value="${orderDetails[0].city}" />
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="col-md-6">
                            <h4>Order Information</h4>
                            <table class="table table-bordered">
                                <tr>
                                    <th>Payment Method:</th>
                                    <td><c:out value="${orderDetails[0].paymentName}" /></td>
                                </tr>
                                <tr>
                                    <th>Payment Status:</th>
                                    <td style="color: ${orderDetails[0].paymentStatus eq 'Paid' ? 'green' : 'red'};">
                                        <c:out value="${orderDetails[0].paymentStatus}" />
                                    </td>
                                </tr>
                                <tr>
                                    <th>Order Date:</th>
                                    <td>
                                        <fmt:formatDate value="${orderDetails[0].orderTime}" pattern="dd/MM/yyyy HH:mm" />
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>

                <div class="bill-body">
                    <h4>Products</h4>
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead>
                                <tr style="background-color: #F0F0E9;">
                                    <th>Product</th>
                                    <th>Image</th>
                                    <th>Color</th>
                                    <th>Storage</th>
                                    <th>Quantity</th>
                                    <th>Unit Price</th>
                                    <th>Total Price</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="orderTotal" value="0" />
                                <c:forEach var="detail" items="${orderDetails}">
                                    <c:set var="itemTotal" value="${detail.price * detail.quantity}" />
                                    <c:set var="orderTotal" value="${orderTotal + itemTotal}" />
                                    <tr>
                                        <td><c:out value="${detail.productName}" /></td>
                                        <td style="text-align: center;">
                                            <img src="<c:out value='${detail.imageURL}' />" alt="Product Image" width="50" height="50">
                                        </td>
                                        <td style="text-align: center;"><c:out value="${detail.colorName}" /></td>
                                        <td style="text-align: center;"><c:out value="${detail.capacity}" /></td>
                                        <td style="text-align: center;"><c:out value="${detail.quantity}" /></td>
                                        <td style="text-align: right;">
                                            <fmt:formatNumber value="${detail.price}" type="number" groupingUsed="true"/>
                                            VND
                                        </td>
                                        <td style="text-align: right;">
                                            <fmt:formatNumber value="${itemTotal}" type="number" groupingUsed="true"/>
                                            VND
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <tfoot>
                                <tr style="font-weight: bold; background-color: #F0F0E9;">
                                    <td colspan="6" style="text-align: right;">Order Total:</td>
                                    <td style="text-align: right;">
                                        <fmt:formatNumber value="${orderTotal}" type="number" groupingUsed="true"/> VND
                                    </td>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>

                <div class="bill-footer">
                    <a href="CustomerOrderController?service=displayAllOrders" class="btn btn-primary">
                        <i class="fa fa-arrow-left"></i> Back to Orders
                    </a>
                    <button onclick="printBill()" class="btn btn-success">
                        <i class="fa fa-print"></i> Print Bill
                    </button>
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
                        <p class="pull-left">Copyright © 2013 E-SHOPPER Inc. All rights reserved.</p>
                        <p class="pull-right">Designed by <span><a target="_blank" href="http://www.themeum.com">Themeum</a></span></p>
                    </div>
                </div>
            </div>

        </footer><!--/Footer-->>

        <!-- JS libs -->
        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
        <script src="js/cart.js"></script>
        <script>
                        function printBill() {
                            window.print();
                        }
        </script>
    </body>
</html>
