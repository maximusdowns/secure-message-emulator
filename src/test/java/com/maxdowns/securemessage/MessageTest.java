package com.maxdowns.securemessage;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import java.time.Instant;


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

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t"})
    void messageShouldRejectBlankSender(String invalidSender) {
        assertThrows(IllegalArgumentException.class, () ->
                new Message(invalidSender, "Receiver", "Hello")
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t"})
    void messageShouldRejectBlankBody(String invalidBody) {
        assertThrows(IllegalArgumentException.class, () ->
                new Message("Max", "Receiver", invalidBody)
        );
    }

    @Test
    void sendingMessageShouldChangeStatusToSent(){
        Message message = new Message("Max", "Receiver", "Hello");

        message.send();

        assertEquals(MessageStatus.SENT, message.getStatus());
    }

    @Test
    void sentMessageShouldNotBeSentAgain(){
        Message message = new Message("Max", "Receiver", "Hello");

        message.send();

        assertThrows(IllegalStateException.class, message::send); // message::send = () -> message.send()
    }

    @Test
    void newMessageShouldNotHaveSentTimestamp() {
        Message message = new Message("Max", "Receiver", "Hello");

        assertNull(message.getSentAt());
    }

    @Test
    void sentMessageShouldHaveSentTimestamp() {
        Message message = new Message("Max", "Receiver", "Hello");

        message.send();

        assertNotNull(message.getSentAt());
    }

    @Test
    void sentMessageShouldRecordTimeItWasSent() {
        Message message = new Message("Max", "Receiver", "Hello");

        Instant beforeSend = Instant.now();

        message.send();

        Instant afterSend = Instant.now();

        assertFalse(message.getSentAt().isBefore(beforeSend));
        assertFalse(message.getSentAt().isAfter(afterSend));
    }
}
