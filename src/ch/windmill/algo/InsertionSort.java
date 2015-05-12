package ch.windmill.algo;

public class InsertionSort extends SimpleSort{

	/**
	 * Constructor for SelectionSort objects
	 * @param list	The unsorted list
	 */
	public InsertionSort(int[] list) {
		super(list);
	}
	
	/**
	 * Call the specific sort method in this class
	 * @return	The sorted list
	 */
	@Override
	public int[] sort() {
		return insertionSort(getList());
	}
	
	/**
	 * InsertionSort
	 * Increment the position of the new value and check at which position it must be saved in the list part.
	 * It will check the values from the highest to the lowest position in the list part.
	 * @param unsorted the unsorted list.
	 * @return the sorted list.
	 */
	private int[] insertionSort(int[] unsorted) {
		int[] sorted = unsorted;
		int currentValue = 0;
		int j = 0;
		
		for(int i = 0; i < sorted.length; i++) {
			currentValue = sorted[i];
			j = i;
			while((j > 0) && (sorted[j-1] > currentValue)) {
				sorted[j] = sorted[j -1];
				j--;
			}
			
			sorted[j] = currentValue;
		}
		
		return sorted;
	}
}
