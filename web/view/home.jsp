<%-- 
    Document   : home
    Created on : 12.01.2016, 16:10:08
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
        <title><fmt:message key="home.text.title" /></title>
    </head>
    <body>
        <c:if test="${not empty errorMessage}" >
            <fmt:message key="${errorMessage}" />
        </c:if>
        <h1><fmt:message key="home.text.welcome" /></h1>
        <h2><a href="${pageContext.request.contextPath}/servlet?action=mainMenu"><fmt:message key="home.link.mainmenu" /></a></h2>
    </body>
</html>
