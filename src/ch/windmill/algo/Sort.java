package ch.windmill.algo;

import java.util.Arrays;

/**
 * This is class has serveral sort algorithms.
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class Sort {
    /**
     * Creates Sort objects
     */
    public Sort() {
        // do nothing
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
     * Quicksort algorithm with right separation element.
     * @param a Array to sort.
     * @param left Left border, at beginning <code> 0 </code>
     * @param right right border, at beginning <code> a.lenght - 1</code>.
     */
    public void quickSort(final int[] a, final int left, final int right) {
        int up = left;                  // linke Grenze
        int down = right - 1;           // rechte Grenze (ohne Trennelement)
        int t = a[right];               // rechtes Element als Trennelemt
        boolean allElementChecked = false;

        do {
            while (a[up] < t) {
                up++;                   // suchen groesseres Element von links an
            }
            while ((a[down] > t) && (down > up)) {
                down--;                 // suchen kleineres Element von rechts an
            }
            if (up < down) {
                exchange(a, up, down);  // austauschen
                up++;                   // linke und rechte Grenze verschieben:
                down--;
            } else {
                allElementChecked = true;
            }
        } while (!allElementChecked);   // Ueberschneidung

        exchange(a, up, right);         // Trennelement an endgueltige Position (a[up])

        if (left < up - 1) {            // mehr als 1 Element in linker Teilfolge?
            quickSort(a, left, up - 1); // linke Haelfte sortieren
        }
        if (up + 1 < right) {           // mehr als 1 Element in rechter Teilfolge?
            quickSort(a, up + 1, right); // rechte Haelfte sortieren (ohne Trennelement)
        }
    }
    
    /**
     * 
     * @param a 
     */
    public void mergeSort(final int[] a) {
        MergeSort ms = new MergeSort();
        ms.mergeSort(a);
    }
}
