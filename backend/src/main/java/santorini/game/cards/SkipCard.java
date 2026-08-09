package santorini.game.cards;

/**
 * Represents a Skip Card that allows a player to skip the next player's turn.
 * The card can only be used once.
 *
 * Refactored: Removed JOptionPane and GameController dependency.
 * The skip-card activation logic (prompting the user, advancing turns)
 * is now handled by GameService.
 *
 * @author Xin Thung
 */
public class SkipCard extends FunctionCard {

    /**
     * Constructor for the SkipCard.
     * Sets the name and description of the card.
     */
    public SkipCard() {
        super("Skip Card", "You can skip your next player's turn.");
    }
}
