
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.Utilities;
import javax.swing.text.html.HTML;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;



public class VelesGUI extends Thread{
	 FileHandler fileHandler;
	 java.util.List<Thread> threads;
	 JFrame frame;
	 HashMap<Object, Action> textPaneActions;
	 JMenuBar menuBar;
	 JMenu styleMenu;
	 JMenu editMenu;
	 JMenu checkMenu;
	 JMenu assistMenu;
	 JMenu creditsMenu;
	 UndoManager undoManager;
	 StyledDocument doc;
	 UndoAction undoAction;
	 Action cutAction;
	 JPopupMenu suggestionsPopupMenu;
	 JMenu fileMenu;
	 JMenuItem openItem;
	 JMenuItem saveItem;
	 JMenuItem saveAsItem;
	 JMenuItem checkItem;
	 JMenuItem uncheckItme;
	 JMenuItem newItem;
	 JFontChooser fontChooser;
	 JMenuItem fontItem;
	 JScrollPane scrollPane;
	 JTextPane textPane;
	 JDialog backgroundDialog;
	 JDialog foregroundDialog;
	 JColorChooser bColorChooser;
	 JColorChooser fColorChooser;
	 JMenuItem bColorItem;
	 JMenuItem fColorItem;
	 JSeparator separator_1;
	 JSeparator separator_2;
	 JSeparator separator_4;
	 JSeparator separator_5;
	 JSeparator separator_6;

	
	
	// Launch the application.
	 
	public void run() {
				try {
					this.frame.setVisible(true);	
				} catch (Exception e) {
					e.printStackTrace();
				}
	}

	
	 //Create the application.
	 
	public VelesGUI(java.util.List<Thread> threads) {
		this.threads = threads;
		initialize();
	}

	
	 //Initialize the contents of the frame.
	 
	@SuppressWarnings("deprecation")
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 751, 663);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageIcon icon = new ImageIcon("C:\\Users\\Tempest\\Desktop\\Tests\\FTN2.png");
		frame.setIconImage(icon.getImage());
		
		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		textPaneActions = createActionTable(textPane);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File"); 
		menuBar.add(fileMenu);
		
		editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		
		styleMenu = new JMenu("Style");
		menuBar.add(styleMenu);
		checkMenu = new JMenu("Fix double spaces");
		menuBar.add(checkMenu);
		 assistMenu =new JMenu("Assistance");
			menuBar.add(assistMenu);
			creditsMenu=new JMenu("Credits");
			menuBar.add(creditsMenu);
			
		cutAction = getActionByName(DefaultEditorKit.cutAction);
		cutAction.putValue(Action.NAME, "Cut");
		editMenu.add(cutAction);
		textPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK), DefaultEditorKit.cutAction);
		Action copyAction= getActionByName(DefaultEditorKit.copyAction);
		copyAction.putValue(Action.NAME, "Copy");
		editMenu.add(copyAction);
		textPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK), DefaultEditorKit.copyAction);
		
		Action pasteAction= getActionByName(DefaultEditorKit.pasteAction);
		pasteAction.putValue(Action.NAME, "Paste");
		editMenu.add(pasteAction);
		textPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), DefaultEditorKit.pasteAction);
		
		Action boldAction = new StyledEditorKit.BoldAction();
	    boldAction.putValue(Action.NAME, "Bold");
		styleMenu.add(boldAction);
		
		Action italicAction = new StyledEditorKit.ItalicAction();
		italicAction.putValue(Action.NAME, "Italic");
		styleMenu.add(italicAction);
		
		Action underlineAction = new StyledEditorKit.UnderlineAction();
		underlineAction.putValue(Action.NAME, "Underline");
		styleMenu.add(underlineAction);
		
		undoAction = new UndoAction("Undo");
		separator_4 = new JSeparator();
		editMenu.add(separator_4);
		editMenu.add(undoAction);
		
		textPane.getActionMap().put("Undo", undoAction);
		textPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK), "Undo");
		
	
		undoManager = new UndoManager();
		doc = (StyledDocument) textPane.getDocument();
		doc.addUndoableEditListener(new OurUndoableEditListener());
	
        
		//settings for file handling
		fileHandler = new FileHandler(this);
		
		newItem = new JMenuItem("New");
		newItem.addActionListener(fileHandler.createListener());
		fileMenu.add(newItem);
		
		openItem = new JMenuItem("Open");
		openItem.addActionListener(fileHandler.createListener());
		
		separator_2 = new JSeparator();
		fileMenu.add(separator_2);
		fileMenu.add(openItem);
		
		saveItem = new JMenuItem("Save");
		saveItem.addActionListener(fileHandler.createListener());
		fileMenu.add(saveItem);
		
		saveAsItem = new JMenuItem("Save As...");
		saveAsItem.addActionListener(fileHandler.createListener());
		fileMenu.add(saveAsItem);
		saveAsItem.setEnabled(false);

		checkItem=new JMenuItem("Find them");
		checkItem.addActionListener(fileHandler.createListener());
		checkMenu.add(checkItem);
		
		uncheckItme=new JMenuItem("Fix them");
		uncheckItme.addActionListener(fileHandler.createListener());
		checkMenu.add(uncheckItme);
	

		checkItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String text = textPane.getText();
		        textPane.setText(text.replaceAll("  ", "✔"));
		    }
		});

		uncheckItme.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String text = textPane.getText();
		        textPane.setText(text.replaceAll("✔", " "));
		    }
		});

		JMenuItem wordCountItem = new JMenuItem("Word Count");
		wordCountItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String text = textPane.getText();
		        int wordCount = text.trim().isEmpty() ? 0 : text.split("\\s+").length;
		        JOptionPane.showMessageDialog(null, "Word count: " + wordCount);
		    }
		});
		JMenuItem creditsItem = new JMenuItem("Credits");
		creditsItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Names are here
		        String[] names = {"dr Marija Blagojević", "dr Lena Tica", "Petar Matijević 66/2019"};

		        // Load image
		        ImageIcon icon = new ImageIcon("C:\\\\Users\\\\Tempest\\\\Desktop\\\\Tests\\\\FTN2.png");

		        // Credits text
		        StringBuilder creditsBuilder = new StringBuilder();
		        creditsBuilder.append("Credits:\n\n");
		        for (int i = 0; i < names.length; i++) {
		            creditsBuilder.append("- ");
		            creditsBuilder.append(names[i]);
		            creditsBuilder.append("\n");
		        }

		        // Create popup window
		        JTextArea creditsText = new JTextArea(creditsBuilder.toString());
		        creditsText.setEditable(false);
		        JScrollPane scrollPane = new JScrollPane(creditsText);
		        scrollPane.setPreferredSize(new Dimension(300, 150));
		        JOptionPane.showMessageDialog(frame, scrollPane, "Credits", JOptionPane.PLAIN_MESSAGE, icon);
		    }
		});

		creditsMenu.add(creditsItem);

		assistMenu.add(wordCountItem);
		
		
		JMenuItem closeQuotesItem = new JMenuItem("Close Quotation Marks");
		closeQuotesItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String text = textPane.getText();
		        String result = text.replaceAll("(?<!\")(?<=\\b|\\s)\"(?!\\s|\\p{Punct})|(?<=\\w)\"(?=\\w)|(?<!\\s)\"(?=\\b|\\s)(?!\")", "\"\"");

		        int quotesAdded = 0;
		        String[] sentences = result.split("(?<=[.?!])\\s+");
		        for (int i = 0; i < sentences.length; i++) {
		            if (sentences[i].startsWith("\"") && !sentences[i].endsWith("\"")) {
		                sentences[i] += "\"";
		                quotesAdded++;
		            } else if (!sentences[i].startsWith("\"") && sentences[i].endsWith("\"")) {
		                sentences[i] = "\"" + sentences[i];
		                quotesAdded++;
		            }
		        }
		        result = String.join(" ", sentences);

		        if (quotesAdded == 0) {
		            JOptionPane.showMessageDialog(null, "No quotes were added or fixed.");
		        } else if (quotesAdded == 1) {
		            JOptionPane.showMessageDialog(null, "One quote was added or fixed.");
		        } else {
		            JOptionPane.showMessageDialog(null, quotesAdded + " quotes were added or fixed.");
		        }

		        textPane.setText(result);
		    }
		});
		assistMenu.add(closeQuotesItem);
		JMenuItem removeQuotesItem = new JMenuItem("Remove Excessive Quotation Marks");
		removeQuotesItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String text = textPane.getText();
		        String result = text.replaceAll("(\"{2,})", "\"");

		        int quotesRemoved = (text.length() - result.length()) / 2;

		        if (quotesRemoved == 0) {
		           
		        } else if (quotesRemoved == 1) {
		            JOptionPane.showMessageDialog(null, "One quote was removed.");
		        } else {
		            JOptionPane.showMessageDialog(null, quotesRemoved + " quotes were removed.");
		        }
		        
		        textPane.setText(result);
		    }
		});
		assistMenu.add(removeQuotesItem);



		separator_5 = new JSeparator();
		assistMenu.add(separator_5);

		JMenuItem addPeriodItem = new JMenuItem("Add Period to Sentence");
		addPeriodItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String text = textPane.getText();
		        String result = text.replaceAll("(?<=[a-z])\\n|\\n(?=[A-Z])", ". ");
		        if (!text.isEmpty()) {
		            if (!text.endsWith(".") && !text.endsWith("\n")) {
		                result += ".";
		            } else if (text.endsWith("\n")) {
		                result = result.substring(0, result.length() - 1) + ".";
		            }
		        }
		        textPane.setText(result);
		    }
		});
		assistMenu.add(addPeriodItem);

		JMenuItem replaceItem = new JMenuItem("Replace Text");
		replaceItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    
		        JDialog dialog = new JDialog(frame, "Replace Text", true);
		        JPanel panel = new JPanel(new BorderLayout());
		        dialog.setMinimumSize(new Dimension(400, 300));
		        dialog.setResizable(true);
		        JLabel findLabel = new JLabel("Find:");
		        JTextField findField = new JTextField();
		        JPanel findPanel = new JPanel(new BorderLayout());
		        findPanel.add(findLabel, BorderLayout.WEST);
		        findPanel.add(findField, BorderLayout.CENTER);
		        panel.add(findPanel, BorderLayout.NORTH);

		        JLabel replaceLabel = new JLabel("Replace with:");
		        JTextField replaceField = new JTextField();
		        JPanel replacePanel = new JPanel(new BorderLayout());
		        replacePanel.add(replaceLabel, BorderLayout.WEST);
		        replacePanel.add(replaceField, BorderLayout.CENTER);
		        panel.add(replacePanel, BorderLayout.CENTER);

		        // Listener that captures copy and paste events
		        findField.addKeyListener(new KeyAdapter() {
		            @Override
		            public void keyPressed(KeyEvent e) {
		                if ((e.getKeyCode() == KeyEvent.VK_C) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
		                    findField.copy();
		                }
		                if ((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
		                    findField.paste();
		                }
		            }
		        });

		        replaceField.addKeyListener(new KeyAdapter() {
		            @Override
		            public void keyPressed(KeyEvent e) {
		                if ((e.getKeyCode() == KeyEvent.VK_C) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
		                    replaceField.copy();
		                }
		                if ((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
		                    replaceField.paste();
		                }
		            }
		        });

		        JButton replaceButton = new JButton("Replace");
		        replaceButton.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		                String text = textPane.getText();
		                String findStr = findField.getText();
		                String replaceStr = replaceField.getText();
		                if (findStr != null && replaceStr != null) {
		                    String result = text.replaceAll(findStr, replaceStr);
		                    textPane.setText(result);
		                }
		                dialog.dispose();
		            }
		        });
		        panel.add(replaceButton, BorderLayout.SOUTH);

		        dialog.setContentPane(panel);
		        dialog.pack();
		        dialog.setLocationRelativeTo(frame);
		        dialog.setVisible(true);
		    }
		});
		assistMenu.add(replaceItem);

		assistMenu.add(replaceItem);
		JMenuItem separateWordsItem = new JMenuItem("Separate Words from Punctuation");
		separateWordsItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String text = textPane.getText();
		        String result = text.replaceAll("(\\p{L})([,.!?])(?!(\\s|$))", "$1$2 ");
		        textPane.setText(result);
		    }
		});
		assistMenu.add(separateWordsItem);

		JMenuItem removeDuplicateLinesItem = new JMenuItem("Remove Duplicate Lines");
		removeDuplicateLinesItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to remove duplicate lines?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
		        if (confirm == JOptionPane.OK_OPTION) {
		            String text = textPane.getText();
		            String[] lines = text.split("\\r?\\n");
		            Set<String> set = new LinkedHashSet<>();
		            for (String line : lines) {
		                set.add(line.trim());
		            }
		            String result = String.join(System.lineSeparator(), set);
		            textPane.setText(result);
		        }
		    }
		});
		assistMenu.add(removeDuplicateLinesItem);

		separator_6 = new JSeparator();
		assistMenu.add(separator_6);
		JMenuItem capitalizeFirstWordItem = new JMenuItem("Capitalize First Word of Sentence");
		capitalizeFirstWordItem.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		String text = textPane.getText();
		StringBuilder result = new StringBuilder(text.length());
		boolean capitalize = true;
		for (char c : text.toCharArray()) {
		if (capitalize && Character.isLetter(c)) {
		result.append(Character.toUpperCase(c));
		capitalize = false;
		} else {
		result.append(c);
		if (c == '.' || c == '!' || c == '?') {
		capitalize = true;
		}
		}
		}
		textPane.setText(result.toString());
		}
		});
		assistMenu.add(capitalizeFirstWordItem);
		
		JMenuItem removeBlankLinesItem = new JMenuItem("Remove Blank Lines");
		removeBlankLinesItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to remove blank lines?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
		        if (confirm == JOptionPane.OK_OPTION) {
		            String text = textPane.getText();
		            String[] lines = text.split("\\r?\\n");
		            StringBuilder builder = new StringBuilder();
		            for (String line : lines) {
		                if (!line.trim().isEmpty()) {
		                    builder.append(line).append(System.lineSeparator());
		                }
		            }
		            String result = builder.toString().trim();
		            textPane.setText(result);
		        }
		    }
		});
		assistMenu.add(removeBlankLinesItem);
		JMenuItem addListItems = new JMenuItem("Add Bulleted/Numbered List");
		addListItems.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // This is a new dialog window that gets list's settings and items
		        JDialog dialog = new JDialog(frame, "Add List Items", true);
		        dialog.setMinimumSize(new Dimension(400, 300));
		        dialog.setResizable(true);

		        JPanel panel = new JPanel(new BorderLayout());
		        JPanel settingsPanel = new JPanel(new GridLayout(2, 1));
		        ButtonGroup group = new ButtonGroup();
		        JRadioButton bulletButton = new JRadioButton("Bulleted List");
		        bulletButton.setSelected(true);
		        JRadioButton numberButton = new JRadioButton("Numbered List");
		        group.add(bulletButton);
		        group.add(numberButton);
		        settingsPanel.add(bulletButton);
		        settingsPanel.add(numberButton);
		        panel.add(settingsPanel, BorderLayout.NORTH);

		        JTextArea listItems = new JTextArea();
		        JScrollPane scrollPane = new JScrollPane(listItems);
		        panel.add(scrollPane, BorderLayout.CENTER);

		        // Listener that captures paste events
		        listItems.addKeyListener(new KeyAdapter() {
		            @Override
		            public void keyPressed(KeyEvent e) {
		                if ((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
		                    listItems.paste();
		                }
		            }
		        });

		        JTextField textField = new JTextField();
		        panel.add(textField, BorderLayout.SOUTH);

		      
		        textField.addKeyListener(new KeyAdapter() {
		            @Override
		            public void keyPressed(KeyEvent e) {
		                if ((e.getKeyCode() == KeyEvent.VK_C) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
		                    textField.copy();
		                }
		                if ((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
		                    textField.paste();
		                }
		            }
		        });

		        JButton addButton = new JButton("Add List");
		        addButton.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		                String prefix;
		                if (bulletButton.isSelected()) {
		                    prefix = "• ";
		                } else {
		                    prefix = "%d. ";
		                }
		                String[] items = listItems.getText().split("\\r?\\n");
		                StringBuilder sb = new StringBuilder();
		                for (int i = 0; i < items.length; i++) {
		                    sb.append(String.format(prefix, i+1));
		                    sb.append(items[i]);
		                    if (i < items.length - 1) {
		                        sb.append("\n");
		                    }
		                }
		                if (!textField.getText().isEmpty()) {
		                    sb.append("\n" + textField.getText());
		                }
		                textPane.replaceSelection(sb.toString());
		                dialog.dispose();
		            }
		        });
		        panel.add(addButton, BorderLayout.SOUTH);

		        dialog.setContentPane(panel);
		        dialog.pack();
		        dialog.setLocationRelativeTo(frame);
		        dialog.setVisible(true);
		    }
		});
		assistMenu.add(addListItems);

		JMenuItem addHyperlinkItem = new JMenuItem("Make HTML Hyperlink");
		addHyperlinkItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Create a new dialog to get link settings
		        JDialog dialog = new JDialog(frame, "Add Hyperlink", true);
		        JPanel panel = new JPanel(new BorderLayout());
		        dialog.setMinimumSize(new Dimension(450, 120));
		        dialog.setResizable(true);

		        JLabel urlLabel = new JLabel("URL:");
		        JTextField urlField = new JTextField();
		        JPanel urlPanel = new JPanel(new BorderLayout());
		        urlPanel.add(urlLabel, BorderLayout.WEST);
		        urlPanel.add(urlField, BorderLayout.CENTER);
		        panel.add(urlPanel, BorderLayout.NORTH);

		        JLabel textLabel = new JLabel("Link Text:");
		        JTextField textField = new JTextField();
		        JPanel textPanel = new JPanel(new BorderLayout());
		        textPanel.add(textLabel, BorderLayout.WEST);
		        textPanel.add(textField, BorderLayout.CENTER);
		        panel.add(textPanel, BorderLayout.CENTER);

		   
		        urlField.addKeyListener(new KeyAdapter() {
		            @Override
		            public void keyPressed(KeyEvent e) {
		                if ((e.getKeyCode() == KeyEvent.VK_C) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
		                    urlField.copy();
		                }
		                if ((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
		                    urlField.paste();
		                }
		            }
		        });

		        textField.addKeyListener(new KeyAdapter() {
		            @Override
		            public void keyPressed(KeyEvent e) {
		                if ((e.getKeyCode() == KeyEvent.VK_C) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
		                    textField.copy();
		                }
		                if ((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
		                    textField.paste();
		                }
		            }
		        });

		        JButton addButton = new JButton("Add Link");
		        addButton.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		                String url = urlField.getText();
		                String text = textField.getText();
		                if (text.isEmpty()) {
		                    text = url;
		                }
		                if (!url.isEmpty()) {
		                    StyledDocument doc = textPane.getStyledDocument();
		                    SimpleAttributeSet attrs = new SimpleAttributeSet();
		                    attrs.addAttribute(HTML.Attribute.HREF, url);
		                    attrs.addAttribute(HTML.Attribute.TARGET, "_blank");
		                    try {
		                        doc.insertString(doc.getLength(), "<a href='" + url + "'>" + text + "</a>", attrs);
		                    } catch (BadLocationException ex) {
		                        ex.printStackTrace();
		                    }
		                }
		                dialog.dispose();
		            }
		        });
		        panel.add(addButton, BorderLayout.SOUTH);

		        dialog.setContentPane(panel);
		        dialog.pack();
		        dialog.setLocationRelativeTo(frame);
		        dialog.setVisible(true);
		    }
		});
		assistMenu.add(addHyperlinkItem);



		suggestionsPopupMenu = new JPopupMenu();
        textPane.addMouseListener(new OurMouseListener());
		
		//setting spell checking
		textPane.getDocument().addDocumentListener(new DocumentListener() {
		      public void insertUpdate(DocumentEvent evt) {
		    	  synchronized(threads.get(1)) {
		    		  threads.get(1).notifyAll();
		    	  }
		      }

		      public void removeUpdate(DocumentEvent evt) {
		    	  synchronized(threads.get(1)) {
		    		  threads.get(1).notifyAll();
		    	  }
		      }

		      public void changedUpdate(DocumentEvent evt) {
		    	  synchronized(threads.get(1)) {
		    		  threads.get(1).notifyAll();
		    	  }
		      }
		    });
		
		//setting font option
		fontChooser = new JFontChooser();
		
		JSeparator separator = new JSeparator();
		styleMenu.add(separator);
		fontItem = new JMenuItem("Choose Font");
		styleMenu.add(fontItem);
		fontItem.addActionListener( new FontListener());
		
		//setting color option
		bColorChooser = new JColorChooser();
		
		separator_1 = new JSeparator();
		styleMenu.add(separator_1);
		bColorItem = new JMenuItem("Choose Background Color");
		styleMenu.add(bColorItem);
		bColorItem.addActionListener( new colorListener());
		
		fColorChooser = new JColorChooser();
		fColorItem = new JMenuItem("Choose Foreground Color");
		styleMenu.add(fColorItem);
		fColorItem.addActionListener( new colorListener());
		
		
		
	}
	
	
	private class FontListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			   int result = fontChooser.showDialog(frame);
			   if (result == JFontChooser.OK_OPTION)
			   {
			        Font font = fontChooser.getSelectedFont(); 
			        textPane.setFont(font);
			   }
		}
	}
	
	private class colorListener implements ActionListener{
		public void actionPerformed (ActionEvent e) {
			if(e.getSource() == bColorItem) {
				backgroundDialog=JColorChooser.createDialog
						(textPane,
						"Background Color",
						false,
						bColorChooser,
						new ActionListener(){
							public void actionPerformed(ActionEvent e){
							textPane.setBackground(bColorChooser.getColor());
							}
						},
						null);		

				backgroundDialog.setVisible(true);
			}
			else if(e.getSource() == fColorItem){
				foregroundDialog=JColorChooser.createDialog
						(textPane,
						"Foreground Color",
						false,
						fColorChooser,
						new ActionListener(){
							public void actionPerformed(ActionEvent e){
							textPane.setForeground(fColorChooser.getColor());
							textPane.setCaretColor(fColorChooser.getColor());
							}
						},
						null);		

				foregroundDialog.setVisible(true);
			}
			
		}
	}
	
	
	protected class OurUndoableEditListener implements UndoableEditListener {
	    public void undoableEditHappened( UndoableEditEvent e) {
	        undoManager.addEdit(e.getEdit());
	    }
	} 
	
	protected class UndoAction extends AbstractAction{
		private static final long serialVersionUID = 1L;
		public UndoAction(String name) {
			super(name);
		}
		public void actionPerformed(ActionEvent e) {
		    try {
		        undoManager.undo();
		    } catch (CannotUndoException ex) {
		        System.out.println("Unable to undo: " + ex);
		        ex.printStackTrace();
		    }
		}
	}
	
	private HashMap<Object, Action> createActionTable(JTextComponent textComponent) {
        HashMap<Object, Action> actions = new HashMap<Object, Action>();
        Action[] actionsArray = textComponent.getActions();
        for (int i = 0; i < actionsArray.length; i++) {
            Action a = actionsArray[i];
            actions.put(a.getValue(Action.NAME), a);
        }
        return actions;
    }
	
	private Action getActionByName(String name) {
	    return textPaneActions.get(name);
	}
	
	public Document getDoc() {
		return doc;
	}
	
	public String getStrDoc() throws BadLocationException {
		    System.out.println(doc.getLength());
			return  (String)doc.getText(0, doc.getLength());
	}
	
	
	
	private class OurMouseListener extends MouseAdapter	 {
        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                int rightClickPosition = textPane.viewToModel2D(e.getPoint());
                textPane.setCaretPosition(rightClickPosition);
                Boolean show = refreshSuggestionsPopupMenu(suggestionsPopupMenu);
                if (show) suggestionsPopupMenu.show(textPane, e.getX(), e.getY());
            }
        }
	}
	
	public void correctWord(String w, AttributeSet atr) throws BadLocationException {
		int caretPosition = textPane.getCaretPosition();
        int start = Utilities.getWordStart(textPane, caretPosition);
        int end = Utilities.getWordEnd(textPane, caretPosition);
        doc.remove(start, end-start);
		doc.insertString(start, w, atr);
	}
	public String getWordAtCaret() {
	      try 
	      {
	          int caretPosition = textPane.getCaretPosition();
	          int start = Utilities.getWordStart(textPane, caretPosition);
	          int end = Utilities.getWordEnd(textPane, caretPosition);
	          return textPane.getText(start, end - start);
	      } catch (BadLocationException e) {
	          System.err.println(e);
	          return null;
	      }
	}
	
	private Boolean refreshSuggestionsPopupMenu(JPopupMenu pm) {
		 pm.removeAll();
		 String word = getWordAtCaret();
		 java.util.List<String> words = SpellCheck.getSuggestions(word,7);
		 for (String w : words) {
			 JMenuItem item = new JMenuItem(w);
			 pm.add(item);
			 item.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						correctWord(w, null);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					
				} 
			 });
		 }
		 JMenuItem ignoreThis = new JMenuItem("Ignore for This Document");
		 pm.add(ignoreThis);
		 ignoreThis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SpellCheck.addWordToThisDictionary(word);
				synchronized(threads.get(1)) {
		    		  threads.get(1).notifyAll();
		    	  }
				
			}
		 });
		 JMenuItem addDic = new JMenuItem("Add to Dictionary");
		 pm.add(addDic);
		 addDic.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					SpellCheck.addWordToAllDictionary(word);
					synchronized(threads.get(1)) {
			    		  threads.get(1).notifyAll();
			    	  }
				}
			 });
		 
		 if(words.isEmpty()) return false;
		 else return true;
	}
	
	public JTextPane getTextPane() {
		return textPane;
	}
	
	public JFrame getFrame() {
		return frame;
	}
}

