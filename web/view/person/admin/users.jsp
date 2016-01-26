<%-- 
    Document   : users
    Created on : 22.01.2016, 20:28:21
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
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
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
           
        <h3>
            <form action="servlet" method="get" >
                <c:if test="${empty errorMessage}" >
                    <c:if test="${not empty users}">
                        <table>
                            <tr>
                                <td><h3><fmt:message key="administration.users.table.userid" /></h3></td>
                                <td><h3><fmt:message key="administration.users.table.firstname" /></h3></td>
                                <td><h3><fmt:message key="administration.users.table.lastname" /></h3></td>
                                <td><h3><fmt:message key="administration.users.table.email" /></h3></td>
                            </tr>
                            <c:forEach items="${users}" var="user" >
                                <tr>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/servlet?getAction=get_user&userId=${user.id}" >
                                            ${user.id}</a>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/servlet?getAction=get_user&userId=${user.id}" >
                                            ${user.firstName}</a>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/servlet?getAction=get_user&userId=${user.id}" >
                                            ${user.lastName}</a>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/servlet?getAction=get_user&userId=${user.id}" >
                                        ${user.email}</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                </c:if>
            </form>
        </h3>
    </body>
</html>