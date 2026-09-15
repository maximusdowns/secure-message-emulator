package com.maxdowns.securemessage;

import java.util.List;

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

    // Tell this message to send itself, thenm ake sure its changed state is saved through the repository
    public Message send(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }

        message.send();  //Change MY state from DRAFT → SENT and record when I was sent
        repository.save(message);

        return message;
    }

    public List<Message> findByRecipient(String recipient) {
        return repository.findByRecipient(recipient);
    }
}
