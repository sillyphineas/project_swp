<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.User"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Details</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h2>User Details</h2>
        
        <c:set var="user" value="${user}" />
        
        <table class="table table-bordered">
            <tr>
                <th>ID</th>
                <td>${user.id}</td>
            </tr>
            <tr>
                <th>Name</th>
                <td>${user.name}</td>
            </tr>
            <tr>
                <th>Email</th>
                <td>${user.email}</td>
            </tr>
            <tr>
                <th>Gender</th>
                <td>${user.gender ? 'Male' : 'Female'}</td>
            </tr>
            <tr>
                <th>Phone Number</th>
                <td>${user.phoneNumber}</td>
            </tr>
            <tr>
                <th>Password</th>
                <td>${user.passHash}</td>
            </tr>
            <tr>
                <th>Date of Birth</th>
                <td>${user.dateOfBirth}</td>
            </tr>
            <tr>
                <th>Role</th>
                <td>
                    <c:forEach var="role" items="${roles}">
                        <c:if test="${role.roleId == user.roleId}">
                            ${role.roleName}
                        </c:if>
                    </c:forEach>
                </td>
            </tr>
            <tr>
                <th>Status</th>
                <td class="${user.disabled ? 'inactive-status' : 'active-status'}">
                    ${user.disabled ? 'Inactive' : 'Active'}
                </td>
            </tr>
        </table>

        <a href="UserController" class="btn btn-primary">Back to User List</a>
    </div>
</body>
</html>
