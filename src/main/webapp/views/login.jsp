<%--
  Created by IntelliJ IDEA.
  User: chuva
  Date: 9/16/2022
  Time: 2:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Đăng nhập hệ thống</title>
</head>
<body>

<form method="POST" action="/login">
    <table border="0">
        <tr>
            <td>User Name</td>
            <td><input type="text" name="username"/></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="password"/></td>
        </tr>

        <tr>
            <td colspan="2">
                <input type="submit" value="Submit"/>
                <a href="${pageContext.request.contextPath}/admin/index">Cancel</a>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
