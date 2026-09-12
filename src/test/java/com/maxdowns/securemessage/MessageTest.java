package com.maxdowns.securemessage;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

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

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t"})
    void messageShouldRejectBlankRecipient(String invalidRecipient) {
        assertThrows(IllegalArgumentException.class, () ->
                new Message("Max", invalidRecipient, "Hello")
        );
    }
}
