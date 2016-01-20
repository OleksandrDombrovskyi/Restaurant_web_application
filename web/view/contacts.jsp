<%-- 
    Document   : contacts
    Created on : 20.01.2016, 12:08:12
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
        <title>Contacts</title>
    </head>
    <body>
        <h1><fmt:message key="contacts.text.firstline" /></h1>
        <h3><fmt:message key="contacts.text.address" /></h3>
        <h3><fmt:message key="contacts.text.phone" /></h3>
    </body>
</html>
