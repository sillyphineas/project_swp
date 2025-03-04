<%-- 
    Document   : add_productvariant
    Created on : Mar 4, 2025, 4:54:44 AM
    Author     : Admin
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="entity.Product, entity.ProductVariant, java.util.Vector, entity.Brand" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product & Variants</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h2>Add Product Variants</h2>
        <form action="AddProductController" method="post">
            <input type="hidden" name="action" value="addProductVariant">
<div class="form-group">
                <label for="productID">Product:</label>
                <select class="form-control" name="productID" required>
                    <option value="">-- Select Product --</option>
                    <c:forEach var="product" items="${products}">
                        <option value="${product.id}">${product.name} (ID: ${product.id})</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="color">Color:</label>
                <input type="text" class="form-control" name="color" required>
            </div>

            <div class="form-group">
                <label for="storage">Storage (GB):</label>
                <input type="number" class="form-control" name="storage" required>
            </div>

            <div class="form-group">
                <label for="price">Price:</label>
                <input type="number" step="0.01" class="form-control" name="price" required>
            </div>

            <div class="form-group">
                <label for="stock">Stock:</label>
                <input type="number" class="form-control" name="stock" required>
            </div>

            <button type="submit" class="btn btn-success">Add Variant</button>
            <a href="MarketingProductController" class="btn btn-secondary">Cancel</a>
        </form>
    </div>

    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>
