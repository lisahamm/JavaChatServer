package com.lisa;
import java.net.*;
import java.io.*;

public class ChatThread extends Thread {
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
            out.println("Hello");
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                out.println(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

