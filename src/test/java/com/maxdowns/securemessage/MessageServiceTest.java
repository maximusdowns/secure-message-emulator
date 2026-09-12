package com.maxdowns.securemessage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessageServiceTest {

    @Test
    void shouldCreateDraftMessage() {
        MessageRepository repository = new InMemoryMessageRepository();  //polymorphism
        MessageService service = new MessageService(repository);  //dependency injection

        Message message = service.createDraft(
                "Max",
                "Receiver",
                "Hello"
        );

        assertEquals(MessageStatus.DRAFT, message.getStatus());
        assertEquals("Max", message.getSender());
        assertEquals("Receiver", message.getRecipient());
        assertEquals("Hello", message.getBody());
    }

    @Test
    void sendingDraftShouldMarkMessageAsSent() {
        MessageRepository repository = new InMemoryMessageRepository();
        MessageService service = new MessageService(repository);

        Message message = service.createDraft(
                "Max",
                "Receiver",
                "Hello"
        );

        service.send(message);

        assertEquals(MessageStatus.SENT, message.getStatus());
    }

    @Test
    void sendingMessageShouldReturnSentMessage() {
        MessageRepository repository = new InMemoryMessageRepository();
        MessageService service = new MessageService(repository);

        Message message = service.createDraft(
                "Max",
                "Receiver",
                "Hello"
        );

        Message sentMessage = service.send(message);

        assertEquals(MessageStatus.SENT, sentMessage.getStatus());
    }

    @Test
    void sendingNullMessageShouldThrowException() {
        MessageRepository repository = new InMemoryMessageRepository();
        MessageService service = new MessageService(repository);

        assertThrows(IllegalArgumentException.class, () ->
                service.send(null)
        );
    }

    @Test
    void createdDraftShouldBeSavedInRepository() {
        MessageRepository repository = new InMemoryMessageRepository();  //polymorphism
        MessageService  service = new MessageService(repository);  //dependency injection

        Message message = service.createDraft(
                "Max",
                "Receiver",
                "Hello"
        );

        Message savedMessage = repository.findLatest();

        assertEquals(message, savedMessage);
    }
}