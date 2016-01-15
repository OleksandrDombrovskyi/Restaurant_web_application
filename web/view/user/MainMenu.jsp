<%-- 
    Document   : MainMenu
    Created on : 11.01.2016, 18:38:31
    Author     : Sasha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="user.mainmenu.text.title" /></title>
    </head>
    <body>
        <table>
            <c:forEach items="${meals}" var="meal">
                <tr>
                    <td><c:out value="${meal.name}" /></td>
                    <td><c:out value="${meal.description}"/></td>
                    <td><fmt:formatNumber value="${meal.price}" type="currency" currencyCode="USD"/></td>
                    <td>
                        <form action="servlet" method="POST">
                            <input type="submit" value=<fmt:message key="user.mainmenu.button.add" /> />
                            <input type="hidden" name="id" value="${meal.id}" />
                        </form>
                    </td>
                </tr>
            </c:forEach> 
        </table>
    </body>
</html>
