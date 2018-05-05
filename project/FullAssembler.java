package project;

import java.util.Arrays;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class FullAssembler implements Assembler {
	private boolean blankFound = false;
	private int retVal = 0;
	private int lineNum = 0;
	private int firstBlankLineNum = 0;

	@Override
	public int assemble(String inputFileName, String outputFileName, StringBuilder error) {
		if(error.equals(null)) {
			throw new IllegalArgumentException("Error Buffer cannot be Null");
		}
		File input;
		File output;
		try {
			input = new File(inputFileName);
			output = new File(outputFileName);
		} catch(FileNotFoundException e) {
			error.append("File Not Found");
			retVal = -1;
			return -1;
		} 
		ArrayList<String> codeInput = new ArrayList<>();
		ArrayList<String> dataInput = new ArrayList<>();
		try {
			Scanner IN = new Scanner(input);
			while(IN.hasNextLine() && retVal == 0) {
				String line = IN.nextLine();
				lineNum++;
				if(line.trim().length() == 0 && !blankFound) {
					blankFound = true;
					firstBlankLineNum = lineNum;
				} else if(blankFound && line.trim().length() != 0) {
					error.append("Illegal blank line found at " + lineNum);
					retVal = firstBlankLineNum;
				} else if(!blankFound && (Character.toString(line.charAt(0)).equals(" ") || Character.toString(line.charAt(0)).equals("/t"))) {
					error.append("Line " + lineNum + " starts with illegal white space");
				} else {
					codeInput.add(line);
				}
			}
		}
	}
	
}
