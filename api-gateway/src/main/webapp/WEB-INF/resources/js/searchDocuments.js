var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var action_url_search_documents_api, action_url_display_document, action_url_download_document, total;
function pagination(currentPage) {
    console.log("total " + total);
    $('#pagination-demo').twbsPagination({
        totalPages: total,
        page: currentPage,
        visiblePages: "1",
        onPageClick: function (event, page) {
            console.log("page " + page);
            var query = $("#input-search-docs").val();
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
        url: action_url_search_documents_api,
        data: {query: query, page: page},
        beforeSend: function (request) {
            request.setRequestHeader(header, token);
        },
        dataType: 'json',
        success: function (data) {
            var documentsHtml = "";
            var documents = data.documents;
            total = data.total;
            for (var i = 0; i < documents.length; i++) {
                documentsHtml += '<ul class="list-group">' +
                        '<li class="list-group-item clearfix">' +
                        '<a class="btn btn-default pull-right" href="' + action_url_download_document + '/' + documents[i].id + '" title="Download">' +
                        '<span class="icon_folder_download"></span> Download file</a>' +
                        '<a class="btn btn-default pull-right" href="' + action_url_display_document + '/' + documents[i].id + '" target="_blank" title="View file">' +
                        '<span class="icon_folder-open"></span> View file</a>' +
                        '<h3 class="list-group-item-heading">' + documents[i].fileName + '</h3>';
                for (var j = 0; j < documents[i].descriptors.length; j++) {
                    documentsHtml += '<p class="list-group-item-text">' +
                            '<strong>' + documents[i].descriptors[j].descriptorKey + ': </strong>' +
                            documents[i].descriptors[j].value + '</p>';
                }
                documentsHtml += ' </li> </ul>';
            }
            $("#documents").html(documentsHtml);
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