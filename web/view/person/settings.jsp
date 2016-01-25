<%-- 
    Document   : settings
    Created on : 18.01.2016, 0:52:13
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
        <h3>
            <%--  
            <c:if test="${not empty errorMessage}" >
                <fmt:message key="${errorMessage}" />
                <c:remove var="errorMessage" scope="session" />
            </c:if>
            <c:if test="${not empty message}" >
                <fmt:message key="${message}" />
                <c:remove var="message" scope="session" />
            </c:if>
            --%>
        </h3>
        <form action="servlet" method="post" >
            <h3>
                <fmt:message key="settings.text.name" />: 
                <input type="text" name="firstName" value="${firstName}" />
            </h3>
            <h3>
                <fmt:message key="settings.text.lastname" />: 
                <input type="text" name="lastName" value="${lastName}" />
            </h3>
            <h3>
                <fmt:message key="settings.text.email" />: 
                <input type="text" name="email" value="${email}" />
            </h3>
            <input type="submit" value=<fmt:message key="settings.button.savechanges" /> />
            <c:if test="${not empty user}" >
                <input type="hidden" name="postAction" value="userSaveChanges" />
            </c:if>
            <c:if test="${not empty admin}" >
                <input type="hidden" name="postAction" value="adminSaveChanges" />
            </c:if>
        </form>
            <hr/>
        <form action="servlet" method="post" >
            <h3>
                <fmt:message key="settings.text.oldpassword" />: 
                <input type="text" name="oldPassword" />
            </h3>
            <h3>
                <fmt:message key="settings.text.newpassword" />: 
                <input type="text" name="newPassword" />
            </h3>
            <h3>
                <fmt:message key="settings.text.confirmpassword" />: 
                <input type="text" name="confirmPassword" />
            </h3>
            <input type="submit" value=<fmt:message key="settings.button.changepassword" /> />
            <c:if test="${not empty user}" >
                <input type="hidden" name="postAction" value="userChangePassword" />
            </c:if>
            <c:if test="${not empty admin}" >
                <input type="hidden" name="postAction" value="adminChangePassword" />
            </c:if>
        </form>
    </body>
</html>
