var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
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
        type: "POST",
        url: "/api/company",
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify({
            name: $("#name").val(),
            pib: $("#pib").val(),
            identificationNumber: $("#identificationNumber").val(),
            headquarters: $("#headquarters").val()
        }),
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

function editCompany() {
    $.ajax({
        type: "PUT",
        url: "/api/company",
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify({
            id: $("#id").val(),
            name: $("#name").val(),
            pib: $("#pib").val(),
            identificationNumber: $("#identificationNumber").val(),
            headquarters: $("#headquarters").val()
        }),
        // beforeSend: function (request) {
        //     request.setRequestHeader(header, token);
        // },
        success: function (data) {
            showMessage(data, "alert-success");
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

function fillForm(company) {
    $("#id").val(company.id);
    $("#name").val(company.name);
    $("#pib").val(company.pib);
    $("#identificationNumber").val(company.identificationNumber);
    $("#headquarters").val(company.headquarters);
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