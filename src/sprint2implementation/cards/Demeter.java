package sprint2implementation.cards;

import sprint2implementation.actions.BuildAction;
import sprint2implementation.actions.MoveAction;
import sprint2implementation.characters.Player;
import sprint2implementation.grounds.Tile;
import sprint2implementation.towers.Tower;

import javax.swing.*;

/**
 * Represents the Demeter God card which allows a player to build twice in one turn,
 * but not on the same tile.
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
                JOptionPane.showMessageDialog(null, "Now is your building phase!", "Building Stage", JOptionPane.PLAIN_MESSAGE);
            }
        }
        else
        {
            // Prevent building twice on the same tile
            if (waitingForSecondBuild && clickedTile == firstBuildTile) {
                JOptionPane.showMessageDialog(null, "You cannot build on the same tile twice!", "Invalid Build", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            BuildAction buildAction = new BuildAction(clickedTile, tower);
            buildAction.execute();

            if (buildAction.isBuildSuccessful()) {
                if (waitingForSecondBuild) { // second building
                    waitingForSecondBuild = false;
                    isBuildingPhase = false;
                    isMoving = false;
                    currentPlayer.setActionSuccessful(true); // the player completed both move and build actions

                } else if (builtNum < MAX_BUILD) { // first building
                    firstBuildTile = clickedTile;

                    int response = JOptionPane.showConfirmDialog(
                            null,
                            getDescription() + "\n" + currentPlayer.getName() + ", do you want to perform a second build using Demeter's power?",
                            "Second Build?",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (response == JOptionPane.YES_OPTION) {
                        waitingForSecondBuild = true;
                        isMoving = false;
                        JOptionPane.showMessageDialog(null, "Perform your second build.", "Building Stage", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        waitingForSecondBuild = false;
                        isBuildingPhase = false;
                        isMoving = false;
                        currentPlayer.setActionSuccessful(true); // Player ends turn after 1 build
                    }
                }
            }
        }

        return isMoving;
    }

}
