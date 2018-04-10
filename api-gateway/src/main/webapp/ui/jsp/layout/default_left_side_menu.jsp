<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div id="sidebar" class="nav-collapse sidebar-custom">
    <ul class="sidebar-menu">
        <li class="active">
            <a class="" href="/">
                <i class="icon_house_alt"></i>
                <span>Dashboard</span>
            </a>
        </li>
        <sec:authorize access="hasRole('ADMIN')">
            <li class="sub-menu">
                <a href="javascript:;" class="">
                    <i class="icon_document_alt"></i>
                    <span>Company</span>
                    <span class="menu-arrow arrow_carrot-right"></span>
                </a>
                <ul class="sub">
                    <li><a class="" href="${pageContext.request.contextPath}/company/search">Search companies</a></li>
                    <li><a class="" href="${pageContext.request.contextPath}/company/add">Add company</a></li>
                </ul>
            </li>
            <li class="sub-menu">
                <a href="javascript:;" class="">
                    <i class="icon_document_alt"></i>
                    <span>Users</span>
                    <span class="menu-arrow arrow_carrot-right"></span>
                </a>
                <ul class="sub">
                    <li><a class="" href="${pageContext.request.contextPath}/user/add">Add user</a></li>
                    <li><a class="" href="${pageContext.request.contextPath}/user/search">Search users</a></li>
                </ul>
            </li>
        </sec:authorize>
        <sec:authorize access="hasRole('USER')">
            <li class="sub-menu">
                <a href="javascript:;" class="">
                    <i class="icon_document_alt"></i>
                    <span>Process</span>
                    <span class="menu-arrow arrow_carrot-right"></span>
                </a>
                <ul class="sub">
                    <li><a class="" href="${pageContext.request.contextPath}/process/add">Add process / activity</a>
                    </li>
                </ul>
            </li>
        </sec:authorize>
        <sec:authorize access="hasRole('UPLOADER')">
            <li class="sub-menu">
                <a href="javascript:;" class="">
                    <i class="icon_document_alt"></i>
                    <span>Documents</span>
                    <span class="menu-arrow arrow_carrot-right"></span>
                </a>
                <ul class="sub">
                    <li><a class="" href="${pageContext.request.contextPath}/document/add">Add document</a></li>
                    <li><a class="" href="${pageContext.request.contextPath}/document/search">Search documents</a></li>
                </ul>
            </li>
        </sec:authorize>
    </ul>
</div>