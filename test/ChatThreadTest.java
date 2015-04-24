import com.lisa.ChatSubject;
import com.lisa.ChatThread;
import com.lisa.MyReader;
import com.lisa.MyWriter;
import org.junit.Before;
import org.junit.Test;

import java.awt.print.PrinterGraphics;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

public class ChatThreadTest {
    private ChatThread chatThread;
    private StringWriter writer;
    private StringReader reader;

    @Before
    public void setUp() throws Exception {
        ChatSubject chatSubject = new ChatSubject();
        writer = new StringWriter();
        reader = new StringReader("username\nmessage string");
        PrintWriter out = new PrintWriter(writer, true);
        BufferedReader in = new BufferedReader(reader);
        chatThread = new ChatThread(out, in, chatSubject);
    }

    @Test
    public void testClientInput() throws Exception {
        chatThread.run();
        assertEquals("message", writer.toString());

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
        public String readLine() {
            return "readLine string";
        }

        public void close() {
            closed = true;
        }
    }
}
