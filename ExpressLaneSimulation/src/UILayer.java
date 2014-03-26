import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class UILayer 
{
	private DataLayer dlayer;
	private BusinessLayer blayer;
	private Container c1;
	private JTextField text1, text2, text3, text4, text5, text6;
	private JProgressBar progressBar;
	private SimulationManager task;
	private JTextArea taskOutput;
	private JPanel graphPanel;
	
	public UILayer(Container c)
	{
		c1 = c;
	}
	
	public DataLayer getDataLayer()
	{
		return dlayer;
	}
	
	public BusinessLayer getBusinessLayer()
	{
		return blayer;
	} 
	
	public void setDataLayer(DataLayer layer)
	{
		dlayer = layer;
	}
	
	public void setBusinessLayer(BusinessLayer layer)
	{
		blayer = layer;
	}	
	
	// Set layout for container and initialize input data panel
	public void addControls() 
	{
		c1.setLayout(new CardLayout());
		initInputDataPanel();
	}
	
	// Initialize input data panel taking into account a given variety of 
	// environmental conditions and add to the container as a main panel 
	public void initInputDataPanel()
	{
		JPanel mainPanel, centerPanel, buttonPanel;
		JLabel label, label1, label2, label3, label4, label5, label6;
		JButton exitButton, enterButton;
		
		mainPanel = new JPanel();
		centerPanel = new JPanel();
		buttonPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		centerPanel.setLayout(new GridLayout(13,1));
		buttonPanel.setLayout(new GridLayout(1,3));
		mainPanel.setBackground(Color.BLACK);
		centerPanel.setBackground(Color.CYAN);
		buttonPanel.setBackground(Color.BLACK);
		label = new JLabel("");
		label1 = new JLabel("Enter total number of lanes");
		label2 = new JLabel("Enter the number of express lanes");
		label3 = new JLabel("Enter the beginning express line item limit range");
		label4 = new JLabel("Enter the ending express line item limit range");
		label5 = new JLabel("Enter the total number of customers to test at each limit");
		label6 = new JLabel("Enter the maximum number of items that can be in a customer's cart");
		text1 = new JTextField(10); 
		text2 = new JTextField(10); 
		text3 = new JTextField(10); 
		text4 = new JTextField(10); 
		text5 = new JTextField(10); 
		text6 = new JTextField(10); 
		text1.addActionListener(new TextClickListener(text1));
		text2.addActionListener(new TextClickListener(text2));
		text3.addActionListener(new TextClickListener(text3));
		text4.addActionListener(new TextClickListener(text4));
		text5.addActionListener(new TextClickListener(text5));
		text6.addActionListener(new TextClickListener(text6));
		exitButton = new JButton("Exit");
		enterButton = new JButton("Enter");
		exitButton.addActionListener(new ExitClickListener());
		enterButton.addActionListener(new EnterClickListener());
		buttonPanel.add(label);
		buttonPanel.add(exitButton);
		buttonPanel.add(enterButton);
		centerPanel.add(label1);
		centerPanel.add(text1);
		centerPanel.add(label2);
		centerPanel.add(text2);
		centerPanel.add(label3);
		centerPanel.add(text3);
		centerPanel.add(label4);
		centerPanel.add(text4);
		centerPanel.add(label5);
		centerPanel.add(text5);
		centerPanel.add(label6);
		centerPanel.add(text6);
		centerPanel.add(buttonPanel);
		mainPanel.add(centerPanel, BorderLayout.NORTH);
		c1.add(mainPanel, "Main Panel");	
	}
	
	// Initialize progress bar panel and add to the container
	public void initProgressBar()
	{
		JPanel processPanel, progressBarPanel, taskPanel ;
		processPanel = new JPanel();
		progressBarPanel = new JPanel();
		taskPanel = new JPanel();
		processPanel.setLayout(new GridLayout(2,1));
		progressBarPanel.setLayout(new BorderLayout());
		taskPanel.setLayout(new BorderLayout());
		processPanel.setBackground(Color.black);
		progressBarPanel.setBackground(Color.LIGHT_GRAY);
		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		taskOutput = new JTextArea(5, 20);
		taskOutput.setMargin(new Insets(5, 5, 5, 5));
		taskOutput.setEditable(false);
		progressBarPanel.add(progressBar);
		taskPanel.add(new JScrollPane(taskOutput), BorderLayout.CENTER);
		processPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		processPanel.add(progressBarPanel);
		processPanel.add(taskPanel);
		c1.add(processPanel, "Progress Bar Panel");
	}
	
	// Initialize graph panel and add to the container
	public void initGraphPanel(JPanel chart)
	{
		graphPanel = chart;
		c1.add(graphPanel, "Graph Panel");
	}
	
	// Invoke progress bar after user approve all the entered data    
	public void showProgressBar()
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() 
			{
				initProgressBar();
				showProgressBarPanel();
			}
		});
	}
	
	// Show result graph after progress bar has been completed in 100%.
	// It graphically show the output from each simulations over the ranges. 
	public void showGraph(JPanel chart)
	{
				initGraphPanel(chart);
				showGraphPanel();
	}
	
	// Action is performed when user enter text. Try to validate the text form 
	// and numbers. Catch text format and number exceptions and box with error
	// message appear on the screen.
	
	protected class TextClickListener implements ActionListener 
	{
		JTextField t;
		
		TextClickListener(JTextField t) 
		{ 
			this.t = t;
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) 
		{	
			try
			{
				Integer.parseInt(event.getActionCommand());
				if(!(event.getActionCommand().equals(null )))
				{
					validateForm();
					validateNumbers();
					t.transferFocus();
				}
				
			}
			catch(NumberFormatException e) 
			{
				JOptionPane.showMessageDialog( null, "Error.\nPlease enter number.", 
						 "Message", JOptionPane.INFORMATION_MESSAGE );
			} catch (Exception e) {
				JOptionPane.showMessageDialog( null, "Error.\n" + e.getMessage() , 
						 "Message", JOptionPane.INFORMATION_MESSAGE );
			}
		}
	}
		
	protected class ExitClickListener implements ActionListener
	{
		ExitClickListener() { }
		
		@Override
		public void actionPerformed( ActionEvent event ) 
		{	
				int response = JOptionPane.showConfirmDialog( null, "Are you sure you want to exit?", "Message",
					    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );
				
				if( response == JOptionPane.YES_OPTION )
				{
					JOptionPane.showMessageDialog( null, "Thank you." +
				    " Have a nice day", "Message", JOptionPane.INFORMATION_MESSAGE );
					System.exit(0);
				}	
		}
	}
	
	private void validateForm() throws Exception
	{
		if(!text1.getText().equals("") && !text2.getText().equals("") && 
				Integer.parseInt(text1.getText()) <=Integer.parseInt(text2.getText()))
			throw new Exception("Too many express lanes!! \n Please enter correct number!");
		
		if(!text3.getText().equals("") && !text4.getText().equals("") &&
				Integer.parseInt(text3.getText()) >Integer.parseInt(text4.getText()))
			throw new Exception("Express lane item limit range beginning is grater then its ending!! \n " +
					"Please enter correct numbers!");
		
		if(!text4.getText().equals("") && !text6.getText().equals("") && 
				Integer.parseInt(text4.getText()) >= Integer.parseInt(text6.getText()))
			throw new Exception("The maximum number of items must be greater then express lane item limit range ending!! \n " +
					"Please enter correct numbers!");
	}
	
	private void validateNumbers() throws Exception
	{
		
		
		if(!text1.getText().equals("") && 
				Integer.parseInt(text1.getText()) <= 0 )
			throw new Exception("Wrong number of lanes!! \n Please enter positive number!");
		if(!text2.getText().equals("") && 
				Integer.parseInt(text2.getText()) <= 0 )
			throw new Exception("Wrong number of express lanes!! \n Please enter positive number!");
		
		if(!text3.getText().equals("") && 
				Integer.parseInt(text3.getText()) <= 0 )
			throw new Exception("Wrong number of express lanes range beginnig!! \n Please enter positive number!");
		text3.addActionListener(new TextClickListener(text3) );
		if(!text4.getText().equals("") && 
				Integer.parseInt(text4.getText()) <= 0 )
			throw new Exception("Wrong number of express lanes range ending!! \n Please enter positive number!");
		if(!text5.getText().equals("") && 
				Integer.parseInt(text5.getText()) <= 0 )
			throw new Exception("Wrong number of customers!! \n Please enter positive number!");
		if(!text6.getText().equals("") && 
				Integer.parseInt(text6.getText()) <= 0 )
			throw new Exception("Wrong number of max items!! \n Please enter positive number!");
		
	}
	
	protected class EnterClickListener implements ActionListener
	{
		private SimulationConfig info;
		
		EnterClickListener( )
		{ 
			info = new SimulationConfig();
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) 
		{	
			try
			{
				if(!(event.getActionCommand().equals(null )))
				{
					validateForm();
					validateNumbers();
					
					info.setCustomerRestriction(Integer.parseInt(text5.getText()), Integer.parseInt(text6.getText()));
					info.setExpressRange(Integer.parseInt(text3.getText()), Integer.parseInt(text4.getText()));
					info.setLanesNum(Integer.parseInt(text1.getText()), Integer.parseInt(text2.getText()));
					showProgressBar();
					task = blayer.startSimulation(info);
					task.addPropertyChangeListener(new PropertyChangeListener());
					task.execute();
				}	
			}
			catch(NumberFormatException e) 
			{
				JOptionPane.showMessageDialog( null, "Error.\nPlease enter number.", 
						 "Message", JOptionPane.INFORMATION_MESSAGE );
			} 
			catch (Exception e)
			{
				JOptionPane.showMessageDialog( null, "Error.\n" + e.getMessage() , 
						 "Message", JOptionPane.INFORMATION_MESSAGE );
			}
		}
	}
		
	protected class PropertyChangeListener implements java.beans.PropertyChangeListener
	{
		PropertyChangeListener() { }
		
		// Invoked when task's progress property changes.
		public void propertyChange(PropertyChangeEvent evt) 
		{
			if ("progress" == evt.getPropertyName()) 
			{
				int progress = (Integer) evt.getNewValue();
				progressBar.setValue(progress);
				taskOutput.append(String.format("Completed %d%% of task.\n", task.getProgress()));
			}
			else if ("state".equals(evt.getPropertyName()) && evt.getNewValue() == SwingWorker.StateValue.DONE) 
			{
				progressBar.setValue(100);
				ArrayList<Long> npoints = new ArrayList<Long> ();
				Iterator<SessionManager> iterator = dlayer.getSessions().iterator();     
				 
				 while (iterator.hasNext())
				 {
					 SessionManager s1 = (SessionManager)iterator.next();
					 npoints.add(s1.getAvgLaneTime());
				 }
				
				BarGraphRotate chart = new BarGraphRotate(npoints, c1.getHeight());
				showGraph(chart);
			}
		}
	}
	
	public void showProgressBarPanel( )
	{
		CardLayout card1 = (CardLayout)c1.getLayout();	
		card1.show( c1, "Progress Bar Panel" );
	}
	
	public void showGraphPanel( )
	{
		CardLayout card1 = (CardLayout)c1.getLayout();	
		card1.show( c1, "Graph Panel" );
	}
}

		