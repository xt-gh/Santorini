package prototype.wong_xin_thung;


public class Cell {
    private Worker worker;
    private Tower tower;

    public Cell() {
        this.worker = null;
        this.tower = new Tower();  // Each cell starts with a base tower (level 0)
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Tower getTower(){
        return tower;
    }

    public boolean buildTowerOnCell() {
        // cannot build if there has a worker and dome
        if (worker != null || tower.hasDome()) {
            return false;
        }
        return tower.build();
    }

    public int getTowerLevel() {
        return tower.getLevel();
    }

    public boolean isOccupied() {
        return worker != null || tower.hasDome();
    }

    public String toString() {
        return (worker != null ? worker.getPlayer().getName() : "") + tower.toString();
    }
}
