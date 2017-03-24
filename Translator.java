/**
* AC12001 Translator Program
* @author Evan Lott Joe Riemersma  160005234   07/03/16
* @version v1.0
* Translator class :: 
* Handles translation by pulling wordlists into ArrayLists
*/

//Import the neccesary libraries
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.ArrayList;

public class Translator
{
	//Initalises standard library array lists
	private ArrayList<String> nativeLanguageList;
	private ArrayList<String> translationLanguageList;
	private String[] language;
	private String[] languageFile;

	//Initalises fields
	private String nativeLanguage;
	private String translationLanguage;
	private String nativeLanguageFile;
	private String translationLanguageFile;
	private String punctuationMark;
	private boolean upperCase;
	
	//Needed for loading in wordlists
	private FileReader fileReader;
	private BufferedReader bufferedReader;

	public boolean txtToSpeech;

	public Translator()
	{
		txtToSpeech = false;
		//Creates 2 new arraylists, 1 for the native language,
		//one for the translational
		nativeLanguageList = new ArrayList<String>();	
		translationLanguageList = new ArrayList<String>();	
		
		//Default languages are English to French
		setTranslationLanguage("french","fr.txt");
		setNativeLanguage("english","en.txt");
		punctuationMark = "";
	}

	public String getTranslationLanguage()
	{
		return translationLanguage;
	}
	
	public String getNativeLanguage()
	{
		return nativeLanguage;
	}

	//Sets the transational language to a new language
	public void setTranslationLanguage(String translationLanguage,String translationLanguageFile)
	{
		this.translationLanguage = translationLanguage;	
		this.translationLanguageFile = translationLanguageFile;
		loadLanguage(false);
	}

	public void setNativeLanguage(String nativeLanguage,String nativeLanguageFile)
	{
		this.nativeLanguage = nativeLanguage;	
		this.nativeLanguageFile = nativeLanguageFile;
		loadLanguage(true);
	}

	//Loads language
	public void loadLanguage(boolean x)
	{
		//If x is true load the new native language into the cleared ArrayList
		if(x)
		{
			try
			{
				nativeLanguageList.clear();
				fileReader = new FileReader(nativeLanguageFile);
				bufferedReader = new BufferedReader(fileReader);
				String nextLine = bufferedReader.readLine();
				while(nextLine!=null)
				{
					nativeLanguageList.add(nextLine);
					nextLine = bufferedReader.readLine();
				}
				bufferedReader.close();
			}
			catch(IOException e){System.out.println(e);}
		}
		//If x is false load the new translation language into the cleared ArrayList
		else
		{
			try
			{
				translationLanguageList.clear();
				fileReader = new FileReader(translationLanguageFile);
				bufferedReader = new BufferedReader(fileReader);
				String nextLine = bufferedReader.readLine();
				while(nextLine!=null)
				{
					translationLanguageList.add(nextLine);
					nextLine = bufferedReader.readLine();
				}
			}
			catch(IOException e){System.out.println(e);}
		}
	}
	
	//takes the inputted text and returns the translation
	public String translateText(String text)
	{
		upperCase = true;
		String parsedText[] = text.split(" ");
		String str = "";
		for(int i = 0; i < parsedText.length; i++)
		{
			for(int f = 0; f < nativeLanguageList.size(); f++)
			{
				if(punctuationSorter(parsedText[i]).equals(nativeLanguageList.get(f)))
				{
					//Makes the text add a word with an uppercase first letter if the last word had a given
					//punctuation mark
					if(upperCase)
					{
						parsedText[i] = makeSentenceStarter(translationLanguageList.get(f));
						break;
					}
					else
					{
						parsedText[i] = translationLanguageList.get(f);					
						break;
					}
				}	
			}

			//If the punctuation mark of the word is either of those strings then upperCase = true
			//so that the next word starts with upper case
			if(
			punctuationMark.equals(". ")||
			punctuationMark.equals("; ")||
			punctuationMark.equals("! ")||
			punctuationMark.equals("? "))
				upperCase = true;

			str += parsedText[i] + punctuationMark;
		}
		return str;
	}	

	//Switches the 2 language options round
	public void switchLanguages()
	{
		String x = nativeLanguage;
		String xd = nativeLanguageFile;
		setNativeLanguage(translationLanguage,translationLanguageFile);
		setTranslationLanguage(x,xd);
		loadLanguage(true);
		loadLanguage(false);
	}

		//adds unknown word and users entry to corresponding dictionaries
	public void addToDictionary(String filename){

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
				translatedLine = translateText(nextLine);
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
	

	//Sorts punctuation
	private String punctuationSorter(String s)
	{
		String p = "";

		//Makes string p contain every char in s, but all lower case
		for(int f = 0; f < s.length(); f++)
		{
        	if(Character.isUpperCase(s.charAt(f)))
				p += Character.toLowerCase(s.charAt(f));
			else
				p += s.charAt(f);
		}

		//Assigns a globabl punctuation mark and removes the punctuation from the s
		if(p.charAt(p.length()-1)=='.')
		{
			p =  p.substring(0,p.length()-1);
			punctuationMark = ". ";
		}
		else if(p.charAt(p.length()-1)==';')
		{
			p =  p.substring(0,p.length()-1);
			punctuationMark = "; ";
		}
		else if(p.charAt(p.length()-1)==',')
		{
			p =  p.substring(0,p.length()-1);
			punctuationMark = ", ";
		}
		else if(p.charAt(p.length()-1)=='?')
		{
			p =  p.substring(0,p.length()-1);
			punctuationMark = "? ";
		}
		else if(p.charAt(p.length()-1)=='!')
		{
			p =  p.substring(0,p.length()-1);
			punctuationMark = "! ";
		}

		else
			punctuationMark = " ";
		return p;
    }
	
	//Returns x with the first char uppercase
	private String makeSentenceStarter(String x)
	{
		String y = "";
		y += Character.toUpperCase(x.charAt(0));
		y += x.substring(1,x.length());
		upperCase = false;
		return y;
	}
}
