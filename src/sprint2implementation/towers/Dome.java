package sprint2implementation.towers;

import javax.swing.*;
import java.net.URL;

public class Dome {
    private ImageIcon domeIcon;

    public Dome(URL iconPath) {
        this.domeIcon = new ImageIcon(iconPath);
    }


    public ImageIcon getDomeIcon() {
        return domeIcon;
    }
}
