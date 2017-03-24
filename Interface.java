/**
* AC12001 Translator Program
* @author Evan Lott   160005234   23/03/2017
* @author Calum Watson 
* @version 1.1
* GUI class :: Provides user interface with the translator
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;


public class Interface 
{
	//Initalises GUI references
    private static JButton translateButton;
    private static JLabel nlLabel, tllabel; 
    private static JTextArea nlTextField, tlTextField;
	
	private Translator translator;

	public Interface()
	{
		translator = new Translator();
	}

    //Create the Menu
    private JMenuBar createMenuBar() 
    {
        JMenuBar menuBar  = new JMenuBar();
        JMenu menu = new JMenu("Tools");
        menuBar.add(menu);

        JMenuItem menuItem;
		
        // A group of menuitems under Tools tab
        menuItem = new JMenuItem("Menu Option 1");
        menu.add(menuItem);

        menuItem = new JMenuItem("Menu Option 2");
        menu.add(menuItem);
        return menuBar;
    }

	//Creates the native language pane
	private Container createNLPane()
	{
        /* Create a panel to add components into 
         * Look up layouts to see how to plce items in a nicer way
         */
        JPanel panel = new JPanel();

        /* Create a text field and add to the panel */
        nlTextField = new JTextArea(10,40);
		nlTextField.setLineWrap(true);
		nlTextField.setWrapStyleWord(true);
		nlTextField.getDocument().addDocumentListener(new DocumentListener()
		{
			public void changedUpdate(DocumentEvent e)
			{}
			public void removeUpdate(DocumentEvent e)
			{
				tlTextField.setText(translator.translateText(nlTextField.getText()));
  			}
			public void insertUpdate(DocumentEvent e)
			{
				tlTextField.setText(translator.translateText(nlTextField.getText()));
  			}
		});
        panel.add(nlTextField);
        return panel;
    }
	
	//Creates the native language pane
    private Container createTRPane() 
    {
        JPanel panel = new JPanel();

        //Create a text field, change the font and add to the panel
        tlTextField = new JTextArea(10,40);
		tlTextField.setEditable(false);
		tlTextField.setLineWrap(true);
		tlTextField.setWrapStyleWord(true);
        panel.add(tlTextField);

        return panel;
    }

    //Creates the contentpane containing the translateButton and drop down menus
    private Container createContentPaneButtons()
    {
        JPanel panel = new JPanel();

		translateButton = new JButton("Translate");
		translateButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
					tlTextField.setText(translator.translateText(nlTextField.getText()));
			} 
		});

        String[] choices = {"english","french", "spanish","russian","italian","gaelic","german"};
        String[] file  = {"en.txt","fr.txt", "es.txt","ru.txt","it.txt","gd.txt","de.txt"};

        final JComboBox<String> nlChoice = new JComboBox<String>(choices);
		nlChoice.setSelectedIndex(0);
        nlChoice.setVisible(true);
		nlChoice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int x = nlChoice.getSelectedIndex();
				translator.setNativeLanguage(choices[x],file[x]);
			} 
		});

        
        final JComboBox<String> tlChoice = new JComboBox<String>(choices);
		tlChoice.setSelectedIndex(1);
        tlChoice.setVisible(true);
		tlChoice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int x = tlChoice.getSelectedIndex();
				translator.setTranslationLanguage(choices[x],file[x]);
				tlTextField.setText(translator.translateText(tlTextField.getText()));
			} 
		});

        panel.add(nlChoice);
        panel.add(translateButton);
        panel.add(tlChoice);
        return panel;
    }
    
    /**
     * Create the GUI and show it.
     * For thread safety, this method should be invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() 
    {
        // Create and set up the window.
        JFrame frame = new JFrame("GO-GO-GADGET TRANSLATOR");
        Interface i = new Interface();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create a panel and add components to it.
        JPanel contentPane = new JPanel(new BorderLayout());

        contentPane.add(i.createContentPaneButtons(), BorderLayout.NORTH);
        contentPane.add(i.createTRPane(), BorderLayout.EAST);
        contentPane.add(i.createNLPane(), BorderLayout.WEST);
        
        // Create and set up the content pane.
        frame.setJMenuBar(i.createMenuBar());
        frame.setContentPane(contentPane); 
        
        // Display the window, setting the size
		frame.setMinimumSize(new Dimension(1000,250));
        
        frame.setVisible(true);
    }

    /**
     * This is a standard main function for a Java GUI
     */
    public static void main(String[] args) 
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
            {
                public void run() 
                {
                    createAndShowGUI();
                }
            });
    }
}


