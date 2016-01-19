<%-- 
    Document   : navigation
    Created on : 18.01.2016, 17:56:55
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
        <hr/>
        <h4>
            <table>
                <c:forEach items="${links}" var="concreteLink" >
                    <tr>
                        <a href="${pageContext.request.contextPath}${concreteLink.linkValue}" >
                            <fmt:message key="${concreteLink.linkName}" />
                        </a>
                        > 
                    </tr>
                </c:forEach>
            </table>
        </h4>
    </body>
</html>
