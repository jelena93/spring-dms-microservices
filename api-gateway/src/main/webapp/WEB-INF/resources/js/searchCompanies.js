var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var action_url_show_company, action_url_search_companies_api, total;
var selectedCompanyId = null;
function pagination(currentPage) {
    console.log("total " + total);
    $('#pagination-demo').twbsPagination({
        totalPages: total,
        page: currentPage,
        visiblePages: "1",
        onPageClick: function (event, page) {
            console.log("page " + page);
            var query = $("#company").val();
            search(query, page);
        }
    });
}
$(document).ready(function () {
    pagination("1");
});
function search(query, page) {
    $.ajax({
        type: "GET",
        url: action_url_search_companies_api,
        data: {query: query, page: page},
        beforeSend: function (request) {
            request.setRequestHeader(header, token);
        },
        dataType: 'json',
        success: function (data) {
            $("#table-companies tbody").html('');
            var companies = data.companies;
            total = data.total;
            for (var i = 0; i < companies.length; i++) {
                console.log("id " + companies[i].id);
                var link = '<div class="btn-group"><a class="btn btn-success" href="' + action_url_show_company + '/' + companies[i].id + '"><i class="icon_check_alt2"></i></a></div>';
                $('#table-companies tbody').
                        append('<tr><td>' + companies[i].id + '</td><td>' + companies[i].name + '</td><td>' +
                                companies[i].pib + '</td><td>'
                                + companies[i].identificationNumber + '</td><td>' +
                                companies[i].headquarters
                                + '</td><td>' + link + ' </td></tr>');
            }
            pagination(page + "");
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