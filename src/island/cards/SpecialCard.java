package island.cards;

/**
 * Class to represent special cards used in game, each has an associated
 * SpecialCardAbility; HELICOPTER_LIFT, SANDBAG, WATER_RISE
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class SpecialCard extends Card<SpecialCardAbility> {

	/**
	 * Constructor for SpecialCard instances.
	 * @param SpecialCardAbility associated with a given SpecialCard.
	 */
	public SpecialCard(SpecialCardAbility cardAbility) {
		super(cardAbility.getName(), cardAbility);
	}

}
