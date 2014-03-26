import java.util.ArrayList;
import java.util.TreeSet;

public class DataLayer 
{
	private TreeSet<SessionManager> slist;
	
	public DataLayer() 
	{
		slist = new TreeSet<SessionManager>(new SessionComparator());
	}
	
	public TreeSet<SessionManager> getSessions()
	{
		return slist;
	}
	
	public void addSession(SessionManager sm)
	{
		slist.add(sm);
	}
}
