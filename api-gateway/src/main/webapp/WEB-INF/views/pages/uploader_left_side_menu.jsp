<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:importAttribute name="action_url_add_document"/>
<tiles:importAttribute name="action_url_search_documents"/>
<div id="sidebar" class="nav-collapse ">
    <ul class="sidebar-menu">                
        <li>
            <a href="${pageContext.request.contextPath}">
                <i class="icon_house_alt"></i>
                <span>Dashboard</span>
            </a>
        </li>
        <li class="sub-menu">
            <a href="javascript:;" class="">
                <i class="icon_document_alt"></i>
                <span>Documents</span>
                <span class="menu-arrow arrow_carrot-right"></span>
            </a>
            <ul class="sub">
                <li><a class="" href="${pageContext.request.contextPath}/${action_url_add_document}">Add document</a></li>                          
                <li><a class="" href="${pageContext.request.contextPath}/${action_url_search_documents}">Search documents</a></li>                          
            </ul>
        </li> 
    </ul>
</div>