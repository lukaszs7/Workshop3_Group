<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Solutions</title>
    <style>
        table, th, td {
            padding: 2px;
            border: 1px solid black;
        }
    </style>
</head>
<body style="margin: 30px 30px 30px 30px">
<h1>Last solutions</h1>
<table>
    <tr>
        <th>Exercise name</th>
        <th>Solution author</th>
        <th>Date</th>
        <th>Actions</th>
    </tr>
    <c:forEach items="${solutions}" var="solution">
        <tr>
            <td><c:out value="${solution.exercise.title}"/></td>
            <td><c:out value="${solution.author.username}"/></td>
            <td><c:out value="${solution.createdAt}"/></td>
            <td><a href="/solutions/${solution.id}">Details</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
