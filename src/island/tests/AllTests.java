package island.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CardsTest.class,
				ComponentsTest.class,
				DecksTest.class,
				GameSetupTest.class,
				ObserversTest.class,
				PlayersTest.class,
				GameWinTest.class })
public class AllTests {

}
