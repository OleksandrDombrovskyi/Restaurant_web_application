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
        <h3><fmt:message key="basket.text.title" /></h3>
            <c:if test="${not empty basketOrder.orderItems}">
                <table>
                    <tr>
                        <td><h3><fmt:message key="basket.table.mealname" /></h3></td>
                        <td><h3><fmt:message key="basket.table.mealdescription" /></h3></td>
                        <td><h3><fmt:message key="basket.table.mealprice" /></h3></td>
                        <td><h3><fmt:message key="basket.table.mealnumber" /></h3></td>
                        <td><h3><fmt:message key="basket.table.itemprice" /></h3></td>
                    </tr>
                    <c:forEach items="${basketOrder.orderItems}" var="item" >
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
                        <td><h3><fmt:message key="basket.table.totalprice" /></h3></td>
                        <td><h3><fmt:formatNumber value="${basketOrder.totalPrice}"
                                   type="currency" currencyCode="USD" /></h3></td>
                    </tr>
                </table>
                <h3>  
                    <form action="servlet" method="post" >
                        <input type="submit" value="<fmt:message key="basket.button.confirm" />" 
                               onclick="return confirm('<fmt:message key="basket.dialogbox.doconfirm" />')"
                        />
                        <input type="hidden" name="postAction" value="basket_confirm" />
                        <input type="hidden" name="userId" value="${basketOrder.userId}" />
                        <input type="hidden" name="orderId" value="${basketOrder.id}" />
                    </form>
                    <form action="servlet" method="post" >
                        <input type="submit" value="<fmt:message key="basket.button.remove" />" 
                               onclick="return confirm('<fmt:message key="basket.dialogbox.doremove" />')"
                        />
                        <input type="hidden" name="postAction" value="clear_basket" />
                        <input type="hidden" name="orderId" value="${basketOrder.id}" />
                    </form>
                </h3> 
            </c:if>
    </body>
</html>
