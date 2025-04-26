package sprint2implementation.actions;

import sprint2implementation.grounds.Board;
import sprint2implementation.grounds.Tile;
import sprint2implementation.towers.Tower;
import sprint2implementation.characters.Worker;

import javax.swing.*;

public class MoveAction extends Action {
    private Tile fromTile;
    private Tile toTile;
    private Worker worker;
    private boolean moveSuccessful;

    public MoveAction(Board gameBoard, Tile fromTile, Tile toTile, Worker worker) {
        super(gameBoard, fromTile.getTileRow(), fromTile.getTileColumn());
        this.fromTile = fromTile;
        this.toTile = toTile;
        this.worker = worker;
        this.moveSuccessful = validateMove();
    }

    private boolean validateMove() {

        if (fromTile == toTile) {
            return false;
        }

        if (toTile.getWorker() != null) {
            return false;
        }

        int fromLevel = getTowerLevel(fromTile.getTower());
        int toLevel = getTowerLevel(toTile.getTower());

        Tower toTower = toTile.getTower();
        if ((toTower != null && toTower.hasDome()) || (toLevel - fromLevel > 1))
        {
            return false;
        }

        return true;
    }

    @Override
    public void execute() {
        if (moveSuccessful) {

            Tower fromTower = fromTile.getTower();
            Tower toTower = toTile.getTower();

            fromTile.setWorker(null);

            if (fromTower == null)
            {
                fromTile.updateIcon(null);
            }
            else
            {
                fromTile.updateIcon(fromTower.getCurrentIcon());
            }

            toTile.setWorker(worker);
            worker.setPosition(toTile.getTileRow(), toTile.getTileColumn());
            System.out.println("Current worker position: " + worker.getPosition());

            if (toTower == null || !toTower.hasDome()) {
                int toLevel = getTowerLevel(toTower);

                ImageIcon workerOnTower = worker.getPlayer().getPlayerPositionTower().getOrDefault(toLevel, worker.getPlayer().getPlayerIcon());

                toTile.updateIcon(workerOnTower);
            }
        }
    }

    public boolean isMoveSuccessful() {
        return moveSuccessful;
    }

    private int getTowerLevel(Tower tower) {
        return (tower != null) ? tower.getLevelCount() : 0;
    }
}

