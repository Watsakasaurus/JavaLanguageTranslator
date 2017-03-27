/**
* AC12001 Translator Program
* @author   Evan Lott
* @author   Joe Riemersma
* @author   Calum Watson
* @author   Lucy Murphy
* @version   1.1   23/03/2017
* Interface class :: Provides a main user interface with the translator
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
    private JButton translateButton, switchLanguageButton;
    private JTextArea nlTextField, tlTextField;
	private JCheckBox autoTranslateBox;
	private boolean autoTranslate;
	
	//Initialises a new translator
	private Translator translator;
	private Color bgColour = new Color(47,52,63);
	
	/**
     * Constructor: Creates the GUI elements and populates them
     */
	public Interface()
	{
		//Sets default global variables
		autoTranslate = true;
		translator = new Translator();

        //Create and set up the window.
        JFrame frame = new JFrame("GO-GO-GADGET TRANSLATOR");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(bgColour);

		//Create a panel and add components to it.
        JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(bgColour);
        contentPane.add(createContentPaneButtons(), BorderLayout.NORTH);
        contentPane.add(createTRPane(), BorderLayout.EAST);
        contentPane.add(createNLPane(), BorderLayout.WEST);
        
        //Add menuBar and pane to the frame
        frame.setJMenuBar(createMenuBar());
        frame.setContentPane(contentPane); 
        
        // Display the window, setting the size
		frame.setMinimumSize(new Dimension(750,700));
        frame.setVisible(true);
	}

	/**
     * main: creates and shows the interface 
     */
    public static void main(String[] args) 
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
            {
                public void run() 
                {
					new Interface();
                }
            });
    }

	/**
     * Creates a menuBar
	 * @return JMenuBar: the populated menu bar
     */
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

	/**
     * Creates the text area to input text to translate into
	 * @return Container: A panel containing the formatted JTextArea with DocumentListener
     */
	private Container createNLPane()
	{
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
	
	/**
     * Creates the text area to input translated text
	 * @return Container: A panel containing the formatted JTextArea with DocumentListener
     */
    private Container createTRPane() 
    {
        JPanel panel = new JPanel();
		panel.setBackground(bgColour);

        //Create a text field
		tlTextField = new JTextArea(40,30);
		tlTextField.setEditable(false);
		tlTextField.setLineWrap(true);
		tlTextField.setWrapStyleWord(true);
        panel.add(tlTextField);

        return panel;
    }

	/**
     * Creates the buttons for interacting with the interface
	 * @return Container: A panel containing all the formatted buttons, labels etc.
	 */ 
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

		//Create the ComboBox
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

		//Create another ComboBox
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
		
		//Switch native to translational and vice versa
		switchLanguageButton = new JButton("Switch languages");
		switchLanguageButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
					nlTextField.setText("");
					tlTextField.setText("");
					int x = nlChoice.getSelectedIndex();
					nlChoice.setSelectedIndex(tlChoice.getSelectedIndex());
					tlChoice.setSelectedIndex(x);
			} 
		});
		switchLanguageButton.setBackground(Color.white);

		//Add all the components to the panel
        panel.add(nlChoice);
		panel.add(autoTranslateBox);
		panel.add(switchLanguageButton);
        panel.add(translateButton);
        panel.add(tlChoice);
        return panel;
    }
}
