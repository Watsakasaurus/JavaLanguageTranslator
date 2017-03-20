/**
* AC12001 Translator Program
* @author Evan Lott Joe Riemersma  160005234   07/03/16
* @version v1.0
* Translator class :: 
* Handles translation by pulling wordlists into ArrayLists
*/

//import the neccesary libraries




import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.ArrayList;


import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;



public class Translator
{
	//initalises standard library array lists
	private ArrayList<String> nativeLanguageList;
	private ArrayList<String> translationLanguageList;

	//initalises fields
	private String nativeLanguage;
	private String translationLanguage;

	//needed for loading in wordlists
	private FileReader fileReader;
	private BufferedReader bufferedReader;

	public boolean txtToSpeech;


	public Translator()
	{
		 txtToSpeech = false;

		//creates 2 new arraylists, 1 for the native language,
		//one for the translational
		nativeLanguageList = new ArrayList<String>();	
		translationLanguageList = new ArrayList<String>();	
		
		//default languages are English to French
		setNativeLanguage("en.txt");
		setTranslationLanguage("fr.txt");

		//Initialises Speech Synthesiser



	}

	public String getTranslationLanguage()
	{
		if(translationLanguage.equals("en.txt"))
			return "english";
		else if(translationLanguage.equals("fr.txt"))
			return "french";
		else if(translationLanguage.equals("es.txt"))
			return "spanish";
		else if(translationLanguage.equals("it.txt"))
			return "italian";
		else if(translationLanguage.equals("ru.txt"))
			return "russian";
		else if(translationLanguage.equals("gd.txt"))
			return "gaelic";
		return "";
	}
	
	public String getNativeLanguage()
	{
		if(nativeLanguage.equals("en.txt"))
			return "english";
		else if(nativeLanguage.equals("fr.txt"))
			return "french";
		else if(nativeLanguage.equals("es.txt"))
			return "spanish";
		else if(nativeLanguage.equals("it.txt"))
			return "italian";
		else if(nativeLanguage.equals("ru.txt"))
			return "russian";
		else if(nativeLanguage.equals("gd.txt"))
			return "gaelic";
		return "";
	}

	//sets the transational language to a new language
	public void setTranslationLanguage(String translationLanguage)
	{
		if(this.translationLanguage==translationLanguage)
			return;
		this.translationLanguage = translationLanguage;	
		loadLanguage(false);
	}
	
	//sets the native language to a new language
	public void setNativeLanguage(String nativeLanguage)
	{
		if(this.nativeLanguage==nativeLanguage)
			return;
		this.nativeLanguage = nativeLanguage;	
		loadLanguage(true);
	}

	//loads language
	public void loadLanguage(boolean x)
	{
		//if x is true load the new native language into the cleared ArrayList
		if(x)
		{
			try
			{
				nativeLanguageList.clear();
				fileReader = new FileReader("resources/" + nativeLanguage);
				bufferedReader = new BufferedReader(fileReader);
				String nextLine = bufferedReader.readLine();
				while(nextLine!=null)
				{
					nativeLanguageList.add(nextLine);
					nextLine = bufferedReader.readLine();
				}
				bufferedReader.close();
			}
			catch(IOException e)
			{
				System.out.println(e);	
			}
		}
		//if x is false load the new translation language into the cleared ArrayList
		else
		{
			try
			{
				translationLanguageList.clear();
				fileReader = new FileReader("resources/" + translationLanguage);
				bufferedReader = new BufferedReader(fileReader);
				String nextLine = bufferedReader.readLine();
				while(nextLine!=null)
				{
					translationLanguageList.add(nextLine);
					nextLine = bufferedReader.readLine();
				}
			}
			catch(IOException e)
			{
				System.out.println(e);	
			}
		}
	}
	
	//takes the inputted text and returns the translation
	public String translateText(String text, Boolean x)
	{
		String parsedText[] = text.split(" ");
		String str = "";
		for(int i = 0; i < parsedText.length; i++)
		{
			for(int f = 0; f < nativeLanguageList.size(); f++)
			{
				if(punctuationSorter(parsedText[i]).equals(nativeLanguageList.get(f)))
				{
					parsedText[i] = translationLanguageList.get(f);					
					break;
				}


			}
			str += parsedText[i] + " ";
		}

		if(x){
			speechSynthesiser(str);
		}
		return str;
	}	

	//goes through string s, makes every uppercase character lowercase
	public String punctuationSorter(String s)
	{
		String p = "";
		for(int f = 0; f < s.length(); f++)
		{
        		if (Character.isUpperCase(s.charAt(f)))
				p += Character.toLowerCase(s.charAt(f));
			else
				p += s.charAt(f);
		}
		return p;
    }
	//adds unknown word and users entry to corresponding dictionaries
    public void addToDictionary(String unknown){

		String translated;
		char answer;

		BufferedWriter bw = null;
		FileWriter fw = null;

		BufferedWriter bw1 = null;
		FileWriter fw1 = null;

		System.out.println("Please enter the translation of " + unknown + " in " + this.getTranslationLanguage());
		translated = Genio.getString();

		System.out.println("Do you wish to add " + translated + " to the " + this.getTranslationLanguage() + " dictionary: y/n");
		answer = Genio.getCharacter();

		if(answer == 'y' || answer == 'Y'){

			try{

				fw = new FileWriter("resources/" + nativeLanguage, true);
				bw = new BufferedWriter(fw);

				fw1 = new FileWriter("resources/"+ translationLanguage, true);
				bw1 = new BufferedWriter(fw1);
				bw.newLine();
				bw.write(unknown);
				bw1.newLine();
				bw1.write(translated);


			}catch(IOException e){

				System.out.println("There was an error writing to the file.");

			}finally{

				try{
					if (bw != null){
						bw.close();
					}
					if (fw != null) {
						fw.close();
					}
					if (bw1 != null){
						bw1.close();
					}
					if (fw1 != null) {
						fw1.close();
					}
				} catch(IOException e) {

					System.out.println("There was an error closing the files");

				}




			}


		}



	}

	//Creates .wav file, then converts that to text
	public String speechToText(){

		String fullTxt = "";
		SpeechResult result;
		StreamSpeechRecognizer recognizer;
		InputStream stream;


		//JavaSoundRecorder sound = new JavaSoundRecorder();  // For Speech to text UNCOMMENT These 2 lines AND
		//sound.recordWav();


		Configuration configuration = new Configuration();

		configuration.setAcousticModelPath("resources/en-us");
		configuration.setDictionaryPath("resources/cmudict-en-us.dict");
		configuration.setLanguageModelPath("resources/en-us.lm.bin");



		try {
			recognizer = new StreamSpeechRecognizer(configuration);
			stream = new FileInputStream(new File("resources/tst.wav")); //CHANGE THIS LINE to "resources/tran.wav"
		}catch (Exception e){
			System.out.print("Unable to Load Speech recognition");
			return fullTxt;
		}


		recognizer.startRecognition(stream);

		while((result = recognizer.getResult()) != null){
			System.out.format("Hypothesis: %s\n", result.getHypothesis());
			fullTxt += " " + result.getHypothesis();
		}

		recognizer.stopRecognition();
		System.out.print(fullTxt);
		return fullTxt;

	}

	public void txtFileTrans(String filePath){

		long startTime = 0;
		long endTime = 0;

		FileWriter fw = null;
		BufferedWriter bw = null;

		String translatedLine;

		try{

			fileReader = new FileReader(filePath);
			bufferedReader = new BufferedReader(fileReader);

			fw = new FileWriter("translated.txt");
			bw = new BufferedWriter(fw);

			String nextLine = bufferedReader.readLine();

			startTime = System.nanoTime();

			while(nextLine!=null)
			{
				translatedLine = translateText(nextLine, txtToSpeech);
				bw.write(translatedLine);
				bw.newLine();

				System.out.println(translatedLine);

				nextLine = bufferedReader.readLine();
			}

			endTime = System.nanoTime();
			bufferedReader.close();

		}catch(IOException e){
			System.out.println("Unable to translate file :: make sure you have entered the correct path to the file.");
			return;

		}finally {

			try {
				if (bw != null) {
					bw.close();
				}
				if (fw != null) {
					fw.close();
				}

			} catch (IOException e) {

				System.out.println("There was an error closing the files");

			}
		}


		long duration = (endTime - startTime);


		System.out.println("\n");
		System.out.println("Translation Complete ");
		System.out.println("Translated File in root folder");
		System.out.println("Time Taken: " +  duration/1000000 + " Miliseconds");
	}


	public void speechSynthesiser(String x){
		voce.SpeechInterface.synthesize(x);

	}



}






