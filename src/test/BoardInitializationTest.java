package test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sprint2implementation.grounds.Board;
import sprint2implementation.grounds.Tile;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardInitializationTest {
    // Test case 1.1
    private Board gameBoard;

    @BeforeEach
    void setUp() {
        gameBoard = Board.getInstance(600, 5, 5);
    }

    @Test
    void testBoardInitialization() {
        assertEquals(5, gameBoard.getBoardRows(), "Board should have 5 rows.");
        assertEquals(5, gameBoard.getBoardColumns(), "Board should have 5 columns.");

        for (int row = 0; row < gameBoard.getBoardRows(); row++) {
            for (int col = 0; col < gameBoard.getBoardColumns(); col++) {
                Tile tile = gameBoard.getTileLocation(row, col);
                assertNotNull(String.valueOf(tile), String.format("Tile at (%d,%d) should not be null.", row, col));
                assertEquals(row, tile.getTileRow(), "Tile row should match.");
                assertEquals(col, tile.getTileColumn(), "Tile column should match.");
            }
        }
    }
}