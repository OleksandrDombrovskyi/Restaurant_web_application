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
        <c:if test="${not empty errorMessage}" >
            <fmt:message key="${errorMessage}" />
            <c:remove var="errorMessage" scope="session" />
        </c:if>
        <c:if test="${not empty message}" >
            <fmt:message key="${message}" />
            <c:remove var="message" scope="session" />
        </c:if>
        <form action="servlet" method="post" >
            <h3>
                <fmt:message key="signup.text.name" />: 
                <input type="text" name="name" 
                    <c:if test="${not empty previousName}" >
                        value="${previousName}"
                    </c:if>
                       />
            </h3>
            <h3>
                <fmt:message key="signup.text.lastname" />: 
                <input type="text" name="lastname" 
                    <c:if test="${not empty previousLastName}" >
                        value="${previousLastName}"
                    </c:if>
                       />
            </h3>
            <h3>
                <fmt:message key="signup.text.email" />: 
                <input type="text" name="email" 
                    <c:if test="${not empty previousEmail}" >
                        value="${previousEmail}"
                    </c:if>
                       />
            </h3>
            <h3><fmt:message key="signup.text.password" />: <input type="text" name="password" /></h3>
            <h3><fmt:message key="signup.text.confirmpassword" />: <input type="text" name="confirmPassword" /></h3>
            <input type="submit" value=<fmt:message key="signup.button.createaccount" /> />
            <input type="hidden" name="postAction" value="createAccount" />
            <input type="hidden" name="from" value="${param.from}">
        </form>
    </body>
</html>
