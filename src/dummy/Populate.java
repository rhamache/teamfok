package dummy;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import proj1.DatabaseController;
import proj1.HTMLBuilder;

public class Populate extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
			HTMLBuilder html = new HTMLBuilder();
			html.makeHeader();
			html.makeMenu(true);
			html.appendHTML("<form name=\"upload-image\" method=\"POST\" enctype=\"multipart/form-data\" id=\"info\">");
			html.appendHTML("<input type=\"submit\" name=\".submit\"value=\"Populate\"></td>");
			html.appendHTML("</form>");
			html.makeFooter();
			html.putInResponse(response);
			}
	
	
	public void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
			try {
				PopulateController pdbc = new PopulateController();
				//pdbc.populate();
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		
}

	
