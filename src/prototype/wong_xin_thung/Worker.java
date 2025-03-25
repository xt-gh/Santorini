package prototype.wong_xin_thung;


public class Worker {
    private int row;
    private int col;
    private Player player;

    public Worker(Player player){
        this.player = player;
    }

    public Player getPlayer(){
        return player;
    }
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public String toString(){
        return player.getName();
    }
}
