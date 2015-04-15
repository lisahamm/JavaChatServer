package com.lisa;
import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer implements Observer, Runnable {
    private static Set<PrintWriter> printWriters = Collections.synchronizedSet(new HashSet<PrintWriter>());
    private int portNumber = 0;

    public ChatServer(int portNumber) {
        this.portNumber = portNumber;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(portNumber);
             Socket clientSocket = serverSocket.accept()) {
            System.out.println("Server is listening on port: " + portNumber);
            while (true) {
                System.out.println("Connection made with " + clientSocket);
                ChatThread chatThread = new ChatThread(clientSocket, this);
                chatThread.start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    public void addPrintWriter(PrintWriter printWriter) {
        printWriters.add(printWriter);
    }

    public void update(String message) {
        for(PrintWriter printWriter : printWriters) {
            printWriter.println(message);
        }
    }
}