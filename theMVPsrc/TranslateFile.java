/**
* AC12001 Translator Program
* @author   Evan Lott
* @version 1.0   160005234   26/03/2017
* TranslateFile class :: Provides an interface to translate an entire file into another language
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class TranslateFile 
{
	//Initalises GUI references
    private JButton translateButton;
    private JTextArea tlTextField;
	private JTextArea inputArea;
	private String fileName;
	private Translator translator;

	private Color bgColour = new Color(47,52,63);
	
	public TranslateFile()
	{
		translator = new Translator();
		fileName = "";
		
        // Create and set up the window.
        JFrame frame = new JFrame("Translate File");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setBackground(bgColour);

		//Create a panel and add components to it.
        JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(bgColour);
		
        contentPane.add(createContentPaneButtons(), BorderLayout.NORTH);
        contentPane.add(createTRPane(), BorderLayout.CENTER);
        
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
    private Container createTRPane() 
    {
        JPanel panel = new JPanel();
		panel.setBackground(bgColour);

        //Create a text field, change the font and add to the panel
        tlTextField = new JTextArea(41,40);
		JScrollPane scroll = new JScrollPane(tlTextField);
		tlTextField.setEditable(false);
		tlTextField.setLineWrap(true);
		tlTextField.setWrapStyleWord(true);
		scroll.setBackground(Color.white);
        panel.add(scroll);

        return panel;
    }

    //Creates the contentpane containing the translateButton and drop down menus
    private Container createContentPaneButtons()
    {
        JPanel panel = new JPanel();
		panel.setBackground(bgColour);

		//Create the translateButton
		translateButton = new JButton("Translate");
		translateButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				tlTextField.setText(translator.txtFileTrans(inputArea.getText()));
			} 
		});
		translateButton.setBackground(Color.white);


		//Create the drop-down language selectors
        String[] choice = {"english","french", "spanish","russian","italian","gaelic","german"};
        String[] file  = {"en.txt","fr.txt", "es.txt","ru.txt","it.txt","gd.txt","de.txt"};

        final JComboBox<String> nlChoice = new JComboBox<String>(choice);
		nlChoice.setSelectedIndex(0);
        nlChoice.setVisible(true);
		nlChoice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int x = nlChoice.getSelectedIndex();
				translator.setNativeLanguage(choice[x],file[x]);
			} 
		});
		nlChoice.setBackground(Color.white);
		
        final JComboBox<String> tlChoice = new JComboBox<String>(choice);
		tlChoice.setSelectedIndex(1);
		tlChoice.setVisible(true);
		tlChoice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int x = tlChoice.getSelectedIndex();
				translator.setTranslationLanguage(choice[x],file[x]);
			} 
		});
		tlChoice.setBackground(Color.white);

		//Create the filename input box	
		JLabel label = new JLabel("filename:");
		label.setForeground(Color.white);
		label.setVisible(true);
		inputArea = new JTextArea(1,10);
		inputArea.setWrapStyleWord(false);
		inputArea.setLineWrap(false);
		inputArea.setBackground(Color.white);
		inputArea.setVisible(true);
		
		panel.add(label);
		panel.add(inputArea);
		panel.add(nlChoice);
		panel.add(translateButton);
		panel.add(tlChoice);
		panel.setBackground(bgColour);
		return panel;
	}
}
