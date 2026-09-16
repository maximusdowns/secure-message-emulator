package com.maxdowns.securemessage;

import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;

public class MessageClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);

            ///PrintWriter acts as a text-oriented interface over a lower-level byte stream
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            Message message = new Message(
                    "Max",
                    "Alice",
                    "Hello from a real Message object!"
            );

            //The client shouldn't know how the protocol is constructed, it just asks MessageProtocol to serialize the object and sends the result
            String serializedMessage = MessageProtocol.serialize(message);

            writer.println(serializedMessage);

            System.out.println("Message sent to server: " + serializedMessage);
        } catch (IOException exception) {
            System.err.println("Unable to connect to message server: "
                    + exception.getMessage());
        }
    }

}
