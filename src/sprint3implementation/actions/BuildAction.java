package sprint3implementation.actions;

import sprint3implementation.grounds.Tile;
import sprint3implementation.towers.Tower;

import javax.swing.*;

/**
 * An action that builds on a given tile.
 * Adds a new tower level or places a dome if the tower is complete.
 *  * @author Tiffany
 *  * Modified by: Yee Peen, Xin Thung
 */
public class BuildAction extends Action {

    /**
     * The tile on which the build action is attempted.
     */
    private Tile targetTile;

    /**
     * The tower being build or updated on the target tile.
     */
    private Tower tower;

    /**
     * Indicates whether the build action is valid and can be executed.
     */
    private boolean buildSuccessful;

    private boolean allowBuildUnderSelf;


    /**
     * Constructor for BuildAction.
     *
     * @param targetTile the tile on which to perform the build
     * @param tower the tower to be built or updated
     */
    public BuildAction(Tile targetTile, Tower tower, boolean allowBuildUnderSelf) {
        super(targetTile.getTileRow(), targetTile.getTileColumn());
        this.targetTile = targetTile;
        this.tower = tower;
        this.allowBuildUnderSelf = allowBuildUnderSelf;
        this.buildSuccessful = validateBuild();
    }

    /**
     * Validated whether a build can occur on the target tile.
     * A build is not allowed if there is a worker on the tile or if the tower already has a dome.
     * A new tower created if none exists.
     *
     * @return true if the build is allowed
     */
//    private boolean validateBuild() {
//
//        if (tower == null) {
//            tower = new Tower();
//        }
//
//        if (tower.hasDome()) {
//            return false;
//        }
//
//        if (targetTile.getWorker() != null && !allowBuildUnderSelf) {
//            return false;
//        }
//
//        return tower.getLevelCount() < Tower.getMaxLevels() || !tower.hasDome();
////        if (tower == null) {
////            tower = new Tower();
////        }
//////        // Prevents building if the worker is already standing on a tile or a tower already has a dome
//////        if (targetTile.getWorker() != null || tower.hasDome()) {
//////            return false;
//////        }
////
////        if (tower.hasDome()) {
////            return false;
////        }
////
////        if (targetTile.getWorker() != null && !allowBuildUnderSelf) {
////            return false;
//    }
//
//
//
//
//
////        // Allows building only if the current tower level has not exceeded the maximum level
////        if (tower.getLevelCount() <= Tower.getMaxLevels())
////        {
////            return true;
////        }
//        return false;
//
//    }

    private boolean validateBuild() {
        // Create a new tower if none exists
        if (tower == null) {
            tower = new Tower();
        }

        // Cannot build if tower already has a dome
        if (tower.hasDome()) {
            return false;
        }

        // Cannot build if there's a worker and building under self is not allowed
        if (targetTile.getWorker() != null && !allowBuildUnderSelf) {
            return false;
        }

        // Allow build if: not at max level OR at max level and no dome yet
        return tower.getLevelCount() < Tower.getMaxLevels() ||
                (tower.getLevelCount() == Tower.getMaxLevels() && !tower.hasDome());
    }



    /**
     * Performs the build if allowed
     * Adds a level if not at max level, or adds a dome is it is on max level.
     * Updates the tile with the new tower state.
     * Shows an error message if the build is not valid.
     */
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


    /**
     * Returns whether the build passed validation.
     *
     * @return true if valid
     */
    public boolean isBuildSuccessful() {
        return buildSuccessful;
    }
}

