package sprint2implementation.cards;

import sprint2implementation.actions.BuildAction;
import sprint2implementation.actions.MoveAction;
import sprint2implementation.characters.Player;
import sprint2implementation.grounds.Tile;
import sprint2implementation.towers.Tower;

import javax.swing.*;

public class Demeter extends GodCard {
    private boolean waitingForSecondBuild;
    private Tile firstBuildTile = null;
    private boolean isBuildingPhase = false;
    private final int MAX_BUILD = 2;
    private int builtNum = 0;
    private Boolean isMoving = false;

    public Demeter() {
        super("Demeter", "can build twice in a turn");
    }

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
            if (waitingForSecondBuild && clickedTile == firstBuildTile) { // to check if the worker built at the tile before
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
                    waitingForSecondBuild = true;
                    isMoving = false;
                    firstBuildTile = clickedTile;
                    JOptionPane.showMessageDialog(null, currentPlayer.getName() + " " + currentPlayer.getGodCard().getDescription());
                }
            }
        }
        return isMoving;
    }
}
