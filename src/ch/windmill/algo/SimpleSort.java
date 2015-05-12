package ch.windmill.algo;

public abstract class SimpleSort {
	private int[] list;
	
	/**
	 * Constructor for SimpleSort objects
	 * @param list	The list to sort
	 */
	public SimpleSort(int[] list) {
		this.list = list;
	}
	
	/**
	 * Cast the chars of the given String to an integer array
	 * @param text	String to cast
	 * @return	An integer array with the char values
	 */
	public int[] castStringToIntArray(String text) {
		int[] intList = new int[text.length()];
		char[] charList = text.toCharArray();
		
		for(int i = 0; i < charList.length; i++) {
			intList[i] = (int) charList[i];
		}
		
		return intList;
	}
	
	/**
	 * Default execution method to start with sorting.
	 */
	public abstract int[] sort();
	
	/**
	 * Get the list field.
	 * @return the list field
	 */
	public int[] getList() {
		return list;
	}
	
	/**
	 * Set the list field.
	 * @param list set the list field.
	 */
	public void setList(int[] list) {
		this.list = list;
	}
}
