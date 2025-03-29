<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="entity.User"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add Voucher | E-Shopper</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        <link href="css/responsive.css" rel="stylesheet">
        <style>
            .form-group { margin-bottom: 15px; }
            .form-control { width: 100%; padding: 8px; }
            .container { max-width: 600px; margin-top: 50px; }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Add New Voucher</h2>
            <form action="MarketingVoucherCotroller" method="POST">
                <input type="hidden" name="service" value="addVoucher">

                <div class="form-group">
                    <label for="voucherCode">Voucher Code:</label>
                    <input type="text" class="form-control" id="voucherCode" name="voucherCode" required>
                </div>

                <div class="form-group">
                    <label for="voucherTypeID">Voucher Type:</label>
                    <select class="form-control" id="voucherTypeID" name="voucherTypeID" required>
                        <option value="">Select Voucher Type</option>
                        <c:forEach var="type" items="${voucherTypes}">
                            <option value="${type.voucherTypeID}">${type.typeName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea class="form-control" id="description" name="description" rows="3"></textarea>
                </div>

                <div class="form-group">
                    <label for="point">Points:</label>
                    <input type="number" class="form-control" id="point" name="point" required>
                </div>

                <div class="form-group">
                    <label for="startDate">Start Date:</label>
                    <input type="date" class="form-control" id="startDate" name="startDate" required>
                </div>

                <div class="form-group">
                    <label for="expiredDate">Expiration Date:</label>
                    <input type="date" class="form-control" id="expiredDate" name="expiredDate" required>
                </div>

                <div class="form-group">
                    <label for="usageLimit">Usage Limit:</label>
                    <input type="number" class="form-control" id="usageLimit" name="usageLimit" value="1" required>
                </div>

                <div class="form-group">
                    <label for="isDisabled">Status:</label>
                    <select class="form-control" id="isDisabled" name="isDisabled">
                        <option value="false">Active</option>
                        <option value="true">Inactive</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="value">Value:</label>
                    <input type="number" class="form-control" id="value" name="value" required>
                </div>

                <button type="submit" class="btn btn-primary">Add Voucher</button>
                <a href="MarketingVoucherCotroller?service=displayVouchers" class="btn btn-secondary">Cancel</a>
            </form>
        </div>
    </body>
</html>