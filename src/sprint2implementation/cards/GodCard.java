package sprint2implementation.cards;

/**
 * Abstract base class representing a God Card in the game.
 * Each God Card has a name and a description and implements a special ability.
 *
 * @author Emil
 * Modified by: Yee Peen
 */
public abstract class GodCard implements SpecialAbility{

    /**
     * The name of the god represented by the card.
     */
    protected String name;

    /**
     * A short description of the god's special ability.
     */
    protected String description;

    /**
     * Constructor of GodCard.
     *
     * @param name the name of teh god
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
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the god's special ability.
     *
     * @return the description of the special ability
     */
    public String getDescription() {
        return description;
    }

}
