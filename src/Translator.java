/**
* AC12001 Translator Program
* @author Evan Lott   160005234   07/03/16 
* @version v1.0
* Translator class :: 
* Handles translation by pulling wordlists into ArrayLists
*/

//import the neccesary libraries
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

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
				fileReader = new FileReader(nativeLanguage);
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
				fileReader = new FileReader(translationLanguage);
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
}
