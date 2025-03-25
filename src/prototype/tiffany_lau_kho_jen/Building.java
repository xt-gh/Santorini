package prototype.tiffany_lau_kho_jen;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Building {
    private Map<Point, Integer> buildingLevels = new HashMap<>();
    private ImageIcon[] buildingImages;
    private int tileSize;

    public Building(int tileSize) {
        this.tileSize = tileSize;
        loadBuildingImages();
        initializeBuildings();
    }

    private void initializeBuildings() {
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 5; column++) {
                Point tile = new Point(column, row);
                buildingLevels.put(tile, 0);
            }
        }
    }

    public boolean canBuild(Point tile, Player playerOne, Player playerTwo) {
        if ((playerOne.getRow() == tile.y && playerOne.getColumn() == tile.x) ||
                (playerTwo.getRow() == tile.y && playerTwo.getColumn() == tile.x)) {
            return false;
        }

        int currentLevel = buildingLevels.getOrDefault(tile, 0);
        if (currentLevel >= 4)
        {
            return false;
        }

        return true;
    }

    public void build(Point tile, Player playerOne, Player playerTwo) {
        if (canBuild(tile, playerOne, playerTwo)) {
            int currentLevel = buildingLevels.getOrDefault(tile, 0);
            buildingLevels.put(tile, currentLevel + 1);
        }
    }

    public int getBuildingLevel(Point tile) {
        return buildingLevels.getOrDefault(tile, 0);
    }


    private void loadBuildingImages() {
        buildingImages = new ImageIcon[5];
        int buildingWidth = (int) (tileSize * 0.75);
        int buildingHeight = (int) (tileSize * 0.75);

        for (int i = 1; i <= 4; i++) {
            buildingImages[i] = scaleImage("src/prototype/tiffany_lau_kho_jen/pictures/Lvl" + i + "building.png", buildingWidth, buildingHeight);
        }
    }

    private ImageIcon scaleImage(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(path);
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public void drawBuildings(Graphics graphic, int tileSize, Player playerOne, Player playerTwo) {
        for (Map.Entry<Point, Integer> entry : buildingLevels.entrySet()) {
            Point tile = entry.getKey();
            int level = entry.getValue();

            if (level > 0) {
                ImageIcon buildingImage = buildingImages[level];

                boolean isPlayerOneExist = (playerOne.getRow() == tile.y && playerOne.getColumn() == tile.x);
                boolean isPlayerTwoExist = (playerTwo.getRow() == tile.y && playerTwo.getColumn() == tile.x);

                if (!isPlayerOneExist && !isPlayerTwoExist) {
                    int x = (tile.x * tileSize) + (tileSize / 2 - buildingImage.getIconWidth() / 2);
                    int y = (tile.y * tileSize) + (tileSize / 2 - buildingImage.getIconHeight() / 2);

                    graphic.drawImage(buildingImage.getImage(), x, y,
                    buildingImage.getIconWidth(), buildingImage.getIconHeight(), null);
                }
            }
        }
    }
}






