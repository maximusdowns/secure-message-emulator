package com.maxdowns.securemessage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessageTest {

    @Test
    void newMessageShouldStartAsDraft(){
        Message message = new Message("Max", "Receiver", "Hello");

        assertEquals(MessageStatus.DRAFT, message.getStatus());
    }

    @Test
    void messageShouldRetainSender(){
        Message message = new Message("Max", "Receiver", "Hello");

        assertEquals("Max", message.getSender());
    }

    @Test
    void messageShouldRetainRecipient(){
        Message message = new Message("Max", "Receiver", "Hello");

        assertEquals("Receiver", message.getRecipient());
    }
    @Test
    void messageShouldRetainBody(){
        Message message = new Message("Max", "Receiver", "Hello");

        assertEquals("Hello", message.getBody());
    }

    @Test
    void messageShouldRejectBlankRecipient() {
        assertThrows(IllegalArgumentException.class, () ->
                new Message("Max", "", "Hello")
        );
    }
}
