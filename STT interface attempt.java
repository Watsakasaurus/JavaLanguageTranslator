import javax.swing.SpringLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;


public class STT
{
	//Initalises GUI references
    private JButton button;
	private JTextArea[] inputArea;
	private JTextArea messageBox;
	private String[] wordToBeAdded;
	private Color bgColour = new Color(47,52,63);
	private Translator translator;
	
	public STT()
	{
		translator = new Translator();

        // Create and set up the window.
        JFrame frame = new JFrame("Add To Dictionary");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setBackground(bgColour);

		//Create a panel and add components to it.
        JPanel contentPane = getContentPane(); 
		contentPane.setBackground(bgColour);
        
        // Create and set up the content pane.
        frame.setContentPane(contentPane); 
        
        // Display the window, setting the size
		frame.setMinimumSize(new Dimension(400,400));
        frame.setVisible(true);
	}

	public static void main(String[] args)
	{
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() 
			{
				new Interface();
			}
		});
    }


    private Container createContainer(){

    	JLabel cbLabel = new JLabel("Text to speech - Will record for 10 seconds");
		cbLabel.setForeground(Color.white);
		cbLabel.setVisible(True);

		

    	JButton button = new JButton("Record");
		button.setVisible(true);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
				tlTextField.setText("Speak now:");
				translator.getSpeech();
				tlTextField.setText("Loading");
				tlTextField.setText(translator.translateText(translator.speechToText(), false)); 


    		}
    	});
    	

    	panel.add(cbLabel);
    	panel.add(button);
       	return panel;	
    }
    	


    private Container createTRPane(){
        JPanel panel = new JPanel();
		panel.setBackground(bgColour);

        //Create a text field, change the font and add to the panel
        tlTextField = new JTextArea(40,30);
		tlTextField.setEditable(false);
		tlTextField.setLineWrap(true);
		tlTextField.setWrapStyleWord(true);
        panel.add(tlTextField);

        return panel;
    }

}	