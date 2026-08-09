package santorini.game.cards;

import santorini.game.actions.BuildAction;
import santorini.game.actions.MoveAction;
import santorini.game.characters.Player;
import santorini.game.grounds.Tile;
import santorini.game.towers.Tower;

/**
 * Represents the Demeter God card which allows a player to build twice in one turn,
 * but not on the same tile.
 *
 * Refactored: Replaced JOptionPane dialogs with pending-choice mechanism.
 * After the first build, sets pendingChoice=true so the frontend can ask the player
 * whether to build again. resolveChoice() handles the player's response.
 *
 * @author Tiffany
 * Modified by: Yee Peen, Emil
 */
public class Demeter extends GodCard {

    /**
     * The tile where the first build occurred.
     */
    private Tile firstBuildTile = null;

    /**
     * Indicates whether the player is waiting to perform the second build.
     */
    private boolean waitingForSecondBuild;

    /**
     * Indicates whether the building phase is active.
     */
    private boolean isBuildingPhase = false;

    /**
     * Indicates whether the player is in the moving phase.
     */
    private boolean isMoving = false;

    /**
     * The maximum number of builds allowed (2 for Demeter).
     */
    private final int MAX_BUILD = 2;

    /**
     * The number of builds performed so far.
     */
    private int builtNum = 0;


    /**
     * Constructs a Demeter GodCard with name and description.
     */
    public Demeter() {
        super("Demeter", "Demeter can build twice in a turn.");
    }

    /**
     * Executes Demeter's special ability which includes moving and up to two builds,
     * with the restriction that both builds cannot occur on the same tile.
     *
     * After the first successful build, sets pendingChoice=true to ask the player
     * whether they want to build again.
     *
     * @param currentPlayer the current player taking the action
     * @param selectedTile the tile where the current worker starts (before movement)
     * @param clickedTile the tile the player clicks to either move to or build on
     * @param tower the tower being built upon
     *
     * @return true if still in progress (i.e., moving/building), otherwise false
     */
    public boolean executeSpecialAbility(Player currentPlayer, Tile selectedTile, Tile clickedTile, Tower tower)
    {
        if (!isBuildingPhase) {
            MoveAction moveAction = new MoveAction(selectedTile, clickedTile, selectedTile.getWorker());
            moveAction.execute();

            if (moveAction.isMoveSuccessful()) {
                isMoving = true;
                isBuildingPhase = true;
                firstBuildTile = null;
            }
        }
        else
        {
            // Prevent building twice on the same tile
            if (waitingForSecondBuild && clickedTile == firstBuildTile) {
                return false;
            }

            BuildAction buildAction = new BuildAction(clickedTile, tower, false);
            buildAction.execute();

            if (buildAction.isBuildSuccessful()) {
                if (waitingForSecondBuild) { // second building
                    waitingForSecondBuild = false;
                    isBuildingPhase = false;
                    isMoving = false;
                    currentPlayer.setActionSuccessful(true); // the player completed both move and build actions

                } else if (builtNum < MAX_BUILD) { // first building
                    firstBuildTile = clickedTile;

                    // Ask via pending choice instead of JOptionPane
                    pendingChoice = true;
                    choiceType = "SECOND_BUILD";
                    choiceMessage = getDescription() + "\n" + currentPlayer.getName() + ", do you want to perform a second build using Demeter's power?";
                }
            }
        }

        return isMoving;
    }

    /**
     * Resolves the pending choice for the second build.
     *
     * @param accepted true if the player wants to build again, false to end turn
     */
    @Override
    public void resolveChoice(boolean accepted) {
        super.resolveChoice(accepted);
        if (accepted) {
            waitingForSecondBuild = true;
            isMoving = false;
            // Wait for second build click
        } else {
            waitingForSecondBuild = false;
            isBuildingPhase = false;
            isMoving = false;
            // Action will be marked successful by the service
        }
    }

    /**
     * Whether Demeter's choice was declined (player chose not to build again).
     * Used by GameService to mark the action as successful.
     */
    public boolean isDeclinedSecondBuild() {
        return !waitingForSecondBuild && !isBuildingPhase && !isMoving && !pendingChoice;
    }

    /**
     * Resets Demeter's internal state for a new turn or game.
     */
    @Override
    public void resetState() {
        super.resetState();
        firstBuildTile = null;
        waitingForSecondBuild = false;
        isBuildingPhase = false;
        isMoving = false;
        builtNum = 0;
    }
}
