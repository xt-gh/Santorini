package sprint2implementation;

public class BuildAction extends Action {
    private Tile targetTile;
    private Tower tower;
    private boolean buildSuccessful;

    public BuildAction(Board gameBoard, Tile targetTile, Tower tower) {
        super(gameBoard, targetTile.getTileRow(), targetTile.getTileColumn());
        this.targetTile = targetTile;
        this.tower = tower;
        this.buildSuccessful = validateBuild();
    }

    public boolean validateBuild() {

        // Prevents building if the worker is already standing on a tile or a tower already has a dome
        if (targetTile.getWorker() != null || tower.hasDome()) {
            return false;
        }

        // Allows building only if the current tower level has not exceeded the maximum level
        return tower.getLevelCount() <= Tower.getMaxLevels();
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

