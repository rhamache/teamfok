import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class LoginController extends DatabaseController
{

	public LoginController() throws SQLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException
	{
		super();
	}
	
	public boolean checkCredentials(String username, String password) throws SQLException {
		String query = "select PASSWORD from USERS where USER_NAME = '"+username+"'";
		Statement stmt = null; ResultSet rset = null;
		
        stmt = conn.createStatement();
	    rset = stmt.executeQuery(query);
    	
        String truepwd = "";
    	
    	while(rset != null && rset.next()) {
        	truepwd = (rset.getString(1)).trim();
    	}

        if(password.equals(truepwd))
	        return true;
    	else
        	return false;
		
	}

}
