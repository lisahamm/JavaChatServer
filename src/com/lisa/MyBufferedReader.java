package com.lisa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class MyBufferedReader implements MyReader {
    private BufferedReader in;

    public MyBufferedReader(Reader input) {
        in = new BufferedReader(input);
    }

    public String readLine() {
        String line = null;
        try {
            line = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public void close() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
