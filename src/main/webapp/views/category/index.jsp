<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page import="com.aptech.user.User" %>
<%@ page import="java.util.List" %>
<h1>JSTL demo</h1>
<c:out value="Hello jstl"/>

<c:set value="C2007L" var="name" scope="application"/>
<c:if test="${applicationScope.name != ''}">
    <h1>Hello ${applicationScope.name}</h1>
</c:if>
<c:choose>
    <c:when test="${applicationScope.name != ''}">
        <h1>Hello ${applicationScope.name}</h1>
    </c:when>
    <c:otherwise>
        <h1>No name</h1>
    </c:otherwise>
</c:choose>
<c:forEach items="${requestScope.categories}" var="cateroy">
    <div style="color: red">
        ${cateroy.title}
    </div>
</c:forEach>