<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Order Management | E-Shopper</title>

    <!-- CSS (Bootstrap, FontAwesome, etc.) -->
    <link href="css/bootstrap.min.css" rel="stylesheet" />
    <link href="css/font-awesome.min.css" rel="stylesheet" />
    <link href="css/prettyPhoto.css" rel="stylesheet" />
    <link href="css/price-range.css" rel="stylesheet" />
    <link href="css/animate.css" rel="stylesheet" />
    <link href="css/main.css" rel="stylesheet" />
    <link href="css/responsive.css" rel="stylesheet" />

    <style>
      /* Container for the order list */
      #my-orders-section {
        background: #f9f9f9;
        padding: 15px;
        border-radius: 4px;
      }

      /* Tabs style */
      .my-order-tabs button {
        padding: 8px 12px;
        border: 1px solid #ddd;
        background: #fff;
        margin-right: 5px;
        cursor: pointer;
        border-radius: 4px;
      }
      .my-order-tabs button.active {
        border-bottom: 2px solid #e74c3c;
        font-weight: bold;
      }

      /* Hide sections by default */
      .order-section {
        margin-bottom: 30px;
        display: none; /* Hidden by default; shown via JS */
      }

      /* Order card container */
      .my-order-card {
        background: #fff;
        border: 1px solid #ddd;
        border-radius: 5px;
        margin-bottom: 20px;
        padding: 15px;
      }

      /* We'll use .order-row to align items horizontally */
      .order-row {
        display: flex;
        align-items: center;
        justify-content: space-between;
      }

      /* Left column: image + product info */
      .order-left {
        display: flex;
        align-items: center;
        gap: 10px;
      }
      .order-left img {
        width: 90px;
        height: 90px;
        object-fit: cover;
        border: 1px solid #ccc;
        border-radius: 4px;
      }
      .order-details {
        display: flex;
        flex-direction: column;
      }

      /* Right column: status, total price, buttons, etc. */
      .order-right {
        text-align: right;
        display: flex;
        flex-direction: column;
        align-items: flex-end;
        gap: 6px;
        min-width: 160px; /* optional, to keep consistent width */
      }

      /* Status color classes */
      .order-status {
        font-weight: bold;
        color: #f1c40f; /* default color */
      }
      .order-status.completed {
        color: #27ae60;
      }
      .order-status.canceled {
        color: #e74c3c;
      }
      .order-status.pending {
        color: #f1c40f;
      }
      .order-status.shipping {
        color: #3498db;
      }
      .order-status.returning {
        color: #9b59b6;
      }

      .total-price {
        font-weight: bold;
        color: #e67e22;
      }

      /* Buttons/links */
      .btn-link {
        color: #3498db;
        text-decoration: none;
      }
      .btn-link:hover {
        text-decoration: underline;
      }
      .buy-again-btn {
        background-color: #ff9800;
        color: #fff;
        border: none;
        padding: 6px 12px;
        border-radius: 4px;
        cursor: pointer;
      }
      .buy-again-btn:hover {
        background-color: #e68a00;
      }

      /* Pagination styling */
      .pagination {
        text-align: center;
        margin-top: 10px;
      }
      .pagination a,
      .pagination span {
        margin: 0 5px;
        color: #555;
        text-decoration: none;
        font-weight: bold;
        display: inline-block;
        padding: 6px 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
      }
      .pagination a:hover {
        background-color: #f3f3f3;
      }
      .pagination .active-page {
        background-color: #ff9800;
        color: #fff;
        border-color: #ff9800;
      }
    </style>
  </head>
  <body>
    <!-- ================= HEADER START ================= -->
    <header id="header">
      <!-- Header Top -->
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
      <!-- Header Middle -->
      <div class="header-middle">
        <div class="container">
          <div class="row">
            <div class="col-sm-4">
              <div class="logo pull-left">
                <a href="HomePageController">
                  <img src="images/home/logo.png" alt="E-Shopper Logo" />
                </a>
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
                  <li>
                    <a href="${pageContext.request.contextPath}/UserProfileServlet"
                      ><i class="fa fa-user"></i> Account</a
                    >
                  </li>
                  <li>
                    <a href="CustomerOrderController"
                      ><i class="fa fa-shopping-cart"></i> My Orders</a
                    >
                  </li>
                  <li>
                    <a href="${pageContext.request.contextPath}/CartURL"
                      ><i class="fa fa-shopping-cart"></i> Cart</a
                    >
                  </li>
                  <li>
                    <a style="font-weight: bold"
                      ><i class="fa fa-hand-o-up"></i> Hello, <%= user.getEmail() %></a
                    >
                  </li>
                  <li>
                    <a href="${pageContext.request.contextPath}/LogoutController"
                      ><i class="fa fa-power-off"></i> Logout</a
                    >
                  </li>
                  <%
                    } else {
                  %>
                  <li>
                    <a href="${pageContext.request.contextPath}/LoginController"
                      ><i class="fa fa-lock"></i> Login</a
                    >
                  </li>
                  <%
                    }
                  %>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- Header Bottom -->
      <div class="header-bottom">
        <div class="container">
          <div class="row">
            <div class="col-sm-9">
              <div class="navbar-header">
                <button
                  type="button"
                  class="navbar-toggle"
                  data-toggle="collapse"
                  data-target=".navbar-collapse"
                >
                  <span class="sr-only">Toggle navigation</span>
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
                </button>
              </div>
              <div class="mainmenu pull-left">
                <ul class="nav navbar-nav collapse navbar-collapse">
                  <li><a href="HomePageController" class="active">Home</a></li>
                  <li class="dropdown">
                    <a href="#">Shop<i class="fa fa-angle-down"></i></a>
                    <ul role="menu" class="sub-menu">
                      <li><a href="ProductController">Products</a></li>
                      <li><a href="CartURL?service=checkOut">Checkout</a></li>
                      <li><a href="CartURL">Cart</a></li>
                    </ul>
                  </li>
                  <li class="dropdown">
                    <a href="BlogURL?service=listAllBlogs"
                      >Blog<i class="fa fa-angle-down"></i
                    ></a>
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
                  <button type="submit" class="btn btn-default">
                    <i class="fa fa-search"></i>
                  </button>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </header>
    <!-- ================= HEADER END ================= -->

    <section id="my-orders-section" class="container" style="margin-top: 30px;">
      <!-- Tabs -->
      <div class="my-order-tabs row" style="margin-bottom: 20px;">
        <div class="col-sm-12">
          <button data-tab="all">All</button>
          <button data-tab="pending">Awaiting Pickup</button>
          <button data-tab="shipping">Shipping</button>
          <button data-tab="delivered">Delivered</button>
          <button data-tab="canceled">Canceled</button>
          <button data-tab="return">Refund</button>
        </div>
      </div>

      <!-- ALL ORDERS TAB -->
      <div id="tab-all" class="order-section">
        <c:forEach var="order" items="${allOrdersPaged}">
          <div class="my-order-card" id="order-all-${order.id}">
            <!-- Single row that aligns image+info on left, status+price+buttons on right -->
            <div class="order-row">
              <!-- Left: image + product details -->
              <div class="order-left">
                <img src="${order.imageURL}" alt="Product Image" />
                <div class="order-details">
                  <strong>${order.productName}</strong>
                  <div>Color: ${order.colorName}, Capacity: ${order.capacity}</div>
                  <div>Quantity: ${order.quantity}</div>
                  <!-- If you want to show payment method here, uncomment:
                  <div>Payment method: ${order.paymentName}</div> 
                  -->
                </div>
              </div>
              <!-- Right: status, price, buttons -->
              <div class="order-right">
                <div class="order-status ${order.orderStatus}">${order.orderStatus}</div>
                <div class="total-price">
                  <fmt:formatNumber value="${order.totalPrice}" type="number" groupingUsed="true"/> ₫
                </div>
                <a href="#" class="btn-link">View Order Details</a>
                <c:if test="${order.orderStatus == 'Awaiting Pickup'}">
                  <a
                    href="javascript:void(0);"
                    class="btn-link cancel-order"
                    data-orderid="${order.id}"
                    style="color: red; font-weight: bold;"
                  >
                    Cancel
                  </a>
                </c:if>
              </div>
            </div>
          </div>
        </c:forEach>

        <!-- Pagination for All Orders -->
        <div class="pagination">
          <c:forEach begin="1" end="${totalAllPages}" var="p">
            <c:choose>
              <c:when test="${p == currentAllPage}">
                <span class="active-page">${p}</span>
              </c:when>
              <c:otherwise>
                <a href="CustomerOrderController?service=displayAllOrders&pageAll=${p}&activeTab=all#tab-all"
                  >${p}</a
                >
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </div>
      </div>

      <!-- AWAITING PICKUP TAB -->
      <div id="tab-pending" class="order-section">
        <h3>Awaiting Pickup</h3>
        <c:forEach var="order" items="${awaitingPickupPaged}">
          <div class="my-order-card" id="order-pending-${order.id}">
            <div class="order-row">
              <!-- Left -->
              <div class="order-left">
                <img src="${order.imageURL}" alt="Product Image" />
                <div class="order-details">
                  <strong>${order.productName}</strong>
                  <div>Color: ${order.colorName}, Capacity: ${order.capacity}</div>
                  <div>Quantity: ${order.quantity}</div>
                </div>
              </div>
              <!-- Right -->
              <div class="order-right">
                <div class="order-status pending">${order.orderStatus}</div>
                <div class="total-price">
                  <fmt:formatNumber value="${order.totalPrice}" type="number" groupingUsed="true"/> ₫
                </div>
                <a href="#" class="btn-link">View Order Details</a>
                <a
                  href="javascript:void(0);"
                  class="btn-link cancel-order"
                  data-orderid="${order.id}"
                  style="color: red; font-weight: bold;"
                >
                  Cancel
                </a>
              </div>
            </div>
          </div>
        </c:forEach>
        <!-- Pagination for Awaiting Pickup -->
        <div class="pagination">
          <c:forEach begin="1" end="${totalAwaitingPages}" var="p">
            <c:choose>
              <c:when test="${p == currentAwaitingPage}">
                <span class="active-page">${p}</span>
              </c:when>
              <c:otherwise>
                <a
                  href="CustomerOrderController?service=displayAllOrders&pageAwaiting=${p}&activeTab=pending#tab-pending"
                  >${p}</a
                >
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </div>
      </div>

      <!-- SHIPPING TAB -->
      <div id="tab-shipping" class="order-section">
        <h3>Shipping</h3>
        <c:forEach var="order" items="${shippingPaged}">
          <div class="my-order-card" id="order-shipping-${order.id}">
            <div class="order-row">
              <div class="order-left">
                <img src="${order.imageURL}" alt="Product Image" />
                <div class="order-details">
                  <strong>${order.productName}</strong>
                  <div>Color: ${order.colorName}, Capacity: ${order.capacity}</div>
                  <div>Quantity: ${order.quantity}</div>
                </div>
              </div>
              <div class="order-right">
                <div class="order-status shipping">${order.orderStatus}</div>
                <div class="total-price">
                  <fmt:formatNumber value="${order.totalPrice}" type="number" groupingUsed="true"/> ₫
                </div>
                <a href="#" class="btn-link">View Order Details</a>
              </div>
            </div>
          </div>
        </c:forEach>
        <!-- Pagination for Shipping -->
        <div class="pagination">
          <c:forEach begin="1" end="${totalShippingPages}" var="p">
            <c:choose>
              <c:when test="${p == currentShippingPage}">
                <span class="active-page">${p}</span>
              </c:when>
              <c:otherwise>
                <a
                  href="CustomerOrderController?service=displayAllOrders&pageShipping=${p}&activeTab=shipping#tab-shipping"
                  >${p}</a
                >
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </div>
      </div>

      <!-- DELIVERED TAB -->
      <div id="tab-delivered" class="order-section">
        <h3>Delivered</h3>
        <c:forEach var="order" items="${deliveredPaged}">
          <div class="my-order-card" id="order-delivered-${order.id}">
            <div class="order-row">
              <div class="order-left">
                <img src="${order.imageURL}" alt="Product Image" />
                <div class="order-details">
                  <strong>${order.productName}</strong>
                  <div>Color: ${order.colorName}, Capacity: ${order.capacity}</div>
                  <div>Quantity: ${order.quantity}</div>
                </div>
              </div>
              <div class="order-right">
                <div class="order-status completed">${order.orderStatus}</div>
                <div class="total-price">
                  <fmt:formatNumber value="${order.totalPrice}" type="number" groupingUsed="true"/> ₫
                </div>
                <a href="#" class="btn-link">View Order Details</a>
                <!-- Nút Refund cho Delivered -->
                <a
                  href="javascript:void(0);"
                  class="btn-link refund-order"
                  data-orderid="${order.id}"
                  style="color: orange; font-weight: bold;"
                >
                  Request Refund
                </a>
              </div>
            </div>
          </div>
        </c:forEach>
        <!-- Pagination for Delivered -->
        <div class="pagination">
          <c:forEach begin="1" end="${totalDeliveredPages}" var="p">
            <c:choose>
              <c:when test="${p == currentDeliveredPage}">
                <span class="active-page">${p}</span>
              </c:when>
              <c:otherwise>
                <a
                  href="CustomerOrderController?service=displayAllOrders&pageDelivered=${p}&activeTab=delivered#tab-delivered"
                  >${p}</a
                >
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </div>
      </div>

      <!-- CANCELED TAB -->
      <div id="tab-canceled" class="order-section">
        <h3>Canceled</h3>
        <c:forEach var="order" items="${cancelPaged}">
          <div class="my-order-card" id="order-canceled-${order.id}">
            <div class="order-row">
              <div class="order-left">
                <img src="${order.imageURL}" alt="Product Image" />
                <div class="order-details">
                  <strong>${order.productName}</strong>
                  <div>Color: ${order.colorName}, Capacity: ${order.capacity}</div>
                  <div>Quantity: ${order.quantity}</div>
                </div>
              </div>
              <div class="order-right">
                <div class="order-status canceled">${order.orderStatus}</div>
                <div class="total-price">
                  <fmt:formatNumber value="${order.totalPrice}" type="number" groupingUsed="true"/> ₫
                </div>
                <button class="buy-again-btn">Buy Again</button>
                <a href="#" class="btn-link">View Order Details</a>
              </div>
            </div>
          </div>
        </c:forEach>
        <!-- Pagination for Canceled -->
        <div class="pagination">
          <c:forEach begin="1" end="${totalCanceledPages}" var="p">
            <c:choose>
              <c:when test="${p == currentCanceledPage}">
                <span class="active-page">${p}</span>
              </c:when>
              <c:otherwise>
                <a
                  href="CustomerOrderController?service=displayAllOrders&pageCanceled=${p}&activeTab=canceled#tab-canceled"
                  >${p}</a
                >
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </div>
      </div>

      <!-- REFUND TAB -->
      <div id="tab-return" class="order-section">
        <h3>Return/Refund</h3>
        <c:forEach var="order" items="${refundPaged}">
          <div class="my-order-card" id="order-refund-${order.id}">
            <div class="order-row">
              <div class="order-left">
                <img src="${order.imageURL}" alt="Product Image" />
                <div class="order-details">
                  <strong>${order.productName}</strong>
                  <div>Color: ${order.colorName}, Capacity: ${order.capacity}</div>
                  <div>Quantity: ${order.quantity}</div>
                </div>
              </div>
              <div class="order-right">
                <div class="order-status returning">${order.orderStatus}</div>
                <div class="total-price">
                  <fmt:formatNumber value="${order.totalPrice}" type="number" groupingUsed="true"/> ₫
                </div>
                <a href="#" class="btn-link">View Order Details</a>
              </div>
            </div>
          </div>
        </c:forEach>
        <!-- Pagination for Refund -->
        <div class="pagination">
          <c:forEach begin="1" end="${totalRefundPages}" var="p">
            <c:choose>
              <c:when test="${p == currentRefundPage}">
                <span class="active-page">${p}</span>
              </c:when>
              <c:otherwise>
                <a
                  href="CustomerOrderController?service=displayAllOrders&pageRefund=${p}&activeTab=return#tab-return"
                  >${p}</a
                >
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </div>
      </div>
    </section>

    <!-- ================= FOOTER START ================= -->
    <footer id="footer">
      <div class="footer-top">
        <div class="container">
          <div class="row">
            <div class="col-sm-2">
              <div class="companyinfo">
                <h2><span>e</span>-shopper</h2>
                <p>
                  Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
                  eiusmod tempor
                </p>
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
                <h2>Quick Shop</h2>
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
                  <li><a href="">Privacy Policy</a></li>
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
                  <li><a href="">Affiliate Program</a></li>
                  <li><a href="">Copyright</a></li>
                </ul>
              </div>
            </div>
            <div class="col-sm-3 col-sm-offset-1">
              <div class="single-widget">
                <h2>About Shopper</h2>
                <form action="#" class="searchform">
                  <input type="text" placeholder="Your email address" />
                  <button type="submit" class="btn btn-default">
                    <i class="fa fa-arrow-circle-o-right"></i>
                  </button>
                  <p>Get the most recent updates from our site and be updated yourself...</p>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="footer-bottom">
        <div class="container">
          <div class="row">
            <p class="pull-left">Copyright © 2013 E-SHOPPER. All rights reserved.</p>
            <p class="pull-right">
              Designed by
              <span><a target="_blank" href="http://www.themeum.com">Themeum</a></span>
            </p>
          </div>
        </div>
      </div>
    </footer>
    <!-- ================= FOOTER END ================= -->

    <!-- JS libs -->
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.scrollUp.min.js"></script>
    <script src="js/price-range.js"></script>
    <script src="js/jquery.prettyPhoto.js"></script>
    <script src="js/main.js"></script>
    <script src="js/cart.js"></script>

    <script>
      // Lấy param activeTab từ server (nếu có)
      const serverActiveTab = "<c:out value='${param.activeTab}' default='all'/>";

      // Hàm hiển thị tab
      function showTab(tab) {
        // Ẩn tất cả order-section
        document.querySelectorAll(".order-section").forEach((sec) => (sec.style.display = "none"));
        // Hiển thị tab mong muốn
        const tabDiv = document.getElementById("tab-" + tab);
        if (tabDiv) {
          tabDiv.style.display = "block";
        }
        // Bỏ active trên tất cả button
        document.querySelectorAll(".my-order-tabs button").forEach((btn) => {
          btn.classList.remove("active");
        });
        // Tìm button có data-tab=xxx => active
        const activeBtn = document.querySelector('.my-order-tabs button[data-tab="' + tab + '"]');
        if (activeBtn) {
          activeBtn.classList.add("active");
        }
      }

      // Khi trang load, hiển thị tab theo param activeTab
      document.addEventListener("DOMContentLoaded", () => {
        if (serverActiveTab) {
          showTab(serverActiveTab);
        } else {
          showTab("all");
        }
      });

      // Gắn sự kiện click cho các button tab
      document.querySelectorAll(".my-order-tabs button").forEach((btn) => {
        btn.addEventListener("click", () => {
          const tabName = btn.getAttribute("data-tab");
          showTab(tabName);
        });
      });

      // Ajax for Cancel / Refund
      $(document).ready(function () {
        // Cancel order
        $(".cancel-order").click(function () {
          let orderId = $(this).data("orderid");
          if (confirm("Are you sure you want to cancel this order?")) {
            $.ajax({
              url: "CustomerOrderController",
              type: "GET",
              data: { service: "cancelOrder", orderId: orderId, ajax: "true" },
              dataType: "json",
              success: function (response) {
                if (response.success) {
                  alert(response.message);
                  // Update status in tab All
                  let $cardAll = $("#order-all-" + orderId);
                  if ($cardAll.length) {
                    $cardAll
                      .find(".order-status")
                      .text("Canceled")
                      .removeClass("pending awaiting shipping delivered returning")
                      .addClass("canceled");
                  }
                  // Remove from Awaiting Pickup tab
                  let $cardPending = $("#order-pending-" + orderId);
                  if ($cardPending.length) {
                    $cardPending.remove();
                  }
                  // Optionally, add to Canceled tab
                  if ($cardAll.length) {
                    let $clone = $cardAll.clone();
                    $clone.attr("id", "order-canceled-" + orderId);
                    $clone
                      .find(".order-status")
                      .text("Canceled")
                      .removeClass("pending awaiting shipping delivered returning")
                      .addClass("canceled");
                    $clone
                      .find(".order-footer")
                      .prepend('<button class="buy-again-btn">Buy Again</button>');
                    $("#tab-canceled").append($clone);
                  }
                } else {
                  alert(response.message);
                }
              },
              error: function () {
                alert("An error occurred while processing your request.");
              },
            });
          }
        });

        // Refund order
        $(".refund-order").click(function () {
          let orderId = $(this).data("orderid");
          if (confirm("Are you sure you want to request a refund? (Only for Delivered)")) {
            $.ajax({
              url: "CustomerOrderController",
              type: "GET",
              data: { service: "refundOrder", orderId: orderId, ajax: "true" },
              dataType: "json",
              success: function (response) {
                if (response.success) {
                  alert(response.message);
                  let $cardAll = $("#order-all-" + orderId);
                  if ($cardAll.length) {
                    $cardAll
                      .find(".order-status")
                      .text("Refund")
                      .removeClass("delivered completed")
                      .addClass("returning");
                  }
                  let $cardDelivered = $("#order-delivered-" + orderId);
                  if ($cardDelivered.length) {
                    $cardDelivered.remove();
                  }
                  if ($cardAll.length) {
                    let $clone = $cardAll.clone();
                    $clone.attr("id", "order-refund-" + orderId);
                    $clone
                      .find(".order-status")
                      .text("Refund")
                      .removeClass("delivered completed")
                      .addClass("returning");
                    $("#tab-return").append($clone);
                  }
                } else {
                  alert(response.message);
                }
              },
              error: function () {
                alert("An error occurred while processing your request.");
              },
            });
          }
        });
      });
    </script>
  </body>
</html>
