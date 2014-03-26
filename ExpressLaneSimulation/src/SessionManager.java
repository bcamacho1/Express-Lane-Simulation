import java.util.ArrayList;

public class SessionManager extends LaneManager 
{
	private int progress;
	
	public SessionManager(int expressLimit, int lanes, int expressLanes,  ArrayList<Customer> customers) 
	{
		super(expressLimit, lanes, expressLanes, customers);
		progress = 0;
	}
	
	public int getProgress()
	{
		progress = 0;
		for(Lane l : lmlist)
		{
			progress += l.getProgress();
		}
		return progress;
	}
	
	public long getAvgLaneTime()
	{
		long t = 0;
		for(Lane l : lmlist)
		{
			t += l.getTime();
		}
		t = t / lmlist.size();
		
		return t;
	}
	
	public int getExpressLimit()
	{
		return expressLimit;
	}
}
