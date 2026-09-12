package com.maxdowns.securemessage;

public interface MessageRepository {
    void save(Message message);

    Message findLatest();
}
