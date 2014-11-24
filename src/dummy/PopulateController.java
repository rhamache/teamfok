package dummy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import proj1.DatabaseController;
import proj1.HTMLBuilder;

public class PopulateController extends DatabaseController{
	
	public PopulateController() throws SQLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		super();
	}

	public void main(String[] args) throws SQLException{
		int number = 0;
		while (number <= 20){
			String id = "test" + number;
			String num = Integer.toString(number);
			String passwd = ""+id.hashCode();
			String phoneNo = generateMyNumber();
			String userSql = "INSERT INTO users VALUES('"+id+"','"+passwd+"', sysdate)";
			String peronsSql = "INSERT INTO persons VALUES('"+id+"', 'test','"+num+"','testing grounds "+num+"', 'tester"+num+"@dummy.com','"+phoneNo+"')";
			Statement stmt = null;
			stmt = conn.createStatement();
			stmt.execute(userSql); 
			stmt.executeUpdate("COMMIT");
			stmt = null;
			stmt = conn.createStatement();
			stmt.execute(peronsSql);
			stmt.executeUpdate("COMMIT");
			number++;
		}
		int numberGroups = 0;
		String query = "SELECT group_id_seq.nextval FROM dual";
		while (numberGroups <= 20){
			String id = "test" + numberGroups;
			int groupID = 0;
			Statement stmt = null; ResultSet rset = null;
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			while (rset != null && rset.next()) {
				groupID = rset.getInt(1);
			}
			String groupSql = "INSERT INTO groups VALUES("+groupID+",'"+id+"','group"+numberGroups+"', sysdate)";
			stmt = conn.createStatement();
			stmt.execute(groupSql);
			stmt.executeUpdate("COMMIT");
			String gListSql = "INSERT INTO group_lists VALUES("+groupID+",'"+id+"', sysdate, 'Group Creator')";
			stmt.execute(gListSql);
			stmt.executeUpdate("COMMIT");
			numberGroups++;
		}
		
		ArrayList<Integer> groupID = gatherGID();
		
		while (number <= 50){
			String id = "test" + number;
			String num = Integer.toString(number);
			String passwd = ""+id.hashCode();
			String phoneNo = generateMyNumber();
			String userSql = "INSERT INTO users VALUES('"+id+"','"+passwd+"', sysdate)";
			String peronsSql = "INSERT INTO persons VALUES('"+id+"', 'test','"+num+"','testing grounds "+num+"', 'tester"+num+"@dummy.com','"+phoneNo+"')";
			Statement stmt = null;
			stmt = conn.createStatement();
			stmt.execute(userSql); 
			stmt.executeUpdate("COMMIT");
			stmt = null;
			stmt = conn.createStatement();
			stmt.execute(peronsSql);
			stmt.executeUpdate("COMMIT");

			int myGID = 1;
			while (myGID == 1 || myGID == 2){
				myGID = groupID.get((int) (Math.random()*groupID.size()));
			}
			
			String gListAddSql = "INSERT INTO group_lists VALUES("+myGID+",'"+id+"', sysdate, 'New')";
			stmt = conn.createStatement();
			stmt.execute(gListAddSql);
			stmt.executeUpdate("COMMIT");
			
			number++;
			}
				
		}
	
	public ArrayList<Integer> gatherGID() throws SQLException{
		Statement stmt = null; ResultSet rset = null;
		stmt = conn.createStatement();
		ArrayList<Integer> groupsInvolved = new ArrayList<Integer>();
		rset = stmt.executeQuery("select group_id from groups");
    	while(rset != null && rset.next()) {
    		groupsInvolved.add((rset.getInt(1)));
    	}
    	return groupsInvolved;
	}
	
	
	
	public static String generateMyNumber()
	{
		int aNumber = 0; 
		int bNumber = 0;
		aNumber = (int)(Math.round((Math.random() * 89999)+10000)); 
		bNumber = (int)(Math.round((Math.random() * 89999)+10000)); 
		String phone = Integer.toString(aNumber) + Integer.toString(bNumber);
		return phone;
	}

}
