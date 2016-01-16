<%-- 
    Document   : MainMenu
    Created on : 11.01.2016, 18:38:31
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
        <title><fmt:message key="mainmenu.text.title" /></title>
    </head>
    <body>
        <c:if test="${not empty errorMessage}" >
            <fmt:message key="${errorMessage}" />
        </c:if>
        <h3>
            <c:if test="${empty user}" >
                <fmt:message key="mainmenu.message.loginplease" />
            </c:if>
        </h3>
        <c:if test="${not empty meals}" >
            <table>
                <c:forEach items="${meals}" var="meal">
                    <tr>
                        <td><c:out value="${meal.name}" /></td>
                        <td><c:out value="${meal.description}"/></td>
                        <td><fmt:formatNumber value="${meal.price}" 
                                          type="currency" currencyCode="USD"/>
                        </td>
                        <c:if test="${not empty user}" >
                            <td>
                                <form action="servlet" method="POST">
                                    <input type="submit" value=<fmt:message 
                                               key="mainmenu.button.add" /> />
                                    <input type="hidden" name="id" 
                                           value="${meal.id}" />
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach> 
            </table>
        </c:if>
    </body>
</html>
