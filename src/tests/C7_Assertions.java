package tests;

import java.beans.Statement;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;

import packages.TestAssert;
import packages.TestResourceCloseable;

public class C7_Assertions {

	public static void main(String[] args) {
		testAssertions();

		try {
			testMultiCatchException();
			testAutocloseableResources();
		}
		catch (Exception e) {
			
		}
	}
	
	// Test Assertion
	// Info: If the assert is false then throw AssertionError
	// Info: Only execute the first assert
	// Info: To old version (<1.4) you can use assert as identifier
	// Info: To compile old version use javac -source 1.3 xxx.java or javac -source 3 xxx.java
	// Keyword: assert (boolean b) : toString value => toString value can be a primitive, a object, a method, a new ValidAssert object
	// Keyword: assert boolean b : toString value
	// Use: enable assert with java -ea xxx or java -enableassertions xxx
	// Use: disable assert with java -da xxx or java -disableassertions xxx
	// Use: you can use java -ea -da:package... xxx or java -ea -da:package.TestClass xxx to disabled an especific assert
	// Use: you can use java -da -ea:package... xxx or java -da -ea:package.TestClass xxx to enabled an especific assert
	// Rule: Don't catch AssertionError
	// Rule: Don't use to validate argument in public method
	// Rule: Don't use to validate console argument in main method
	// Rule: Don't use when it cause side effect
	// Rule: Use to validate argumment in private method
	// Rule: Use to validate code block that never should be executed
	public static void testAssertions() {
		System.out.println("Test Assertions");
		
		int i1 = 10;
		int i2 = 20, i3= 30;
		boolean b1 = true;
		//TestAssert ta1 = new TestAssert();

		// to old version (<1.4) you can use assert as identifier
		//int assert = 0;
		
		// really simple use of assert
		//assert(false);
		//assert(i1 != 10);

		// simple use of assert with message
		//assert !b1 : "it is true";
		//assert(i2 != 20) : "i2 is " + i2;

		// you can use assert with method
		//assert(TestAssert.testAssertMethod());
		
		switch (i1) {
			case 10:
				i3 = 1;
				break;
			case 20:
				i3 = 2;
				break;
			default:
				// never to get here
				assert false;
		}
	}
	
	// Test Multicatch Exception
	// Use: catch (Exception 1 | Exception 2 | Exception 3)
	// Rule: Only work with exceptions in different inheritance hierarchies
	// Rule: Order of exception in catch doesn't matter
	// Rule: The parameter (e) of a multicatch block cannot be assigned (only with simple catch though it's a bad practice)
	// Rule: The sub-exception always go before super-exception independint it is a simplecatch or a multicatch
	// Rule: When a method throw a list of exception, you can catch all them using catch (Exception e), but we aren't really catching all exceptions, only what were declare in method (JAVA 1.7 o highter)
	// Rule: When occurs the previous rule, you can assign a new exception to (e) parameter but you can't throw it outside of the method.
	public static void testMultiCatchException() {
		System.out.println("\n\nTest Multicatch");
		
		Random rd = new Random();
		try {
			if (rd.nextInt() > 0) {
				throw new IOException("Test Exception 1");
			}
			else if (rd.nextInt() > 0) {
				throw new SQLException("Test Exception 2");
			}
			else {
				throw new FileNotFoundException("Test Exception 3");
			}
		}
		catch (FileNotFoundException e) {
			e = new FileNotFoundException("Test Exception 3 rewrite");
			System.out.println(e);
		}
		catch (SQLException | IOException e) {
			// you can't do this in multicatch
			//e = new Exception();
			System.out.println(e);
		}
		
		// you can catch several exception from method with multicatch
		try {
			throwException();
		}
		catch (IOException | SQLException e) {
			System.out.println(e);
		}
		
		// you can catch several exception from method using catch (Exception e)
		// it will NOT catch all Exception, it will only catch SQLException and IOException because the method uses them
		// it is use when change the exception throw by the method and you can't change all the places where you use it
		try {
			throwException();
		}
		catch (Exception e) {
			System.out.println(e);
		}

	}
	
	// Test Autocloseable Resources (try-with-resources)
	// Use: try (resource1; resource2; resource 3) {}
	// Rule: Use only resources that implement AutoCloseable (all exceptions) or Closeable (only IOException and subclass) interfaces
	// Rule: Resources used in try-with-resources are final, so they can't be assigned again
	// Rule: AutoCloseable can close several times (idempotent), Closeable only one
	// Rule: try execute a invisible finally that close the resources
	// Rule: resource's close method gets called in the reverse order in which resources are declared
	// Rule: Explicit catch or finally are optionals (but you need throw the exception outside the method)
	// Rule: When you use try-with-resource and the resources throw several exception, those are chained fromthe first one to the last one
	// Method Exception: getSuppressed() get a list of throwable object that represent the exception suppressed.
	public static void testAutocloseableResources() throws IOException, SQLException {
		System.out.println("\n\nTest Autoclosable Resources");
		
		// close resources without try-with-resources
		Reader rd1 = null;
		try {
			rd1 = new FileReader("test.txt");
		}
		catch (IOException e) {
			System.out.println("try without resources: " + e);
		}
		finally {
			if (rd1 != null) {
				try {
					rd1.close();
				}
				catch (IOException e) {
					System.out.println("close fail");
				}
			}
		}
		
		// try-with-resources with catch
		try (Reader rd2 = new FileReader("test.txt")) {
			
		}
		catch (IOException e) {
			System.out.println("try-with-resources with catch and finally: " + e);
		}
		
		// autocloseable class
		try (TestResourceCloseable test1 = new TestResourceCloseable("test 1");
		     TestResourceCloseable test2 = new TestResourceCloseable("test 2")) {
			System.out.println("Autocloseable: execute try");
			
			// you can do this in try-with-resources
			//test2 = new TestResourceCloseable("test 3");
		}
		
		// use suppressed exceptions
		try (TestResourceCloseable test1 = new TestResourceCloseable("exception order test 1");
			 TestResourceCloseable test2 = new TestResourceCloseable("exception order test 2")) {
			throw new SQLException("try throw exception");
		}
		catch (SQLException e) {
			System.out.println(e);
			for (Throwable t: e.getSuppressed()) {
				System.out.println(t);
			}
		}
		
		// close resources with try-with-resources
		// the method stop its execution when you not catch an exception
		try (Reader rd2 = new FileReader("test.txt");
			 Reader rd3 = new FileReader("test2.txt");
			 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/")) {
			
		}
		
	}
	
	public static void throwException() throws IOException, SQLException {
		throw new IOException("Test Exception 4 from method");
	}	
}
