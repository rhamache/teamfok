package proj1;

import java.io.PrintWriter;
import java.io.StringWriter;

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
}