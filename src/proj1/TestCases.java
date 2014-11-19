package proj1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import usermanagement.LoginServlet;

public class TestCases {

	public static void main(String[] args) {
		
		int total_tests = 3, tests_passed = 0;
		
		tests_passed += TestCases.TestDBC();
		
		tests_passed += TestCases.TestLoginPost();
		
		tests_passed += TestCases.TestHomePageGet();

		
		System.out.println("Tests passed: "+tests_passed+"/"+total_tests);
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
	
	public static int TestLoginPost() {
		
		TestHttpServletRequest req = new TestHttpServletRequest();
		TestHttpServletResponse res = new TestHttpServletResponse();
		LoginServlet ls = new LoginServlet();
		
		req.setParameter("USERID", "test_username");
		req.setParameter("PASSWD", "test_password");
		try {
			ls.doPost(req, res);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(res.sw.getBuffer());
		
		
		return 1;
	}
	
	public static int TestHomePageGet() {
		TestHttpServletRequest req = new TestHttpServletRequest();
		TestHttpServletResponse res = new TestHttpServletResponse();
		HomePageServlet ls = new HomePageServlet();
		
		try
		{
			ls.doGet(req, res);
		} catch (ServletException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

}
