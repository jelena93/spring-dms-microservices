var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ready(function () {
    search('');
});

function search(query) {
    $.ajax({
        type: "GET",
        url: "/api/company/search",
        data: {query: query},
        dataType: 'json',
        success: function (data) {
            $("#table-companies tbody").html('');
            for (var i = 0; i < data.length; i++) {
                var link = '<div class="btn-group"><a class="btn btn-success" href="/company/' + data[i].id + '"><i class="icon_check_alt2"></i></a></div>';
                $('#table-companies tbody').append('<tr><td>' + data[i].id + '</td><td>' + data[i].name + '</td><td>' +
                        data[i].pib + '</td><td>'
                        + data[i].identificationNumber + '</td><td>' +
                        data[i].headquarters
                        + '</td><td>' + link + ' </td></tr>');
            }
        },
        error: function (request) {
            console.log(request);
            showErrorMessage(request.responseText);
        }
    });
}