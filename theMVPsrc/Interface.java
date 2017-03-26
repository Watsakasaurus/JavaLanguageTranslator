/**
* AC12001 Translator Program
* @author Evan Lott
* @author Calum Watson 
* @version 1.1   160005234   23/03/2017
* GUI class :: Provides user interface with the translator
*/

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;


public class Interface 
{
	//Initalises GUI references
    private JButton translateButton;
    private JTextArea nlTextField, tlTextField;
	private JCheckBox autoTranslateBox;
	private boolean autoTranslate;
	
	private Translator translator;

	private Color bgColour = new Color(47,52,63);
	
	public Interface()
	{
		autoTranslate = true;
		translator = new Translator();
        // Create and set up the window.
        JFrame frame = new JFrame("GO-GO-GADGET TRANSLATOR");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(bgColour);
		//Create a panel and add components to it.
        JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(bgColour);
		
        contentPane.add(createContentPaneButtons(), BorderLayout.NORTH);
        contentPane.add(createTRPane(), BorderLayout.EAST);
        contentPane.add(createNLPane(), BorderLayout.WEST);
        
        // Create and set up the content pane.
        frame.setJMenuBar(createMenuBar());
        frame.setContentPane(contentPane); 
        
        // Display the window, setting the size
		frame.setMinimumSize(new Dimension(750,700));
        
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
    //Create the Menu
    private JMenuBar createMenuBar() 
    {
        JMenuBar menuBar  = new JMenuBar();
		menuBar.setBackground(Color.white);
        JMenu menu = new JMenu("Tools");
		menu.setBackground(Color.white);
        menuBar.add(menu);

        JMenuItem menuItem;
		
        // A group of menuitems under Tools tab
        menuItem = new JMenuItem("Translate File");
		menuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new TranslateFile();
			}
		});
		menuItem.setBackground(Color.white);
        menu.add(menuItem);

        menuItem = new JMenuItem("Add to Dictionary");
		menuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new AddToDictionary();
				translator.loadLanguage(true);
				translator.loadLanguage(false);
			}
		});
		menuItem.setBackground(Color.white);
        menu.add(menuItem);

		menuItem = new JMenuItem("Display WordList");
		menuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new DisplayWordList();
			}
		});
		menuItem.setBackground(Color.white);
        menu.add(menuItem);
		
		menuItem = new JMenuItem("Delete a Word From Dictionary");
		menuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new DeleteWord();
				translator.loadLanguage(true);
				translator.loadLanguage(false);
			}
		});
		menuItem.setBackground(Color.white);
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
		panel.setBackground(bgColour);

        /* Create a text field and add to the panel */
        nlTextField = new JTextArea(40,30);
		nlTextField.setLineWrap(true);
		nlTextField.setWrapStyleWord(true);
		nlTextField.getDocument().addDocumentListener(new DocumentListener()
		{
			public void changedUpdate(DocumentEvent e)
			{
				if(autoTranslate)
					tlTextField.setText(translator.translateText(nlTextField.getText(),false));
			}
			public void removeUpdate(DocumentEvent e)
			{
				if(autoTranslate)
					tlTextField.setText(translator.translateText(nlTextField.getText(),false));
  			}
			public void insertUpdate(DocumentEvent e)
			{
				if(autoTranslate)
					tlTextField.setText(translator.translateText(nlTextField.getText(),false));
  			}
		});
        panel.add(nlTextField);
        return panel;
    }
	
	//Creates the native language pane
    private Container createTRPane() 
    {
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

    //Creates the contentpane containing the translateButton and drop down menus
    private Container createContentPaneButtons()
    {
        JPanel panel = new JPanel();
		panel.setBackground(bgColour);

		
		//Create the checkbox
		autoTranslateBox = new JCheckBox("auto-translate:");
    	autoTranslateBox.setMnemonic(KeyEvent.VK_C);
	    autoTranslateBox.setSelected(true);
		autoTranslateBox.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				autoTranslate = !autoTranslate;
			}
		});
		autoTranslateBox.setBackground(Color.white);

		//Create the translateButton
		translateButton = new JButton("Translate");
		translateButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
					tlTextField.setText(translator.translateText(nlTextField.getText(),false));
			} 
		});
		translateButton.setBackground(Color.white);

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
		nlChoice.setBackground(Color.white);

        
        final JComboBox<String> tlChoice = new JComboBox<String>(choices);
		tlChoice.setSelectedIndex(1);
        tlChoice.setVisible(true);
		tlChoice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int x = tlChoice.getSelectedIndex();
				translator.setTranslationLanguage(choices[x],file[x]);
				tlTextField.setText(translator.translateText(nlTextField.getText(),true));
			} 
		});
		tlChoice.setBackground(Color.white);

        panel.add(nlChoice);
		panel.add(autoTranslateBox);
        panel.add(translateButton);
        panel.add(tlChoice);
        return panel;
    }
    
    
}
