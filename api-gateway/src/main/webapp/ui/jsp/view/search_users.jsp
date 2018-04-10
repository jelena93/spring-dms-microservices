<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<script src="<c:url value="/resources/js/jquery.twbsPagination.js" />"></script>
<input class="form-control" name="user" id="user" type="text" onkeyup="search(this.value)" placeholder="Search users" />
<br/>
<section class="panel">
    <table class="table table-striped table-advance table-hover" id="table-users">
        <thead>
            <tr>
                <th><i class="icon_profile"></i> Username</th>
                <th><i class="icon_calendar"></i> Name</th>
                <th><i class="icon_calendar"></i> Surname</th>
                <th><i class="icon_calendar"></i> Roles</th>
                <th><i class="icon_pin_alt"></i> Company</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</section>
<script src="<c:url value="/resources/js/userSearch.js" />"></script>
