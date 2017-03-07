/**
* AC12001 Translator Program
* @author Evan Lott   160005234   07/03/16 
* @version v1.0
* Translator class :: 
* Handles translation by pulling wordlists into ArrayLists
*/
import java.util.*;
import java.io.*;

public class Translator
{
	private ArrayList<String> nativeLanguageList;
	private ArrayList<String> translationLanguageList;
	private String nativeLanguage;
	private String translationLanguage;
	private boolean[] languageChanged;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	private String text;

	public Translator()
	{
		nativeLanguageList = new ArrayList<String>();	
		translationLanguageList = new ArrayList<String>();	
		languageChanged = new boolean[2];
		text = "";
	}

	public void setTranslationLanguage(String translationLanguage)
	{
		this.translationLanguage = translationLanguage;	
		loadLanguage(false);
	}
	
	public void setNativeLanguage(String nativeLanguage)
	{
		this.nativeLanguage = nativeLanguage;	
		loadLanguage(true);
	}

	public void loadLanguage(boolean x)
	{
		if(x)
		{
			try
			{
				fileReader = new FileReader(nativeLanguage);
				bufferedReader = new BufferedReader(fileReader);
				String nextLine = bufferedReader.readLine();
				while(nextLine!=null)
				{
					nativeLanguageList.add(nextLine);
					nextLine = bufferedReader.readLine();
				}
			}
			catch(IOException e)
			{
				System.out.println(e);	
			}
		}
		else
		{
			try
			{
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
	
	public String translateText(String text)
	{
		String parsedText[] = text.split(" ");
		String str = "";
		for(int i = 0; i < parsedText.length; i++)
		{
			for(int f = 0; f < nativeLanguageList.size(); f++)
			{
				if(parsedText[i].equals(nativeLanguageList.get(f)))
				{
					parsedText[i] = translationLanguageList.get(f);					
					break;
				}	
			}
			str += parsedText[i] + " ";
		}
		return str;
	}	
	
	/*public String isUpperCase(String s)
	{
        if (Character.isUpperCase(s.charAt(0)))
        {
            return ;
        }
    }
    return true;
	}*/
}
