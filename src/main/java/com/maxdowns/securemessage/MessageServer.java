package com.maxdowns.securemessage;

import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageServer {

     public static void main(String[] args) {

         // Fixed thread pool limits concurrent client-handling threads.
         // This development server runs until the process is terminated.
         ExecutorService clientPool = Executors.newFixedThreadPool(4);

         try (ServerSocket serverSocket = new ServerSocket(5000)){ //listen on port 5000, managed resource

             System.out.println("Message server listening on port 5000");

             while (true) {  //keep accepting connections
                 Socket clientSocket = serverSocket.accept(); //here is the actual conversation
                 // Concurrency
                 // Here is a task. Please execute it when one of your worker threads is available.
                 clientPool.submit(() -> handleClient(clientSocket));
             }
         } catch (IOException exception) {
             System.err.println("Unable to start message server: " + exception.getMessage());
         }
     }

     private static void handleClient(Socket clientSocket) {
         try (clientSocket) {
             System.out.println("Client connected!");

             // ex: wrapping - one object wraps another to add higher-level functionality
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(clientSocket.getInputStream())
             );

             String receivedMessage;

             // remember blocking from readline(), so this conditional is saying
             // Keep waiting for and reading messages from this connection. Each time a line arrives, put it into receivedMessage
             // Keep doing that until the client closes the connection.
             while ((receivedMessage = reader.readLine()) != null) {
                 Message message = MessageProtocol.deserialize(receivedMessage);

                 System.out.println("Received message:");
                 System.out.println("  Sender: " + message.getSender());
                 System.out.println("  Recipient: " + message.getRecipient());
                 System.out.println("  Body: " + message.getBody());
             }

             System.out.println("Client disconnected.");

         } catch (IOException exception) {
             System.err.println(
                "Client connection error: " + exception.getMessage()
             );
         };
     }
}
