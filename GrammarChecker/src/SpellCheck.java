import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.JTextComponent;
import javax.swing.text.LayeredHighlighter;
import javax.swing.text.Position;
import javax.swing.text.View;
import java.nio.charset.StandardCharsets;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

public class SpellCheck extends Thread {
	private static List<String> dic;
	private static String specialChars;
	private JTextPane pane;
	static Path dicPath;
	
	private static Highlighter highlighter;
	private Highlighter.HighlightPainter painter;
	
	SpellCheck(JTextPane pane){
		this.pane = pane;
		highlighter = new UnderlineHighlighter(null);
		this.painter = new UnderlineHighlighter.UnderlineHighlightPainter(Color.red);
		pane.setHighlighter(highlighter);
		dicPath = Paths.get("C:\\Users\\Tempest\\Desktop\\Diplomski\\Program\\GrammarChecker\\src\\dictionary1.txt");
		
		dic = Collections.emptyList();
		try {
			dic = Files.readAllLines(dicPath, StandardCharsets.UTF_8);
			
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "cannot open dictionary", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
	
		specialChars = ".,'\";:\\|][}{+=-_)(*&^%$#@!`~/ \n";
		
	}
	
	public void run() {
		
		synchronized(this) {
			while(true) {
				try {
					startJob();
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Thread.yield();
			}
		}
	}
	public static List<String> getSuggestions(String word, int maxSuggestions) {

	    List<String> suggestedWords = new ArrayList<>();
	    PriorityQueue<String> suggestionsQueue = new PriorityQueue<>((w1, w2) -> {
	        int distance1 = levenshteinDistance(word, w1);
	        int distance2 = levenshteinDistance(word, w2);
	        return Integer.compare(distance1, distance2);
	    });
	    Set<String> alreadySuggested = new HashSet<>();

	    if (!dic.contains(word)) {
	        String suggestedWord = findSuggestedWord(dic, word);
	        if (!suggestedWord.isEmpty()) {
	            suggestedWord = capitalizeIfFirstWord(suggestedWord, word);
	            suggestionsQueue.offer(suggestedWord);
	            alreadySuggested.add(suggestedWord);
	        }
	    }

	    while (!suggestionsQueue.isEmpty() && suggestedWords.size() < maxSuggestions) {
	        String suggestion = suggestionsQueue.poll();
	        suggestedWords.add(suggestion);

	        for (String w : dic) {
	            if (suggestedWords.size() >= maxSuggestions) {
	                break;
	            }
	            if (!alreadySuggested.contains(w)) {
	                int distance = levenshteinDistance(word, w);
	                if (distance <= 2) {
	                    w = capitalizeIfFirstWord(w, word);
	                    suggestionsQueue.offer(w);
	                    alreadySuggested.add(w);
	                }
	            }
	        }
	    }

	    return suggestedWords;
	}

	private static String capitalizeIfFirstWord(String word, String previousWord) {
	    if (previousWord.isEmpty() || previousWord.charAt(previousWord.length() - 1) == '.') {
	        return capitalize(word);
	    } else {
	        return word;
	    }
	}

	public static String findSuggestedWord(List<String> words, String word) {
	    int minDistance = Integer.MAX_VALUE;
	    int minLen = Math.max(1, word.length() - 1);
	    int maxLen = word.length() + 1;
	    int maxDistance = 2;

	    Map<Character, Integer> letterFreq = word.chars()
	            .mapToObj(c -> (char) c)
	            .collect(Collectors.toMap(c -> c, c -> 1, Integer::sum));

	    String perfectMatch = words.stream()
	            .filter(w -> w.equals(word))
	            .findFirst()
	            .orElse(null);
	    if (perfectMatch != null) {
	        return perfectMatch;
	    }

	    Map<String, Map<Character, Integer>> letterFreqCache = new HashMap<>();

	    String suggestedWord = null;
	    for (String w : words) {
	        if (w.length() >= Math.min(minLen, w.length()) && w.length() <= maxLen) {
	            Map<Character, Integer> wLetterFreq = letterFreqCache.computeIfAbsent(w, k -> w.chars()
	                    .mapToObj(c -> (char) c)
	                    .collect(Collectors.toMap(c -> c, c -> 1, Integer::sum)));
	            int distance = levenshteinDistance(word, w);
	            if (distance <= maxDistance) {
	                int weightedDistance = distance + letterFreq.keySet().stream()
	                        .mapToInt(c -> Math.abs(letterFreq.get(c) - wLetterFreq.getOrDefault(c, 0)))
	                        .sum();
	                if (weightedDistance < minDistance) {
	                    minDistance = weightedDistance;
	                    suggestedWord = w;
	                }
	            }
	        }
	    }

	    return suggestedWord;
	}

	public static String capitalize(String str) {
	    if (str == null || str.isEmpty()) {
	        return str;
	    }
	    return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}

	public static int levenshteinDistance(String word1, String word2) {
	    int m = word1.length();
	    int n = word2.length();

	    int[][] dp = new int[m + 1][n + 1];

	    for (int i = 0; i <= m; i++) {
	        dp[i][0] = i;
	    }

	    for (int j = 0; j <= n; j++) {
	        dp[0][j] = j;
	    }

	    for (int i = 1; i <= m; i++) {
	        for (int j = 1; j <= n; j++) {
	            int substitutionCost = word1.charAt(i - 1) == word2.charAt(j - 1) ? 0 : 1;
	            dp[i][j] = Math.min(dp[i - 1][j] + 1, Math.min(dp[i][j - 1] + 1, dp[i - 1][j - 1] + substitutionCost));
	        }
	    }

	    return dp[m][n];
	}

	
	private void startJob() throws BadLocationException {
	    deletePreviosHighlights();

	    Document doc = pane.getDocument();
	    String text = doc.getText(0, doc.getLength()).toLowerCase();

	    int startIndex = 0;
	    int endIndex = 0;
	    boolean isLastSpecial = true;
	    boolean isStartOfSentence = true;
	    String word;

	    for (int i = 0; i < text.length(); i++) {
	        char c = text.charAt(i);

	        if (specialChars.indexOf(c) != -1) {
	            if (!isLastSpecial) {
	                word = text.substring(startIndex, endIndex);
	                if (!dic.contains(word)) {
	                    if (isStartOfSentence && !Character.isUpperCase(word.charAt(0))) {
	                        capitalLetterPainter(startIndex, endIndex);
	                    } else {
	                        highlighter.addHighlight(startIndex, endIndex, painter);
	                    }
	                }
	                isStartOfSentence = c == '.' || c == '?' || c == '!';
	            }
	            startIndex = endIndex + 1;
	            endIndex = startIndex;
	            isLastSpecial = true;
	        } else {
	            endIndex += 1;
	            isLastSpecial = false;
	        }
	    }
	}

	private void capitalLetterPainter(int startIndex, int endIndex) throws BadLocationException {
	    String text = pane.getDocument().getText(startIndex, endIndex - startIndex);
	    int index = pane.getText().indexOf(text, startIndex);
	    if (index >= 0) {
	        Highlighter.HighlightPainter capitalLetterPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.LIGHT_GRAY);
	        highlighter.addHighlight(index, index + text.length(), capitalLetterPainter);
	    }
	}

	
	private void deletePreviosHighlights() {
		Highlighter.Highlight[] highlights = highlighter.getHighlights();
	    for (int i = 0; i < highlights.length; i++) {
	      Highlighter.Highlight h = highlights[i];
	      if (h.getPainter() instanceof UnderlineHighlighter.UnderlineHighlightPainter) {
	        highlighter.removeHighlight(h);
	      }
	    }
	}

	public static void addWordToAllDictionary(String word) {
		dic.add(word);
		try {
		    Files.write(dicPath, ("\n"+ word).getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		    
		}	
	}

	public static void addWordToThisDictionary(String word) {
		dic.add(word);
	
	}
}

class UnderlineHighlighter extends DefaultHighlighter {
	  public UnderlineHighlighter(Color c) {
	    painter = (c == null ? sharedPainter : new UnderlineHighlightPainter(c));
	  }

	
	  public Object addHighlight(int p0, int p1, Color c) throws BadLocationException {
		    if (p0 < 0 || p1 < 0 || p0 >= p1) {
		        throw new IllegalArgumentException("Invalid start and end positions for highlight");
		    }
		    UnderlineHighlightPainter painter = new UnderlineHighlightPainter(c);
		    return addHighlight(p0, p1, painter);
		}


	  public void setDrawsLayeredHighlights(boolean newValue) {
	  
	    if (newValue == false) {
	      throw new IllegalArgumentException(
	          "UnderlineHighlighter only draws layered highlights");
	    }
	    super.setDrawsLayeredHighlights(true);
	  }

	  // Painter for underlined highlights
	  public static class UnderlineHighlightPainter extends
	      LayeredHighlighter.LayerPainter {
	    public UnderlineHighlightPainter(Color c) {
	      color = c;
	    }
	    
	    public void paint(Graphics g, int offs0, int offs1, Shape bounds,
	            JTextComponent c) {
	          // Do nothing: this method will never be called
	        }

	    public Shape paintLayer(Graphics g, int offs0, int offs1, Shape bounds,
	        JTextComponent c, View view) {
	      g.setColor(color == null ? c.getSelectionColor() : color);

	      Rectangle alloc = null;
	      if (offs0 == view.getStartOffset() && offs1 == view.getEndOffset()) {
	        if (bounds instanceof Rectangle) {
	          alloc = (Rectangle) bounds;
	        } else {
	          alloc = bounds.getBounds();
	        }
	      } else {
	        try {
	          Shape shape = view.modelToView(offs0,
	              Position.Bias.Forward, offs1,
	              Position.Bias.Backward, bounds);
	          alloc = (shape instanceof Rectangle) ? (Rectangle) shape
	              : shape.getBounds();
	        } catch (BadLocationException e) {
	          return null;
	        }
	      }

	      FontMetrics fm = c.getFontMetrics(c.getFont());
	      int baseline = alloc.y + alloc.height - fm.getDescent() + 1;
	      g.drawLine(alloc.x, baseline, alloc.x + alloc.width, baseline);
	      g.drawLine(alloc.x, baseline + 1, alloc.x + alloc.width,
	          baseline + 1);

	      return alloc;
	    }

	    protected Color color; // The color for the underline
	  }

	  // Shared painter used for default highlighting
	  protected static final Highlighter.HighlightPainter sharedPainter = new UnderlineHighlightPainter(
	      null);

	  // Painter used for this highlighter
	  protected Highlighter.HighlightPainter painter;
	}
