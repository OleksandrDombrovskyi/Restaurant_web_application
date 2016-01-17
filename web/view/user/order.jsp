<%-- 
    Document   : order
    Created on : 17.01.2016, 2:15:31
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
        <title><fmt:message key="${title}" /></title>
    </head>
    <body>
        <h3><fmt:message key="order.text.ordernumber" /> ${order.id}</h3>
        <table>
            <tr>
                <td><fmt:message key="order.table.mealname" /></td>
                <td><fmt:message key="order.table.mealdescription" /></td>
                <td><fmt:message key="order.table.mealprice" /></td>
                <td><fmt:message key="order.table.mealnumber" /></td>
                <td><fmt:message key="order.table.itemprice" /></td>
            </tr>
            <c:forEach items="${items}" var="item" >
                <tr>
                    <td><c:out value="${item.meal.name}" /></td>
                    <td><c:out value="${item.meal.description}" /></td>
                    <td><fmt:formatNumber value="${item.meal.price}" 
                           type="currency" currencyCode="USD" /></td>
                    <td><c:out value="${item.mealAmount}" /></td>
                    <td><fmt:formatNumber value="${item.totalPrice}"
                           type="currency" currencyCode="USD" /></td>
                </tr>
            </c:forEach>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td><h3><fmt:message key="order.table.totalprice" /></h3></td>
                <td><h3><fmt:formatNumber value="${order.totalPrice}"
                           type="currency" currencyCode="USD" /></h3></td>
            </tr>
        </table>
        <form action="servlet" method="post" >
            <input type="submit" value=<fmt:message key="order.button.confirm" /> />
            <input type="hidden" name="button" value="confirm" />
        </form>
            <form action="servlet" method="post" >
            <input type="submit" value=<fmt:message key="order.button.remove" /> />
            <input type="hidden" name="button" value="remove" />
        </form>
    </body>
</html>
