package prototype.pee_yee_peen;

import javax.swing.*;

public class MoveAction extends Action {
    private Tile fromTile;
    private Tile toTile;
    private Player player;
    private boolean moveSuccessful;

    public MoveAction(Board gameBoard, Tile fromTile, Tile toTile, Player player) {
        super(gameBoard, fromTile.getTileRow(), fromTile.getTileColumn());
        this.fromTile = fromTile;
        this.toTile = toTile;
        this.player = player;
        this.moveSuccessful = validateMove();
    }

    private boolean validateMove() {
        // Check if moving to same tile
        if (fromTile == toTile)
        {
            return false;
        }

        // Check if target tile has another player
        if (toTile.getPlayer() != null && toTile.getPlayer() != player) {
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
            fromTile.setPlayer(null);
            if (fromTileTower == null)
            {
                fromTile.updateIcon(null);
            }
            else {
                fromTile.updateIcon(fromTileTower.getTowerLevels().get(fromTileTower.getLevel()));
            }

            if (toTileTower == null)
            {
                toTile.updateIcon(player.getPlayerIcon());
            }
            else {
                ImageIcon playerOnTower = player.getPlayerPositionTower().get(toTileTower.getLevel());
                toTile.updateIcon(playerOnTower);
            }
            toTile.setPlayer(player);

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