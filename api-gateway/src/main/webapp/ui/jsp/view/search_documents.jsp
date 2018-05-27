<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div class="input-group">
    <div class="input-group-addon">
        <input type="text" class="form-control" id="input-search-docs" onkeyup="search(this.value, 1)"
               placeholder="Search documents...">
    </div>
</div>
<section class="panel">
    <div class="panel panel-default">
        <div class="panel-heading">Documents</div>
        <div class="panel-body">
            <div id="documents">
            </div>
            <%--<div class="text-center">--%>
                <%--<ul id="pagination-demo" class="pagination-sm"></ul>--%>
            <%--</div>--%>
        </div>
    </div>
</section>
<script src="<c:url value="/resources/js/jquery.twbsPagination.js" />"></script>
<script src="<c:url value="/resources/js/searchDocuments.js" />"></script>
