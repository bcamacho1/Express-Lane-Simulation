/* Driver class for the line simulation in the store program 
   that purpose is to determine the optimum limit of products 
   in the basket for the express lines */

import javax.swing.JFrame;

public class Driver extends JFrame
{
	UILayer uiLayer;
	BusinessLayer businessLayer;
	DataLayer dataLayer;

	// Initialize ui, business and data layers
	public void init()
	{
		uiLayer = new UILayer(getContentPane());
		businessLayer = new BusinessLayer();
		dataLayer = new DataLayer();
		businessLayer.setDataLayer(dataLayer);
		businessLayer.setUILayer(uiLayer);
		uiLayer.setDataLayer(dataLayer);
		uiLayer.setBusinessLayer(businessLayer);
	}
	
	// Add controls to ui layer 
	public void start()
	{
		uiLayer.addControls();
	}
	
	// Set up title, initialize layers, use methods included in the pack from awt library
	// set to visible and start JFrame 
	public Driver( String title )
	{
		super(title);
		init();
		pack();
		setVisible(true);
		start();	
	}
	
	// Run console for the line simulation in the store
	public static void main(String [ ] args)
	{	
		javax.swing.SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() 
			{
				Driver driver = new Driver("Line Simulation in the store");
				Console.run(driver, 500, 500);
			}
		});
	}
}
