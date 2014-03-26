import javax.swing.JFrame;

public class Console 
{
	public static void run( JFrame frame, int width, int high )
	{
		frame.setLocationRelativeTo( null );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		
		if ( width > 0 && high > 0 )
		{
			frame.setSize(width, high);
			frame.setVisible(true);
		}
		else
		{
			frame.setVisible(false);
		}
	}
}
