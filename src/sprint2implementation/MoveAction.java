package sprint2implementation;

import javax.swing.*;

public class MoveAction extends Action {
    private Tile fromTile;
    private Tile toTile;
//    private Player player;
    private Worker worker;
    private boolean moveSuccessful;

    public MoveAction(Board gameBoard, Tile fromTile, Tile toTile, Worker worker) {
        super(gameBoard, fromTile.getTileRow(), fromTile.getTileColumn());
        this.fromTile = fromTile;
        this.toTile = toTile;
        this.worker = worker;
//        this.player = worker.getPlayer();
        this.moveSuccessful = validateMove();
    }

    private boolean validateMove() {
        // Check if moving to same tile
        if (fromTile == toTile) {
            return false;
        }

        // Check if target tile has another player
        if (toTile.getWorker() != null && toTile.getWorker().getPlayer() != worker.getPlayer()) {
            return false;
        }

        Tower targetTower = toTile.getTower();
        Tower currentTower = fromTile.getTower();
        if (targetTower != null && (targetTower.isHasDome() || (targetTower.getLevel() - currentTower.getLevel() > 1)))
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
}