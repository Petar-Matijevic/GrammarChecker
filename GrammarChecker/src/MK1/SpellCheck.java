package MK1;


public class SpellCheck
{
    private String text;
    private StringArray wordsInText;
    private StringArray dictionary;
    private StringArray textArray;
    private StringArray incorrectWords;

    public SpellCheck(String text)
    {
        this.text = text;
        this.wordsInText = splitText(tidyText(text));
        innitDictionary();
        this.textArray = splitText(text);
        this.incorrectWords = findIncorrectWords();
    }

    public String getText() { return text; }

    public StringArray getWordsInText() { return wordsInText; }

    public StringArray getDictionary() { return dictionary; }

    public StringArray getTextArray() { return textArray; }

    public StringArray getIncorrectWords() { return incorrectWords; }

    public StringArray similarWords(String word) {
        StringArray similar = new StringArray(); 
        int largest = 0;
        int wordLength = word.length();
        int similarityThreshold = Math.max(2, (int) Math.ceil(wordLength / 4.0)); // adjust similarity threshold based on word length
        for (int i = 0; i < dictionary.size(); i++) {
            String check = dictionary.get(i);
            int similarity = similarityScore(check, word);
            int differenceInLength = Math.abs(check.length() - wordLength);
            if (similarity >= largest && differenceInLength < 2) {
                similar.insert(0, check);
                largest = similarity;
            } else if (similarity >= similarityThreshold) {
                similar.add(check);
            }
        }
        return topResults(similar);
    }


    public String replaceIncorrectText(StringArray correctedWords)
    {
        for (int i = 0; i < incorrectWords.size(); i++)
        {
            int index = wordsInText.indexOf(incorrectWords.get(i));// find index of wrong word replace wrong word with corrected word
            textArray.set(index, correctedWords.get(i));            
        }
        return convertTextArrayToString();
    }

    private String convertTextArrayToString()
    {
        int length = textArray.size();
        String[] temp = new String[length];
        for (int i = 0; i < length; i++)
        {
            temp[i] = textArray.get(i);
        }
        return String.join(" ", temp);
    }
    
    private StringArray topResults(StringArray a)
    {
        int loop = Math.min(5,a.size());
        StringArray temp = new StringArray();
        for (int i = 0; i < loop; i++)
        {
            temp.add(a.get(i));
        }
        return temp;// returns the top highest ranked words
    }

    private int similarityScore(String check, String word) {
        // Convert both words to lowercase to make the comparison case-insensitive
        check = check.toLowerCase();
        word = word.toLowerCase();

        // If the words are the same, return the length of the word
        if (check.equals(word)) {
            return check.length();
        }

        // Otherwise, use dynamic programming to calculate the longest common subsequence
        int n = check.length();
        int m = word.length();
        int[][] dp = new int[n+1][m+1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (check.charAt(i-1) == word.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        // Return the length of the longest common subsequence
        return dp[n][m];
    }


    private StringArray findIncorrectWords()
    {
        StringArray temp = new StringArray();
        for (int i = 0; i < wordsInText.size(); i++)
        {
            String word = wordsInText.get(i);
            if (!dictionary.contains(word))
            {
                temp.add(word);
            }
        }
        return temp;
    }

    private StringArray splitText(String t)
    {
        String[] words = t.split("\\s");// splits t everywhere there is a space
        StringArray temp = new StringArray();
        for (String word : words)
        {
            temp.add(word);
        }
        return temp;
    }

    private String tidyText(String t)
    {
        return t.replaceAll("[^a-zA-Z\\s-]", ""); // removes all punctuation apart from the dash
    }

    private void innitDictionary()
    {
        dictionary = new StringArray();
        try
        {
            FileInput input = new FileInput("C:\\Users\\Tempest\\Desktop\\Diplomski\\Program\\GrammarChecker\\src\\MK1/dictionary2.txt");
            generatingDictionary(input);
        }
        catch (Exception error)
        {
            System.err.println("Dictionary Could Not Be Initialized");
            System.exit(1);
        }
    }

    private void generatingDictionary(FileInput input)
    {
        while (input.hasNextLine())
        {
            String word = input.nextLine();
            dictionary.add(word);
        }
    }
}