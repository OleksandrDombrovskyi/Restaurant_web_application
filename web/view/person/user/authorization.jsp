<%-- 
    Document   : LogInLogOut
    Created on : 10.01.2016, 23:16:02
    Author     : Sasha
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
--%>

<%@ page contentType="text/html; charset=windows-1251" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<fmt:setLocale value="${language}" /> 
<fmt:setBundle basename="controller.properties.text" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
        <title><fmt:message key="user.authorization.text.title" /></title>
    </head>
    <body>
        <h4>${user.firstName} | 
            <a href="${pageContext.request.contextPath}/servlet?getAction=logout"><fmt:message key="user.authorization.link.logout"/></a> 
            <a href="${pageContext.request.contextPath}/servlet?getAction=profile"><fmt:message key="user.authorization.link.profile" /></a>
        </h4>
    </body>
</html>
