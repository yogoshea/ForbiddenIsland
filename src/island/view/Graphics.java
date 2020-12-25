package island.view;

import java.util.ArrayList;
import java.util.List;

import island.components.GameModel;
import island.components.IslandTile;
import island.controllers.GameEndings;
import island.players.Player;

/**
 * Class populated with static methods for displaying the game play
 * through the console or terminal window.
 * 
 * ASCII Art sourced form and generated using the following:
 * https://www.ascii-art-generator.org/
 * https://www.asciiart.eu/vehicles/choppers (Art by Graeme Porter)
 * https://www.asciiart.eu/nature/islands (Art by sjw)
 * https://textfancy.com/multiline-text-art/
 * 
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
class Graphics {

	// display component dimensions
	private final static int tileCharWidth = 25; // change to make tiles wider
	private final static int displayCharWidth = 6 * tileCharWidth; // display width in characters
	
	protected static void displayWelcome() {
		System.out.println("		__      __      _                             _                             ");
		System.out.println("		\\ \\    / / ___ | | __  ___  _ __   ___       | |_  ___                      ");
		System.out.println("		 \\ \\/\\/ / / -_)| |/ _|/ _ \\| '  \\ / -_)      |  _|/ _ \\  _      _      _    ");
		System.out.println("		  \\_/\\_/  \\___||_|\\__|\\___/|_|_|_|\\___|       \\__|\\___/ (_)    (_)    (_)   ");
		System.out.println("");
		System.out.println("                  __..-----')");
		System.out.println("        ,.--._ .-'_..--...-'");
		System.out.println("       '-\"'. _/_ /  ..--''\"\"'-.				 ______         _     _     _     _              _____     _                 _ ");
		System.out.println("       _.--\"\"...:._:(_ ..:\"::. \\			|  ____|       | |   (_)   | |   | |            |_   _|   | |               | |");
		System.out.println("    .-' ..::--\"\"_(##)#)\"':. \\ \\)    \\ _|_ /		| |__ ___  _ __| |__  _  __| | __| | ___ _ __     | |  ___| | __ _ _ __   __| |");
		System.out.println("   /_:-:'/  :__(##)##)    ): )   '-./'   '\\.-'		|  __/ _ \\| '__| '_ \\| |/ _` |/ _` |/ _ \\ '_ \\    | | / __| |/ _` | '_ \\ / _` |");
		System.out.println("   \"  / |  :' :/\"\"\\///)  /:.'    --(       )--		| | | (_) | |  | |_) | | (_| | (_| |  __/ | | |  _| |_\\__ \\ | (_| | | | | (_| |");
		System.out.println("     / :( :( :(   (#//)  \"       .-'\\.___./'-.		|_|  \\___/|_|  |_.__/|_|\\__,_|\\__,_|\\___|_| |_| |_____|___/_|\\__,_|_| |_|\\__,_|");
		System.out.println("    / :/|\\ :\\_:\\   \\#//\\            /  |  \\");
		System.out.println("    |:/ | \"\"--':\\   (#//)              '");
		System.out.println("    \\/  \\ :|  \\ :\\  (#//)");
		System.out.println("         \\:\\   '.':. \\#//\\");
		System.out.println("          ':|    \"--'(#///)");
		System.out.println("                     (#///)				Your objective is to collect all of the four treasures and escape the");
		System.out.println("                     (#///)         ___/\"\"\\		island. After collecting each of the four treasures, all players must");
		System.out.println("                      \\#///\\           oo##		go to Fool's Landing and take a helicopter lift off of the island.");
		System.out.println("                      (##///)         `-6 #");
		System.out.println("                      (##///)          ,'`.");
		System.out.println("                      (##///)         // `.\\");
		System.out.println("                      (##///)        ||o   \\\\");
		System.out.println("                       \\##///\\        \\-+--//");
		System.out.println("                       (###///)       :_|_(/				 _           _    _       _              _         _");
		System.out.println("                       (sjw////)__...--:: :...__			| |   ___  _| |_ |/ ___  | |_  ___  ___ [_] _ _   | |");
		System.out.println("                       (#/::'''        :: :     \"\"--.._			| |_ / ._]  | |    [_-[  | . \\/ ._]/ . || || ' |  |_/");
		System.out.println("                  __..-'''           __;: :            \"-._		|___|\\___.  |_|    /__/  |___/\\___.\\_. ||_||_|_|  [_]");
		System.out.println("          __..--\"\"                  `---/ ;                '._	                                  	   [___|");
		System.out.println(" ___..--\"\"                             `-'                    \"-..___");
		System.out.println("");
		System.out.println("   (_ \"\"---....___                                     __...--\"\" _)");
		System.out.println("     \"\"\"--...  ___\"\"\"\"\"-----......._______......----\"\"\"     --\"\"\"");
		System.out.println("                   \"\"\"\"       ---.....   ___....-");
		System.out.println("");
		System.out.println("");
	}
	
	/**
	 * Refreshes the terminal user interface describing the current state of the game.
	 * @param Reference to the GameModel.
	 */
	protected static void refreshDisplay(GameModel gameModel) {
		
		System.out.println("=".repeat(displayCharWidth));
//		System.out.println(String.format("%-" + displayCharWidth/2 + "s" + "%-" + displayCharWidth/2 + "s", 
//				"FORBIDDEN ISLAND", "WATER LEVEL: " + gameModel.getWaterMeter().getWaterLevel()));
		System.out.println(String.format("||| FORBIDDEN ISLAND |||\t\t\tWATER METER: %s\t  ||\tTREASURE DECK: %s / 28 Cards\t||\tFLOOD DECK: %s / 24 Cards",
				gameModel.getWaterMeter().getWaterLevel(),
				gameModel.getTreasureDeck().getAllCards().size(),
				gameModel.getFloodDeck().getAllCards().size()));
		System.out.println("=".repeat(displayCharWidth));
		displayIslandBoard(gameModel);
		
		// display current player information
		System.out.println("=".repeat(displayCharWidth));
		
		// display any captured treasures
		if (gameModel.getGamePlayers().getCapturedTreasures().isEmpty())
			System.out.println("PLAYER INFORMATION\t\tCaptured Treasures: None");
		else
			System.out.println("PLAYER INFORMATION\t\tCaptured Treasures: " + gameModel.getGamePlayers().getCapturedTreasures());
			
		System.out.println("=".repeat(displayCharWidth));
		displayPlayerInformation(gameModel);
		
		// display Dialog box
		System.out.println("=".repeat(displayCharWidth));
		System.out.println(String.format("%-" + displayCharWidth/2 + "s" + "%-" + displayCharWidth/2 + "s", 
				"GAME DIALOG", "NOTE: To use Heli Lift or Sandbag at any time, enter [HELI] or [SAND]"));
		System.out.println("=".repeat(displayCharWidth));
	}
	
	/**
	 * Method to print the island board structure on console screen.
	 * @param Reference to GameModel.
	 */
	private static void displayIslandBoard(GameModel gameModel) {

		String outputString = "";
		String vertBars = "-".repeat(tileCharWidth);
		String leftWaves = "      ~~~~" + " ".repeat(tileCharWidth - 10);
		String rightWaves = " ".repeat(tileCharWidth - 10) + "~~~~      "; 
		
		// retrieve Island board state from model
		IslandTile[][] boardStructure = gameModel.getIslandBoard().getBoardStructure();
		
		// iterate of island grid rows
		for (int i = 0; i < boardStructure.length; i++) {
			
			int rowLength = boardStructure[i].length;
			int rowOffset = ((rowLength * 2) % 6) / 2; // offset needed to from island structure

			// add top bar of island tiles
			outputString += " ".repeat(tileCharWidth * rowOffset);
			for (int j = 0; j < rowLength; j++) {
				if (boardStructure[i][j].isSunk()) {
					outputString += " ".repeat(tileCharWidth);
				} else {
					outputString += vertBars;
				}
			}
			outputString += " ".repeat(tileCharWidth * rowOffset) + "\n";
			
			// add row structure offset before
			outputString += leftWaves.repeat(rowOffset);
		
			// iterate over island grid columns; show island tile name
			for (int j = 0; j < rowLength; j++) {
				
				if (! boardStructure[i][j].isSunk()) {
					// add specific tile name
					outputString += "| ";
					outputString += String.format("%-" + (tileCharWidth - 4) + "s",
							String.format("%" + ((tileCharWidth - 4 + (boardStructure[i][j].getName()).length()) / 2) + "s", boardStructure[i][j].getName()));
					outputString += " |";
				} else {
					outputString += leftWaves;
				}
			}
			outputString += rightWaves.repeat(rowOffset);
			
			// add row structure offset before
			outputString += "\n";
			outputString += " ".repeat(tileCharWidth * rowOffset);
			
			// iterate over island grid columns; show treasure on island tile
			for (int j = 0; j < boardStructure[i].length; j++) {
				
				if (! boardStructure[i][j].isSunk()) {

					// add treasure name
					outputString += "| ";
					if (boardStructure[i][j].getAssociatedTreasure() != null) {
						String treasureName = "*" + boardStructure[i][j].getAssociatedTreasure().getName() + "*";
						outputString += String.format("%-" + (tileCharWidth - 4) + "s",
								String.format("%" + ((tileCharWidth - 4 + (treasureName).length()) / 2) + "s", treasureName));
					} else {
						outputString += " ".repeat(tileCharWidth - 4);
					}
					outputString += " |";
				} else {
					outputString += " ".repeat(tileCharWidth);
				}
			}
			
			// add row structure offset before
			outputString += "\n";
			outputString += rightWaves.repeat(rowOffset);
			
			// iterate over island grid columns; show flooded status
			for (int j = 0; j < boardStructure[i].length; j++) {
				
				if (! boardStructure[i][j].isSunk()) {

					// add flooded status String
					outputString += "| ";

					if (boardStructure[i][j].isSafe())
							outputString += String.format("%-" + (tileCharWidth - 4) + "s", String.format("%" + ((tileCharWidth - 4 + (" ").length()) / 2) + "s", " "));
					else if (boardStructure[i][j].isFlooded())
							outputString += String.format("%-" + (tileCharWidth - 4) + "s", String.format("%" + ((tileCharWidth - 4 + ("~~~ FLOODED ~~~").length()) / 2) + "s", "~~~ FLOODED ~~~"));
						
					outputString += " |";
				} else {
					outputString += rightWaves;
				}
			}
			outputString += leftWaves.repeat(rowOffset);
			
			// add row structure offset before
			outputString += "\n";
			outputString += " ".repeat(tileCharWidth * rowOffset);
			
			// iterate over island grid columns; show Pawn locations
			for (int j = 0; j < boardStructure[i].length; j++) {
				
				if (! boardStructure[i][j].isSunk()) {
					
					// get players on current tile
					List<String> playersOnTile = new ArrayList<String>();
					for (Player player : gameModel.getGamePlayers()) {
						if (player.getPawn().getTile().equals(boardStructure[i][j])) {
							playersOnTile.add(player.getName());
						}
					}

					// add specific tile details
					outputString += "| ";
					if (playersOnTile.size() > 1) {
						
						// only show first letters of names
						String firstLetters = "";
						for (String p : playersOnTile) {
							firstLetters += p.charAt(0);
							if (playersOnTile.indexOf(p) != playersOnTile.size() - 1)
								firstLetters += " & ";
						}
						outputString += String.format("%-" + (tileCharWidth - 4) + "s",
								String.format("%" + ((tileCharWidth - 4 + (firstLetters.length())) / 2) + "s", firstLetters));
					} else if (playersOnTile.size() == 1) {
						outputString += String.format("%-" + (tileCharWidth - 4) + "s",
								String.format("%" + ((tileCharWidth - 4 + (playersOnTile.get(0).length())) / 2) + "s", playersOnTile.get(0)));
					} else {
						outputString += " ".repeat(tileCharWidth - 4);
					}
					outputString += " |";
				} else {
					outputString += " ".repeat(tileCharWidth);
				}
			}

			// add bottom bar of island tiles
			outputString += "\n" + " ".repeat(tileCharWidth * rowOffset);
			for (int j = 0; j < rowLength; j++) {
				if (boardStructure[i][j].isSunk()) {
					outputString += " ".repeat(tileCharWidth);
				} else {
					outputString += vertBars;
				}
			}
			outputString += " ".repeat(tileCharWidth * rowOffset) + "\n";					
		}
		
		System.out.print(outputString);
	}
	
	/**
	 * Static method to display player information based on the current state of game play.
	 * @param Reference to GameModel.
	 */
	private static void displayPlayerInformation(GameModel gameModel) {

		// Retrieve players' current information from model
		final int maxTreasureCards = 5;
		final int playerCount = gameModel.getGamePlayers().getPlayersList().size();
		final List<Player> playerList = gameModel.getGamePlayers().getPlayersList();
		for (Player p : gameModel.getGamePlayers()) {
			System.out.printf("%" + (-6*tileCharWidth)/playerCount + "s", p.getName() + " - " + p.getRole()); // left alignment 
		}
		System.out.println(); // newline 
		for (@SuppressWarnings("unused") Player p : gameModel.getGamePlayers()) {
			System.out.printf("%" + (-6*tileCharWidth)/playerCount + "s", "Cards in hand:"); // left alignment 
		}
		System.out.println(); // newline 
		
		// List currently held treasure cards
		for (int i = 0; i < maxTreasureCards; i++) {
			for (int j = 0; j < playerCount; j++) {
				if (i < playerList.get(j).getCards().size())
					System.out.printf("%" + (-6*tileCharWidth)/playerCount + "s", "  " + (i+1) + ". " + playerList.get(j).getCards().get(i).getName()); // left alignment 
				else
					System.out.printf("%" + (-6*tileCharWidth)/playerCount + "s", "  " + (i+1) + ". "+ "----------"); // left alignment 
			}
			System.out.println(); //newline
		}
	}
	
	/**
	 * Displays ending view, with message giving reason for game end
	 */
	protected static void displayEnding(GameEndings ending) {
		
		String gameOverSign = "";
		gameOverSign += "\t\t   _____          __  __ ______    ______      ________ _____\n";
		gameOverSign += "\t\t  / ____|   /\\   |  \\/  |  ____|  / __ \\ \\    / /  ____|  __ \\\n";
		gameOverSign += "\t\t | |  __   /  \\  | \\  / | |__    | |  | \\ \\  / /| |__  | |__) |\n";
		gameOverSign += "\t\t | | |_ | / /\\ \\ | |\\/| |  __|   | |  | |\\ \\/ / |  __| |  _  /\n";
		gameOverSign += "\t\t | |__| |/ ____ \\| |  | | |____  | |__| | \\  /  | |____| | \\ \\\n";
		gameOverSign += "\t\t  \\_____/_/    \\_\\_|  |_|______|  \\____/   \\/   |______|_|  \\_\\\n";
		
		System.out.println(); // newline
		switch(ending) {
		
		case FOOLS_LANDING_SUNK:
			System.out.println("Fool's Landing has sunk, which means it is...");
			System.out.println(gameOverSign);
			break;
			
		case TREASURE_SUNK:
			System.out.println("A treasure has sunk, which means it is...");
			System.out.println(gameOverSign);
			break;
			
		case PLAYER_SUNK:
			System.out.println("A player has sunk, which means it is...");
			System.out.println(gameOverSign);
			break;

		case MAX_WATER_LEVEL:
			System.out.println("The maximum water level has been reached, which means it is...");
			System.out.println(gameOverSign);
			break;

		case WIN:
			System.out.println("You have succesfully escaped the island with all of the treasures, you have won the game!\n"); // TODO: add congratulations (Cluedoesque?)
			System.out.println("	    _____                            _         _       _   _                     _");
			System.out.println("	   / ____|                          | |       | |     | | (_)                   | |");
			System.out.println("	  | |     ___  _ __   __ _ _ __ __ _| |_ _   _| | __ _| |_ _  ___  _ __  ___    | |");
			System.out.println("	  | |    / _ \\| '_ \\ / _` | '__/ _` | __| | | | |/ _` | __| |/ _ \\| '_ \\/ __|   | |");
			System.out.println("	  | |___| (_) | | | | (_| | | | (_| | |_| |_| | | (_| | |_| | (_) | | | \\__ \\   |_|");
			System.out.println("	   \\_____\\___/|_| |_|\\__, |_|  \\__,_|\\__|\\__,_|_|\\__,_|\\__|_|\\___/|_| |_|___/   (_)");
			System.out.println("			     __/  |");   
			System.out.println("			    |____/");                                               

			break;
			
		default:
			System.out.println("The game has ended");
			System.out.println(gameOverSign);
			break;
				
		}	
		System.out.println("\nThank you for playing!");
	}
	
// TODO: delete this if not needed but might be cool
//	          ______.........--=T=--.........______
//			     .             |:|
//			:-. //           /""""""-.
//			': '-._____..--""(""""""()`---.__
//			 /:   _..__   ''  ":""""'[] |""`\\
//			 ': :'     `-.     _:._     '"""" :
//			  ::          '--=:____:.___....-"
//			                    O"       O" grp

}
