package edu.cpp.cs411.symboltable;

public class CharSymbol implements TrieSymbol {

    private final char c;

    public CharSymbol(char c){
        this.c = c;
    }

    public char getChar() {
        return c;
    }

    @Override
    public String toString(){
        return String.valueOf(c);
    }
}
