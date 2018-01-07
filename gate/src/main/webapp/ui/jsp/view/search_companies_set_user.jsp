<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>            
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:importAttribute name="action_url_search_companies_api" ignore="true"/>
<script language=javascript>
    var action_url_search_companies_api = "${pageContext.request.contextPath}/${action_url_search_companies_api}";
</script>
<input class="form-control" name="company" id="company" type="text" onkeyup="search(this.value)" />
<br/>
<section class="panel">
    <table class="table" id="table-companies-set-user">
        <thead>
            <tr>
                <th><i class="icon_profile"></i> Id</th>
                <th><i class="icon_profile"></i> Name</th>
                <th><i class="icon_calendar"></i> Pib</th>
                <th><i class="icon_mail_alt"></i> Identification number</th>
                <th><i class="icon_pin_alt"></i> Headquarters</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="c" items="${companies}">
                <tr class="clickable-row" id = "${c.id}">
                    <td>${c.id}</td>
                    <td>${c.name}</td>
                    <td>${c.pib}</td>
                    <td>${c.identificationNumber}</td>
                    <td>${c.headquarters}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</section>
