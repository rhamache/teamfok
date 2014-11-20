package search;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

import security.SecurityModule;
import search.SearchController;

public class SearchServlet extends HttpServlet
{
	  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
      {	  
		  PrintWriter out = response.getWriter();
		  if (!SecurityModule.isLoggedIn(request.getSession())) 
		  {
			  out.println("<h2>You are not logged in</h2>");
			  return;
		  }
		  else
		  {
			  RequestDispatcher view = request.getRequestDispatcher("/html/sql_input.html");
			  view.forward(request, response);
		  }
		  
      }
	  public void doPost(HttpServletRequest request, HttpServletResponse response)
			  throws ServletException, IOException
			  {
		  PrintWriter out = response.getWriter();
		  String keywords = request.getParameter("Keywords");
		  String mostrecent = request.getParameter("Most-Recent");
		  String leastrecent = request.getParameter("Least-Recent");
		  String neither = request.getParameter("Neither");
		  String from = request.getParameter("From");
		  String to = request.getParameter("To");
		  String timespace= from + '/' +to;
		  String timebias = "";
		  
		  if ((mostrecent != "") || (mostrecent != null))
				  timebias = mostrecent;
		  else
			  if ((leastrecent != "") || (leastrecent != null))
			  		timebias = leastrecent;
			  else
				     timebias = neither;
		  
		  response.setContentType("text/html");
	      
	      SearchController sbc = null;
	      try {
	    	  sbc = new SearchController();
	      } catch (Exception e) {
	    	  out.println(e.getMessage());
	    	  return;
	      }
	      HttpSession session = request.getSession(true);
	      ResultSet results = null;
	      try
	      {
	    	results = sbc.keywordsearch(keywords, timebias, timespace);
	      } catch (SQLException e)
	      {
			out.println("Exception: "+e.getMessage());
			return;
	      }
	      
			  }
			  	
		 }
		 
		