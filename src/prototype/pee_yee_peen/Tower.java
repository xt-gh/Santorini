package prototype.pee_yee_peen;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Tower {
    private int level;
    private ImageIcon towerIcon;
    private boolean hasDome;
    private Map<Integer, ImageIcon> towerLevels;

    public Tower() {
        this.level = 0;
        hasDome = false;
        initializeTowerLevels();
    }

    private void initializeTowerLevels() {
        towerLevels = new HashMap<>();
        towerLevels.put(0, new ImageIcon("src/prototype/pee_yee_peen/pics/Empty_tile.png"));
        towerLevels.put(1, new ImageIcon("src/prototype/pee_yee_peen/pics/Lvl_1.png"));
        towerLevels.put(2, new ImageIcon("src/prototype/pee_yee_peen/pics/Lvl_2.png"));
        towerLevels.put(3, new ImageIcon("src/prototype/pee_yee_peen/pics/Lvl_3.png"));
        towerLevels.put(4, new ImageIcon("src/prototype/pee_yee_peen/pics/Lvl_complete.png"));
    }

    public void increaseLevel() {
        if (level < 4) {
            level++;
        }
    }

    public ImageIcon getTowerIcon(int level) {
        towerIcon = towerLevels.get(level);
        if (towerIcon == null) {
            return towerLevels.get(0); // Return ground if null
        }
        return towerIcon;
    }

    public int getLevel() {
        return level;
    }

    public Map<Integer, ImageIcon> getTowerLevels() {
        return towerLevels;
    }

    public boolean isHasDome()
    {
        return level ==4;
    }

    @Override
    public String toString() {
        return "Tower Level: " + level;
    }
}