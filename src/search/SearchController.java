package search;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import proj1.DatabaseController;
import javax.servlet.*;




public class SearchController extends DatabaseController
{

	public SearchController() throws SQLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException
	{

		super();
		// TODO Auto-generated constructor stub
	}

	

	public ResultSet keywordsearch(String keywords, String timebias, String timespace) throws SQLException
	{
		String[] keywordarr = keywords.split(" ");
		String[] photoargs  = {"photo_id","owner_name","subject","place","description"};
		ArrayList<String> photoIDarr =  new ArrayList<String>();

		for (String keyword : keywordarr)
		{
			for (String arg : photoargs)
			{	
				String query = "select photo_ID from PHOTOS where '"+arg+"' LIKE '"+keyword+"'";
				Statement stmt = null; ResultSet rset = null;
	        	stmt = conn.createStatement();
	        	rset = stmt.executeQuery(query);
	      
	        	while (rset.next()) 
	        	         photoIDarr.add(rset.getString(1));
	        	
			}
		}
			
		
		// TODO Auto-generated method stub
		return null;
	}
}
