package proj1;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;


public class DatabaseController{
	/* Oracle credentials */
	private final String Orc_Driver = "oracle.jdbc.driver.OracleDriver";
    private final String Orc_URL = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
    private final String Orc_Username = "hfok";
    private final String Orc_Passwd = "43KBSQL5";
    
    protected Connection conn;
    
    public DatabaseController() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException{
    	this.conn = null;
    	Class drvClass = Class.forName(Orc_Driver); 
    	DriverManager.registerDriver((Driver) drvClass.newInstance());
    	conn = DriverManager.getConnection(Orc_URL, Orc_Username,Orc_Passwd);
    	conn.setAutoCommit(false);
    }

    
    public void close() throws SQLException {
    	if (this.conn != null){
				conn.close();
    	}
    }
    
    public ResultSet executeSQLTextStatement(String statement) throws SQLException {
    	Statement stmt = null;
    	ResultSet rset = null;

	    stmt = conn.createStatement();
	    rset = stmt.executeQuery(statement);
	    
		return rset;
    }
    
    public static void displayResultSet( PrintWriter out, ResultSet rset ) {

		out.println("<table border = 1 alian>");
		String value = null;
		Object o = null;
		int type;
		Blob image;
	
		/* 
		 *  to generate the column labels
		 */
		try {
	
		    ResultSetMetaData rsetMetaData = rset.getMetaData();
		    int columnCount = rsetMetaData.getColumnCount();
	
		    out.println("<tr valign = \"top\">");
	
		    for ( int column = 1; column <= columnCount; column++) {
			value = rsetMetaData.getColumnLabel(column);
			out.print("<td>" + value + "</td>");
		    }
		    out.println("</tr>");
	
		    /*
		     *   generate answers, one tuple at a time
		     */
		    while (rset.next() ) {
			out.println("<tr valign = \"top\">");
			for ( int index = 1; index <= columnCount; index++) {
			    type= rsetMetaData.getColumnType(index);
	
			    if (type==Types.LONGVARBINARY||
				type==Types.BLOB||type==Types.CLOB) {
	
				out.println("<img src=\"/yuan/servlet/GetOnePic\"></a>");
				/*
				image= rset.getBlob(index);
				rese.setContentType("image/gif");
				InputStream input = rset.getBinaryStream(index);
				int imageByte;
				while((imageByte = input.read()) != -1) {
				    out.write(imageByte);
				}
				input.close();
				*/
	
			    }
			    else {
				o = rset.getObject(index);
				if (o != null )
				    value = o.toString();
				else 
				    value = "null";
				out.print("<td>" + value + "</td>");
			    }
			}
			out.println("</tr>");
		    }
		} catch ( Exception io ){ out.println(io.getMessage()); }
	
		out.println("</table>");
    }
    
}