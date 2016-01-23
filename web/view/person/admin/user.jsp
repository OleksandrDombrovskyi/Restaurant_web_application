<%-- 
    Document   : user
    Created on : 22.01.2016, 23:15:34
    Author     : Sasha
--%>

<%@ page contentType="text/html; charset=windows-1251" %>

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
            <fmt:message key="administration.user.text.title" />
        </h3>
        
        <h3>
            <fmt:message key="administration.user.text.firstname" />: ${concreteUser.firstName}
        </h3>
        <h3>
            <fmt:message key="administration.user.text.lastname" />: ${concreteUser.lastName}
        </h3>
        <h3>
            <fmt:message key="administration.user.text.email" />: ${concreteUser.email}
        </h3>
        <h3>
            <form action="servlet" method="get" >
                <a href="${pageContext.request.contextPath}/servlet?getAction=getUserOrders&userId=${concreteUser.id}" >
                    <fmt:message key="administration.user.link.getorders" />
                </a>
            </form>
        </h3>
    </body>
</html>
