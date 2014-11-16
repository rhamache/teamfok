
public class HTMLBuilder
{
	public String bundle = "";
	
	
	public HTMLBuilder(){
	}
	
	
	public void makeHeader(String headerText){
		bundle += "<!DOCTYPE html>\n" + "<html>\n" + "<header>";
		bundle += "</header>";
	}
	
	public void makeFooter(String footerText){
		bundle += "<footer>\n";
		bundle += "</footer>\n" + "</html>";
	}
	
	public String grabBundle(){
		return bundle;
	}
}