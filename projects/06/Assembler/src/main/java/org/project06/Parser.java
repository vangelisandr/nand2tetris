package org.project06;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class Parser {
    private Scanner sc;

    public Parser(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        sc = new Scanner(file);
    }

    public boolean hasMoreCommands() {
        return sc.hasNextLine();
    }

    public String advance() {
        String command = sc.nextLine().trim();
        // Ignore comments and whitespace
        while (command.isEmpty() || command.startsWith("//")) {
            command = sc.nextLine().trim();
        }
        // Check for in-line comments
        if (command.contains("/")) {
            command = command.substring(0, command.indexOf('/')).trim();
        }
        return command;
    }

    public String identifyCommandType(String command) {
        switch (command.charAt(0)) {
            case '@':
                return "A_COMMAND";
            case '(':
                return "L_COMMAND";
            default:
                return "C_COMMAND";
        }
    }

    public String retrieveSymbolOfCommand(String commandType, String command) {
        return commandType.equals("A_COMMAND") ? command.substring(1) : command.substring(1, command.length() - 1);
    }

    public String retrieveDestFromCommand(String command) {
        return command.contains("=") ? command.substring(0, command.indexOf("=")) : null;
    }

    public String retrieveJumpFromCommand(String command) {
        return command.contains(";") ? command.substring(command.indexOf(";") + 1) : null;
    }

    public String retrieveCompFromCommand(String command) {
        int startIndexOfComp = command.contains("=") ? command.indexOf("=") + 1 : 0;
        int endIndexOfComp = command.contains(";") ? command.indexOf(";") : command.length();
        return command.substring(startIndexOfComp, endIndexOfComp);
    }

    public String convertToBinary(String input) {
        String result = null;
        try {
            int number = Integer.parseInt(input.trim());
            result = Integer.toBinaryString(number);
        } catch (NumberFormatException e) {
            System.err.println("Invalid Symbol Format: " + input);
        }
        return result;
    }

    public String convertToBinary(int input) {
       return Integer.toBinaryString(input);
    }

    public void close() {
        if (sc != null) {
            sc.close();
        }
    }

}