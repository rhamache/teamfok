package dummy;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.*;

import javax.imageio.ImageIO;

import oracle.jdbc.OracleResultSet;
import oracle.sql.BLOB;

import proj1.DatabaseController;
import proj1.HTMLBuilder;

public class PopulateController extends DatabaseController{

    
	public PopulateController() throws SQLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		super();
	}
	
	public void main(String argv[]) throws SQLException, IOException{
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
		int photoCount = 1;
		int permit = 1;
		while (number <= 40){
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
			//////////////////////////////////////////////////////////
			String path = "D:/Henry/Documents/apache-tomcat-8.0.15/webapps/proj1/WEB-INF/src/pokemon/"+Integer.toString(photoCount)+".jpg";
			photoCount++;
			BufferedImage img = ImageIO.read(new File(path));
		    BufferedImage thumbNail = shrink(img);
		    
		    int PicID = getPID();
		    if (permit == 1){
		    	permit++;
		    } else if (permit == 2){
		    	permit--;
		    }
			
			String imageSql = "INSERT INTO images VALUES("+PicID+", '" +id+"', "+permit+", 'subject "+number+"', 'location "+number+"', sysdate, 'description "+ number+"',empty_blob(),empty_blob())";
			String que = "SELECT * FROM images WHERE photo_id = '"+PicID+"' FOR UPDATE";
			//String sql2 = "INSERT INTO hitcounts VALUES("+id+", 0)";
			ResultSet rset = null;

			stmt = conn.createStatement();
			stmt.executeUpdate(imageSql);
			//stmt.executeUpdate(sql2);
			stmt.executeUpdate("COMMIT");
			rset = stmt.executeQuery(que);
			rset.next();
			
			BLOB theblob = ((OracleResultSet)rset).getBLOB(8);

		    OutputStream outstream = theblob.getBinaryOutputStream();
		    try {
				ImageIO.write(thumbNail, "jpg", outstream);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		    theblob = ((OracleResultSet)rset).getBLOB(9);
		    
		  	outstream = theblob.getBinaryOutputStream();
		    try {
				ImageIO.write(img, "jpg", outstream);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    stmt.executeUpdate("COMMIT");
		    /////////////////////////////////////////////////////
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
	
	public static BufferedImage shrink(BufferedImage image) {

		BufferedImage bigtmb;
		int size, diff, wstart = 0, hstart = 0;
		if (image.getHeight() > image.getWidth())
		{
			size = image.getHeight();
			diff = size - image.getWidth();
			wstart = diff/2;
			bigtmb = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		} else
		{
			size = image.getWidth();
			diff = size - image.getHeight();
			hstart = diff/2;
			bigtmb = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		}
		
		int i,j;
		for (i = 0; i < bigtmb.getHeight(); i++)
			for(j = 0; j < bigtmb.getWidth(); j++)
				bigtmb.setRGB(j, i, 0);
		
		int _w = 0, _h = 0;
		for (int w=wstart; w < size; ++w)
		{
			_h = 0;
			for (int h=hstart; h < size; ++h)
			{
				if (_w < image.getWidth() && _h < image.getHeight())
					bigtmb.setRGB(w, h, image.getRGB(_w, _h));
				_h++;
			}
			_w++;
		}
		
		BufferedImage thumbnail = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
		Image scaled = bigtmb.getScaledInstance(300, 300, Image.SCALE_FAST);
		thumbnail.createGraphics().drawImage(scaled, 0, 0, null);
		
		return thumbnail;
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
	
	public int getPID() throws SQLException{
		ResultSet rset1 = executeSQLTextStatement("SELECT pic_id_seq.nextval FROM dual");
        rset1.next();
        int id = rset1.getInt(1);
		return id;
	}

}
