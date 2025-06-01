package sprint3implementation.characters;

import sprint3implementation.actions.BuildAction;
import sprint3implementation.actions.MoveAction;
import sprint3implementation.grounds.Tile;
import sprint3implementation.towers.Tower;

/**
 * Represents a worker in the game.
 * Each worker is associated with a player,
 * can move and build,
 * and may be affected by special god card abilities.
 *
 * @author Xin Thung
 * Modified by: Yee Peen
 */
public class Worker {

    /**
     * The player who owns this worker.
     */
    private Player player;

    /**
     * The row position of the worker on the board.
     */
    private int row;

    /**
     * The column position of the worker on the board.
     */
    private int col;

    /**
     * Indicates whether the worker is stuck and cannot perform actions.
     */
    private boolean booleanStuck;

    /**
     * Indicates whether the worker is currently moving.
     */
    private Boolean isMoving = false;

    /**
     * The tile the worker is currently on.
     */
    private Tile currentTile;


    /**
     * Construct a new worker associated with the specified player.
     *
     * @param player the player who owns the worker
     */
    public Worker(Player player) {
        this.player = player;
        setBooleanStuck(false);
    }

    /**
     * Returns the player who owns this worker.
     *
     * @return the owning player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the tile the worker is currently on.
     *
     * @return the current tile
     */
    public Tile getCurrentTile() {
        return currentTile;
    }

    /**
     * Sets the tile the worker is currently on.
     *
     * @param tile the new current tile
     */
    public void setCurrentTile(Tile tile) {
        this.currentTile = tile;
    }


    /**
     * Sets the worker's position on the board.
     *
     * @param row the new row position
     * @param col the new column position
     */
    public void setPosition(int row, int col){
        this.row = row;
        this.col = col;
    }

    /**
     * Checks whether the worker is stuck and unable to perform actions.
     *
     * @return true if the worker is stuck, otherwise false
     */
    public boolean isStuck()
    {
        return booleanStuck;
    }

    /**
     * Sets whether the worker is stuck.
     *
     * @param booleanStuck true if the worker should be marked as stuck
     */
    public void setBooleanStuck(boolean booleanStuck) {
        this.booleanStuck = booleanStuck;
    }


    /**
     * Executes the worker's action.
     * If the player has no GodCard, performs a move followed by a build.
     * If the player has a GodCard, invokes its special ability.
     *
     * @param currentPlayer the player performing the action
     * @param selectedTile the tile the worker is on
     * @param clickedTile the tile the worker is attempting to move to or act upon
     * @param tower the tower to build on
     *
     * @return true if the move or special ability was successfully executed, otherwise false
     */
    public boolean executeAction(Player currentPlayer, Tile selectedTile, Tile clickedTile, Tower tower)
    {
        // execution of regular move and build actions
        if (currentPlayer.getGodCard() == null)
        {
            MoveAction moveAction = new MoveAction(selectedTile, clickedTile, this);
            moveAction.execute();

            if (moveAction.isMoveSuccessful())
            {
                isMoving = true;
                BuildAction buildAction = new BuildAction(clickedTile, tower, false);
                buildAction.execute();

                if (buildAction.isBuildSuccessful())
                {
                    currentPlayer.setActionSuccessful(true);
                }
            }
        }
        else {
            // handle god card abilities (Zeus)
            boolean allowBuildUnderSelf = clickedTile.getWorker() != null && clickedTile.getWorker().equals(this);
            BuildAction buildAction = new BuildAction(clickedTile, tower, allowBuildUnderSelf);
            // special god cards execution of their special abilities
            isMoving = currentPlayer.getGodCard().executeSpecialAbility(currentPlayer, selectedTile, clickedTile, tower);
        }
        return isMoving;
    }
}

