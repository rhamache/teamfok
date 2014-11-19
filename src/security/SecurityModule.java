package security;
import javax.servlet.http.HttpSession;



public class SecurityModule
{
	public static boolean isLoggedIn(HttpSession sesh)
	{
		  if (sesh.getAttribute("username") == null) {
			  return false;
		  } else {
			  return true;
		  }
	}
}
