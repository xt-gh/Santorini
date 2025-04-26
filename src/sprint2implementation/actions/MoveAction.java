package sprint2implementation.actions;

import sprint2implementation.grounds.Tile;
import sprint2implementation.towers.Tower;
import sprint2implementation.characters.Worker;

import javax.swing.*;

public class MoveAction extends Action {
    private Tile fromTile;
    private Tile toTile;
    private Worker worker;
    private boolean moveSuccessful;

    public MoveAction(Tile fromTile, Tile toTile, Worker worker) {
        super(fromTile.getTileRow(), fromTile.getTileColumn());
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
                checkWinningCondition(worker,toTile);
            }
        }
    }

    public boolean isMoveSuccessful() {
        return moveSuccessful;
    }

    private int getTowerLevel(Tower tower) {
        return (tower != null) ? tower.getLevelCount() : 0;
    }

    private void checkWinningCondition(Worker worker, Tile toTile) {
        // Check if the worker is on a tower and the tower has reached level 3
        Tower toTower = toTile.getTower();
        if (toTower != null && toTower.getLevelCount() == Tower.getMaxLevels() && !toTower.hasDome()) {
            JOptionPane.showMessageDialog(null, worker.getPlayer().getName() + " WINS!!", "Tournament Result", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
}

