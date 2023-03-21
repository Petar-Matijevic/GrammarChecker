package MK1;


public class UserInterfaceConsole {

	
	    private Input input = new Input();
	    private SpellCheck current;

	    private int inputInt(String msg, int min, int max)
	    {
	        System.out.print(msg + " : ");
	        int num = Integer.parseInt(input.nextLine());
	        while (num < min | num > max)
	        {
	            System.out.println("Error! " + msg + " has to be between " + min + " - " + max + "!");
	            System.out.print(msg + " : ");
	            num = Integer.parseInt(input.nextLine());
	        }
	        return num; 
	    }

	    private String inputString(String msg)
	    {
	        System.out.print(msg + " : ");
	        String str = input.nextLine();
	        while (str.length() == 0)
	        {
	            System.out.println("Error! " + msg + " cannot be empty!");
	            System.out.print(msg + " : ");
	            str = input.nextLine();
	        }
	        return str;
	    }

	    private void displayDefault()
	    {
	        System.out.println("Option 1 - Ignore, Word Is Correct");
	        System.out.println("Option 2 - Retype Word");
	        System.out.println("-----------------------");
	    }

	    private void displaySimilarWords(StringArray s)
	    {
	        for (int j = 0; j < s.size(); j++)
	        {
	            System.out.println("Option " + (j + 3) + " " + s.get(j));
	        }
	    }

	    private int chooseCorrections(StringArray similarWords)
	    {
	        displayDefault();
	        int choice;
	        if (similarWords.size() == 0)
	        {
	            System.out.println("No Suggestions Generated");
	        }
	        else
	        {
	            displaySimilarWords(similarWords);
	        }
	        choice = inputInt("Option", 1, similarWords.size() + 2);
	        return choice;
	    }

	    private void addCorrection(int choice, int i, StringArray a, StringArray c, StringArray s)
	    {
	        if (choice == 1)
	        {
	            c.add(a.get(i));
	        }
	        else if (choice == 2)
	        {
	            c.add(inputString("Corrected Word"));
	        }
	        else if (choice > 2 & choice <= s.size() + 2)
	        {
	            c.add(s.get(choice - 3));
	        }
	    }

	    private StringArray suggestCorrections(StringArray a)
	    {
	        StringArray correctedWords = new StringArray();
	        System.out.println("Found " + a.size() + " word/s not in our dictionary.");
	        System.out.println("");
	        for (int i = 0; i < a.size(); i++)
	        {
	            System.out.println("Incorrect Word " + (i + 1) + " - " + a.get(i));
	            StringArray similarWords = current.similarWords(a.get(i)); //finds similar words to the incorrect word
	            int choice = chooseCorrections(similarWords);
	            addCorrection(choice, i, a, correctedWords, similarWords);
	        }
	        return correctedWords;
	    }

	    private String checking(String text)
	    {
	        this.current = new SpellCheck(text);
	        StringArray incorrectWords = current.getIncorrectWords();
	        if (incorrectWords.size() == 0) { return text; }
	        else
	        {
	            StringArray correctedWords = suggestCorrections(incorrectWords);
	            return current.replaceIncorrectText(correctedWords);
	        }
	    }

	    private String readFile(String fName)
	    {
	        try
	        {
	            FileInput in = new FileInput(fName);
	            return extractText(in);
	        }
	        catch (Exception error)
	        {
	            return null;
	        }
	    }

	    private String extractText(FileInput in)
	    {
	        String text = "";
	        while (in.hasNextChar())
	        {
	            text += in.nextChar();
	        }
	        in.close();
	        return text;
	    }

	    private void saveQuestion(String correctedText, String text)
	    {
	        if (correctedText.equals(text))
	        {
	            System.out.println("All the words in your text are in our dictionary!");
	            System.out.println("Save Text To File?");
	        }
	        else
	        {
	            System.out.println(correctedText);
	            System.out.println("Save Corrected Text To File?");
	        }
	    }

	    private void saveOptions()
	    {
	        System.out.println("1 - Yes");
	        System.out.println("2 - No");
	    }

	    private void saveTextToFile(String text)
	    {
	        String fileName = inputString("Filename (full path name)");
	        FileOutput out = new FileOutput(fileName);
	        out.writeString(text + "\n");
	        out.close();
	        System.out.println("Text saved to " + fileName);
	    }

	    private void save(String correctedText, String text)
	    {
	        saveQuestion(correctedText, text);
	        saveOptions();
	        int saveChoice = inputInt("Choice", 1,2);
	        if (saveChoice==1) { saveTextToFile(correctedText); }
	    }

	    private void check(String text)
	    {
	        String correctedText = checking(text);
	        save(correctedText, text);
	    }

	    private void validateText(String text, String fileName)
	    {
	        if (text != null)
	        {
	            text = text.replaceAll("\n", ""); // removes all next line characters
	            if (text.length() == 0) { System.out.println(fileName + " Is Empty!"); }
	            else { check(text); }
	        }
	        else { System.out.println("Text Could Not Be Read From " + fileName + "!"); }
	    }

	    private void checkType()
	    {
	        System.out.println("Enter Text That You Want To Check");
	        String text = inputString("Text");
	        check(text);
	    }

	    private void checkFile()
	    {
	        String fileName = inputString("Filename (full path name)");
	        if (readFile(fileName) == null)
	        {
	            System.out.println("File " + fileName + " could not be found.");
	            return;
	        }
	        String text = readFile(fileName);
	        validateText(text, fileName);
	    }

	    private void displayMainMenu()
	    {
	        System.out.println("");
	        System.out.println("        Main Menu");
	        System.out.println("1 - Check Text By Typing");
	        System.out.println("2 - Check Text From File");
	        System.out.println("3 - Quit");
	    }

	    public void run()
	    {
	        System.out.println("     Grammar Checker");
	        while(true)
	        {
	            displayMainMenu();
	            int userInput = inputInt("Choice", 1,3);
	            switch (userInput)
	            {
	                case 1 -> checkType();
	                case 2 -> checkFile();
	                case 3 -> { return; }
	            }
	        }
	    }
	}
