package island.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import island.cards.Card;
import island.cards.SpecialCardAbility;
import island.components.IslandTile;
import island.controllers.Action;
import island.players.Player;

/**
 * Class to provided methods for specific user prompts. To be called from
 * controller classes. Part of View components for MVC design pattern.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class Prompter {
	
	private GameView gameView;
	
	/**
	 * Constructs prompts class to be used with GameView.
	 * @param newGameView
	 */
	public Prompter(GameView gameView) {
		this.gameView = gameView;
	}
	
	/*
	 * The following are methods called by Controllers which call in turn pickFromList()
	 * to get users choice of a particular type of objects. This ensures the gameView can
	 * decide how exactly to prompt the user, without a controller specifying a string.
	 * This is to match the MVC pattern and allow for different Views to be implemented
	 * with the same Controllers and Model.
	 */
	
	/**
	 * Retrieve user choice of game's initial water level.
	 * @return integer value representing selected water level.
	 */
	public int pickStartingWaterLevel() {
		String prompt = "What water level would you like to start on?";
		List<String> startingDifficulties = Arrays.asList("Novice", "Normal", "Elite", "Legendary");
		return startingDifficulties.indexOf(gameView.pickFromList(startingDifficulties, prompt)) + 1;
	}		
	
	/**
	 * Retrieves player action choice from user input.
	 * @param Integer number of available actions.
	 */
	public Action pickAction(int availableActions) {
		String prompt = "Select one of the following actions: ("+availableActions+" remaining)";
		return gameView.pickFromList( Arrays.asList(Action.values()) , prompt);
	}
	
	/**
	 * Retrieves player choice of island tile to move to.
	 * @param List of possible tiles a player can move to.
	 * @return IslandTile selected by user.
	 */
	public IslandTile pickTileDestination(List<IslandTile> tiles) {
		String prompt = "Which tile do you wish to move to?";
		return gameView.pickFromList(tiles, prompt);
	}
	
	/**
	 * Retrieves player choice of island tiles from the possible swimmable locations.
	 * @param Player that needs to swim to another tile.
	 * @param List of IslandTiles that can be swam to.
	 * @return Player's choice of IslandTile.
	 */
	public IslandTile pickSwimmableTile(Player player, List<IslandTile> tiles) {
		String prompt = player.toString()+", YOUR TILE HAS SUNK!!\nWhich tile do you wish to move to?";
		return gameView.pickFromList(tiles, prompt);
	}
	
	/**
	 * Retrieves player choice of IslandTile to shore up.
	 * @param List of IslandTiles that player can shore up.
	 * @return Player's choice of IslandTile.
	 */
	public IslandTile pickShoreUpTile(List<IslandTile> tiles) {
		String prompt = "Which tile do you wish to shore up?";
		return gameView.pickFromList(tiles, prompt);
	}
	
	/**
	 * Check if player wishes to shore up another tile. this only applies to the Engineer. 
	 * @return Boolean of choice outcome.
	 */
	public Boolean shoreUpAnother() {
		String prompt = "As an Engineer you may shore-up 2 tiles. Shore-up another?";
		List<String> choices = Arrays.asList("Yes", "No"); //Create choices
		String choice = gameView.pickFromList(choices, prompt);
		return choice.equals("Yes");
	}
	
	/**
	 * Retrieves player choice of other player to give card to.
	 * @param List of players that can receive cards of current player.
	 * @return User's choice of player.
	 */
	public Player pickPlayerToRecieveCard(List<Player> players) {
		String prompt = "Which player do you wish to give a card to?";
		return gameView.pickFromList(players, prompt);
	}
	
	/**
	 * Retrieves player choice of card to give to player.
	 * @param List of possible treasure cards that can be given.
	 * @return Choice of card from player.
	 */
	public Card<?> pickCardToGive(List<Card<?>> treasureCards) {
		String prompt = "Which card do you wish to give?";
		return gameView.pickFromList(treasureCards, prompt);
	}
	
	/**
	 * Retrieves player choice of card to discard.
	 * @param Player which mustt discard a card.
	 * @return Player's choice of card.
	 */
	public Card<?> pickCardToDiscard(Player player) {
		List<Card<?>> cards = player.getCards();
		String prompt = player.getName() + ", you have too many cards in your hand, which do you wish to discard?";
		Card<?> card = gameView.pickFromList(cards, prompt);
		return card;
	}
	
	/**
	 * Retrieves player choice of IslandTile to move to using Helicopter Lift.
	 * @param List of possible choices of IslandTiles.
	 * @return Player's choice of IslandTile.
	 */
	public IslandTile pickHeliDestination(List<IslandTile> availableTiles) {
		String prompt = "Which tile do you wish to helicopter to?";
		return gameView.pickFromList(availableTiles, prompt);
	}
	
	/**
	 * Retrieves user input identifying who wants to use a SpecialCard.
	 * @param List of players in game.
	 * @return User choice of player.
	 */
	public Player pickRequestPlayer(List<Player> players, SpecialCardAbility ability) {
		String prompt = "Which player wants to play a " +ability.toString()+ " card?";
		return gameView.pickFromList(players, prompt);
	}
	
	/**
	 * Retrieves user's choice of players looking to avail of the Helicopter Lift.
	 * @param List of players in game.
	 * @param IslandTile players will move to.
	 * @return User's choice of players.
	 */
	public List<Player> pickHeliPlayers(List<Player> players, IslandTile destination) {
		
		String prompt;
		List<Player> heliPlayers = new ArrayList<Player>();
		
		//Check each player to see if they wish to take the lift
		for(Player player : players) {
			prompt = "Does " + player.getName() + " wish to move to " + destination.getName() + "?";
			if(gameView.pickFromList(Arrays.asList("Yes", "No"), prompt).equals("Yes")) {
				heliPlayers.add(player);
			}
		}
		return heliPlayers;
	}
	
	/**
	 * Retrieves players choice to keep or give drawn treasure card.
	 * @return Player's choice of card.
	 */
	public Boolean pickKeepOrGive() {
		String prompt = "Do you wish to keep your card or give it to another Player?";
		List<String> choices = Arrays.asList("Keep", "Give");
		String choice = gameView.pickFromList(choices, prompt);
		return choice.equals("Keep");
	}
	
	/**
	 * Gets user to control continuation of game.
	 */
	public void promptEnterToContinue() {
		String prompt = "\nTo continue, press [Enter]...";
		gameView.scanEnter(prompt);
	}
	
	/**
	 * Gets user to proceed with drawing treasure cards.
	 */
	public void promptDrawTreasureCards() {
		String prompt = "\nTo draw your two treasure cards, press [Enter]...";
		gameView.scanEnter(prompt);
	}
	
	/**
	 * Gets user to proceed with drawing flood cards.
	 */
	public void promptDrawFloodCards() {
		String prompt = "\nTo draw your flood cards, press [Enter]...";
		gameView.scanEnter(prompt);
	}
	
	/**
	 * Gets user to proceed with game after special card played.
	 */
	public void promptSpecialCardDone() {
		String prompt = "\nTo return to before request was made, press Enter[]...";
		gameView.scanEnter(prompt);
	}

}
