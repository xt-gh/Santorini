package santorini.dto;

/**
 * DTO representing a single tile on the board.
 */
public class TileDTO {

    private int row;
    private int col;
    private int towerLevel;      // 0-3
    private boolean hasDome;
    private boolean hasWorker;
    private int workerOwnerIndex;  // -1 if no worker, otherwise 0 or 1
    private String workerOwnerName;

    public TileDTO() {}

    public TileDTO(int row, int col, int towerLevel, boolean hasDome,
                   boolean hasWorker, int workerOwnerIndex, String workerOwnerName) {
        this.row = row;
        this.col = col;
        this.towerLevel = towerLevel;
        this.hasDome = hasDome;
        this.hasWorker = hasWorker;
        this.workerOwnerIndex = workerOwnerIndex;
        this.workerOwnerName = workerOwnerName;
    }

    // Getters and Setters

    public int getRow() { return row; }
    public void setRow(int row) { this.row = row; }

    public int getCol() { return col; }
    public void setCol(int col) { this.col = col; }

    public int getTowerLevel() { return towerLevel; }
    public void setTowerLevel(int towerLevel) { this.towerLevel = towerLevel; }

    public boolean isHasDome() { return hasDome; }
    public void setHasDome(boolean hasDome) { this.hasDome = hasDome; }

    public boolean isHasWorker() { return hasWorker; }
    public void setHasWorker(boolean hasWorker) { this.hasWorker = hasWorker; }

    public int getWorkerOwnerIndex() { return workerOwnerIndex; }
    public void setWorkerOwnerIndex(int workerOwnerIndex) { this.workerOwnerIndex = workerOwnerIndex; }

    public String getWorkerOwnerName() { return workerOwnerName; }
    public void setWorkerOwnerName(String workerOwnerName) { this.workerOwnerName = workerOwnerName; }
}
