/**
* AC12001 Translator Program
* @author Evan Lott   160005234   07/03/16 
* @version v1.0
* Menu class :: 
* Provides user interface, propbably temporarily until a GUI is made
*/
public class Menu
{
	private Translator translator;
	public Menu()
	{
		translator = new Translator();
		selectOption();
	}

	private void displayMenu()
	{
		System.out.println("0 :: quit");
		System.out.println("1 :: set native language :: currently " + translator.getNativeLanguage());
		System.out.println("2 :: set translation language :: currently " + translator.getTranslationLanguage());
		System.out.println("3 :: translate text");
	}

	private void clearTerminal()
	{
	    System.out.print("\033[H\033[2J");
	}

	private int languageSelectionMenu()
	{
		clearTerminal();
		System.out.println();
		System.out.println("0 :: English");
		System.out.println("1 :: Russian");
		System.out.println("2 :: French");
		System.out.println("3 :: Galic");
		System.out.println("4 :: Italian");
		System.out.println("5 :: Spanish");
		System.out.println("Enter the language");
		return Genio.getInteger();
	}

	private void selectOption()
	{
		clearTerminal();
		boolean quit = false;
		while(!quit)
		{
			System.out.println();
			displayMenu();
			switch(Genio.getInteger())
			{
				case 0:
					quit = !quit;
					break;
				case 1:
					int a = languageSelectionMenu();
					if(a==0)
						translator.setNativeLanguage("en.txt");
					if(a==1)
						translator.setNativeLanguage("ru.txt");
					if(a==2)
						translator.setNativeLanguage("fr.txt");
					if(a==3)
						translator.setNativeLanguage("gd.txt");
					if(a==4)
						translator.setNativeLanguage("it.txt");
					if(a==5)
						translator.setNativeLanguage("es.txt");
					clearTerminal();
					break;
					case 2:
					int b = languageSelectionMenu();
					if(b==0)
						translator.setTranslationLanguage("en.txt");
					if(b==1)
						translator.setTranslationLanguage("ru.txt");
					if(b==2)
						translator.setTranslationLanguage("fr.txt");
					if(b==3)
						translator.setTranslationLanguage("gd.txt");
					if(b==4)
						translator.setTranslationLanguage("it.txt");
					if(b==5)
						translator.setTranslationLanguage("es.txt");
					clearTerminal();
					break;
				case 3:
					clearTerminal();
					System.out.println("Enter text to translate");
					String t = translator.translateText(Genio.getString());
					clearTerminal();
					System.out.print(t);
					break;
					
			}
		}
	}

	public static void main(String[] args)
	{
		new Menu();	
	}
}
