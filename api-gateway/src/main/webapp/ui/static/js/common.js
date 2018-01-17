function showSuccessMessage(data) {
    $("#message-box").removeClass("alert-success");
    $("#message-box").removeClass("alert-danger");
    $("#message-box").addClass("alert-success");
    $("#message-text").html(data);
    $("#message-box-container").show();
}

function showErrorMessage(data) {
    $("#message-box").removeClass("alert-success");
    $("#message-box").removeClass("alert-danger");
    $("#message-box").addClass("alert-danger");
    $("#message-text").html(data);
    $("#message-box-container").show();
}

function hideMessage() {
    $("#message-box-container").hide();
    $("#message-box").removeClass("alert-success");
    $("#message-box").removeClass("alert-danger");
}