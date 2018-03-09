package edu.cpp.cs411.symboltable;

import edu.cpp.cs411.scanner.Keyword;

public class SymbolTable {

    private Trie trie;

    public SymbolTable(){
        trie = new Trie();
        addKeyword();
    }

    public Delimiter put(String id){
        return trie.put(id,new IdDelimiter());
    }

    private void addKeyword(){
        for(Keyword keyword : Keyword.values()){
            trie.put(keyword.getLiteral(),new KeywordDelimiter(keyword));
        }
    }


    public String prettyPrint() {
        return "Symbol Table:\n"+trie.prettyPrint()+"[Note] id delimiter: @ | keyword delimiter: *\n";
    }
}
