package edu.cpp.cs411.scanner;

import edu.cpp.cs411.symboltable.Delimiter;
import edu.cpp.cs411.symboltable.KeywordDelimiter;
import edu.cpp.cs411.symboltable.SymbolTable;
import java.util.ArrayList;
import java.util.List;

public class ConsoleOutput implements Output {

    private final SymbolTable symbolTable;

    private  StringBuilder stringBuilder;

    private final List<Token> result;

    public ConsoleOutput(){
        symbolTable = new SymbolTable();
        stringBuilder = new StringBuilder();
        result = new ArrayList<>();
    }


    @Override
    public void generateToken(TokenType tokenType, String value) {
        Token token = new Token(tokenType,value);
        result.add(token);
        stringBuilder = new StringBuilder();
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
        stringBuilder = new StringBuilder();
    }

    @Override
    public void consume(Character character) {
        stringBuilder.append(character);
    }

    @Override
    public String consumed() {
        return stringBuilder.toString();
    }

    @Override
    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void newLine(){
        result.add(null);
    }

    @Override
    public List<Token> getResult() {
        return result;
    }

    public void consolePrint(){
        System.out.println("Result of the scanner:");
        for(int i=0;i<result.size();i++){
            Token token = result.get(i);
            if(token==null && i!=0 && result.get(i-1)!=null){
                System.out.println();
                continue;
            } else if (token==null){
                continue;
            }
            System.out.print(token.getType());
            System.out.print(" ");
        }

    }
}
