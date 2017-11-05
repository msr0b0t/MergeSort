//Kouvela Maria, 2360, mkouvela@csd.auth.gr

/* Part of this mergesort implementation is a copy of the code in this link:
https://github.com/andrewhill157/coursera-algorithms-part1/blob/master/Counting%20Inversions%20(Divide%20and%20Conquer)/src/CountInversions.java
*/

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Main {

    //global count of the cost of inversions
    private static long cost = 0;

    /**
     * Main method of the class. Counts the cost of the inversions found in the file provided for the assignment.
     */
    public static void main(String[] args) throws IOException {

        ArrayList<Integer> list = new ArrayList<>();

        //open the input file and add each integer to the arraylist
        String fileName = args[0];
        File file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                list.add(scanner.nextInt());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        //copy the list to an array
        int[] numbers = arrayListToArray(list);

        //mergesort
        int[] sortedNumbers = sortAndCountInversions(numbers);

        System.out.println("Total cost: " + cost);

    }

    /**
     * Returns a sorted copy of the input array and sets inversions (global) to the number of inversions
     * Inversions: number of instances where a[i] > a[j] for i < j*
     * Complexity: O(n*log(n))
     * @param a - array of integers containing at least one element
     * @return sorted array containing the elements of a
     */
    private static int[] sortAndCountInversions(int[] a) {

        if(a.length == 1) { //Base-case, array has one element, return array (already sorted)
            return a;
        } else {
            // Recursively sort each half of the array separately and then merge the results, keeping track of inversions
            int[] firstHalf = sortAndCountInversions(Arrays.copyOfRange(a,0, a.length/2)); // sort first half
            int[] secondHalf = sortAndCountInversions(Arrays.copyOfRange(a, (a.length/2), a.length)); // sort second half
            return mergeAndCountSplitInversions(firstHalf, secondHalf); // merge results and count inversions
        }
    }

    /**
     * Merges the arrays a and b, such that the resulting array is in sorted ascending order.
     * inversions (global) is incremented when split inversions are detected
     * Split inversions: number of instances where a[i] > a[j] for i <= n/2 and j > n/2, where n is the length of a*
     * Complexity: O(n*log(n))
     * @param a - array of integers sorted in ascending order containing at least one element
     * @param b - array of integers sorted in ascending order containing at least one element
     * @return array of elements containing the combined elements of a and b in sorted ascending order
     */
    private static int[] mergeAndCountSplitInversions(int[] a, int[] b) {

        int j = 0; // index for a
        int k = 0; // index for b

        // array to store merged a and b arrays
        int[] result = new int[a.length + b.length];

        // merge the arrays element by element, keeping track of split inversions
        for(int i = 0; i < result.length; i++) {

            if(j < a.length && k < b.length) { // there are remaining elements in both a and b
                // transfer smaller of a[j], b[k] to results
                if(a[j] <= b[k]) {
                    result[i] = a[j];
                    j++;

                } else {
                    result[i] = b[k];

                    if (a[j] == b[k] + 1) { //if the integers differ by 1
                        cost += 2 + (a.length - (j + 1)) * 3;
                    }else {
                        cost += (a.length - j) * 3;
                    }

                    //increase k to get the next element of b
                    k++;
                }

            } else if(j < a.length) { // there are remaining elements only in a
                result[i] = a[j];
                //increase j to get the next element of a
                j++;
            } else if(k < b.length) { // there are remaining elements only in b
                result[i] = b[k];
                //increase k to get the next element of b
                k++;
            }
        }
        return result;
    }

    /**
     * Produces an array of ints from an ArrayList of Integers
     * @param a - ArrayList of Integers containing at least one element
     * @return an array of ints with all the elements of a
     */
    private static int[] arrayListToArray(List<Integer> a) {

        int[] result = new int[a.size()];

        // Transfer each element of a into the new array
        for(int i = 0; i < a.size(); i++) {
            result[i] = a.get(i);
        }
        return result;
    }
}
