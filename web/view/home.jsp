<%-- 
    Document   : home
    Created on : 12.01.2016, 16:10:08
    Author     : Sasha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<fmt:setLocale value="${language}" /> 
<fmt:setBundle basename="controller.properties.text" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="home.text.title" /></title>
    </head>
    <body>
        <%-- 
        <c:if test="${not empty errorMessage}" >
            <fmt:message key="${errorMessage}" />
            <c:remove var="errorMessage" scope="session" />
        </c:if>
        <c:if test="${not empty message}" >
            <fmt:message key="${message}" />
            <c:remove var="message" scope="session" />
        </c:if>
        --%>
        <h1><fmt:message key="home.text.welcome" /></h1>
        <h2>
            <a href="${pageContext.request.contextPath}/servlet?getAction=main_menu"><fmt:message key="home.link.mainmenu" /></a>
            <a href="${pageContext.request.contextPath}/servlet?getAction=info"><fmt:message key="home.link.info" /></a>
            <a href="${pageContext.request.contextPath}/servlet?getAction=contacts"><fmt:message key="home.link.contacts" /></a>
        </h2>
    </body>
</html>
