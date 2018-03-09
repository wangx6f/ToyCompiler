package edu.cpp.cs411.scanner;

import java.io.IOException;

public class Scanner {

    public static boolean scan(Input input,Output output) throws IOException {
        State state = State.INITIAL_STATE;
        while(state!=State.REJECT_STATE && state!=State.TERMINATE_STATE){
            state = state.next(input,output);
        }
        return state == State.TERMINATE_STATE ? true : false;
    }
}
