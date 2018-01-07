var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var save = false;
var action_url_edit_company_api;
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
//function editCompany() {
//    $.ajax({
//        type: "POST",
//        url: action_url_edit_company_api,
//        data: {id: $("#id").val(), name: $("#name").val(), pib: $("#pib").val(),
//            identificationNumber: $("#identificationNumber").val(), headquarters: $("#headquarters").val()},
//        beforeSend: function (request) {
//            request.setRequestHeader(header, token);
//        },
//        success: function (data) {
//            showMessage(data, "alert-success");
//            save = false;
//            prepareForm(true, "Edit");
//        },
//        error: function (request, status, error) {
//            try {
//                var message = jQuery.parseJSON(request.responseText);
//                showMessage(message.messageText, message.messageType);
//            } catch (e) {
//                console.log(request);
//            }
//        }
//    });
//}
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