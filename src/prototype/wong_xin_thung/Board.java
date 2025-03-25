package prototype.wong_xin_thung;

import java.awt.*;
import javax.swing.*;


public class Board extends JPanel{
    private static final int SIZE = 5;
    private Cell[][] grid;
    private JButton[][] boardButtons;
    private Tower tower;

    public Board(int boardSize) {
        setLayout(new GridLayout(SIZE, SIZE));
        setPreferredSize(new Dimension(boardSize, boardSize));

        grid = new Cell[SIZE][SIZE];
        boardButtons = new JButton[SIZE][SIZE];

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                grid[row][col] = new Cell();
                boardButtons[row][col] = new JButton();
                boardButtons[row][col].setFont(new Font("Arial", Font.BOLD, 20));
                boardButtons[row][col].setOpaque(true);
                boardButtons[row][col].setContentAreaFilled(true);  // Set to false if background is not showing properly
                boardButtons[row][col].setBorderPainted(true);
                boardButtons[row][col].setBackground(Color.lightGray); // Change the background color
                add(boardButtons[row][col]); // Add buttons to the board
            }
        }
    }

    public JButton getButton(int row, int col) {
        return boardButtons[row][col];
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public Worker getWorkerAt( int row, int col){
        return grid[row][col].getWorker();
    }

public boolean moveWorker(Worker worker, int row, int col) {
    for (int r = 0; r < 5; r++) {
        for (int c = 0; c < 5; c++) {

            // check if the moving position is adjacent to worker current position and is not occupied by anything
            if (grid[r][c].getWorker() == worker && isAdjacent(r, c, row, col) && grid[row][col].getWorker() == null && !grid[row][col].isOccupied()) {
                // Remove worker from current position
                grid[r][c].setWorker(null);

                // Move worker to new position
                grid[row][col].setWorker(worker);

                // Update worker's internal position
                worker.setPosition(row, col);

                return true;
            }
        }
    }
    return false;
}


    public boolean buildTower(Worker worker, int row, int col) {
        // check if there is a worker, if no --> cannot build
        if (worker == null){
            return false;
        }

        // check the target position is adjacent to the worker
        if (!isAdjacent(worker.getRow(), worker.getCol(), row, col)){
            return false;
        }

        return grid[row][col].buildTowerOnCell();
    }

    // initialize the worker on the board
    public void placeInitialWorkers(Player p1, Player p2) {
        Worker w1 = new Worker(p1);
        w1.setPosition(0, 0);
        grid[0][0].setWorker(w1);

        Worker w2 = new Worker(p1);
        w2.setPosition(0, 1);
        grid[0][1].setWorker(w2);

        Worker w3 = new Worker(p2);
        w3.setPosition(4, 4);
        grid[4][4].setWorker(w3);

        Worker w4 = new Worker(p2);
        w4.setPosition(4, 3);
        grid[4][3].setWorker(w4);
    }


    // check if the player can move or not
    public boolean canMove(Player player) {
        // iterate every row can col of board based on the SIZE
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Worker worker = grid[row][col].getWorker();
                if (worker != null && worker.getPlayer() == player) {

                    // loops all 8 adjacent positions ( up, down, left, right, diagonals)
                    for (int adjacentRow = -1; adjacentRow <= 1; adjacentRow++) {
                        for (int adjacentColumn = -1; adjacentColumn <= 1; adjacentColumn++) {

                            // calculate the new position
                            int newRow = row + adjacentRow;
                            int newCol = col + adjacentColumn;

                            // check worker can move to the new position or not
                            if (isValidMove(worker, newRow, newCol)) {
                                return true;    // at least one worker can move
                            }
                        }
                    }
                }
            }
        }
        return false;   // all workers are stuck
    }

    // to determine whether the worker can move to a specific cell or not
    private boolean isValidMove(Worker worker, int newRow, int newCol) {
        // invalid: if the new position is outside the board
        // newRow < 0: out of bounds above
        // newRow > SIZE: out of bounds below
        // newCol < 0: out of bounds left
        // newCol > SIZE: out of bounds right
        if (newRow < 0 || newRow >= SIZE || newCol < 0 || newCol >= SIZE) {
            return false;
        }
        // invalid: if the cell is occupied()
        if (grid[newRow][newCol].isOccupied()) {
            return false;
        }
        return true;
    }

    public boolean checkWinCondition(Player player) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                Worker worker = grid[row][col].getWorker();

                // check if the worker is on level 3
                if (worker != null && worker.getPlayer() == player && grid[row][col].getTowerLevel() == 3) {
                    return true;
                }
            }
        }
        return false;
    }

    private  boolean isAdjacent(int r1, int c1, int r2, int c2){
        return Math.abs(r1 - r2) <= 1 && Math.abs(c1 - c2) <= 1;
    }

}
