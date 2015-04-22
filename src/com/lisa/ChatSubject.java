package com.lisa;

import java.util.*;

public class ChatSubject {
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
