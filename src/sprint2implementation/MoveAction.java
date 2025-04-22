package sprint2implementation;

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
        if (toTile.getWorker() != null && toTile.getWorker().getPlayer() != worker.getPlayer()) {
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
                JOptionPane.showMessageDialog(null, "WIN!!", "Tournament Result", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }
    }

    public boolean isMoveSuccessful() {
        return moveSuccessful;
    }

    private int getTowerLevel(Tower tower) {
        return (tower != null) ? tower.getLevelCount() : 0;
    }
}
