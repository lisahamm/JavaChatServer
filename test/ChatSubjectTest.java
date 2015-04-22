import com.lisa.ChatSubject;
import com.lisa.Observer;
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
    public void testRegisterObserver() throws Exception {
        chatSubject.registerObserver(observer);
        String message = "Update message";
        chatSubject.notifyObservers(message);
        assertEquals(message, observer.lastMessage);
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
    //
//    @Test
//    public void testNotifyObservers() throws Exception {
//
//    }
//
//    @Test
//    public void testGetItsObservers() throws Exception {
//
//    }

    public class MockObserver implements Observer {
        public String lastMessage;
        public void update(String message) {
            lastMessage = message;
        }
    }
}
