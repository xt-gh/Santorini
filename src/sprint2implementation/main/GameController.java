package sprint2implementation.main;

import sprint2implementation.actions.MoveAction;
import sprint2implementation.characters.Player;
import sprint2implementation.characters.Worker;
import sprint2implementation.grounds.Board;
import sprint2implementation.grounds.Tile;
import sprint2implementation.towers.Tower;

import javax.swing.*;
import java.util.*;

// Design pattern: Singleton.
// Singleton is used as the design pattern for the GameController class because only one game controller instance is needed.
public class GameController {
    private static GameController instance;
    private Board gameBoard;
    private Map<Integer, Player> playerList; // Use of a map here is to store the players with their respective player number
    private Player currentPlayer;
    private Tile selectedTile;
    private Tile lastMovedTile; // the tile where the worker just moved to during the Movement phase
    private int currentPlayerIndex;
    private Boolean isMoving = false;
    private JLabel currentPlayerLabel; //indicator for the current player's turn

    private GameController(Board gameBoard, Map<Integer, Player> playerList, JLabel currentPlayerLabel) {
        this.gameBoard = gameBoard;
        this.playerList = playerList; // store players as a map so that the player can be accessed with a specific player number
        this.currentPlayerIndex = 0;
        this.currentPlayer = playerList.get(currentPlayerIndex);
        this.currentPlayerLabel = currentPlayerLabel;
        updateCurrentPlayerLabel();
        randomiseWorkers();
        setupTileListeners();
    }

    private void checkLosingCondition() {
        // Check if both workers of the current player are stuck
        boolean bothWorkersStuck = true;
        for (Worker worker : currentPlayer.getWorkerList()) {
            bothWorkersStuck = bothWorkersStuck && worker.isStuck();
        }
        if (bothWorkersStuck) {
            JOptionPane.showMessageDialog(null, currentPlayer.getName() + " LOSE!! All workers are stuck!", "Tournament Result", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    // use a static method to call the GameController object
    public static GameController getInstance(Board gameBoard, Map<Integer, Player> playerList, JLabel currentPlayerLabel) {
        if (instance == null) {
            instance = new GameController(gameBoard, playerList, currentPlayerLabel);
        }
        return instance;
    }

    // this method will be invoked everytime a tile is clicked
    private void handleTileClick(Tile clickedTile) {
        if (selectedTile == null) {
            // First click - select a piece
            if (clickedTile.getWorker() != null && clickedTile.getWorker().getPlayer() == currentPlayer) {
                Worker selectedWorker = clickedTile.getWorker();

                // Check if the selected worker is stuck
                if (isWorkerStuck(clickedTile)) {
                    JOptionPane.showMessageDialog(null, "This worker is stuck. Please move another worker.", "Message", JOptionPane.INFORMATION_MESSAGE);
                    selectedWorker.setBooleanStuck(true);
                    selectedTile = null;  // Clear selection so player can choose another worker
                    checkLosingCondition();
                    return;  // Exit method, forcing the player to select another worker
                }
                selectedTile = clickedTile;
                lastMovedTile = clickedTile;
                currentPlayer.setCurrentWorker(selectedWorker);
            }

        } else {

            Worker worker = currentPlayer.getCurrentWorker();

            if (!isAdjacent(selectedTile, clickedTile)) {
                JOptionPane.showMessageDialog(null, "Tile not adjacent!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (clickedTile.getWorker() != null) {
                JOptionPane.showMessageDialog(null, "Tile already occupied by another worker!", "Invalid Move", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // get the tower from the clicked tile to be passed into the action
            Tower tower = clickedTile.getTower();
            if (tower.isTowerEmpty()) {
                tower = new Tower();
            }

            isMoving = worker.executeAction(currentPlayer,selectedTile,clickedTile,tower);

            if (isMoving) // isMoving is to check whether the worker is successfully moved (to store the lastMovedTile)
            {
                lastMovedTile = clickedTile;
                selectedTile = clickedTile;
            }
        }

        if (currentPlayer.isActionSuccessful()) // if the current player has successfully moved and build
        {
            currentPlayer.setActionSuccessful(false);
            selectedTile = null;
            currentPlayer.clearCurrentWorker();
            resetTurn();
            updateCurrentPlayerLabel();
            JOptionPane.showMessageDialog(null, "Now is " + currentPlayer.getName() + "'s turn", "Player turn", JOptionPane.PLAIN_MESSAGE);
        }
    }

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

    private void setupTileListeners() {
        for (int row = 0; row < gameBoard.getBoardRows(); row++) {
            for (int col = 0; col < gameBoard.getBoardColumns(); col++) {
                Tile tile = gameBoard.getTileLocation(row, col);
                tile.addActionListener(e -> handleTileClick(tile));
            }
        }
    }

    private void updateCurrentPlayerLabel() {
        if (currentPlayerLabel != null){
            currentPlayerLabel.setText("Player's Turn: " + currentPlayer.getName() +" (God Card: " + currentPlayer.getGodCard().getName() + ")");

        }
    }

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


    private void resetTurn()
    {
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

    public boolean isAdjacent(Tile tile1, Tile tile2) {
        int dx = Math.abs(tile1.getTileRow() - tile2.getTileRow());
        int dy = Math.abs(tile1.getTileColumn() - tile2.getTileColumn());
        return (dx <= 1 && dy <= 1) && !(dx == 0 && dy == 0);
    }
}