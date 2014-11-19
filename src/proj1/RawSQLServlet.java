package proj1;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

public class RawSQLServlet extends HttpServlet
{
	  public void doGet(HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException
	  {
		  response.setContentType("text/html");
		  // Display the login page
		  RequestDispatcher view = request.getRequestDispatcher("/html/sql_input.html");
		  view.forward(request, response);
	  }
	  
	  public void doPost(HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException
	  {
		  PrintWriter out = response.getWriter();
		  DatabaseController dbc = null;
		  ResultSet rset = null;
		  
		  try {
			  dbc = new DatabaseController();
		  } catch (Exception e){
			  out.println(e.getMessage());
			  return;
		  } 

		  String query = request.getParameter("query");
		  try {
			  rset = dbc.executeSQLTextStatement(query);
		  } catch (SQLException e) {
			  out.println(e.getMessage());
		  }
		  
		  DatabaseController.displayResultSet(out, rset);
	  }
}


