package sprint2implementation.actions;

import sprint2implementation.grounds.Tile;
import sprint2implementation.towers.Tower;

import javax.swing.*;

public class BuildAction extends Action {
    private Tile targetTile;
    private Tower tower;
    private boolean buildSuccessful;

    public BuildAction(Tile targetTile, Tower tower) {
        super(targetTile.getTileRow(), targetTile.getTileColumn());
        this.targetTile = targetTile;
        this.tower = tower;
        this.buildSuccessful = validateBuild();
    }

    private boolean validateBuild() {

        if (tower == null) {
            tower = new Tower();
        }
        // Prevents building if the worker is already standing on a tile or a tower already has a dome
        if (targetTile.getWorker() != null || tower.hasDome()) {
            return false;
        }
        // Allows building only if the current tower level has not exceeded the maximum level
        return tower.getLevelCount() <= Tower.getMaxLevels();
    }

    @Override
    public void execute() {
        if (buildSuccessful) {

            // If the tower has not reached its maximum level yet, add a new level
            if (!tower.isAtMaxLevel()) {
                tower.addLevel();
            }

            // If the tower is at the maximum level but does not have a dome yet, place a dome
            else if (!tower.hasDome())
            {
                tower.addDome();
            }

            // Update the tower object on the tile to reflect changes
            targetTile.setTower(tower);
            targetTile.updateIcon(tower.getCurrentIcon());
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Build failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isBuildSuccessful() {
        return buildSuccessful;
    }
}

