package security;
import javax.servlet.http.HttpSession;



public class SecurityModule
{
	/* returns true is a user is logged in, false if not */
	public static boolean isLoggedIn(HttpSession sesh)
	{
		  if (sesh.getAttribute("username") == null) {
			  return false;
		  } else {
			  return true;
		  }
	}
	
	public static boolean isMemberOf(HttpSession sesh, GroupType gt)
	{
		return false;
		
	}
}
