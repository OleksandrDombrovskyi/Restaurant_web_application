<%-- 
    Document   : administration
    Created on : 22.01.2016, 20:05:18
    Author     : Sasha
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
        <title>JSP Page</title>
    </head>
    <body>
        <c:if test="${not empty errorMessage}" >
            <fmt:message key="${errorMessage}" />
            <c:remove var="errorMessage" scope="session" />
        </c:if>
        <c:if test="${not empty message}" >
            <fmt:message key="${message}" />
            <c:remove var="message" scope="session" />
        </c:if>
        
        <h3>
            <a href="${pageContext.request.contextPath}/servlet?getAction=getUsers" >
                <fmt:message key="administration.link.getusers" />
            </a>
        </h3>
        <h3>
            <a href="${pageContext.request.contextPath}/servlet?getAction=getAllOrders" >
                <fmt:message key="administration.link.getorders" />
            </a>
        </h3>
        <h3>
            <a href="${pageContext.request.contextPath}/servlet?getAction=editMenu" >
                <fmt:message key="administration.link.editmainmenu" />
            </a>
        </h3> 
    </body>
</html>
