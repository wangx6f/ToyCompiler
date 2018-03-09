package edu.cpp.cs411.scanner;

import edu.cpp.cs411.symboltable.Delimiter;
import edu.cpp.cs411.symboltable.KeywordDelimiter;
import edu.cpp.cs411.symboltable.SymbolTable;
import sun.awt.Symbol;

import java.util.ArrayList;
import java.util.List;

public class ConsoleOutput implements Output {

    private final SymbolTable symbolTable;

    private  StringBuilder tempBuilder;

    private final List<Token> result;


    private final StringBuilder printStringBuilder;

    public ConsoleOutput(){
        symbolTable = new SymbolTable();
        tempBuilder = new StringBuilder();
        printStringBuilder = new StringBuilder();
        result = new ArrayList<>();
    }


    @Override
    public void generateToken(TokenType tokenType, String value) {
        Token token = new Token(tokenType,value);
        result.add(token);
        tempBuilder = new StringBuilder();
        addTokenToPrint(token);
    }

    @Override
    public void generateToken(String value) {
        Token token;
        Delimiter delimiter = symbolTable.put(value);
        if(delimiter instanceof KeywordDelimiter){
            token = new Token(((KeywordDelimiter) delimiter).getKeyword().getType(),value);
        } else {
            token = new Token(TokenType.ID,value);
        }
        result.add(token);
        tempBuilder = new StringBuilder();
        addTokenToPrint(token);
    }

    @Override
    public void consume(Character character) {
        tempBuilder.append(character);
    }

    @Override
    public String consumed() {
        return tempBuilder.toString();
    }

    @Override
    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void newLine(){
        if (printStringBuilder.length()!=0 && printStringBuilder.charAt(printStringBuilder.length()-1) != '\n') {
            printStringBuilder.append("\n");
        }

    }

    @Override
    public List<Token> getResult() {
        return result;
    }

    public void consolePrint(){
        System.out.println("\nResult of the scanner:");
        System.out.println(printStringBuilder.toString());
    }

    private void addTokenToPrint(Token token) {
        printStringBuilder.append(token.getType());
        printStringBuilder.append(" ");
    }
}
