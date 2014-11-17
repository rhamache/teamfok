import java.sql.*;
import java.util.*;


public class DatabaseController{
	private static final String Orc_Driver = "oracle.jdbc.driver.OracleDriver";
    private static final String Orc_URL = "jdbc:oracle:thin:@host:1521:database";
    private static final String Orc_Username = "hfok";
    private static final String Orc_Passwd = "43KBSQL5";
    
    
    public DatabaseController(){
    	Connection connection = null;
    	connection = establishConnection(Orc_Driver, Orc_URL, Orc_Username, Orc_Passwd);
    	DatabaseMetaData meta = connection.getMetaData();
    	System.out.println(meta.getDatabaseProductName());
        System.out.println(meta.getDatabaseProductVersion());
    }
    
    public static Connection establishConnection(String driver, String url,
    		String username, String passwd) throws ClassNotFoundException, SQLException{
    	
    	Class.forName(driver);
    	return DriverManager.getConnection(url, username, password);
    }
    
    public static endConnectioin(Connection connection){
    	if (connectoin != null){
    		connection.close();
    	}
    }
    
    
}