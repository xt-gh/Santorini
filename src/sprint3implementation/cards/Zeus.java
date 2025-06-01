package sprint3implementation.cards;

import sprint3implementation.actions.BuildAction;
import sprint3implementation.actions.MoveAction;
import sprint3implementation.characters.Player;
import sprint3implementation.grounds.Tile;
import sprint3implementation.towers.Tower;
import sprint3implementation.characters.Worker;


import javax.swing.*;

/**
 * Zeus class represents the Zeus god card with a special ability
 * that allows building under itself during the building phase.
 *
 * This class controls the logic of Zeus's movement and building phases,
 * including the special building under self power.
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
                JOptionPane.showMessageDialog(null, "Now is your building phase!", "Building Stage", JOptionPane.PLAIN_MESSAGE);
            }

        } else {
            // Building Phase
            Tile activeWorkerTile = activeWorker.getCurrentTile();

            // prevent building on another worker's tile
            if (clickedTile.hasWorker() && clickedTile != activeWorkerTile){
                JOptionPane.showMessageDialog(null, "You cannot build under another worker!", "Invalid Action", JOptionPane.ERROR_MESSAGE);
                return true;    // let the player try again

            }

            // if player tries to build under itself, confirm the special power use
            if (clickedTile == activeWorkerTile) {
                int response = JOptionPane.showConfirmDialog(
                        null,
                        getDescription() + "\n" + currentPlayer.getName() + ", do you want to build under yourself using Zeus's power?",
                        "Build Under Self?",
                        JOptionPane.YES_NO_OPTION
                );

                // if player choose the Zeus power
                if (response == JOptionPane.YES_OPTION) {
                    builtUnderSelf = true;
                }else if (response == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(null, "You chose not to build under yourself. Please select another tile.", "Notice", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    builtUnderSelf = false;
                    isBuildingPhase = false;
                    isMoving = false;
                    currentPlayer.setActionSuccessful(true); // Player ends turn after 1 build
                    return false;
                }
            }

            // Build Action
            BuildAction buildAction = new BuildAction(clickedTile, tower, true);
            buildAction.execute();

            if (buildAction.isBuildSuccessful()) {
                // build successful - end turn
                isMoving = false;
                isBuildingPhase = false;
                currentPlayer.setActionSuccessful(true);
                currentPlayer.setBuiltUnderSelf(builtUnderSelf);

                // update icon if building under self
                if (builtUnderSelf){
                    int newLevel = activeWorkerTile.getTower().getLevelCount();
                    ImageIcon newIcon = activeWorker.getPlayer().getPlayerPositionTower().get(newLevel);
                    activeWorkerTile.updateIcon(newIcon);
                }
            }
        }

        return isMoving;
    }
}
