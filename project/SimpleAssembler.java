package project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleAssembler implements Assembler {
    private boolean readingCode = true;

    private String makeOutputCode(String[] parts) {
        if (parts.length == 1) {
            return InstrMap.toCode.get(parts[0]) + "\n" + 0;
        }
        return InstrMap.toCode.get(parts[0]) + "\n" + Integer.parseInt(parts[1], 16);
    }


    private String makeOutputData(String[] parts) {
        return Integer.parseInt(parts[0], 16) + "\n" + Integer.parseInt(parts[1], 16);

    }

    @Override
    public int assemble(String inputFileName, String outputFileName, StringBuilder error) {
        Map<Boolean, List<String>> lists = null;
        try (Stream<String> lines = Files.lines(Paths.get(inputFileName))) {
            lists = lines
                    .filter(line -> line.trim().length() > 0) // << CORRECTION <<
                    .map(line -> line.trim())
                    .peek(line -> {
                        if (line.toUpperCase().equals("DATA")) readingCode = false;
                    })
                    .map(line -> line.trim())
                    .collect(Collectors.partitioningBy(line -> readingCode));
			//System.out.println("true List " + lists.get(true));
			//System.out.println("false List " + lists.get(false)); 
        } catch (IOException e) {
            e.printStackTrace();
        }

        lists.get(false).remove("DATA");
        List<String> outputCode = lists.get(true).stream()
                .map(line -> line.split("\\s+"))
                .map(this::makeOutputCode) // note how we use an instance method in the same class
                .collect(Collectors.toList());
        List<String> outputData = lists.get(false).stream()
                .map(line -> line.split("\\s+"))
                .map(this::makeOutputData)
                .collect(Collectors.toList());
        try (PrintWriter output = new PrintWriter(outputFileName)) {
            for (String s : outputCode) output.println(s);
            output.println(-1); // signal for the "DATA" separating code and data
            output.println(0); // filler for the 2-line pattern
            for (String s : outputData) output.println(s);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }
    public static void main(String[] args) {
        StringBuilder error = new StringBuilder();
        System.out.println("Enter the name of the file without extension: ");
        try (Scanner keyboard = new Scanner(System.in)) {
            String filename = keyboard.nextLine();
            int i = new SimpleAssembler().assemble(filename + ".pasm",
                    filename + ".pexe", error);
            System.out.println("result = " + i);
        }
    }
}
