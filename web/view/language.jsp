<%-- 
    Document   : language
    Created on : 12.01.2016, 16:13:31
    Author     : Sasha
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
--%>



<%@ page contentType="text/html; charset=windows-1251" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" /> 
<fmt:setBundle basename="controller.properties.text" />

<!DOCTYPE html>
<html lang="${language}">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
        <title><fmt:message key="${title}" /></title>
    </head>
    <body>
        <form>
            <select name="language" onchange="submit()"  >
                <option value="en" ${language == 'en' ? 'selected' : ''}>English</option> 
                <option value="ru" ${language == 'ru' ? 'selected' : ''}>???????</option>
                <option value="ua" ${language == 'ua' ? 'selected' : ''}>?????????</option>
                <input type="hidden" name="getAction" value="change_language" />
            </select>
        </form>
    </body>
</html>
