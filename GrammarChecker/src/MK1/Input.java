package MK1;

import java.io.Closeable;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Input implements Closeable, Iterator<String>
{

    protected Scanner scanner;


    public Input()
    {
        this(System.in);
    }


    public Input(final InputStream in)
    {
        scanner = new Scanner(in);
    }


    
     // Close the file when finished
     
    public void close()
    {
        scanner.close();
    }


    public boolean hasNext()
    {
        boolean returnValue = false;
        try
        {
            returnValue = scanner.hasNext();
        }
        catch (IllegalStateException e)
        {
            illegalStateExceptionHandler();
        }
        return returnValue;
    }

 
    public String next()
    {
        String returnValue = null;
        try
        {
            returnValue = scanner.next();
        }
        catch (NoSuchElementException nsee)
        {
            noSuchElementHandler();
        }
        catch (IllegalStateException ise)
        {
            illegalStateExceptionHandler();
        }
        return returnValue;
    }


    public void remove()
    {
    }


    public boolean hasNextChar()
    {

 

        return hasNext();
    }


    public char nextChar()
    {
        char returnValue = '\0';
        try
        {
            returnValue = scanner.findWithinHorizon("(?s).", 1).charAt(0);
        }
        catch (IllegalArgumentException iae)
        {
    
            System.exit(1);
        }
        catch (IllegalStateException ise)
        {
            illegalStateExceptionHandler();
        }
        return returnValue;
    }

    public boolean hasNextInt()
    {
        boolean returnValue = false;
        try
        {
            returnValue = scanner.hasNextInt();
        }
        catch (IllegalStateException e)
        {
            illegalStateExceptionHandler();
        }
        return returnValue;
    }

   
    public int nextInt()
    {
        int returnValue = 0;
        try
        {
            returnValue = scanner.nextInt();
        }
        catch (InputMismatchException ime)
        {
            inputMismatchExceptionHandler("int");
        }
        catch (NoSuchElementException nsee)
        {
            noSuchElementHandler();
        }
        catch (IllegalStateException ise)
        {
            illegalStateExceptionHandler();
        }
        return returnValue;
    }


    public int nextInt(final int radix)
    {
        int returnValue = 0;
        try
        {
            returnValue = scanner.nextInt(radix);
        }
        catch (InputMismatchException ime)
        {
            inputMismatchExceptionHandler("int");
        }
        catch (NoSuchElementException nsee)
        {
            noSuchElementHandler();
        }
        catch (IllegalStateException ise)
        {
            illegalStateExceptionHandler();
        }
        return returnValue;
    }


    public boolean hasNextLong()
    {
        boolean returnValue = false;
        try
        {
            returnValue = scanner.hasNextLong();
        }
        catch (IllegalStateException e)
        {
            illegalStateExceptionHandler();
        }
        return returnValue;
    }


    public long nextLong()
    {
        long returnValue = 0;
        try
        {
            returnValue = scanner.nextLong();
        }
        catch (InputMismatchException ime)
        {
            inputMismatchExceptionHandler("long");
        }
        catch (NoSuchElementException nsee)
        {
            noSuchElementHandler();
        }
        catch (IllegalStateException ise)
        {
            illegalStateExceptionHandler();
        }
        return returnValue;
    }


    public long nextLong(final int radix)
    {
        long returnValue = 0;
        try
        {
            returnValue = scanner.nextLong(radix);
        }
        catch (InputMismatchException ime)
        {
            inputMismatchExceptionHandler("long");
        }
        catch (NoSuchElementException nsee)
        {
            noSuchElementHandler();
        }
        catch (IllegalStateException ise)
        {
            illegalStateExceptionHandler();
        }
        return returnValue;
    }

 
    public boolean hasNextBigInteger()
    {
        boolean returnValue = false;
        try
        {
            returnValue = scanner.hasNextBigInteger();
        }
        catch (IllegalStateException e)
        {
            illegalStateExceptionHandler();
        }
        return returnValue;
    }


    public BigInteger nextBigInteger()
    {
        BigInteger returnValue = new BigInteger("0");
        try
        {
            returnValue = scanner.nextBigInteger();
        }
        catch (InputMismatchException ime)
        {
            inputMismatchExceptionHandler("BigInteger");
        }
        catch (NoSuchElementException nsee)
        {
            noSuchElementHandler();
        }
        catch (IllegalStateException ise)
        {
            illegalStateExceptionHandler();
        }
        return returnValue;
    }


    public BigInteger nextBigInteger(final int radix)
    {
        BigInteger returnValue = new BigInteger("0");
        try
        {
            returnValue = scanner.nextBigInteger(radix);
        }
        catch (InputMismatchException ime)
        {
            inputMismatchExceptionHandler("BigInteger");
        }
        catch (NoSuchElementException nsee)
        {
            noSuchElementHandler();
        }
        catch (IllegalStateException ise)
        {
            illegalStateExceptionHandler();
        }
        return returnValue;
    }


    public boolean hasNextFloat()
    {
        boolean returnValue = false;
        try
        {
            returnValue = scanner.hasNextFloat();
        }
        catch (IllegalStateException e)
        {
            illegalStateExceptionHandler();
        }
        return returnValue;
    }


    public float nextFloat()
    {
        float returnValue = 0;
        try
        {
            returnValue = scanner.nextFloat();
        }
        catch (InputMismatchException ime)
        {
            inputMismatchExceptionHandler("float");
        }
        catch (NoSuchElementException nsee)
        {
            noSuchElementHandler();
        }
        catch (IllegalStateException ise)
        {
            illegalStateExceptionHandler();
        }
        return returnValue;
    }


    public boolean hasNextDouble()
    {
        boolean returnValue = false;
        try
        {
            returnValue = scanner.hasNextDouble();
        }
        catch (IllegalStateException e)
        {
            illegalStateExceptionHandler();
        }
        return returnValue;
    }


    public double nextDouble()
    {
        double returnValue = 0;
        try
        {
            returnValue = scanner.nextDouble();
        }
        catch (InputMismatchException ime)
        {
            inputMismatchExceptionHandler("double");
        }
        catch (NoSuchElementException nsee)
        {
            noSuchElementHandler();
        }
        catch (IllegalStateException ise)
        {
            illegalStateExceptionHandler();
        }
        return returnValue;
    }

 
    public boolean hasNextBigDecimal()
    {
        boolean returnValue = false;
        try
        {
            returnValue = scanner.hasNextBigDecimal();
        }
        catch (IllegalStateException e)
        {
            illegalStateExceptionHandler();
        }
        return returnValue;
    }


    public BigDecimal nextBigDecimal()
    {
        BigDecimal returnValue = new BigDecimal("0");
        try
        {
            returnValue = scanner.nextBigDecimal();
        }
        catch (InputMismatchException ime)
        {
            inputMismatchExceptionHandler("BigDecimal");
        }
        catch (NoSuchElementException nsee)
        {
            noSuchElementHandler();
        }
        catch (IllegalStateException ise)
        {
            illegalStateExceptionHandler();
        }
        return returnValue;
    }


    public boolean hasNextLine()
    {
        boolean returnValue = false;
        try
        {
            returnValue = scanner.hasNextLine();
        }
        catch (IllegalStateException e)
        {
            illegalStateExceptionHandler();
        }
        return returnValue;
    }

    public String nextLine()
    {
        String returnValue = null;
        try
        {
            returnValue = scanner.nextLine();
        }
        catch (NoSuchElementException nsee)
        {
            noSuchElementHandler();
        }
        catch (IllegalStateException ise)
        {
            illegalStateExceptionHandler();
        }
        return returnValue;
    }


    private void illegalStateExceptionHandler()
    {
        System.err.println("Input has been closed.");
        System.exit(1);
    }


    private void inputMismatchExceptionHandler(final String type)
    {
        System.err.println("Input did not represent " +
                (type.equals("int") ? "an" : "a") + " " + type + " value.");
        System.exit(1);
    }

    private void noSuchElementHandler()
    {
        System.err.println("No input to be read.");
        System.exit(1);
    }
}
