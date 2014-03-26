import java.util.ArrayList;

public class LaneTimer 
{
	private long startValue;
	private long stopValue;
	private long timeDiff;
	private long avgTime;
	private ArrayList<Long> timings; 
	
	public LaneTimer() 
	{
		startValue = 0;
		stopValue = 0;
		timeDiff = 0;
		avgTime = 0;
		timings = new ArrayList<Long> ();
	}
	
	public void start() 
	{
		startValue = System.currentTimeMillis();		
		stopValue = 0;
	}

	public void stop() 
	{
		stopValue = System.currentTimeMillis();
		timeDiff += stopValue - startValue;
		timings.add(timeDiff);
		startValue = 0;
		stopValue = 0;
	}
	
	public void reset() 
	{
		startValue = 0;
		stopValue = 0;
		timeDiff = 0;
	}
	
	public long getTime() 
	{
		return timeDiff;
	}
	
	public void setAvgTime(int parts)
	{
		avgTime = 0;
		
		if(parts !=0)
		{
			for(Long t : timings)
			{
				avgTime += t;
			}
			
			avgTime = avgTime / parts;
		}
		else
		{
			avgTime = 0;
		}
	}
	
	public long getAvgTime() 
	{
		return avgTime;
	}
}
