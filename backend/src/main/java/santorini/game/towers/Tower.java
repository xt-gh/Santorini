package santorini.game.towers;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a tower in the game, consisting of multiple levels and optionally a dome at the top.
 * Towers can have up to a maximum of three levels and may be completed by placing a dome.
 *
 * Refactored: Removed ImageIcon dependencies. Tower levels are now tracked as a simple count
 * and dome as a boolean, since the frontend handles all rendering.
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
     * The current number of levels built on the tower.
     */
    private int levelCount;

    /**
     * Whether a dome has been placed on top of the tower.
     */
    private boolean hasDome;

    /**
     * Constructs an empty tower with no levels and no dome.
     */
    public Tower() {
        this.levelCount = 0;
        this.hasDome = false;
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
     */
    public void addLevel() {
        if (levelCount < MAX_LEVELS) {
            levelCount++;
        }
    }

    /**
     * Adds a dome to the tower if one is not already present.
     * The dome marks the tower as complete and unusable.
     */
    public void addDome() {
        if (!hasDome) {
            hasDome = true;
        }
    }

    /**
     * Returns the number of levels currently in the tower.
     *
     * @return the current number of levels
     */
    public int getLevelCount() {
        return levelCount;
    }

    /**
     * Checks whether the tower currently has a dome.
     *
     * @return true if the tower has a dome, false otherwise
     */
    public boolean hasDome() {
        return hasDome;
    }

    /**
     * Checks if the tower has reached the maximum number of levels.
     *
     * @return true if the tower is at max level, false otherwise
     */
    public boolean isAtMaxLevel() {
        return levelCount == MAX_LEVELS;
    }

    /**
     * Checks whether the tower has no levels.
     *
     * @return true if the tower has no levels, false otherwise
     */
    public boolean isTowerEmpty() {
        return levelCount == 0;
    }
}
