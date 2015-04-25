package com.lisa;

import java.io.*;
import java.net.*;

public class ChatThread extends Thread implements Observer {
    private Socket clientSocket = null;
    private ChatSubject chatSubject = null;
    private PrintWriter printWriter = null;

    public ChatThread(Socket socket, ChatSubject chatSubject) {
        super("ChatThread");
        this.clientSocket = socket;
        this.chatSubject = chatSubject;
    }

    public void run() {
        try (
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()))
        ) {
            this.printWriter = out;
            chatSubject.registerObserver(this);
            out.println("Hello, there. Please enter a username to join the chat: ");
            String username = in.readLine();

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String message = username + " says: " + inputLine;
                System.out.println(message);
                if(inputLine.equals("Bye"))
                    break;
                chatSubject.notifyObservers(message);
            }
            close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(String message) {
        printWriter.println(message);
    }

    protected void close() throws IOException {
        chatSubject.deregisterObserver(this);
        printWriter.flush();
        printWriter.close();
        clientSocket.close();
    }
}