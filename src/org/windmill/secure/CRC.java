package org.windmill.secure;

/**
 * This class has methods to calculate the crc of raw binary data.
 * 
 * @author Cyrill Jauner
 * @version 1.0
 */
public class CRC {
	public final static String DEFAULTCRC = "11001";
	private String polyCRC;
	
	/**
	 * Creates a new crc object. The polynomial crc value is the {@code DEFAULTCRC}.
	 */
	public CRC() {
		this(DEFAULTCRC);
	}
	
	
	/**
	 * Creates a new CRC object. The given polynomial CRC must be an correct CRC value
	 * in the right binary format (11001). If you doesn't know a correct CRC value, you can use
	 * the {@code DEFAULTCRC} value.
	 * @param polyCRC The polynomial CRC value.
	 */
	public CRC(final String polyCRC) {
		this.polyCRC = polyCRC;
	}
	
	/**
	 * Set the value of the polyCRC field new.
	 * @param polyCRC The polynomial value.
	 */
	public void setPolyCRC(final String polyCRC) {
		this.polyCRC = polyCRC;
	}
	
	/**
	 * Get the value of polyCRC.
	 * @return The current value of the field polyCRC.
	 */
	public String getPolyCRC() {
		return polyCRC;
	}
	
	/**
	 * Calculate the CRC value for the given data. 
	 * @param data The raw data.
	 * @return The data with the rest of the division.
	 */
	public String calcCRC(final String data) {
		return (data + polyDivision(getPaddedData(data)));
	}
	
	/**
	 * Check the given CRC parsed String data with the current polyCRC value.
	 * @param data The CRC parsed String.
	 * @return True if the String is correct, otherwise false.
	 */
	public boolean checkCRC(final String data) {
		boolean correct = false;
		String result = polyDivision(data.toCharArray());
		
		if((result.charAt(result.length() -1)) == '0') {	// Check if the lowest bit is equals 0
			correct = true;
		}
		
		return correct;
	}
	
	/**
	 * This method makes an polynomial division for the data and the polyCRC value.
	 * The rest of the division must be as long as the length of the polyCRC value -1.
	 * If the rest is too short, it will be filled up with zeros.
	 * @param bits
	 * @return
	 */
	@SuppressWarnings("unused")
	private String polyDivision(final char[] bits) {
		String result = "";
		
		for(char c : bits) {
			result += c;								// add the next bit to the sequence
														// check if the bit sequence is long enough
			if(result.length() == polyCRC.length()) {	// XOR calculation of the current bit sequence and the poly crc value
				result = Integer.toBinaryString(Byte.parseByte(result, 2)^Byte.parseByte(polyCRC, 2));
			}
		}
		
		for(int i = 0; result.length() < (polyCRC.length() -1); i++) {
			result = "0"+result; 	// if the current value is too short, add zeros before the value
		}
		
		return result;
	}
	
	/**
	 * Append zeros to the given data. It adds (length of polyCRC -1) zeros to the String.
	 * @param data The raw data.
	 * @return The data with the appendix as char array.
	 */
	private char[] getPaddedData(final String data) {
		String paddedData = data;
		
		for(int i = 0; i < (polyCRC.length()-1); i++) {
			paddedData += "0";
		}
		
		return paddedData.toCharArray();
	}
}
