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
            if (clickedTile == firstMoveFromTile) { // the worker cannot move back to its previous position
                JOptionPane.showMessageDialog(null, "You cannot move back to your original position!", "Invalid Move", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            MoveAction moveAction = new MoveAction(selectedTile, clickedTile, selectedTile.getWorker());
            moveAction.execute();

            if (moveAction.isMoveSuccessful()) {
                movedNum++;

                if (waitingForSecondMove) { // the second movement
                    waitingForSecondMove = false;
                    isBuildingPhase = true;
                    JOptionPane.showMessageDialog(null, "Now is your building phase!", "Building Stage", JOptionPane.PLAIN_MESSAGE);
                } else if (movedNum < MAX_MOVE){ // the first movement
                    waitingForSecondMove = true;
                    firstMoveFromTile = selectedTile;
                    JOptionPane.showMessageDialog(null, currentPlayer.getName() + " " + currentPlayer.getGodCard().getDescription());
                }
                isMoving = true;
            }
        }
        else
        {
            BuildAction buildAction = new BuildAction(clickedTile, tower);
            buildAction.execute();
            isMoving = false;
            if (buildAction.isBuildSuccessful()) {
                isBuildingPhase = false;
                movedNum = 0;
                currentPlayer.setActionSuccessful(true); // the player completed both move and build actions
            }
        }
        return isMoving;
    }
}
