package com.maxdowns.securemessage;

import java.util.ArrayList;
import java.util.List;

public class InMemoryMessageRepository implements MessageRepository{
    private final List<Message> messages = new ArrayList<>();

    @Override
    public void save(Message message) {
        messages.add(message);
    }

    @Override
    public Message findLatest() {
        if (messages.isEmpty()) {
            return null;
        }

        return messages.get(messages.size() - 1);
    }

    @Override
    public List<Message> findAll() {
        return new ArrayList<>(messages);  // example of defensive copy
    }

    @Override
    public List<Message> findByRecipient(String recipient) {
        List<Message> matchingMessages = new ArrayList<>();

        for (Message message : messages){  // for each message in messages
            if (message.getRecipient().equals(recipient)) {
                matchingMessages.add((message));
            }
        }
        return matchingMessages;
    }

    @Override
    public List<Message> findByStatus(MessageStatus status) {
        List<Message> matchingMessages = new ArrayList<>();

        for (Message message : messages){  // for each message in messages
            if (message.getStatus() == status) {
                matchingMessages.add((message));
            }
        }
        return matchingMessages;
    }
}
