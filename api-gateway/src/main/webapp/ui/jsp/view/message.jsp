<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${not empty message}">
        <div id="message-box-container">
            <div class="alert ${message.messageType} " id="message-box">
                <%--<a href="#" class="close" data-dismiss="alert" aria-label="close">�</a>--%>
                <strong id="message-text">${message.messageText}</strong> 
            </div> 
        </div>
    </c:when>
    <c:otherwise>
        <div id="message-box-container" style="display: none;">
            <div class="alert" id="message-box">
                <%--<a href="#" class="close" data-dismiss="alert" aria-label="close">�</a>--%>
                <strong id="message-text"></strong> 
            </div> 
        </div>
    </c:otherwise>
</c:choose>
