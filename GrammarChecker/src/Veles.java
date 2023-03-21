import java.util.ArrayList;
import java.util.List;

import MK1.UserInterface;

public class Veles {

	public static void main(String[] args) {
		Veles ui = new Veles();
        ui.run();}
		 public void run() {
		List<Thread> threads = new ArrayList<Thread>();
		
		Thread threadGUI = new VelesGUI(threads);
		threads.add(threadGUI);
		
		
		Thread threadSpellCheck = new SpellCheck(((VelesGUI) threadGUI).getTextPane());
		threads.add(threadSpellCheck);
		threadSpellCheck.setDaemon(true);
		
		Thread threadAutoSave = new AutoSave(0.1, (VelesGUI) threadGUI);
		threads.add(threadAutoSave);
		threadAutoSave.setDaemon(true);
		
		threadGUI.start();
		threadSpellCheck.start();
		threadAutoSave.start();}
	 
	
	    }
		
	


