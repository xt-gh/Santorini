package sprint2implementation.grounds;

import sprint2implementation.characters.Worker;
import sprint2implementation.towers.Tower;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Tile extends JButton {
    private Tower tower;
    private ImageIcon oriImageIcon;
    private ImageIcon currentIcon;
    private int tileRow;
    private int tileColumn;
    private Worker worker;

    public Tile(URL imagePath, int tileRow, int tileColumn) {
        this.tower = new Tower();
        oriImageIcon = new ImageIcon(imagePath);
        currentIcon = oriImageIcon;
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

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public boolean hasWorker(){
        return worker != null;
    }


    public Tower getTower() {
        return tower;
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
        }
    }

    @Override
    public String toString() {
        return "Tile Position: (" + tileRow + ", " + tileColumn + ")";
    }
}
