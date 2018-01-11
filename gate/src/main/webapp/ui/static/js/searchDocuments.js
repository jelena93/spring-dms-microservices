var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ready(function () {
    search( "");
});

function search(query) {
    $.ajax({
        type: "GET",
        url: "/api/document/search",
        data: {ownerId: 1, query: query},
        // beforeSend: function (request) {
        //     request.setRequestHeader(header, token);
        // },
        dataType: 'json',
        success: function (data) {
            var documentsHtml = "";
            for (var i = 0; i < data.length; i++) {
                documentsHtml += '<ul class="list-group">' +
                    '<li class="list-group-item clearfix">' +
                    '<a class="btn btn-default pull-right" href="/api/document/download/' + data[i].id + '" title="Download">' +
                    '<span class="icon_folder_download"></span> Download</a>' +
                    '<a class="btn btn-default pull-right" href="/api/document/1/' + data[i].id + '" target="_blank" title="View file">' +
                    '<span class="icon_folder-open"></span> View</a>' +
                    '<h3 class="list-group-item-heading">' + data[i].fileName + '</h3>';
                for (var j = 0; j < data[i].descriptors.length; j++) {
                    documentsHtml += '<p class="list-group-item-text">' +
                        '<strong>' + data[i].descriptors[j].descriptorKey + ': </strong>' +
                        data[i].descriptors[j].descriptorValue + '</p>';
                }
                documentsHtml += ' </li> </ul>';
            }
            $("#documents").html(documentsHtml);
        },
        error: function (textStatus, errorThrown) {
            alert(textStatus);
        }
    });
}