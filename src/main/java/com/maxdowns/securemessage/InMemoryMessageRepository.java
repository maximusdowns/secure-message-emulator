package com.maxdowns.securemessage;

public class InMemoryMessageRepository implements MessageRepository{
    private Message latestMessage;

    @Override
    public void save(Message message) {
        latestMessage = message;
    }

    @Override
    public Message findLatest() {
        return latestMessage;
    }
}
