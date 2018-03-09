package edu.cpp.cs411.scanner;

public enum Keyword {

    BOOLEAN(TokenType.BOOLEAN),
    BREAK(TokenType.BREAK),
    CLASS(TokenType.CLASS),
    DOUBLE(TokenType.DOUBLE),
    ELSE(TokenType.ELSE),
    EXTENDS(TokenType.EXTENDS),
    FALSE(TokenType.BOOLEANCONSTANT),
    TRUE(TokenType.BOOLEANCONSTANT),
    FOR(TokenType.FOR),
    IF(TokenType.IF),
    IMPLEMENTS(TokenType.IMPLEMENTS),
    INT(TokenType.INT),
    INTERFACE(TokenType.INTERFACE),
    NEWARRAY(TokenType.NEWARRAY),
    PRINTLN(TokenType.PRINTLN),
    READLN(TokenType.READLN),
    RETURN(TokenType.RETURN),
    STRING(TokenType.STRING),
    VOID(TokenType.VOID),
    WHILE(TokenType.WHILE);



    private final TokenType type;


    Keyword(TokenType type) {
        this.type = type;
    }

    public TokenType getType() {
        return type;
    }

    public String getLiteral() {
        return name().toLowerCase();
    }

}
