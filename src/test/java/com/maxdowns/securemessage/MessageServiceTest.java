package com.maxdowns.securemessage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageServiceTest {

    @Test
    void shouldCreateDraftMessage() {
        MessageService service = new MessageService();

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
        MessageService service = new MessageService();

        Message message = service.createDraft(
                "Max",
                "Receiver",
                "Hello"
        );

        service.send(message);

        assertEquals(MessageStatus.SENT, message.getStatus());
    }
}