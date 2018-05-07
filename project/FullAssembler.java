package project;

import java.util.Arrays;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FullAssembler implements Assembler {
	private boolean blankFound = false;
	private int retVal = 0;
	private int lineNum = 0;
	private int firstBlankLineNum = 0;
	private boolean inCode = true;

	@Override
	public int assemble(String inputFileName, String outputFileName, StringBuilder error) {
		if(error.equals(null)) {
			throw new IllegalArgumentException("Error Buffer cannot be Null");
		}
		File input = new File(inputFileName);
		File output = new File(outputFileName);
		ArrayList<String> codeInput = new ArrayList<>();
		ArrayList<String> dataInput = new ArrayList<>();
		boolean addressError = true;
		try {
			Scanner IN = new Scanner(input);
			while(IN.hasNextLine() && retVal == 0) {
				addressError = true;
				String line = IN.nextLine();
				String[] parts = line.trim().split("\\s+");
				lineNum++;
				if(line.trim().length() == 0 && !blankFound) {
					blankFound = true;
					firstBlankLineNum = lineNum;
				} if(blankFound && line.trim().length() != 0) {
					error.append("Illegal blank line found at " + lineNum);
					retVal = firstBlankLineNum;
				} if(!blankFound && (Character.toString(line.charAt(0)).equals(" ") || Character.toString(line.charAt(0)).equals("/t"))) {
					error.append("Line " + lineNum + " starts with illegal white space");
				} if (line.trim().toUpperCase().equals("DATA") && inCode) {
					inCode = false;
					if (!line.trim().equals("DATA")) {
						error.append("DATA must be uppercase at Line " + lineNum);
					}
				} if (line.trim().toUpperCase().equals("DATA") && !inCode) {
					error.append("Illegal DATA tag at Line " + lineNum);
					retVal = lineNum;
				} if (!InstrMap.toCode.keySet().contains(parts[0].toUpperCase())) {
					error.append("Illegal Mnemonic on Line " + lineNum);
					retVal = lineNum;
				} if (!InstrMap.toCode.keySet().contains(parts[0])) {
					error.append("Mnemonic must be Uppercase on Line " + lineNum);
					retVal = lineNum;
				} if (Assembler.noArgument.contains(parts[0]) && parts.length != 1) {
					error.append("Mnemonic on Line " + lineNum + " does not require an argument");
					retVal = lineNum;
				} if (!Assembler.noArgument.contains(parts[0]) && parts.length != 2) {
					error.append("Mnemonic on Line " + lineNum + " requires 1 argument");
					retVal = lineNum;
				} if (!inCode && parts.length != 2) {
					error.append("Data at Line " + lineNum + " must have address and value");
				} if (!inCode) {
					int address = Integer.parseInt(parts[0],16);
				}
				addressError = false;
				if(parts.length == 2) {
					int arg = Integer.parseInt(parts[1],16);	
				}
				if (inCode) {
					codeInput.add(line);	
				} else {
					dataInput.add(line);
				}
			} IN.close();
		} catch(NumberFormatException e) {
			if(!inCode && addressError) {
				error.append("\nError on line " + lineNum + 
						": address is not a hex number");
				retVal = lineNum;
			}
			error.append("\nError on line " + lineNum + 
					": argument is not a hex number");
			retVal = lineNum;	
		} catch (FileNotFoundException e) {
			error.append("\nError: Unable to write the assembled program to the output file");
			retVal = -1;
		} catch (IOException e) {
			error.append("\nUnexplained IO Exception");
			retVal = -1;
		} 
		if (retVal == 0) {
			new SimpleAssembler().assemble(inputFileName, outputFileName, error);
		}
		return retVal;
		
	} 
	
}
