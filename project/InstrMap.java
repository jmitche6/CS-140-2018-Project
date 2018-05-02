package project;

import java.util.Map;
import java.util.TreeMap;

public class InstrMap {

    public static Map<String, Integer> toCode = new TreeMap<>();
    public static Map<Integer, String> toMnemonic = new TreeMap<>();

    static {
        toCode.put("NOP", 0);
        toCode.put("LODI", 1);
        toCode.put("LOD", 2);
        toCode.put("LODN", 3);
        toCode.put("STO", 4);
        toCode.put("STON", 5);
        toCode.put("JMPR", 6);
        toCode.put("JUMP",7);
        toCode.put("JUMPI",8);
        toCode.put("JMPZR", 9);
        toCode.put("JMPZ", 0xA);
        toCode.put("JMPZI", 0xB);
        toCode.put("ADDI", 0xC);
        toCode.put("ADD", 0xD);
        toCode.put("ADDN", 0xE);
        toCode.put("SUBI", 0xF);
        toCode.put("SUB", 0x10);
        toCode.put("SUBN", 0x11);
        toCode.put("MULI", 0x12);
        toCode.put("MUL", 0x13);
        toCode.put("MULN", 0x14);
        toCode.put("DIVI", 0x15);
        toCode.put("DIV", 0x16);
        toCode.put("DIVN", 0x17);
        toCode.put("ANDI", 0x18);
        toCode.put("AND", 0x19);
        toCode.put("NOT", 0x1A);
        toCode.put("CMPL", 0x1B);  // can use decimals instead of hex: 0x1B = 27, 0X1C = 28, etc.
        toCode.put("CMPZ", 0x1C);
        toCode.put("JUMPN", 0x1D);
        toCode.put("HALT", 0x1F);

        for(String s : toCode.keySet()) {
            toMnemonic.put(toCode.get(s), s);
        }
    }
}
