package edu.cpp.cs411.symboltable;

import edu.cpp.cs411.scanner.Keyword;

public class KeywordDelimiter implements Delimiter {

    private final Keyword keyword;

    public KeywordDelimiter(Keyword keyword){
        this.keyword = keyword;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    @Override
    public String toString() {
        return "*";
    }
}
