package project;


import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public interface Assembler {
    Set<String> noArgument = new TreeSet<>(Arrays.asList("HALT", "NOP", "NOT"));

    int assemble(String inputFileName, String outputFileName, StringBuilder error);
}
