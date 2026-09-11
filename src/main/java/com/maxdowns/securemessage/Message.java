package com.maxdowns.securemessage;

public class Message {

    private final String sender;
    private final String recipient;
    private final String body;
    private final MessageStatus status;

    public Message(String sender, String recipient, String body) {
        this.sender = sender;
        this.recipient = recipient;
        this.body = body;
        this.status = MessageStatus.DRAFT;
    }

    public MessageStatus getStatus(){
        return status;
    }
}
