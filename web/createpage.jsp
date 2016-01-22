<%-- 
    Document   : sethead
    Created on : 21.01.2016, 19:33:32
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
        <title><fmt:message key="${title}" /></title>
    </head>
    <body>
        <%-- include language block --%>
        <jsp:include page="/view/language.jsp" flush="true"/>
        
        <%-- include authorization block --%>
        <c:if test="${not empty user}" >
            <jsp:include page="/view/person/user/authorization.jsp" flush="true" />
        </c:if>
        <c:if test="${not empty admin}" >
            <jsp:include page="/view/person/admin/authorization.jsp" flush="true" />
        </c:if>
        <c:if test="${empty user and empty admin}" >
            <jsp:include page="/view/guest/authorization.jsp" flush="true" />
        </c:if>
        
        <%-- include navigation link panel --%>
        <jsp:include page="/view/navigation.jsp" />
        
        <%-- include required page by relative URI from request parameter --%>
        <jsp:include page="${relativeURI}" flush="true" />
    </body>
</html>
