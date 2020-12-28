package island.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.Permission;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import island.cards.SpecialCard;
import island.cards.SpecialCardAbility;
import island.components.GameModel;
import island.components.IslandTile;
import island.components.Treasure;
import island.controllers.GameController;
import island.controllers.SpecialCardController;
import island.observers.FoolsLandingObserver;
import island.observers.PlayerSunkObserver;
import island.observers.TreasureTilesObserver;
import island.observers.WaterMeterObserver;
import island.players.Diver;
import island.players.Engineer;
import island.players.GamePlayers;
import island.players.Player;
import island.view.GameView;

public class GameWinTest {
	
	private GameModel gameModel;
	private GameView gameView;
	private GameController gameController;
	private GamePlayers players;
	private InputStream backup;
	
	// Subclasses to check game exit with exceptions
	@SuppressWarnings("serial")
	private static class GameExitException extends SecurityException {
		public final int status;
        public GameExitException(int status) {
            super("Testing GameExitException");
            this.status = status;
        }
    }

	private static class CheckGameExitSecurityManager extends SecurityManager {
		@Override
		public void checkPermission(Permission p) {} 
		@Override
		public void checkPermission(Permission p, Object c) {}
        @Override
        public void checkExit(int status) {
            super.checkExit(status);
            throw new GameExitException(status);
        }
    }

	@Before
	public void setUp() throws Exception {
		
		// Provide necessary user input for test
		String sampleUserInput = "1\n"; 
		backup = System.in; // backup
		InputStream in = new ByteArrayInputStream(sampleUserInput.getBytes());
		System.setIn(in);
		
		gameModel = GameModel.getInstance();
		gameView =  GameView.getInstance();
		gameController = GameController.getInstance(gameModel, gameView);
		players = gameModel.getGamePlayers();
        System.setSecurityManager(new CheckGameExitSecurityManager());
	}
	
	@After
	public void tearDown() throws Exception {
		GameModel.reset();
		GameView.reset();
		GameController.reset();
		FoolsLandingObserver.reset();
		PlayerSunkObserver.reset();
		TreasureTilesObserver.reset();
		WaterMeterObserver.reset();
		for (IslandTile tile : IslandTile.values())
			tile.setToSafe();

		System.setSecurityManager(null);
	    System.setIn(backup); // Reset system input
	}
	
	@Test
	public void test_winGame_gameExit() {
		
		players.addPlayer(new Diver("Alice"));
		players.addPlayer(new Engineer("Bob"));
		
		// Let players capture all treasures
		for (Treasure treasure : Treasure.values())
			players.addTreasure(treasure);
		
		// Place all players on Fool's Landing
		for (Player player : players)
			player.getPawn().setTile(IslandTile.FOOLS_LANDING);
		
		// Give player Helicopter Lift card
		players.getPlayersList().get(0).addCard(new SpecialCard(SpecialCardAbility.HELICOPTER_LIFT));
		
		// Attempt Helicopter Lift off the island
		try {
			
			SpecialCardController.getInstance(gameModel, gameView, gameController).specialCardRequest(GameView.HELI);
			
        } catch (GameExitException e) {
           assertEquals("Check game exit status", 0, e.status);
        }
	}

}
