$(document).ready(function () {
    if (getCookie("JSESSIONID") === null) {
        eraseCookie("companyId");
    }
    if (getCookie("companyId") === null) {
        getUsersCompany();
    }
});

function getUsersCompany() {
    $.ajax({
        type: "GET",
        url: "/api/company/user/" + user,
        success: function (data) {
            if (data.companyId !== null) {
                setCookie("companyId", data.companyId);
            }
        },
        error: function (request) {
            console.log(request);
            showErrorMessage(request.responseText);
        }
    });
}

function setCookie(name, value) {
    var expires = "";
    expires = "; expires=Session";
    document.cookie = name + "=" + (value || "") + expires + "; path=/";
}

function getCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

function eraseCookie(name) {
    document.cookie = name + '=; Max-Age=-99999999;';
}

function showSuccessMessage(data) {
    $("#message-box").removeClass("alert-success");
    $("#message-box").removeClass("alert-danger");
    $("#message-box").addClass("alert-success");
    try {
        var message = jQuery.parseJSON(data);
        $("#message-text").text(message);
    } catch (e) {
        $("#message-text").text(data);
    }
    $("#message-box-container").show();
}

function showErrorMessage(data) {
    $("#message-box").removeClass("alert-success");
    $("#message-box").removeClass("alert-danger");
    $("#message-box").addClass("alert-danger");
    try {
        // var message = jQuery.parseJSON(data);
        $("#message-text").text(JSON.stringify(data));
    } catch (e) {
        $("#message-text").text(data);
    }
    $("#message-box-container").show();
}

function hideMessage() {
    $("#message-box-container").hide();
    $("#message-box").removeClass("alert-success");
    $("#message-box").removeClass("alert-danger");
}