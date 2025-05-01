package sprint2implementation.towers;

import javax.swing.*;
import java.net.URL;

public class TowerLevel
{
    private ImageIcon levelIcon;

    public TowerLevel(URL iconPath)
    {
        this.levelIcon = new ImageIcon(iconPath);
    }

    public ImageIcon getLevelIcon() {
        return levelIcon;
    }
}
