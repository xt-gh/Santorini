package sprint2implementation.towers;

import javax.swing.*;
import java.net.URL;

/**
 * Represents a dome in the game, typically used as the top level of a tower.
 * It holds the image icon associated with the dome for rendering purposes.
 *
 * @author Tiffany
 */
public class Dome {

    /**
     * The ImageIcon representing the visual of the dome.
     */
    private ImageIcon domeIcon;

    /**
     * Constructs a Dome with the given image icon path.
     *
     * @param iconPath the URL path to the dome image icon
     */
    public Dome(URL iconPath) {
        this.domeIcon = new ImageIcon(iconPath);
    }


    /**
     * Returns the ImageIcon associated with the dome.
     *
     * @return the dome's ImageIcon
     */
    public ImageIcon getDomeIcon() {
        return domeIcon;
    }
}
