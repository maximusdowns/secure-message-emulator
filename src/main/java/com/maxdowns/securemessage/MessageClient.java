package com.maxdowns.securemessage;

import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;

public class MessageClient {

    public static void main(String[] args) {

        // Ex: Java AutoClosable "try with resources" mechanism, similar to C# "using"
        // This socket is a resource. When execution leaves this block, Java will close it for me.
        try (Socket socket = new Socket("localhost", 5000)) {

            // PrintWriter acts as a text-oriented interface over a lower-level byte stream
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            Message message = new Message(
                    "Max",
                    "Alice",
                    "Hello from a real Message object!"
            );

            Message secondMessage = new Message(
                    "Max",
                    "Bob",
                    "This is the second message!"
            );

            Message thirdMessage = new Message(
                    "Max",
                    "Charlie",
                    "This is the third message!"
            );

            // The client shouldn't know how the protocol is constructed, it just asks MessageProtocol to serialize the object and sends the result
            String serializedMessage = MessageProtocol.serialize(message);
            String serializedSecondMessage = MessageProtocol.serialize(secondMessage);
            String serializedThirdMessage = MessageProtocol.serialize(thirdMessage);

            writer.println(serializedMessage);
            writer.println(serializedSecondMessage);
            writer.println(serializedThirdMessage);

            System.out.println("Message sent to server: " + serializedMessage);
        } catch (IOException exception) {
            System.err.println("Unable to connect to message server: "
                    + exception.getMessage());
        }
    }

}
