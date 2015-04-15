package com.lisa;

import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.Vector;

public class ChatThread extends Thread {
    private Vector itsObservers = new Vector();
    private Socket clientSocket = null;
    private ChatServer chatServer = null;

    public ChatThread(Socket socket, ChatServer chatServer) {
        super("ChatThread");
        this.clientSocket = socket;
        this.chatServer = chatServer;
    }

    public void run() {
        try (
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        ) {
            registerObserver(chatServer);
            chatServer.addPrintWriter(out);
            out.println("Hello, there. Please enter a username to join the chat: ");
            String username = in.readLine();

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String message = username + " says: " + inputLine;
                System.out.println(message);
                if(inputLine.equals("Bye"))
                    break;
                notifyObservers(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void notifyObservers(String message) {
        Iterator i = itsObservers.iterator();
        while (i.hasNext())
        {
            Observer observer = (Observer) i.next();
            observer.update(message);
        }
    }

    public void registerObserver(Observer observer) {
        itsObservers.add(observer);
    }
}