package com.lisa;

public class ChatServerMain {
    private static ChatServer chatServer = null;

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Usage: java ChatServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        chatServer = new ChatServer(portNumber);

        Thread mainThread = Thread.currentThread();
        ShutdownThread shutdownThread = new ShutdownThread(mainThread);
        Runtime.getRuntime().addShutdownHook(shutdownThread);

        chatServer.run();
        System.out.println("Finished");

    }

    public static class ShutdownThread extends Thread {
        private Thread mainThread;

        public ShutdownThread(Thread mainThread) {
            this.mainThread = mainThread;
        }

        public void run() {
            chatServer.shutdown();
            try {
                mainThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Exit. Shutdown hook ran!");
        }
    }
}