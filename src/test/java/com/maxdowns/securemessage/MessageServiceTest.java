package com.maxdowns.securemessage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

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

    // updated to use test double
    @Test
    void sendingMessageShouldSaveUpdatedMessageInRepository() {
        TrackingMessageRepository repository = new TrackingMessageRepository();
        MessageService service = new MessageService(repository);  //Inject that repository into my service

        Message message = service.createDraft(  //Create a draft through the service
                "Max",
                "Receiver",
                "Hello"
        );

        assertEquals(1, repository.getSaveCount());

        service.send(message);

        assertEquals(2, repository.getSaveCount());
    }

    // Test Double (precursor to Mockito)
    private static class TrackingMessageRepository implements MessageRepository {
        private Message latestMessage;
        private int saveCount;

        @Override
        public void save(Message message) {
            latestMessage = message;
            saveCount++;
        }

        @Override
        public Message findLatest() {
            return latestMessage;
        }

        public int getSaveCount() {
            return saveCount;
        }

        @Override
        public List<Message> findAll() {
            if (latestMessage == null) {
                return List.of();  //Java way of creating an empty list
            }

            return List.of(latestMessage); //list of latestMessage
        }
    }
}