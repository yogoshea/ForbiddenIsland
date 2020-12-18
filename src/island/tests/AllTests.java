package island.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CardsTest.class,
				ControllersTest.class,
				DecksTest.class,
				GameSetupTest.class,
				ObserversTest.class,
				PlayersTest.class,
				UserInputTest.class })
public class AllTests {

}
