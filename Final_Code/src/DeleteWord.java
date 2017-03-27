/**
* AC12001 Translator Program
* @author   Evan Lott
* @author   Joe Riemersma
* @author   Calum Watson
* @author   Lucy Murphy
* @version   1.0   25/03/2017
* DeleteWord class :: Provides an interface to delete a word and it's translations
*/

import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class DeleteWord 
{
	private Translator translator;
	private String languageFile;
	private String languageName;
	private int chosenLan;
	private Color bgColour = new Color(47,52,63);
	
	/**
     * Constructor: Initialises the global fields and sets up and displays the GUI
	 * @return Container: the panel with the elements added to it
	 */
	public DeleteWord()
	{
		//Creates a new Translator object
		translator = new Translator();

		//Sets fields to default values
		chosenLan = 0;
		languageFile = "en.txt";
		languageName = "english";
		
        //Create and set up the window.
        JFrame frame = new JFrame("Delete a Word");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setBackground(bgColour);

		//Create a panel 
        JPanel contentPane = new JPanel(new FlowLayout());
		contentPane.setBackground(bgColour);
		
		//Add all content to the pane
        contentPane.add(createContent());
        
        frame.setContentPane(contentPane); 
		frame.setMinimumSize(new Dimension(800,70));

		//Show the frame
        frame.setVisible(true);
	}

	/**
     * createContent: creates all the labels, buttons, etc and adds them to a panel
	 * @return : Container - the panel with all the elements added
	*/
    private Container createContent()
    {
		JPanel panel = new JPanel();
	
		//Create a label for the inputArea
		JLabel iaLabel = new JLabel("Word to delete (will delete translations too):");
		iaLabel.setForeground(Color.white);
		iaLabel.setVisible(true);

		//Set up the inputArea
		JTextArea inputArea = new JTextArea(1,10);
		inputArea.setLineWrap(false);
		inputArea.setBackground(Color.white);
		
		//Create the label for the ComboBox
		JLabel cbLabel = new JLabel("Language of word:");
		cbLabel.setForeground(Color.white);
		cbLabel.setVisible(true);

		//Create the drop-down language selectors
        String[] choice = {"english","french", "spanish","russian","italian","gaelic","german"};
        String[] file  = {"en.txt","fr.txt", "es.txt","ru.txt","it.txt","gd.txt","de.txt"};
        final JComboBox<String> language = new JComboBox<String>(choice);
		language.setSelectedIndex(0);
        language.setVisible(true);
		language.addActionListener(new ActionListener()
		{
			//If the ComboBox is pressed then the chosenLan is changed
			//to the number corresponding to the press. The languageFile and
			//languageName are set to the correct language Strings.
			public void actionPerformed(ActionEvent e)
			{
				inputArea.setText("");
				chosenLan = language.getSelectedIndex();
				languageFile = file[chosenLan];	
				languageName = choice[chosenLan];
			} 
		});
		language.setBackground(Color.white);

		//Create the delete button
		JButton button = new JButton("delete");
		button.setVisible(true);
		button.addActionListener(new ActionListener()
		{
			//If the inputArea is empty do nothing, if the return of the 
			//delete method is -1 do nothing. Delete the corresponding lineSeparator
			//on all other text files
			public void actionPerformed(ActionEvent e)
			{	
				String y = inputArea.getText();
				if(y.equals(""))
					return;
				int x = translator.deleteLineFromDictionary(languageFile,translator.punctuationSorter(y),0);	
				if(x==-1)
					return;

				for(int i=0;i<7;i++)
				{
					if(i==chosenLan)	
						continue;
					else
						translator.deleteLineFromDictionary(file[i],"",x);
				}
				inputArea.setText("");
			}
		});
		button.setBackground(Color.white);

		//Add all the elements to the panel
		panel.add(cbLabel);
		panel.add(language);
		panel.add(iaLabel);
		panel.add(inputArea);
		panel.add(button);
		panel.setBackground(bgColour);
		return panel;
	}
}
