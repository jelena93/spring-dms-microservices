<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:importAttribute name="site_name"/>
<tiles:importAttribute name="active_role" ignore="true"/>
<div class="toggle-nav">
    <div class="icon-reorder tooltips" data-original-title="Toggle Navigation" data-placement="bottom"><i class="icon_menu"></i></div>
</div>
<a href="${pageContext.request.contextPath}" class="logo"><span class="lite">${site_name}</span></a>
<div class="top-nav notification-row">                
    <ul class="nav pull-right top-menu">
        <li class="dropdown">
            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                <span class="username">Logged in as ${user}</span>
                <b class="caret"></b>
            </a>
            <ul class="dropdown-menu extended logout">
                <div class="log-arrow-up"></div>
                <li class="eborder-top">
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/logout"><i class="icon_key_alt"></i> Log Out</a>
                </li>
            </ul>
        </li>
    </ul>
</div>