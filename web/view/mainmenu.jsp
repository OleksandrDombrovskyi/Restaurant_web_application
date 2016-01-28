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
        <form action="servlet" method="post" >
            <cst:mealtable meals="${meals}" />
            <c:if test="${not empty user}" >
                <input type="submit" value=<fmt:message 
                           key="mainmenu.button.addtobasket" /> />
                <input type="hidden" name="postAction" value="add_to_basket" />
            </c:if>
        </form>
    </body>
</html>
