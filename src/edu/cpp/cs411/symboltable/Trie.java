package edu.cpp.cs411.symboltable;
import java.util.Arrays;
import java.util.Formatter;


public class Trie {

    private final int[] switches;

    private TrieSymbol[] symbol;

    private int[] next;

    private final int INITIAL_SIZE = 5000;

    private int count = 0;

    public Trie(){
        switches = new int[52];
        Arrays.fill(switches,-1);
        symbol = new TrieSymbol[INITIAL_SIZE];
        Arrays.fill(symbol,null);
        next = new int[INITIAL_SIZE];
        Arrays.fill(next,-1);
    }

    public Delimiter put(String s,Delimiter delimiter) {

        int sIndex = 0;
        int switchesIndex = getSwitchesIndex(s.charAt(sIndex++));
        int ptr = switches[switchesIndex];
        if( ptr == -1) {
            switches[switchesIndex] = count;
            ptr = count;
        }

        while(sIndex<s.length()){
            TrieSymbol ptrSymbol = symbol[ptr];
            if(ptrSymbol==null){
                symbol[ptr++] = new CharSymbol(s.charAt(sIndex++));
                increaseCount();
            } else if (!(ptrSymbol instanceof CharSymbol) || ((CharSymbol) ptrSymbol).getChar() != s.charAt(sIndex)) {
                ptr = getNextAvailable(ptr);
            } else {
                ptr++;
                sIndex++;
            }
        }

        while (!(symbol[ptr] instanceof Delimiter)) {
            if(symbol[ptr] == null){
                symbol[ptr] = delimiter;
                increaseCount();
            } else {
                ptr = getNextAvailable(ptr);
            }
        }

        return (Delimiter) symbol[ptr];
    }

    public String prettyPrint() {
        return switchesToString().concat(symbolToString());
    }

    private String switchesToString() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        final int col = 10;
        for(int i=0;i*col<switches.length;i++){
            sb.append("=======\t");
            for(int j = 0;j<col && i*col+j<switches.length;j++){
                formatter.format("%8c",switchesIndexToChar(i*col+j));
            }
            sb.append("\nswitch:\t");
            for(int j = 0;j<col && i*col+j<switches.length;j++){
                if(switches[i*col+j]!=-1){
                    formatter.format("%8d",switches[i*col+j]);
                } else {
                    formatter.format("%8c",' ');
                }
            }
            sb.append("\n\n");
        }
        return sb.toString();
    }

    private String symbolToString() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        final int col = 10;
        for(int i=0;i*col<count;i++){
            sb.append("=======\t");
            for(int j = 0;j<col && i*col+j<count;j++){
                formatter.format("%8d",i*col+j);
            }
            sb.append("\nsymbol:\t");
            for(int j = 0;j<col && i*col+j<count;j++){
                formatter.format("%8s",symbol[i*col+j]);
            }
            sb.append("\nnext:\t");
            for(int j = 0;j<col && i*col+j<count;j++){
                if(next[i*col+j]!=-1){
                    formatter.format("%8d",next[i*col+j]);
                } else {
                    formatter.format("%8c",' ');
                }
            }
            sb.append("\n\n");
        }
        return sb.toString();
    }



    private int getSwitchesIndex(char c){
        if (c>='a' && c<='z'){
           return c-'a';
        } else if (c>='A'&&c<='Z'){
            return c-'A'+26;
        } else {
            return -1;
        }
    }

    private char switchesIndexToChar(int index){
        if (index>=0 && index<=25){
            return (char) (index + 'a');
        } else {
            return (char) (index - 26 + 'A');
        }
    }

    private void increaseCount() {
        count++;
        if(count == symbol.length){
            int newSize = symbol.length * 2;
            TrieSymbol[] newSymbol = new TrieSymbol[newSize];
            Arrays.fill(newSymbol,null);
            int[] newNext = new int[newSize];
            Arrays.fill(newNext,-1);
            for (int i=0;i<symbol.length;i++){
                newSymbol[i] = symbol[i];
                newNext[i] = next[i];
            }
            symbol = newSymbol;
            next = newNext;
        }
    }




    private int getNextAvailable(int ptr){
        if(next[ptr] ==-1){
            next[ptr] = count;
        }
        return next[ptr];
    }



}
