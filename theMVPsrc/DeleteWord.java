/**
* AC12001 Translator Program
* @author   Evan Lott
* @version 1.0   160005234   25/03/2017
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
	//Initalises GUI references
	private Translator translator;
	String languageFile;
	String languageName;
	int chosenLan;


	private Color bgColour = new Color(47,52,63);
	
	public DeleteWord()
	{
		translator = new Translator();
		chosenLan = 0;
		languageFile = "en.txt";
		languageName = "english";
		
        // Create and set up the window.
        JFrame frame = new JFrame("Delete a Word");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setBackground(bgColour);

		//Create a panel and add components to it.
        JPanel contentPane = new JPanel(new FlowLayout());
		contentPane.setBackground(bgColour);
		
        contentPane.add(createContent());
        
        // Create and set up the content pane.
        frame.setContentPane(contentPane); 
        
        // Display the window, setting the size
		frame.setMinimumSize(new Dimension(800,70));
        
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
	

    private Container createContent()
    {
		JPanel panel = new JPanel();
		panel.setBackground(bgColour);
		
		JLabel iaLabel = new JLabel("Word to delete (will delete translations too):");
		iaLabel.setForeground(Color.white);
		iaLabel.setVisible(true);


		JTextArea inputArea = new JTextArea(1,10);
		inputArea.setLineWrap(false);
		inputArea.setBackground(Color.white);

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
			public void actionPerformed(ActionEvent e)
			{
				inputArea.setText("");
				chosenLan = language.getSelectedIndex();
				languageFile = file[chosenLan];	
				languageName = choice[chosenLan];
			} 
		});
		language.setBackground(Color.white);

		JButton button = new JButton("delete");
		button.setVisible(true);
		button.addActionListener(new ActionListener()
		{
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

		//Create the filename input box	
		panel.add(cbLabel);
		panel.add(language);
		panel.add(iaLabel);
		panel.add(inputArea);
		panel.add(button);
		panel.setBackground(bgColour);
		return panel;
	}
}
