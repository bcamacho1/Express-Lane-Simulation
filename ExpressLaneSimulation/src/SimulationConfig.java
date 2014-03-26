
public class SimulationConfig
{
	private int totalLanes;
	private int expressLanes;
	private int minExpressLimit;
	private int maxExpressLimit;
	private int totalCustomers;
	private int maxItems;
	
	public SimulationConfig() { }
	
	public void setExpressRange(int minExpressLimit, int maxExpressLimit)
	{
		this.minExpressLimit = minExpressLimit;
		this.maxExpressLimit = maxExpressLimit;
	}
	
	public void setLanesNum(int totalLanes, int expressLanes)
	{
		this.totalLanes = totalLanes;
		this.expressLanes = expressLanes;
	}
	
	public void setCustomerRestriction(int totalCustomers, int maxItems)
	{
		this.totalCustomers = totalCustomers;
		this.maxItems = maxItems;
	}
	
	public int getTotalLanes()
	{
		return totalLanes;
	}
	
	public int getExpressLanes()
	{
		return expressLanes;
	}
	
	public int getMinExpressLimit()
	{
		return minExpressLimit;
	}
	
	public int getMaxExpressLimit()
	{
		return maxExpressLimit;
	}
	
	public int getTotalCustomers()
	{
		return totalCustomers;
	}
	
	public int getMaxItems()
	{
		return maxItems;
	}
}
