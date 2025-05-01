package sprint2implementation.cards;

import sprint2implementation.actions.BuildAction;
import sprint2implementation.actions.MoveAction;
import sprint2implementation.characters.Player;
import sprint2implementation.grounds.Tile;
import sprint2implementation.towers.Tower;

import javax.swing.*;

public class Artemis extends GodCard {
    private boolean waitingForSecondMove = false;
    private Tile firstMoveFromTile = null;
    private final int MAX_MOVE = 2;
    private int movedNum = 0;
    private boolean isBuildingPhase = false;
    private Boolean isMoving = false;

    public Artemis(){
        super("Artemis", "can move twice in a turn");
    }

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
                            currentPlayer.getName() + ", Do you want to move again using Artemis's ability?",
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
            BuildAction buildAction = new BuildAction(clickedTile, tower);
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

