package sprint2implementation;

import javax.swing.*;
import java.util.*;

public class GameController {
    private Board gameBoard;
    // change Player instance variable to arraylist
    private List<Player>players;
    private Player currentPlayer;
    private int currentPlayerIndex;
    private Tile selectedTile;
    private boolean isBuildingPhase;

    public GameController(Board gameBoard, Player player1, Player player2) {
        this.gameBoard = gameBoard;
        // store players as an arraylist
        this.players = new ArrayList<>();
        // add the player1 and player2 into the array list
        players.add(player1);
        players.add(player2);
        this.currentPlayerIndex = 0;
        this.currentPlayer = players.get(currentPlayerIndex);

        initializeWorkers();
        setupTileListeners();
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

    private void initializeWorkers() {
        // put all workers in an arraylist
        List<Worker> allWorkers = new ArrayList<>();
        for (Player player: players){
            allWorkers.addAll(player.getWorkers());
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
                Player player = worker.getPlayer(); // get the player that owns the worker
                gameBoard.placePlayerOnTile(row, col, player);  // place the player on the tile
                tile.setWorker(worker); // mark the tile which owned by that player
                player.setWorkers(allWorkers);  // set the updated worker list back to the player

                workerIndex++;
            }
        }
    }


    private void handleMovementPhase(Tile clickedTile) {
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
            MoveAction moveAction = new MoveAction(gameBoard, selectedTile, clickedTile, selectedTile.getWorker());
            moveAction.execute();

            if (moveAction.isMoveSuccessful()) {
                isBuildingPhase = true;
                // Keep the selectedTile reference for building phase
                System.out.println("Move successful, now in building phase");
                JOptionPane.showMessageDialog(null, "Move successfully, now in building phase!", "Moving Stage", JOptionPane.PLAIN_MESSAGE);
            } else {
                selectedTile = null;
                System.out.println("Move failed");
                JOptionPane.showMessageDialog(null, "Move failed!", "Error", JOptionPane.ERROR_MESSAGE);

            }
        }
    }

    private void handleBuildingPhase(Tile clickedTile) {
        if (selectedTile == null) {
            System.out.println("Error: No selected tile in building phase");
            return;
        }

        if (isAdjacent(selectedTile, clickedTile)) {
            Tower tower = clickedTile.getTower();
            if (tower == null) {
                tower = new Tower();
                System.out.println("Selected tile at: " + selectedTile.getTileRow() + "," + selectedTile.getTileColumn());
            }

            BuildAction buildAction = new BuildAction(gameBoard, clickedTile, tower);
            buildAction.execute();

            if (buildAction.isBuildSuccessful()) {
                System.out.println("Build successful");

//                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
//                currentPlayer = players.get(currentPlayerIndex);

                if (currentPlayer == players.get(0))
                {
                    currentPlayer = players.get(1);
                    JOptionPane.showMessageDialog(null, "Now is " + currentPlayer.getName() + "'s turn", "Player turn", JOptionPane.PLAIN_MESSAGE);
                }
                else {
                    currentPlayer = players.get(0);
                    JOptionPane.showMessageDialog(null, "Now is " + currentPlayer.getName() + "'s turn", "Player turn", JOptionPane.PLAIN_MESSAGE);
                }
                isBuildingPhase = false;
                selectedTile = null; // Only reset after successful build
            } else {
                System.out.println("Build failed");
                JOptionPane.showMessageDialog(null, "Build failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Tile not adjacent");
            JOptionPane.showMessageDialog(null, "Tile not adjacent!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

//    public boolean isPlayerStuck(Player player, Tile currentTile) {
//        int currentRow = currentTile.getTileRow();
//        int currentColumn = currentTile.getTileColumn();
//
//        // Check all 8 surrounding tiles
//        for (int row = currentRow - 1; row <= currentRow + 1; row++) {
//            for (int column = currentColumn - 1; column <= currentColumn + 1; column++) {
//                // Skip out-of-bounds or the same tile
//                if (row == currentRow && column == currentColumn) continue;
//                if (row < 0 || row >= gameBoard.getBoardRows() || column < 0 || column >= gameBoard.getBoardColumns()) continue;
//
//                Tile neighborTile = gameBoard.getTileLocation(row, column);
//                MoveAction testMove = new MoveAction(gameBoard, currentTile, neighborTile, player);
//
//                // If at least one move is valid, player is not stuck
//                if (testMove.isMoveSuccessful()) {
//                    return false;
//                }
//            }
//        }
//
//        return true;
//    }

    public boolean isPlayerStuck(Player player, Tile currentTile) {
        int currentRow = currentTile.getTileRow();
        int currentColumn = currentTile.getTileColumn();

        Worker worker = currentTile.getWorker();

        // Only check if the worker exists and belongs to the given player
        if (worker == null || worker.getPlayer() != player) {
            return true;
        }

        // Check all 8 surrounding tiles
        for (int row = currentRow - 1; row <= currentRow + 1; row++) {
            for (int column = currentColumn - 1; column <= currentColumn + 1; column++) {
                // Skip out-of-bounds or the same tile
                if (row == currentRow && column == currentColumn) continue;
                if (row < 0 || row >= gameBoard.getBoardRows() || column < 0 || column >= gameBoard.getBoardColumns()) continue;

                Tile neighborTile = gameBoard.getTileLocation(row, column);
                MoveAction testMove = new MoveAction(gameBoard, currentTile, neighborTile, worker);

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
//        return (dx <= 2 && dy <= 2);
        return dx <= 1 && dy <= 1 && (dx + dy > 0);
    }
}