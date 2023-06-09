package com.example.transportmanager.Student;

public class MessageModel {
    String username,message,messageId;

    public MessageModel(String username, String message, String messageId) {
        this.username = username;
        this.message = message;
        this.messageId = messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
