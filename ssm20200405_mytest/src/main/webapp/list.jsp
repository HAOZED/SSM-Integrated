<%--
  Created by IntelliJ IDEA.
  User: china
  Date: 2020/4/6
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>list</title>
</head>
<body>
<h2>findAll</h2>
<table>
    <tr>
        <td>课程编号</td>
        <td>课程名</td>
        <td>课程地址</td>
    </tr>
    <c:forEach items="${list}" var="course">
        <tr>
            <td>${course.cid}</td>
            <td>${course.cname}</td>
            <td>${course.clocal}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
