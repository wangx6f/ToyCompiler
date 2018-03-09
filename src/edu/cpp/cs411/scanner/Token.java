package edu.cpp.cs411.scanner;

public class Token {

    private final TokenType type;

    private final int STARTING_INT = 1000;

    private final String value;

    public Token(TokenType type,String value){
        this.type =type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public int getTokenCode() {
        return type.ordinal() + STARTING_INT;
    }

    @Override
    public String toString() {
        return type.toString()+"["+getTokenCode()+"]:"+value;
    }
}
