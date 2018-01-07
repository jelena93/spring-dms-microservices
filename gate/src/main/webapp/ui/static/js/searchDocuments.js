var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var action_url_search_documents_api, action_url_display_document, action_url_download_document;
function search(query) {
    $.ajax({
        type: "GET",
        url: action_url_search_documents_api,
        data: {query: query},
        beforeSend: function (request) {
            request.setRequestHeader(header, token);
        },
        dataType: 'json',
        success: function (data) {
            var documentsHtml = "";
            for (var i = 0; i < data.length; i++) {
                documentsHtml += '<ul class="list-group">' +
                        '<li class="list-group-item clearfix">' +
                        '<a class="btn btn-default pull-right" href="' + action_url_download_document + '/' + data[i].id + '" title="Download">' +
                        '<span class="icon_folder_download"></span> Download file</a>' +
                        '<a class="btn btn-default pull-right" href="' + action_url_display_document + '/' + data[i].id + '" target="_blank" title="View file">' +
                        '<span class="icon_folder-open"></span> View file</a>' +
                        '<h3 class="list-group-item-heading">' + data[i].fileName + '</h3>';
                for (var j = 0; j < data[i].descriptors.length; j++) {
                    documentsHtml += '<p class="list-group-item-text">' +
                            '<strong>' + data[i].descriptors[j].descriptorKey + ': </strong>' +
                            data[i].descriptors[j].value + '</p>';
                }
                documentsHtml += ' </li> </ul>';
            }
            $("#documents").html(documentsHtml);
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