package prototype.pee_yee_peen;

import javax.swing.*;
import java.util.HashMap;

public class Player {
    private ImageIcon playerIcon;
    private String name;
    private HashMap<Integer, ImageIcon> playerPositionTower = new HashMap<>();

    public Player(String name, String imagePath)
    {
        this.name = name;
        setPlayerImageIcon(imagePath);
        playerPositionTower.put(0, new ImageIcon(imagePath));

    }

    public HashMap<Integer, ImageIcon> getPlayerPositionTower() {
        return playerPositionTower;
    }

    public void setPlayerPositionTower(int num, String imagePath) {
        playerPositionTower.put(num, new ImageIcon(imagePath));
    }

    public void setPlayerImageIcon(String imagePath) {
        playerIcon = new ImageIcon(imagePath);
    }

    public ImageIcon getPlayerIcon() {
        return playerIcon;
    }

    public String getName() {
        return name;
    }
}
