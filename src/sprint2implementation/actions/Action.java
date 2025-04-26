package sprint2implementation.actions;

import sprint2implementation.grounds.Board;

public abstract class Action {

    protected int row;
    protected int column;

    public Action( int row, int column) {
        this.row = row;
        this.column = column;

    }
    public abstract void execute();
}
