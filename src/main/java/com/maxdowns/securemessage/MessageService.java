package com.maxdowns.securemessage;

public class MessageService {

    private final MessageRepository repository;  //dependency inversion, we didn't need to write private final InMemoryMessageRepository repository;

    // constructor
    public MessageService(MessageRepository repository){  //dependency injection
        this.repository = repository;
    }

    public Message createDraft(String sender, String recipient, String body){
        Message message = new Message(sender, recipient, body);

        repository.save(message);

        return message;
    }

    public Message send(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }

        message.send();
        repository.save(message);

        return message;
    }
}
