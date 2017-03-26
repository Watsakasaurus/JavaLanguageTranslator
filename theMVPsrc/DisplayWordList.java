/**
* AC12001 Translator Program
* @author   Evan Lott
* @version 1.0   160005234   25/03/2017
* DisplayWordList class :: Provides an interface to display an entire wordlist
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class DisplayWordList 
{
	//Initalises GUI references
    private JTextArea displayArea;
	private Translator translator;

	private Color bgColour = new Color(47,52,63);
	
	public DisplayWordList()
	{
		translator = new Translator();
		
        // Create and set up the window.
        JFrame frame = new JFrame("Display Dictionary");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setBackground(bgColour);

		//Create a panel and add components to it.
        JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(bgColour);
		
        contentPane.add(createContentPaneButtons(), BorderLayout.NORTH);
        contentPane.add(createDisplayPane(), BorderLayout.CENTER);
        
        // Create and set up the content pane.
        frame.setContentPane(contentPane); 
        
        // Display the window, setting the size
		frame.setMinimumSize(new Dimension(500,700));
        
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
	
	//Creates the native language pane
    private Container createDisplayPane() 
    {
        JPanel panel = new JPanel();
		panel.setBackground(bgColour);

        //Create a text field, change the font and add to the panel
        displayArea = new JTextArea(41,40);
		JScrollPane scroll = new JScrollPane(displayArea);
		displayArea.setEditable(false);
		displayArea.setLineWrap(true);
		displayArea.setWrapStyleWord(true);
		scroll.setBackground(Color.white);
        panel.add(scroll);

        return panel;
    }

    //Creates the contentpane containing the translateButton and drop down menus
    private Container createContentPaneButtons()
    {
        JPanel panel = new JPanel();
		panel.setBackground(bgColour);

		//Create the drop-down language selectors
        String[] choice = {"english","french", "spanish","russian","italian","gaelic","german"};
        String[] file  = {"en.txt","fr.txt", "es.txt","ru.txt","it.txt","gd.txt","de.txt"};

        final JComboBox<String> dictionaryChoice = new JComboBox<String>(choice);
		dictionaryChoice.setSelectedIndex(0);
        dictionaryChoice.setVisible(true);
		dictionaryChoice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				displayArea.setText("");
				int x = dictionaryChoice.getSelectedIndex();
				translator.setNativeLanguage(choice[x],file[x]);
				for(int i=0;i < translator.getNativeListSize();i++)
				{
					displayArea.append(translator.getNativeListRow(i) + '\n');
				}
			} 
		});
		dictionaryChoice.setBackground(Color.white);
		
		//Create the filename input box	
		JLabel label = new JLabel("Dictionary:");
		label.setForeground(Color.white);
		label.setVisible(true);
		
		panel.add(label);
		panel.add(dictionaryChoice);
		panel.setBackground(bgColour);
		return panel;
	}
}
