/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.windmill.main;

import ch.windmill.algo.Sort;

/**
 * This is only test class for the sort algorithms of the package <code>ch.windmill.algo</code>.
 * @author Cyrill Jauner
 */
public class SortTest {
    
    public static void main(String[] args) {
        int[] list = new int[] {4,3,6,7,8,1,9,0,2,5};
        Sort s = new Sort();
        s.bubbleSort(list);
        
        for(int i = 0; i < list.length; i++) {
            System.out.print(list[i]+" ");
        }
    }
}
