<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>            
<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <header class="panel-heading"> ${title}</header>
            <div class="panel-body">
                <div class="form">
                    <form class="form-validate form-horizontal " id="register_form">
                        <div class="form-group ">
                            <label for="name" class="control-label col-lg-2">Name <span class="required">*</span></label>
                            <div class="col-lg-10">
                                <input class=" form-control" id="name" name="name" type="text" />
                            </div>
                        </div>
                        <div class="form-group ">
                            <label for="pib" class="control-label col-lg-2">Pib <span class="required">*</span></label>
                            <div class="col-lg-10">
                                <input class=" form-control" id="pib" name="pib" type="number"  minlength="9" maxlength="9" />
                            </div>
                        </div>
                        <div class="form-group ">
                            <label for="identificationNumber" class="control-label col-lg-2">Identification number <span class="required">*</span></label>
                            <div class="col-lg-10">
                                <input class="form-control " id="identificationNumber" name="identificationNumber" type="number"  minlength="8" maxlength="8" />
                            </div>
                        </div>
                        <div class="form-group ">
                            <label for="headquarters" class="control-label col-lg-2">Headquarters <span class="required">*</span></label>
                            <div class="col-lg-10">
                                <input class="form-control " id="headquarters" name="headquarters" type="text" />
                            </div>
                        </div>
                        <input type="hidden"  name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-group">
                            <div class="col-lg-offset-2 col-lg-10">
                                <button class="btn btn-primary" type="button" onclick="saveCompany()">${title}</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </section>
    </div>
</div>
<script src="<c:url value="/resources/js/company.js" />"></script>