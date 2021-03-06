package ch.windmill.main;

import ch.windmill.algo.Sort;
import java.util.Arrays;

/**
 * This is only test class for the sort algorithms of the package <code>ch.windmill.algo</code>.
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class SortTest {
    
    public static void main(String[] args) {
        //int[] list = new int[] {4,6,2,3,1,5};
        int[] list = new int[100000];
        long start, end = 0;
        
        for(int j = 0; j < list.length; j++) {
            list[j] = (int)(Math.random()*256);
        }
        
        start = System.currentTimeMillis();
        Sort.quickSort(Arrays.copyOf(list, list.length));
        end = System.currentTimeMillis();
        System.out.println("quickSort time: "+(end -start)+" miliseconds");
        
        start = System.currentTimeMillis();
        Sort.quickInsertionSort(Arrays.copyOf(list, list.length));
        end = System.currentTimeMillis();
        System.out.println("quickInsertionSort time: "+(end -start)+" miliseconds");
        
        start = System.currentTimeMillis();
        Sort.mergeSort(Arrays.copyOf(list, list.length));
        end = System.currentTimeMillis();
        System.out.println("mergeSort time: "+(end -start)+" miliseconds");
        
        start = System.currentTimeMillis();
        Sort.insertionSort(Arrays.copyOf(list, list.length));
        end = System.currentTimeMillis();
        System.out.println("insertionSort time: "+(end -start)+" miliseconds");
        
        start = System.currentTimeMillis();
        Sort.selectionSort(Arrays.copyOf(list, list.length));
        end = System.currentTimeMillis();
        System.out.println("selectionSort time: "+(end -start)+" miliseconds");
        
        start = System.currentTimeMillis();
        Sort.bubbleSort(Arrays.copyOf(list, list.length));
        end = System.currentTimeMillis();
        System.out.println("bubbleSort time: "+(end -start)+" miliseconds");
    }
}
