package ch.windmill.algo;

public class SelectionSort extends SimpleSort {

    /**
     * Constructor for SelectionSort objects
     *
     * @param list	The unsorted list
     */
    public SelectionSort(int[] list) {
        super(list);
    }

    /**
     * Call the specific sort method in this class
     *
     * @return	The sorted list
     */
    @Override
    public int[] sort() {
        return selectionSort(getList(), false);
    }

    /**
     * SelectionSort Check every position of the array, if it's lower than the next position (left) or if it's higher as the last position (right). If it's true change the position of the current
     * value with the lower/higher position.
     *
     * @param unsorted The unsorted list
     * @param left	Flag to choose the sorting direction
     * @return	The sorted list
     */
    public int[] selectionSort(final int[] unsorted, final boolean left) {
        int[] sorted = unsorted;
        int tmp = 0;
        int min = 0;

        if (left) {
            for (int i = 0; i < sorted.length; i++) {
                min = i;
                for (int j = i + 1; j < sorted.length; j++) {
                    if (sorted[j] < sorted[min]) {
                        min = j;
                    }
                }
                tmp = sorted[min];
                sorted[min] = sorted[i];
                sorted[i] = tmp;
            }
        } else {
            for (int i = sorted.length - 1; i >= 0; i--) {
                min = i;
                for (int j = i - 1; j >= 0; j--) {
                    if (sorted[j] > sorted[min]) {
                        min = j;
                    }
                }
                tmp = sorted[min];
                sorted[min] = sorted[i];
                sorted[i] = tmp;
            }
        }

        return sorted;
    }
}
