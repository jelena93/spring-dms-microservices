<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>           
<div class="form" id="form-document" style="display: none;">
    <form class="form-validate form-horizontal" id="register_form">
        <div class="form-group">
            <label class="control-label col-lg-2">Input or output</label>
            <div class="col-lg-10">
                <div class="radio">
                    <label>
                        <input type="radio" name="inputOutput" id="optionsRadios1" value="input" checked>
                        Input
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="inputOutput" id="optionsRadios2" value="output">
                        Output
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="file" class="control-label col-lg-2">Document <span class="required">*</span></label>
            <input type="file" id="file" name="file">
        </div>
        <div class="form-group">
            <label for="docType" class="control-label col-lg-2"><div id="docTypeLabel"></div><span class="required">*</span></label>
            <div class="col-lg-10">
                <select class="form-control" name="docType" id="docType" onchange="setDescriptors(this);"></select>
                <section class="panel">
                    <header class="panel-heading"> Descriptors</header>
                    <div class="panel-body" id="descriptors"></div>
                </section>
            </div>
        </div>

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="activityID" id="activityID"/>
        <input type="hidden" name="existingDocumentID" id="existingDocumentID"/>
        <div class="form-group">
            <div class="col-lg-offset-2 col-lg-10">
                <button class="btn btn-primary pull-right" type="button" onclick="saveDocument()">${title}</button>
            </div>
        </div>
    </form>
</div>