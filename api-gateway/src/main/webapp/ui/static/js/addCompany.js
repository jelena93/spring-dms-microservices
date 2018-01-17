var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

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
        beforeSend: function (request) {
            request.setRequestHeader(header, token);
        },
        success: function (data) {
            showSuccessMessage(data);
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