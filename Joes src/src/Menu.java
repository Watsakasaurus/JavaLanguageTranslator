/**
* AC12001 Translator Program
* @author Evan Lott   160005234   07/03/16 
* @version v1.0
* Menu class :: 
* Provides user interface, propbably temporarily until a GUI is made
*/
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;


public class Menu {
	private Translator translator;

	public Menu() {
		translator = new Translator();
		//getVoice();
		selectOption();
	}

	private void displayMenu() {
		System.out.println("0 :: quit");
		System.out.println("1 :: set native language :: currently " + translator.getNativeLanguage());
		System.out.println("2 :: set translation language :: currently " + translator.getTranslationLanguage());
		System.out.println("3 :: translate text");
		System.out.println("5 :: add new words to dictionary");
	}

	private void clearTerminal() {
		System.out.print("\033[H\033[2J");
	}

	private int languageSelectionMenu() {
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

	private void selectOption() {
		clearTerminal();
		boolean quit = false;
		while (!quit) {
			System.out.println();
			displayMenu();
			switch (Genio.getInteger()) {
				case 0:
					quit = !quit;
					break;
				case 1:
					int a = languageSelectionMenu();
					if (a == 0)
						translator.setNativeLanguage("en.txt");
					if (a == 1)
						translator.setNativeLanguage("ru.txt");
					if (a == 2)
						translator.setNativeLanguage("fr.txt");
					if (a == 3)
						translator.setNativeLanguage("gd.txt");
					if (a == 4)
						translator.setNativeLanguage("it.txt");
					if (a == 5)
						translator.setNativeLanguage("es.txt");
					clearTerminal();
					break;
				case 2:
					int b = languageSelectionMenu();
					if (b == 0)
						translator.setTranslationLanguage("en.txt");
					if (b == 1)
						translator.setTranslationLanguage("ru.txt");
					if (b == 2)
						translator.setTranslationLanguage("fr.txt");
					if (b == 3)
						translator.setTranslationLanguage("gd.txt");
					if (b == 4)
						translator.setTranslationLanguage("it.txt");
					if (b == 5)
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
				case 5:
					clearTerminal();
					System.out.println("Enter the word in " + translator.getNativeLanguage() + " that you wish to translate into " + translator.getTranslationLanguage());
					translator.addToDictionary(Genio.getString());
					break;

			}
		}
	}

	public static void main(String[] args) throws Exception{
		//voce.SpeechInterface.init("src/voce-0.9.1/voce-0.9.1/lib", true, false,
			//	"src/voce-0.9.1/voce-0.9.1/lib/gram", "digits");
		//voce.SpeechInterface.synthesize("Somebody once told me the world is gonna roll me I ain't the sharpest tool in the shed She was looking kind of dumb with her finger and her thumb in the shape of an L on her forehead Well the years start coming and they don't stop coming and they don't stop coming and they don't stop coming and they don't stop coming");

        Configuration config = new Configuration();


        config.setLanguageModelPath("resource:/src/en-us.lm.bin");

        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(config);


        recognizer.startRecognition(true);
        SpeechResult result;
        while ((result = recognizer.getResult()) != null) {
            System.out.format("Hypothesis: %s\n", result.getHypothesis());
        }
        recognizer.stopRecognition();

        //new Menu()
    }








}
