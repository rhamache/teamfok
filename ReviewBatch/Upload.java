import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;


import javax.servlet.*;
import javax.servlet.http.*;

import oracle.sql.BLOB;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import security.SecurityModule;

public class Upload extends HttpServlet
{
	public String response_message;
	public ArrayList<String> infoBundle = new ArrayList<String>();
	public String subject = ""; 
	public String place = "";
	public String description = "";
	public String privacy = "";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException
		      {
				  // Display the upload page
				  if (!SecurityModule.isLoggedIn(request.getSession())) {
					  RequestDispatcher view = request.getRequestDispatcher("/html/login.html");
					  view.forward(request, response);
				  } else {
					  PrintWriter out = response.getWriter();
					  RequestDispatcher view = request.getRequestDispatcher("/html/upload.html");
				  }
		      }
    
	public void doPost(HttpServletRequest request,HttpServletResponse response)
		throws ServletException, IOException {
		
	//  change the following parameters to connect to the oracle database
		HttpSession session = request.getSession();
		String username = session.getAttribute("username").toString();
		infoBundle.add(username);
		subject = (String)request.getParameter('SUB');
		place = (String)request.getParameter('PLACE');
		description = (String)request.getParameter('DES');
		privacy = (String)request.getParameter('PRIBOX');
		infoBundle.add(privacy);
		infoBundle.add(subject);
		infoBundle.add(place);
		infoBundle.add(description);
		

		int pic_id;

		UploadController udbc = new UploadController();

		try {
			//Parse the HTTP request to get the image stream
			DiskFileUpload fu = new DiskFileUpload();
			List FileItems = fu.parseRequest(request);
	    
			// Process the uploaded items, assuming only 1 image file uploaded
			Iterator i = FileItems.iterator();
			FileItem item = (FileItem) i.next();
			while (i.hasNext() && item.isFormField()) {
				item = (FileItem) i.next();
			}

			//Get the image stream
			InputStream instream = item.getInputStream();
	    
            	// Connect to the database and create a statement
            //Connection conn = getConnection();
            //.Statement stmt = conn.createStatement();

            /*
             *  First, to generate a unique pic_id using an SQL sequence
             */
            //ResultSet rset1 = ULC.executeSQLTextStatement("SELECT pic_id_sequence.nextval from dual");
            //rset1.next();
            pic_id = udbc.getPicId();

            //Insert an empty blob into the table first. Note that you have to 
            //use the Oracle specific function empty_blob() to create an empty blob
            //stmt.execute("INSERT INTO pictures VALUES("+pic_id+",'test',empty_blob())");
 
            // to retrieve the lob_locator 
            // Note that you must use "FOR UPDATE" in the select statement
            //String cmd = "SELECT * FROM pictures WHERE pic_id = "+pic_id+" FOR UPDATE";
            //ResultSet rset = ULC.executeSQLTextStatement(cmd);
            //rset.next();
            
            udbc.writeBlob(pic_id, infoBundle, instream);

            //Write the image to the blob object
           //OutputStream outstream = myblob.getBinaryOutputStream();
            //int size = myblob.getBufferSize();
            //byte[] buffer = new byte[size];
            //int length = -1;
            //while ((length = instream.read(buffer)) != -1)
            //	outstream.write(buffer, 0, length);
            
	    	instream.close();
	    	//outstream.close();
	    	
	    	udbc.executeSQLTextStatement("commit");
            response_message = " Upload OK!  ";
            udbc.close();

		} catch( Exception ex ) {
			//System.out.println( ex.getMessage());
			response_message = ex.getMessage();
		}

		//Output response to the client
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HTMLBuilder html = new HTMLBuilder();
		html.makeBody(response_message);
		
		}
}

