package org.windmill.main;

import java.util.Scanner;

import org.windmill.secure.CRC;

/**
 * This class is a CRC calculator. It calculates a checksum for the given binary data. The 
 * calculator scan for new user typed input as long as the given String is not equal 'e'.
 * 
 * @author Cyrill Jauner
 * @version 1.0
 */
public class CRCCalculator {
	private final String paramDecode = "d";
	
	/**
	 * Start the program. Invoke the constructor of the crc calculator.
	 * @param args Not needed
	 */
	public static void main(String[] args) {
		new CRCCalculator();
	}
	
	/**
	 * Creates a new crc calculator object. It creates a new Scanner object and starts with
	 * scanning the {@code System.in} stream.
	 */
	@SuppressWarnings("resource")
	public CRCCalculator() {
		String input = "";
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("This is a calculator for CRC values\n"
				+ "The syntax is: [data] [optional:poly crc value]\n"
				+ "If you want to end this programm type e\n\n"
				+ "Type your data in the binary format to the console:");
		
		while(!(input = scanner.nextLine()).equals("e")) {
			try {
				process(input);
			} catch(IllegalArgumentException e) {
				System.out.println("Error while processing input.");
			}
		}
	}
	
	/**
	 * Process the user typed text. Check if there is the correct number of parameters and
	 * invoke the crc calculation method of the class {@code CRC}.
	 * @param s The user typed text.
	 * @throws IllegalArgumentException There are too many or too less parameters.
	 */
	private void process(final String s) throws IllegalArgumentException {
		CRC crc = new CRC();
		String res = "";
		String[] words = s.split(" ");
		
		if(words.length == 1) {
			res = crc.calcCRC(words[0]);
		} else if(words.length == 2) {
			if(words[1].equals(paramDecode)) {
				crc.checkCRC(words[0]);
			} else {
				crc.setPolyCRC(words[1]);
				res = crc.calcCRC(words[0]);
			}
		} else if(words.length == 3) {
			if(words[2].equals(paramDecode)) {
				crc.setPolyCRC(words[1]);
				crc.checkCRC(words[0]);
			}
		} else {
			throw new IllegalArgumentException("The number of parameters is not correct.");
		}
		
		System.out.println("Padded data: "+res+"\n"
				+ "Polynomial value: "+crc.getPolyCRC());
	}
}
