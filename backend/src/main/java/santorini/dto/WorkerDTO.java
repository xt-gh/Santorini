package santorini.dto;

/**
 * DTO representing a worker.
 */
public class WorkerDTO {

    private int row;
    private int col;
    private boolean isStuck;

    public WorkerDTO() {}

    public WorkerDTO(int row, int col, boolean isStuck) {
        this.row = row;
        this.col = col;
        this.isStuck = isStuck;
    }

    // Getters and Setters

    public int getRow() { return row; }
    public void setRow(int row) { this.row = row; }

    public int getCol() { return col; }
    public void setCol(int col) { this.col = col; }

    public boolean isStuck() { return isStuck; }
    public void setStuck(boolean stuck) { isStuck = stuck; }
}
