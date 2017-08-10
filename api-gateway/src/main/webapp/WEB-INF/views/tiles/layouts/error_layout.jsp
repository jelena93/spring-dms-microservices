<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="<c:url value="/resources/img/favicon.jpg" />" />
        <!-- Bootstrap CSS -->    
        <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
        <!-- bootstrap theme -->
        <link href="<c:url value="/resources/css/bootstrap-theme.css" />" rel="stylesheet">
        <!--external css-->
        <!-- font icon -->
        <link href="<c:url value="/resources/css/elegant-icons-style.css" />" rel="stylesheet" />
        <link href="<c:url value="/resources/css/font-awesome.css" />" rel="stylesheet" />
        <!-- Custom styles -->
        <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/css/style-responsive.css" />" rel="stylesheet">
        <script src="<c:url value="/resources/js/jquery.js" />"></script>

        <sec:authentication var="user" property="principal" scope="request"/>
        <title>${title} ${error.errorCode}</title>
    </head>
    <body>
        <div class="page-404">
            <p class="text-404">${error.errorCode}</p>
            <h2>Aww Snap!</h2>
            <p>${error.errorMessage} <br><a href="${pageContext.request.contextPath}">Return Home</a></p>
        </div>
    </body>
</html>
