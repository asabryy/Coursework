/*
 * A1Tester
 *
 * A class to test the methods in Assignment 1
 *
 */
public class A1Tester {

    private static int testPassCount = 0;
    private static int testCount = 0;

    private static int a0[] = {};
    private static int a1[] = {2};
    private static int a4[] = {2, 1, 3, 0};
    private static int a5[] = {-1, 2, 4, 1, 3};
    private static int a1NotEqual[] = {3};
    private static int a1Equal[] = {2};
    private static int a4NotEqual[] = {2, 1, 3, 0, 5};
    private static int a5Equal[] = {-1, 2, 4, 1, 3};
    private static int a5NotEqual[] = {-1, 2, 4, 0, 3};

    // for approximate comparison of floating point numbers
    private static final double THRESHOLD = 0.01;

    private static double[] person1 = {160, 159.64,  4};
    private static double[] person2 = {95,   89.53, 14};
    private static double[] person3 = {40,  225.20,  3};

    private static double[][] population1 = {person2};
    private static double[][] population3 = {person3, person2, person1};

    public static void main(String[] args) {

        // Test Array Operations methods
        ArrayOperations.printArray(a1);
        ArrayOperations.printArray(a4);
        ArrayOperations.printArray(a5);

        testProductArray();
        testMaxArray();
        testMinArray();
        testEqualArrays();
        testShiftLeft();

        // Test ExpenseCalculator methods
        testCalcMileage();
        testCalcFood();
        testCalcHotel();
        testCalcTotalExpense();
        testCalcAllExpenses();

        System.out.println("Passed " + testPassCount + "/" + testCount + " tests");
    }

    public static void displayResults (boolean passed, String testName)
    {
        /* There is some magic going on here getting the line number
         * Borrowed from:
         * http://blog.taragana.com/index.php/archive/core-java-how-to-get-java-source-code-line-number-file-name-in-code/
         */

        testCount++;
        if (passed)
        {
            System.out.println ("Passed test: " + testName);
            testPassCount++;
        }
        else
        {
            System.out.println ("Failed test: " + testName + " at line "
                                + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }

    public static void testProductArray() {
        int result = 0;


        result = ArrayOperations.arrayProduct(a0);
        //System.out.println("should be 1: " + result);
        displayResults(result==1, "testProductArray");

        result = ArrayOperations.arrayProduct(a1);
        //System.out.println("should be 2: " + result);
        displayResults(result==2, "testProductArray");

        result = ArrayOperations.arrayProduct(a4);
        //System.out.println("should be 0: " + result);
        displayResults(result==0, "testProductArray");

        result = ArrayOperations.arrayProduct(a5);
        //System.out.println("should be -24: " + result);
        displayResults(result==-24, "testProductArray");

    }

    public static void testMaxArray() {
        int result = 0;

       
        result = ArrayOperations.arrayMax(a1);
        //System.out.println("should be 2: " + result);
        displayResults(result==2, "testMaxArray");

        result = ArrayOperations.arrayMax(a4);
        //System.out.println("should be 3: " + result);
        displayResults(result==3, "testMaxArray");

        result = ArrayOperations.arrayMax(a5);
        //System.out.println("should be 4: " + result);
        displayResults(result==4, "testMaxArray");
    }

    public static void testMinArray() {
        int result = 0;

        result = ArrayOperations.arrayMin(a1);
        //System.out.println("should be 2: " + result);
        displayResults(result==2, "testMinArray");

        result = ArrayOperations.arrayMin(a4);
        //System.out.println("should be 0: " + result);
        displayResults(result==0, "testMinArray");

        result = ArrayOperations.arrayMin(a5);
        //System.out.println("should be -1: " + result);
        displayResults(result==-1, "testMinArray");
    }



    public static void testEqualArrays() {
        boolean result = true;

        // TODO: uncomment the following code to test your MaxArray method

        result = ArrayOperations.arraysEqual(a1, a1Equal);
        //System.out.println("should be true: " + result);
        displayResults(result, "testEqualArrays");

        result = ArrayOperations.arraysEqual(a1, a1NotEqual);
        //System.out.println("should be false: " + result);
        displayResults(!result, "testEqualArrays");;

        result = ArrayOperations.arraysEqual(a5, a5Equal);
        //System.out.println("should be true: " + result);
        displayResults(result, "testEqualArrays");

        result = ArrayOperations.arraysEqual(a5, a5NotEqual);
        //System.out.println("should be false: " + result);
        displayResults(!result, "testEqualArrays");

        result = ArrayOperations.arraysEqual(a5NotEqual, a5);
        //System.out.println("should be false: " + result);
        displayResults(!result, "testEqualArrays");

        result = ArrayOperations.arraysEqual(a4NotEqual, a4);
        //System.out.println("should be false: " + result);
        displayResults(!result, "testEqualArrays");

        result = ArrayOperations.arraysEqual(a4, a4NotEqual);
        //System.out.println("should be false: " + result);
        displayResults(!result, "testEqualArrays");
    }

    public static void testShiftLeft() {
        int[] result;

        result = ArrayOperations.shiftLeft(a0, 2);
        int[] shouldBe0 = {};
        //System.out.print("should be: ");
        //ArrayOperations.printArray(shouldBe0);
        //ArrayOperations.printArray(result);
        displayResults(ArrayOperations.arraysEqual(result,shouldBe0), "testShiftBy");

        result = ArrayOperations.shiftLeft(a1, 2);
        int[] shouldBe1 = {2};
        //System.out.print("should be: ");
        //ArrayOperations.printArray(shouldBe1);
        //ArrayOperations.printArray(result);
        displayResults(ArrayOperations.arraysEqual(result,shouldBe1), "testShiftBy");

        result = ArrayOperations.shiftLeft(a4, 1);
        int[] shouldBe4 = {1,3,0,2};
        //System.out.print("should be: ");
        //ArrayOperations.printArray(shouldBe4);
        //ArrayOperations.printArray(result);
        displayResults(ArrayOperations.arraysEqual(result,shouldBe4), "testShiftBy");

        result = ArrayOperations.shiftLeft(a5, 3);
        int[] shouldBe5 = {1,3,-1,2,4};
        //System.out.print("should be: ");
        //ArrayOperations.printArray(shouldBe5);
        //ArrayOperations.printArray(result);
        displayResults(ArrayOperations.arraysEqual(result,shouldBe5), "testShiftBy");

        result = ArrayOperations.shiftLeft(a5, 7);
        int[] shouldBe6 = {4,1,3,-1,2};
        //System.out.print("should be: ");
        //ArrayOperations.printArray(shouldBe6);
        //ArrayOperations.printArray(result);
        displayResults(ArrayOperations.arraysEqual(result,shouldBe6), "testShiftBy");
    }

    public static void testCalcMileage() {

        double result  = 0;

        double kmsDrive   = 0;
        result = ExpenseCalculator.calcMileage(0);
        //System.out.println("should be approx 0: " + result);
        displayResults(Math.abs(result-0)<THRESHOLD, "testMileage");

        kmsDrive = 21.7;
        result = ExpenseCalculator.calcMileage(kmsDrive);
        //System.out.println("should be approx 5.208: " + result);
        displayResults(Math.abs(result-5.208)<THRESHOLD, "testMileage");

        kmsDrive = 100;
        result = ExpenseCalculator.calcMileage(kmsDrive);
        //System.out.println("should be approx 24.0: " + result);
        displayResults(Math.abs(result-24)<THRESHOLD, "testMileage");

        kmsDrive = 101.5;
        result = ExpenseCalculator.calcMileage(kmsDrive);
        //System.out.println("should be approx 24.72: " + result);
        displayResults(Math.abs(result-24.72)<THRESHOLD, "testMileage");
    }

    public static void testCalcFood() {

        double result = 0;

        int days = 1;
        result = ExpenseCalculator.calcFood(days);
        //System.out.println("should be approx 65.0: " + result);
        displayResults(Math.abs(result-65)<THRESHOLD, "testCalcFood");

        days = 4;
        result = ExpenseCalculator.calcFood(days);
        //System.out.println("should be approx 260.0: " + result);
        displayResults(Math.abs(result-260)<THRESHOLD, "testCalcFood");

        days = 7;
        result = ExpenseCalculator.calcFood(days);
        //System.out.println("should be approx 345.0: " + result);
        displayResults(Math.abs(result-345)<THRESHOLD, "testCalcFood");

        days = 13;
        result = ExpenseCalculator.calcFood(days);
        //System.out.println("should be approx 640.71: " + result);
        displayResults(Math.abs(result-640.71)<THRESHOLD, "testCalcFood");
    }

    public static void testCalcHotel() {

        double result = 0;

        int days = 5;
        double rate = 99.97;
        result = ExpenseCalculator.calcHotel(rate, days);
        //System.out.println("should be approx 451.86:" + result);
        displayResults(Math.abs(result-451.86)<THRESHOLD, "testCalcHotel");

        days = 3;
        rate = 172.56;
        result = ExpenseCalculator.calcHotel(rate, days);
        //System.out.println("should be approx 389.99:" + result);
        displayResults(Math.abs(result-389.99)<THRESHOLD, "testCalcHotel");
    }

    public static void testCalcTotalExpense() {
        double result = 0;

        result = ExpenseCalculator.calcTotalExpense(person1);
        //System.out.println("should be approx 853.98: " + result);
        displayResults(Math.abs(result-853.98)<THRESHOLD, "testCalcTotalExpense");

        result = ExpenseCalculator.calcTotalExpense(person2);
        //System.out.println("should be approx 2028.00: " + result);
        displayResults(Math.abs(result-2028.00)<THRESHOLD, "testCalcTotalExpense");

        result = ExpenseCalculator.calcTotalExpense(person3);
        //System.out.println("should be approx 713.55: " + result);
        displayResults(Math.abs(result-713.55)<THRESHOLD, "testCalcTotalExpense");
    }

    public static void testCalcAllExpenses() {

        int result1[] = new int[population1.length];
        int expected1[] = {2027};

        ExpenseCalculator.calcAllExpenses(population1, result1);
        //        System.out.print("should be:");
        //        ArrayOperations.printArray(expected1);
        //        System.out.print("is:");
        //        ArrayOperations.printArray(result1);
        displayResults(ArrayOperations.arraysEqual(result1, expected1), "testCalcAllExpenses");

        int result2[] = new int[population3.length];
        int expected2[] = {713, 2027, 853};

        ExpenseCalculator.calcAllExpenses(population3, result2);
        //        System.out.print("should be:");
        //        ArrayOperations.printArray(expected2);
        //        System.out.print("is:");
        //        ArrayOperations.printArray(result2);
        displayResults(ArrayOperations.arraysEqual(result2, expected2), "testCalcAllExpenses");
    }
}
