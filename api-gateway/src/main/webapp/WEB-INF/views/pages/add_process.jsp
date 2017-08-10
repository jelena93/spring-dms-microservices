<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>            
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:importAttribute name="action_url_processes_api"/>
<tiles:importAttribute name="action_url_show_process_api"/>
<tiles:importAttribute name="action_url_show_activity_api"/>
<tiles:importAttribute name="action_url_edit_process_api"/>
<tiles:importAttribute name="action_url_edit_activity_api"/>
<script language=javascript>
    var action_url_processes_api = "${pageContext.request.contextPath}/${action_url_processes_api}";
        var action_url_show_process_api = "${pageContext.request.contextPath}/${action_url_show_process_api}";
            var action_url_show_activity_api = "${pageContext.request.contextPath}/${action_url_show_activity_api}";
                var action_url_edit_process_api = "${pageContext.request.contextPath}/${action_url_edit_process_api}";
                    var action_url_edit_activity_api = "${pageContext.request.contextPath}/${action_url_edit_activity_api}";
</script>
<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <header class="panel-heading"> ${title} for company: ${company.name}</header>
            <div class="panel-body">
                <div class="col-lg-4">
                    <tiles:insertAttribute name="process_tree" />
                    <button class="btn btn-primary" type="button" id="btn-add" onclick="add()" style="display: none;">Add process</button>
                </div>
                <div class="col-lg-8">
                    <tiles:insertAttribute name="process_activity_form" />
                </div>
            </div>
        </section>
    </div>
</div>
<script src="<c:url value="/resources/js/processes-tree.js" />"></script>
<script>getProcessesForAddProcess();</script>
