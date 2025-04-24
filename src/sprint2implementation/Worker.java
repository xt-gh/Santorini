package sprint2implementation;


import java.util.List;

public class Worker {
    private int row;
    private int col;
    private Player player;
    private boolean booleanStuck;

    public Worker(Player player) {
        this.player = player;
        setBooleanStuck(false);
    }

    public Player getPlayer() {
        return player;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setPosition(int row, int col){
        this.row = row;
        this.col = col;

    }

    public String getPosition()
    {
        return "(" + row + "," + col + ")";
    }

    public String toString(){
        return "Worker at position: " + getPosition();
    }

    public boolean isStuck()
    {
        return booleanStuck;
    }

    public void setBooleanStuck(boolean booleanStuck) {
        this.booleanStuck = booleanStuck;
    }
}

