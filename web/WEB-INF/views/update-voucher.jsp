<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Voucher | E-Shopper</title>
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
            <h2>Edit Voucher</h2>
            <form action="MarketingVoucherController" method="POST">
                <input type="hidden" name="service" value="updateVoucher">
                <input type="hidden" name="voucherId" value="${voucher.voucherID}">

                <div class="form-group">
                    <label for="voucherCode">Voucher Code:</label>
                    <input type="text" class="form-control" id="voucherCode" name="voucherCode" value="${voucher.voucherCode}" required>
                </div>

                <div class="form-group">
                    <label for="voucherTypeID">Voucher Type:</label>
                    <select class="form-control" id="voucherTypeID" name="voucherTypeID" required>
                        <option value="">Select Voucher Type</option>
                        <c:forEach var="type" items="${voucherTypes}">
                            <option value="${type.voucherTypeID}" ${type.voucherTypeID == voucher.voucherTypeID ? 'selected' : ''}>${type.typeName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea class="form-control" id="description" name="description" rows="3">${voucher.description}</textarea>
                </div>

                <div class="form-group">
                    <label for="point">Points:</label>
                    <input type="number" class="form-control" id="point" name="point" value="${voucher.point}" required>
                </div>

                <div class="form-group">
                    <label for="startDate">Start Date:</label>
                    <input type="date" class="form-control" id="startDate" name="startDate" value="${voucher.startDate}" required>
                </div>

                <div class="form-group">
                    <label for="expiredDate">Expiration Date:</label>
                    <input type="date" class="form-control" id="expiredDate" name="expiredDate" value="${voucher.expiredDate}" required>
                </div>

                <div class="form-group">
                    <label for="usageLimit">Usage Limit:</label>
                    <input type="number" class="form-control" id="usageLimit" name="usageLimit" value="${voucher.usageLimit}" required>
                </div>

                <div class="form-group">
                    <label for="isDisabled">Status:</label>
                    <select class="form-control" id="isDisabled" name="isDisabled">
                        <option value="false" ${!voucher.isDisabled ? 'selected' : ''}>Active</option>
                        <option value="true" ${voucher.isDisabled ? 'selected' : ''}>Inactive</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="value">Value:</label>
                    <input type="number" class="form-control" id="value" name="value" value="${voucher.value}" required>
                </div>

                <button type="submit" class="btn btn-primary">Update Voucher</button>
                <a href="MarketingVoucherController?service=displayVouchers" class="btn btn-secondary">Cancel</a>
            </form>
        </div>
    </body>
</html>