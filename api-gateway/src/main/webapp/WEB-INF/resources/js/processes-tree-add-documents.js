var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var selectedNode = null;
var checked = false;
var isSure = false;
var inputListDocumentTypes = null;
var outputListDocumentTypes = null;
var action_url_processes_api, action_url_show_activity_api, action_url_document_validation_api,
        action_url_download_document, action_url_display_document, action_url_document_type_api;
$(document).ready(function () {
    $('#form-document input[type="radio"]').click(function () {
        $("#docType").html("");
        if ($(this).val() === "input") {
            $("#docTypeLabel").text("Input document types");
            for (var i = 0; i < inputListDocumentTypes.length; i++) {
                $("#docType").append('<option value="' + inputListDocumentTypes[i].id + '">' + inputListDocumentTypes[i].name + '</option>');
            }
            if (inputListDocumentTypes.length > 0) {
                showDescriptors(inputListDocumentTypes[0].descriptors);
            } else {
                showDescriptors(null);
            }
        } else if ($(this).val() === "output") {
            $("#docTypeLabel").text("Output document types");
            for (var i = 0; i < outputListDocumentTypes.length; i++) {
                $("#docType").append('<option value="' + outputListDocumentTypes[i].id + '">' + outputListDocumentTypes[i].name + '</option>');
            }
            if (outputListDocumentTypes.length > 0) {
                showDescriptors(outputListDocumentTypes[0].descriptors);
            } else {
                showDescriptors(null);
            }
        }
    });
});
function setDescriptors(docType) {
    $.ajax({
        type: "GET",
        url: action_url_document_type_api + "/" + docType.value,
        beforeSend: function (request) {
            request.setRequestHeader(header, token);
        },
        dataType: 'json',
        success: function (data) {
            showDescriptors(data);
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
function getProcessesForAddDocument(user) {
    $('#processes').jstree({
        'core': {
            'data': {
                'url': action_url_processes_api + "/" + user,
                'data': function (node) {
                    return {'id': node.id};
                }
            },
            "multiple": false,
            "themes": {
                "variant": "large"
            },
            "plugins": ["wholerow"]
        }}).on('activate_node.jstree', function (e, data) {
        if (data.node.original.activity) {
            if (selectedNode !== null && selectedNode.id === data.node.original.id) {
                reset(data);
            } else {
                selectedNode = data.node.original;
                getActivityInfo(selectedNode.id);
            }
        } else {
            reset(data);
        }
    });
}
function getActivityInfo(id) {
    $.ajax({
        type: "GET",
        url: action_url_show_activity_api + "/" + id,
        beforeSend: function (request) {
            request.setRequestHeader(header, token);
        },
        dataType: 'json',
        success: function (data) {
            displayActivityInfo(data);
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
function displayActivityInfo(activity) {
    var inputList = "";
    for (var i = 0; i < activity.inputList.length; i++) {
        inputList += '<div class="panel-group" id="accordion">' +
                '<div class="panel panel-default">' +
                '<div class="panel-heading">' +
                '<h4 class="panel-title">' +
                '<a data-toggle="collapse" data-parent="#accordion" href="#colapse' + activity.inputList[i].id + '">' + activity.inputList[i].fileName + '</a>' +
                '</h4>' +
                '</div>' +
                '<div id="colapse' + activity.inputList[i].id + '" class="panel-collapse collapse">' +
                '<div class="panel-body">';
        for (var j = 0; j < activity.inputList[i].descriptors.length; j++) {
            if (activity.inputList[i].descriptors[j].descriptorType.paramClass === "java.util.Date") {
                inputList += '<p><strong>' + activity.inputList[i].descriptors[j].descriptorKey + '</strong>: ' +
                        getFormattedDate(activity.inputList[i].descriptors[j].value) + '</p>';
            } else {
                inputList += '<p><strong>' + activity.inputList[i].descriptors[j].descriptorKey + '</strong>: ' +
                        activity.inputList[i].descriptors[j].value + '</p>';
            }
        }
        inputList += "</div><div class='panel-footer clearfix'>";
        inputList += "<a class='btn btn-default' target='_blank' href='" + action_url_display_document + "/" + activity.inputList[i].id
                + "' title='View file'><span class='icon_folder-open'></span> View file </a>" +
                "<a class='btn btn-default pull-right' href='" + action_url_download_document + "/" + activity.inputList[i].id +
                "' title='Download'><span class='icon_folder_download'></span> Download file</a>";
        inputList += '</div></div></div></div> ';
    }
    $('#inputList').html(inputList);
    var outputList = "";
    for (var i = 0; i < activity.outputList.length; i++) {
        outputList += '<div class="panel-group" id="accordion">' +
                '<div class="panel panel-default">' +
                '<div class="panel-heading">' +
                '<h4 class="panel-title">' +
                '<a data-toggle="collapse" data-parent="#accordion" href="#colapse' + activity.outputList[i].id + '">' + activity.outputList[i].fileName + '</a>' +
                '</h4>' +
                '</div>' +
                '<div id="colapse' + activity.outputList[i].id + '" class="panel-collapse collapse">' +
                '<div class="panel-body">';
        for (var j = 0; j < activity.outputList[i].descriptors.length; j++) {
            outputList += '<p><strong>' + activity.outputList[i].descriptors[j].descriptorKey + '</strong>: ' +
                    activity.outputList[i].descriptors[j].value + '</p>';
        }
        outputList += "</div><div class='panel-footer'>";
        outputList += "<a target='_blank' href='" + action_url_display_document + "/" + activity.outputList[i].id + "'>View file </a>" +
                "<a class='btn btn-default pull-right' href='" + action_url_download_document + "/" + activity.outputList[i].id +
                "' title='Download'><span class='icon_cloud-download'></span> Download file</a>";
        outputList += '</div></div></div></div> ';
    }
    inputListDocumentTypes = activity.inputListDocumentTypes;
    outputListDocumentTypes = activity.outputListDocumentTypes;
    $('#outputList').html(outputList);
    $("#form-document").hide();
    $("#activity-info").show();
    $("#btn-add-document").show();
}
function showDescriptors(descriptors) {
    $("#descriptors").html("");
    var html = "";
    if (descriptors !== null) {
        for (var i = 0; i < descriptors.length; i++) {
            console.log(descriptors[i]);
            if (descriptors[i].value === null) {
                if (descriptors[i].descriptorType.paramClass === 'java.util.Date') {
                    html = '<div class="form-group">' +
                            '<label for="' + descriptors[i].id + '" class="control-label col-lg-4">' + descriptors[i].descriptorKey
                            + '<span class="required">*</span></label><div class="col-lg-8">' +
                            '<input type="text" class="form-control descriptors" name="' + descriptors[i].descriptorKey
                            + '" id="' + descriptors[i].id + '" placeholder="Enter ' + descriptors[i].descriptorKey
                            + ' in format ' + descriptors[i].date_FORMAT + '" required></div></div>';
                } else {
                    html = '<div class="form-group">' +
                            '<label for="' + descriptors[i].id + '" class="control-label col-lg-4">' + descriptors[i].descriptorKey +
                            ' <span class="required">*</span></label><div class="col-lg-8">' +
                            '<input type="text" class="form-control descriptors" name="' + descriptors[i].descriptorKey +
                            '" id="' + descriptors[i].id + '" placeholder="Enter ' + descriptors[i].descriptorKey + '" required>' +
                            '</div></div>';
                }
                $("#descriptors").append(html);
            }
        }
    }
}
function showFormAddDocument() {
    $("#activity-info").hide();
    $("#btn-add-document").hide();
    $("#docTypeLabel").text("Input document types");
    $("#docType").html("");
    for (var i = 0; i < inputListDocumentTypes.length; i++) {
        $("#docType").append('<option value="' + inputListDocumentTypes[i].id + '">' + inputListDocumentTypes[i].name + '</option>');
    }
    showDescriptors(inputListDocumentTypes[0].descriptors);
    $("#form-document").show();
}
function onSubmitForm() {
    if ($("#file").val() === "") {
        return false;
    }
    if (checked || isSure) {
        return true;
    }
    validateDocument();
    return false;
}
function validateDocument() {
    if (selectedNode !== null) {
        $("#activityID").val(selectedNode.id);
        var docType = $("#docType").val();
        var data = new FormData();
        data.append("file", $("#file").prop('files')[0]);
        data.append("docType", docType);
        data.append("activityID", selectedNode.id);
        data.append("inputOutput", $("input[name='inputOutput']:checked").val());
        var descriptors = $(".descriptors");
        var sendValidationRequest = true;
        for (var i = 0; i < descriptors.length; i++) {
            data.append([descriptors[i].name], descriptors[i].value);
            if (descriptors[i].value === "") {
                sendValidationRequest = false;
                break;
            }
        }
        if (sendValidationRequest) {
            documentValidation(data);
        }
    }

}
function documentValidation(params) {
    $.ajax({
        type: "POST",
        url: action_url_document_validation_api,
        data: params,
        processData: false,
        enctype: 'multipart/form-data',
        contentType: false,
        dataType: 'json',
        beforeSend: function (request) {
            request.setRequestHeader(header, token);
        },
        success: function (data) {
            if (data.messageType === "question") {
                if (data.messageAction === "edit") {
                    $("#existingDocumentID").val(data.messageData);
                } else {
                    $("#existingDocumentID").val(null);
                }
                showPopUp(data.messageText);
            } else if (data.messageType === "alert-success") {
                checked = true;
                $("#register_form").submit();
            } else {
                console.log(data);
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

function reset(data) {
    $("#activity-info").hide();
    $("#form-document").hide();
    $("#btn-add-document").hide();
    selectedNode = null;
    data.instance.deselect_node(data.node, true);
}
function showMessage(data, messageType) {
    $("#message-box").removeClass("alert-success");
    $("#message-box").removeClass("alert-danger");
    $("#message-box").addClass(messageType);
    $("#message-text").html(data);
    $("#message-box-container").show();
}
function showPopUp(text) {
    $("#modal-question-text").text(text);
    $("#modal").modal({
        show: true,
        backdrop: 'static',
        keyboard: false
    });
}
function sendRequest() {
    isSure = true;
    $('#modal').modal('hide');
    $("#register_form").submit();
}
function closeModal() {
    isSure = false;
    $("#existingDocumentID").val(null);
    $('#modal').modal('hide');
}
function getFormattedDate(dateString) {
    var date = new Date(dateString);
    var day = date.getDate();
    var month = date.getMonth() + 1;
    var year = date.getFullYear();
    return day + "." + month + "." + year;
}