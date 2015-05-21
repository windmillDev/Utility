package ch.windmill.algo;

/**
 * This class has serveral sort algorithms.
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class Sort {
    public final static int M = 25;
    
    /**
     * Exchange the element on the position first with the element on the position second.
     * @param a Array to sort.
     * @param first The first index.
     * @param second The second index.
     */
    private static void exchange(final int[] a, final int first, final int second) {
        int tmp = a[first];
        a[first] = a[second];
        a[second] = tmp;
    }
    
    /**
     * Calculates the median item value of three items (left, right and center). 
     * @param a Array to sort.
     * @param left The left item index.
     * @param right The right item index.
     */
    private static int medianOfThree(final int[] a, final int left, final int right) {
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
     * Cast the chars of the given String to an integer array
     * @param text	String to cast
     * @return	An integer array with the char values
     */
    public static int[] castStringToIntArray(final String text) {
        int[] intList = new int[text.length()];
        char[] charList = text.toCharArray();

        for (int i = 0; i < charList.length; i++) {
            intList[i] = (int) charList[i];
        }

        return intList;
    }
    
    /**
     * SelectionSort Check every position of the array, if it's lower than the next position. If it's true, change the position of the current
     * value with the lower position. This algorithm has a complexity of O(n^2) and is not stable.
     * @param a Array to sort.
     */
    public static void selectionSort(final int[] a) {
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
     * position. This algorithm has a complexity of O(n^2) and is stable.
     * @param a Array to sort.
     */
    public static void insertionSort(final int[] a) {
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
     * was needed, the array is still sorted. This algorithm has a complexity of O(n^2) and is stable.
     * @param a Array to sort.
     */
    public static void bubbleSort(final int[] a) {
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
     * Quicksort algorithm with a median pivot element. This algorithm has a not guaranteed complexity of 
     * O(n*log(n)) and is not stable.
     * @param a Array to sort.
     * @param left Left border, at beginning <code>0</code>.
     * @param right right border, at beginning <code>a.lenght - 1</code>.
     */
    private static void quickSort(final int[] a, final int left, final int right) {
        int up = left;                  // left border
        int down = right - 1;           // right border hallo nicole
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
        exchange(a, up, right);         // set the pivot element to the right position
        if (left < up - 1) {            // check if there are more than 1 element in the left part
            quickSort(a, left, up - 1); // sort the left part
        }
        if (up + 1 < right) {           // check if there are more than 1 elements in the right part
            quickSort(a, up + 1, right); // sort the right part (without the pivot)
        }
    }
    
    /**
     * Invokes the QuickSort method in this class. The start values are <code>left = 0</code> and
     * <code>right = a.length -1</code>.
     * @param a Array to sort.
     */
    public static void quickSort(final int[] a) {
        quickSort(a, 0, a.length -1);
    }
    
    /**
     * Quickinsertionsort algorithm with a median pivot element. It works equals as the normal quicksort algorithm,
     * but if the list is shorter than <code>M</code>, it use an Insertionsort. This algorithm has a not 
     * guaranteed complexity of O(n*log(n)) and is not stable.
     * @param a Array to sort.
     * @param left Left border, at beginning <code>0</code>.
     * @param right right border, at beginning <code>a.lenght - 1</code>.
     */
    private static void quickInsertionSort(final int[] a, final int left, final int right) {
        if(right -left > M) {
            int up = left;                  // left border
            int down = right - 1;           // right border hallo nicole
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
            exchange(a, up, right);         // set the pivot element to the right position
            if (left < up - 1) {            // check if there are more than 1 element in the left part
                quickInsertionSort(a, left, up - 1); // sort the left part
            }
            if (up + 1 < right) {           // check if there are more than 1 elements in the right part
                quickInsertionSort(a, up + 1, right); // sort the right part (without the pivot)
            }
        } else {
            int tmp, i, j;                  // insertion sort

            for (i = (left + 1); i <= right; ++i) {
                tmp = a[i];
                for (j = (i - 1); (j >= left) && (tmp < a[j]); --j) {
                    a[j + 1] = a[j];
                }
                a[j + 1] = tmp;
            }
        }
    }
    
    /**
     * Invokes the QuickInsertionSort method in this class. The start values are <code>left = 0</code> and
     * <code>right = a.length -1</code>.
     * @param a Array to sort.
     */
    public static void quickInsertionSort(final int[] a) {
        quickInsertionSort(a, 0, a.length -1);
    }
    
    /**
     * Invokes an MergeSort algorithm.
     * @param a Array to sort.
     */
    public static void mergeSort(final int[] a) {
        (new MergeSort()).sort(a);
    }
    
    public static void printOutArray(final int[] a) {
        System.out.println();
        for(int i = 0; i < a.length; i++) {
            System.out.print(a[i]+" ");
        }
    }
}
