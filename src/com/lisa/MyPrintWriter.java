package com.lisa;

import java.io.OutputStream;
import java.io.PrintWriter;


public class MyPrintWriter implements MyWriter {
    private PrintWriter out;

    public MyPrintWriter(OutputStream outputStream) {
        out = new PrintWriter(outputStream, true);
    }
    public void println(String message) {
        out.println(message);
    }

    public void close() {
        out.close();
    }

    public void flush() {
        out.flush();
    }
}
