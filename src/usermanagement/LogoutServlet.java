package usermanagement;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import proj1.HTMLBuilder;

import security.SecurityModule;


public class LogoutServlet extends HttpServlet
{
	  public void doGet(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException
		      {
		  			HTMLBuilder html = new HTMLBuilder();
		  			if(SecurityModule.isLoggedIn(request.getSession()))
		  			{
		  				request.getSession().removeAttribute("username");
		  				html.makeBody("You have successfully logged out.");
		  			} else {
		  				html.makeBody("Error: you are not logged in!");
		  			}
		  			
		  			html.putInResponse(response);
		      }
}
