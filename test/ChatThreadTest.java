import com.lisa.ChatSubject;
import com.lisa.ChatThread;
import com.lisa.MyReader;
import com.lisa.MyWriter;
import org.junit.*;

import java.net.Socket;

import static org.junit.Assert.*;


public class ChatThreadTest {
    private ChatThread chatThread;
    private MockPrintWriter out;
    private MockBufferedReader in;

    @Before
    public void setUp() throws Exception {
        ChatSubject chatSubject = new ChatSubject();
        out = new MockPrintWriter();
        in = new MockBufferedReader();
        chatThread = new ChatThread(out, in, chatSubject);
    }

    @Test
    public void testClientInputIsWrittenToOutputStream() throws Exception {
        chatThread.run();
        assertEquals("username says: message string", out.lastMessage);
    }

    @Test
    public void testIOStreamsCloseWhenInputIsNull() throws Exception {
        assertTrue(in.closed == false);
        assertTrue(out.closed == false);
        chatThread.run();
        assertTrue(in.closed == true);
        assertTrue(out.closed == true);
    }

    @Test
    public void testOutputStreamIsFlushedWhenInputIsNull() throws Exception {
        assertTrue(out.flushed == false);
        chatThread.run();
        assertTrue(out.flushed == true);
    }

    public class MockPrintWriter implements MyWriter {
        public String lastMessage;
        public boolean closed = false;
        public boolean flushed = false;

        public void println(String message) {
            lastMessage = message;
        }

        public void close() {
            closed = true;
        }

        public void flush() {
            flushed = true;
        }
    }

    public class MockBufferedReader implements MyReader {
        public Boolean closed = false;
        private int count = 0;

        public String readLine() {
            if (count == 0) {
                count++;
                return "username";
            } else if (count == 1) {
                count++;
                return "message string";
            } else {
                count++;
                return null;
            }
        }

        public void close() {
            closed = true;
        }
    }
}