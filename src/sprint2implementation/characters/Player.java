package sprint2implementation.characters;

import sprint2implementation.cards.GodCard;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private Map<Integer, ImageIcon> playerPositionTower = new HashMap<>();
    private List<Worker> workerList;
    private Worker currentWorker = null;
    private GodCard godCard;
    private ImageIcon playerIcon;
    private String name;
    private int workerNum;
    private boolean isActionSuccessful = false;


    public Player(String name, URL imagePath, int workerNum)
    {
        this.name = name;
        setPlayerImageIcon(imagePath);
        playerPositionTower.put(0, new ImageIcon(imagePath));
        this.workerList = new ArrayList<>();
        this.workerNum = workerNum;

        initialiseWorkers();

    }

    private void initialiseWorkers() {
        for (int i = 0; i < workerNum; i++) {
            Worker worker = new Worker(this);
            addWorker(worker);
        }
    }

    public void addWorker(Worker worker) {
        workerList.add(worker);
    }

    public String getName() {
        return name;
    }

    public GodCard getGodCard(){
        return godCard;
    }

    public void setGodCard(GodCard godCard) {
        this.godCard = godCard;
    }

    public List<Worker> getWorkerList() {
        return workerList;
    }

    public Map<Integer, ImageIcon> getPlayerPositionTower() {
        return playerPositionTower;
    }

    public void setPlayerPositionTower(int num, URL imagePath) {
        playerPositionTower.put(num, new ImageIcon(imagePath));
    }

    public ImageIcon getPlayerIcon() {
        return playerIcon;
    }

    private void setPlayerImageIcon(URL imagePath) {
        playerIcon = new ImageIcon(imagePath);
    }

    public Worker getCurrentWorker() {
        return currentWorker;
    }

    public void setCurrentWorker(Worker worker) {
        this.currentWorker = worker;
    }

    public void clearCurrentWorker() {
        this.currentWorker = null;
    }

    public boolean isActionSuccessful() {
        return isActionSuccessful;
    }

    public void setActionSuccessful(Boolean actionSuccessful) {
        isActionSuccessful = actionSuccessful;
    }


}
