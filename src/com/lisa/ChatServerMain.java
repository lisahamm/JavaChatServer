package com.lisa;

public class ChatServerMain {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        ChatServer chatServer = new ChatServer(portNumber);
        chatServer.run();
    }


}