package sprint2implementation.actions;

import sprint2implementation.grounds.Board;
import sprint2implementation.grounds.Tile;
import sprint2implementation.towers.Tower;
import sprint2implementation.characters.Worker;

import javax.swing.*;

public class MoveAction extends Action {
    private Tile fromTile;
    private Tile toTile;
    private Worker worker;
    private boolean moveSuccessful;

    public MoveAction(Board gameBoard, Tile fromTile, Tile toTile, Worker worker) {
        super(gameBoard, fromTile.getTileRow(), fromTile.getTileColumn());
        this.fromTile = fromTile;
        this.toTile = toTile;
        this.worker = worker;
        this.moveSuccessful = validateMove();
    }

    private boolean validateMove() {

        if (fromTile == toTile) {
            return false;
        }

        // Check if target tile has another worker
        if (toTile.getWorker() != null) {
            return false;
        }

        // Get tower levels, defaulting to 0 if no tower exists
        int fromLevel = getTowerLevel(fromTile.getTower());
        int toLevel = getTowerLevel(toTile.getTower());

        // Prevent moving onto a tower that already has a dome or climbing up more than 1 level
        Tower toTower = toTile.getTower();
        if ((toTower != null && toTower.hasDome()) || (toLevel - fromLevel > 1))
        {
            return false;
        }

        return true;
    }


    @Override
    public void execute() {
        if (moveSuccessful) {

            // Get the towers on the starting tiles and the destination tiles
            Tower fromTower = fromTile.getTower();
            Tower toTower = toTile.getTower();

            // Clear the worker from the starting tile
            fromTile.setWorker(null);

            // Update the icon on the starting tile
            if (fromTower == null)
            {
                // If there is no tower, show an empty tile
                fromTile.updateIcon(null);
            }
            else
            {
                // If there is a tower, show the current tower's image (without the worker)
                fromTile.updateIcon(fromTower.getCurrentIcon());
            }

            // Move the worker to the destination tile
            toTile.setWorker(worker);
            worker.setPosition(toTile.getTileRow(), toTile.getTileColumn());
            System.out.println("Current worker position: " + worker.getPosition());

            // Update the icon on the destination tile
            // Check if the tower on the destination tile does not have a dome
            if (toTower == null || !toTower.hasDome()) {
                int toLevel = getTowerLevel(toTower);

                // Get the worker's icon that matches the level they're standing on
                ImageIcon workerOnTower = worker.getPlayer().getPlayerPositionTower().getOrDefault(toLevel, worker.getPlayer().getPlayerIcon());

                // Show the worker on that level
                toTile.updateIcon(workerOnTower);
            }

            // Check winning condition: the worker has moved onto the topmost level without a dome
            if (toTower != null && toTower.getLevelCount() == Tower.getMaxLevels() && !toTower.hasDome()) {
                JOptionPane.showMessageDialog(null, worker.getPlayer().getName() + " WINS!!", "Tournament Result", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }


        }
        else {
            if (isWorkerStuck(fromTile.getWorker(), fromTile)) {
                JOptionPane.showMessageDialog(null, " This worker is stuck. Please move another worker.", "Message", JOptionPane.INFORMATION_MESSAGE);

                fromTile.getWorker().setBooleanStuck(true);
            }

        }
        boolean bothWorkersStuck = true;
        for (Worker worker : worker.getPlayer().getWorkerList())
        {
            bothWorkersStuck = bothWorkersStuck && worker.isStuck();
        }
        if (bothWorkersStuck)
        {
            JOptionPane.showMessageDialog(null, worker.getPlayer().getName() + " LOSE!! All workers are stuck!", "Tournament Result", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    public boolean isMoveSuccessful() {
        return moveSuccessful;
    }

    private int getTowerLevel(Tower tower) {
        return (tower != null) ? tower.getLevelCount() : 0;
    }

    public boolean isWorkerStuck(Worker worker, Tile currentTile) {
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
}
