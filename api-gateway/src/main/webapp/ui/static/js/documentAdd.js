var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var selectedNode = null;
var inputListDocumentTypes = null;
var outputListDocumentTypes = null;
var documentTypes;
var documents;

$(document).ready(function () {
    $("#company-name").html(getCookie("companyName"));
    $('#form-document input[type="radio"]').click(function () {
        $("#docType").html("");
        if ($(this).val() === "input") {
            $("#docTypeLabel").text("Input document types");
            for (var i = 0; i < inputListDocumentTypes.length; i++) {
                $("#docType").append('<option value="' + documentTypes[inputListDocumentTypes[i]].id + '">' + documentTypes[inputListDocumentTypes[i]].name + '</option>');
            }
            showDescriptors(documentTypes[inputListDocumentTypes[0]].descriptors);
        } else if ($(this).val() === "output") {
            $("#docTypeLabel").text("Output document types");
            for (var i = 0; i < outputListDocumentTypes.length; i++) {
                $("#docType").append('<option value="' + documentTypes[outputListDocumentTypes[i]].id + '">' + documentTypes[outputListDocumentTypes[i]].name + '</option>');
            }
            showDescriptors(documentTypes[outputListDocumentTypes[0]].descriptors);
        }
    });
    getProcesses();
    getDocumentTypes();
    getDocuments(null);
});

function setDescriptors(docType) {
    $.ajax({
        type: "GET",
        url: "descriptor/document-types/" + docType.value,
        beforeSend: function (request) {
            request.setRequestHeader(header, token);
        },
        dataType: 'json',
        success: function (data) {
            showDescriptors(data);
        },
        error: function (request) {
            console.log(request);
            showErrorMessage(request.responseText);
        }
    });
}

function getProcesses() {
    $('#processes').jstree({
        'core': {
            'data': {
                url: "/api/process/all/" + company,
                'data': function (node) {
                    return {'id': node.id};
                }
            },
            "multiple": false,
            "themes": {
                "variant": "large"
            },
            "state": {"opened": false},
            "plugins": ["wholerow"]
        }
    }).on('activate_node.jstree', function (e, data) {
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

function getDocumentTypes() {
    $.ajax({
        type: "GET",
        url: "/api/descriptor/document-type/all",
        dataType: 'json',
        success: function (docTypes) {
            documentTypes = docTypes.reduce(function (map, obj) {
                map[obj.id] = obj;
                return map;
            }, {});

            console.log(documentTypes);
        },
        error: function (request) {
            console.log(request);
            showErrorMessage(request.responseText);
        }
    });
}

function getDocuments(activity) {
    $.ajax({
        type: "GET",
        url: "/api/document/" + company + "/all",
        dataType: 'json',
        success: function (docs) {
            documents = docs.reduce(function (map, obj) {
                map[obj.id] = obj;
                return map;
            }, {});

            console.log('documents:');
            console.log(documents);
            if (activity !== null) {
                displayActivityInfo(activity);
            }
        },
        error: function (request) {
            console.log(request);
            showErrorMessage(request.responseText);
        }
    });
}

function getActivityInfo(id) {
    $.ajax({
        type: "GET",
        url: "/api/process/activity" + "/" + id,
        dataType: 'json',
        success: function (data) {
            getDocuments(data);
        },
        error: function (request) {
            console.log(request);
            showErrorMessage(request.responseText);
        }
    });
}

function displayActivityInfo(activity) {
    var inputList = "";
    console.log(activity)
    for (var i = 0; i < activity.inputList.length; i++) {
        inputList += '<div class="panel-group" id="accordion">' +
                '<div class="panel panel-default">' +
                '<div class="panel-heading">' +
                '<h4 class="panel-title">' +
                '<a data-toggle="collapse" data-parent="#accordion" href="#colapse' + documents[activity.inputList[i]].id + '">' + documents[activity.inputList[i]].fileName + '</a>' +
                '</h4>' +
                '</div>' +
                '<div id="colapse' + documents[activity.inputList[i]].id + '" class="panel-collapse collapse">' +
                '<div class="panel-body">';
        for (var j = 0; j < documents[activity.inputList[i]].descriptors.length; j++) {
            inputList += '<p><strong>' + documents[activity.inputList[i]].descriptors[j].descriptorKey + '</strong>: ' +
                    documents[activity.inputList[i]].descriptors[j].descriptorValue + '</p>';
        }
        inputList += "</div><div class='panel-footer clearfix'>";
        inputList += "<a class='btn btn-default' target='_blank' href='/api/document/1/" + documents[activity.inputList[i]].id
                + "' title='View file'><span class='icon_folder-open'></span> View file </a>" +
                "<a class='btn btn-default pull-right' download href='/api/document/download/1/" + documents[activity.inputList[i]].id +
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
                '<a data-toggle="collapse" data-parent="#accordion" href="#colapse' + documents[activity.outputList[i]].id + '">' + documents[activity.outputList[i]].fileName + '</a>' +
                '</h4>' +
                '</div>' +
                '<div id="colapse' + documents[activity.outputList[i]].id + '" class="panel-collapse collapse">' +
                '<div class="panel-body">';
        for (var j = 0; j < documents[activity.outputList[i]].descriptors.length; j++) {
            outputList += '<p><strong>' + documents[activity.outputList[i]].descriptors[j].descriptorKey + '</strong>: ' +
                    documents[activity.outputList[i]].descriptors[j].descriptorValue + '</p>';
        }
        outputList += "</div><div class='panel-footer'>";
        outputList += "<a target='_blank' href='/api/document/1/" + documents[activity.outputList[i]].id + "'>View file </a>" +
                "<a class='btn btn-default pull-right' download href='/api/document/download/1/" + documents[activity.outputList[i]].id +
                "' title='Download'><span class='icon_cloud-download'></span> Download file</a>";
        outputList += '</div></div></div></div> ';
    }
    inputListDocumentTypes = activity.inputListDocumentTypes;
    outputListDocumentTypes = activity.outputListDocumentTypes;
    if (inputListDocumentTypes.length === 0 && outputListDocumentTypes.length === 0) {
        showErrorMessage({"message": "Activity doesn't have document types"});
        return;
    }
    $('#outputList').html(outputList);
    $("#form-document").hide();
    $("#activity-info").show();
    $("#btn-add-document").show();
}

function showDescriptors(descriptors) {
    console.log("desc " + descriptors);
    $("#descriptors").html("");
    var html = "";
    if (descriptors !== null) {
        for (var i = 0; i < descriptors.length; i++) {
            console.log(descriptors[i]);
            if (descriptors[i].descriptorValue === null) {
                if (descriptors[i].paramClass === 'java.util.Date') {
                    html = '<div class="form-group">' +
                            '<label for="' + descriptors[i].descriptorKey + '" class="control-label col-lg-4">' + descriptors[i].descriptorKey
                            + '<span class="required"> *</span></label><div class="col-lg-8">' +
                            '<input type="text" class="form-control descriptors" name="' + descriptors[i].descriptorKey
                            + '" id="' + descriptors[i].descriptorKey + '" placeholder="Enter ' + descriptors[i].descriptorKey
                            + ' in format DD.MM.YYYY" required></div></div>';
                } else {
                    html = '<div class="form-group">' +
                            '<label for="' + descriptors[i].descriptorKey + '" class="control-label col-lg-4">' + descriptors[i].descriptorKey +
                            ' <span class="required">*</span></label><div class="col-lg-8">' +
                            '<input type="text" class="form-control descriptors" name="' + descriptors[i].descriptorKey +
                            '" id="' + descriptors[i].descriptorKey + '" placeholder="Enter ' + descriptors[i].descriptorKey + '" required>' +
                            '</div></div>';
                }
                $("#descriptors").append(html);
            }
        }
    }
}

function showFormAddDocument() {
    if (documentTypes[inputListDocumentTypes[0]] === undefined) {
        showErrorMessage({"message": "Activity doesn't have document types"});
        return;
    }
    $("#activity-info").hide();
    $("#btn-add-document").hide();
    $("#docTypeLabel").text("Input document types");
    $("#docType").html("");
    for (var i = 0; i < inputListDocumentTypes.length; i++) {
        $("#docType").append('<option value="' + documentTypes[inputListDocumentTypes[i]].id + '">' + documentTypes[inputListDocumentTypes[i]].name + '</option>');
    }
    $("#inputOption").prop("checked", true);
    $("#file").val("");
    showDescriptors(documentTypes[inputListDocumentTypes[0]].descriptors);
    $("#form-document").show();
}

function saveDocument() {
//    if ($("#register_form").valid()) {
    var data = new FormData();
    data.append("ownerId", company);
    data.append("file", $("#file").prop('files')[0]);
    data.append("documentTypeId", $("#docType").val());
    data.append("activityId", selectedNode.id);
    data.append("input", $("input[name='inputOutput']:checked").val() === "input");
    var descriptors = $(".descriptors");
    for (var i = 0; i < descriptors.length; i++) {
        data.append([descriptors[i].name], descriptors[i].value);
    }
    $.ajax({
        type: "POST",
        url: "/api/descriptor/upload",
        data: data,
        processData: false,
        enctype: 'multipart/form-data',
        contentType: false,
        dataType: 'json',
        beforeSend: function (request) {
            request.setRequestHeader(header, token);
        },
        success: function (data) {
//                checked = false;
//                isSure = false;
            console.log(data);
            $("#activity-info").hide();
            $("#form-document").hide();
            $("#btn-add-document").hide();
            selectedNode = null;
            $('#processes').jstree("deselect_all");
            $('#processes').jstree(true).refresh();
            showSuccessMessage("Document successfully added");
        },
        error: function (request) {
            console.log(request);
            showErrorMessage(request.responseText);
        }
    });
//    } 
}
function reset(data) {
    $("#activity-info").hide();
    $("#form-document").hide();
    $("#btn-add-document").hide();
    selectedNode = null;
    data.instance.deselect_node(data.node, true);
}