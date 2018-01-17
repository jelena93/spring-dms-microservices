<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="sidebar"  class="nav-collapse ">
    <ul class="sidebar-menu">                
        <li class="active">
            <a class="" href="/">
                <i class="icon_house_alt"></i>
                <span>Dashboard</span>
            </a>
        </li>
        <c:forEach var="role" items="${authorities}">
            <c:if test = "${role == 'ROLE_ADMIN'}">
                <li class="sub-menu">
                    <a href="javascript:;" class="">
                        <i class="icon_document_alt"></i>
                        <span>Company</span>
                        <span class="menu-arrow arrow_carrot-right"></span>
                    </a>
                    <ul class="sub">
                        <li><a class="" href="${pageContext.request.contextPath}/company/search">Search company</a></li>                          
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
                    </ul>
                </li>  
            </c:if>
            <c:if test = "${role == 'ROLE_USER'}">
                <li class="sub-menu">
                    <a href="javascript:;" class="">
                        <i class="icon_document_alt"></i>
                        <span>Process</span>
                        <span class="menu-arrow arrow_carrot-right"></span>
                    </a>
                    <ul class="sub">
                        <li><a class="" href="${pageContext.request.contextPath}/process/add">Add process / Activity</a></li>
                    </ul>
                </li>  
            </c:if>
            <c:if test = "${role == 'ROLE_UPLOADER'}">
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
            </c:if>
        </c:forEach>
    </ul>
</div>