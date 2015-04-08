package com.lisa;
import java.net.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private static Set<PrintWriter> printWriters = new HashSet<PrintWriter>();

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket =
                     new ServerSocket(Integer.parseInt(args[0]))) {
            System.out.println("Server is listening on port: " + portNumber);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection made with " + clientSocket);
                new ChatThread(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    public static class ChatThread extends Thread {
        private Socket clientSocket = null;

        public ChatThread(Socket socket) {
            super("ChatThread");
            this.clientSocket = socket;
        }

        public void run() {
            try (
                    PrintWriter out =
                            new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
            ) {

                printWriters.add(out);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                    for(PrintWriter printWriter : printWriters) {
                        printWriter.println(inputLine);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}