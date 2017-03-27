/**
* AC12001 Translator Program
* @author   Evan Lott   
* @author   Joe Riemersma
* @author   Calum Watson
* @author   Lucy Murphy
* @version   1.0   07/03/16
* Translator class :: Handles translation by pulling wordlists into ArrayLists
*/

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

public class Translator
{
	//Initalises standard array lists for holding the dictionaries
	private ArrayList<String> nativeLanguageList;
	private ArrayList<String> translationLanguageList;

	//Initalises a HashMap to store translated words so the dictionary files dont have 
	//to be searched twice for words already translated
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

	/**
    * Constructor: Initalises the default values for the fields
	*/
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

	/**
    * getTranslationLanguage: returns the transational language String
	* @return translationLanguage
	*/
	public String getTranslationLanguage()
	{
		return translationLanguage;
	}
	
	/**
    * getNativeLanguage: returns the native language String
	* @return nativeLanguage
	*/ 
	public String getNativeLanguage()
	{
		return nativeLanguage;
	}

	//
	
	/**
    * setTranslationLanguage: Sets the transational language to a new language then loads the language from 
	* the file to the arraylist
	* @param translationLanguage : What the new translationLanguage String will be set to
	* @param translationLanguageFile : What the new translationLanguageFile String will be set to
	*/ 
	public void setTranslationLanguage(String translationLanguage,String translationLanguageFile)
	{
		this.translationLanguage = translationLanguage;	
		this.translationLanguageFile = translationLanguageFile;
		loadLanguage(false);
	}

	/**
    * setNativeLanguage: Sets the native language to a new language then loads the language from
	* the file to the arrayList
	* @param nativeLanguage : What the new nativeLanguage String will be set to
	* @param nativeLanguageFile : What the new nativeLanguageFile String will be set to
	*/ 
	public void setNativeLanguage(String nativeLanguage,String nativeLanguageFile)
	{
		this.nativeLanguage = nativeLanguage;	
		this.nativeLanguageFile = nativeLanguageFile;
		loadLanguage(true);
	}

	/**
    * loadLanguage: loads the language from the .txt file
	* @param x : If x is true, the native language will be loaded, else the translation language
	*/ 
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
	
	/**
    * translateText: translates the inputted
	* @param text : the text to be translated
	* @param emptyHashMap : If true then the HashMap will be cleared
	* @return : returns the translated String
	*/ 
	public String translateText(String text,boolean emptyHashMap)
	{
		//By default upperCase is true because the text will start with a Capital letter
		upperCase = true;

		//emptys the HashMap if true
		if(emptyHashMap)
			translationMade.clear();
		//Splits the text by tab or whitespace
		String parsedText[] = text.split("\\s+");

		//Ensures that if the translation field has just been cleared the translationMade is cleared
		//and the translation of empty (always empty also) is returned
		if(parsedText[0].equals(""))
		{
			translationMade.clear();
			return "";
		}
		
		//Creates new empty strings
		String tow = "";
		String str = "";

		//Runs through each word in the array list.
		//If the hashtable already contains the translation then the corresponding translation
		//is used
		//If not then the translation is compared against the wordlist
		//The wordlist is all lower case, and the inputted text is sorted so that each character is lower case
		//and punctuation marks are removed and added back after translation
		//The str String is concatenated with all the translations so that a single translated String can be returned
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

			//The translated word has its punctuationMark added back to the end
			tow += punctuationMark;	
			str += tow;
		}
		return str;
	}	

	/**
    * addToDictionary: adds unknown word and users entry to corresponding dictionaries
	* @param content : the word to be added
	* @param filename : the name of the txt file the word is being added to
	*/ 
	public void addToDictionary(String content,String filename)
	{
		BufferedWriter bw = null;
		FileWriter fw = null;
		try
		{
			fw = new FileWriter(filename, true);
			bw = new BufferedWriter(fw);
			bw.write(System.getProperty("line.separator") + content + System.getProperty("line.separator"));
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
	
	/**
    * txtFileTrans: translates a text file and returns the translation and statistics
	* @param filename : the name of the txt file to be translated
	* @return : the String of the translated file
	*/ 
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
	
	/**
    * isInList: checks if a String is in the native array list
	* @param x : the string to check the ArrayList for
	* @return : true if the word is in the array list, false if not
	*/ 
	public boolean isInList(String x)
	{
		for(int i=0;i<nativeLanguageList.size();i++)
		{
			if(x.equals(nativeLanguageList.get(i)))
				return true;
		}
		return false;
	}

	/**
    * getNativeSize: returns the size of the nativeLanguageList
	* @return : returns the size
	*/ 
	public int getNativeListSize()
	{
		return nativeLanguageList.size();
	}

	/**
    * getNativeListRow: returns the String corresponding to the index i
	* @return : the String word at the index i
	* @param i : the index desired
	*/ 
	public String getNativeListRow(int i)
	{
		return nativeLanguageList.get(i);
	}

	/**
    * punctuationSorter: sorts punctuation
	* @return : The sorted String (no punctuation or capitalization)
	* @param s : The String to be sorted
	*/ 
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

	/**
    * deleteLineFromDictionary: deletes a line from a given dictionary
	* @return : the number of the line deleted
	* @param fileName : The file to delete from
	* @param word : the word to delete
	* @param x : the line to delete (if word = "")
	*/ 
	public int deleteLineFromDictionary(String fileName,String word,int x)
    {
		int lineNumber = -1;
		try
		{
			File file = new File(fileName);
			File tempFile = new File(fileName.concat("temp"));
			BufferedReader r = new BufferedReader(new FileReader(file));
			BufferedWriter w = new BufferedWriter(new FileWriter(tempFile));
			String line;
			int counter = 0;
			if(!word.equals(""))
				while((line = r.readLine()) != null)
				{
					counter++;
            		if(null!=line && !line.equalsIgnoreCase(word))
					{
               			w.write(line + System.getProperty("line.separator"));
            		}
					else
						lineNumber = counter;
        		}
			else
				while((line = r.readLine()) != null)
				{
					counter++;
            		if(x!=counter)
					{
               			w.write(line + System.getProperty("line.separator"));
            		}
        		}

        	w.close();
        	r.close();
			tempFile.renameTo(file);
		}
		catch(IOException e)
		{}
		return lineNumber;
    }
	
	/**
    * makeSentenceStarter: Returns x with the first char uppercase
	* @return : the String of x but with the first letter capitalized
	* @param x : the String to have the first letter capitalized
	*/ 
	private String makeSentenceStarter(String x)
	{
		String y = "";
		y += Character.toUpperCase(x.charAt(0));
		y += x.substring(1,x.length());
		upperCase = false;
		return y;
	}
}
