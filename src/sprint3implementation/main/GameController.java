package sprint3implementation.main;

import sprint3implementation.actions.MoveAction;
import sprint3implementation.characters.Player;
import sprint3implementation.characters.Worker;
import sprint3implementation.grounds.Board;
import sprint3implementation.grounds.Tile;
import sprint3implementation.timers.PlayerTimer;
import sprint3implementation.timers.TimerListener;
import sprint3implementation.towers.Tower;

import javax.swing.*;
import java.util.*;
import java.util.Timer;

//singleton - getInstance() method enforces one GameController instance.

/**
 * GameController is a singleton class that controls the flow of the game.
 * It manages player turns, board interactions, worker movements and turn resents.
 *
 * @author Yee Peen
 * Modified by: Xin Thung, Tiffany
 */
public class GameController {

    /**
     * Singleton instance of GameController.
     */
    private static GameController instance;

    /**
     * The game board.
     */
    private Board gameBoard;

    /**
     * List of players mapped by their player number.
     */
    private Map<Integer, Player> playerList; // Use of a map here is to store the players with their respective player number

    /**
     * The player whose turn is currently active.
     */
    private Player currentPlayer;

    /**
     * The tile currently selected by the player
     */
    private Tile selectedTile;

    /**
     * The tile where the current worker just moved to.
     */
    private Tile lastMovedTile; // the tile where the worker just moved to during the Movement phase

    /**
     * Index to track the current player.
     */
    private int currentPlayerIndex;

    /**
     * Flag to indicate if a worker is currently moving.
     */
    private boolean isMoving = false;

    /**
     * Label used in the UI to display the current player's turn
     */
    private JLabel currentPlayerLabel; //indicator for the current player's turn

    private PlayerTimer playerTimer;
    private JLabel timerLabel;



    /**
     * Private constructor for singleton instance.
     *
     * @param gameBoard The game board.
     * @param playerList The players in the game.
     * @param currentPlayerLabel JLabel used to show whose turn it is.
     */
    private GameController(Board gameBoard, Map<Integer, Player> playerList, JLabel currentPlayerLabel, JLabel timerLabel) {
        this.gameBoard = gameBoard;
        this.playerList = playerList; // store players as a map so that the player can be accessed with a specific player number
        this.currentPlayerIndex = 0;
        this.currentPlayer = playerList.get(currentPlayerIndex);
        this.currentPlayerLabel = currentPlayerLabel;
        this.timerLabel = timerLabel;
        updateCurrentPlayerLabel();

        randomiseWorkers();
        setupTileListeners();
//        updateCurrentTimerLabel();

        this.playerTimer = new PlayerTimer(timerLabel, currentPlayer.getName());
//        this.playerTimer.setTimerListener(
//            new TimerListener() {
//            @Override
//            public void onTimeOut() {
//                JOptionPane.showMessageDialog(null, currentPlayer.getName() + " LOSE!! Time's up!", "Tournament Result", JOptionPane.INFORMATION_MESSAGE);
//                System.exit(0);
//            }
//        });
        this.playerTimer.start();
    }



    /**
     * Checks whether both workers of the current player are stuck,
     * if yes, then end the game.
     */
    private void checkLosingCondition() {
        // Check if both workers of the current player are stuck
        boolean bothWorkersStuck = true;
        for (Worker worker : currentPlayer.getWorkerList()) {
            bothWorkersStuck = bothWorkersStuck && worker.isStuck();
        }
        if (bothWorkersStuck) {
            playerTimer.pause();
            JOptionPane.showMessageDialog(null, currentPlayer.getName() + " LOSE!! All workers are stuck!", "Tournament Result", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    /**
     * Gets the singleton instance of GameController.
     *
     * @param gameBoard the game board
     * @param playerList the player
     * @param currentPlayerLabel the label for the current player
     *
     * @return the GameController instance
     */
    // use a static method to call the GameController object
    public static GameController getInstance(Board gameBoard, Map<Integer, Player> playerList, JLabel currentPlayerLabel, JLabel timerLabel) {
        if (instance == null) {
            instance = new GameController(gameBoard, playerList, currentPlayerLabel, timerLabel);
        }
        return instance;
    }


    // this method will be invoked everytime a tile is clicked
    /**
     * Handles the logic when a tile is clicked during a player's turn.
     *
     * @param clickedTile The tile that was clicked.
     */
    private void handleTileClick(Tile clickedTile) {
        //----------- 1st click: Select a worker -------------
        if (selectedTile == null) {
            // check if the clicked tile has a worker belonging to the current player
            if (clickedTile.getWorker() != null && clickedTile.getWorker().getPlayer() == currentPlayer) {
                Worker selectedWorker = clickedTile.getWorker();

                // Check if the selected worker is stuck (cannot move or build)
                if (isWorkerStuck(clickedTile)) {
                    JOptionPane.showMessageDialog(null, "This worker is stuck. Please move another worker.", "Message", JOptionPane.INFORMATION_MESSAGE);
                    selectedWorker.setBooleanStuck(true);
                    selectedTile = null;  // Clear selection so player can choose another worker
                    checkLosingCondition();
                    return;  // Exit method, forcing the player to select another worker
                }
                // set the selected tile and current worker for the pplayer
                selectedTile = clickedTile;
                lastMovedTile = clickedTile;
                currentPlayer.setCurrentWorker(selectedWorker);
            }

        } else {
            //----------- 2nd click: Move or Build -------------
            boolean canBuild;

            // check adjacency based on god power: Zeus can build on the same tile (under self)
            if (currentPlayer.getGodCard().getName().equalsIgnoreCase("zeus")){
                canBuild = isAdjacentOrSame(selectedTile, clickedTile); // Zeus can build under self
            }else {
                canBuild = isAdjacent(selectedTile, clickedTile); // others must build adjacent
            }

            // if the clicked tile is not adjacent (or same for Zeus), reject the action
            if (!canBuild) {
                JOptionPane.showMessageDialog(null, "Tile not adjacent!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // prevent building on a tile occupied by another worker (not yourself)
            Worker currentWorker = currentPlayer.getCurrentWorker();
            if (clickedTile.getWorker() != null && clickedTile.getWorker() != currentWorker) {
                JOptionPane.showMessageDialog(null, "Tile already occupied by another worker!", "Invalid Move", JOptionPane.ERROR_MESSAGE);
                return;
            }



            //----------- Proceed with the action (move/build) -------------
            // get the tower from the clicked tile to be passed into the action
            Tower tower = clickedTile.getTower();
            if (tower.isTowerEmpty()) {
                tower = new Tower();
            }

            isMoving = currentWorker.executeAction(currentPlayer,selectedTile,clickedTile,tower);

            if (isMoving) // isMoving is to check whether the worker is successfully moved (to store the lastMovedTile)
            {
                lastMovedTile = clickedTile;
                selectedTile = clickedTile;
            }
        }

        if (currentPlayer.isActionSuccessful()) // if the current player has successfully moved and build
        {
                currentPlayer.setActionSuccessful(false);   // reset for the next round
                selectedTile = null;                         // clear selection
                currentPlayer.clearCurrentWorker();         // clear selected worker
                resetTurn();                                // change next player
                playerTimer.pause();
                updateCurrentPlayerLabel();
                JOptionPane.showMessageDialog(null, "Now is " + currentPlayer.getName() + "'s turn", "Player turn", JOptionPane.PLAIN_MESSAGE);
                playerTimer.reset();
            }
    }


    /**
     * Randomly places all workers on the board at the start of the game.
     */
    private void randomiseWorkers()
    {
        // copy all workers in an arraylist
        List<Worker> allWorkers = new ArrayList<>();
        for (int playerNum=0; playerNum<playerList.size(); playerNum++){
            Player player = playerList.get(playerNum);
            allWorkers.addAll(player.getWorkerList());
            player.getWorkerList().clear();  // clear all the workers in the player's worker list after adding into the allWorkers list
        }

        int workerIndex = 0;
        Random random = new Random();

        while(workerIndex < allWorkers.size()){
            // randomly generate a number within the board rows and columns
            int row = random.nextInt(gameBoard.getBoardRows());
            int col = random.nextInt(gameBoard.getBoardColumns());
            Tile tile = gameBoard.getTileLocation(row, col);    // set the row and col to the tile location

            // if the tile is empty (no worker on the tile)
            if (tile.getWorker() == null){
                Worker worker = allWorkers.get(workerIndex);    // get the current worker in the worker list
                Player player = worker.getPlayer();             // get the player that owns the worker
                gameBoard.placePlayerOnTile(row, col, player);  // place the player on the tile
                tile.setWorker(worker);                         // mark the tile which owned by that player
                worker.setPosition(row, col);                   // set the position of each worker on the tile
                player.addWorker(worker);                       // add the updated worker list back to the player

                workerIndex++;
            }
        }
    }

    /**
     * Sets up listeners on all tiles of the board for player interaction.
     */
    private void setupTileListeners() {
        for (int row = 0; row < gameBoard.getBoardRows(); row++) {
            for (int col = 0; col < gameBoard.getBoardColumns(); col++) {
                Tile tile = gameBoard.getTileLocation(row, col);
                tile.addActionListener(e -> handleTileClick(tile));
            }
        }
    }

    /**
     * Updates the label that shows the current player's name and god card.
     */
    private void updateCurrentPlayerLabel() {
        if (currentPlayerLabel != null){
            currentPlayerLabel.setText("Player's Turn: " + currentPlayer.getName() +" (God Card: " + currentPlayer.getGodCard().getName() + ")");

        }
    }


    /**
     * Checks if a worker is stuck (cannot move to any adjacent tile).
     *
     * @param currentTile The tile of the worker.
     * @return True if the worker is stuck, false otherwise.
     */
    private boolean isWorkerStuck(Tile currentTile) {
        int currentRow = currentTile.getTileRow();
        int currentColumn = currentTile.getTileColumn();

        // Check all 8 surrounding tiles
        for (int row = currentRow - 1; row <= currentRow + 1; row++) {
            for (int column = currentColumn - 1; column <= currentColumn + 1; column++) {
                if (row == currentRow && column == currentColumn) {
                    continue;
                }
                if (row < 0 || row >= gameBoard.getBoardRows() || column < 0 || column >= gameBoard.getBoardColumns()) {
                    continue;
                }

                Tile neighborTile = gameBoard.getTileLocation(row, column);
                MoveAction testMove = new MoveAction(currentTile, neighborTile, currentTile.getWorker());

                if (testMove.isMoveSuccessful()) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Resets the turn and moves to the next player.
     */
    private void resetTurn()
    {
        boolean usedCard = currentPlayer.useFunctionCard("skip card", this);
        if(usedCard){
            currentPlayer.useFunctionCard("skip card", this);
        }else{
            if (currentPlayerIndex < playerList.size()) {
                for (Map.Entry<Integer, Player> entry : playerList.entrySet()) {
                    Player player = entry.getValue();
                    if (player.equals(currentPlayer)) {
                        int nextPlayerNum = entry.getKey()+1;
                        if (nextPlayerNum >= playerList.size())
                        {
                            nextPlayerNum = 0;
                            currentPlayerIndex = 0;
                            currentPlayer = playerList.get(currentPlayerIndex);
                            return;
                        }
                        currentPlayer = playerList.get(nextPlayerNum);
                        currentPlayerIndex = nextPlayerNum;
                        return;
                    }
                }
            }else {
                currentPlayerIndex = 0;
                currentPlayer = playerList.get(currentPlayerIndex);
            }
        }
    }


    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int index) {
        this.currentPlayerIndex = index;
    }

    public Map<Integer, Player> getPlayerList() {
        return playerList;
    }

    /**
     * Determines whether two tiles are adjacent.
     *
     * @param tile1 the first tile.
     * @param tile2 the second tile.
     * @return true if adjacent, otherwise false
     */
    public boolean isAdjacent(Tile tile1, Tile tile2) {
        int dx = Math.abs(tile1.getTileRow() - tile2.getTileRow());
        int dy = Math.abs(tile1.getTileColumn() - tile2.getTileColumn());
        return (dx <= 1 && dy <= 1) && !(dx == 0 && dy == 0);
    }

    public boolean isAdjacentOrSame(Tile tile1, Tile tile2) {
        int dx = Math.abs(tile1.getTileRow() - tile2.getTileRow());
        int dy = Math.abs(tile1.getTileColumn() - tile2.getTileColumn());
        return (dx <= 1 && dy <= 1);
    }


}