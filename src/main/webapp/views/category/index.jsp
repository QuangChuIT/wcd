<%@ page import="com.aptech.user.User" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<%
    List<User> userList = (List<User>) request.getAttribute("users");
%>
<h1><%=userList.size()%></h1>
<c:forEach items="${users}" var="user" >
    <tr>
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td>${user.mobile}</td>
    </tr>
</c:forEach>
<c:forEach var="window" items="${pageScope.windows}">
    <c:out value="${window}"/>
</c:forEach>
