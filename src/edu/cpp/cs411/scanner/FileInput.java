package edu.cpp.cs411.scanner;

import java.io.*;

public class FileInput implements Input {

    private final BufferedReader bufferedReader;

    private Character current;

    public FileInput(String fileName) throws IOException {
        this.bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        next();
    }

    @Override
    public void next() throws IOException {
        int nextCharAsInt = bufferedReader.read();
        if (nextCharAsInt == -1) {
            current = null;
        } else {
            current = new Character((char) nextCharAsInt);
        }
    }

    @Override
    public Character getCurrent() {
        return current;
    }
}
