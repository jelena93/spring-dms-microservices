<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:importAttribute name="cssList"/>
<tiles:importAttribute name="jsList"/>
<tiles:importAttribute name="shortcut_icon"/>
<link rel="shortcut icon" href="<c:url value="${shortcut_icon}" />" />
<c:forEach var="css" items="${cssList}">
    <link href="<c:url value="${css}" />" rel="stylesheet">
</c:forEach>
<c:forEach var="js" items="${jsList}">
    <script src="<c:url value="${js}" />"></script>
</c:forEach>