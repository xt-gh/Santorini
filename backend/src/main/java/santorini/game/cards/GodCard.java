package santorini.game.cards;

/**
 * Abstract base class representing a God Card in the game.
 * Each God Card has a name and a description and implements a special ability
 * via the {@link SpecialAbility} interface.
 *
 * Refactored: Added pending-choice mechanism to replace JOptionPane dialogs.
 * When a god card reaches a decision point (e.g., "move again?"),
 * it sets pendingChoice=true and returns. The GameService reads this state,
 * sends it to the frontend, and calls resolveChoice() when the player responds.
 *
 * @author Emil
 * Modified by: Yee Peen, Xin Thung
 */
public abstract class GodCard implements SpecialAbility, Card{

    /**
     * The name of the god represented by the card.
     */
    protected String name;

    /**
     * A short description of the god's special ability.
     */
    protected String description;

    /**
     * Whether the god card is waiting for a player choice.
     */
    protected boolean pendingChoice = false;

    /**
     * The type of choice pending (e.g., "SECOND_MOVE", "SECOND_BUILD", "BUILD_UNDER_SELF").
     */
    protected String choiceType = null;

    /**
     * The message to display to the player for the pending choice.
     */
    protected String choiceMessage = null;

    /**
     * Constructor of GodCard.
     *
     * @param name the name of the god
     * @param description the description of the god's special ability
     */
    public GodCard(String name, String description){
        this.name = name;
        this.description = description;
    }

    /**
     * Returns the name of the god.
     *
     * @return the name of the god.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the god's special ability.
     *
     * @return the description of the special ability
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Whether this god card is waiting for a player decision.
     */
    public boolean hasPendingChoice() {
        return pendingChoice;
    }

    /**
     * Returns the type of choice pending.
     */
    public String getChoiceType() {
        return choiceType;
    }

    /**
     * Returns the message to display for the pending choice.
     */
    public String getChoiceMessage() {
        return choiceMessage;
    }

    /**
     * Resolves a pending player choice. Subclasses override to implement
     * specific behavior (e.g., Artemis sets waitingForSecondMove).
     *
     * @param accepted true if the player accepted the choice, false if declined
     */
    public void resolveChoice(boolean accepted) {
        pendingChoice = false;
        choiceType = null;
        choiceMessage = null;
    }

    /**
     * Resets the god card's internal state for a new turn or game.
     * Subclasses should override to reset their specific state fields.
     */
    public void resetState() {
        pendingChoice = false;
        choiceType = null;
        choiceMessage = null;
    }
}
