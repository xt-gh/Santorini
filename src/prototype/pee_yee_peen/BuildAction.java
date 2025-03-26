package prototype.pee_yee_peen;

public class BuildAction extends Action {
    private Tile targetTile;
    private Tower tower;
    private int currentLevel;
    private boolean buildSuccessful;

    public BuildAction(Board gameBoard, Tile targetTile, Tower tower) {
        super(gameBoard, targetTile.getTileRow(), targetTile.getTileColumn());
        currentLevel = tower.getLevel();
        this.targetTile = targetTile;
        this.tower = tower;
        this.buildSuccessful = validateBuild();
    }

    public boolean validateBuild() {
        if (targetTile.getPlayer() != null || targetTile.getTower().isHasDome()) {
            return false;
        }

        // Check tower level limits
        if (tower.getLevel() >= 4) {
            return false;
        }

        return true;
    }

    @Override
    public void execute() {
        if (buildSuccessful) {
            tower.increaseLevel();
            currentLevel = tower.getLevel();
            targetTile.setTower(tower);

            targetTile.updateIcon(tower.getTowerIcon(currentLevel));
        }
    }

    public boolean isBuildSuccessful() {
        return buildSuccessful;
    }
}
