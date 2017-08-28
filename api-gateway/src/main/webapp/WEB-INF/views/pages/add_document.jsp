<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>            
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:importAttribute name="action_url_processes_api"/>
<tiles:importAttribute name="action_url_show_activity_api"/>
<tiles:importAttribute name="action_url_document_validation_api"/>
<tiles:importAttribute name="action_url_document_type_api"/>
<tiles:importAttribute name="action_url_download_document"/>
<tiles:importAttribute name="action_url_display_document"/>
<script language=javascript>
    var action_url_processes_api = "${pageContext.request.contextPath}/${action_url_processes_api}";
        var action_url_show_activity_api = "${pageContext.request.contextPath}/${action_url_show_activity_api}";
            var action_url_document_validation_api = "${pageContext.request.contextPath}/${action_url_document_validation_api}";
                var action_url_document_type_api = "${pageContext.request.contextPath}/${action_url_document_type_api}";
                        var action_url_download_document = "${pageContext.request.contextPath}/${action_url_download_document}";
                            var action_url_display_document = "${pageContext.request.contextPath}/${action_url_display_document}";
</script>
<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <header class="panel-heading"> ${title} for company: ${company.name}</header>
            <div class="panel-body">
                <div class="col-lg-4">
                    <tiles:insertAttribute name="process_tree" />
                </div>
                <div class="col-lg-8">
                    <tiles:insertAttribute name="activity_info" />
                    <tiles:insertAttribute name="document" />
                    <button class="btn btn-primary pull-right" type="button" id="btn-add-document" onclick="showFormAddDocument()" style="display: none;">${title}</button>
                </div>
            </div>
        </section>
    </div>
</div>
<script src="<c:url value="/resources/js/processes-tree-add-documents.js" />"></script>
<script>getProcessesForAddDocument("${user}");</script>