package island.view;

import island.cards.Card;
import island.cards.SpecialCardAbility;
import island.components.IslandTile;
import island.components.Treasure;
import island.controllers.GameEndings;
import island.players.Player;

/**
 * Class to hold static methods which print messages to the user regarding game events
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class Messages {
	
	//******************************************************************************************************
	//The following are methods called by Controllers, displaying a message to the user giving information of game events.
	//This ensures the gameView can decide how exactly to tell the user about the event.
	//This is to match the MVC pattern and allow for different Views to be implemented with the same Controllers and Model.
	//******************************************************************************************************
		
	/**
	 * Displays welcome view
	 */
	public static void showWelcome() {
		Graphics.displayWelcomeMessage();
	}
	
	/**
	 * Displays message telling user they have skipped their action
	 */
	public static void showSkippingActions() {
		System.out.println("Skipping actions...");
	}
	
	/**
	 * Displays message telling user there are no tiles to move to
	 */
	public static void showNoMoveTiles() {
		System.out.println("No tiles available to move to");
	}
	
	/**
	 * Displays message to show a successfull move has been made
	 * @param player who moved
	 * @param tile player moved to
	 */
	public static void showSuccessfulMove(Player player, IslandTile tile) {
		System.out.println(player.getName() + " has moved to " + tile.getName());
	}
	
	/**
	 * Displays message telling user there are no tiles to move to
	 */
	public static void showNoShoreUpTiles() {
		System.out.println("No available tiles to shore-up");
	}
	
	/**
	 * Displays message to show tile has been shored up
	 * @param tile that was shored up
	 */
	public static void showSuccessfulShoreUp(IslandTile tile) {
		System.out.println(tile.getName() + " has been shored up");
	}
	
	/**
	 * Displays message telling user there are no other players on their tile
	 */
	public static void showNoAvailablePlayers() {
		System.out.println("No other players on your tile");
	}
	
	/**
	 * Displays message telling user they have no treasure cards
	 */
	public static void showNoTreasureCards() {
		System.out.println("No treasure cards in your hand");
	}
	
	/**
	 * Displays message telling user there is no treasure on their current tile
	 */
	public static void showNoTreasure(IslandTile tile) {
		System.out.println("No treasure found at " + tile.getName());
	}
	
	/**
	 * Displays message telling user there is no treasure on their current tile
	 */
	public static void showNotEnoughCards(Treasure treasure) {
		System.out.println("You need 4 " + treasure.getName() + " cards to capture this treasure");
	}
	
	/**
	 * Tells user which players turn it is
	 */
	public static void showPlayerTurn(Player p) {
		System.out.println("It is the turn of: " + p.getName());
	}
	
	/**
	 * Tells user that treasure cards are being drawn
	 */
	public static void showDrawingTreasureCards() {
		System.out.println("Drawing 2 treasure cards...");
	}
	
	/**
	 * Tells user that treasure cards are being drawn
	 */
	public static void showTreasureCardDrawn(Card<?> card) {
		System.out.println("\n--> You have drawn: " + card.getName());
	}
	
	/**
	 * Displays message showing card has been given to another player
	 * @param card that was given
	 * @param giver, player who gave card
	 * @param reciever, player who recieved card
	 */
	public static void showCardGiven(Card<?> card,Player giver, Player reciever) {
		System.out.println(giver.toString()+ " has given a " +card.toString()+ " to " +reciever.toString());
	}
	
	public static void showCardDiscarded(Card<?> card) {
		System.out.println("You have discarded: " + card.getName());
	}
	/**
	 * Tells user that treasure cards are being drawn
	 */
	public static void showWaterRise(int level) {
		System.out.println("\nNEW WATER LEVEL: " + Integer.toString(level));
		System.out.println("The flood deck has been refilled");
	}
	
	/**
	 * Tells user that the player doesn't have a helicopter lift card
	 */
	public static void showNoSpecialCard(Player player, SpecialCardAbility ability) {
		System.out.println(player.getName() + " does not have a "+ability.toString()+" Sandbag card");
	}
	
	
	/**
	 * Tells user that the player doesn't have a helicopter lift card
	 */
	public static void showTileFlooded(IslandTile tile) {
		System.out.println(tile.getName() + " has been flooded!!");
	}
	
	/**
	 * Tell user a tile has been sunk
	 * @param tile
	 */
	public static void showTileSunk(IslandTile tile) {
		System.out.println(tile.getName() + " has SUNK!!!");
	}
	
	/**
	 * Tell user a tile has been flooded
	 * @param treasure
	 */
	public static void showAlreadyCaptured(Treasure treasure) {
		System.out.println(treasure.getName() + " has already been captured");
	}
	
	/**
	 * Tell user they have captured a treasure
	 * @param treasure
	 */
	public static void showTreasureCaptured(Treasure treasure) {
		System.out.println("You have captured "+treasure.getName());
	}
	
	/**
	 * Tell user that both treasure tiles with the same associated treasure have sunk without the treasure being captured
	 * @param firstTile, one of the treasure tiles that sunk
	 * @param secondTile, the other treasure tile that sunk
	 */
	public static void showTreasureSunk(IslandTile firstTile, IslandTile secondTile) {
		System.out.println("\n" + firstTile.toString()+" and "+secondTile.toString()+" are both sunk and "+firstTile.getAssociatedTreasure()+" hasn't been captured");
	}
	
	/**
	 * Tell user that a player has sunk
	 * @param player that sunk
	 */
	public static void showPlayerSunk(Player player) {
		System.out.println("\n"+player.toString()+" could not reach any safes tiles!!!");
	}

	/**
	 * Displays ending view, with message giving reason for game end
	 */
	public static void showEnding(GameEndings ending) {
		
		System.out.println(); // newline
		switch(ending) {
		
		case FOOLS_LANDING_SUNK:
			System.out.println("GAME OVER - Fools Landing has Sunk");
			break;
			
		case TREASURE_SUNK:
			System.out.println("GAME OVER - A treasure has Sunk");
			break;
			
		case PLAYER_SUNK:
			System.out.println("GAME OVER - A player has Sunk");
			break;

		case MAX_WATER_LEVEL:
			System.out.println("GAME OVER - Maximum water level has been reached");
			break;

		case WIN:
			System.out.println("!!!! The game has been won !!!!"); // TODO: add congratulations (Cluedoesque?)
			break;
			
		default:
			System.out.println("Game has ended");
			break;
				
		}	
	}



}
