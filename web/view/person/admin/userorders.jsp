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
        <h3>
            <fmt:message key="administration.user.orders.text.title" />
        </h3>
        <h4>
            <fmt:message key="administration.orders.table.username" />: 
            <a href="${pageContext.request.contextPath}/servlet?getAction=get_user&userId=${concreteUser.id}" >
                ${concreteUser.firstName}
            </a>
        </h4>
        <h4>
            <fmt:message key="administration.orders.table.userlastname" />: 
            <a href="${pageContext.request.contextPath}/servlet?getAction=get_user&userId=${concreteUser.id}" >
                ${concreteUser.lastName}
            </a>
        </h4>
        <h4>
            <fmt:message key="administration.orders.table.useremail" />: 
            <a href="${pageContext.request.contextPath}/servlet?getAction=get_user&userId=${concreteUser.id}" >
                ${concreteUser.email}
            </a>
        </h4>
        <form action="servlet" method="get" >
            <c:if test="${empty errorMessage}" >
                <c:if test="${not empty concreteUser.orders}">
                    <table>
                        <tr>
                            <td><h3><fmt:message key="administration.orders.table.ordernumber" /></h3></td>
                            <td><h3><fmt:message key="administration.orders.table.orderdate" /></h3></td>
                            <td><h3><fmt:message key="administration.orders.table.orderprice" /></h3></td>
                            <td><h3><fmt:message key="administration.orders.table.orderstatus" /></h3></td>
                        </tr>
                        <c:forEach items="${concreteUser.orders}" var="order" >
                            <tr>
                                <td>
                                    <a href="${pageContext.request.contextPath}/servlet?getAction=get_order_admin&orderId=${order.id}" >
                                        ${order.id}</a>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/servlet?getAction=get_order_admin&orderId=${order.id}" >
                                        ${order.date}</a>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/servlet?getAction=get_order_admin&orderId=${order.id}" >
                                        <fmt:formatNumber value="${order.totalPrice}" type="currency" currencyCode="USD" /></a>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/servlet?getAction=get_order_by_status&orderStatus=${order.status}" >
                                        <h3>
                                            <c:choose>
                                                <c:when test="${order.status == 'NOT_CONFIRMED'}">
                                                    <fmt:message key="order.status.notconfirmed" />
                                                </c:when>
                                                <c:when test="${order.status == 'CREATED'}">
                                                    <fmt:message key="order.status.created" />
                                                </c:when>
                                                <c:when test="${order.status == 'ACCEPTED'}">
                                                    <fmt:message key="order.status.accepted" />
                                                </c:when>
                                                <c:when test="${order.status == 'PREPARED'}">
                                                    <fmt:message key="order.status.prepared" />
                                                </c:when>
                                                <c:when test="${order.status == 'READY'}">
                                                    <fmt:message key="order.status.ready" />
                                                </c:when>
                                                <c:when test="${order.status == 'PAYED'}">
                                                    <fmt:message key="order.status.payed" />
                                                </c:when>
                                                <c:otherwise>undefined</c:otherwise>
                                            </c:choose>
                                        </h3>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </c:if>
        </form>
    </body>
</html>
