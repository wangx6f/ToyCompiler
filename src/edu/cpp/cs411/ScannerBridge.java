package edu.cpp.cs411;

import edu.cpp.cs411.parser.ParserSym;
import edu.cpp.cs411.scanner.Output;
import edu.cpp.cs411.scanner.Token;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;
import java_cup.runtime.SymbolFactory;

import java.util.List;

public class ScannerBridge implements Scanner {

    private List<Token> result;

    private int count;

    private SymbolFactory sf;

    public ScannerBridge(Output output) {
        result = output.getResult();
        sf = new ComplexSymbolFactory();
        count = 0;
    }

    @Override
    public Symbol next_token() {
        if (count == result.size()) {
            return sf.newSymbol("EOF",ParserSym.EOF);
        }
        if(count!=0) {
            System.out.println("[shift]");
        }
        Token cur = result.get(count++);
        System.out.print(cur.getType()+" ");
        switch (cur.getType()) {
            case INTCONSTANT:
                return sf.newSymbol("INTCONSTANT",ParserSym.INTCONSTANT);
            case DOUBLECONSTANT:
                return sf.newSymbol("DOUBLECONSTANT",ParserSym.DOUBLECONSTANT);
            case BOOLEANCONSTANT:
                return sf.newSymbol("BOOLEANCONSTANT",ParserSym.BOOLEANCONSTANT);
            case ID:
                return sf.newSymbol("ID",ParserSym.ID);
            case IF:
                return sf.newSymbol("IF",ParserSym.IF);
            case OR:
                return sf.newSymbol("OR",ParserSym.OR);
            case AND:
                return sf.newSymbol("AND",ParserSym.AND);
            case FOR:
                return sf.newSymbol("FOR",ParserSym.FOR);
            case INT:
                return sf.newSymbol("INT",ParserSym.INT);
            case MOD:
                return sf.newSymbol("MOD",ParserSym.MOD);
            case NOT:
                return sf.newSymbol("NOT",ParserSym.NOT);
            case ELSE:
                return sf.newSymbol("ELSE",ParserSym.ELSE);
            case LESS:
                return sf.newSymbol("LESS",ParserSym.LESS);
            case PLUS:
                return sf.newSymbol("PLUS",ParserSym.PLUS);
            case VOID:
                return sf.newSymbol("VOID",ParserSym.VOID);
            case BREAK:
                return sf.newSymbol("BREAK",ParserSym.BREAK);
            case CLASS:
                return sf.newSymbol("CLASS",ParserSym.CLASS);
            case COMMA:
                return sf.newSymbol("COMMA",ParserSym.COMMA);
            case EQUAL:
                return sf.newSymbol("EQUAL",ParserSym.EQUAL);
            case MINUS:
                return sf.newSymbol("MINUS",ParserSym.MINUS);
            case WHILE:
                return sf.newSymbol("WHILE",ParserSym.WHILE);
            case DOUBLE:
                return sf.newSymbol("DOUBLE",ParserSym.DOUBLE);
            case PERIOD:
                return sf.newSymbol("PERIOD",ParserSym.PERIOD);
            case READLN:
                return sf.newSymbol("READLN",ParserSym.READLN);
            case RETURN:
                return sf.newSymbol("RETURN",ParserSym.RETURN);
            case STRING:
                return sf.newSymbol("STRING",ParserSym.STRING);
            case BOOLEAN:
                return sf.newSymbol("BOOLEAN",ParserSym.BOOLEAN);
            case EXTENDS:
                return sf.newSymbol("EXTENDS",ParserSym.EXTENDS);
            case GREATER:
                return sf.newSymbol("GREATER",ParserSym.GREATER);
            case PRINTLN:
                return sf.newSymbol("PRINTLN",ParserSym.PRINTLN);
            case ASSIGNOP:
                return sf.newSymbol("ASSIGNOP",ParserSym.ASSIGNOP);
            case DIVISION:
                return sf.newSymbol("DIVISION",ParserSym.DIVISION);
            case NEWARRAY:
                return sf.newSymbol("NEWARRAY",ParserSym.NEWARRAY);
            case NOTEQUAL:
                return sf.newSymbol("NOTEQUAL",ParserSym.NOTEQUAL);
            case INTERFACE:
                return sf.newSymbol("INTERFACE",ParserSym.INTERFACE);
            case LEFTBRACE:
                return sf.newSymbol("LEFTBRACE",ParserSym.LEFTBRACE);
            case LEFTPAREN:
                return sf.newSymbol("LEFTPAREN",ParserSym.LEFTPAREN);
            case LESSEQUAL:
                return sf.newSymbol("LESSEQUAL",ParserSym.LESSEQUAL);
            case SEMICOLON:
                return sf.newSymbol("SEMICOLON",ParserSym.SEMICOLON);
            case IMPLEMENTS:
                return sf.newSymbol("IMPLEMENTS",ParserSym.IMPLEMENTS);
            case RIGHTBRACE:
                return sf.newSymbol("RIGHTBRACE",ParserSym.RIGHTBRACE);
            case RIGHTPAREN:
                return sf.newSymbol("RIGHTPAREN",ParserSym.RIGHTPAREN);
            case LEFTBRACKET:
                return sf.newSymbol("LEFTBRACKET",ParserSym.LEFTBRACKET);
            case GREATEREQUAL:
                return sf.newSymbol("GREATEREQUAL",ParserSym.GREATEREQUAL);
            case RIGHTBRACKET:
                return sf.newSymbol("RIGHTBRACKET",ParserSym.RIGHTBRACKET);
            case MULTIPLICATION:
                return sf.newSymbol("MULTIPLICATION",ParserSym.MULTIPLICATION);
            case STRINGCONSTANT:
                return sf.newSymbol("STRINGCONSTANT",ParserSym.STRINGCONSTANT);
            default:
                return sf.newSymbol("error",ParserSym.error);
        }
    }
}
