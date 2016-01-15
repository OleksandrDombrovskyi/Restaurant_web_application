<%-- 
    Document   : signup
    Created on : 15.01.2016, 12:58:14
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
        <form action="servlet" method="post" >
            <h3><fmt:message key="signup.text.name" />: <input type="text" name="name" /></h3>
            <h3><fmt:message key="signup.text.lastname" />: <input type="text" name="lastname" /></h3>
            <h3><fmt:message key="signup.text.email" />: <input type="text" name="email" /></h3>
            <h3><fmt:message key="signup.text.password" />: <input type="text" name="password" /></h3>
            <h3><fmt:message key="signup.text.confirmpassword" />: <input type="text" name="confirmpassword" /></h3>
            <input type="submit" value=<fmt:message key="signup.button.createaccount" /> />
            <input type="hidden" name="button" value="createaccount" />
            <input type="hidden" name="from" value="${param.from}">
            <c:if test="${not empty errormessage}" >
                <fmt:message key="${errormessage}" />
            </c:if>
        </form>
    </body>
</html>
