/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.windmill.algo;

/**
 *
 * @author Cyrill Jauner
 */
public class MergeSort {
    int[] b;
    
    public MergeSort() {
        // do nothing
    }
    
    /**
     * 
     * @param a 
     */
    public void mergeSort(final int[] a) {
        int[] b = new int[a.length];
        mergeSort(a, 0, a.length -1);
    }
    
    /**
     * 
     * @param a
     * @param left
     * @param right 
     */
    private void mergeSort(final int[] a, final int left, final int right) {
        int m, i, j, k;
        
        if(right > left) {
            m = (right + left) / 2;         // find the middle index
            mergeSort(a, left, m);          // sort the left side
            mergeSort(a, m+1, right);       // sort the right side
            
            // start with merge
            for(i = left; i <= m; i++) {    // copy the left side to the tmp array
                b[i] = a[i];
            }
            
            for(j = m; j < right; j++) {    // copy the right side reverse to the tmp array
                b[right + m -j] = a[j+1];
            }
            
            i = left;
            j = right;
            for(k = left; k <= right; k++) {    // copy sorted into array
                if(b[i] <= b[j]) {
                    a[k] = b[i];
                    System.out.println("copy "+b[i]+" to pos "+k);
                    i++;
                } else {
                    a[k] = b[j];
                    System.out.println("copy "+b[j]+" to pos "+k);
                    j--;
                }
            }
        }
    }
}
