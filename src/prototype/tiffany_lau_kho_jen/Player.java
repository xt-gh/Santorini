package prototype.tiffany_lau_kho_jen;

import javax.swing.*;
import java.awt.*;

public class Player extends JLabel {
    private int row;
    private int column;
    private boolean isSelected = false;
    private ImageIcon[] spritesOnLevel;
    private ImageIcon defaultSprite;
    private int currentLevel = 0;

    public Player(String defaultSpritePath, String[] spritePaths, int startRow, int startCol) {
        this.row = startRow;
        this.column = startCol;
        this.defaultSprite = scaleImage(defaultSpritePath, 80, 80);
        loadSprites(spritePaths);
        updateSprite();
        setSize(80, 80);
    }

    private void loadSprites(String[] spritePaths) {
        spritesOnLevel = new ImageIcon[spritePaths.length];
        for (int i = 0; i < spritePaths.length; i++) {
            spritesOnLevel[i] = scaleImage(spritePaths[i], 100, 100);
        }
    }

    private ImageIcon scaleImage(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(path);
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public void setPosition(int newRow, int newColumn, int level) {
        this.row = newRow;
        this.column = newColumn;
        this.currentLevel = level;
        updateSprite();
    }

    private void updateSprite() {
        if (currentLevel == 0) {
            setIcon(defaultSprite);
        }
        else {
            setIcon(spritesOnLevel[currentLevel - 1]);
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void selectPlayer() {
        isSelected = true;
        setBorder(BorderFactory.createLineBorder(new Color(217, 158, 29), 3));
    }

    public void deselectPlayer() {
        isSelected = false;
        setBorder(null);
    }

    public boolean isSelected() {
        return isSelected;
    }

}






