package sprint3implementation.characters;

import sprint3implementation.cards.GodCard;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a player in the game, including their workers, icon, and assigned GodCard.
 *
 * @author Yee Peen
 * Modified by: Xin Thung, Tiffany
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

    private boolean builtUnderSelf = false;

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

    public void loadCombinedTowerPlayerIcons(){
        setPlayerPositionTower(1, getClass().getResource("pics/Lvl_1_player_1.png"));
        setPlayerPositionTower(2, getClass().getResource("pics/Lvl_2_player_1.png"));
        setPlayerPositionTower(3, getClass().getResource("pics/Lvl_3_player_1.png"));

        setPlayerPositionTower(1, getClass().getResource("pics/Lvl_1_player_2.png"));
        setPlayerPositionTower(1, getClass().getResource("pics/Lvl_1_player_2.png"));
        setPlayerPositionTower(1, getClass().getResource("pics/Lvl_1_player_2.png"));

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


    public void setBuiltUnderSelf(boolean flag){
        this.builtUnderSelf = flag;
    }


    public boolean hasBuiltUnderSelf() {
        return builtUnderSelf;
    }

}
