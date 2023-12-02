/*
 * a2tester.java
 *
 * A test program for assignment 2.
 *
 * While this program includes many test cases, it was still developed in an ad-hoc
 * manner.  In situations where the code under test is required to work correctly
 * at all times, more care would need to be taken to ensure the test cases 
 * provided good code coverage and that all 'edge cases' were, in fact,
 * tested.
 *
 * When your code has failed one of the test cases, you should look at the what the
 * tester is doing prior to failing the test.  Try to determine which of your methods
 * has the bug in it.  Just because the tester is reporting that you failed the result
 * of a particular method (i.e. get) doesn't mean the bug is in the get method.
 * 
 */
public class a2tester
{
	public static int  	testCount = 0;
	public static int   testsPassed = 0;
	public static boolean  	testArraySolution = false;
	public static int 	stressTestSize = 200;


	public static void displayResults (boolean passed)
	{
		/* There is some magic going on here getting the line number 
		 * Borrowed from:
		 * http://blog.taragana.com/index.php/archive/core-java-how-to-get-java-source-code-line-number-file-name-in-code/
		 *
		 * Once we've discussed Exceptions in more detail this won't be required.		 
		*/
		if (passed)
		{
			System.out.println ("Passed test: " + testCount);
		}
		else
		{
			System.out.println ("Failed test: " + testCount + " at line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.out.println(testsPassed + "/32 tests passed.");
			System.exit(1);
		}
		testCount++;
		testsPassed++;
	}
	
	public static void PhoneNumberTest()
	{
		System.out.println("PhoneNumber testing.");
		PhoneNumber p = new PhoneNumber("5551212");

		displayResults (p.getDigits().equals("5551212"));
		displayResults (p.getLabel().equals("Default"));
		
		PhoneNumber p2 = new PhoneNumber("5551212", "Work");
		displayResults (p2.getDigits().equals("5551212"));
		displayResults (p2.getLabel().equals("Work"));

		p.setDigits("4441212");
		displayResults (p.getDigits().equals("4441212"));
		p.setLabel("Home");
		displayResults (p.getLabel().equals("Home"));	

		displayResults(p.toString().equals("Home:4441212"));		
		p = new PhoneNumber("12345678");
		displayResults(p.toString().equals("Default:12345678"));

		p = new PhoneNumber("5551212");
		p2 = new PhoneNumber("5551212", "Home");
		displayResults(p.equals(p2));
		p.setDigits("1234123");
		displayResults(p.equals(p2) == false);
	}


	public static void PhoneNumberListTestOne()
	{
		System.out.println("PhoneNumberList testing: constructor, size, add, get, find");

		PhoneNumberList l = new PhoneNumberList();
		PhoneNumber p = new PhoneNumber("5551212");

		displayResults(l.size() == 0);
		l.add(p);
		displayResults(l.size() == 1);
		displayResults(l.get(0).equals(p));
		displayResults(l.find(p) != -1);
	}

	public static void PhoneNumberListTestTwo()
	{
		System.out.println("PhoneNumberList testing: add, get, find, remove");
	
		PhoneNumberList l = new PhoneNumberList();
		PhoneNumber p1 = new PhoneNumber("5551212", "Work");
		PhoneNumber p2 = new PhoneNumber("4441212", "Home");
	
		l.add(p1);
		l.add(p2);
		displayResults(l.size() == 2);
		PhoneNumber p3 = l.get(0);
		PhoneNumber remaining;

		if (p3.equals(p1))
		{
			remaining = p2;
		}
		else
		{
			remaining = p1;
		}
		l.remove(0);
		displayResults(l.find(remaining) != -1);
		displayResults(l.size() == 1);
		displayResults(l.get(0).equals(remaining));
	
		l.remove(0);
		displayResults(l.size() == 0);
	}

	public static void PhoneNumberListResizeTest()
	{
		System.out.println("PhoneNumberList testing resizing, add, find");
		PhoneNumberList l = new PhoneNumberList();
		
		int	num = 5500000;
		for (int i = 0; i < 100; i++)
		{
			l.add(new PhoneNumber(Integer.toString(num+i)));
		}
		displayResults(l.size() == 100);
		
		boolean passed = true;

		for (int i = 99; i >= 0; i--)
		{
			if (l.find(new PhoneNumber(Integer.toString(num+i))) == -1)
				passed = false;
		}
		displayResults(passed);	
	}

	public static void PhoneNumberListStressTest()
	{
		System.out.println("PhoneNumberList stress test.");
		
		PhoneNumberList l = new PhoneNumberList();
		
		int	num = 5500000;
		for (int i = 0; i < stressTestSize; i++)
		{
			l.add(new PhoneNumber(Integer.toString(num+i)));
		}
		displayResults(l.size() == stressTestSize);
		
		boolean passed = true;

		for (int i = (stressTestSize - 1); i >= 0; i--)
		{
			if (l.find(new PhoneNumber(Integer.toString(num+i))) == -1)
				passed = false;
		}
		displayResults(passed);	

		passed = true;
		for (int i = (stressTestSize - 1); i > 0; i--)
		{
			PhoneNumber p = l.get(0);
			l.remove(0);

			if (l.find(p) != -1)
			{
				passed = false;
				break;
			}

			if (l.size() != i)
			{
				passed = false;	
				break;	
			}
		}
		displayResults(passed);
		
	}

	public static void ContactTest()
	{
		System.out.println("Testing Contact");
		PhoneNumber p1 = new PhoneNumber("5551212", "Work");
		PhoneNumber p2 = new PhoneNumber("7771212", "Home");
		PhoneNumber p3 = new PhoneNumber("8881212", "Cell");

		Contact c = new Contact("Bob Smith");
		displayResults(c.getName().equals("Bob Smith"));
		displayResults(c.getNumberCount() == 0);
		c.setName("Jane Smith");
		displayResults(c.getName().equals("Jane Smith"));

		Contact c2 = new Contact("Susan Jones", p1);
		displayResults(c2.getName().equals("Susan Jones"));
		displayResults(c2.getNumberCount() == 1);
		displayResults(c2.getNumber(0).equals(p1));
		c2.removeNumber(p1);
		displayResults(c2.getNumberCount() == 0);
		c2.addNumber(p2);
		c2.addNumber(p3);
		displayResults(c2.getNumberCount() == 2);
	}

	public static void main (String[] args)
	{
		try
		{
			PhoneNumberTest();
			PhoneNumberListTestOne();
			PhoneNumberListTestTwo();
			PhoneNumberListResizeTest();
			PhoneNumberListStressTest();
			ContactTest();
		}
		catch (Exception e)
		{
			System.out.println("Your code threw an Exception.");
			System.out.println("Perhaps a stack trace will help:");
			e.printStackTrace(System.out);
		}
		System.out.println(testsPassed + "/32 tests passed.");
	}
}
