<%-- 
    Document   : MainMenu
    Created on : 11.01.2016, 18:38:31
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
        <title><fmt:message key="mainmenu.text.title" /></title>
    </head>
    <body>
        <h3>
            <c:if test="${empty user}" >
                <fmt:message key="mainmenu.message.loginplease" />
            </c:if>
        </h3>
        
        <%--
        <c:if test="${not empty meals}" >
        --%>
            <form action="servlet" method="post" >
                
                <%--
                <table>
                    <tr>
                        <td><h3><fmt:message key="order.table.mealname" /></h3></td>
                        <td><h3><fmt:message key="order.table.mealdescription" /></h3></td>
                        <td><h3><fmt:message key="order.table.mealprice" /></h3></td>
                    </tr>
                    <c:forEach items="${meals}" var="meal">
                        <tr>
                            <td><c:out value="${meal.name}" /></td>
                            <td><c:out value="${meal.description}"/></td>
                            <td><fmt:formatNumber value="${meal.price}" type="currency" currencyCode="USD" /></td>
                            <c:if test="${not empty user}" >
                                <td>
                                    <select name="${meal.id}" >
                                        <c:forEach begin="0" end="10" var="number" >
                                            <option value="${number}" >${number}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach> 
                </table>
                
                
                
                <cst:mealtable rows="${mealIterator.size}">
                    <c:set var="meal" value="${mealIterator.meal}" />
                    <td><c:out value="${meal.name}" /></td>
                    <td><c:out value="${meal.description}"/></td>
                    <td><fmt:formatNumber value="${meal.price}" type="currency" currencyCode="USD" /></td>
                    <c:if test="${not empty user}" >
                        <td>
                            <select name="${meal.id}" >
                                <c:forEach begin="0" end="10" var="number" >
                                    <option value="${number}" >${number}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </c:if>
                </cst:mealtable>
                --%>
                
                <cst:mealtable meals="${meals}" />
                
                <c:if test="${not empty user}" >
                    <input type="submit" value=<fmt:message 
                               key="mainmenu.button.addtobasket" /> />
                    <input type="hidden" name="postAction" value="add_to_basket" />
                </c:if>
            </form>
                <%--
        </c:if>
                --%>
    </body>
</html>
