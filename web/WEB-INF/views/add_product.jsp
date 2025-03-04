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
            <h2>Add Product</h2>

            <form action="AddProductController?action=addProduct" method="post">


                <div class="form-group">
                    <label for="name">Product Name:</label>
                    <input type="text" class="form-control" id="name" name="name" required>
                </div>

                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea class="form-control" id="description" name="description" required></textarea>
                </div>

                <div class="form-group">
                    <label for="brandID">Brand:</label>
                    <select class="form-control" id="brandID" name="brandID">
                        <c:forEach var="brand" items="${brands}">
                            <option value="${brand.id}">${brand.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="chipset">Chipset:</label>
                    <input type="text" class="form-control" id="chipset" name="chipset">
                </div>

                <div class="form-group">
                    <label for="ram">RAM (GB):</label>
                    <input type="number" class="form-control" id="ram" name="ram" min="0">
                </div>

                <div class="form-group">
                    <label for="screenSize">Screen Size (inches):</label>
                    <input type="number" class="form-control" id="screenSize" name="screenSize" step="any">
                </div>

                <div class="form-group">
                    <label for="screenType">Screen Type:</label>
                    <input type="text" class="form-control" id="screenType" name="screenType">
                </div>
                <div class="form-group">
                    <label for="screenType">CameraSpecs:</label>
                    <input type="text" class="form-control" id="cameraSpecs" name="cameraSpecs">
                </div>
                <div class="form-group">
                    <label for="screenType">Feedback Count:</label>
                    <input type="number" class="form-control" id="feedbackCount" name="feedbackCount">
                </div>
                <div class="form-group">
                    <label for="screenType">SimType:</label>
                    <input type="text" class="form-control" id="simType" name="simType">
                </div>

                <div class="form-group">
                    <label for="resolution">Resolution:</label>
                    <input type="text" class="form-control" id="resolution" name="resolution">
                </div>

                <div class="form-group">
                    <label for="batteryCapacity">Battery Capacity (mAh):</label>
                    <input type="number" class="form-control" id="batteryCapacity" name="batteryCapacity" min="0">
                </div>

                <div class="form-group">
                    <label for="os">Operating System:</label>
                    <input type="text" class="form-control" id="os" name="os">
                </div>

                <div class="form-group">
                    <label for="connectivity">Connectivity:</label>
                    <input type="text" class="form-control" id="connectivity" name="connectivity">
                </div>
                <div class="form-group">
                    <label for="createAt">Creation Date:</label>
                    <input type="date" class="form-control" id="createAt" name="createAt">
                </div>
                <div class="form-group">
                    <label for="imageURL">Image URL:</label>
                    <input type="text" class="form-control" id="imageURL" name="imageURL">
                </div>

                <div class="form-group">
                    <label>Status:</label>
                    <select class="form-control" name="status">
                        <option value="Available">Available</option>
                        <option value="Out of Stock">Out of Stock</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Disable Product:</label>
                    <input type="checkbox" name="isDisabled" ${product.isDisabled ? 'checked' : ''}>
                </div>
                <button type="submit" class="btn btn-primary">Add Product</button>
                <a href="MarketingProductController" class="btn btn-secondary">Cancel</a>
            </form>


        </div>

        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
