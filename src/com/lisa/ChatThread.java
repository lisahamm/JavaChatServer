package com.lisa;

import java.io.*;
import java.net.*;

public class ChatThread extends Thread implements Observer {
    private ChatSubject chatSubject = null;
    private MyWriter out = null;
    private MyReader in = null;

    public ChatThread(MyWriter out, MyReader input, ChatSubject chatSubject) {
        super("ChatThread");
        this.out = out;
        this.in = input;
        this.chatSubject = chatSubject;
    }

    public void run() {
        try {
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
            e.printStackTrace();
        }
    }

    public void update(String message) {
        out.println(message);
    }

    protected void close() throws IOException {
        chatSubject.deregisterObserver(this);
        in.close();
        out.flush();
        out.close();
        chatSubject.getClientSocket(Thread.currentThread().getId()).close();
    }
}