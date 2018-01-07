<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="sidebar"  class="nav-collapse ">
    <ul class="sidebar-menu">                
        <li class="active">
            <a class="" href="${pageContext.request.contextPath}">
                <i class="icon_house_alt"></i>
                <span>Dashboard</span>
            </a>
        </li>
        <c:forEach var="role" items="${authorities}">
            <c:if test = "${role == 'ADMIN'}">
                <li class="sub-menu">
                    <a href="javascript:;" class="">
                        <i class="icon_document_alt"></i>
                        <span>Companies</span>
                        <span class="menu-arrow arrow_carrot-right"></span>
                    </a>
                    <ul class="sub">
                        <li><a class="" href="${pageContext.request.contextPath}/companies/search">Search companies</a></li>                          
                        <li><a class="" href="${pageContext.request.contextPath}/companies/add">Add company</a></li>                          
                    </ul>
                </li>  
                <li class="sub-menu">
                    <a href="javascript:;" class="">
                        <i class="icon_document_alt"></i>
                        <span>Users</span>
                        <span class="menu-arrow arrow_carrot-right"></span>
                    </a>
                    <ul class="sub">
                        <li><a class="" href="${pageContext.request.contextPath}/users/add">Add user</a></li>                          
                    </ul>
                </li>  
            </c:if>
            <c:if test = "${role == 'USER'}">
                <li class="sub-menu">
                    <a href="javascript:;" class="">
                        <i class="icon_document_alt"></i>
                        <span>Processes</span>
                        <span class="menu-arrow arrow_carrot-right"></span>
                    </a>
                    <ul class="sub">
                        <li><a class="" href="${pageContext.request.contextPath}/processes/add">Add process / Activity</a></li>                          
                    </ul>
                </li>  
            </c:if>
            <c:if test = "${role == 'UPLOADER'}">
                <li class="sub-menu">
                    <a href="javascript:;" class="">
                        <i class="icon_document_alt"></i>
                        <span>Documents</span>
                        <span class="menu-arrow arrow_carrot-right"></span>
                    </a>
                    <ul class="sub">
                        <li><a class="" href="${pageContext.request.contextPath}/documents/add">Add document</a></li>                          
                        <li><a class="" href="${pageContext.request.contextPath}/documents/search">Search documents</a></li>                          
                    </ul>
                </li> 
            </c:if>
        </c:forEach>
    </ul>
</div>