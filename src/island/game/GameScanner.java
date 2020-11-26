package island.game;

import java.util.List;
import java.util.Scanner;

import island.players.GamePlayers;
import island.players.Player;

/**
 * Class to allow checking of user input for a heli/sandbag card use request
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class GameScanner {
	
	private static GameScanner gameScanner = new GameScanner();
	
	private Scanner userInput;
	
	private GameScanner() {
		userInput = new Scanner(System.in);
	}
	
	public static GameScanner getInstance() {
		return gameScanner;
	}
	
	/*
	 * This would be the main scanner method
	 * Takes in prompt so can re-print if needed
	 * Check if heli/sandbag move requested - if so implements these then reprompts initial prompt
	 * Then returns scanned string to be used back in game flow 
	 */
	public String scanNextLine(String initialPrompt) {
		//Print prompt in here (rather than before function)??
		
		String input = userInput.nextLine();
		
		while(input.equals("Heli") || input.equals("Sandbag")) {
			if(input.equals("Heli")) {
				heliRequest();
			}
			if(input.equals("Sandbag")) {
				sandbagRequest();
			}
			
			System.out.println(initialPrompt);
			input = userInput.nextLine();
		}
		 
		return input;
	}
	
	public void heliRequest() {
		String prompt = "Which player wishes to play a heli card?";
		//System.out.println(prompt);
		//Just make sandbag and heli cards communal in GamePlayers?
		Player p = pickFromList(GamePlayers.getInstance().getPlayersList(), prompt);
		p.playHeliCard();
	}
	
	public void sandbagRequest() {
		String prompt = "Which player wishes to play a sandbag card?";
		Player p = pickFromList(GamePlayers.getInstance().getPlayersList(), prompt); //bad practice to instantiate here?
		p.playSandBagCard();
	}
	
	/*
	 * Class prompts user to pick an item from input list and returns chosen item
	 */
	public <E> E pickFromList(List<E> items, String prompt){
		//system.out.println("Choose "+E.toString():);
		
		int index;
		
		int i = 1;
		String options = "";
		for(E item : items) {
			options += item.toString()+" ["+Integer.toString(i)+"]";
			i++;
		}
		System.out.println(options);
		prompt += "\n"+options;
		index = Integer.parseInt(scanNextLine(prompt)) - 1;
		return items.get(index);
	}
	
	
	// updateDisplay() {}

}
