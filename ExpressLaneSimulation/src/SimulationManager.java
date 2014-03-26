import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.SwingWorker;

public class SimulationManager extends SwingWorker<ArrayList<SessionManager>, Void>
{
	private SimulationConfig info;
	private ArrayList<SessionManager> slist;
	private Integer progress;
	private int totalProgress;
	private ArrayList<Customer> clist;
	
	public SimulationManager(SimulationConfig data) 
	{
		this.slist = new ArrayList<SessionManager> ();
		this.info = data;
		this.progress = 0;
		totalProgress = (info.getMaxExpressLimit() - info.getMinExpressLimit() + 1) * info.getTotalCustomers();
		loadCustomers(info.getTotalCustomers());
	}
	
	private void loadCustomers(int total)
	{
		clist = new ArrayList<Customer> ();
		for(int i = 0; i<total; i++)
		{
			clist.add(new Customer(info.getMaxItems()));
		}
	}
	
	public void runSimulation()
	{
		while(getProgress() < 100)
		{
			try 
			{
				Thread.sleep(200);
				progress ++;
				System.out.println(getProgress());
			} 
			catch (InterruptedException ignore) 
			{
			}
		}
	}
	
	public int getSimulationProgress()
	{
		progress = 0;
		for(SessionManager s : slist)
		{
			progress += s.getProgress();
		}
		
		return (int)( ( progress / (totalProgress * 1.0) ) * 100 ) ;
	}
	
	public ArrayList<SessionManager> doInBackground() 
	{
		int progress = 0;
		// Initialize progress property.
		setProgress(0);
		SessionManager sm;
		for(int i = info.getMinExpressLimit(); i <= info.getMaxExpressLimit(); i++)
		{
			sm = new SessionManager(i, info.getTotalLanes(), info.getExpressLanes(), clist);
			slist.add(sm);
			sm.start();
		}
		
		while (progress < 100) 
		{
			try 
			{
				Thread.sleep(500);
			} 
			catch (InterruptedException ignore) 
			{
			}
			
			progress = getSimulationProgress();
			setProgress(Math.min(progress, 100));
		}
		return slist;
	}
}
