package sprint3implementation.characters;

import sprint3implementation.cards.FunctionCard;
import sprint3implementation.cards.GodCard;
import sprint3implementation.main.GameController;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a player in the game, including their workers, icon, assigned GodCard,
 * and any function cards they possess. Manages player actions and state.
 * <p>
 * Responsible for tracking player-specific data such as current selected worker,
 * player's icon, GodCard abilities, and function cards usage.
 * </p>
 *
 * @author Yee Peen
 * modified by Xin Thung, Tiffany
 */
public class Player {

    /**
     * A map representing the player's position with associated tower icons.
     */
    private Map<Integer, ImageIcon> playerPositionTower = new HashMap<>();

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
     * The player's icon.
     */
    private ImageIcon playerIcon;

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
     * @param imagePath the URL to the player's icon image
     * @param workerNum the number of workers to initialize for the player
     */
    public Player(String name, URL imagePath, int workerNum)
    {
        this.name = name;
        setPlayerImageIcon(imagePath);
        playerPositionTower.put(0, new ImageIcon(imagePath));
        this.workerList = new ArrayList<>();
        this.workerNum = workerNum;

        initialiseWorkers();

        loadCombinedTowerPlayerIcons();

    }

    /**
     * Loads tower icons for levels 1 to 3 based on player number.
     */
    public void loadCombinedTowerPlayerIcons(){
        String playerNum = name.toLowerCase().contains("1") ? "1" : "2";

        playerPositionTower.put(1, new ImageIcon(getClass().getResource("/pics/Lvl_1_player_" + playerNum + ".png")));
        playerPositionTower.put(2, new ImageIcon(getClass().getResource("/pics/Lvl_2_player_" + playerNum + ".png")));
        playerPositionTower.put(3, new ImageIcon(getClass().getResource("/pics/Lvl_3_player_" + playerNum + ".png")));
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
     * Returns the player's position to tower image mapping.
     *
     * @return a map of tower levels to icons
     */
    public Map<Integer, ImageIcon> getPlayerPositionTower() {
        return playerPositionTower;
    }

    /**
     * Sets the icon for a specific tower level.
     *
     * @param num the tower level
     * @param imagePath the image path to set as icon
     */
    public void setPlayerPositionTower(int num, URL imagePath) {
        playerPositionTower.put(num, new ImageIcon(imagePath));
    }

    /**
     * Returns the player's icon.
     *
     * @return the player's ImageIcon
     */
    public ImageIcon getPlayerIcon() {
        return playerIcon;
    }

    /**
     * Sets the player's main image icon.
     *
     * @param imagePath the image path to use
     */
    private void setPlayerImageIcon(URL imagePath) {
        playerIcon = new ImageIcon(imagePath);
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
     * Attempts to use a function card with the given name.
     * If the card is found and unused, activates its effect.
     *
     * @param cardName       the name of the function card to use
     * @param gameController reference to the GameController managing the game
     * @return true if the card effect was successfully activated; false otherwise
     */
    public boolean useFunctionCard(String cardName, GameController gameController) {
        for (FunctionCard functionCard : functionCards) {
            if (functionCard.getName().equalsIgnoreCase(cardName) && !functionCard.isUsed()) {
                // if the card has not been used
                // activate the card's effect
                // if the card works return true, otherwise false
                boolean success = functionCard.activateCardEffect(this,gameController);
                return success;

            }
        }
        return false;
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
