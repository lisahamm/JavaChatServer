package com.lisa;

public class ChatServerMain {
    private static ChatServer chatServer = null;

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        chatServer = new ChatServer(portNumber);

        ShutdownThread shutdownThread = new ShutdownThread();
        Runtime.getRuntime().addShutdownHook(shutdownThread);

        chatServer.run();
    }

    public static class ShutdownThread extends Thread {
        public void run() {
            chatServer.setKeepRunning(false);
            try {
                chatServer.closeThreads();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Exit. Shutdown hook ran!");
        }
    }
}