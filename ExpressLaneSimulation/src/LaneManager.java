import java.util.ArrayList;

public class LaneManager extends Thread
{
	protected ArrayList<Lane> lmlist;
	protected int expressLimit;
	private int lanes, expressLanes;
	private ArrayList<Customer> customers;
	
	public LaneManager(int expressLimit, int lanes, int expressLanes, ArrayList<Customer> customers)
	{
		lmlist = new ArrayList<Lane> ();
		this.expressLimit = expressLimit;
		this.lanes = lanes;
		this.expressLanes = expressLanes;
		this.customers = customers;
	}
	
	public void setLanes(int lanes, int expressLanes)
	{
		for(int i=0; i<lanes; i++)
		{
			if( i < expressLanes)	
				lmlist.add(new ExpressLane());
			else
				lmlist.add(new Lane());
		}
	}
	
	private void distributeCustomers(ArrayList<Customer> customers)
	{
		Lane l;
		int i = 0, j = 0;
		for( Customer c : customers )
		{
			if( c.getNumOfItems() <= expressLimit)
			{
				l = findLane(true);
				i++;
			}
			else
			{
				l = findLane(false);
				j++;
			}
			l.addCustomer(c);
		}
	}
	
	public void startLanes()
	{
		for(Lane l : lmlist)
		{
			l.start();
		}
	}
	
	private Lane findLane(boolean isExpress)
	{
		Lane line = null;
		
		for(Lane l : lmlist)
		{
			if(isExpress == true && l instanceof ExpressLane )
			{
				if(line == null || (l.getSize() < line.getSize()))
				{
					line = l;		
				}
			}
			else if(isExpress == false && !(l instanceof ExpressLane) )
			{
				if(line == null || (l.getSize() < line.getSize()))
				{
					line = l;		
				}
			}
		}
		
		return line;
	}
	
	@Override
	public void run()
	{	
		setLanes(lanes, expressLanes);
		distributeCustomers(customers);
		startLanes();
	}	
}
