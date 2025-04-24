package sprint2implementation;

import javax.swing.*;
import java.util.*;

public class GameController {
    private Board gameBoard;
    private List<Player> playerList; // change Player instance variable to arraylist
    private Player currentPlayer;
    private int currentPlayerIndex;
    private Tile selectedTile;

    private Tile lastMovedTile; // the tile where the worker just moved to during the Movement phase

    // SMALL NOTE for lastMovedTile:
    // - 1. players select the worker, 2. move it to an adjacent tile, 3. and the worker builds on an adjacent tile
    // to its current location
    // purpose of lastMovedTile: to perform step 3 correctly, the game needs to know where the worker currently
    // is after step 2
    // e.g: select worker on tile (2,2), moved it to (3,3), worker wants to build on (3,4) but the game
    // has to check whether (3,4) is adjacent to the tile where the worker moved to
    // So, use isAdjacent(lastMovedToTile, clickedTile), where lastMovedToTile = (3,3) and
    // clickedTile = (3,4) - tile clicked during build phase

    private boolean isBuildingPhase;
    private JLabel currentPlayerLabel; //indicator for the current player's turn

    private Tile originalMoveTile = null;
    private boolean waitingForSecondMove = false;
    private boolean waitingForSecondBuild = false;

    public GameController(Board gameBoard, Player player1, Player player2, JLabel currentPlayerLabel) {
        this.gameBoard = gameBoard;
        // store players as an arraylist
        this.playerList = new ArrayList<>();
        // add the player1 and player2 into the array list
        playerList.add(player1);
        playerList.add(player2);
        this.currentPlayerIndex = 0;
        this.currentPlayer = playerList.get(currentPlayerIndex);

        this.currentPlayerLabel = currentPlayerLabel;
        updateCurrentPlayerLabel();

        initializeWorkers();
        setupTileListeners();
    }

    private void updateCurrentPlayerLabel() {
        if (currentPlayerLabel != null){
            currentPlayerLabel.setText("Player's Turn: " + currentPlayer.getName() +" (God Card: " + currentPlayer.getGodCard().getName() + ")");

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

    public void handleTileClick(Tile clickedTile) {
        if (!isBuildingPhase) {
            handleMovementPhase(clickedTile);
        } else {
            handleBuildingPhase(clickedTile);
        }
    }

    private void initializeWorkers()
    {
        // put all workers in an arraylist
        List<Worker> allWorkers = new ArrayList<>();
        for (Player player: playerList){
            allWorkers.addAll(player.getWorkerList());
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
                player.setWorkers(allWorkers);                  // set the updated worker list back to the player

                workerIndex++;
            }
        }
    }


    private void handleMovementPhase(Tile clickedTile) {

        if (waitingForSecondMove){
            selectedTile = lastMovedTile;
        }

        if (selectedTile == null) {
            // First click - select a piece
            if (clickedTile.getWorker() != null && clickedTile.getWorker().getPlayer() == currentPlayer) {
                selectedTile = clickedTile;
                System.out.println("Selected tile at: " + selectedTile.getTileRow() + "," + selectedTile.getTileColumn());

                if (isPlayerStuck(currentPlayer, selectedTile)) {
                    JOptionPane.showMessageDialog(null, currentPlayer.getName() + " LOSE!! The player is stuck!", "Tournament Result", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }

            }

        } else {

            // Second click - move the piece
            if (!isAdjacent(selectedTile, clickedTile)) {
                JOptionPane.showMessageDialog(null, "Tile not adjacent!", "Error", JOptionPane.ERROR_MESSAGE);

                if (!waitingForSecondMove){
                    selectedTile = null;
                    originalMoveTile = null;
                }

                return;
            }

            MoveAction moveAction = new MoveAction(gameBoard, selectedTile, clickedTile, selectedTile.getWorker());
            moveAction.execute();

            if (moveAction.isMoveSuccessful()) {
                currentPlayer.decrementMove();
                lastMovedTile = clickedTile;

                if (waitingForSecondMove){
                    waitingForSecondMove = false;
                    isBuildingPhase = true;
                    JOptionPane.showMessageDialog(null, "Second move successful, now in building phase!", "Moving Stage", JOptionPane.PLAIN_MESSAGE);
                } else if (currentPlayer.canMoveAgain()){
                    waitingForSecondMove = true;
                    JOptionPane.showMessageDialog(null, currentPlayer.getName() + " " + currentPlayer.getGodCard().getDescription());
                } else {
                    isBuildingPhase = true;
                    // Keep the selectedTile reference for building phase
                    System.out.println("Move successful, now in building phase");
                    JOptionPane.showMessageDialog(null, "Move successfully, now in building phase!", "Moving Stage", JOptionPane.PLAIN_MESSAGE);
                }

            } else {
                System.out.println("Move failed");
                JOptionPane.showMessageDialog(null, "Move failed!", "Error", JOptionPane.ERROR_MESSAGE);

                if (!waitingForSecondMove){
                    selectedTile = null;
                    originalMoveTile = null;
                }
            }
        }
    }

    private void handleBuildingPhase(Tile clickedTile) {
        if (lastMovedTile == null) {
            System.out.println("Error: No selected tile in building phase");
            return;
        }

        // Check adjacency before building
        if (!isAdjacent(lastMovedTile, clickedTile)) {
            System.out.println("Tile not adjacent");
            JOptionPane.showMessageDialog(null, "Tile not adjacent!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get or create the tower on the target tile
        Tower tower = clickedTile.getTower();
        if (tower == null) {
            tower = new Tower();
        }

        // Attempt to build
        BuildAction buildAction = new BuildAction(gameBoard, clickedTile, tower);
        buildAction.execute();

        if (buildAction.isBuildSuccessful()) {
            currentPlayer.decrementBuild();
            System.out.println("Build successful");

            if (currentPlayer.canBuildAgain()) {
                waitingForSecondBuild = true;
                JOptionPane.showMessageDialog(null, currentPlayer.getName() + " " + currentPlayer.getGodCard().getDescription());
                // Don't switch turns yet
            } else {
                currentPlayer.resetTurn();
                currentPlayer = (currentPlayer == playerList.get(0)) ? playerList.get(1) : playerList.get(0);
                isBuildingPhase = false;
                selectedTile = null;
                lastMovedTile = null;
                originalMoveTile = null;
                waitingForSecondMove = false;
                waitingForSecondBuild = false;

                updateCurrentPlayerLabel(); //update current player's turn
                JOptionPane.showMessageDialog(null, "Now is " + currentPlayer.getName() + "'s turn", "Player turn", JOptionPane.PLAIN_MESSAGE);
            }
        }
        else
        {
            System.out.println("Build failed");
            JOptionPane.showMessageDialog(null, "Build failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isPlayerStuck(Player player, Tile currentTile) {
        int currentRow = currentTile.getTileRow();
        int currentColumn = currentTile.getTileColumn();

        // Check all 8 surrounding tiles
        for (int row = currentRow - 1; row <= currentRow + 1; row++) {
            for (int column = currentColumn - 1; column <= currentColumn + 1; column++) {
                // Skip out-of-bounds or the same tile
                if (row == currentRow && column == currentColumn) continue;
                if (row < 0 || row >= gameBoard.getBoardRows() || column < 0 || column >= gameBoard.getBoardColumns()) continue;

                Tile neighborTile = gameBoard.getTileLocation(row, column);
                MoveAction testMove = new MoveAction(gameBoard, currentTile, neighborTile, currentTile.getWorker());

                // If at least one move is valid, player is not stuck
                if (testMove.isMoveSuccessful()) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isAdjacent(Tile tile1, Tile tile2) {
        int dx = Math.abs(tile1.getTileRow() - tile2.getTileRow());
        int dy = Math.abs(tile1.getTileColumn() - tile2.getTileColumn());
        return (dx <= 1 && dy <= 1) && !(dx == 0 && dy == 0);
    }
}