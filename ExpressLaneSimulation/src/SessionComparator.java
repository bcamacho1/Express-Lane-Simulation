
import java.util.Comparator;

public class SessionComparator implements Comparator<Object>
{
	public int compare( Object a, Object b)
	{
		SessionManager v1 = (SessionManager)a;
		SessionManager v2 = (SessionManager)b;
	    if(v1.getExpressLimit() < v2.getExpressLimit())
	    {
	    	return -1;
	    }
	    else if(v1.getExpressLimit() == v2.getExpressLimit())
	    {
	    	return 0;
	    }
	    else
	    {
	    	return 1;
	    }
	} 
}
