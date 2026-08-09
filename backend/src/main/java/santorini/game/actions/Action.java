package santorini.game.actions;


/**
 * Represent an abstract action that occurs at a specific position
 * defined by a row and column on a board or grid.
 * @author Pee Yee Peen
 */
public abstract class Action {


    /**
     * The row index where the action occurs
     */
    protected int row;


    /**
     * The column index where the action occurs
     */
    protected int column;


    /**
     * Constructs an Action with the specified row and column position
     *
     * @param row the row index of the action
     * @param column the column index of the action
     */
    public Action( int row, int column) {
        this.row = row;
        this.column = column;
    }


    /**
     * Perform the Action
     */
    public abstract void execute();

}
