package com.maxdowns.securemessage;

import java.util.List;

public interface MessageRepository {
    void save(Message message);

    Message findLatest();
    List<Message> findAll(); //generic
}
