package org.project06;

import java.util.HashMap;

public class SymbolTable {

    private static final HashMap<String, Integer> predefinedValues = new HashMap<>();

    static {
        predefinedValues.put("SP", 0);
        predefinedValues.put("LCL", 1);
        predefinedValues.put("ARG", 2);
        predefinedValues.put("THIS", 3);
        predefinedValues.put("THAT", 4);
        predefinedValues.put("R0", 0);
        predefinedValues.put("R1", 1);
        predefinedValues.put("R2", 2);
        predefinedValues.put("R3", 3);
        predefinedValues.put("R4", 4);
        predefinedValues.put("R5", 5);
        predefinedValues.put("R6", 6);
        predefinedValues.put("R7", 7);
        predefinedValues.put("R8", 8);
        predefinedValues.put("R9", 9);
        predefinedValues.put("R10", 10);
        predefinedValues.put("R11", 11);
        predefinedValues.put("R12", 12);
        predefinedValues.put("R13", 13);
        predefinedValues.put("R14", 14);
        predefinedValues.put("R15", 15);
        predefinedValues.put("SCREEN", 16384);
        predefinedValues.put("KBD", 24576);
    }

    private HashMap<String, Integer> table;

    public SymbolTable() {
        table = new HashMap<>(predefinedValues);
    }

    public void addEntry(String symbol, Integer value) {
        table.put(symbol, value);
    }

    public boolean contains(String symbol) {
        return table.containsKey(symbol);
    }

    public boolean notContains(String symbol) {
        return !table.containsKey(symbol);
    }

    public Integer getAddress(String symbol) {
        return table.get(symbol);
    }

}
