import java.beans.PropertyChangeEvent;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;


public class BusinessLayer
{
	private DataLayer dlayer;
	private UILayer ulayer;
	private SimulationManager manager;
    
	public DataLayer getDataLayer()
	{
		return dlayer;
	}
	
	public UILayer getUILayer()
	{
		return ulayer;
	}
	
	public void setDataLayer( DataLayer layer )
	{
		dlayer = layer;
	}
	
	public void setUILayer( UILayer layer )
	{
		ulayer = layer;
	}
	
	public SimulationManager startSimulation( SimulationConfig info)
	{
		manager = new SimulationManager(info); 
		manager.addPropertyChangeListener(new PropertyChangeListener());
		return manager;
	}
	
	public double getSimulationProgress()
	{
		return manager.getProgress();
	}
	
	protected class PropertyChangeListener implements java.beans.PropertyChangeListener
	{
		PropertyChangeListener() { }
		
		// Invoked when task's progress property changes.
		public void propertyChange(PropertyChangeEvent evt) 
		{
			if ("state".equals(evt.getPropertyName()) && evt.getNewValue() == SwingWorker.StateValue.DONE) 
			{
				try {
					
					for(SessionManager sm : manager.get())
					{
						dlayer.addSession(sm);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
