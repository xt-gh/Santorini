package prototype.pee_yee_peen;

import javax.swing.*;
import java.awt.*;

public class Tile extends JButton {
    private Player player;
    private Tower tower;
    private ImageIcon oriImageIcon;
    private ImageIcon currentIcon;
    private int tileRow;
    private int tileColumn;

    public Tile(String imagePath, int tileRow, int tileColumn) {
        setPlayer(null);
        this.tower = new Tower();
        oriImageIcon = new ImageIcon(imagePath);
        currentIcon = oriImageIcon;
//        currentIcon = new ImageIcon(imagePath);
        this.tileRow = tileRow;
        this.tileColumn = tileColumn;
        resizeAndSetIcon();

    }

    public void updateIcon(ImageIcon icon) {
        if (icon != null) {
            currentIcon = icon;
        } else {
            currentIcon = oriImageIcon;
        }
        resizeAndSetIcon();
    }

    public ImageIcon getCurrentIcon() {
        return currentIcon;
    }

    public int getTileRow() {
        return tileRow;
    }

    public int getTileColumn() {
        return tileColumn;
    }

    public Player getPlayer() {
        return player;
    }

    public Tower getTower() {
        return tower;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        resizeAndSetIcon();
    }

    private void resizeAndSetIcon() {
        if (currentIcon != null && getWidth() > 0 && getHeight() > 0) {
            Image scaledImage = currentIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(scaledImage));
//            System.out.println((new ImageIcon(scaledImage)));
//            System.out.println("success2");
        }
    }

    @Override
    public String toString() {
        return "Tile location: (" + tileRow + ", " + tileColumn + ")";
    }
}
