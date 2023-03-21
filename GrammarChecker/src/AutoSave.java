
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class AutoSave extends Thread{

	private int counter;
	private String defaultFilePath;
	private int ms;
	private VelesGUI gui;
	private FileHandler fh;
	private String counterPath;
	private boolean firstTime;
	
	//ms will be initialized 2*60000 which is 2 minutes
	AutoSave(Double min, VelesGUI gui){
	    firstTime = true;
	    this.ms = (int) (min * 60000);
	    this.gui = gui;
	    this.fh = this.gui.fileHandler;
	    counterPath = System.getProperty("user.dir") + "/src/counter.txt";

	    try {
	        Scanner scanner = new Scanner(new File(counterPath));
	        if (scanner.hasNextInt()) {
	            counter = scanner.nextInt();
	        }
	        defaultFilePath = System.getProperty("user.dir") + "/autosave/AutoSaved_" + counter + ".txt";

	        Writer wr;
	        wr = new FileWriter(counterPath);
	        wr.write(String.valueOf(counter+1));
	        wr.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	}

	public void autoSave() throws IOException {
	    if(fh.file == null) {
	        File autosaveDirectory = new File(System.getProperty("user.dir") + "/autosave");
	        if (!autosaveDirectory.exists()) {
	            autosaveDirectory.mkdir();
	        }

	        BufferedWriter outFile = new BufferedWriter(new FileWriter(defaultFilePath));
	        gui.textPane.write(outFile);
	        outFile.close();

	        if (firstTime) {
	            firstTime = false;
	            JOptionPane.showMessageDialog(null, "The file is automatically saved to " + defaultFilePath, "Auto Save", JOptionPane.INFORMATION_MESSAGE);
	        }
	    }
	    else {
	        fh.save();
	    }
	}


	public void run() {
		while(true) {
			try {
				sleep(ms);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				autoSave();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

}
