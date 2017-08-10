<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>            
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:importAttribute name="action_url_search_documents_api" />
<tiles:importAttribute name="action_url_download_document" />
<tiles:importAttribute name="action_url_display_document"/>
<script src="<c:url value="/resources/js/jquery.twbsPagination.js" />"></script>
<div class="input-group">
    <div class="input-group-addon">
        <input type="text" class="form-control" id="input-search-docs" onkeyup="search(this.value, 1)" placeholder="Search documents..." >
    </div>
</div>
<script language=javascript>
    var action_url_search_documents_api = "${pageContext.request.contextPath}/${action_url_search_documents_api}";
        var action_url_display_document = "${pageContext.request.contextPath}/${action_url_display_document}";
            var action_url_download_document = "${pageContext.request.contextPath}/${action_url_download_document}";
                var total = ${total};
</script>
<section class="panel">
    <div class="panel panel-default">
        <div class="panel-heading">Documents</div>
        <div class="panel-body">
            <div id="documents">
                <c:forEach var="doc" items="${documents}">
                    <ul class="list-group">
                        <li class="list-group-item clearfix">
                            <a class='btn btn-default pull-right' href="${pageContext.request.contextPath}/${action_url_download_document}/${doc.id}" title='Download'>
                                <span class='icon_folder_download'></span> Download file</a>
                            <a class='btn btn-default pull-right' href="${pageContext.request.contextPath}/${action_url_display_document}/${doc.id}" target='_blank' title='View file'>
                                <span class='icon_folder-open'></span> View file</a>
                            <h3 class="list-group-item-heading">${doc.fileName}</h3>
                            <c:forEach var="desc" items="${doc.descriptors}">
                                <p class="list-group-item-text"><strong>${desc.descriptorKey}: </strong>${desc.value}</p>
                            </c:forEach>
                        </li> 
                    </ul>
                </c:forEach>
            </div>
            <div class="text-center">
                <ul id="pagination-demo" class="pagination-sm"></ul>
            </div>
        </div>
    </div>
</section>
<script src="<c:url value="/resources/js/searchDocuments.js" />"></script>
