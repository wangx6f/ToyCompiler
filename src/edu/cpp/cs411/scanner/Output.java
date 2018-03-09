package edu.cpp.cs411.scanner;

import edu.cpp.cs411.symboltable.SymbolTable;

import java.util.List;

public interface Output {

    // generate the token with known type, the consumed buffer will be flushed
    void generateToken(TokenType tokenType,String value);

    // generate the token that is either an identifier or a keyword, which can be checked
    // from symbol table
    void generateToken(String value);

    // consume a character to the buffer
    void consume(Character character);

    // retrieve current content in the buffer
    String consumed();

    SymbolTable getSymbolTable();

    public List<Token> getResult();
}
