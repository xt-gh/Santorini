package sprint2implementation.characters;

import sprint2implementation.actions.BuildAction;
import sprint2implementation.actions.MoveAction;
import sprint2implementation.grounds.Tile;
import sprint2implementation.towers.Tower;

public class Worker {
    private Player player;
    private int row;
    private int col;
    private boolean booleanStuck;
    private boolean isMoving = false;


    public Worker(Player player) {
        this.player = player;
        setBooleanStuck(false);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPosition(int row, int col){
        this.row = row;
        this.col = col;
    }

    public boolean isStuck()
    {
        return booleanStuck;
    }

    public void setBooleanStuck(boolean booleanStuck) {
        this.booleanStuck = booleanStuck;
    }


    public boolean executeAction(Player currentPlayer, Tile selectedTile, Tile clickedTile, Tower tower)
    {
        // execution of regular move and build actions
        if (currentPlayer.getGodCard() == null)
        {
            MoveAction moveAction = new MoveAction(selectedTile, clickedTile, this);
            moveAction.execute();

            if (moveAction.isMoveSuccessful())
            {
                isMoving = true;
                BuildAction buildAction = new BuildAction(clickedTile, tower);
                buildAction.execute();

                if (buildAction.isBuildSuccessful())
                {
                    currentPlayer.setActionSuccessful(true);
                }
            }
        }
        else { // special god cards execution of their special abilities
            isMoving = currentPlayer.getGodCard().executeSpecialAbility(currentPlayer, selectedTile, clickedTile, tower);
        }
        return isMoving;
    }
}

