package edu.cpp.cs411.scanner;

import java.io.IOException;

public interface Input {

    // move the reading pointer to next character
    void next() throws IOException;

    // get the character that the reading pointer currently pointing to
    // null if reach EOF
    Character getCurrent();
}
