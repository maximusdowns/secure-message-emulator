package com.maxdowns.securemessage;

public class MessageService {

    public Message createDraft(String sender, String recipient, String body){
        return new Message(sender, recipient, body);
    }

    public Message send(Message message) {
        message.send();
        return message;
    }
}
