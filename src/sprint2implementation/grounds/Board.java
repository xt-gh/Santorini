package sprint2implementation.grounds;

import sprint2implementation.characters.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Design pattern: Singleton.
// Singleton is used as the design pattern for the Board class because only one board instance is needed for the game.

/**
 * Represents the game board for the game.
 * Only one instance of the board can exist at a time.
 * The board is a grid of link {@link Tile} objects used to place players and perform game actions.
 *
 * @author Yee Peen
 * Modified by: Tiffany
 */
public class Board extends JPanel {

    /**
     * The single instance of the board (Singleton).
     */
    private static Board instance;

    /**
     * The list of all tiles on the board.
     */
    private List<Tile> tiles;

    /**
     * The total number of tiles (board size).
     */
    private int boardSize;

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
     * @param boardSize the total size of teh board (number of tiles)
     * @param boardRows the number of rows
     * @param boardColumns the number of columns
     */
    private Board(int boardSize, int boardRows, int boardColumns)
    {
        setBoardSize(boardSize);
        setBoardRows(boardRows);
        setBoardColumns(boardColumns);
        tiles = new ArrayList<>();
        GridLayout boardGrid = new GridLayout(this.boardRows, this.boardColumns,2,2);
        setLayout(boardGrid);
        setTileImageIcon();

    }


    /**
     * Returns the singleton instance of the board.
     * If it does not exist yet, it will be created.
     *
     * @param boardSize the total board size
     * @param boardRows the number of rows
     * @param boardColumns the number of columns
     *
     * @return the singleton board instance
     */
    public static Board getInstance(int boardSize, int boardRows, int boardColumns) {
        if (instance == null) {
            instance = new Board(boardSize, boardRows, boardColumns);
        }
        return instance;
    }

    /**
     * Gets the total board size.
     *
     * @return the board size
     */
    public int getBoardSize() {
        return boardSize;
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


    // Helper to convert row/column to list index
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

    // Get a tile based on row and column
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
     * Sets the board size.
     *
     * @param boardSize the total number of tiles
     */
    private void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

//     Place the player on a specific tile (row, column)
    /**
     * Places a player on the board at the specified tile location.
     *
     * @param row the row index
     * @param column the column index
     * @param player the player to place
     */
    public void placePlayerOnTile(int row, int column, Player player) {
        if (row >= 0 && row < boardRows && column >= 0 && column < boardColumns) {
            Tile tile = getTileLocation(row, column);
            // Update the tile with the player's icon
            tile.updateIcon(player.getPlayerIcon());
        }
    }


    /**
     * Sets the number of rows for the board.
     *
     * @param boardRows the number of rows
     */
    private void setBoardRows(int boardRows) {
        this.boardRows = boardRows;
    }

    /**
     * Sets the number of columns for the board.
     *
     * @param boardColumns the number of columns
     */
    private void setBoardColumns(int boardColumns) {
        this.boardColumns = boardColumns;
    }

    // Set up all the tiles and add them to the board
    /**
     * Initializes the tiles on the board and sets their icons.
     * Adds each tile to the board layout.
     */
    private void setTileImageIcon() {
        for (int currentRow = 0; currentRow < boardRows; currentRow++) {
            for (int currentColumn = 0; currentColumn < boardColumns; currentColumn++) {
                Tile tile = new Tile(Board.class.getResource("/pics/Empty_tile.png"), currentRow, currentColumn);
                // Store tile
                tiles.add(tile);
                add(tile);
            }
        }
    }

}
