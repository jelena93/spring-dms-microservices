<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<tiles:importAttribute name="site_name"/>
<div class="toggle-nav">
    <div class="icon-reorder tooltips" data-original-title="Toggle Navigation" data-placement="bottom"><i
            class="icon_menu"></i></div>
</div>
<a href="/" class="logo"><span class="lite">${site_name}</span></a>
<div class="top-nav notification-row">
    <ul class="nav pull-right top-menu">
        <li class="dropdown">
            <!--<a data-toggle="dropdown" class="dropdown-toggle" href="#">-->
                <span class="username">Logged in as ${user.details.principal.username}</span>
                <br>
                <span class="roles">Roles:
                    <c:forEach var="a" items="${user.details.principal.authorities}" varStatus="loop">
                        <c:choose>
                            <c:when test="${loop.index == fn:length(user.details.principal.authorities) - 1}">
                                ${fn:substringAfter(a.authority,'ROLE_')}
                            </c:when>
                            <c:otherwise>
                                ${fn:substringAfter(a.authority,'ROLE_')},
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </span>
            <!--</a>-->
        </li>
    </ul>
</div>