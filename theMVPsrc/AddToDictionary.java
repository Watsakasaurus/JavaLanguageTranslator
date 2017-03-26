/**
* AC12001 Translator Program
* @author   Evan Lott
* @version 1.0   160005234   25/03/2017
* AddToDictionary class :: Provides an interface to add a word to all wordlists
*/

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

public class AddToDictionary
{
	//Initalises GUI references
    private JButton button;
	private JTextArea[] inputArea;
	private JTextArea messageBox;
	private String[] wordToBeAdded;
	private Color bgColour = new Color(47,52,63);
	private Translator translator;
	
	public AddToDictionary()
	{
		translator = new Translator();

		inputArea = new JTextArea[7];
		inputArea[0] = new JTextArea("");
		inputArea[1] = new JTextArea("");
		inputArea[2] = new JTextArea("");
		inputArea[3] = new JTextArea("");
		inputArea[4] = new JTextArea("");
		inputArea[5] = new JTextArea("");
		inputArea[6] = new JTextArea("");

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
		frame.setMinimumSize(new Dimension(350,250));
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

	private JPanel getContentPane()
	{
		JPanel contentPane = new JPanel(new SpringLayout());
        String[] labelString = {"english","french", "spanish","russian","italian","gaelic","german"};
        String[] fileString  = {"en.txt","fr.txt", "es.txt","ru.txt","it.txt","gd.txt","de.txt"};
		button = new JButton("Add to Dictionary");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				translator.loadLanguage(true);
				messageBox.setText("");
				if
				(
					inputArea[0].getText().equals("")||
					inputArea[1].getText().equals("")||
					inputArea[2].getText().equals("")||
					inputArea[3].getText().equals("")||
					inputArea[4].getText().equals("")||
					inputArea[5].getText().equals("")||
					inputArea[6].getText().equals("")
				)			
					messageBox.setText("Fill all boxes");
				else
				{
				
					if(translator.isInList(translator.punctuationSorter(inputArea[0].getText())))
						messageBox.setText("Word already in list");
					else
					{
						for(int f=0;f<7;f++)
						{
							translator.addToDictionary(translator.punctuationSorter(inputArea[f].getText()),fileString[f]);
							messageBox.setText("Word added");
						}
					}
				}
			} 
		});
		button.setBackground(Color.white);
		contentPane.add(button);

		messageBox = new JTextArea();
		messageBox.setEditable(false);
		messageBox.setBackground(Color.white);
		contentPane.add(messageBox);
		for(int i=0;i<7;i++)
		{
			JLabel label = new JLabel(labelString[i]+":");
			label.setForeground(Color.white);
			contentPane.add(label);
			inputArea[i].setWrapStyleWord(false);
			inputArea[i].setLineWrap(false);
			contentPane.add(inputArea[i]);
		}
		SpringUtilities.makeCompactGrid(contentPane,8,2,1,1,20,10);
		return contentPane;
	}
}
