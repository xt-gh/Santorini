package sprint2implementation;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {

    private Tile[][] tileLocation;
    private int boardSize;
    private int boardRows;
    private int boardColumns;

    public Board(int boardSize, int boardRows, int boardColumns)
    {
        setBoardSize(boardSize);
        setBoardRows(boardRows);
        setBoardColumns(boardColumns);
        tileLocation = new Tile[boardRows][boardColumns];
        GridLayout boardGrid = new GridLayout(this.boardRows, this.boardColumns,2,2);
        setLayout(boardGrid);
        setTileImageIcon();

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

    public Tile getTileLocation(int row, int column) {
        return tileLocation[row][column];
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    // Place the player on a specific tile (row, column)
    public void placePlayerOnTile(int row, int column, Player player) {
        if (row >= 0 && row < boardRows && column >= 0 && column < boardColumns) {
            // Update the tile with the player's icon
            tileLocation[row][column].updateIcon(player.getPlayerIcon());
        }
    }

    public void setBoardRows(int boardRows) {
        this.boardRows = boardRows;
    }

    public void setBoardColumns(int boardColumns) {
        this.boardColumns = boardColumns;
    }

    public void setTileImageIcon() {
        for (int currentRow = 0; currentRow < boardRows; currentRow++) {
            for (int currentColumn = 0; currentColumn < boardColumns; currentColumn++) {
                Tile tile = new Tile("src/sprint2implementation/pics/Empty_tile.png", currentRow, currentColumn);

                tileLocation[currentRow][currentColumn] = tile; // Store tile
                add(tile);
            }
        }
    }




}
