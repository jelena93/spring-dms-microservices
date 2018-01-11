<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>           
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:importAttribute name="label_name"/>
<tiles:importAttribute name="input_name"/>
<div class="form" id="info" style="display: none">
    <form class="form-validate form-horizontal" id="register_form">
        <div class="form-group ">
            <label for="name" class="control-label col-lg-2">Name <span class="required">*</span></label>
            <div class="col-lg-8">
                <input class=" form-control" id="name" name="name" type="text" disabled />
            </div>
        </div>
        <div class="form-group" id="form_input_document_types">
            <label for="primitive" class="control-label col-lg-2">Input document types: <span class="required">*</span></label>
            <div class="col-lg-8">
                <select multiple="true" class="form-control" id="input_document_types" name="inputActivityDocumentTypes" required>
                    <c:forEach var="documentType" items="${documentTypes}">
                        <option value="${documentType.id}">${documentType.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group " id="form_output_document_types" >
            <label for="primitive" class="control-label col-lg-2">Output document types: <span class="required">*</span></label>
            <div class="col-lg-8">
                <select multiple="true" class="form-control" id="output_document_types" name="outputActivityDocumentTypes" required>
                    <c:forEach var="documentType" items="${documentTypes}">
                        <option value="${documentType.id}">${documentType.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group " id="form-primitive">
            <label for="primitive" class="control-label col-lg-2">Primitive </label>
            <div class="col-lg-8">
                <input type="checkbox" style="width: 20px" class="checkbox form-control" id="primitive" name="primitive" disabled/>
            </div>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="isActivity" id="isActivity"/>
        <input type="hidden" name="parent" id ="parent" />
        <input type="hidden" name="id" id ="id" />
        <div class="form-group">
            <div class="col-lg-offset-8 col-lg-2">
                <button class="btn btn-primary" id="btn-edit" type="button" onclick="checkData()">Edit</button>
            </div>
        </div>
    </form>
</div>