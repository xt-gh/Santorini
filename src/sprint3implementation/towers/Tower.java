package sprint3implementation.towers;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a tower in the game, consisting of multiple levels and optionally a dome at the top.
 * Towers can have up to a maximum of three levels and may be completed by placing a dome.
 *
 * @author Tiffany
 * Modified by: Yee Peen
 */
public class Tower {

    /**
     * The maximum number of levels a tower can have before a dome can be placed.
     */
    private static final int MAX_LEVELS = 3;

    /**
     * A list of levels currently built on the tower.
     */
    private List<TowerLevel> towerLevels;

    /**
     * The dome placed on top of the tower.
     */
    private Dome dome;

    /**
     * Constructs an empty tower with no levels and no dome.
     */
    public Tower() {
        this.towerLevels = new ArrayList<>();
        this.dome = null;
    }

    /**
     * Returns the maximum number of levels a tower can have.
     *
     * @return the maximum number of levels (3)
     */
    public static int getMaxLevels() {
        return MAX_LEVELS;
    }

    /**
     * Adds a level to the tower if it hasn't reached the maximum number of levels.
     * Loads the appropriate level image based on the current level count.
     */
    public void addLevel() {
        if (towerLevels.size() < MAX_LEVELS) {
            int nextLevel = towerLevels.size() + 1;
            URL iconPath = Tower.class.getResource("/pics/Lvl_" + nextLevel + ".png");
            towerLevels.add(new TowerLevel(iconPath));
        }
    }

//    public void addLevel(int playerNumber) {
//        if (towerLevels.size() < MAX_LEVELS) {
//            int nextLevel = towerLevels.size() + 1;
//            URL iconPath;
//            if (playerNumber > 0) {
//                iconPath = Tower.class.getResource("/pics/Lvl_" + nextLevel + "_player_" + playerNumber + ".png");
//            } else {
//                iconPath = Tower.class.getResource("/pics/Lvl_" + nextLevel + ".png");
//            }
//            towerLevels.add(new TowerLevel(iconPath));
//        }
//    }

    /**
     * Adds a dome to the tower if one is not already present.
     * The dome marks the tower as complete and unusable.
     */
    public void addDome() {
        if (!hasDome()) {
            dome = new Dome(Tower.class.getResource("/pics/Lvl_complete.png"));
        }
    }

    /**
     * Returns the number of levels currently in the tower.
     *
     * @return the current number of levels
     */
    public int getLevelCount() {
        return towerLevels.size();
    }

    /**
     * Checks whether the tower currently has a dome.
     *
     * @return true if the tower has a dome, false otherwise
     */
    public boolean hasDome() {
        return dome != null;
    }

    /**
     * Checks if the tower has reached the maximum number of levels.
     *
     * @return true if the tower is at max level, false otherwise
     */
    public boolean isAtMaxLevel() {
        return towerLevels.size() == MAX_LEVELS;
    }


    /**
     * Returns the icon representing the current state of the tower.
     * If it has a dome, returns the dome icon; otherwise, returns the icon of the top level or an empty tile if the tower is empty.
     *
     * @return the current icon representing the tower
     */
    public ImageIcon getCurrentIcon() {
        if (hasDome())
        {
            return dome.getDomeIcon();
        }
        else if (!isTowerEmpty())
        {
            return towerLevels.get(towerLevels.size() - 1).getLevelIcon();
        }
        else
        {
            URL emptyTilePath = Tower.class.getResource("/pics/Empty_tile.png");
            return new ImageIcon(emptyTilePath);
        }
    }

    /**
     * Checks whether the tower has no levels.
     *
     * @return true if the tower has no levels, false otherwise
     */
    public boolean isTowerEmpty() {
        return towerLevels.isEmpty();
    }

}