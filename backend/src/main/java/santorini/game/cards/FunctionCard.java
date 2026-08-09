package santorini.game.cards;

/**
 * Abstract base class representing a function card in the game.
 * Each function card has a name, description, and usage status.
 *
 * Refactored: Removed activateCardEffect(Player, GameController) method.
 * The skip-card activation logic is now handled by GameService,
 * which checks canUse() and calls markUsed() directly.
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
     * Checks if this card can currently be used.
     *
     * @return true if the card has not been used yet
     */
    public boolean canUse() {
        return !isUsed;
    }

    /**
     * Marks this card as used.
     */
    public void markUsed() {
        isUsed = true;
    }
}
