package sprint3implementation.actions;

import sprint3implementation.grounds.Tile;
import sprint3implementation.towers.Tower;
import sprint3implementation.characters.Worker;

import javax.swing.*;

/**
 * An action that moves a worker form one tile to another.
 * The move must follow game rules, such ad no dome, height limit and no worker blocking.
 * @author Yee Peen
 * Modified by: Tiffany
 */
public class MoveAction extends Action {
    /**
     * The tile from which the worker is moving.
     */
    private Tile fromTile;

    /**
     * The tile to which the worker is moving.
     */
    private Tile toTile;

    /**
     * The worker being the moved.
     */
    private Worker worker;

    /**
     * Indicates whether the move passed validation and is allowed to execute.
     */
    private boolean moveSuccessful;

    /**
     * Constructor for MoveAction.
     *
     * @param fromTile the tile the worker is moving form
     * @param toTile the tile the worker is moving to
     * @param worker the worker being moved
     */
    public MoveAction(Tile fromTile, Tile toTile, Worker worker) {
        super(fromTile.getTileRow(), fromTile.getTileColumn());
        this.fromTile = fromTile;
        this.toTile = toTile;
        this.worker = worker;
        this.moveSuccessful = validateMove();
    }

    /**
     * Validates the move follows game rules
     * The worker cannot move to the same tile.
     * The destination must be unoccupied.
     * The worker cannot move onto a dome.
     * The worker cannot climb more than 1 level.
     *
     * @return true if the move is allowed
     */
    private boolean validateMove() {

        // cannot move on the same tile
        if (fromTile == toTile) {
            return false;
        }

        // cannot move to the tile that is occupied by other worker
        if (toTile.getWorker() != null) {
            return false;
        }

        int fromLevel = getTowerLevel(fromTile.getTower());
        int toLevel = getTowerLevel(toTile.getTower());

        // cannot move to a tile with dome or more than one level from the previous tower
        Tower toTower = toTile.getTower();
        if ((toTower != null && toTower.hasDome()) || (toLevel - fromLevel > 1))
        {
            return false;
        }

        return true;
    }

    /**
     * Executes the move if it is valid.
     * Updated worker position and tile icons and checks for a win condition.
     * Shows a message dialog with the result.
     */
    @Override
    public void execute() {
        if (moveSuccessful) {

            Tower fromTower = fromTile.getTower();
            Tower toTower = toTile.getTower();

            fromTile.setWorker(null);

            if (fromTower == null)
            {
                fromTile.updateIcon(null);
            }
            else
            {
                fromTile.updateIcon(fromTower.getCurrentIcon());
            }

            toTile.setWorker(worker);
            worker.setPosition(toTile.getTileRow(), toTile.getTileColumn());

            if (toTower == null || !toTower.hasDome()) {
                int toLevel = getTowerLevel(toTower);

                ImageIcon workerOnTower = worker.getPlayer().getPlayerPositionTower().getOrDefault(toLevel, worker.getPlayer().getPlayerIcon());

                toTile.updateIcon(workerOnTower);
                checkWinningCondition(worker,toTile);
                JOptionPane.showMessageDialog(null, "Move successfully", "Moving Stage", JOptionPane.PLAIN_MESSAGE);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Move failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Return whether the move passed validation.
     *
     * @return true if valid
     */
    public boolean isMoveSuccessful() {
        return moveSuccessful;
    }

    /**
     * Returns the tower level or 0 if the tower is null.
     *
     * @param tower the tower to check
     * @return the number of levels
     */
    private int getTowerLevel(Tower tower) {
        return (tower != null) ? tower.getLevelCount() : 0;
    }

    /**
     * Ends the game if the worker moves onto a complete level-3 tower without a dome.
     *
     * @param worker the worker that moved
     * @param toTile the tile the worker moved to
     */
    private void checkWinningCondition(Worker worker, Tile toTile) {
        // Check if the worker is on a tower and the tower has reached level 3
        Tower toTower = toTile.getTower();
        if (toTower != null && toTower.getLevelCount() == Tower.getMaxLevels() && !toTower.hasDome()) {
            JOptionPane.showMessageDialog(null, worker.getPlayer().getName() + " WINS!!", "Tournament Result", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
}

