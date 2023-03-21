import javax.imageio.ImageIO;
import javax.swing.*;

import MK1.UserInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class Master {
    private JFrame frame;
    private JLabel headerLabel;
    private JButton button1;
    private JButton button2;
    private JButton quitButton;

    public Master() {
        frame = new JFrame("FTN - Veles");
        frame.getContentPane().setBackground(new Color(51, 51, 51));

        // Header label
        headerLabel = new JLabel("Welcome to Veles Writing Assistant");
        headerLabel.setForeground(new Color(255, 255, 255));
        headerLabel.setFont(new Font("Arial", Font.BOLD, 36));
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        frame.add(headerLabel, BorderLayout.NORTH);

        
        ImageIcon spellCheckerIcon = new ImageIcon("");
        button1 = new JButton("Spell Checker", spellCheckerIcon);
        button1.setBackground(new Color(52, 152, 219));
        button1.setForeground(new Color(255, 255, 255));
        button1.setFont(new Font("Arial", Font.PLAIN, 24));
        button1.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Launch Spell Checker
                UserInterface program = new UserInterface();
                program.run();
            }
        });

        ImageIcon assistanceIcon = new ImageIcon("");
        button2 = new JButton("Writing Assistance", assistanceIcon);
        button2.setBackground(new Color(46, 204, 113));
        button2.setForeground(new Color(255, 255, 255));
        button2.setFont(new Font("Arial", Font.PLAIN, 24));
        button2.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Launch Veles
                Veles class2 = new Veles();
                class2.run();
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonPanel.setBackground(new Color(51, 51, 51));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Set up the quit button with icon
        ImageIcon quitIcon = new ImageIcon("C:\\\\Users\\\\Tempest\\\\Desktop\\\\Tests\\\\FTN2.png");
        quitButton = new JButton("Quit", quitIcon);
        quitButton.setBackground(new Color(255, 51, 51));
        quitButton.setForeground(new Color(255, 255, 255));
        quitButton.setFont(new Font("Arial", Font.PLAIN, 24));
        quitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        quitButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        System.exit(0);
        }
        });
        frame.add(quitButton, BorderLayout.SOUTH);
        // Set up the frame icon
        ImageIcon frameIcon = new ImageIcon("C:\\\\Users\\\\Tempest\\\\Desktop\\\\Tests\\\\FTN2.png");
        frame.setIconImage(frameIcon.getImage());

        // Set up the header image
        try {
            BufferedImage headerImage = ImageIO.read(new File("headerImage.jpg"));
            JLabel headerImageLabel = new JLabel(new ImageIcon(headerImage));
            frame.add(headerImageLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            // If the header image can't be loaded, just add the button panel
            System.err.println("Could not load header image: " + e.getMessage());
            frame.add(buttonPanel, BorderLayout.CENTER);
        }

        // Set up the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Creates and displays the GUI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Master();
            }
        });
    }
}

