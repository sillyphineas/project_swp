<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Product</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Arial', sans-serif;
        }
        .container {
            max-width: 900px;
            margin-top: 50px;
            padding: 30px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            margin-bottom: 30px;
            color: #007bff;
        }
        .form-label {
            font-weight: bold;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .variant {
            margin-bottom: 20px;
            padding: 20px;
            background-color: #f1f3f5;
            border-radius: 8px;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        }
        .variant input {
            margin-bottom: 10px;
        }
        button {
            width: 100%;
            padding: 12px;
            font-size: 16px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        button:hover {
            background-color: #0056b3;
        }
        .form-control {
            border-radius: 5px;
        }
        textarea.form-control {
            height: 120px;
        }
        .btn-group {
            margin-top: 20px;
            text-align: center;
        }
       
    </style>
</head>
<body>
    <div class="container">
        <h2>Add New Product</h2>
        <form action="MarketingProductDetails" method="get">
            <input type="hidden" name="action" value="editProduct">
            
            <div class="form-group">
                <label class="form-label" for="brandID">Brand:</label>
                <select name="brandID" class="form-control">
                    <c:forEach var="brand" items="${brands}">
                        <option value="${brand.id}">${brand.name}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label class="form-label" for="name">Product Name:</label>
                <input type="text" name="name" class="form-control" required>
            </div>

            <div class="form-group">
                <label class="form-label" for="description">Description:</label>
                <textarea name="description" class="form-control" placeholder="Enter product description"></textarea>
            </div>

            <div class="form-group">
                        <label for="image">Image: </label>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text">Upload</span>
                            </div>
                            <div class="custom-file">
                                <input type="file" class="custom-file-input" id="image" name="image" onchange="displayImage(this)">
                                <label class="custom-file-label" >Choose file</label>
                            </div>
                        </div>
                        <img id="previewImage" src="#" alt="Preview"
                             style="display: none; max-width: 300px; max-height: 300px;">

            </div>

            <div class="form-group">
                <label class="form-label" for="chipset">Chipset:</label>
                <input type="text" name="chipset" class="form-control" required>
            </div>

            <div class="form-group">
                <label class="form-label" for="ram">RAM (GB):</label>
                <input type="number" name="ram" class="form-control" required>
            </div>

            <div class="form-group">
                <label class="form-label" for="screenSize">Screen Size (Inches):</label>
                <input type="number" step="0.1" name="screenSize" class="form-control" required>
            </div>

            <div class="form-group">
                <label class="form-label" for="screenType">Screen Type:</label>
                <input type="text" name="screenType" class="form-control" required>
            </div>

            <div class="form-group">
                <label class="form-label" for="resolution">Resolution:</label>
                <input type="text" name="resolution" class="form-control" required>
            </div>

            <div class="form-group">
                <label class="form-label" for="batteryCapacity">Battery Capacity (mAh):</label>
                <input type="number" name="batteryCapacity" class="form-control" required>
            </div>

            <div class="form-group">
                <label class="form-label" for="cameraSpecs">Camera Specifications:</label>
                <input type="text" name="cameraSpecs" class="form-control" required>
            </div>

            <div class="form-group">
                <label class="form-label" for="os">Operating System:</label>
                <input type="text" name="os" class="form-control" required>
            </div>

            <div class="form-group">
                <label class="form-label" for="simType">SIM Type:</label>
                <input type="text" name="simType" class="form-control" required>
            </div>

            <div class="form-group">
                <label class="form-label" for="connectivity">Connectivity:</label>
                <input type="text" name="connectivity" class="form-control" required>
            </div>

            <h3>Add Product Variants</h3>
            <div id="variantContainer">
                <div class="variant">
                    <div class="form-group">
                        <label class="form-label" for="color">Color:</label>
                        <input type="text" name="color" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="storage">Storage (GB):</label>
                        <input type="number" name="storage" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="price">Price ($):</label>
                        <input type="number" step="0.01" name="price" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="stock">Stock:</label>
                        <input type="number" name="stock" class="form-control" required>
                    </div>
                </div>
            </div>

            <div class="btn-group">
                <button type="submit" class="btn btn-primary">editProduct</button>
            </div>
        </form>
    </div>
</body>
</html>
<script>
     function displayImage(input) {
        var previewImage = document.getElementById("previewImage");
        var file = input.files[0];
        var reader = new FileReader();

        reader.onload = function (e) {
            previewImage.src = e.target.result;
            previewImage.style.display = "block";
        }

        reader.readAsDataURL(file);
    }

</script>