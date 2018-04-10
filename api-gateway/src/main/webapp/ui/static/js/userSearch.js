var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ready(function () {
    search('');
});

function search(query) {
    $.ajax({
        type: "GET",
        url: "/api/company/user/search",
        data: {query: query},
        dataType: 'json',
        success: function (data) {
            $("#table-users tbody").html('');
            for (var i = 0; i < data.length; i++) {
                $('#table-users tbody').append('<tr><td>' + data[i].username + '</td><td>' + data[i].name + '</td><td>' +
                        data[i].surname + '</td><td>'
                        + data[i].roles + '</td><td>' +
                        data[i].company + '</td></tr>');
            }
        },
        error: function (request) {
            console.log(request);
            showErrorMessage(request.responseText);
        }
    });
}