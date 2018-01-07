<%-- 
    Document   : login
    Created on : Jan 2, 2017, 3:29:41 PM
    Author     : ana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <tiles:insertAttribute name="head" />
        <tiles:importAttribute name="title" />
        <tiles:importAttribute name="action_login_url" />
        <title>${title}</title>
    </head>
    <body class="login-img3-body">
        <div class="container">
            <form class="login-form" action="${pageContext.request.contextPath}/${action_login_url}" method="POST">        
                <div class="login-wrap">
                    <p class="login-img"><i class="icon_lock_alt"></i></p>
                        <c:choose>
                            <c:when test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
                            <p><font color="red">${SPRING_SECURITY_LAST_EXCEPTION.message}</font></p>
                            </c:when>
                            <c:otherwise><p>Enter your username and password:</p></c:otherwise>
                    </c:choose>
                    <div class="input-group">
                        <span class="input-group-addon"><i class="icon_profile"></i></span>
                        <input type="text" class="form-control" name ="username" placeholder="Username" autofocus required>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon"><i class="icon_key_alt"></i></span>
                        <input type="password" class="form-control" name ="password" placeholder="Password" required>
                    </div>
                    <input type="hidden"  name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button class="btn btn-primary btn-lg btn-block" type="submit">Login</button>
                </div>
            </form>
        </div>
    </body>
</html>
