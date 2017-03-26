/**
* AC12001 Translator Program
* @author   Evan Lott   
* @author Joe Riemersma
* @version v1.0   160005234   07/03/16
* Translator class :: Handles translation by pulling wordlists into ArrayLists
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
import java.util.HashMap;
import java.util.Iterator;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

public class Translator
{
	//Initalises standard array lists for holding the dictionaries
	private ArrayList<String> nativeLanguageList;
	private ArrayList<String> translationLanguageList;

	//Initalises a HashMap to store translated words so the dictionary files dont have 
	//to be searched twice
	private HashMap<String,String> translationMade;

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

	public Translator()
	{
		//Creates 2 new arraylists, 1 for the native language,
		//one for the translational
		nativeLanguageList = new ArrayList<String>();	
		translationLanguageList = new ArrayList<String>();	
		translationMade = new HashMap<String,String>();
		
		//Default languages are English to French
		setTranslationLanguage("french","fr.txt");
		setNativeLanguage("english","en.txt");
		punctuationMark = "";
		upperCase = true;
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
			catch(IOException e)
			{}
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
			catch(IOException e)
			{}
		}
	}
	
	//takes the inputted text and returns the translation
	public String translateText(String text,boolean emptyHashTable)
	{
		upperCase = true;
		if(emptyHashTable)
			translationMade.clear();
		String parsedText[] = text.split("\\s");
		if(parsedText[0].equals(""))
		{
			translationMade.clear();
			return "";
		}
		String tow = "";
		String str = "";
		for(int i=0;i<parsedText.length;i++)
		{
			String sortedText = punctuationSorter((parsedText[i]));
			if(translationMade.containsKey(sortedText))
			{
				tow = translationMade.get(sortedText);
				if(upperCase)
					tow = makeSentenceStarter(tow);
			}
			else	
			{
				for(int f=0;f<nativeLanguageList.size();f++)
				{
					if(sortedText.equals(nativeLanguageList.get(f)))
					{
						//Makes the text add a word with an uppercase first letter if the last word had a given
						//punctuation mark
						if(upperCase)
						{
							tow = makeSentenceStarter(translationLanguageList.get(f));
							break;
						}
						else
						{
							tow = translationLanguageList.get(f);	
							break;
						}
					}	
					tow = parsedText[i];
				}
				translationMade.put(sortedText,tow);
			}
			
			//If the punctuation mark of the word is either of those strings then upperCase = true
			//so that the next word starts with upper case
			if(
			punctuationMark.equals(". ")||
			punctuationMark.equals("; ")||
			punctuationMark.equals("! ")||
			punctuationMark.equals("? "))
				upperCase = true;
			tow += punctuationMark;	
			str += tow;
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
	public void addToDictionary(String content,String filename)
	{
		BufferedWriter bw = null;
		FileWriter fw = null;
		try
		{
			fw = new FileWriter(filename, true);
			bw = new BufferedWriter(fw);
			if(!filename.equals("en.txt"))
				bw.write(System.lineSeparator() + content);
			else
				bw.write(content);
		}
		catch(IOException e)
		{}
		finally
		{
			try
			{
				if(bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} 
			catch(IOException e) 
			{}
		}
	}
		
	public String txtFileTrans(String fileName)
	{
		long startTime = 0;
		long endTime = 0;

		FileWriter fw = null;
		BufferedWriter bw = null;
		String str = "";
		String translatedLine;

		try{
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);

			fw = new FileWriter(fileName.concat("_translated"));
			bw = new BufferedWriter(fw);

			String nextLine = bufferedReader.readLine();

			startTime = System.nanoTime();

			while(nextLine!=null)
			{
				translatedLine = translateText(nextLine,true);
				str += translatedLine;
				bw.write(translatedLine);
				bw.newLine();
				nextLine = bufferedReader.readLine();
			}
			endTime = System.nanoTime();
			bufferedReader.close();
		}
		catch(IOException e)
		{
			return "file not found";
		}
		finally
		{
			try
			{
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			}
			catch(IOException e)
			{}
		}
		long duration = (endTime - startTime)/1000000;
		str += "\n\n";
		str += "Translation Complete\n";
		str += "Translated File created\n";
		str += "Time Taken: " +  duration + " ms";
		return str;
	}

	public boolean isInList(String x)
	{
		for(int i=0;i<nativeLanguageList.size();i++)
		{
			if(x.equals(nativeLanguageList.get(i)))
				return true;
		}
		return false;
	}

	public int getNativeListSize()
	{
		return nativeLanguageList.size();
	}

	public String getNativeListRow(int i)
	{
		return nativeLanguageList.get(i);
	}

	//Sorts punctuation
	public String punctuationSorter(String s)
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
		else if(p.charAt(p.length()-1)==':')
		{
			p =  p.substring(0,p.length()-1);
			punctuationMark = ": ";
		}

		else
			punctuationMark = " ";
		return p;
    }

	public int deleteLineFromDictionary(String fileName,String word,int x)
    {
		int lineNumber = -1;
		try
		{
			File realFile = new File(fileName);
			File tempFile = new File(fileName.concat("temp"));
			BufferedReader reader = new BufferedReader(new FileReader(realFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			String currentLine;
			int counter = 0;
			if(!word.equals(""))
				while((currentLine = reader.readLine()) != null)
				{
					counter++;
            		if(null!=currentLine && !currentLine.equalsIgnoreCase(word))
					{
               			writer.write(currentLine + System.getProperty("line.separator"));
            		}
					else
						lineNumber = counter;
        		}
			else
				while((currentLine = reader.readLine()) != null)
				{
					counter++;
            		if(x!=counter)
					{
               			writer.write(currentLine + System.getProperty("line.separator"));
            		}
        		}

        	writer.close();
        	reader.close();
        	tempFile.renameTo(realFile);
		}
		catch(IOException e)
		{}
		return lineNumber;
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

	public void getSpeech(){

		JavaSoundRecorder sound = new JavaSoundRecorder();  
		sound.recordWav();

	}

	

	public String speechToText(){

		String fullTxt = "";
		SpeechResult result;
		StreamSpeechRecognizer recognizer;
		InputStream stream;


		


		Configuration configuration = new Configuration();

		configuration.setAcousticModelPath("resources/en-us");
		configuration.setDictionaryPath("resources/cmudict-en-us.dict");
		configuration.setLanguageModelPath("resources/en-us.lm.bin");



		try {
			recognizer = new StreamSpeechRecognizer(configuration);
			stream = new FileInputStream(new File("tran/tst.wav")); //CHANGE THIS LINE to "resources/tran.wav"
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
