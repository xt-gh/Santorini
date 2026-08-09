package santorini.game.grounds;

import santorini.game.characters.Worker;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a singleton game board for the game.
 * The board is a grid of {@link Tile} objects used to place players and perform game actions.
 *
 * Refactored: Removed extends JPanel, GridLayout, and ImageIcon dependencies.
 * The frontend handles all visual representation of the board.
 *
 * @author Yee Peen
 * Modified by: Tiffany
 */
public class Board {

    /**
     * The single instance of the board (Singleton).
     */
    private static Board instance;

    /**
     * The list of all tiles on the board.
     */
    private List<Tile> tiles;

    /**
     * The number of rows in the board.
     */
    private int boardRows;

    /**
     * The number of columns in the board.
     */
    private int boardColumns;


    /**
     * Private constructor to enforce Singleton pattern.
     *
     * @param boardRows the number of rows
     * @param boardColumns the number of columns
     */
    private Board(int boardRows, int boardColumns)
    {
        this.boardRows = boardRows;
        this.boardColumns = boardColumns;
        tiles = new ArrayList<>();
        initializeTiles();
    }


    /**
     * Creates and returns a new singleton instance of the board.
     * If an instance already exists, it is replaced (supports game restart).
     *
     * @param boardRows the number of rows
     * @param boardColumns the number of columns
     *
     * @return the new Board instance
     */
    public static Board createInstance(int boardRows, int boardColumns) {
        instance = new Board(boardRows, boardColumns);
        return instance;
    }

    /**
     * Returns the existing singleton instance of the board.
     *
     * @return the singleton board instance, or null if not created
     */
    public static Board getInstance() {
        return instance;
    }

    /**
     * Resets the singleton instance (for game restart).
     */
    public static void resetInstance() {
        instance = null;
    }

    /**
     * Gets the number of rows in the board.
     *
     * @return the number of rows
     */
    public int getBoardRows() {
        return boardRows;
    }

    /**
     * Gets the number of columns in the board.
     *
     * @return the number of columns
     */
    public int getBoardColumns() {
        return boardColumns;
    }


    /**
     * Converts a row and column to a 1D list index for the tile list.
     *
     * @param row the row index
     * @param column the column index
     *
     * @return the index in the tile list
     */
    private int getTileIndex(int row, int column) {
        return row * boardColumns + column;
    }

    /**
     * Retrieves a tile at the specified location.
     *
     * @param row the row index
     * @param column the column index
     *
     * @return the {@link Tile} at that location, or null if out of bounds
     */
    public Tile getTileLocation(int row, int column) {
        if (row < 0 || row >= boardRows || column < 0 || column >= boardColumns)
        {
            return null;
        }
        return tiles.get(getTileIndex(row, column));
    }

    /**
     * Returns the list of all tiles on the board.
     *
     * @return list of tiles
     */
    public List<Tile> getTiles() {
        return tiles;
    }

    /**
     * Initializes the tiles on the board.
     */
    private void initializeTiles() {
        for (int currentRow = 0; currentRow < boardRows; currentRow++) {
            for (int currentColumn = 0; currentColumn < boardColumns; currentColumn++) {
                Tile tile = new Tile(currentRow, currentColumn);
                tiles.add(tile);
            }
        }
    }
}
