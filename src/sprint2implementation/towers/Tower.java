package sprint2implementation.towers;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Tower {
    private static final int MAX_LEVELS = 3;
    private List<TowerLevel> towerLevels;
    private Dome dome;

    public Tower() {
        this.towerLevels = new ArrayList<>();
        this.dome = null;
    }

    public static int getMaxLevels() {
        return MAX_LEVELS;
    }

    public void addLevel() {
        if (towerLevels.size() < MAX_LEVELS) {
            int nextLevel = towerLevels.size() + 1;
            URL iconPath = Tower.class.getResource("/pics/Lvl_" + nextLevel + ".png");
            towerLevels.add(new TowerLevel(iconPath));
        }
    }

    public void addDome() {
        if (!hasDome()) {
            dome = new Dome(Tower.class.getResource("/pics/Lvl_complete.png"));
        }
    }

    public int getLevelCount() {
        return towerLevels.size();
    }

    public boolean hasDome() {
        return dome != null;
    }

    public boolean isAtMaxLevel() {
        return towerLevels.size() == MAX_LEVELS;
    }

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

    public boolean isTowerEmpty()
    {
        return towerLevels.isEmpty();
    }

}