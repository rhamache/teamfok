import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet
{
	  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
      {
		  // Display the login page
		  RequestDispatcher view = request.getRequestDispatcher("/html/login.html");
		  view.forward(request, response);
      }
	  
	  public void doPost(HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException
	  {
		  String username = request.getParameter("USERID");
		  String password = request.getParameter("PASSWD");
		  PrintWriter out = response.getWriter();
		  
	      // Set response content type
	      response.setContentType("text/html");
	      
	      try {
	    	  LoginController dbc = new LoginController();
	      } catch (Exception e) {
	    	  out.println(e.getMessage());
	    	  return;
	      }
	      
	      out.println("<h2>Username:"+username+"</h2>");
	      out.println("<h2>Password:"+password+"</h2>");
	  }
}
