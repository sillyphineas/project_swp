<%-- 
    Document   : index
    Created on : Jan 18, 2025, 1:13:24 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.User,java.util.List,jakarta.servlet.http.HttpSession,model.DAOUser, model.DAOOrder, entity.OrderDetail"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.text.NumberFormat, java.util.Locale" %>
<%@page import="java.util.List,entity.Blog,jakarta.servlet.http.HttpSession,entity.User,model.DAOBlog,entity.Category,entity.Order" %>
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
                                    <li><a href="salesDashboardController" class="active">Home</a></li>
                                    <li><a href="SaleOrderController">Order List</a></li>
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
        <section>
            <%
            List<OrderDetail> orderDetails = (List<OrderDetail>) request.getAttribute("orderDetails");
            String recipientName = (String) request.getAttribute("recipientName");
            String recipientPhone = (String) request.getAttribute("recipientPhone");
            String shippingAddress = (String) request.getAttribute("shippingAddress");
            String OrderTime = (String) request.getAttribute("orderTime");
            int orderID = request.getAttribute("selectedOrderID") != null ? 
                         (Integer)request.getAttribute("selectedOrderID") : 0;
            boolean showBill = request.getAttribute("showBillModal") != null ? 
                             (Boolean)request.getAttribute("showBillModal") : false;
            %>

            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="bill-container" id="billDetails" 
                             style="background: white; padding: 30px; border-radius: 10px;
                             box-shadow: 0 0 15px rgba(0,0,0,0.2); margin: 30px 0;">

                            <div class="bill-header">
                                <h2 style="text-align: center; margin-bottom: 20px; color: #FE980F;">Order #<%= orderID %> Details</h2>
                                <div class="row">
                                    <div class="col-md-6">
                                        <h4>Customer Information</h4>
                                        <table class="table table-bordered">
                                            <tr>
                                                <th style="width: 30%;">Recipient Name:</th>
                                                <td><%= recipientName != null ? recipientName : "N/A" %></td>
                                            </tr>
                                            <tr>
                                                <th>Phone Number:</th>
                                                <td><%= recipientPhone != null ? recipientPhone : "N/A" %></td>
                                            </tr>
                                            <tr>
                                                <th>Shipping Address:</th>
                                                <td><%= shippingAddress != null ? shippingAddress : "N/A" %></td>
                                            </tr>
                                        </table>
                                    </div>
                                    <div class="col-md-6">
                                        <h4>Order Information</h4>
                                        <table class="table table-bordered">
                                            <tr>
                                                <th style="width: 30%;">Payment Status:</th>
                                                <td style="color: <%= orderDetails != null && !orderDetails.isEmpty() && 
                                                        orderDetails.get(0).getPayment().getPaymentStatus().equals("Paid") ? 
                                                        "green" : "red" %>;">
                                                    <%= orderDetails != null && !orderDetails.isEmpty() ? 
                                                    orderDetails.get(0).getPayment().getPaymentStatus() : "N/A" %>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>Payment Method:</th>
                                                <td>
                                                    <%= orderDetails != null && !orderDetails.isEmpty() ? 
                                                orderDetails.get(0).getPaymentMethod().getName() : "N/A" %>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>Order Date:</th>
                                                <td><%= OrderTime != null ? OrderTime : "N/A" %></td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </div>

                            <!-- Product Details Table -->
                            <div class="bill-body">
                                <h4>Products</h4>
                                <div class="table-responsive">
                                    <table class="table table-bordered table-hover">
                                        <thead>
                                            <tr style="background-color: #F0F0E9;">
                                                <th style="text-align: center;">Product</th>
                                                <th style="text-align: center;">Image</th>
                                                <th style="text-align: center;">Color</th>
                                                <th style="text-align: center;">Storage</th>
                                                <th style="text-align: center;">Quantity</th>
                                                <th style="text-align: center;">Unit Price</th>
                                                <th style="text-align: center;">Total Price</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                            double orderTotal = 0;
                                            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                                            if (orderDetails != null && !orderDetails.isEmpty()) {
                                                for (OrderDetail detail : orderDetails) {
                                                    double itemTotal = detail.getProductVariant().getPrice() * detail.getQuantity();
                                                    orderTotal += itemTotal;
                                            %>
                                            <tr>
                                                <td><%= detail.getProductVariant().getProduct().getName() %></td>
                                                <td style="text-align: center;">
                                                    <img src="<%= detail.getProductVariant().getProduct().getImageURL() %>" 
                                                         alt="Product Image" width="50" height="50">
                                                </td>
                                                <td style="text-align: center;"><%= detail.getProductVariant().getColor().getColorName() %></td>
                                                <td style="text-align: center;"><%= detail.getProductVariant().getStorage().getCapacity() %></td>
                                                <td style="text-align: center;"><%= detail.getQuantity() %></td>
                                                <td style="text-align: right;"><%= currencyFormatter.format(detail.getProductVariant().getPrice()) %> VND</td>
                                                <td style="text-align: right;"><%=currencyFormatter.format( itemTotal )%> VND</td>
                                            </tr>
                                            <%
                                                }
                                            } else {
                                            %>
                                            <tr>
                                                <td colspan="7" style="text-align: center;">No product details available.</td>
                                            </tr>
                                            <%
                                            }
                                            %>
                                        </tbody>
                                        <tfoot>
                                            <tr style="font-weight: bold; background-color: #F0F0E9;">
                                                <td colspan="6" style="text-align: right;">Order Total:</td>
                                                <td style="text-align: right;"><%= currencyFormatter.format(orderTotal) %> VND</td>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>

                            <!-- Action Buttons -->
                            <div style="display: flex; justify-content: flex-end; gap: 10px; padding: 15px 0;">
                                <a href="SaleOrderController?service=listAllOrder" 
                                   style="padding: 8px 16px; background-color: #f0ad4e; color: white; border: none;
                                   border-radius: 4px; font-weight: bold; text-decoration: none; font-size: 14px; cursor: pointer;">
                                    &#8592; Back to Orders
                                </a>

                                <button onclick="printBill()" 
                                        style="padding: 8px 16px; background-color: #5cb85c; color: white; border: none;
                                        border-radius: 4px; font-weight: bold; font-size: 14px; cursor: pointer;">
                                    &#128424; Print Bill
                                </button>

                            </div>

                        </div>
                    </div>
                </div>
            </div>

            <script>
                // Script to show bill details automatically if needed
                $(document).ready(function () {
                    var showBill = <%= showBill %>;
                    if (showBill) {
                        document.getElementById('billDetails').scrollIntoView();
                    }
                });
            </script>
            <script>
                function printBill() {
                    var billContent = document.getElementById('billDetails').innerHTML;

                    var printWindow = window.open('', '', 'height=700,width=900');
                    printWindow.document.write('<html><head><title>Print Bill</title>');
                    printWindow.document.write('<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">');
                    printWindow.document.write('<style>');
                    printWindow.document.write('body { font-family: Arial, sans-serif; margin: 20px; }');
                    printWindow.document.write('table { width: 100%; border-collapse: collapse; }');
                    printWindow.document.write('th, td { padding: 8px; border: 1px solid #ddd; text-align: center; }');
                    printWindow.document.write('h2, h4 { text-align: center; color: #FE980F; }');
                    printWindow.document.write('</style>');
                    printWindow.document.write('</head><body>');
                    printWindow.document.write(billContent);
                    printWindow.document.write('</body></html>');
                    printWindow.document.close();

                    printWindow.focus();
                    printWindow.print();
                    printWindow.close();
                }
            </script>

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
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
    </body>
</html>