package santorini.game.cards;

import santorini.game.actions.BuildAction;
import santorini.game.actions.MoveAction;
import santorini.game.characters.Player;
import santorini.game.grounds.Tile;
import santorini.game.towers.Tower;

/**
 * Represents the Artemis God card which allows a player to move twice in one turn,
 * but cannot return to the original tile.
 *
 * Refactored: Replaced JOptionPane dialogs with pending-choice mechanism.
 * After the first move, sets pendingChoice=true so the frontend can ask the player
 * whether to move again. resolveChoice() handles the player's response.
 *
 * @author Tiffany
 * Modified by: Yee Peen, Emil
 */
public class Artemis extends GodCard {

    /**
     * The tile from which the first move was made.
     */
    private Tile firstMoveFromTile = null;

    /**
     * Indicates whether the player is waiting to perform the second move.
     */
    private boolean waitingForSecondMove = false;

    /**
     * Indicates whether the building phase is active.
     */
    private boolean isBuildingPhase = false;

    /**
     * Indicates whether the player is in the moving phase.
     */
    private boolean isMoving = false;

    /**
     * The maximum number of moves allowed (2 for Artemis).
     */
    private final int MAX_MOVE = 2;

    /**
     * The number of moves performed so far.
     */
    private int movedNum = 0;

    /**
     * Constructs an Artemis GodCard with name and description.
     */
    public Artemis(){
        super("Artemis", "Artemis can move twice in a turn.");
    }

    /**
     * Executes Artemis's special ability, allowing up to two moves followed by a build.
     * The second move cannot be back to the original tile.
     *
     * After the first successful move, sets pendingChoice=true to ask the player
     * whether they want to use the second move. The actual decision is handled
     * by resolveChoice().
     *
     * @param currentPlayer the player currently performing the action
     * @param selectedTile the tile where the current worker starts (or is standing)
     * @param clickedTile the tile the player chooses to move to or build on
     * @param tower the tower to build upon during the building phase
     *
     * @return true if still in the moving phase, false otherwise
     */
    public boolean executeSpecialAbility(Player currentPlayer, Tile selectedTile, Tile clickedTile, Tower tower) {
        if (!isBuildingPhase) {
            if (clickedTile == firstMoveFromTile) { // Can't move back to original position
                return false;
            }

            MoveAction moveAction = new MoveAction(selectedTile, clickedTile, selectedTile.getWorker());
            moveAction.execute();

            if (moveAction.isMoveSuccessful()) {
                movedNum++;
                isMoving = true;

                if (waitingForSecondMove) {
                    // This is the second move — proceed to building phase
                    waitingForSecondMove = false;
                    isBuildingPhase = true;
                } else if (movedNum < MAX_MOVE) {
                    // After first move, ask if user wants to move again via pending choice
                    pendingChoice = true;
                    choiceType = "SECOND_MOVE";
                    choiceMessage = getDescription() + "\n" + currentPlayer.getName() + ", do you want to move again using Artemis's ability?";
                    firstMoveFromTile = selectedTile;
                }
            }
        } else {
            // Building phase
            BuildAction buildAction = new BuildAction(clickedTile, tower, false);
            buildAction.execute();
            isMoving = false;

            if (buildAction.isBuildSuccessful()) {
                // Reset Artemis state
                waitingForSecondMove = false;
                isBuildingPhase = false;
                movedNum = 0;
                firstMoveFromTile = null;
                currentPlayer.setActionSuccessful(true);
            }
        }

        return isMoving;
    }

    /**
     * Resolves the pending choice for the second move.
     *
     * @param accepted true if the player wants to move again, false to skip
     */
    @Override
    public void resolveChoice(boolean accepted) {
        super.resolveChoice(accepted);
        if (accepted) {
            waitingForSecondMove = true;
            // isMoving stays true — waiting for second move click
        } else {
            // Skip second move, go straight to building phase
            waitingForSecondMove = false;
            isBuildingPhase = true;
            // isMoving stays true — waiting for build click
        }
    }

    /**
     * Resets Artemis's internal state for a new turn or game.
     */
    @Override
    public void resetState() {
        super.resetState();
        firstMoveFromTile = null;
        waitingForSecondMove = false;
        isBuildingPhase = false;
        isMoving = false;
        movedNum = 0;
    }
}
