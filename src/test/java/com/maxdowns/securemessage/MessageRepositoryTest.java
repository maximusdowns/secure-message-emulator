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

    @Test
    void shouldFindMessagesByRecipient() {
        MessageRepository repository = new InMemoryMessageRepository();

        Message aliceMessageOne = new Message(
                "Max",
                "Alice",
                "First message for Alice"
        );

        Message bobMessage = new Message(
                "Max",
                "Bob",
                "Message for Bob"
        );

        Message aliceMessageTwo = new Message(
                "Max",
                "Alice",
                "Second message for Alice"
        );

        repository.save(aliceMessageOne);
        repository.save(bobMessage);
        repository.save(aliceMessageTwo);

        List<Message> aliceMessages = repository.findByRecipient("Alice");

        assertEquals(2, aliceMessages.size());
        assertEquals(aliceMessageOne, aliceMessages.get(0));
        assertEquals(aliceMessageTwo, aliceMessages.get(1));
    }

    @Test
    void shouldReturnEmptyListWhenRecipientHasNoMessages() {
        MessageRepository repository = new InMemoryMessageRepository();

        Message message = new Message(
                "Max",
                "Alice",
                "Hello Alice"
        );

        repository.save(message);

        List<Message> messages = repository.findByRecipient("Charlie");

        assertEquals(0, messages.size());
    }
}