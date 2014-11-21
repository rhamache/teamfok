import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import oracle.jdbc.OracleResultSet;
import oracle.sql.BLOB;

public class UploadController extends DatabaseController
{

	public UploadController() throws SQLException, ClassNotFoundException,
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
	
	public int getPicId() throws SQLException{
		ResultSet rset1 = executeSQLTextStatement("SELECT pic_id_sequence.nextval from dual");
        rset1.next();
        int id = rset1.getInt(1);
		return id;
	}
	
	public writeBlob(int id, ArrayList<String> bundle, InputStream instr) throws SQLException{
		//*** Inserting with empty blobs
		Date date = new Date();
		String today = new SimpleDateFormat("yyyy/MM/dd").format(date);
		String assembleQuery = "insert into images values("+id+"," + bundle.get(0)+","+Integer.parseInt(bundle.get(1))+","+bundle.get(2)+","+bundle.get(3)+","+today+","+bundle.get(4)+",empty_blob(),empty_blob());";
		this.executeSQLTextStatement(assembleQuery);
		String cmd = "SELECT * FROM pictures WHERE pic_id = "+id+" FOR UPDATE";
		ResultSet rset = executeSQLTextStatement(cmd);
		rset.next();
		//*** Start Write image sequence
		BLOB image = ((OracleResultSet)rset).getBLOB(9);
		OutputStream outstr = image.getBinaryOutputStream();
		int size = image.getBufferSize();
		byte[] buffer = new byte[size];
        int length = -1;
        while ((length = instr.read(buffer)) != -1)
        	outstr.write(buffer, 0, length);
        outstr.close();
        //*** Start write thumb nail sequence
        BLOB smallImg = ((OracleResultSet)rset).getBLOB(8);
        OutputStream outstr2 = image.getBinaryOutputStream();
        BufferedImage thumb = ImageIO.read(instr);
        BufferedImage thumbNail = shrink(thumb, 10);
        ImageIO.write(thumbNail, "jpg", outstr2);
        outstr.close();
        
		return;
	}

	
	public static BufferedImage shrink(BufferedImage image, int n) {

        int w = image.getWidth() / n;
        int h = image.getHeight() / n;

        BufferedImage shrunkImage =
            new BufferedImage(w, h, image.getType());

        for (int y=0; y < h; ++y)
            for (int x=0; x < w; ++x)
                shrunkImage.setRGB(x, y, image.getRGB(x*n, y*n));

        return shrunkImage;
    }

}





