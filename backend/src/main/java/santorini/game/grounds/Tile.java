package santorini.game.grounds;

import santorini.game.characters.Worker;
import santorini.game.towers.Tower;

/**
 * Represents a single tile on the game board.
 * A tile may hold a tower and/or a worker.
 *
 * Refactored: Removed extends JButton and all ImageIcon/rendering logic.
 * The frontend handles all visual representation.
 *
 * @author Yee Peen
 * Modified by: Xin Thung, Tiffany
 */
public class Tile {

    /**
     * The tower located on this tile.
     */
    private Tower tower;

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
     * Constructs a Tile object with the given position.
     *
     * @param tileRow the row index of the tile
     * @param tileColumn the column index of the tile
     */
    public Tile(int tileRow, int tileColumn) {
        this.tower = new Tower();
        this.tileRow = tileRow;
        this.tileColumn = tileColumn;
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


    public void setWorker(Worker worker) {
        this.worker = worker;
        if (worker != null) {
            worker.setCurrentTile(this);
        }
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
     * Returns a string representation of the tile's position.
     *
     * @return a string indicating the tile's coordinates
     */
    @Override
    public String toString() {
        return "Tile Position: (" + tileRow + ", " + tileColumn + ")";
    }
}
