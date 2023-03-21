package MK1;

import java.io.*;


public class FileOutput implements Closeable, Flushable
{
    
      //Name of the file being written to.
     
    protected final String filename;

    protected final BufferedWriter writer;

    public FileOutput(final String filename, boolean append)
    {
        this.filename = filename;
 
        FileWriter fw = null;
        try
        {
            fw = new FileWriter(filename,append);
        }
        catch (IOException e)
        {
            error("Cannot open file: " + filename);
        }
        writer = new BufferedWriter(fw);
    }


    public FileOutput(final File file, boolean append)
    {
        this(file.getName(),append);
    }


    public FileOutput(final String filename)
    {
        this(filename,false);
    }


    public FileOutput(final File file)
    {
        this(file.getName(),false);
    }

    public final synchronized void flush()
    {
        try
        {
            writer.flush();
        }
        catch (IOException e)
        {
            error("Cannot flush file: " + filename);
        }
    }

   // Close the file
    public final synchronized void close()
    {
        flush();
        try
        {
            writer.close();
        }
        catch (IOException e)
        {
            error("Cannot close file: " + filename);
        }
    }

 
    public final synchronized void writeInt(final int i)
    {
        try
        {
            writer.write(Integer.toString(i));
        }
        catch (IOException e)
        {
            error("writeInteger failed to file: " + filename);
        }
    }

 
    public final synchronized void writeLong(final long l)
    {
        try
        {
            writer.write(Long.toString(l));
        }
        catch (IOException e)
        {
            error("writeLong failed to file: " + filename);
        }
    }


    public final synchronized void writeFloat(final float f)
    {
        try
        {
            writer.write(Float.toString(f));
        }
        catch (IOException e)
        {
            error("writeFloat failed to file: " + filename);
        }
    }


    public final synchronized void writeDouble(final double d)
    {
        try
        {
            writer.write(Double.toString(d));
        }
        catch (IOException e)
        {
            error("writeDouble failed to file: " + filename);
        }
    }


    public final synchronized void writeChar(final char c)
    {
        try
        {
            writer.write(c);
        }
        catch (IOException e)
        {
            error("writeChar failed to file: " + filename);
        }
    }


    public final synchronized void writeString(final String s)
    {
        try
        {
            writer.write(s);
        }
        catch (IOException e)
        {
            error("writeString failed to file: " + filename);
        }
    }


    public final synchronized void writeEndOfLine()
    {
        try
        {
            writer.newLine();
        }
        catch (IOException e)
        {
            error("writeEndOfLine failed to file: " + filename);
        }
    }

  
    private void error(final String message)
    {
        System.err.println(message);
        System.err.println("Unable to continue executing program.");
        System.exit(1);
    }
}
