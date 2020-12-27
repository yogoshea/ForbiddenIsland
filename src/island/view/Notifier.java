package island.view;

import island.cards.Card;
import island.cards.SpecialCardAbility;
import island.components.IslandTile;
import island.components.Treasure;
import island.players.Player;

/**
 * Class to hold methods which print messages to the user regarding game events
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class Notifier {
	
	private GameView gameView;
	
	protected Notifier(GameView gameView) {
		this.gameView = gameView;
	}
	
	//******************************************************************************************************
	//The following are methods called by Controllers, displaying a message to the user giving information of game events.
	//This ensures the gameView can decide how exactly to tell the user about the event.
	//This is to match the MVC pattern and allow for different Views to be implemented with the same Controllers and Model.
	//******************************************************************************************************

	/**
	 * Displays message telling user they have skipped their action
	 */
	public void showSkippingActions() {
		gameView.show("Skipping actions...");
	}
	
	/**
	 * Displays message telling user there are no tiles to move to
	 */
	public void showNoMoveTiles() {
		gameView.show("No tiles available to move to");
	}
	
	/**
	 * Displays message to show a successfull move has been made
	 * @param player who moved
	 * @param tile player moved to
	 */
	public void showSuccessfulMove(Player player, IslandTile tile) {
		gameView.show(player.getName() + " has moved to " + tile.getName());
	}
	
	/**
	 * Displays message telling user there are no tiles to move to
	 */
	public void showNoShoreUpTiles() {
		gameView.show("No available tiles to shore-up");
	}
	
	/**
	 * Displays message to show tile has been shored up
	 * @param tile that was shored up
	 */
	public void showSuccessfulShoreUp(IslandTile tile) {
		gameView.show(tile.getName() + " has been shored up");
	}
	
	/**
	 * Displays message telling user there are no other players on their tile
	 */
	public void showNoAvailablePlayers() {
		gameView.show("No other players on your tile");
	}
	
	/**
	 * Displays message telling user they have no treasure cards
	 */
	public void showNoTreasureCards() {
		gameView.show("No treasure cards in your hand");
	}
	
	/**
	 * Displays message telling user there is no treasure on their current tile
	 */
	public void showNoTreasure(IslandTile tile) {
		gameView.show("No treasure found at " + tile.getName());
	}
	
	/**
	 * Displays message telling user there is no treasure on their current tile
	 */
	public void showNotEnoughCards(Treasure treasure) {
		gameView.show("You need 4 " + treasure.getName() + " cards to capture this treasure");
	}
	
	/**
	 * Tells user which players turn it is
	 */
	public void showPlayerTurn(Player p) {
		gameView.show("PLAYER TURN: " + p.getName());
	}
	
	/**
	 * Tells user that treasure cards are being drawn
	 */
	public void showDrawingTreasureCards() {
		gameView.show("Drawing 2 treasure cards...");
	}
	
	/**
	 * Tells user that treasure cards are being drawn
	 */
	public void showTreasureCardDrawn(Card<?> card) {
		gameView.show("\n> You have drawn: " + card.getName());
	}
	
	/**
	 * Displays message showing card has been given to another player
	 * @param card that was given
	 * @param giver, player who gave card
	 * @param reciever, player who recieved card
	 */
	public void showCardGiven(Card<?> card,Player giver, Player reciever) {
		gameView.show(giver.toString()+ " has given " +card.toString()+ " to " +reciever.toString());
	}
	
	/**
	 * Tells user that treasure cards are being drawn
	 */
	public void showWaterRise(int level) {
		gameView.show("\nNEW WATER LEVEL: " + Integer.toString(level));
		gameView.show("The flood deck has been refilled");
	}
	
	/**
	 * Tells user that the player doesn't have a helicopter lift card
	 */
	public void showNoSpecialCard(Player player, SpecialCardAbility ability) {
		gameView.show(player.getName() + " does not have a "+ability.toString()+" Sandbag card");
	}
	
	
	/**
	 * Tells user that the player doesn't have a helicopter lift card
	 */
	public void showTileFlooded(IslandTile tile) {
		gameView.show(tile.getName() + " has been flooded!!");
	}
	
	/**
	 * Tell user a tile has been sunk
	 * @param tile
	 */
	public void showTileSunk(IslandTile tile) {
		gameView.show(tile.getName() + " has SUNK!!!");
	}
	
	/**
	 * Tell user a tile has been flooded
	 * @param treasure
	 */
	public void showAlreadyCaptured(Treasure treasure) {
		gameView.show(treasure.getName() + " has already been captured");
	}
	
	/**
	 * Tell user they have captured a treasure
	 * @param treasure
	 */
	public void showTreasureCaptured(Treasure treasure) {
		gameView.show("You have captured the "+treasure.getName()+" treasure!!");
	}
	
	/**
	 * Tell user that both treasure tiles with the same associated treasure have sunk without the treasure being captured
	 * @param firstTile, one of the treasure tiles that sunk
	 * @param secondTile, the other treasure tile that sunk
	 */
	public void showTreasureSunk(IslandTile firstTile, IslandTile secondTile) {
		gameView.show("\n" + firstTile.toString()+" and "+secondTile.toString()+" have sunk and the "+firstTile.getAssociatedTreasure()+" hasn't been captured...");
	}
	
	/**
	 * Tell user that a player has sunk
	 * @param player that sunk
	 */
	public void showPlayerSunk(Player player) {
		gameView.show("\n"+player.toString()+" could not reach any safes tiles!!!");
	}
	
	/**
	 * Tell user they have discarded a card
	 * @param card to discard
	 */
	public void showCardDiscarded(Card<?> card) {
		System.out.println("You have discarded a "+card.toString());
	}
	
	
	public void showDiscardCancelled(Player player) {
		System.out.println("\n" + player.toString() + ": Cards in hand has decreased to 5. Discard no longer required.");
	}

}
