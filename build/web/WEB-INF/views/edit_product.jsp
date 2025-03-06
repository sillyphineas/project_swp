<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="entity.Product, entity.ProductVariant, java.util.Vector, entity.Brand" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Product & Variants</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h2>Edit Product</h2>
        
        <c:if test="${not empty product}">
            <form action="MarketingProductDetails" method="post">
                <input type="hidden" name="action" value="editProduct">
                <input type="hidden" name="id" value="${product.id}">

                <div class="form-group">
                    <label for="name">Product Name:</label>
                    <input type="text" class="form-control" id="name" name="name" value="${product.name}" required>
                </div>

                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea class="form-control" id="description" name="description" required>${product.description}</textarea>
                </div>

                <div class="form-group">
                    <label for="brandID">Brand:</label>
                    <select class="form-control" id="brandID" name="brandID">
                        <c:forEach var="brand" items="${brands}">
                            <option value="${brand.id}" ${brand.id == product.brandID ? 'selected' : ''}>${brand.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="chipset">Chipset:</label>
                    <input type="text" class="form-control" id="chipset" name="chipset" value="${product.chipset}">
                </div>

                <div class="form-group">
                    <label for="ram">RAM (GB):</label>
                    <input type="number" class="form-control" id="ram" name="ram" value="${product.ram}" min="0">
                </div>

                <div class="form-group">
                    <label for="screenSize">Screen Size (inches):</label>
                    <input type="number" class="form-control" id="screenSize" name="screenSize" step="any" value="${product.screenSize}">
                </div>

                <div class="form-group">
                    <label for="screenType">Screen Type:</label>
                    <input type="text" class="form-control" id="screenType" name="screenType" value="${product.screenType}">
                </div>

                <div class="form-group">
                    <label for="resolution">Resolution:</label>
                    <input type="text" class="form-control" id="resolution" name="resolution" value="${product.resolution}">
                </div>

                <div class="form-group">
                    <label for="batteryCapacity">Battery Capacity (mAh):</label>
                    <input type="number" class="form-control" id="batteryCapacity" name="batteryCapacity" value="${product.batteryCapacity}" min="0">
                </div>

                <div class="form-group">
                    <label for="os">Operating System:</label>
                    <input type="text" class="form-control" id="os" name="os" value="${product.os}">
                </div>

                <div class="form-group">
                    <label for="connectivity">Connectivity:</label>
                    <input type="text" class="form-control" id="connectivity" name="connectivity" value="${product.connectivity}">
                </div>

                <div class="form-group">
                    <label for="imageURL">Image URL:</label>
                    <input type="text" class="form-control" id="imageURL" name="imageURL" value="${product.imageURL}">
                </div>

                <div class="form-group">
                    <label>Status:</label>
                    <select class="form-control" name="status">
                        <option value="Available" ${product.status == 'Available' ? 'selected' : ''}>Available</option>
                        <option value="Out of Stock" ${product.status == 'Out of Stock' ? 'selected' : ''}>Out of Stock</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Disable Product:</label>
                    <input type="checkbox" name="isDisabled" ${product.isDisabled ? 'checked' : ''}>
                </div>

                <button type="submit" class="btn btn-primary">Save Changes</button>
                <a href="MarketingProductDetails?id=${product.id}" class="btn btn-secondary">Cancel</a>
            </form>
        </c:if>

        <h2>Edit Product Variants</h2>
        <c:forEach var="variant" items="${variants}">
            <form action="MarketingProductDetails" method="post">
                <input type="hidden" name="action" value="editVariant">
                <input type="hidden" name="id" value="${variant.id}">
                <input type="hidden" name="productID" value="${product.id}">
                
                <div class="form-group">
                    <label for="color">Color:</label>
                    <input type="text" class="form-control" name="color" value="${variant.color}" required>
                </div>
                
                <div class="form-group">
                    <label for="storage">Storage (GB):</label>
                    <input type="number" class="form-control" name="storage" value="${variant.storage}" required>
                </div>
                
                <div class="form-group">
                    <label for="price">Price:</label>
                    <input type="number" step="0.01" class="form-control" name="price" value="${variant.price}" required>
                </div>
                
                <div class="form-group">
                    <label for="stock">Stock:</label>
                    <input type="number" class="form-control" name="stock" value="${variant.stock}" required>
                </div>
                
                <button type="submit" class="btn btn-success">Update Variant</button>
                <a href="MarketingProductDetails?id=${product.id}" class="btn btn-secondary">Cancel</a>
            </form>
        </c:forEach>
    </div>

    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>
