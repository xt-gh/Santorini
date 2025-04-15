package sprint2implementation;

import javax.swing.*;

public class GameController {
    private Board gameBoard;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Tile selectedTile;
    private boolean isBuildingPhase;

    public GameController(Board gameBoard, Player player1, Player player2) {
        this.gameBoard = gameBoard;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        initializePlayers();
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

    private void initializePlayers() {
        gameBoard.placePlayerOnTile(0, 0, player1);
        gameBoard.getTileLocation(0, 0).setPlayer(player1);
        gameBoard.placePlayerOnTile(gameBoard.getBoardRows()-1, gameBoard.getBoardColumns()-1, player2);
        gameBoard.getTileLocation(gameBoard.getBoardRows()-1, gameBoard.getBoardColumns()-1).setPlayer(player2);
    }

    private void handleMovementPhase(Tile clickedTile) {
        if (selectedTile == null) {
            // First click - select a piece
            if (clickedTile.getPlayer() == currentPlayer) {
                selectedTile = clickedTile;
                System.out.println("Selected tile at: " + selectedTile.getTileRow() + "," + selectedTile.getTileColumn());

                if (isPlayerStuck(currentPlayer, selectedTile)) {
                    JOptionPane.showMessageDialog(null, currentPlayer.getName() + " LOSE!! The player is stuck!", "Tournament Result", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }

            }
        } else {
            // Second click - move the piece
            MoveAction moveAction = new MoveAction(gameBoard, selectedTile, clickedTile, currentPlayer);
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
            }

            BuildAction buildAction = new BuildAction(gameBoard, clickedTile, tower);
            buildAction.execute();

            if (buildAction.isBuildSuccessful()) {
                System.out.println("Build successful");

                if (currentPlayer == player1)
                {
                    currentPlayer = player2;
                    JOptionPane.showMessageDialog(null, "Now is " + currentPlayer.getName() + "'s turn", "Player turn", JOptionPane.PLAIN_MESSAGE);
                }
                else {
                    currentPlayer = player1;
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
                MoveAction testMove = new MoveAction(gameBoard, currentTile, neighborTile, player);

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
        return (dx <= 2 && dy <= 2);
    }
}