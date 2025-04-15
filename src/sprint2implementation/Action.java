package sprint2implementation;

public abstract class Action {

    protected Board gameBoard;
    protected int row;
    protected int column;

    public Action(Board gameBoard, int row, int column) {
        this.gameBoard = gameBoard;
        this.row = row;
        this.column = column;

    }
    public abstract void execute();
}
