package com.maxdowns.securemessage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageProtocolTest {

    @Test
    void shouldSerializeMessageForTransmission() {
        Message message = new Message(
                "Max",
                "Alice",
                "Hello"
        );

        String result = MessageProtocol.serialize(message);

        assertEquals("Max|Alice|Hello", result);
    }

    @Test
    void shouldDeserializeMessageFromTransmission() {
        String transmittedMessage =
                "Max|Alice|Hello from a real Message object!";

        Message message =
                MessageProtocol.deserialize(transmittedMessage);

        assertEquals("Max", message.getSender());
        assertEquals("Alice", message.getRecipient());
        assertEquals(
                "Hello from a real Message object!",
                message.getBody()
        );
    }

    @Test
    void shouldPreserveMessageDataThroughRoundTrip() {
        Message originalMessage = new Message(
                "Max",
                "Alice",
                "Round trip test"
        );

        String transmittedMessage = MessageProtocol.serialize(originalMessage);

        Message reconstructedMessage =
                MessageProtocol.deserialize(transmittedMessage);

        assertEquals(
                originalMessage.getSender(),
                reconstructedMessage.getSender()
        );

        assertEquals(
                originalMessage.getRecipient(),
                reconstructedMessage.getRecipient()
        );

        assertEquals(
                originalMessage.getBody(),
                reconstructedMessage.getBody()
        );
    }
}
