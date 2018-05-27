var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

function saveCompany() {
    if ($("#register_form").valid()) {
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
                window.location = "/company/" + data.id;
            },
            error: function (request) {
                console.log(request);
                showErrorMessage(request.responseText);
            }
        });
    }
}