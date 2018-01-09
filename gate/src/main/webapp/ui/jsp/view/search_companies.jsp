<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>            
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:importAttribute name="action_url_show_company" ignore="true"/>
<script src="<c:url value="/resources/js/jquery.twbsPagination.js" />"></script>
<input class="form-control" name="company" id="company" type="text" onkeyup="search(this.value)" placeholder="Search companies" />
<br/>
<section class="panel">
    <table class="table table-striped table-advance table-hover" id="table-companies">
        <thead>
            <tr>
                <th><i class="icon_profile"></i> Id</th>
                <th><i class="icon_profile"></i> Name</th>
                <th><i class="icon_calendar"></i> Pib</th>
                <th><i class="icon_mail_alt"></i> Identification number</th>
                <th><i class="icon_pin_alt"></i> Headquarters</th>
                <th><i class="icon_cogs"></i> Show company</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="c" items="${companies}">
                <tr>
                    <td>${c.id}</td>
                    <td>${c.name}</td>
                    <td>${c.pib}</td>
                    <td>${c.identificationNumber}</td>
                    <td>${c.headquarters}</td>
                    <td>
                        <div class="btn-group">
                            <a class="btn btn-success" href="${pageContext.request.contextPath}/${action_url_show_company}/${c.id}"><i class="icon_check_alt2"></i></a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</section>
<script src="<c:url value="/resources/js/searchCompanies.js" />"></script>
