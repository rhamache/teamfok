package proj1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

public class HTMLBuilder
{
	private StringWriter bundle;
	private PrintWriter pw;
	
	
	
	public HTMLBuilder(){
		bundle = new StringWriter();
		pw = new PrintWriter(bundle);
	}
	
	
	public void makeHeader(){
		pw.println("<!DOCTYPE html>" + "<html>");
	}
	
	public void makeHeader(String text){
		pw.println("<!DOCTYPE html>" + "<html><p>"+text+"</p>");
	}
	
	public void makeBody(String bodyText){
				this.makeHeader();
                pw.println("<body>" + bodyText + "</body>");
                this.makeFooter();
        }

	public void makeFooter(){
		pw.println("</html>");
	}
	
	public String grabBundle(){
		return bundle.getBuffer().toString();
	}
	
	public void buildFromFile(String path) throws IOException
	{
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = null;
		try {
		/*BufferedReader*/ reader = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) { pw.println(System.getProperty("user.dir")); return; }
		char[] buf = new char[1024];
		int bytes_read = 0;
		while ((bytes_read = reader.read(buf)) != -1) {
			String data = String.valueOf(buf, 0, bytes_read);
			fileData.append(data);
			buf = new char[1024];
		}
		
		reader.close();
		String str = fileData.toString();
		pw.print(str);
		
	}
	
	public void putInResponse(HttpServletResponse res) throws IOException
	{
		PrintWriter out = res.getWriter();
		out.println(bundle.getBuffer());
	}
}