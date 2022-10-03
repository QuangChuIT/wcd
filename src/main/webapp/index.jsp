<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    response.sendRedirect("/admin/index");
%>

<h2>${cookie.userlogon.value}</h2>