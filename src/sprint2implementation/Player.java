package sprint2implementation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player {
    private ImageIcon playerIcon;
    private String name;
    private HashMap<Integer, ImageIcon> playerPositionTower = new HashMap<>();
    private List<Worker> workers;
    private int workerNum;

    public Player(String name, String imagePath, int workerNum)
    {
        this.name = name;
        setPlayerImageIcon(imagePath);
        playerPositionTower.put(0, new ImageIcon(imagePath));
        this.workers = new ArrayList<>();
        this.workerNum = workerNum;

        initialiseWorkers();

    }

    public void initialiseWorkers() {
        for (int i = 0; i < workerNum; i++) {
            Worker worker = new Worker(this);
            addWorker(worker);
        }
    }

    public List<Worker> getWorkers() {
        return workers;
    }
    public void addWorker(Worker worker) {
        workers.add(worker);
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
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
