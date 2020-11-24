package island.game;

import java.util.Scanner;

/**
 * Class to allow checking of user input for a heli/sandbag card use request
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class GameScanner {
	
	//Are there any cases we should do lazy instantiation rather than eager???
	GameScanner gameScanner = new GameScanner();
	
	Scanner userInput;
	
	private GameScanner() {
		userInput = new Scanner(System.in);
	}
	
	/*
	 * This would be the main scanner method
	 * Takes in prompt so can re-print if needed
	 * Check if heli/sandbag move requested - if so implements these then reprompts initial prompt
	 * Then returns scanned string to be used back in game flow 
	 */
	public String scanNextLine(String initialPrompt) {
		//Print prompt in here aswell (rather than before)??
		
		String input = userInput.nextLine();
		
		while(input.equals("Heli") || input.equals("Sandbag")) {
			if(input.equals("Heli")) {
				//TODO: a user has requested to use heli card -> implement
			}
			if(input.equals("Sandbag")) {
				//TODO: a user has requested to use sandbag card -> implement
			}
			
			System.out.println(initialPrompt);
			input = userInput.nextLine();
		}
		 
		return input;
	}

}
