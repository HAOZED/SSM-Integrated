<%--
  Created by IntelliJ IDEA.
  User: china
  Date: 2020/4/6
  Time: 10:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>index</title>
</head>
<body>
    <a href="findAll.do">findAll.do</a>
    <form action="Insert.do" method="post">
        <tr>
            <td>课程名</td>
            <td><input type="text" name="cname"></td>
        </tr>
        <tr>
            <td>课程位置</td>
            <td><input type="text" name="clocal"></td>
        </tr>
        <tr>
            <td><button type="submit" value="添加">添加</button></td>
        </tr>
    </form>
</body>
</html>
