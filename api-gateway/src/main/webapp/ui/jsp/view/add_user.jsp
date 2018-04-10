<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>           
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <header class="panel-heading"> ${title}</header>
            <div class="panel-body">
                <div class="form">
                    <form class="form-validate form-horizontal " id="register_form">
                        <div class="form-group ">
                            <label for="name" class="control-label col-lg-2">Name <span
                                    class="required">*</span></label>
                            <div class="col-lg-10">
                                <input class=" form-control" id="name" name="name" type="text"/>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label for="surname" class="control-label col-lg-2">Surname <span class="required">*</span></label>
                            <div class="col-lg-10">
                                <input class=" form-control" id="surname" name="surname" type="text"/>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label for="username" class="control-label col-lg-2">Username <span
                                    class="required">*</span></label>
                            <div class="col-lg-10">
                                <input class="form-control " id="username" name="username" type="text"/>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label for="password" class="control-label col-lg-2">Password <span
                                    class="required">*</span></label>
                            <div class="col-lg-10">
                                <input class="form-control " id="password" name="password" type="password"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="roles" class="control-label col-lg-2">Roles <span class="required">*</span></label>
                            <div class="col-lg-10">
                                <select multiple class="form-control" name="roles" id="roles">
                                    <c:forEach var="role" items="${roles}">
                                        <option value="${role}">${role}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group" id="companySection" style="display: none;">
                            <label class="control-label col-lg-2">Company <span class="required">*</span></label>
                            <div class="col-lg-8">
                                <tiles:insertAttribute name="search_companies"/>
                            </div>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-group">
                            <div class="col-lg-offset-10 col-lg-2">
                                <button class="btn btn-primary" type="button" onclick="addUser()">${title}</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </section>
    </div>
</div>
<script src="<c:url value="/resources/js/userAdd.js" />"></script>
