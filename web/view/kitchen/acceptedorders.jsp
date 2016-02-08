<%-- 
    Document   : orders
    Created on : 17.01.2016, 15:46:26
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
        <title>JSP Page</title>
    </head>
    <body>
        <form action="servlet" method="get" >
            <c:if test="${empty errorMessage}" >
                <c:if test="${not empty orders}">
                    <table>
                        <tr>
                            <td><h3><fmt:message key="orders.table.orderdate" /></h3></td>
                        </tr>
                        <c:forEach items="${orders}" var="order" >
                            <tr>
                                <td>
                                    <a href="${pageContext.request.contextPath}/servlet?getAction=get_order_kitchen&orderId=${order.id}" >
                                        ${order.date}</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </c:if>
        </form>
    </body>
</html>
