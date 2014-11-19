package usermanagement;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

import security.SecurityModule;


public class LoginServlet extends HttpServlet
{
	  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
      {
		  // Display the login page
		  if (!SecurityModule.isLoggedIn(request.getSession())) {
			  RequestDispatcher view = request.getRequestDispatcher("/html/login.html");
			  view.forward(request, response);
		  } else {
			  PrintWriter out = response.getWriter();
			  out.println("<h2>You are already logged in, "+request.getSession().getAttribute("username")+"</h2>");
			  out.println("<p>Want to logout? Click <a href = \"/html/logout.html\">here.</a></p>");
		  }
      }
	  
	  public void doPost(HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException
	  {
		  String username = request.getParameter("USERID");
		  String password = request.getParameter("PASSWD");
		  PrintWriter out = response.getWriter();
		  
		  
	      // Set response content type
	      response.setContentType("text/html");
	      
	      LoginController ldbc = null;
	      try {
	    	  ldbc = new LoginController();
	      } catch (Exception e) {
	    	  out.println(e.getMessage());
	    	  return;
	      }
	      
	      HttpSession session = request.getSession(true);

	      boolean good_credentials = false;
	      try
	      {
	    	  good_credentials = ldbc.checkCredentials(username, password);
	      } catch (SQLException e)
	      {
			out.println("Exception: "+e.getMessage());
			return;
	      }
	      
	      if (good_credentials)
	      {
	    	  if (session == null) { System.out.println("null"); }
		      session.setAttribute("username", username);
		      response.sendRedirect("home");
	      } else {
		      out.println("<h2>Incorrect login information. <a href = \"login.html\">Try Again.</a></h2>");
	      }
	      
	      try
	      {
	    	  ldbc.close();
	      } catch (SQLException e)
	      {
	    	  // TODO Auto-generated catch block
	    	  out.println("Exception: "+e.getMessage());
	      }
	  }
}
