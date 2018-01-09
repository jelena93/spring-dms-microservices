<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<script src="<c:url value="/resources/js/edit-company.js" />"></script>
<div id="profile" >
    <section class="panel">
        <header class="panel-heading"> ${title}</header>
        <div class="panel-body">
            <div class="form">
                <form class="form-validate form-horizontal" id="register_form">
                    <div class="form-group ">
                        <label for="id" class="control-label col-lg-2">Id <span class="required">*</span></label>
                        <div class="col-lg-10">
                            <input class=" form-control" id="id" name="id" type="number" value="${company.id}" readonly/>
                        </div>
                    </div>
                    <div class="form-group ">
                        <label for="name" class="control-label col-lg-2">Name <span class="required">*</span></label>
                        <div class="col-lg-10">
                            <input class=" form-control" id="name" name="name" type="text" value="${company.name}" disabled/>
                        </div>
                    </div>
                    <div class="form-group ">
                        <label for="pib" class="control-label col-lg-2">Pib <span class="required">*</span></label>
                        <div class="col-lg-10">
                            <input class=" form-control" id="pib" name="pib" value="${company.pib}" type="number"  minlength="9" maxlength="9" disabled/>
                        </div>
                    </div>
                    <div class="form-group ">
                        <label for="identificationNumber" class="control-label col-lg-2">Identification number <span class="required">*</span></label>
                        <div class="col-lg-10">
                            <input class="form-control " id="identificationNumber" value="${company.identificationNumber}" name="identificationNumber" type="number"  minlength="8" maxlength="8" disabled />
                        </div>
                    </div>
                    <div class="form-group ">
                        <label for="headquarters" class="control-label col-lg-2">Headquarters <span class="required">*</span></label>
                        <div class="col-lg-10">
                            <input class="form-control " id="headquarters" value="${company.headquarters}" name="headquarters" type="text" disabled />
                        </div>
                    </div>
                    <input type="hidden"  name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="form-group">
                        <div class="col-lg-offset-10 col-lg-2">
                            <button class="btn btn-primary" type="button" onclick="saveCompany()" id="btn-edit" >Edit</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </section>
    <section>
        <div class="row">                                              
        </div>
    </section>
</div>
<!--collapse start-->
<div class="panel-group m-bot20" id="accordion">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapse">
                    Users
                </a>
            </h4>
        </div>
        <div id="collapse" class="panel-collapse collapse">
            <div class="panel-body">
                <c:choose>
                    <c:when test="${users.isEmpty()}" ><h4 style="text-align: center;">There are no users for this company</h4></c:when>
                    <c:otherwise>
                        <table class="table">
                            <thead>
                                <tr>
                                    <th><i class="icon_profile"></i>Username</th>
                                    <th><i class="icon_profile"></i>Name</th>
                                    <th><i class="icon_profile"></i>Surname</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="u" items="${users}">
                                    <tr>
                                        <td>${u.username}</td>
                                        <td>${u.name}</td>
                                        <td>${u.surname}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
<!--collapse end-->