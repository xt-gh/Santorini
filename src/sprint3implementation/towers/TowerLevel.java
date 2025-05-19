package sprint3implementation.towers;

import javax.swing.*;
import java.net.URL;

/**
 * Represents a visual level component of a tower in the game.
 * Each level is associated with an icon used for display purposes.
 *
 * @author Tiffany
 * Modified by: Yee Peen
 */
public class TowerLevel {

    /**
     * The icon representing this tower level.
     */
    private ImageIcon levelIcon;

    /**
     * Constructs a TowerLevel with the specified image icon path.
     *
     * @param iconPath the URL pointing to the image representing this level
     */
    public TowerLevel(URL iconPath) {

        this.levelIcon = new ImageIcon(iconPath);
    }

    /**
     * Returns the icon for this tower level.
     *
     * @return the ImageIcon representing this level
     */
    public ImageIcon getLevelIcon() {
        return levelIcon;
    }
}
