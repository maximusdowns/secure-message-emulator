package com.maxdowns.securemessage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

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

    @Test
    void savedMessagesShouldAllBeRetrievable() {
        MessageRepository repository = new InMemoryMessageRepository();

        Message firstMessage = new Message(
                "Max",
                "Receiver One",
                "First message"
        );

        Message secondMessage = new Message(
                "Max",
                "Receiver Two",
                "Second message"
        );

        repository.save(firstMessage);
        repository.save(secondMessage);

        List<Message> messages = repository.findAll();

        assertEquals(2, messages.size());
        assertEquals(firstMessage, messages.get(0));
        assertEquals(secondMessage, messages.get(1));
    }

    // encapsulation + defensive copying test
    @Test
    void findAllShouldReturnDefensiveCopy() {
        MessageRepository repository = new InMemoryMessageRepository();

        Message message = new Message(
                "Max",
                "Receiver",
                "Hello"
        );

        repository.save(message);

        List<Message> messages = repository.findAll();

        messages.clear();

        assertEquals(1, repository.findAll().size());
    }
}