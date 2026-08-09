package santorini.game.cards;

import santorini.game.actions.BuildAction;
import santorini.game.actions.MoveAction;
import santorini.game.characters.Player;
import santorini.game.characters.Worker;
import santorini.game.grounds.Tile;
import santorini.game.towers.Tower;

/**
 * Zeus class represents the Zeus god card with a special ability
 * that allows building under itself during the building phase.
 *
 * Refactored: Replaced JOptionPane dialogs with pending-choice mechanism.
 * When the player tries to build under their own worker, sets pendingChoice=true
 * so the frontend can confirm. resolveChoice() handles the player's response.
 *
 * @author Xin Thung
 */
public class Zeus extends GodCard {

    /**
     * Indicates whether the player is currently in the building phase.
     */
    private boolean isBuildingPhase = false;

    /**
     * Indicates whether the player is currently in the moving phase.
     */
    private boolean isMoving = false;

    /**
     * Indicates if the player has built under its own worker.
     */
    private boolean builtUnderSelf = false;

    /**
     * The worker currently active for Zeus's actions.
     */
    private Worker activeWorker;

    /**
     * The tile where the player attempted to build under self (saved for after choice).
     */
    private Tile pendingBuildTile = null;

    /**
     * The tower for the pending build (saved for after choice).
     */
    private Tower pendingBuildTower = null;

    /**
     * Constructs a Zeus god card with name and description.
     */
    public Zeus() {
        super("Zeus", "Zeus can build under itself.");
    }

    /**
     * Executes Zeus's special ability, allowing the player to build under its current worker.
     *
     * @param currentPlayer The player currently taking the turn.
     * @param selectedTile The tile where the worker is currently located.
     * @param clickedTile The tile selected for movement or building.
     * @param tower The tower to be built on the tile during the building phase.
     *
     * @return true if still in moving phase (waiting for building), false if action finished.
     */
    @Override
    public boolean executeSpecialAbility(Player currentPlayer, Tile selectedTile, Tile clickedTile, Tower tower) {
        if (!isBuildingPhase) {
            // Movement Phase
            MoveAction moveAction = new MoveAction(selectedTile, clickedTile, selectedTile.getWorker());
            moveAction.execute();

            if (moveAction.isMoveSuccessful()) {
                isMoving = true;
                isBuildingPhase = true;
                builtUnderSelf = false; // reset the flag
                activeWorker = clickedTile.getWorker();
            }

        } else {
            // Building Phase
            Tile activeWorkerTile = activeWorker.getCurrentTile();

            // prevent building on another worker's tile
            if (clickedTile.hasWorker() && clickedTile != activeWorkerTile){
                return true;    // let the player try again
            }

            // if player tries to build under itself, ask via pending choice
            if (clickedTile == activeWorkerTile) {
                pendingChoice = true;
                choiceType = "BUILD_UNDER_SELF";
                choiceMessage = getDescription() + "\n" + currentPlayer.getName() + ", do you want to build under yourself using Zeus's power?";
                pendingBuildTile = clickedTile;
                pendingBuildTower = tower;
                return true;
            }

            // Normal build (not under self)
            executeBuild(currentPlayer, clickedTile, tower, false);
        }

        return isMoving;
    }

    /**
     * Resolves the pending choice for building under self.
     *
     * @param accepted true if the player wants to build under self, false to cancel
     */
    @Override
    public void resolveChoice(boolean accepted) {
        super.resolveChoice(accepted);
        if (accepted) {
            builtUnderSelf = true;
            // The actual build will be executed by the service calling executePendingBuild()
        } else {
            // Player chose not to build under self — they can pick another tile
            pendingBuildTile = null;
            pendingBuildTower = null;
            builtUnderSelf = false;
        }
    }

    /**
     * Executes the pending build after the player has accepted building under self.
     * Called by GameService after resolveChoice(true).
     *
     * @param currentPlayer the current player
     */
    public void executePendingBuild(Player currentPlayer) {
        if (pendingBuildTile != null) {
            executeBuild(currentPlayer, pendingBuildTile, pendingBuildTower, true);
            pendingBuildTile = null;
            pendingBuildTower = null;
        }
    }

    /**
     * Performs the build action.
     *
     * @param currentPlayer the current player
     * @param tile the tile to build on
     * @param tower the tower to build
     * @param allowBuildUnderSelf whether building under self is allowed
     */
    private void executeBuild(Player currentPlayer, Tile tile, Tower tower, boolean allowBuildUnderSelf) {
        BuildAction buildAction = new BuildAction(tile, tower, allowBuildUnderSelf);
        buildAction.execute();

        if (buildAction.isBuildSuccessful()) {
            isMoving = false;
            isBuildingPhase = false;
            currentPlayer.setActionSuccessful(true);
            currentPlayer.setBuiltUnderSelf(builtUnderSelf);
        }
    }

    /**
     * Resets Zeus's internal state for a new turn or game.
     */
    @Override
    public void resetState() {
        super.resetState();
        isBuildingPhase = false;
        isMoving = false;
        builtUnderSelf = false;
        activeWorker = null;
        pendingBuildTile = null;
        pendingBuildTower = null;
    }
}
