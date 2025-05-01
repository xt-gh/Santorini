package sprint2implementation.grounds;

import sprint2implementation.characters.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Design pattern: Singleton.
// Singleton is used as the design pattern for the Board class because only one board instance is needed for the game.
public class Board extends JPanel {

    private static Board instance;
    private List<Tile> tiles;
    private int boardSize;
    private int boardRows;
    private int boardColumns;

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


    public static Board getInstance(int boardSize, int boardRows, int boardColumns) {
        if (instance == null) {
            instance = new Board(boardSize, boardRows, boardColumns);
        }
        return instance;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getBoardRows() {
        return boardRows;
    }

    public int getBoardColumns() {
        return boardColumns;
    }


    // Helper to convert row/column to list index
    private int getTileIndex(int row, int column) {
        return row * boardColumns + column;
    }

    // Get a tile based on row and column
    public Tile getTileLocation(int row, int column) {
        if (row < 0 || row >= boardRows || column < 0 || column >= boardColumns)
        {
            return null;
        }
        return tiles.get(getTileIndex(row, column));
    }

    private void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

//     Place the player on a specific tile (row, column)
    public void placePlayerOnTile(int row, int column, Player player) {
        if (row >= 0 && row < boardRows && column >= 0 && column < boardColumns) {
            Tile tile = getTileLocation(row, column);
            // Update the tile with the player's icon
            tile.updateIcon(player.getPlayerIcon());
        }
    }


    private void setBoardRows(int boardRows) {
        this.boardRows = boardRows;
    }

    private void setBoardColumns(int boardColumns) {
        this.boardColumns = boardColumns;
    }

    // Set up all the tiles and add them to the board
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
