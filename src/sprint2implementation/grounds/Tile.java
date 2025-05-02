package sprint2implementation.grounds;

import sprint2implementation.characters.Worker;
import sprint2implementation.towers.Tower;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Represents a single tile on the game board.
 * A tile may hold a tower and/or a worker and displays an image icon.
 *
 * @author Yee Peen
 * Modified by: Xin Thung, Tiffany
 */
public class Tile extends JButton {

    /**
     * The tower located on this tile.
     */
    private Tower tower;

    /**
     * The original image icon of the tile
     */
    private ImageIcon oriImageIcon;

    /**
     * The current image icon displayed on the tile.
     */
    private ImageIcon currentIcon;

    /**
     * The row position of the tile on the board.
     */
    private int tileRow;

    /**
     * The column position of the tile on the board.
     */
    private int tileColumn;

    /**
     * The worker currently placed on this tile.
     */
    private Worker worker;

    /**
     * Constructs a Tile object with the given image and position.
     *
     * @param imagePath the URL of the tile's image
     * @param tileRow the row index of the tile
     * @param tileColumn the column index of the tile
     */
    public Tile(URL imagePath, int tileRow, int tileColumn) {
        this.tower = new Tower();
        oriImageIcon = new ImageIcon(imagePath);
        currentIcon = oriImageIcon;
        this.tileRow = tileRow;
        this.tileColumn = tileColumn;

        resizeAndSetIcon();
    }

    /**
     * Updates the icon of the tile.
     *
     * @param icon the new icon to display, or null to reset to original
     */
    public void updateIcon(ImageIcon icon) {
        if (icon != null) {
            currentIcon = icon;
        } else {
            currentIcon = oriImageIcon;
        }
        resizeAndSetIcon();
    }
    /**
     * Gets the row index of the tile.
     *
     * @return the tile's row index
     */
    public int getTileRow() {
        return tileRow;
    }

    /**
     * Gets the column index of the tile.
     *
     * @return the tile's column index
     */
    public int getTileColumn() {
        return tileColumn;
    }

    /**
     * Gets the worker currently on the tile.
     *
     * @return the {@link Worker} on the tile, or null if none
     */
    public Worker getWorker() {
        return worker;
    }

    /**
     * Sets a worker on the tile.
     *
     * @param worker the worker to place on this tile
     */
    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    /**
     * Checks if a worker is present on the tile.
     *
     * @return true if a worker is on the tile, otherwise false
     */
    public boolean hasWorker(){
        return worker != null;
    }

    /**
     * Gets the tower associated with this tile.
     *
     * @return the {@link Tower} on the tile
     */
    public Tower getTower() {
        return tower;
    }

    /**
     * Sets a tower on the tile.
     *
     * @param tower the {@link Tower} to associate with the tile
     */
    public void setTower(Tower tower) {
        this.tower = tower;
    }


    /**
     * Overrides the bounds setting to automatically resize the icon when the tile's size changes.
     *
     * @param x the new x position
     * @param y the new y position
     * @param width the new width
     * @param height the new height
     */
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        resizeAndSetIcon();
    }

    /**
     * Resizes and sets the icon based on the current size of the tile.
     */
    private void resizeAndSetIcon() {
        if (currentIcon != null && getWidth() > 0 && getHeight() > 0) {
            Image scaledImage = currentIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(scaledImage));
        }
    }

    /**
     * Returns a string representation of the tile's position.
     *
     * @return a string indicating the tile's coordinates
     */
    @Override
    public String toString() {
        return "Tile Position: (" + tileRow + ", " + tileColumn + ")";
    }
}
