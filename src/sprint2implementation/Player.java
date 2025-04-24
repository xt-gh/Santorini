package sprint2implementation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player {
    private ImageIcon playerIcon;
    private String name;
    private HashMap<Integer, ImageIcon> playerPositionTower = new HashMap<>();
    private List<Worker> workerList;
    private int workerNum;
    private int movesRemaining = 1;
    private int buildsRemaining = 1;
    private GodCard godCard;

    public Player(String name, String imagePath, int workerNum)
    {
        this.name = name;
        setPlayerImageIcon(imagePath);
        playerPositionTower.put(0, new ImageIcon(imagePath));
        this.workerList = new ArrayList<>();
        this.workerNum = workerNum;

        initialiseWorkers();

    }

    public GodCard getGodCard(){
        return godCard;
    }

    public void setGodCard(GodCard godCard) {
        this.godCard = godCard;
        this.movesRemaining = godCard != null && godCard.canMoveTwice() ? 2 : 1;
        this.buildsRemaining = godCard != null && godCard.canBuildTwice() ? 2 : 1;
    }

    public void decrementMove() {
        movesRemaining--;
    }

    public void decrementBuild() {
        buildsRemaining--;
    }

    public boolean canMoveAgain() {
        return movesRemaining > 0;
    }

    public boolean canBuildAgain() {
        return buildsRemaining > 0;
    }

    public void resetTurn() {
        this.movesRemaining = godCard != null && godCard.canMoveTwice() ? 2 : 1;
        this.buildsRemaining = godCard != null && godCard.canBuildTwice() ? 2 : 1;
    }

    public void initialiseWorkers() {
        for (int i = 0; i < workerNum; i++) {
            Worker worker = new Worker(this);
            addWorker(worker);
        }
    }

    public List<Worker> getWorkerList() {
        return workerList;
    }

    public void addWorker(Worker worker) {
        workerList.add(worker);
    }

    public HashMap<Integer, ImageIcon> getPlayerPositionTower() {
        return playerPositionTower;
    }

    public void setPlayerPositionTower(int num, String imagePath) {
        playerPositionTower.put(num, new ImageIcon(imagePath));
    }

    public void setPlayerImageIcon(String imagePath) {
        playerIcon = new ImageIcon(imagePath);
    }

    public ImageIcon getPlayerIcon() {
        return playerIcon;
    }

    public String getName() {
        return name;
    }
}
