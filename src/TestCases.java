import javax.servlet.http.*;

public class TestCases {

	public static void main(String[] args) {
		
		TestCases.TestDBC();

	}
	
	public static void TestDBC() {
		try {
			DatabaseController dbc = new DatabaseController();
		}
		catch (Exception e)
		{
			// fail test
			e.printStackTrace();
			System.out.println("Exception: " + e.getMessage());
			System.out.println("testDBC(): test failed.");
			return;
		}
		
		
	}

}
