package edu.cpp.cs411;

import edu.cpp.cs411.parser.Parser;
import edu.cpp.cs411.scanner.*;
import java_cup.runtime.ComplexSymbolFactory;

public class Main {


    public static void main(String[] args) throws Exception {
        run();
    }

    private static void run() throws Exception {
        ConsoleOutput output = new ConsoleOutput();
        boolean accepted = Scanner.scan(new FileInput(getFileName()),output);
        output.consolePrint();
        System.out.println("* * * * * * *");
        if (accepted){
            System.out.println("* accepted  *");
        } else {
            System.out.println("* rejected  *");
        }
        System.out.println("* * * * * * *");

        if (accepted) {
            Parser p = new Parser(new ScannerBridge(output),new ComplexSymbolFactory());
            p.parse();
        }
    }


    private static String getFileName(){
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.println("Enter the file name that is to be scanned:");
        return "in/"+scanner.nextLine();
    }

}
