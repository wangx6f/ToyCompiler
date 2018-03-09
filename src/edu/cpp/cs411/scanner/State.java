package edu.cpp.cs411.scanner;

import java.io.IOException;

/**
 * These are all the states of a finite automata to scan language toy into list of tokens
 */
public enum State {

    INITIAL_STATE,

    KEYWORD_ID_STATE,

    ZERO_STATE,
    HEX_SYMBOL_STATE,
    HEX_VALUE_STATE,
    INT_STATE,
    DOUBLE_STATE,
    EXPONENT_STATE,
    EXPONENT_SIGN_STATE,
    EXPONENT_VALUE_STATE,

    STRING_STATE,

    SPECIAL_SYMBOL_STATE,
    LESS_SYMBOL_STATE,
    GREATER_SYMBOL_STATE,
    EQUAL_SYMBOL_STATE,
    SLASH_SYMBOL_STATE,
    AND_SYMBOL_STATE,
    NOT_SYMBOL_STATE,
    OR_SYMBOL_STATE,

    SINGLE_LINE_COMMENT_STATE,
    MULTI_LINE_COMMENT_STATE,
    MULTI_LINE_COMMENT_END_STATE,

    REJECT_STATE,
    TERMINATE_STATE,

    WHITE_SPACE_STATE

    ;

    private interface TransitionFunction{
        State next(Input input,Output output) throws IOException;
    }


    private TransitionFunction transitionFunction;


    public State next(Input input,Output output) throws IOException {
        return transitionFunction.next(input,output);
    }

    static {
        INITIAL_STATE.transitionFunction = ((input, output) -> {
            Character curChar = input.getCurrent();
            if(curChar==null){
                return TERMINATE_STATE;
            } else if(isLetter(curChar)){
                consumeChar(curChar,input,output);
                return KEYWORD_ID_STATE;
            } else if(curChar=='0') {
                consumeChar(curChar,input,output);
                return ZERO_STATE;
            } else if(curChar>='1'&&curChar<='9') {
                consumeChar(curChar,input,output);
                return INT_STATE;
            } else if(curChar=='"') {
                consumeChar(curChar,input,output);
                return STRING_STATE;
            } else if(isWhiteSpace(curChar)){
                return WHITE_SPACE_STATE;
            } else {
                return SPECIAL_SYMBOL_STATE;
            }
        });

        KEYWORD_ID_STATE.transitionFunction = (input, output) -> {
            Character curChar = input.getCurrent();
            if(curChar!=null && (isLetter(curChar)||isDigit(curChar)||curChar=='_')){
                consumeChar(curChar,input,output);
                return KEYWORD_ID_STATE;
            } else {
                output.generateToken(output.consumed());
                return INITIAL_STATE;
            }
        };

        ZERO_STATE.transitionFunction = (input, output) -> {
            Character curChar = input.getCurrent();
            if(curChar==null){
                output.generateToken(TokenType.INTCONSTANT,output.consumed());
                return INITIAL_STATE;
            }
            if(curChar=='x'||curChar=='X'){
                consumeChar(curChar,input,output);
                return HEX_SYMBOL_STATE;
            } else if (isDigit(curChar) || curChar=='.' || curChar=='e' || curChar=='E'){
                return INT_STATE;
            } else {
                output.generateToken(TokenType.INTCONSTANT,output.consumed());
                return INITIAL_STATE;
            }
        };

        HEX_SYMBOL_STATE.transitionFunction = (input, output) -> {
            Character curChar = input.getCurrent();
            if(curChar!=null && isHexDigit(curChar)){
                consumeChar(curChar,input,output);
                return HEX_VALUE_STATE;
            } else {
                return REJECT_STATE;
            }
        };

        HEX_VALUE_STATE.transitionFunction = (input, output) -> {
            Character curChar = input.getCurrent();
            if(curChar!=null && isHexDigit(curChar)){
                consumeChar(curChar,input,output);
                return HEX_VALUE_STATE;
            } else {
                output.generateToken(TokenType.INTCONSTANT,output.consumed());
                return INITIAL_STATE;
            }
        };

        INT_STATE.transitionFunction = (input, output) -> {
            Character curChar =input.getCurrent();
            if(curChar==null){
                output.generateToken(TokenType.INTCONSTANT,output.consumed());
                return INITIAL_STATE;
            } else if(isDigit(curChar)){
                consumeChar(curChar,input,output);
                return INT_STATE;
            } else if(curChar=='.'){
                consumeChar(curChar,input,output);
                return DOUBLE_STATE;
            } else {
                output.generateToken(TokenType.INTCONSTANT,output.consumed());
                return INITIAL_STATE;
            }
        };

        DOUBLE_STATE.transitionFunction = (input, output) ->{
            Character curChar =input.getCurrent();
            if(curChar==null){
                output.generateToken(TokenType.DOUBLECONSTANT,output.consumed());
                return INITIAL_STATE;
            } else if(curChar=='e'||curChar=='E'){
                consumeChar(curChar,input,output);
                return EXPONENT_STATE;
            } else if(isDigit(curChar)){
                consumeChar(curChar,input,output);
                return DOUBLE_STATE;
            } else {
                output.generateToken(TokenType.DOUBLECONSTANT,output.consumed());
                return INITIAL_STATE;
            }
        };

        EXPONENT_STATE.transitionFunction = (input, output) -> {
            Character curChar =input.getCurrent();
            if(curChar==null){
                return REJECT_STATE;
            } else if (isDigit(curChar)) {
                consumeChar(curChar,input,output);
                return EXPONENT_VALUE_STATE;
            } else if (curChar=='+' || curChar=='-') {
                consumeChar(curChar,input,output);
                return EXPONENT_SIGN_STATE;
            } else {
                return REJECT_STATE;
            }
        };

        EXPONENT_SIGN_STATE.transitionFunction = (input, output) -> {
            Character curChar =input.getCurrent();
            if(curChar!=null && isDigit(curChar)){
                consumeChar(curChar,input,output);
                return EXPONENT_VALUE_STATE;
            } else {
                return REJECT_STATE;
            }
        };

        EXPONENT_VALUE_STATE.transitionFunction = (input, output) -> {
            Character curChar =input.getCurrent();
            if(curChar!=null && isDigit(curChar)){
                consumeChar(curChar,input,output);
                return EXPONENT_VALUE_STATE;
            } else {
                output.generateToken(TokenType.DOUBLECONSTANT,output.consumed());
                return INITIAL_STATE;
            }
        };

        STRING_STATE.transitionFunction = (input, output) -> {
            Character curChar = input.getCurrent();
            if(curChar==null || isNewline(curChar)){
                return REJECT_STATE;
            } else if (curChar == '"'){
                consumeChar(curChar,input,output);
                output.generateToken(TokenType.STRINGCONSTANT,output.consumed());
                return INITIAL_STATE;
            } else {
                consumeChar(curChar,input,output);
                return STRING_STATE;
            }
        };

        SPECIAL_SYMBOL_STATE.transitionFunction = (input, output) -> {
            Character curChar = input.getCurrent();
            input.next();
            switch (curChar) {
                case '+':
                    output.generateToken(TokenType.PLUS,"+");
                    return INITIAL_STATE;
                case '-':
                    output.generateToken(TokenType.MINUS,"-");
                    return INITIAL_STATE;
                case '*':
                    output.generateToken(TokenType.MULTIPLICATION,"*");
                    return INITIAL_STATE;
                case '%':
                    output.generateToken(TokenType.MOD,"%");
                    return INITIAL_STATE;
                case ';':
                    output.generateToken(TokenType.SEMICOLON,";");
                    return INITIAL_STATE;
                case ',':
                    output.generateToken(TokenType.COMMA,",");
                    return INITIAL_STATE;
                case '.':
                    output.generateToken(TokenType.PERIOD,".");
                    return INITIAL_STATE;
                case '[':
                    output.generateToken(TokenType.LEFTBRACKET,"[");
                    return INITIAL_STATE;
                case ']':
                    output.generateToken(TokenType.RIGHTBRACKET,"]");
                    return INITIAL_STATE;
                case '(':
                    output.generateToken(TokenType.LEFTPAREN,"(");
                    return INITIAL_STATE;
                case ')':
                    output.generateToken(TokenType.RIGHTPAREN,")");
                    return INITIAL_STATE;
                case '{':
                    output.generateToken(TokenType.LEFTBRACE,"{");
                    return INITIAL_STATE;
                case '}':
                    output.generateToken(TokenType.RIGHTBRACE,"}");
                    return INITIAL_STATE;
                case '/':
                    return SLASH_SYMBOL_STATE;
                case '<':
                    return LESS_SYMBOL_STATE;
                case '>':
                    return GREATER_SYMBOL_STATE;
                case '=':
                    return EQUAL_SYMBOL_STATE;
                case '!':
                    return NOT_SYMBOL_STATE;
                case '&':
                    return AND_SYMBOL_STATE;
                case '|':
                    return OR_SYMBOL_STATE;
                default:
                    return REJECT_STATE;

            }
        };

        LESS_SYMBOL_STATE.transitionFunction = (input, output) -> {
            Character curChar = input.getCurrent();
            if (curChar!=null && curChar=='='){
                input.next();
                output.generateToken(TokenType.LESSEQUAL,"<=");
            } else {
                output.generateToken(TokenType.LESS,"<");
            }
            return INITIAL_STATE;
        };

        GREATER_SYMBOL_STATE.transitionFunction = (input, output) -> {
            Character curChar = input.getCurrent();
            if(curChar!=null && curChar=='='){
                input.next();
                output.generateToken(TokenType.GREATEREQUAL,">=");
            } else {
                output.generateToken(TokenType.GREATER,">");
            }
            return INITIAL_STATE;
        };

        NOT_SYMBOL_STATE.transitionFunction = (input, output) -> {
            Character curChar = input.getCurrent();
            if(curChar!=null && curChar=='='){
                input.next();
                output.generateToken(TokenType.NOTEQUAL,"!=");
            } else {
                output.generateToken(TokenType.NOT,"!");
            }
            return INITIAL_STATE;
        };

        AND_SYMBOL_STATE.transitionFunction = (input, output) -> {
            Character curChar = input.getCurrent();
            if(curChar!=null && curChar=='&'){
                input.next();
                output.generateToken(TokenType.AND,"&&");
                return INITIAL_STATE;
            } else {
                return REJECT_STATE;
            }
        };

        OR_SYMBOL_STATE.transitionFunction = (input, output) -> {
            Character curChar = input.getCurrent();
            if(curChar!=null && curChar=='|'){
                input.next();;
                output.generateToken(TokenType.OR,"||");
                return INITIAL_STATE;
            } else {
                return REJECT_STATE;
            }
        };

        EQUAL_SYMBOL_STATE.transitionFunction = (input, output) -> {
            Character curChar = input.getCurrent();
            if(curChar!=null && curChar=='='){
                input.next();
                output.generateToken(TokenType.EQUAL,"==");
            } else {
                output.generateToken(TokenType.ASSIGNOP,"=");
            }
            return INITIAL_STATE;
        };

        SLASH_SYMBOL_STATE.transitionFunction = (input, output) -> {
            Character curChar = input.getCurrent();
            if(curChar==null || curChar!='*'&& curChar!='/'){
                output.generateToken(TokenType.DIVISION,"/");
                return INITIAL_STATE;
            } else {
                return CommentStateHelper(input, curChar, MULTI_LINE_COMMENT_STATE, SINGLE_LINE_COMMENT_STATE);
            }
        };

        MULTI_LINE_COMMENT_STATE.transitionFunction = (input, output) -> {
            Character curChar = input.getCurrent();
            if(curChar==null){
                return REJECT_STATE;
            } else{
                return CommentStateHelper(input, curChar, MULTI_LINE_COMMENT_END_STATE, MULTI_LINE_COMMENT_STATE);
            }
        };

        MULTI_LINE_COMMENT_END_STATE.transitionFunction = (input, output) -> {
            Character curChar = input.getCurrent();
            if(curChar==null){
                return REJECT_STATE;
            } else if(curChar=='/'){
                input.next();
                return INITIAL_STATE;
            } else {
                input.next();
                return MULTI_LINE_COMMENT_STATE;
            }
        };

        SINGLE_LINE_COMMENT_STATE.transitionFunction = (input, output) -> {
            Character curChar = input.getCurrent();
            if(curChar==null || curChar=='\n'){
                return INITIAL_STATE;
            } else {
                input.next();
                return SINGLE_LINE_COMMENT_STATE;
            }
        };

        WHITE_SPACE_STATE.transitionFunction = (input,output) -> {
            Character curChar = input.getCurrent();
            if(curChar==null || !isWhiteSpace(curChar)) {
                return INITIAL_STATE;
            }
            if(output instanceof ConsoleOutput && isNewline(curChar)) {
                ((ConsoleOutput) output).newLine();
            }
            input.next();
            return WHITE_SPACE_STATE;

        };

        REJECT_STATE.transitionFunction = (input,output)->REJECT_STATE;

        TERMINATE_STATE.transitionFunction = ((input, output) -> TERMINATE_STATE);
    }



   static private boolean isLetter(Character c){
        return c>='a'&&c<='z' || c>='A'&& c<='Z';
    }

    static private boolean isDigit(Character c){
        return c>='0'&&c<='9';
    }

    static private boolean isHexDigit(Character c) {
        return c>='0'&&c<='9' || c>='a'&&c<='f' || c>='A'&&c<='F';
    }

    static private void consumeChar(Character c,Input input,Output output) throws IOException{
        output.consume(c);
        input.next();
    }

    static private boolean isWhiteSpace(Character c) {
        return c==' ' || c=='\n' || c=='\t' || c=='\r';
    }

    static private boolean isNewline(Character c) {
        return c=='\n' || c=='\r';
    }


    private static State CommentStateHelper(Input input, Character curChar, State starState, State otherState) throws IOException {
        if (curChar=='*'){
            input.next();
            return starState;
        } else {
            input.next();
            return otherState;
        }
    }

}


