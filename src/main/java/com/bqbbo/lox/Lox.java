package com.bqbbo.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {

    static boolean hadError = false;

    // Determines whether to run from a file or use REPL; extra args return code 64
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            REPL();
        }
    }

    // Load file source and run
    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));

        if (hadError) {
            System.exit(65);
        }
    }

    // Launch REPL loop
    private static void REPL() throws IOException {
        InputStreamReader REPLInputReader = new InputStreamReader(System.in);
        BufferedReader REPLBufferReader = new BufferedReader(REPLInputReader);

        System.out.println("Welcome to Lox!");
        System.out.println("Created by bqbbo with of Crafting Interpreters");
        System.out.println("Version: jlox-scanner [WIP]");
        System.out.println();

        while (true) {
            System.out.print("> ");
            String REPLInput = REPLBufferReader.readLine();

            if (REPLInput == null) {
                break;
            }

            run(REPLInput);
        }
    }

    // Execute/interpret supplied source code from REPL() and runFile()
    private static void run(String sourceCode) {
        Scanner scanner = new Scanner(sourceCode);
        List<Token> tokens = scanner.scanTokens();

        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    // Used by other classes to report errors in source
    static void error(int line, String errorMessage) {
        report(line, "", errorMessage);
    }

    // Reports errors supplied by error() with proper string formatting
    private static void report(int line, String where, String errorMessage) {
        System.out.println("[line" + line + " ] Error " + where + ": " + errorMessage);
        hadError = true;
    }
}