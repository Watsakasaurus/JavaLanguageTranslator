/**
* AC12001 Translator Program
* @author   Evan Lott
* @version 1.0   25/03/2017
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
    private JTextArea displayArea;
	private Translator translator;
	private Color bgColour = new Color(47,52,63);
	
    /**
     * Constructor: sets default values for fields and displays the GUI
	 */
	public DisplayWordList()
	{
		translator = new Translator();
		
        // Create and set up the window.
        JFrame frame = new JFrame("Display Dictionary");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setBackground(bgColour);

		//Create a panel
        JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(bgColour);
		
		//Fill the panel
        contentPane.add(createContentPaneButtons(), BorderLayout.NORTH);
        contentPane.add(createDisplayPane(), BorderLayout.CENTER);
        
		
        frame.setContentPane(contentPane); 
		frame.setMinimumSize(new Dimension(500,700));
        
		//Set the window to visible
        frame.setVisible(true);
	}

	/**
     * createDisplayPane: Creates the displayArea
	 * @return Container: the panel with the displayArea added to it
	 */
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

	/**
     * createContentPaneButtons: Creates the contentpane containing the Combobox and label 
	 * @return Container: the panel with the elements added to it
	 */
	private Container createContentPaneButtons()
    {
        JPanel panel = new JPanel();

		//Create the drop-down language selector
		String[] choice = {"english","french", "spanish","russian","italian","gaelic","german"};
        String[] file  = {"en.txt","fr.txt", "es.txt","ru.txt","it.txt","gd.txt","de.txt"};
        final JComboBox<String> dictionaryChoice = new JComboBox<String>(choice);
		dictionaryChoice.setSelectedIndex(0);
        dictionaryChoice.setVisible(true);
		dictionaryChoice.addActionListener(new ActionListener()
		{
			//If a selection is made, clear the text area and add all the chosen
			//dictionary to it
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
		
		//Create label	
		JLabel label = new JLabel("Dictionary:");
		label.setForeground(Color.white);
		label.setVisible(true);
		
		panel.add(label);
		panel.add(dictionaryChoice);
		panel.setBackground(bgColour);
		return panel;
	}
}
