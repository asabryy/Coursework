import java.lang.Math;

/* Name: Ahmed Ahmed
 * Student Number: V00947456
 */

/*
 * ArrayOperations
 * DO NOT use builtin java Arrays mehthods
 * A class with basic array methods to
 *  - print the values in an array
 *  - calculate the product of the values in an array
 *  - calculate the minimum of the values in an array
 *  - calculate the maximum of the values in an array
 *  - determine the equality to 2 arrays
 *  -
 *
 */
public class ArrayOperations {
    /*
     * printArray
     *
     * Purpose: prints all the values in the array to the console
     *  example format:  {1,2,3,4}
     *
     * Parameters: an array of integers
     *
     * Returns: void
     *
     */
    public static void printArray ( int[] array ) {
        System.out.print("{");
        for(int i=0; i<array.length; i++) {

            System.out.print(array[i]);
            if(i<array.length-1)
                System.out.print(",");
        }

        System.out.println("}");
    }

    /*
     * arrayProduct
     *
     * Purpose: computes the product of all values in the input array
     *  NOTE: product of 3 numbers n1, n2 and n3 = n1*n2*n3
     *  NOTE: product of no numbers = 1
     *
     * Parameters: an array of integers
     *
     * Returns: product of all values in the array
     *
     */
    public static int arrayProduct ( int[] array ) {
        int product = 1;
		for(int i=0; i<array.length; i++){
			product = array[i] * product; 
		}
        
		return product;
    }

    /*
     * arrayMax
     *
     * Purpose: finds the maximum value in the input array
     *
     * Parameters: an array of integers
     *
     * Preconditions:
     *	array contains at least one element
     *
     * Returns: maximum value in the array
     *
     */
    public static int arrayMax (int[] a) {
		int maxA = 1;
		for(int i=0; i<a.length; i++){
			if(a[i] > maxA){
				maxA = a[i];
			}
        }
		
		return maxA;
    }
    
    /*
     * arrayMin
     *
     * Purpose: finds the maximum value in the input array
     *
     * Parameters: an array of integers
     *
     * Preconditions:
     *	array contains at least one element
     *
     * Returns:  minimum value in the array
     *
     */
    public static int arrayMin (int[] a) {
		int minA = a[0];
		for(int i=0; i<a.length; i++){
			if(a[i] < minA) {
				minA = a[i];
			}
		}
        return minA;
    }

    /*
     * arraysEqual
     *
     * Purpose: determines whether the two arrays are equal
     *      where equal means array1 and array2 are the same length
     *      and the contain the same values in the same order
     *
     * Parameters: two arrays of integers
     *
     * Returns: true if the are equal, false otherwise
     *
     */
    public static boolean arraysEqual (int[] a, int[] b) {
		if (a.length == b.length){
			for(int i=0; i<a.length; i++){
				if(a[i] != b[i]){
					return false;
				}
			}
		}else{
			return false;
		}
        return true;
    }

    /*
     * shiftLeft
     *
     * Purpose: copies every element in the array into a new array of the same length
     *  with every element shifted by the left by the specified amount
     *
     * Parameters: an input array of integers, 
     *   and the number of positions to shift left by
     *
     * Returns: int[] - the new array
     *
     */
    public static int[] shiftLeft (int[] a, int pos) {
        int[] b = new int[a.length];
			if(a.length>1){
				for(int i=0; i<a.length; i++){
					int j = i-pos;
					if (j < 0 && a.length + j > -1){
						int h = a.length + j;
						b[h] = a[i];
					}else if(a.length + j < 0){
						int r = a.length + j;
						b[a.length + r] = a[i];
					}else{
						b[j] = a[i];
					}
				}
			}else{
				b = a;
			}
        return b;
    }
}
