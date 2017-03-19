/**
* AC12001 Translator Program
* @author Evan Lott Joe Riemersma  160005234   07/03/16
* @version v1.0
* Translator class :: 
* Handles translation by pulling wordlists into ArrayLists
*/

//import the neccesary libraries




import java.io.*;
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

	public Translator()
	{
		//creates 2 new arraylists, 1 for the native language,
		//one for the translational
		nativeLanguageList = new ArrayList<String>();	
		translationLanguageList = new ArrayList<String>();	
		
		//default languages are English to French
		setNativeLanguage("en.txt");
		setTranslationLanguage("fr.txt");
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
			return "galic";
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
			return "galic";
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
				fileReader = new FileReader("src/" + nativeLanguage);
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
				fileReader = new FileReader("src/" + translationLanguage);
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
	public String translateText(String text)
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

				fw = new FileWriter("src/" + nativeLanguage, true);
				bw = new BufferedWriter(fw);

				fw1 = new FileWriter("src/"+ translationLanguage, true);
				bw1 = new BufferedWriter(fw1);
				bw.write(unknown);
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


		JavaSoundRecorder sound = new JavaSoundRecorder();
		sound.recordWav();


		Configuration configuration = new Configuration();

		configuration.setAcousticModelPath("src/en-us");
		configuration.setDictionaryPath("src/cmudict-en-us.dict");
		configuration.setLanguageModelPath("src/en-us.lm.bin");



		try {
			recognizer = new StreamSpeechRecognizer(configuration);
			stream = new FileInputStream(new File("tran.wav"));
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


}
