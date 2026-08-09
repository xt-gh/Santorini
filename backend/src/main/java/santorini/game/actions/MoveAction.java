package santorini.game.actions;

import santorini.game.GameResult;
import santorini.game.grounds.Tile;
import santorini.game.towers.Tower;
import santorini.game.characters.Worker;

/**
 * An action that moves a worker from one tile to another.
 * The move must follow game rules, such as no dome, height limit and no worker blocking.
 *
 * Refactored: Removed JOptionPane dialogs and ImageIcon updates.
 * Win condition now records result via GameResult instead of System.exit(0).
 *
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
     * The worker being moved.
     */
    private Worker worker;

    /**
     * Indicates whether the move passed validation and is allowed to execute.
     */
    private boolean moveSuccessful;

    /**
     * Constructor for MoveAction.
     *
     * @param fromTile the tile the worker is moving from
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
     * Validates the move follows game rules.
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
     * Updates worker position and checks for a win condition.
     */
    @Override
    public void execute() {
        if (moveSuccessful) {
            fromTile.setWorker(null);
            toTile.setWorker(worker);
            worker.setPosition(toTile.getTileRow(), toTile.getTileColumn());
            checkWinningCondition(worker, toTile);
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
     * Records a win if the worker moves onto a complete level-3 tower without a dome.
     * Replaces the original System.exit(0) with GameResult.recordWin().
     *
     * @param worker the worker that moved
     * @param toTile the tile the worker moved to
     */
    private void checkWinningCondition(Worker worker, Tile toTile) {
        Tower toTower = toTile.getTower();
        if (toTower != null && toTower.getLevelCount() == Tower.getMaxLevels() && !toTower.hasDome()) {
            GameResult.recordWin(worker.getPlayer().getName());
        }
    }
}
