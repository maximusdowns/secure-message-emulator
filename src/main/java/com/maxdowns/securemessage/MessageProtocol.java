package com.maxdowns.securemessage;

// Programming Best Practice: Separation of concerns
// MessageClient doesn't know HOW serialization works
// MessageServer doesn't know HOW parsing works

public class MessageProtocol {

    //MessageProtocal, take this Message and convert it to the protocol representation
    public static String serialize(Message message) {
        return message.getSender()
                + "|"
                + message.getRecipient()
                + "|"
                + message.getBody();
    }

    public static Message deserialize(String transmittedMessage) {

        String[] parts = transmittedMessage.split("\\|");

        String sender = parts[0];
        String recipient = parts[1];
        String body = parts[2];

        return new Message(sender, recipient, body);
    }
}
