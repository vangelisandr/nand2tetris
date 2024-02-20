package org.project06;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Assembler {

    private static final String OUTPUT_FILE_EXTENSION = ".hack";
    private static final int STARTING_RAM_ADDRESS = 16;

    public static void main(String[] args) {
        // Check for correct usage
        if (args.length <= 0) {
            System.out.println("Usage: java Assembler filename.asm");
            System.exit(1);
        }
        String fileName = args[0];
        String fileNameNoExt = getFilenameWithoutExtension(fileName);

        try (FileWriter writer = new FileWriter(fileNameNoExt + OUTPUT_FILE_EXTENSION);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            // First pass write labels to hash table
            SymbolTable symbolTable = constructSymbolTable(fileName);

            // Second pass handle symbols and write to output file
            secondPassCommandOutput(fileName, symbolTable, bufferedWriter);
        } catch (IOException e) {
            System.out.println("Assembler error with message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // First pass construct symbol table without writing code
    private static SymbolTable constructSymbolTable(String fileName) throws FileNotFoundException {
        SymbolTable symbolTable = new SymbolTable();
        Parser parser = new Parser(fileName);
        int instructionCounter = 0;
        while (parser.hasMoreCommands()) {
            String command = parser.advance();
            if ("L_COMMAND".equals(parser.identifyCommandType(command))) {
                symbolTable.addEntry(parser.retrieveSymbolOfCommand("L_COMMAND", command), instructionCounter);
            } else {
                instructionCounter++;
            }
        }
        parser.close();
        return symbolTable;
    }

    // Second pass, handle commands and write to output file
    private static void secondPassCommandOutput(String fileName, SymbolTable symbolTable, BufferedWriter bufferedWriter) throws IOException {
        Parser parser = new Parser(fileName);
        Code code = new Code();
        int nextAvailableRamAddressForVar = STARTING_RAM_ADDRESS;

        while (parser.hasMoreCommands()) {
            String result;
            String command = parser.advance();
            String commandType = parser.identifyCommandType(command);

            switch (commandType) {
                case "A_COMMAND":
                    String symbol = parser.retrieveSymbolOfCommand(commandType, command);
                    if (isNumeric(symbol)) {
                        result = convertTo16Bit('0', parser.convertToBinary(symbol));
                        break;
                    }
                    if (symbolTable.notContains(symbol)) {
                        symbolTable.addEntry(symbol, nextAvailableRamAddressForVar);
                        nextAvailableRamAddressForVar++;
                    }
                    result = convertTo16Bit('0', parser.convertToBinary(symbolTable.getAddress(symbol)));
                    break;
                case "C_COMMAND":
                    String binDest = code.convertDestToBinary(parser.retrieveDestFromCommand(command));
                    String binComp = code.convertCompToBinary(parser.retrieveCompFromCommand(command));
                    String binJump = code.convertJumpToBinary(parser.retrieveJumpFromCommand(command));
                    result = convertTo16Bit('1', binComp + binDest + binJump);
                    break;
                default:
                    continue;
            }
            bufferedWriter.write(result);
            bufferedWriter.newLine();
        }
        parser.close();
    }

    private static String getFilenameWithoutExtension(String fileName) {
        int index = 0;
        while (fileName.charAt(index) != '.') {
            index++;
        }
        return fileName.substring(0, index);
    }

    private static String convertTo16Bit(char propendBit, String binary) {
        while (binary.length() < 16) {
            binary = propendBit + binary;
        }
        return binary;
    }

    private static boolean isNumeric(String symbol) {
        return Character.isDigit(symbol.charAt(0));
    }
}