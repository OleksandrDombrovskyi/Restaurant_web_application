<%-- 
    Document   : info
    Created on : 20.01.2016, 11:59:23
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
        <title>Info</title>
    </head>
    <body>
        <h3><fmt:message key="info.text.firstline" /></h3>
        <h3><fmt:message key="info.text.aboutus" /></h3>
    </body>
</html>
