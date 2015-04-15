package com.lisa;
import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer implements Runnable {
    private int portNumber = 0;
    public ChatSubject chatSubject = new ChatSubject();

    public ChatServer(int portNumber) {
        this.portNumber = portNumber;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Server is listening on port: " + portNumber);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection made with " + clientSocket);
                ChatThread chatThread = new ChatThread(clientSocket, chatSubject);
                chatThread.start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}