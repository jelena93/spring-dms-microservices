<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>            
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<script language=javascript>
    var documentTypes = [];
    <c:forEach items="${documentTypes}" var="doc">
    var documentType = [];
    documentType.id = "${doc.id}";
    documentType.name = "${doc.name}";
    documentType.descriptors = [];
        <c:forEach items="${documentTypes}" var="d">
    var doc = [];
    doc.documentTypeId = "{d.documentTypeId}";
    doc.descriptorKey = "{d.descriptorKey}";
    doc.descriptorValue = "{d.descriptorValue}";
    doc.paramClass = "{d.paramClass}";
    documentType.descriptors.push(doc);
        </c:forEach>
    documentTypes.push(documentType);
    </c:forEach>
</script>
<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <header class="panel-heading"> ${title} for company: <span id="company-name"></span></header>
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
<script src="<c:url value="/resources/js/process.js" />"></script>