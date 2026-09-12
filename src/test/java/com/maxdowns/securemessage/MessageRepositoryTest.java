package com.maxdowns.securemessage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageRepositoryTest {

    @Test
    void savedMessageShouldBeRetrievable() {
        MessageRepository repository = new InMemoryMessageRepository();  //polymorphism - Different kinds of objects can be treated through the same common type

        Message message = new Message(
                "Max",
                "Receiver",
                "Hello"
        );

        repository.save(message);

        Message savedMessage = repository.findLatest();

        assertEquals(message, savedMessage);
    }
}