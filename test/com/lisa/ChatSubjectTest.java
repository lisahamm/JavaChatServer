package com.lisa;

import org.junit.Before;
import org.junit.Test;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;


public class ChatSubjectTest {

    private MockObserver observer;
    private ChatSubject chatSubject;

    @Before
    public void setUp() throws Exception {
        observer = new MockObserver();
        chatSubject = new ChatSubject();
    }

    @Test
    public void testSingleRegisteredObserverNotified() throws Exception {
        assertEquals(null, observer.lastMessage);
        chatSubject.registerObserver(observer);
        String message = "Update message";
        chatSubject.notifyObservers(message);
        assertEquals(message, observer.lastMessage);
    }

    @Test
    public void testMultipleRegisteredObserversNotified() throws Exception {
        MockObserver observer2 = new MockObserver();
        MockObserver observer3 = new MockObserver();
        chatSubject.registerObserver(observer);
        chatSubject.registerObserver(observer2);
        chatSubject.registerObserver(observer3);
        String message = "Update message";
        chatSubject.notifyObservers(message);
        assertEquals(message, observer.lastMessage);
        assertEquals(message, observer2.lastMessage);
        assertEquals(message, observer3.lastMessage);
    }

    @Test
    public void testDeregisterObserver() throws Exception {
        String firstMessage = "Message 1";
        String secondMessage = "Message 2";
        chatSubject.registerObserver(observer);
        chatSubject.notifyObservers(firstMessage);
        assertEquals(firstMessage, observer.lastMessage);
        chatSubject.deregisterObserver(observer);
        chatSubject.notifyObservers(secondMessage);
        assertEquals(firstMessage, observer.lastMessage);
        assertFalse(secondMessage == observer.lastMessage);
    }

    public class MockObserver implements Observer {
        public String lastMessage;
        public void update(String message) {
            lastMessage = message;
        }
    }
}
