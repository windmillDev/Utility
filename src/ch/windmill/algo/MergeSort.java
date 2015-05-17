package ch.windmill.algo;

/**
 *
 * @author Cyrill Jauner
 */
public class MergeSort {
    int[] b;
    
    public MergeSort() {
        b = new int[0];
    }
    
    /**
     * Sort the array with a mergesort algorithm.
     * @param a Array to sort.
     */
    public void sort(final int[] a) {
        b = new int[a.length];
        mergeSort(a, 0, a.length -1);
    }
    
    /**
     * Mergesort algorithm. This method sort the given array with a recursive mergesort algorithm.
     * This algorithm has a guaranteed complexity of O(n*log(n)) and is stable.
     * @param a Array to sort.
     * @param left left border, at beginning <code>0</code>.
     * @param right right border, at beginning <code>a.length -1</code>.
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
                    //System.out.println("copy "+b[i]+" to pos "+k);
                    i++;
                } else {
                    a[k] = b[j];
                    //System.out.println("copy "+b[j]+" to pos "+k);
                    j--;
                }
            }
        }
    }
}
