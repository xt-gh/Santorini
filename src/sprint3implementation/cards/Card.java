package sprint3implementation.cards;

/**
 * Interface representing a generic card in the game.
 * All cards must provide methods to retrieve their name and description.
 *
 * @author Xin Thung
 */
public interface Card {

    /**
     * Returns the name of the card.
     *
     * @return the card's name
     */
    String getName();

    /**
     * Returns the description of the card.
     *
     * @return the card's description
     */
    String getDescription();
}
