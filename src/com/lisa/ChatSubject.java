package com.lisa;

import java.net.Socket;
import java.util.*;

public class ChatSubject {
    private Hashtable<Long, Socket> clientSockets = new Hashtable<>();
    private Vector itsObservers = new Vector();

    public void notifyObservers(String message) {
        synchronized (itsObservers) {
            Iterator i = itsObservers.iterator();
            while (i.hasNext()) {
                Observer observer = (Observer) i.next();
                observer.update(message);
            }
        }
    }

    public void registerClientSocket(Long threadID, Socket clientSocket) {
        clientSockets.put(threadID, clientSocket);
    }

    public Socket getClientSocket(Long threadID) {
        return clientSockets.get(threadID);
    }

    public void registerObserver(Observer observer) {
        itsObservers.add(observer);
    }

    public void deregisterObserver(Observer observer) {
        itsObservers.remove(observer);
    }

    public Set getItsObservers() {
        Set<Observer> chatThreads = new HashSet<>();
        synchronized (itsObservers) {
            Iterator i = itsObservers.iterator();
            while (i.hasNext()) {
                ChatThread chatThread = (ChatThread) i.next();
                chatThreads.add(chatThread);
            }
        }
        return chatThreads;
    }
}
