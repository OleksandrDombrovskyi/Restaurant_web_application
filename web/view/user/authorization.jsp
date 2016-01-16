<%-- 
    Document   : LogInLogOut
    Created on : 10.01.2016, 23:16:02
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
        <title><fmt:message key="user.authorization.text.title" /></title>
    </head>
    <body>
        <h4>${user.firstName} | 
            <a href="${pageContext.request.contextPath}/servlet?action=logout"><fmt:message key="user.authorization.link.logout"/></a> 
            <a href="${pageContext.request.contextPath}/servlet?action=profile"><fmt:message key="user.authorization.link.profile" /></a>
        </h4> 
    </body>
</html>
