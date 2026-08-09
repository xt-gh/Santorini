package santorini.game.characters;

import santorini.game.cards.FunctionCard;
import santorini.game.cards.GodCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game, including their workers, assigned GodCard,
 * and any function cards they possess. Manages player actions and state.
 *
 * Refactored: Removed all ImageIcon/URL dependencies (playerIcon, playerPositionTower).
 * The frontend handles all player icon rendering.
 *
 * @author Yee Peen
 * modified by Xin Thung, Tiffany
 */
public class Player {

    /**
     * A list of workers owned by the player.
     */
    private List<Worker> workerList;

    /**
     * The currently selected worker for this player's turn.
     */
    private Worker currentWorker = null;

    /**
     * The GodCard assigned to this player, which grants special abilities.
     */
    private GodCard godCard;

    /**
     * The name of the player.
     */
    private String name;

    /**
     * The number of workers the player has.
     */
    private int workerNum;

    /**
     * Indicates whether the player's most recent action was successful.
     */
    private boolean isActionSuccessful = false;

    /**
     * Flag indicating if the player has built under themselves during a turn.
     */
    private boolean builtUnderSelf = false;

    /**
     * List of function cards owned by the player.
     */
    private List<FunctionCard> functionCards = new ArrayList<>();


    /**
     * Constructor of Player.
     *
     * @param name the name of the player
     * @param workerNum the number of workers to initialize for the player
     */
    public Player(String name, int workerNum)
    {
        this.name = name;
        this.workerList = new ArrayList<>();
        this.workerNum = workerNum;

        initialiseWorkers();
    }

    /**
     * Initializes the player's workers based on the specified number.
     */
    private void initialiseWorkers() {
        for (int i = 0; i < workerNum; i++) {
            Worker worker = new Worker(this);
            addWorker(worker);
        }
    }

    /**
     * Adds a worker to the player's worker list.
     *
     * @param worker the Worker to add
     */
    public void addWorker(Worker worker) {
        workerList.add(worker);
    }

    /**
     * Returns the name of player
     *
     * @return the name of player
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the God card assigned to the player.
     *
     * @return the player's GodCard
     */
    public GodCard getGodCard(){
        return godCard;
    }

    /**
     * Sets the player's GodCard.
     *
     * @param godCard the GodCard to assign
     */
    public void setGodCard(GodCard godCard) {
        this.godCard = godCard;
    }


    /**
     * Returns the list of workers owned by the player.
     *
     * @return a list of Worker objects
     */
    public List<Worker> getWorkerList() {
        return workerList;
    }

    /**
     * Returns the currently selected worker.
     *
     * @return the current Worker
     */
    public Worker getCurrentWorker() {
        return currentWorker;
    }

    /**
     * Sets the currently selected worker.
     *
     * @param worker the Worker to set as current
     */
    public void setCurrentWorker(Worker worker) {
        this.currentWorker = worker;
    }

    /**
     * Clears the currently selected worker.
     */
    public void clearCurrentWorker() {
        this.currentWorker = null;
    }

    /**
     * Checks whether the player's action was successful.
     *
     * @return true if the action was successful; false otherwise
     */
    public boolean isActionSuccessful() {
        return isActionSuccessful;
    }

    /**
     * Sets the success status of the player's recent action.
     *
     * @param actionSuccessful true if the action succeeded; false otherwise
     */
    public void setActionSuccessful(Boolean actionSuccessful) {
        isActionSuccessful = actionSuccessful;
    }


    /**
     * Sets whether the player has built under their own worker this turn.
     *
     * @param flag true if built under self, false otherwise
     */
    public void setBuiltUnderSelf(boolean flag){
        this.builtUnderSelf = flag;
    }

    /**
     * Returns the function card with the given name if it exists and hasn't been used.
     *
     * @param cardName the name of the function card to look up
     * @return the matching FunctionCard, or null if not found or already used
     */
    public FunctionCard getFunctionCard(String cardName) {
        for (FunctionCard functionCard : functionCards) {
            if (functionCard.getName().equalsIgnoreCase(cardName) && !functionCard.isUsed()) {
                return functionCard;
            }
        }
        return null;
    }

    /**
     * Returns all function cards owned by the player.
     *
     * @return list of function cards
     */
    public List<FunctionCard> getFunctionCards() {
        return functionCards;
    }

    /**
     * Adds a function card to the player's collection.
     *
     * @param functionCard the FunctionCard to add
     */
    public void addFunctionCard(FunctionCard functionCard) {
        functionCards.add(functionCard);
    }

}
