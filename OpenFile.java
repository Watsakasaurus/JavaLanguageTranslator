
import java.io.*;

public class OpenFile 
{
    public static void main (String[] args) throws IOException
    {
        String line;
        BufferedReader in;
        String filepath = "C:\\Users\\calumwatson\\Desktop\\testfile.txt";

        in = new BufferedReader(new FileReader(filepath));
        line = in.readLine();

        while(line != null)
        {
            System.out.println(line);
            line = in.readLine();
        }

        System.out.println(line);
    }

}
