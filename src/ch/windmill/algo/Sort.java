package ch.windmill.algo;

import java.util.Arrays;

/**
 * This is class has serveral sort algorithms.
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class Sort {
    public final static int QUICKSORT = 1;
    public final static int MERGESORT = 2;
    public final static int SELECTIONSORT = 3;
    public final static int INSERTIONSORT = 4;
    public final static int BUBBLESORT = 5;
    
    private int[] unsorted;
    private int[] sorted;
    
    /**
     * Creates Sort objects
     */
    public Sort() {
        this(new int[] {});
    }
    
    /**
     * Creates Sort objects.
     * @param unsorted An array of unsorted values.
     */
    public Sort(final int[] unsorted) {
        this.unsorted = unsorted;
        sorted = Arrays.copyOf(unsorted, unsorted.length);
    }
    
    /**
     * Exchange the element on the position first with the element on the position second.
     * @param a The array of values.
     * @param first The first index.
     * @param second The second index.
     */
    private void exchange(final int[] a, final int first, final int second) {
        int tmp = a[first];
        a[first] = a[second];
        a[second] = tmp;
    }
    
    /**
     * Calculates the median item value of three items (left, right and center). 
     * @param a The array of values.
     * @param left The left item index.
     * @param right The right item index.
     */
    private int medianOfThree(final int[] a, final int left, final int right) {
        int center = (right + left) /2;
        
        if(a[left] > a[center]) {
            exchange(a, left, center);
        }
        if(a[left] > a[right]) {
            exchange(a, left, right);
        }
        if(a[center] > a[right]) {
            exchange(a, right, center);
        }
        exchange(a, center, right);
        return a[right];
    }
    
    /**
     * Get the reference to the unsorted array. This was the start value before sorting.
     * @return The reference to the unsorted array.
     */
    public int[] getUnsorted() {
        return unsorted;
    }
    
    /**
     * Get the reference to the sorted array.
     * @return The reference to the sorted array.
     */
    public int[] getCopyOfSorted() {
        return sorted;
    }
    
    /**
     * Set the reference to the unsorted array.
     * @param unsorted The unsorted array.
     */
    public void setUnsorted(final int[] unsorted) {
        this.unsorted = unsorted;
    }
    
    /**
     * Sort the objects array with the given sort algorithm.
     * @param sortIndex The index of the sort algorithm.
     * @throws NoSuchFieldException The index is not supported.
     */
    public void sort(final int sortIndex) throws NoSuchFieldException {
        switch(sortIndex) {
            case QUICKSORT:
                quickSort(sorted, 0, sorted.length -1);
            case MERGESORT:
                mergeSort(sorted);
            case SELECTIONSORT:
                selectionSort(sorted);
            case INSERTIONSORT:
                insertionSort(sorted);
            case BUBBLESORT:
                bubbleSort(sorted);
            default:
                throw new NoSuchFieldException("The algorithm index was not found.");
        }
    }
    
    /**
     * Cast the chars of the given String to an integer array
     * @param text	String to cast
     * @return	An integer array with the char values
     */
    public int[] castStringToIntArray(final String text) {
        int[] intList = new int[text.length()];
        char[] charList = text.toCharArray();

        for (int i = 0; i < charList.length; i++) {
            intList[i] = (int) charList[i];
        }

        return intList;
    }
    
    /**
     * SelectionSort Check every position of the array, if it's lower than the next position. If it's true, change the position of the current
     * value with the lower position.
     * @param a The unsorted list
     */
    public void selectionSort(final int[] a) {
        int tmp, min = 0;
        
        for (int i = 0; i < a.length; i++) {
            min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }
            tmp = a[min];
            a[min] = a[i];
            a[i] = tmp;
        }
    }
    
    /**
     * InsertionSort, split the array in a unsorted and sorted part. At the beginning, the sorted part is index 0 and the
     * unsorted part is the rest of the array. Every index higher than zero will be placed into the sorted part at the right
     * position.
     * @param a the unsorted list.
     */
    public void insertionSort(final int[] a) {
        int currentValue, j = 0;

        for (int i = 1; i < a.length; i++) {
            currentValue = a[i];
            j = i;
            while ((j > 0) && a[j - 1] > currentValue) {
                a[j] = a[j - 1];
                j--;
            }

            a[j] = currentValue;
        }
    }
    
    /**
     * Sort an array with the bubble sort algorithm. It checks if there was an exchange for the last index. If no exchange
     * was needed, the array is still sorted.
     * @param a Array of unsorted values.
     */
    public void bubbleSort(final int[] a) {
        int range = a.length -1;
        boolean exchanged = false;
        
        do{
            exchanged = false;
            
            for(int i = 0; i < range; i++) {
                if(a[i] > a[i+1]) {
                    exchange(a, i, i+1);
                    exchanged = true;
                }
            }
            
            range--;
        } while(exchanged);
    }
    
    /**
     * Quicksort algorithm whith a median pivot element.
     * @param a Array to sort.
     * @param left Left border, at beginning <code> 0 </code>
     * @param right right border, at beginning <code> a.lenght - 1</code>.
     */
    public void quickSort(final int[] a, final int left, final int right) {
        int up = left;                  // left border
        int down = right - 1;           // right border
        int p = medianOfThree(a, left, right);   // the pivot element is the median of three values
        boolean allElementChecked = false;

        do {
            while (a[up] < p) {
                up++;                   // find the next element who is bigger
            }
            while ((a[down] > p) && (down > up)) {
                down--;                 // find the next element who is lower
            }
            if (up < down) {
                exchange(a, up, down);  // exchange
                up++;                   // move left and right border
                down--;
            } else {
                allElementChecked = true;
            }
        } while (!allElementChecked);   // overlap

        exchange(a, up, right);         // set the separator element to the right position

        if (left < up - 1) {            // check if there are more than 1 element in the left part
            quickSort(a, left, up - 1); // sort the left part
        }
        if (up + 1 < right) {           // check if there are more than 1 element in the right part
            quickSort(a, up + 1, right); // sort the right part (without the separator)
        }
    }
    
    /**
     * Invokes an MergeSort algorithm.
     * @param a An unsorted array.
     */
    public void mergeSort(final int[] a) {
        MergeSort ms = new MergeSort();
        ms.mergeSort(a);
    }
}
