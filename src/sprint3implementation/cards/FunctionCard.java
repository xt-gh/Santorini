package sprint3implementation.cards;

import sprint3implementation.characters.Player;
import sprint3implementation.main.GameController;

/**
 * Abstract base class representing a function card in the game.
 * Each function card has a name, description, and usage status.
 * Subclasses must implement the {@code activateCardEffect} method
 * to define the card's specific effect when activated.
 *
 * @author Xin Thung
 */
public abstract class FunctionCard implements Card{

    /**
     * The name of the card
     */
    protected String name;

    /**
     * A brief description of the card's effect
     */

    protected String description;

    /**
     * Flag indicating whether this card has been used
     */
    protected boolean isUsed = false;

    /**
     * Constructs a FunctionCard with the given name and description.
     *
     * @param name the name of the card
     * @param description the description of the card's effect
     */
    public FunctionCard(String name, String description){
        this.name = name;
        this.description = description;
    }

    /**
     * Returns the name of the card.
     *
     * @return card name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the card.
     *
     * @return card description
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Checks if this card has already been used.
     *
     * @return true if the card has been used; false otherwise
     */
    public boolean isUsed() {
        return isUsed;
    }

    /**
     * Sets the usage status of this card.
     *
     * @param used true if the card is used; false otherwise
     */
    public void setUsed(boolean used) {
        isUsed = used;
    }

    /**
     * Activates the card's special effect.
     * Implementations should apply the card's effect and
     * return true if the effect was successfully applied,
     * or false otherwise (e.g., if the card was already used).
     *
     * @param currentPlayer the player who activates the card
     * @param gameController the controller managing the game state
     * @return true if the card effect was activated successfully; false otherwise
     */
    public abstract boolean activateCardEffect(Player currentPlayer, GameController gameController);
}
