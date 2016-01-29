<%-- 
    Document   : order
    Created on : 17.01.2016, 2:15:31
    Author     : Sasha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="cst" uri="customtags" %>
<fmt:setLocale value="${language}" /> 
<fmt:setBundle basename="controller.properties.text" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="${title}" /></title>
    </head>
    <body>
        <h3><fmt:message key="order.text.ordernumber" /> ${order.id}</h3>
        <h5>
            <fmt:formatDate type="time" value="${order.date}" />
            <fmt:formatDate type="date" value="${order.date}" />
        </h5>
        <cst:ordertable order="${order}" />
        <cst:orderstatus order="${order}" />
            <h3>  
                <form action="servlet" method="post" >
                    <cst:userconfirmbutton order="${order}" />
                </form>
            </h3>   
    </body>
</html>
