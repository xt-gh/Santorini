package sprint2implementation;

import javax.swing.*;

public class TowerLevel
{
    private int level;
    private ImageIcon levelIcon;

    public TowerLevel(int level, String iconPath)
    {
        this.level = level;
        this.levelIcon = new ImageIcon(iconPath);
    }

    public int getLevel() {
        return level;
    }

    public ImageIcon getLevelIcon() {
        return levelIcon;
    }
}
