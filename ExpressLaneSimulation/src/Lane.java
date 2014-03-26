import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;

public class Lane extends Thread
{
	private Queue<Customer> clist;
	private int progress;
	private LaneTimer t;
	
	public Lane()
	{
		clist = new LinkedList<Customer> ();	
		t = new LaneTimer();
		progress = 0;
	}
	
	public int getSize()
	{
		return clist.size();
	}
	
	public void addCustomer(Customer c)
	{
		clist.add(c);	
	}
	
	public void processCustomer()
	{
		Customer c = clist.poll();
		try 
		{
			Thread.sleep(c.getNumOfItems() * 200);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
			while(!clist.isEmpty())
			{
				t.start();
				processCustomer();
				t.stop();
				progress++;
			}
	}
	
	public int getProgress()
	{
		return progress;
	}
	
	public long getTime()
	{ 
		t.setAvgTime(progress);
		return t.getAvgTime();
	}
}
