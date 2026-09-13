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
}
