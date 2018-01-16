<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="modal fade" id="modal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title" id="modal-question-title">Are you sure?</h4>
            </div>
            <div class="modal-body">
                <p id="modal-question-text"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" onclick="sendRequest()">Yes</button>
                <button type="button" class="btn btn-default" onclick="closeModal()">No</button>
            </div>
        </div>
    </div>
</div>