package com.lisa;
import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer implements Runnable {
    private int portNumber = 0;
    private boolean running = true;
    public ChatSubject chatSubject = new ChatSubject();
    private ServerSocket serverSocket;

    public ChatServer(int portNumber) {
        this.portNumber = portNumber;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Server is listening on port: " + portNumber);
            while (running) {
                this.serverSocket = serverSocket;
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection made with " + clientSocket);

                MyWriter out = new MyPrintWriter(clientSocket.getOutputStream());
                MyReader in = new MyBufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                ChatThread chatThread = new ChatThread(out, in, chatSubject);
                chatThread.start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    public void shutdown() {
        running = false;
        try {
            closeThreads();
            joinThreads();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeThreads() throws InterruptedException {
        synchronized (chatSubject.getItsObservers()) {
            Iterator i = chatSubject.getItsObservers().iterator();
            while (i.hasNext()) {
                ChatThread chatThread = (ChatThread) i.next();
                try {
                    chatThread.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void joinThreads() {
        synchronized (chatSubject.getItsObservers()) {
            Iterator i = chatSubject.getItsObservers().iterator();
            while (i.hasNext()) {
                ChatThread chatThread = (ChatThread) i.next();
                try {
                    chatThread.join();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}