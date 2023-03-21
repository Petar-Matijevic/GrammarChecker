package MK1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class FileInput extends Input
{

    public FileInput(final String fileName) throws Exception
    {
        try
        {
            scanner = new Scanner(new FileInputStream(fileName));
        }
        catch (FileNotFoundException fnfe)
        {
            
            throw new Exception("fnfe");
          
        }
    }


    public FileInput(final FileInputStream fileStream)
    {
        super(fileStream);
    }
}
