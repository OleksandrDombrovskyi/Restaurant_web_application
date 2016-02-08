<%-- 
    Document   : LogInLogOut
    Created on : 10.01.2016, 23:16:02
    Author     : Sasha
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
--%>

<%@ page contentType="text/html; charset=windows-1251" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<fmt:setLocale value="${language}" /> 
<fmt:setBundle basename="controller.properties.text" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
    </head>
    <body>
        <h4>
            <form name="submitForm" method="POST" action="${pageContext.request.contextPath}/servlet?postAction=logout">
                ${kitchen.name} | 
                   <a href="javascript:document.submitForm.submit()"><fmt:message key="user.authorization.link.logout"/></A>
                   
                    <a href="${pageContext.request.contextPath}/servlet?getAction=show_accepted_orders"><fmt:message key="kitchen.authorization.link.showorders"/></a>
            </form>
        </h4>
    </body>
</html>
