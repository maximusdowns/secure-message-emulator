package com.maxdowns.securemessage;

import java.util.List;

public interface MessageRepository {
    void save(Message message);

    Message findLatest();

    List<Message> findAll(); //generic

    List<Message> findByRecipient(String recipient);

    List<Message> findByStatus(MessageStatus status);
}
