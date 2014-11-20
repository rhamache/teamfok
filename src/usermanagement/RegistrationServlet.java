package usermanagement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import proj1.HTMLBuilder;

import security.SecurityModule;



public class RegistrationServlet extends HttpServlet
{
	  public void doGet(HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException
	  {
		  // Display the login page
		  if (!SecurityModule.isLoggedIn(request.getSession())) {
			  RequestDispatcher view = request.getRequestDispatcher("/html/registration.html");
			  view.forward(request, response);
		  } else {
			  PrintWriter out = response.getWriter();
			  out.println("<h2>You are already logged in, "+request.getSession().getAttribute("username")+"</h2>");
			  out.println("<p><a href = \"/html/logout.html\">Logout</a> before creating a new account.</p>");
		  }
	  }
	  
	  public void doPost(HttpServletRequest request, HttpServletResponse response)
	  throws IOException 
	  {
		  ArrayList<String> fields = new ArrayList<String>(8);
		  
		  // grab all fields
		  fields.add(request.getParameter("USERID"));
		  fields.add(request.getParameter("PASSWD"));
		  fields.add(request.getParameter("RP_PASSWD"));
		  fields.add(request.getParameter("FNAME"));
		  fields.add(request.getParameter("LNAME"));
		  fields.add(request.getParameter("ADDRESS"));
		  fields.add(request.getParameter("EMAIL"));
		  fields.add(request.getParameter("PHONE"));
		  
		  PrintWriter out = response.getWriter();
		  RegistrationController rc =  null;
		  try
		  {
			  rc = new RegistrationController();
		  } catch (Exception e)
		  {
			  out.println(e.getMessage());
			  return;
		  }
		  
		  boolean isValid;
		  try
		  {
			  isValid = rc.clean_fields(fields);
		  } catch (SQLException e)
		  {
			  out.println(e.getMessage());
			  return;
		  }
		  
		  HttpSession session = request.getSession(true);
		  
		  if (isValid)
		  {
			  try
			{
				rc.addPersonAndUser(fields);
			} catch (SQLException e)
			{
				out.println(e.getMessage());
				return;
			}
		      session.setAttribute("username", fields.get(0));
		      response.sendRedirect("home");
		  } else {
			  ServletContext context = getServletContext();
			  String path = context.getRealPath("/html/registration.html");
			  HTMLBuilder html = new HTMLBuilder();
			  html.makeHeader(rc.getFieldError());
			  html.buildFromFile(path);
			  html.putInResponse(response);
		  }


		  
	  }
}
