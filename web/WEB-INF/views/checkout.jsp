<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.math.BigDecimal" %>
<%@page import="entity.User" %>
<%@page import="entity.Voucher" %>
<%@page import="java.util.Vector" %>
<%@page import="java.util.List" %>
<%@page import="entity.CartItem" %>
<%@page import="entity.Product" %>
<%@page import="entity.ProductVariant" %>
<%@page import="entity.Address" %>
<%@page import="entity.Storage" %>
<%@page import="entity.Color" %>

<%
    // Không khai báo lại session vì JSP đã có sẵn implicit object "session"
    String error = (String) request.getAttribute("error");

    // Lấy giỏ hàng, tổng tiền, danh sách voucher và địa chỉ từ session (nếu có), nếu không thì lấy từ request.
    List<CartItem> cartItems = (List<CartItem>) session.getAttribute("selectedCartItems");
    if (cartItems == null) {
        cartItems = (List<CartItem>) request.getAttribute("cartItems");
    }

    BigDecimal totalOrderPrice = (BigDecimal) session.getAttribute("totalOrderPrice");
    if (totalOrderPrice == null) {
        totalOrderPrice = (BigDecimal) request.getAttribute("totalOrderPrice");
    }
    if (totalOrderPrice == null) {
        totalOrderPrice = BigDecimal.ZERO;
    }

    Vector<Voucher> vouchers = (Vector<Voucher>) session.getAttribute("vouchers");
    if (vouchers == null) {
        vouchers = (Vector<Voucher>) request.getAttribute("vouchers");
    }
    // Nếu danh sách voucher rỗng, khởi tạo mới để tránh lỗi
    if (vouchers == null || vouchers.isEmpty()) {
        vouchers = new Vector<Voucher>();
    }

    List<Address> addresses = (List<Address>) session.getAttribute("userAddresses");
    if (addresses == null) {
        addresses = (List<Address>) request.getAttribute("userAddresses");
    }
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Checkout | E-Shopper</title>
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
        <style>

        /* General styles for the form */
        .shipping-address {
            margin-bottom: 20px;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
            max-width: 600px;
            margin: 0 auto;
            font-family: Arial, sans-serif;
        }

        h3 {
            text-align: center;
        }

        #currentAddress p {
            margin: 0;
            font-size: 16px;
            color: #333;
        }

        #currentAddress {
            margin-bottom: 20px;
        }

        #addressOptions {
            margin-top: 20px;
        }

        #defaultAddress input {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border-radius: 4px;
            border: 1px solid #ccc;
        }

        #newAddress select, #newAddress input {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border-radius: 4px;
            border: 1px solid #ccc;
            margin-bottom: 10px;
        }

        #changeAddressBtn, #confirmAddressBtn {
            display: inline-block;
            margin-top: 10px;
            padding: 8px 16px;
            background-color: #ff7f00;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        #changeAddressBtn:hover, #confirmAddressBtn:hover {
            background-color: #e67000;
        }

        .submit-container {
            text-align: center;
            margin-top: 30px;
        }

        .submit-btn {
            background-color: #4CAF50;
            color: white;
            padding: 15px 32px;
            text-align: center;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
            border: none;
        }

        .submit-btn:hover {
            background-color: #45a049;
        }


        /* Phông nền mờ khi mở form */
        #overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.6); /* Màu nền mờ */
            display: none; /* Ẩn phông mờ mặc định */
            z-index: 1;
            transition: opacity 0.3s ease;
        }

        /* Form Thêm Địa Chỉ */
        #addAddressForm {
            background-color: #ffffff;
            padding: 20px;
            width: 90%;
            max-width: 400px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            position: fixed;
            top: 20%;
            left: 50%;
            transform: translateX(-50%); /* Đảm bảo form căn giữa màn hình */
            z-index: 2;
            opacity: 0;
            display: none; /* Ẩn form mặc định */
            transition: opacity 0.3s ease-in-out;
        }

        #addAddressForm.show {
            opacity: 1;
            display: block;
        }

        /* Các trường input trong form */
        #addAddressForm input {
            width: 100%;
            padding: 12px;
            margin-bottom: 15px;
            border-radius: 8px;
            border: 1px solid #ddd;
            font-size: 14px;
            background-color: #f9f9f9;
            transition: border-color 0.3s ease;
        }

        #addAddressForm input:focus {
            border-color: #4CAF50; /* Đổi màu viền khi focus */
            outline: none;
        }

        /* Nút "Thêm Địa Chỉ" trong form */
        #addAddressForm button {
            width: 100%;
            padding: 12px;
            background-color: #4CAF50;
            color: white;
            border: none;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        #addAddressForm button:hover {
            background-color: #45a049;
        }

        /* Nút đóng form */
        #closeForm {
            font-size: 30px;
            color: #aaa;
            transition: color 0.3s ease;
        }

        #closeForm:hover {
            color: #ff0000;
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
                                    <li><a href="#"><i class="fa fa-user"></i> Account</a></li>
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
                                    <li class="dropdown"><a href="#">Shop<i class="fa fa-angle-down"></i></a>
                                        <ul role="menu" class="sub-menu">
                                            <li><a href="ProductController">Products</a></li>
                                            <li><a href="CartURL?service=checkOut" class="active">Checkout</a></li> 
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
                            <div class="search_box pull-right">
                                <input type="text" placeholder="Search"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!--/header-bottom-->
        </header><!--/header-->

        <section id="checkout_section">
            <div class="container" style="max-width: 800px; margin: auto;">
                <div class="shipping-address">
                    <h3 style="font-size: 24px; font-weight: bold; color: #333;">Shipping Information</h3>

                    <% if (error != null) {%>
                    <div style="color: red; font-weight: bold; margin-bottom: 10px;">
                        <%= error%>
                    </div>
                    <% } %>

                    <form id="orderForm" action="OrderController" method="POST">
                        <input type="hidden" name="service" value="createOrder">

                        <!-- Chọn địa chỉ giao hàng -->
                        <label for="addressSelect" style="font-weight: bold;">Select Shipping Address:</label>
                        <select name="addressSelect" id="addressSelect" required style="width: 100%; padding: 8px; margin-bottom: 10px;">
                            <% if (addresses != null && !addresses.isEmpty()) {
                                    for (Address addr : addresses) {%>
                            <option value="<%= addr.getId()%>">
                                <%= addr.getAddress()%>, <%= addr.getDistrict()%>, <%= addr.getCity()%>
                            </option>
                            <% }
                            } else { %>
                            <option value="">No address available. Please add a new one!</option>
                            <% } %>
                        </select>
                        <!-- Add New Address Button -->
                        <div style="margin-bottom: 10px; text-align: right;">
                            <button type="button" id="addAddressBtn" style="background-color: #4CAF50; color: white; padding: 12px 24px; border: none; cursor: pointer; font-size: 16px; border-radius: 5px; transition: background-color 0.3s ease;">
                                Add New Address
                            </button>
                        </div>


                        <!-- Overlay Background -->
                        <div id="overlay"></div>

                        <!-- Add New Address Form -->
                        <div id="addAddressForm">
                            <span id="closeForm" style="cursor: pointer; position: absolute; top: 10px; right: 10px; font-size: 20px; color: #aaa;">&times;</span>
                            <h3 style="text-align: center; color: #333;">Add New Address</h3>
                            <label for="newAddress">Address:</label>
                            <input type="text" id="newAddress" name="newAddress" required><br>

                            <label for="newDistrict">District:</label>
                            <input type="text" id="newDistrict" name="newDistrict" required><br>

                            <label for="newCity">City:</label>
                            <input type="text" id="newCity" name="newCity" required><br>

                            <button id="submitNewAddress">Add Address</button>
                        </div>



                        <% User user1 = (User)session.getAttribute("user"); %>
                        <h4 style="font-size: 20px; font-weight: bold; color: #555;">Recipient Information</h4>
                        <input type="text" name="newFullName" placeholder="<%=user1.getName()%>"  value="<%=user1.getName()%>"required style="width: 100%; padding: 8px; margin-bottom: 10px;">
                        <input type="text" name="newPhone" placeholder="<%=user1.getPhoneNumber()%>" value="<%=user1.getPhoneNumber()%>" required style="width: 100%; padding: 8px; margin-bottom: 10px;">

                        <div class="review-payment">
                            <h3 style="font-size: 22px; font-weight: bold; color: #333;">Review & Payment</h3>
                        </div>

                        <!-- Hiển thị giỏ hàng -->
                        <div class="cart_info">
                            <% if (cartItems != null && !cartItems.isEmpty()) {
                                    for (CartItem item : cartItems) {
                                        Product product = item.getProduct();
                                        ProductVariant variant = item.getProductVariant();
                                        Color color = item.getColor();
                                        Storage storage = item.getStorage();
                            %>
                            <div style="display: flex; align-items: center; margin-bottom: 15px; border-bottom: 1px solid #ddd; padding-bottom: 15px;">
                                <img src="<%= product.getImageURL()%>" style="width: 80px; height: 80px; margin-right: 15px;">
                                <div>
                                    <p style="margin: 0; font-weight: bold; font-size: 16px;"><%= product.getName()%></p>
                                    <p style="margin: 2px 0; font-size: 14px; color: #666;">
                                        Color: <%= color != null ? color.getColorName() : ""%> /
                                        Storage: <%= storage != null ? storage.getCapacity() : ""%>GB
                                    </p>
                                    <p style="margin: 2px 0; font-weight: bold; color: #e74c3c;">
                                        ₫<%= String.format("%,.0f", item.getPrice())%> x <%= item.getQuantity()%>
                                    </p>
                                    <p style="margin: 2px 0; font-weight: bold; color: #e74c3c;">
                                        Total: ₫<%= String.format("%,.0f", item.getTotalPrice().doubleValue())%>
                                    </p>
                                </div>
                            </div>
                            <%   }
                            } else { %>
                            <p style="text-align: center; color: #777;">Your cart is empty!</p>
                            <% }%>
                        </div>

                        <!-- hidden fields -->
                        <!-- CHÚ Ý: voucherId="" => gửi chuỗi rỗng khi không có voucher -->
                        <input type="hidden" name="discountedTotal" id="discountedTotal" value="<%= totalOrderPrice%>">
                        <input type="hidden" name="voucherId" id="voucherId" value="">

                        <h3 style="font-weight: bold; color: #e74c3c;">
                            Total: ₫<span id="totalDisplay"><%= String.format("%,.0f", totalOrderPrice)%></span>
                        </h3>

                        <hr>
                        <label for="voucherSelect" style="font-weight: bold;">Select Vouchers:</label>
                        <select name="voucherSelect" id="voucherSelect" style="width: 100%; margin-bottom: 10px;">
                            <!-- Khi người dùng không chọn voucher, value="" -->
                            <option value="">Do not apply voucher</option>

                            <% if (vouchers != null && !vouchers.isEmpty()) {
                                    for (Voucher v : vouchers) {
                            %>
                            <!-- Nếu có voucher, hiển thị chúng -->
                            <option value="<%= v.getVoucherID()%>"><%= v.getVoucherCode()%></option>
                            <%   }
                            } else { %>
                            <!-- Trường hợp không có voucher trong DB, cũng set value="" -->
                            <option value="">No voucher available</option>
                            <% } %>
                        </select>

                        <h3 style="font-size: 18px; font-weight: bold;">Select Payment Method</h3>
                        <label><input type="radio" name="paymentMethod" value="1" required> Cash on Delivery (COD)</label><br>
                        <label><input type="radio" name="paymentMethod" value="2"> Pay with VNPay</label><br>

                        <button type="submit" class="submit-btn">Place Order</button>
                    </form>
                </div>
            </div>
        </section>
        <br>
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
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
        <script src="js/updateQuantity.js"></script>
        <script>
            // Tạo map voucherId -> giá trị
            const voucherValues = {
            <% if (vouchers != null && !vouchers.isEmpty()) {
                    for (Voucher vv : vouchers) {%>
            <%= vv.getVoucherID()%>: <%= vv.getValue()%>,
            <% }
                }%>
            };

            const baseTotal = <%= totalOrderPrice%>;
            const voucherSelect = document.getElementById('voucherSelect');
            const totalDisplay = document.getElementById('totalDisplay');
            const hiddenDiscountedTotal = document.getElementById('discountedTotal');
            const voucherId = document.getElementById('voucherId'); // hidden field

            function formatCurrency(amount) {
                return amount.toLocaleString('vi-VN');
            }

            voucherSelect.addEventListener('change', function () {
                const selectedVal = this.value; // Lấy giá trị chuỗi
                if (selectedVal === "") {
                    // Không chọn voucher => discountedTotal = baseTotal
                    totalDisplay.innerText = formatCurrency(baseTotal);
                    hiddenDiscountedTotal.value = baseTotal;
                    voucherId.value = "";
                } else {
                    // parse sang số
                    const selectedId = parseInt(selectedVal) || 0;
                    const voucherValue = voucherValues[selectedId] || 0;
                    const discountedTotal = Math.max(0, baseTotal - voucherValue);

                    totalDisplay.innerText = formatCurrency(discountedTotal);
                    hiddenDiscountedTotal.value = discountedTotal;
                    voucherId.value = selectedId;
                }
            });
        </script>
        <script>// Xử lý mở và đóng form thêm địa chỉ
            document.getElementById("addAddressBtn").addEventListener("click", function () {
                var addAddressForm = document.getElementById("addAddressForm");
                var overlay = document.getElementById("overlay");

                // Kiểm tra xem form có đang hiển thị hay không
                if (addAddressForm.style.display === "none" || addAddressForm.style.display === "") {
                    addAddressForm.style.display = "block";  // Mở form
                    overlay.style.display = "block";  // Hiển thị phông mờ
                    setTimeout(function () {
                        addAddressForm.classList.add("show");
                        overlay.classList.add("show");
                    }, 10);  // Thêm class show để tạo hiệu ứng mờ
                } else {
                    addAddressForm.classList.remove("show");
                    overlay.classList.remove("show");
                    setTimeout(function () {
                        addAddressForm.style.display = "none";  // Đóng form
                        overlay.style.display = "none";  // Ẩn phông mờ
                    }, 300);  // Đợi hiệu ứng chuyển đổi hoàn thành trước khi ẩn form
                }
            });

// Xử lý đóng form khi nhấn nút "X"
            document.getElementById("closeForm").addEventListener("click", function () {
                var addAddressForm = document.getElementById("addAddressForm");
                var overlay = document.getElementById("overlay");

                addAddressForm.classList.remove("show");
                overlay.classList.remove("show");
                setTimeout(function () {
                    addAddressForm.style.display = "none";  // Đóng form
                    overlay.style.display = "none";  // Ẩn phông mờ
                }, 300);  // Đợi hiệu ứng chuyển đổi hoàn thành trước khi ẩn form
            });

// Xử lý gửi địa chỉ mới
            document.getElementById("submitNewAddress").addEventListener("click", function () {
                var newAddress = document.getElementById("newAddress").value;
                var newDistrict = document.getElementById("newDistrict").value;
                var newCity = document.getElementById("newCity").value;

                // Kiểm tra nếu các trường địa chỉ trống
                if (!newAddress || !newDistrict || !newCity) {
                    alert("Vui lòng điền đầy đủ thông tin địa chỉ.");
                    return;
                }

                var xhr = new XMLHttpRequest();
                xhr.open("POST", "CartURL", true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        var response = JSON.parse(xhr.responseText);
                        if (response.status === "success") {
                            // Thêm địa chỉ vào dropdown
                            var newOption = document.createElement("option");
                            newOption.value = response.addressId;
                            newOption.textContent = newAddress + ", " + newDistrict + ", " + newCity;
                            document.getElementById("addressSelect").appendChild(newOption);

                            // Hiển thị thông báo thành công
                            alert("Địa chỉ đã được thêm thành công!");

                            // Đóng form và ẩn phông mờ sau khi thêm thành công
                            document.getElementById("addAddressForm").style.display = "none";
                            document.getElementById("overlay").style.display = "none";

                            // Xóa dữ liệu trong các ô input
                            document.getElementById("newAddress").value = "";
                            document.getElementById("newDistrict").value = "";
                            document.getElementById("newCity").value = "";
                        } else {
                            alert("Không thể thêm địa chỉ: " + response.message);
                        }
                    }
                };
                xhr.send("service=addAddress&address=" + newAddress + "&district=" + newDistrict + "&city=" + newCity);
            });

            document.getElementById("orderForm").addEventListener("submit", function (event) {
                var addAddressForm = document.getElementById("addAddressForm");

                // Kiểm tra nếu form "Thêm địa chỉ mới" đang hiển thị
                if (addAddressForm.style.display === "block") {
                    // Nếu form thêm địa chỉ đang hiển thị, ngừng gửi form checkout
                    alert("Vui lòng hoàn tất việc thêm địa chỉ trước khi tiếp tục.");
                    event.preventDefault();  // Ngừng gửi form checkout
                }
            });
        </script>
    </body>
</html>
