package MK1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserInterface {
	
    private Input input = new Input();
    private SpellCheck current;

    private int inputInt(String msg, int min, int max) {
        int num = -1;
        boolean isValid = false;
        while (!isValid) {
            String inputString = JOptionPane.showInputDialog(msg);
            try {
                num = Integer.parseInt(inputString);
                if (num >= min && num <= max) {
                    isValid = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Error! " + msg + " has to be between " + min + " - " + max + "!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error! " + msg + " must be a valid integer!");
            }
        }
        return num;
    }

    public static String inputString(String msg) {
        String str = JOptionPane.showInputDialog(msg);
        while (str.length() == 0) {
            JOptionPane.showMessageDialog(null, "Error! " + msg + " cannot be empty!");
            str = JOptionPane.showInputDialog(msg);
        }
        return str;
    }

  
    private void displaySimilarWords(StringArray s) {
        StringBuilder sb = new StringBuilder();
        sb.append("Option 1 - Ignore, Word Is Correct\n");
        sb.append("Option 2 - Retype Word\n");
        sb.append("-----------------------\n");
        for (int j = 0; j < s.size(); j++) {
            sb.append("Option ").append(j + 3).append(" ").append(s.get(j)).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void displayOptions() {
    	displaySimilarWords(new StringArray());
    }


    private int chooseCorrections(StringArray similarWords) {
    	
        int choice = 0;
        if (similarWords.size() == 0) {
            JOptionPane.showMessageDialog(null, "No Suggestions Generated");
        } else {
            displaySimilarWords(similarWords);
        }
        while (choice < 1 || choice > similarWords.size() + 2) {
            choice = inputInt("Option", 1, similarWords.size() + 2);
        }
        return choice;
    }

    private void addCorrection(int choice, int i, StringArray a, StringArray c, StringArray s) {
        if (choice == 1) {
            c.add(a.get(i));
        } else if (choice == 2) {
            c.add(inputString("Corrected Word"));
        } else if (choice > 2 & choice <= s.size() + 2) {
            c.add(s.get(choice - 3));
        }
    }

    private StringArray suggestCorrections(StringArray a) {
        StringArray correctedWords = new StringArray();
        JOptionPane.showMessageDialog(null, "Found " + a.size() + " word/s not in our dictionary.");
        for (int i = 0; i < a.size(); i++) {
            String incorrectWord = a.get(i);
            JOptionPane.showMessageDialog(frame, "Incorrect Word " + (i + 1) + " - " + a.get(i));
            int choice = chooseCorrections(current.similarWords(incorrectWord));
            addCorrection(choice, i, a, correctedWords, current.similarWords(incorrectWord));
        }
        return correctedWords;
    }

    private String checking(String text) {
        this.current = new SpellCheck(text);
        StringArray incorrectWords = current.getIncorrectWords();
        if (incorrectWords.size() == 0) {
            JOptionPane.showMessageDialog(null, "All the words in your text are in our dictionary!");
            return text;
        } else {
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
    private void saveQuestion(String correctedText, String text) {
        JTextArea messageArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        if (correctedText.equals(text)) {
            int result = JOptionPane.showConfirmDialog(null, "All the words in your text are in our dictionary! Save text to file?", "Save Text", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                JFileChooser fileChooser = new JFileChooser();
                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    try {
                        FileWriter writer = new FileWriter(fileToSave);
                        writer.write(text);
                        writer.close();
                        messageArea.setText("File saved successfully!");
                    } catch (IOException e) {
                        messageArea.setText("Error saving file: " + e.getMessage());
                    }
                }
            }
        } else {
            int result = JOptionPane.showConfirmDialog(null, correctedText + " Save corrected text to file?", "Save Corrected Text", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                JFileChooser fileChooser = new JFileChooser();
                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    try {
                        FileWriter writer = new FileWriter(fileToSave);
                        writer.write(correctedText);
                        writer.close();
                        messageArea.setText("File saved successfully!");
                    } catch (IOException e) {
                        messageArea.setText("Error saving file: " + e.getMessage());
                    }
                }
            }
        }

        JOptionPane.showMessageDialog(null, scrollPane, "Save File", JOptionPane.PLAIN_MESSAGE);
    }

    private void saveOptions() {
        JOptionPane.showMessageDialog(null, "1 - Yes\n2 - No", "Save Options", JOptionPane.PLAIN_MESSAGE);
    }

    private void saveTextToFile(String text) {
        String fileName = JOptionPane.showInputDialog(null, "Filename (full path name):", "Save Text to File", JOptionPane.PLAIN_MESSAGE);
        if (fileName == null || fileName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error! Filename cannot be empty!", "Save Text to File", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            File file = new File(fileName);
            FileWriter writer = new FileWriter(file);
            writer.write(text + "\n");
            writer.close();
            JOptionPane.showMessageDialog(null, "Text saved to " + fileName, "Save Text to File", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error saving text to file:\n" + e.getMessage(), "Save Text to File", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void save(String correctedText, String text) {
        String message = correctedText.equals(text) ? "All the words in your text are in our dictionary!\nSave Text To File?" : correctedText + "\nSave Corrected Text To File?";
        JTextArea textArea = new JTextArea(message);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        int dialogResult = JOptionPane.showConfirmDialog(null, scrollPane, "Save Text", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            saveOptions();
            int saveChoice = inputInt("Choice", 1, 2);
            if (saveChoice == 1) {
                saveTextToFile(correctedText);
            }
        }else if (dialogResult == JOptionPane.NO_OPTION){
        	
        
		}
    }

    private void check(String text) {
        String correctedText = checking(text);
        save(correctedText, text);
    }

    private void validateText(String text, String fileName) {
        if (text != null) {
            text = text.replaceAll("\n", ""); // removes all next line characters
            if (text.length() == 0) {
                JOptionPane.showMessageDialog(null, fileName + " Is Empty!");
            } else {
                check(text);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Text Could Not Be Read From " + fileName + "!");
        }
    }

    private void checkType() {
        String text = JOptionPane.showInputDialog(null, "Enter Text That You Want To Check", "Text", JOptionPane.PLAIN_MESSAGE);
        if (text != null) {
            check(text);
        }
    }

    private void checkFile() {
        String fileName = JOptionPane.showInputDialog(null, "Filename (full path name)", "File", JOptionPane.PLAIN_MESSAGE);
        if (fileName != null) {
            String fileContent = readFile(fileName);
            if (fileContent == null) {
                JOptionPane.showMessageDialog(null, "File " + fileName + " could not be found.");
                return;
            }
            validateText(fileContent, fileName);
        }
    }

    private JFrame frame;
    private void displayMainMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Main Menu");
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(20));
        panel.add(title);
        panel.add(Box.createVerticalStrut(20));

        JButton checkTypingButton = new JButton("Check Text By Typing");
        JButton checkFileButton = new JButton("Check Text From File");
        JButton quitButton = new JButton("Quit");

        checkTypingButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        checkFileButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        quitButton.setAlignmentX(JButton.CENTER_ALIGNMENT);

        panel.add(checkTypingButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(checkFileButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(quitButton);

        frame.setContentPane(panel);
        frame.pack();
    }
    public void run() {
        // JFrame that holds the main menu
        JFrame frame = new JFrame("Spell Checker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        // Title label for  the header panel
        JLabel titleLabel = new JLabel("Welcome to Spell Checker");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);



        // Footer panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(Color.WHITE);

        // Footer panel
        JLabel newLabel = new JLabel("dr Marija Blagojević************dr Lena Tica************Petar Matijević 66/2019");
        newLabel.setFont(new Font("Arial", Font.BOLD, 12));
        newLabel.setForeground(Color.BLACK);
        footerPanel.add(newLabel);

        // Main menu panel with a GridLayout
        JPanel mainMenuPanel = new JPanel(new GridLayout(3, 1, 10, 10)); 

        // Buttons for each option
        JButton checkTypeButton = new JButton("Check Text By Typing");
        JButton checkFileButton = new JButton("Check Text From File");
        JButton quitButton = new JButton("Quit");

        // Set the background color of the buttons
        checkTypeButton.setBackground(Color.LIGHT_GRAY);
        checkFileButton.setBackground(Color.gray);
        quitButton.setBackground(Color.DARK_GRAY);

        // Set the foreground color of the buttons
        checkTypeButton.setForeground(Color.WHITE);
        checkFileButton.setForeground(Color.WHITE);
        quitButton.setForeground(Color.WHITE);

        // Set the font of the buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        checkTypeButton.setFont(buttonFont);
        checkFileButton.setFont(buttonFont);
        quitButton.setFont(buttonFont);

        // Action listeners for the buttons
        checkTypeButton.addActionListener(e -> checkType());
        checkFileButton.addActionListener(e -> checkFile());
        quitButton.addActionListener(e -> System.exit(0));

        // Buttons on the main menu panel
        mainMenuPanel.add(checkTypeButton);
        mainMenuPanel.add(checkFileButton);
        mainMenuPanel.add(quitButton);

        // Header panel, main menu panel, and footer panel on the frame
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);
        frame.getContentPane().add(mainMenuPanel, BorderLayout.CENTER);
        frame.getContentPane().add(footerPanel, BorderLayout.SOUTH);

        // Set the preferred size of the frame
        frame.setPreferredSize(new Dimension(500, 390));
        frame.setLocation(710, 300);

        // Pack and display the frame
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.run();
    }
    }
