package sprint2implementation.characters;

import sprint2implementation.cards.GodCard;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private ImageIcon playerIcon;
    private String name;
    private Map<Integer, ImageIcon> playerPositionTower = new HashMap<>();
    private GodCard godCard;
    private List<Worker> workerList;
    private int workerNum;
    private Boolean isActionSuccessful = false;
    private Worker currentWorker = null;

    public Player(String name, URL imagePath, int workerNum)
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

    public Map<Integer, ImageIcon> getPlayerPositionTower() {
        return playerPositionTower;
    }

    public void setPlayerPositionTower(int num, URL imagePath) {
        playerPositionTower.put(num, new ImageIcon(imagePath));
    }

    public void setPlayerImageIcon(URL imagePath) {
        playerIcon = new ImageIcon(imagePath);
    }

    public ImageIcon getPlayerIcon() {
        return playerIcon;
    }

    public String getName() {
        return name;
    }

    public Worker getCurrentWorker() {
        return currentWorker;
    }

    public void setCurrentWorker(Worker worker) {
        this.currentWorker = worker;
    }

    public boolean isActionSuccessful() {
        return isActionSuccessful;
    }

    public void setActionSuccessful(Boolean actionSuccessful) {
        isActionSuccessful = actionSuccessful;
    }
    public void clearCurrentWorker() {
        this.currentWorker = null;
    }
}
