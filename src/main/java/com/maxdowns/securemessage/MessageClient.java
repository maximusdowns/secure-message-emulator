package com.maxdowns.securemessage;

import java.io.IOException;
import java.io.PrintWriter;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.security.GeneralSecurityException;
import java.nio.file.Path;

public class MessageClient {

    public static void main(String[] args) {

        String trustStorePasswordValue = System.getenv("SECURE_MESSAGE_TRUSTSTORE_PASSWORD");

        if (trustStorePasswordValue == null || trustStorePasswordValue.isBlank()) {
            System.err.println(
                    "SECURE_MESSAGE_TRUSTSTORE_PASSWORD environment variable is not set."
            );
            return;
        }

        char[] trustStorePassword = trustStorePasswordValue.toCharArray();

        String trustStorePath = Path.of(System.getProperty("user.dir"), "security", "client-truststore.p12").toString();

        SSLContext sslContext;

        try {
             sslContext = TlsContextFactory.createClientContext(
                     trustStorePath,
                     trustStorePassword
             );
        } catch (GeneralSecurityException | IOException exception) {
             System.err.println(
                     "Unable to configure TLS: " + exception.getMessage()
             );
             return;
        }

        SSLSocketFactory socketFactory = sslContext.getSocketFactory();

        // Ex: Java AutoClosable "try with resources" mechanism, similar to C# "using"
        // This socket is a resource. When execution leaves this block, Java will close it for me.
        try (SSLSocket socket = (SSLSocket) socketFactory.createSocket("localhost", 5000)) {

            socket.startHandshake();
            System.out.println("TLS handshake successful.");

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
