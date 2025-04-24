package sprint2implementation.towers;

import javax.swing.*;
import java.net.URL;

public class TowerLevel
{
    private int level;
    private ImageIcon levelIcon;

//    public TowerLevel(int level, String iconPath)
//    {
//        this.level = level;
//        this.levelIcon = new ImageIcon(iconPath);
//    }

    public TowerLevel(int level, URL iconPath)
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
