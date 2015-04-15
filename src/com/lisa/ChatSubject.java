package com.lisa;

import java.io.*;
import java.util.*;

public class ChatSubject {
    private Vector itsObservers = new Vector();

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
