var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var selectedNode = null;
var canEdit = false;
var modeEdit = "edit";
var modeAdd = "add";
var mode = modeEdit;
var isSure = false;
var action_url_processes_api, action_url_show_process_api, action_url_show_activity_api, action_url_edit_process_api, action_url_edit_activity_api;
function getProcessesForAddProcess(user) {
    $('#processes').bind('ready.jstree', function (e, data) {
        $("#btn-add").show();
    }).jstree({
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
        if (selectedNode !== null && selectedNode.id === data.node.original.id) {
            reset(data);
            return;
        }
        selectedNode = data.node.original;
        if (data.node.original.primitive) {
            getInfo(action_url_show_process_api + "/" + selectedNode.id);
            $("#btn-add").prop("disabled", false);
            $("#btn-add").text("Add activity");
        } else if (data.node.original.activity) {
            getInfo(action_url_show_activity_api + "/" + selectedNode.id);
            $("#btn-add").prop("disabled", true);
        } else {
            getInfo(action_url_show_process_api + "/" + selectedNode.id);
            $("#btn-add").text("Add process");
            $("#btn-add").prop("disabled", false);
        }
    });
}
function getInfo(url) {
    $.ajax({
        type: "GET",
        url: url,
        beforeSend: function (request) {
            request.setRequestHeader(header, token);
        },
        dataType: 'json',
        success: function (data) {
            $('#id').val(data.id);
            $('#name').val(data.name);
            canEdit = false;
            if (selectedNode !== null && selectedNode.activity) {
                $("#form-primitive").hide();
                $("#input_document_types > option").attr("selected", false);
                for (var i = 0; i < data.inputListDocumentTypes.length; i++) {
                    $("#input_document_types").find("option[value=" + data.inputListDocumentTypes[i].id + "]").
                            prop("selected", "selected");
                }
                $("#output_document_types > option").attr("selected", false);
                for (var i = 0; i < data.outputListDocumentTypes.length; i++) {
                    $("#output_document_types").find("option[value=" + data.outputListDocumentTypes[i].id + "]").
                            prop("selected", "selected");
                }
                $("#form_output_document_types").show();
                $("#form_input_document_types").show();
            } else {
                $("#form_output_document_types").hide();
                $("#form_input_document_types").hide();
                $("#form-primitive").show();
                $('#primitive').prop("checked", data.primitive);
            }
            disableForm();
            $("#message-box-container").hide();
            $('#info').show();
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
function checkData() {
    if (mode === modeEdit) {
        $("#btn-edit").prop("type", "button");
        if (!canEdit) {
            $('#name').prop("disabled", false);
            $('#primitive').prop("disabled", false);
            $("#input_document_types").prop("disabled", true);
            $("#output_document_types").prop("disabled", true);
            $("#primitive").prop("disabled", true);
            canEdit = true;
            $("#btn-edit").text("Save");
        } else {
            var params = {};
            params.id = selectedNode.id;
            params.name = $("#name").val();
            if (params.name === "") {
                $("#register_form").submit();
                return;
            }
            var url = "";
            if (selectedNode.activity) {
                url = action_url_edit_activity_api;
                var selected = [];
                $('#input_document_types :selected').each(function () {
                    selected[$(this).val()] = $(this).val();
                });
            } else {
                url = action_url_edit_process_api;
                params.primitive = $("#primitive").prop('checked');
            }
            if (!isSure && !selectedNode.activity && !selectedNode.primitive && params.primitive) {
                showPopUp("Setting process to primitive will delete all child nodes of this process, are you sure?");
            } else if (!isSure && !selectedNode.activity && selectedNode.primitive && !params.primitive) {
                showPopUp("Setting process to non primitive will delete all documents from activities, are you sure?");
            } else {
                canEdit = false;
                edit(url, params);
            }
        }
    } else if (mode === modeAdd) {
        $("#isActivity").val(selectedNode !== null ? selectedNode.primitive : false);
        $("#parent").val(selectedNode !== null ? selectedNode.id : null);
        $("#register_form").submit();
    }
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
    checkData();
}
function closeModal() {
    isSure = false;
    $('#modal').modal('hide');
}
function edit(url, params) {
    $.ajax({
        type: "POST",
        url: url,
        data: $("#register_form").serialize(),
        beforeSend: function (request) {
            request.setRequestHeader(header, token);
        },
        success: function (data) {
            showMessage(data, "alert-success");
            canEdit = false;
            isSure = false;
            disableForm();
            $('#processes').jstree(true).refresh();
            selectedNode.name = params["name"];
            if (!selectedNode.activity) {
                selectedNode.primitive = params["primitive"];
                if (selectedNode.primitive) {
                    $("#btn-add").prop("disabled", false);
                } else {
                    $("#btn-add").prop("disabled", false);
                }
            } else {
                $("#btn-add").prop("disabled", true);
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
function add() {
    mode = modeAdd;
    showFormForAdding();
}
function reset(data) {
    data.instance.deselect_node(data.node, true);
    selectedNode = null;
    $('#id').val(selectedNode);
    $('#info').hide();
    $("#btn-add").prop("disabled", false);
}
function disableForm() {
    $("#name").prop("disabled", true);
    $("#input_document_types").prop("disabled", true);
    $("#output_document_types").prop("disabled", true);
    $("#primitive").prop("disabled", true);
    $("#btn-edit").text("Edit");
    $("#btn-edit").text("Edit");
    hideErrorForName();
    mode = modeEdit;
}
function showMessage(data, messageType) {
    $("#message-box").removeClass("alert-success");
    $("#message-box").removeClass("alert-danger");
    $("#message-box").addClass(messageType);
    $("#message-text").html(data);
    $("#message-box-container").show();
}
function showFormForAdding() {
    hideErrorForName();
    $("#name").prop("disabled", false);
    $("#name").val("");
    if (selectedNode !== null && selectedNode.primitive) {
        $("#input_document_types").prop("disabled", false);
        $("#output_document_types").prop("disabled", false);
        $("#form-primitive").hide();
        $("#form_output_document_types").show();
        $("#form_input_document_types").show();
        $("#form_input_document_types option:selected").removeAttr("selected");
        $("#form_output_document_types option:selected").removeAttr("selected");
    } else {
        $("#form-primitive").show();
        $("#form_output_document_types").hide();
        $("#form_input_document_types").hide();
        $("#primitive").prop("disabled", false);
        $('#primitive').prop("checked", false);
    }
    $("#btn-edit").text("Add");
    $('#info').show();
}
function hideErrorForName() {
    if ($("label[for='name']").hasClass("error")) {
        $("label[for='name']").eq(1).hide();
        $("label[for='name']").eq(1).removeClass("error");
        $("#name").removeClass("error");
    }
}