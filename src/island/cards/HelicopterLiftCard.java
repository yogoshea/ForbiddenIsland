package island.cards;

import java.util.Arrays;
import java.util.List;

import island.components.IslandTile;
import island.players.GamePlayers;
import island.players.Player;

/**
 * Class to represent Helicopter Lift cards in game.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class HelicopterLiftCard extends Card {
	
	public HelicopterLiftCard() {
//		super();
	}
	
	public String toString() {
		return "+++ Helicopter Lift Card +++";
	}
	
//	/*
//	 * Implemented so any player can move to the destination but there is only one destination
//	 * Both heli and sandbag have use methods -> parent class use method?
//	 */
//	public void use() {
//		//check if playing card will win game
//		checkForGameWin(); //Should this be an observer???
//		
//		IslandTile destination;
//		String prompt = "Where do you wish to move to?";
//		boolean used = false;
//		
//		System.out.println(prompt);
//		destination = GameScanner.getInstance().pickFromList(Arrays.asList(IslandTile.values()), prompt);
//		
//		String ans;
//		
//		for(Player p : GamePlayers.getInstance().getPlayersList()) {
//			prompt = "Does "+p.toString()+" wish to move to "+destination.name()+"?";
//			prompt += "\n[Y]/[N]";
//			System.out.println(prompt);
//			ans = GameScanner.getInstance().scanNextLine(prompt);
//			if(ans.equals("Y")) {
//				p.setCurrentTile(destination);
//				used = true;
//			}
//		}
//		
//		if(used) {
//			System.out.println("Epic helicopter cut scene");
//		}
//		
//	}
//	
//	/*
//	 * Method to check if the game has been won after playing a helicopter lift card
//	 * Put in GameObserver class? (but can only be called if heli card played
//	 */
//	private void checkForGameWin() {
//		
//		boolean allPlayersAtFoolsL = true;
//		boolean allTreasuresCaptured;
//		
//		allTreasuresCaptured = GamePlayers.getInstance().allTreasuresCaptured();
//		
//		for(Player p : GamePlayers.getInstance().getPlayersList()) {
//			if( !p.getCurrentTile().equals(IslandTile.FOOLS_LANDING) ) {
//				allPlayersAtFoolsL = false;
//				break;
//			}
//		}
//		
//		if(allPlayersAtFoolsL && allTreasuresCaptured) {
//			//TODO: Game a singleton??
//			//Game.getInstance().setGameWin();
//		}
//		
//	}

}
