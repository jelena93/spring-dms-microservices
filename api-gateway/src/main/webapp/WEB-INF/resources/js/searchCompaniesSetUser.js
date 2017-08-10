var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var selectedCompanyId = null;
var action_url_search_companies_api;
function search(name) {
    $.ajax({
        type: "GET",
        url: action_url_search_companies_api,
        data: {name: name},
        beforeSend: function (request) {
            request.setRequestHeader(header, token);
        },
        dataType: 'json',
        success: function (data) {
            $("#table-companies-set-user tbody").html('');
            selectedCompanyId = null;
            for (var i = 0; i < data.length; i++) {
                $('#table-companies-set-user tbody').
                        append('<tr class="clickable-row" id="' + data[i].id + '"><td>' + data[i].id + '</td><td>' + data[i].name + '</td><td>' +
                                data[i].pib + '</td><td>'
                                + data[i].identificationNumber + '</td><td>' +
                                data[i].headquarters
                                + '</td></tr>');
            }
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
$('#table-companies-set-user').on('click', '.clickable-row', function (event) {
    if ($(this).hasClass('active')) {
        $(this).removeClass('active');
        selectedCompanyId = null;
        $("#company").val(null);
        $('label[for="company"]').eq(1).show();
    } else {
        selectedCompanyId = $(this).attr('id');
        $(this).addClass('active').siblings().removeClass('active');
        $("#company").val(selectedCompanyId);
        $('label[for="company"]').eq(1).hide();
    }
});
$('#roles').change(function () {
    var roles = $(this).val();
    if (roles.length === 1 && roles[0] === "ADMIN") {
        $("#companySection").hide();
        $('label[for="company"]').eq(1).hide();
    } else {
        $("#companySection").show();
    }
});
function onSubmitForm() {
    if (selectedCompanyId === null) {
        $("#company").val(null);
        search("");
        return false;
    }
    var roles = $('#roles').val();
    if (roles === null) {
        return false;
    }
    if (roles.length === 1 && roles[0] === "ADMIN") {
        return true;
    }
    return true;
}