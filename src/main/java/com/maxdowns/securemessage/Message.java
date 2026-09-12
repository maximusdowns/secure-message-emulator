package com.maxdowns.securemessage;

import java.time.Instant;

public class Message {

    private final String sender;
    private final String recipient;
    private final String body;
    private MessageStatus status;
    private Instant sentAt;

    public Message(String sender, String recipient, String body) {
        if (sender == null || sender.isBlank()) {
            throw new IllegalArgumentException("Sender cannot be blank");
        }

        if (recipient == null || recipient.isBlank()) {
            throw new IllegalArgumentException("Recipient cannot be blank");
        }

        if (body == null || body.isBlank()) {
            throw new IllegalArgumentException("Body cannot be blank");
        }

        this.sender = sender;
        this.recipient = recipient;
        this.body = body;
        this.status = MessageStatus.DRAFT;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getBody() {
        return body;
    }

    public Instant getSentAt(){
        return sentAt;
    }

    public void send() {
        if (status == MessageStatus.SENT){
            throw new IllegalStateException("Message has already been sent");
        }

        status = MessageStatus.SENT;
        sentAt = Instant.now();
    }

}
