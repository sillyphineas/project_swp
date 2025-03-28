<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="entity.Voucher"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="entity.User, java.util.List, jakarta.servlet.http.HttpSession, model.DAOUser"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Voucher List | E-Shopper</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <link href="css/prettyPhoto.css" rel="stylesheet">
        <link href="css/price-range.css" rel="stylesheet">
        <link href="css/animate.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        <link href="css/responsive.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <style>
            .inactive-status { color: red; font-weight: bold; }
            .active-status { color: green; font-weight: bold; }
            .d-flex { display: flex; align-items: center; justify-content: space-between; }
            .add-setting-button { display: flex; justify-content: flex-end; margin-top: 20px; }
            .form-control { width: 300px; font-size: 16px; padding: 10px; height: 40px; }
            .form-control option { white-space: nowrap; height: auto; }
            .new-pagination-area { text-align: center; margin-bottom: 20px; }
            .new-pagination { list-style: none; padding: 0; margin: 0; display: inline-flex; }
            .new-pagination li { margin: 0 5px; }
            .new-pagination li a { display: inline-block; width: 35px; height: 35px; text-align: center; line-height: 35px; border-radius: 50%; background-color: #f1f1f1; color: #333; text-decoration: none; font-weight: bold; font-size: 14px; transition: 0.3s; }
            .new-pagination li.new-active a { background-color: #ff8c00 !important; color: white !important; }
            .new-pagination li a:hover { background-color: #ddd; }
            .footer { margin-top: 50px; text-align: center; font-size: 14px; color: #777; }
        </style>
        <style>
            #notification { position: fixed; top: 20px; left: 50%; transform: translateX(-50%); background-color: #cce5ff; color: #004085; padding: 15px 30px; border-radius: 5px; display: none; opacity: 0; transition: opacity 0.5s ease-in-out; z-index: 9999; }
            #notification.show { display: block; opacity: 1; }
        </style>
    </head>
    <body>
        <header id="header">
            <div class="header_top">
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
            </div>

            <div class="header-middle">
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
            </div>

            <div class="header-bottom">
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
                                    <li><a href="MarketingVoucherCotroller">Voucher List</a></li>
                                </ul>
                            </div>
                        </div>
                        
                    </div>
                </div>
            </div>
        </header>

        <div class="container">
            <h2>Voucher List</h2>

            <!-- Add Voucher Button -->
            <div class="add-setting-button">
                <a href="MarketingVoucherCotroller?service=addVoucher" class="btn btn-primary">Add New Voucher</a>
            </div>

            <!-- Search and Filter Form -->
            <form action="MarketingVoucherConroller" method="GET" class="form-row d-flex justify-content-between align-items-center mb-3">
                <input type="hidden" name="service" value="searchVouchers">
                <div class="form-group mb-0">
                    <label for="searchKeyword" class="mr-2">Search:</label>
                    <input type="text" id="searchKeyword" name="keyword" class="form-control" placeholder="Enter voucher code or description">
                </div>
                <button type="submit" class="btn btn-warning">Search</button>
                <a href="MarketingVoucherCotroller?service=displayVouchers" class="btn btn-link">Reset</a>
            </form>

            <hr>

            <!-- Voucher List Table -->
            <table class="table table-bordered">
                <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Voucher Code</th>
                        <th>Type</th>
                        <th>Description</th>
                        <th>Points</th>
                        <th>Start Date</th>
                        <th>Expiration Date</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="voucher" items="${voucherList}">
                        <tr>
                            <td>${voucher.voucherID}</td>
                            <td>${voucher.voucherCode}</td>
                            <td>${voucherTypeMap[voucher.voucherTypeID]}</td>
                            <td>${voucher.description}</td>
                            <td>${voucher.point}</td>
                            <td>${voucher.startDate}</td>
                            <td>${voucher.expiredDate}</td>
                            <td class="${voucher.isDisabled ? 'text-danger' : 'text-success'}">${voucher.isDisabled ? 'Inactive' : 'Active'}</td>
                            <td>
                                <div class="btn-group">
                                    <a href="MarketingVoucherCotroller?service=updateVoucher&voucherId=${voucher.voucherID}" class="btn btn-info btn-sm">Edit</a>
                                    <a href="MarketingVoucherCotroller?service=deleteVoucher&voucherId=${voucher.voucherID}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?');">Delete</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Pagination -->
            <div class="pagination">
                <ul class="pagination">
                    <c:if test="${currentPage > 1}">
                        <li><a href="MarketingVoucherCotroller?service=displayVouchers&page=${currentPage - 1}">« Prev</a></li>
                    </c:if>

                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <li class="${i == currentPage ? 'active' : ''}">
                            <a href="MarketingVoucherCotroller?service=displayVouchers&page=${i}">${i}</a>
                        </li>
                    </c:forEach>

                    <c:if test="${currentPage < totalPages}">
                        <li><a href="MarketingVoucherCotroller?service=displayVouchers&page=${currentPage + 1}">Next »</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </body>
</html>