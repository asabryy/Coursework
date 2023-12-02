public class MaxTemp{

  /** t1 and t2 are considered the same if Math.abs(t1-t2) < EPSILON */
  public static final double EPSILON = 0.01;


  /* add attributes as you need */







  /* ----------------------------------------------------
   * constructor
   * ----------------------------------------------------
   */

  public MaxTemp(Temperature t){
    // add your code here
  }


  /* ----------------------------------------------------
   * getter
   * ----------------------------------------------------
   */

  public double[] getMax(){
    // - returns null if empty array was passed to constructor
    // - returns null if null was passed to constructor
    // - otherwise, returns an array of length 2 [max, count]
    //   where max is the maximum temperature (expressed in the Kelvin scale)
    //   of all Temperature objects passed to the constructor, and count
    //   is the number of times that temperature was present (in the input
    //   array of the constructor)


    // add your code here
    return new double[]{0.1, 0.2, 0.3, 0.4};
  }


  /* OPTIONAL - use your main method to test your code */
  public static void main(String[] args){
     // testing code here is optional
  }
}
