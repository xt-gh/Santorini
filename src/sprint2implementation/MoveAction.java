package sprint2implementation;

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

        // Check if target tile has another player
        if (toTile.getWorker() != null && toTile.getWorker().getPlayer() != worker.getPlayer()) {
            return false;
        }

        // Get tower levels, defaulting to 0 if no tower exists
        int fromLevel = getTowerLevel(fromTile.getTower());
        int toLevel = getTowerLevel(toTile.getTower());

        // Prevent moving onto a tower that already has a dome or climbing up more than 1 level
        Tower toTower = toTile.getTower();
        if ((toTower != null && toTower.hasDome()) || (toLevel - fromLevel > 1))
        {
            return false;
        }

        return true;
    }

    @Override
    public void execute() {
        Tower toTileTower = toTile.getTower();
        Tower fromTileTower = fromTile.getTower();

        if (moveSuccessful) {
            fromTile.setWorker(null);
            if (fromTileTower == null)
            {
                fromTile.updateIcon(null);
            }
            else {
                fromTile.updateIcon(fromTileTower.getTowerLevels().get(fromTileTower.getLevel()));
            }

            if (toTileTower == null)
            {
                toTile.updateIcon(worker.getPlayer().getPlayerIcon());
            }
            else {
                ImageIcon playerOnTower = worker.getPlayer().getPlayerPositionTower().get(toTileTower.getLevel());
                toTile.updateIcon(playerOnTower);
            }
            toTile.setWorker(worker);

            if (toTileTower.getLevel() == 3)
            {
                JOptionPane.showMessageDialog(null, "WIN!!", "Tournament Result", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
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
