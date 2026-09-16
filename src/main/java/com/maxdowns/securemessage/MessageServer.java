package com.maxdowns.securemessage;

import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MessageServer {

     public static void main(String[] args) {
         try{
             ServerSocket serverSocket = new ServerSocket(5000);  //listens on port 5000
             System.out.println("Message server listening on port 5000");

             //Blocking - execution stops until client connects
             Socket clientSocket = serverSocket.accept();  //here is the actual conversation

             System.out.println("Client connected!");

             // ex: wrapping - one object wraps another to add higher-level functionality
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(clientSocket.getInputStream())
             );

             // blocking
             String receivedMessage = reader.readLine();

             Message message = MessageProtocol.deserialize(receivedMessage);

             System.out.println("Received message:");
             System.out.println("  Sender: " + message.getSender());
             System.out.println("  Recipient: " + message.getRecipient());
             System.out.println("  Body: " + message.getBody());

         } catch (IOException exception) {
             System.err.println("Unable to start message server: " + exception.getMessage());
         }

     }
}
