package sprint3implementation.cards;

import sprint3implementation.actions.BuildAction;
import sprint3implementation.actions.MoveAction;
import sprint3implementation.characters.Player;
import sprint3implementation.grounds.Tile;
import sprint3implementation.towers.Tower;

import javax.swing.*;

/**
 * Represents the Artemis God card which allows a player to move twice in one turn,
 * but cannot return to the original tile.
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
                JOptionPane.showMessageDialog(null, "You cannot move back to your original position!", "Invalid Move", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            MoveAction moveAction = new MoveAction(selectedTile, clickedTile, selectedTile.getWorker());
            moveAction.execute();

            if (moveAction.isMoveSuccessful()) {
                movedNum++;
                isMoving = true;

                if (waitingForSecondMove) {
                    // This is the second move
                    waitingForSecondMove = false;
                    isBuildingPhase = true;
                    JOptionPane.showMessageDialog(null, "Now is your building phase!", "Building Stage", JOptionPane.PLAIN_MESSAGE);
                } else if (movedNum < MAX_MOVE) {
                    // After first move, ask if user wants to move again
                    int response = JOptionPane.showConfirmDialog(
                            null,
                            getDescription() + "\n" + currentPlayer.getName() + ", Do you want to move again using Artemis's ability?",
                            "Second Move?",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (response == JOptionPane.YES_OPTION) {
                        waitingForSecondMove = true;
                        firstMoveFromTile = selectedTile;
                        JOptionPane.showMessageDialog(null, "You can perform your second move.", "Moving Stage", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // Skip second move, go straight to building phase
                        waitingForSecondMove = false;
                        isBuildingPhase = true;
                        JOptionPane.showMessageDialog(null, "Now is your building phase!", "Building Stage", JOptionPane.PLAIN_MESSAGE);
                    }
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
}

