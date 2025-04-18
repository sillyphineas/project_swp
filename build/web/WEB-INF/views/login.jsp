<%-- 
    Document   : login
    Created on : Jan 18, 2025, 1:13:47 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Login | E-Shopper</title>
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
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script type="text/javascript">
            function register() {
                let form = document.registerForm;

                let email = form.email.value;
                let password = form.password.value;
                let confirmPassword = form.confirmPassword.value;

                // Thêm các trường mới:
                let name = form.name.value;
                let gender = form.gender.value;         // "1" hoặc "0"
                let phoneNumber = form.phoneNumber.value;
                let dateOfBirth = form.dateOfBirth.value; // "YYYY-MM-DD" (theo HTML <input type="date">)

                // Kiểm tra tính hợp lệ của form
                if (!form.checkValidity()) {
                    document.getElementById("msg").innerHTML = "Please fill in all required fields.";
                    return;
                }

                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState == 4 && xhttp.status == 200) {
                        if (xhttp.responseText.trim() === "cancel") {
                            window.location.href = "<%= request.getContextPath()%>/VerifyAccountController?service=cancel";
                        } else if (xhttp.responseText.trim() === "redirect") {
                            window.location.href = "<%= request.getContextPath()%>/VerifyAccountController?service=forward";
                        } else {
                            document.getElementById("msg").innerHTML = xhttp.responseText;
                        }
                    }
                };

                xhttp.open("POST", "<%= request.getContextPath()%>/RegisterController", true);
                xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                // Gửi thêm name, gender, phoneNumber, dateOfBirth
                xhttp.send(
                        "email=" + encodeURIComponent(email)
                        + "&password=" + encodeURIComponent(password)
                        + "&confirmPassword=" + encodeURIComponent(confirmPassword)
                        + "&name=" + encodeURIComponent(name)
                        + "&gender=" + encodeURIComponent(gender)
                        + "&phoneNumber=" + encodeURIComponent(phoneNumber)
                        + "&dateOfBirth=" + encodeURIComponent(dateOfBirth)
                        );
            }


            function login() {
                let form = document.loginForm;
                let email = form.email.value;
                let password = form.password.value;

                if (!form.checkValidity()) {
                    document.getElementById("error").innerHTML = "Please fill in all required fields.";
                    return;
                }
                var xhttp = new XMLHttpRequest();

                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState == 4 && xhttp.status == 200) {
                        if (xhttp.responseText.trim().startsWith("success")) {
                            console.log(xhttp.responseText);
                            let roleId = xhttp.responseText.split(":")[1];
                            console.log(roleId);
                            if (roleId == 1) {
                                window.location.href = "<%= request.getContextPath()%>/AdminDashboardController";
                            } else if (roleId == 5) {
                                window.location.href = "<%= request.getContextPath()%>/HomePageController";
                            } else if (roleId == 4) {
                                window.location.href = "<%= request.getContextPath()%>/ShipperDashboardController";
                            } else if (roleId == 3) {
                                window.location.href = "<%= request.getContextPath()%>/salesDashboardController";
                            } else if (roleId == 2) {
                                window.location.href = "<%= request.getContextPath()%>/MarketingDashboardController";

                            }
                        } else {
                            document.getElementById("error").innerHTML = xhttp.responseText;
                        }
                    }
                };

                xhttp.open("POST", "<%= request.getContextPath()%>/LoginController", true);
                xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhttp.send("email=" + email +
                        "&password=" + password);
            }
        </script>


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
                                    <!--                                    <li><a href="UserProfileServlet"><i class="fa fa-user"></i> Account</a></li>
                                                                        <li><a href=""><i class="fa fa-star"></i> Wishlist</a></li>
                                                                        <li><a href="checkout.html"><i class="fa fa-crosshairs"></i> Checkout</a></li>
                                                                        <li><a href="CartURL?service=checkOut"><i class="fa fa-shopping-cart"></i> Cart</a></li>-->
                                    <li><a href="LoginController" class="active"><i class="fa fa-lock"></i> Login</a></li>
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
                                            <li><a href="checkout.html">Checkout</a></li> 
                                            <li><a href="CartURL">Cart</a></li> 
                                        </ul>
                                    </li> 
                                    <li class="dropdown"><a href="#">Blog<i class="fa fa-angle-down"></i></a>
                                        <ul role="menu" class="sub-menu">
                                            <li><a href="BlogURL">Blog List</a></li>
                                        </ul>
                                    </li> 
                                    <!--                                    <li><a href="404.html">404</a></li>
                                                                        <li><a href="contact-us.html">Contact</a></li>-->
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

        <section id="form"><!--form-->
            <div class="container">
                <c:if test="${not empty successMessage}">
                    <div style="color:green;">
                        ${successMessage}
                    </div>
                </c:if>
                <div class="row">
                    <div class="col-sm-4 col-sm-offset-1">

                        <div class="login-form"><!--login form-->

                            <h2>Login to your account</h2>
                            <form name="loginForm">
                                <input type="email" name="email" placeholder="Email"  required/>
                                <input type="password" name="password" placeholder="Password" maxlength="18" required/>
                                <div id="error" style="color: red; font-style: italic;"></div>
                                <a href="ResetController?service=findYourAccount">Forgotten password?</a>
                                <button type="button" class="btn btn-default" onclick="login()">Login</button>
                            </form>
                        </div>

                    </div>
                    <div class="col-sm-1">
                        <h2 class="or">OR</h2>
                    </div>
                    <div class="col-sm-4">
                        <div class="signup-form">
                            <h2>New User Signup!</h2>
                            <form name="registerForm">
                                <!-- Name -->
                                <input type="text" name="name" placeholder="Full Name" maxlength="40" required/>
                                
                                <!-- Gender (Male / Female). Tùy bạn chọn hiển thị radio, select,... -->
                                <select name="gender" required style="margin-bottom: 10px;">
                                    <option value="">--Select Gender--</option>
                                    <option value="1">Male</option>
                                    <option value="0">Female</option>
                                </select>

                                <!-- Phone Number -->
                                <input type="text" name="phoneNumber" placeholder="Phone Number" required maxlength="10"/>

                                <!-- Date of Birth -->
                                <input type="date" name="dateOfBirth" placeholder="Date of Birth" required/>
                                
                                <!-- Email -->
                                <input type="email" name="email" placeholder="Email" required/>

                                <!-- Password -->
                                <input type="password" name="password" placeholder="Password" maxlength="18" required/>

                                <!-- Confirm Password -->
                                <input type="password" name="confirmPassword" placeholder="Confirm Password" maxlength="18" required/>

                                <p id="msg" style="color: red; font-style: italic;"></p>
                                <button type="button" class="btn btn-default" onclick="register()">Register</button>
                            </form>
                        </div>


                    </div>
                </div>
            </div>
        </section><!--/form-->

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
                                    <button type="submit"
                                            class="btn btn-default"><i class="fa fa-arrow-circle-o-right"></i></button>
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