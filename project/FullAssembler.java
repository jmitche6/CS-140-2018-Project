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
			while(IN.hasNextLine()) {
				addressError = true;
				String line = IN.nextLine();
				String[] parts = line.trim().split("\\s+");
				lineNum++;
				if(line.trim().length() == 0 && !blankFound) {
					blankFound = true;
					firstBlankLineNum = lineNum;
					continue;
				} if(line.trim().length() == 0 && blankFound) {
					continue;	
				} if(blankFound && line.trim().length() != 0) {
					error.append("Illegal blank line found at " + firstBlankLineNum + "\n");
					retVal = firstBlankLineNum;
					blankFound = false;
				} if((Character.toString(line.charAt(0)).equals(" ") || Character.toString(line.charAt(0)).equals("\t"))) {
					error.append("Line " + lineNum + " starts with illegal white space" + "\n");
				} if (line.trim().toUpperCase().equals("DATA") && inCode) {
					if (!line.trim().equals("DATA")) {
						error.append("DATA must be uppercase at Line " + lineNum + "\n");
						retVal = lineNum;
					}
					inCode = false;
					continue;
				} if (line.trim().toUpperCase().equals("DATA") && !inCode) {
					error.append("Illegal DATA tag at Line " + lineNum + "\n");
					retVal = lineNum;
				} if (!InstrMap.toCode.keySet().contains(parts[0].toUpperCase())&& inCode) {
					error.append("Illegal Mnemonic on Line " + lineNum + "\n");
					retVal = lineNum;
				} if (!InstrMap.toCode.keySet().contains(parts[0]) && inCode && InstrMap.toCode.keySet().contains(parts[0].toUpperCase())) {
					error.append("Mnemonic must be Uppercase on Line " + lineNum + "\n");
					retVal = lineNum;
				} if (Assembler.noArgument.contains(parts[0]) && (parts.length != 1 && inCode)) {
					error.append("Mnemonic on Line " + lineNum + " does not require an argument" + "\n");
					retVal = lineNum;
				} if (!Assembler.noArgument.contains(parts[0]) && (parts.length != 2 && inCode)) {
					error.append("Mnemonic on Line " + lineNum + " requires 1 argument" + "\n");
					retVal = lineNum;
				} if (!inCode && parts.length != 2) {
					error.append("Data at Line " + lineNum + " must have address and value" + "\n");
				} if (!inCode) {
					try{Integer.parseInt(parts[0],16);}
					catch (NumberFormatException e) {
						error.append("\nError on line " + lineNum + 
								": address is not a hex number");
						retVal = lineNum;
					} 
					if(parts.length > 1) {
						try{Integer.parseInt(parts[1],16);}
						catch (NumberFormatException e) {
							error.append("\nError on line " + lineNum + 
									": value is not a hex number");
							retVal = lineNum;
						}
					}
				} else {
					if(parts.length > 1) {
						try{Integer.parseInt(parts[1],16);}
						catch (NumberFormatException e) {
							error.append("\nError on line " + lineNum + 
									": argument is not a hex number");
							retVal = lineNum;
						}
					}
				}
				if (inCode) {
					codeInput.add(line);	
				} else {
					dataInput.add(line);
				}
			} IN.close();
		} /*catch(NumberFormatException e) {
			if(!inCode && addressError) {
				error.append("\nError on line " + lineNum + 
						": address is not a hex number");
				retVal = lineNum;
			} else {
				error.append("\nError on line " + lineNum + 
						": argument is not a hex number");
			}
		}	retVal = lineNum;	
		}*/ catch (FileNotFoundException e) {
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
