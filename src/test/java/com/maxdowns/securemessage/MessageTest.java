package com.maxdowns.securemessage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageTest {

    @Test
    void newMessageShouldStartAsDraft(){
        Message message = new Message("Max", "Receiver", "Hello");

        assertEquals(MessageStatus.DRAFT, message.getStatus());
    }
}
