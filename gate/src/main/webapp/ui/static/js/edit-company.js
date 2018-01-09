var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var save = false;
$(document).ready(function () {
    getCompany(window.location.pathname);
});

function getCompany(url) {
    $.ajax({
        type: "GET",
        url: "/api" + url,
        // beforeSend: function (request) {
        //     request.setRequestHeader(header, token);
        // },
        success: function (data) {
            fillForm(data);
        },
        error: function (request, status, error) {
            try {
                var message = jQuery.parseJSON(request.responseText);
                showMessage(message.messageText, message.messageType);
            } catch (e) {
                console.log(request);
            }
        }
    });
}

function saveCompany() {
    $.ajax({
        type: "PUT",
        url: "/api" + window.location.pathname,
        contentType: "application/json",
        data: {
            id: $("#id").val(),
            name: $("#name").val(),
            pib: $("#pib").val(),
            identificationNumber: $("#identificationNumber").val(),
            headquarters: $("#headquarters").val()
        },
        // beforeSend: function (request) {
        //     request.setRequestHeader(header, token);
        // },
        success: function (data) {
            showMessage(data, "alert-success");
            save = false;
            prepareForm(true, "Edit");
        },
        error: function (request, status, error) {
            try {
                var message = jQuery.parseJSON(request.responseText);
                showMessage(message.messageText, message.messageType);
            } catch (e) {
                console.log(request);
            }
        }
    });
}

function enableFormForEditing() {
    if (save) {
//        editCompany();
        return true;
    } else {
        save = true;
        prepareForm(false, "Save");
    }
    return false;
}

function fillForm(company) {
    $("#id").val(company.id);
    $("#name").val(company.name);
    $("#pib").val(company.pib);
    $("#identificationNumber").val(company.identificationNumber);
    $("#headquarters").val(company.headquarters);
    $("#btn-edit").text("Edit");
}

function prepareForm(isDisabled, btnText) {
    $("#name").prop("disabled", isDisabled);
    $("#pib").prop("disabled", isDisabled);
    $("#identificationNumber").prop("disabled", isDisabled);
    $("#headquarters").prop("disabled", isDisabled);
    $("#btn-edit").text(btnText);
}

function showMessage(data, messageType) {
    $("#message-box").removeClass("alert-success");
    $("#message-box").removeClass("alert-danger");
    $("#message-box").addClass(messageType);
    $("#message-text").html(data);
    $("#message-box-container").show();
}

function hideMessage() {
    $("#message-box-container").hide();
}