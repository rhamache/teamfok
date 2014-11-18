import javax.servlet.http.*;

public class TestCases {

	public static void main(String[] args) {
		
		int total_tests = 0, tests_passed = 0;
		
		tests_passed += TestCases.TestDBC();

	}
	
	public static int TestDBC() {
		try {
			DatabaseController dbc = new DatabaseController();
		}
		catch (Exception e)
		{
			// fail test
			e.printStackTrace();
			System.out.println("Exception: " + e.getMessage());
			System.out.println("testDBC(): test failed.");
			return 0;
		}
		
		
		System.out.println("testDBC(): test passed!");
		return 1;
		
	}

}
