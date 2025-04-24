package sprint2implementation.towers;

import javax.swing.*;
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
            String iconPath = "src/sprint2implementation/pics/Lvl_" + nextLevel + ".png";
            towerLevels.add(new TowerLevel(nextLevel, iconPath));
        }
    }

    public void addDome() {
        if (!hasDome()) {
            dome = new Dome("src/sprint2implementation/pics/Lvl_complete.png");
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
        else if (!towerLevels.isEmpty())
        {
            return towerLevels.get(towerLevels.size() - 1).getLevelIcon();
        }
        else
        {
            return new ImageIcon("src/sprint2implementation/pics/Empty_tile.png");
        }
    }

}