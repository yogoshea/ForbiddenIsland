package island.game;

import java.util.Scanner;

import island.players.Player;

/**
 * Controller class for retrieving player choices for GameView and change 
 * game model accordingly
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class ActionController {
	
	// Instantiate singleton
	private static ActionController actionController;
	
	private GameView gameView;
	private GameModel gameModel;
	
	/**
	 * Constructor to retrieve view and model instances
	 */
	private ActionController(GameModel gameModel, GameView gameView) {
		this.gameModel = gameModel;
		this.gameView = gameView;
	}
	
	/**
	 * @return single instance of action controller
	 */
	public static ActionController getInstance(GameModel gameModel, GameView gameView) {
		if (actionController == null) {
			actionController = new ActionController(gameModel, gameView);
		}
		return actionController;
	}
	
	public void takeActions(Player p) {
		gameView.promptUser("...");
		
		System.out.println(toStringDetailed());
		
		System.out.println("Do you wish to take an action? ("+Integer.toString(availableActions)+" remaining)");
		System.out.println("[Y]/[N]");
		String takeAction = userInput.nextLine();
		
		while(availableActions > 0 && takeAction.equals("Y")) {
			
			//take an action
			successfullyTaken = takeAction(userInput);
			
			//decrease available actions if successful
			availableActions -= successfullyTaken ? 1 : 0;
			
			if(availableActions > 0) { //Bit clunky
				System.out.println("\nDo you wish to take another action? ("+Integer.toString(availableActions)+" remaining)");
				System.out.println("[Y]/[N]");
				takeAction = userInput.nextLine();
			}
			System.out.println(toStringDetailed());

		}
	}
	
	/**
	 * method to choose the action to take during a turn
	 * @return whether action successfully taken
	 */
	public boolean takeAction(Scanner userInput) {
		
		boolean successfullyTaken = false;
		
		System.out.println("Select one of the following actions:");
		System.out.println("Move [M], Shore-Up [S], Give Treasure Card [G], Capture a Treasure [C]");
		String actionType = userInput.nextLine();
		
		switch(actionType) {
			case "M":
				successfullyTaken = move(userInput); // TODO: check for validity in Player class, throw Exception
				break;
			
			case "S":
				successfullyTaken = shoreUp(userInput);
				break;
				
			case "G":
				successfullyTaken = giveTreasureCard(userInput);
				break;
				
			case "C":
				successfullyTaken = captureTreasure();
				break;
		}
		
		return successfullyTaken;
	}

}
