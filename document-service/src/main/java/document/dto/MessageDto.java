/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document.dto;

/**
 *
 * @author Jelena
 */
public class MessageDto {

    public static String MESSAGE_TYPE_SUCCESS = "alert-success";
    public static String MESSAGE_TYPE_ERROR = "alert-danger";
    public static String MESSAGE_TYPE_QUESTION = "question";
    public static String MESSAGE_ACTION_EDIT = "edit";
    public static String MESSAGE_ACTION_ADD = "add";

    private String messageType;
    private String messageText;
    private Object messageData;
    private String messageAction;

    public MessageDto(String messageType, String messageText) {
        this.messageType = messageType;
        this.messageText = messageText;
    }

    public MessageDto(String messageType, String messageText, String messageAction) {
        this.messageType = messageType;
        this.messageText = messageText;
        this.messageAction = messageAction;
    }

    public MessageDto(String messageType, String messageText, Object messageData, String messageAction) {
        this.messageType = messageType;
        this.messageText = messageText;
        this.messageData = messageData;
        this.messageAction = messageAction;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Object getMessageData() {
        return messageData;
    }

    public void setMessageData(Object messageData) {
        this.messageData = messageData;
    }

    public String getMessageAction() {
        return messageAction;
    }

    public void setMessageAction(String messageAction) {
        this.messageAction = messageAction;
    }

    @Override
    public String toString() {
        return "MessageDto{" + "messageType=" + messageType + ", messageText=" + messageText + ", messageData=" + messageData + ", messageAction=" + messageAction + '}';
    }

}
