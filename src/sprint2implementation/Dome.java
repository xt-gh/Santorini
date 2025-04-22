package sprint2implementation;

import javax.swing.*;

public class Dome {
    private ImageIcon domeIcon;

    public Dome(String iconPath) {
        this.domeIcon = new ImageIcon(iconPath);
    }

    public ImageIcon getDomeIcon() {
        return domeIcon;
    }
}
