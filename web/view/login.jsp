<%-- 
    Document   : login
    Created on : 09.01.2016, 4:04:27
    Author     : Sasha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" /> 
<fmt:setBundle basename="controller.properties.text" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="login.text.title" /></title>
    </head>
    <body>
        <form action="servlet" method="post">
            <fmt:message key="login.text.name" />: <input type="text" name="name"><br>
            <fmt:message key="login.text.password" />: <input type="text" name="password"><br>
            <input type="submit" value=<fmt:message key="login.button.login" />>
            <input type="hidden" name="button" value="login" />
            <input type="hidden" name="from" value="${param.from}">
        </form>
    </body>
</html>
