//package sprint3implementation.cards;
//
//import sprint3implementation.actions.BuildAction;
//import sprint3implementation.actions.MoveAction;
//import sprint3implementation.characters.Player;
//import sprint3implementation.grounds.Tile;
//import sprint3implementation.towers.Tower;
//
//import javax.swing.*;
//
//public class Zeus extends GodCard {
//    private boolean isBuildingPhase = false;
//    private boolean isMoving = false;
//    private boolean builtUnderSelf = false;
//
//    public Zeus() {
//        super("Zeus", "Zeus can build under himself.");
//    }
//
//    public boolean executeSpecialAbility(Player currentPlayer, Tile selectedTile, Tile clickedTile, Tower tower) {
//        if (!isBuildingPhase) {
//            MoveAction moveAction = new MoveAction(selectedTile, clickedTile, selectedTile.getWorker());
//            moveAction.execute();
//
//            if (moveAction.isMoveSuccessful()) {
//                isMoving = true;
//                isBuildingPhase = true;
//                builtUnderSelf = false;
//                JOptionPane.showMessageDialog(null, "Now is your building phase!", "Building Stage", JOptionPane.PLAIN_MESSAGE);
//
//            }
//        } else {
//            Tile workerTile = currentPlayer.getCurrentWorker().getCurrentTile();
//
//            if (clickedTile == workerTile) {
//                int response = JOptionPane.showConfirmDialog(
//                        null,
//                        getDescription() + "\n" + currentPlayer.getName() + ", do you want to perform a second build using Demeter's power?",
//                        "Zeus Power",
//                        JOptionPane.YES_NO_OPTION
//                );
//                if (response == JOptionPane.YES_NO_OPTION) {
//                    builtUnderSelf = true;
//                }
//            }
//
//            BuildAction buildAction = new BuildAction(clickedTile, tower);
//            buildAction.execute();
//
//            if buildAction.isBuildSuccessful() {
//                isMoving = false;
//                isBuildingPhase = false;
//                currentPlayer.setBuiltUnderSelf(builtUnderSelf);
//                currentPlayer.setActionSuccessful(true);
//            }
//        }
//        return isMoving;
//
//    }
//}
package sprint3implementation.cards;

import com.sun.corba.se.spi.orbutil.threadpool.Work;
import sprint3implementation.actions.BuildAction;
import sprint3implementation.actions.MoveAction;
import sprint3implementation.characters.Player;
import sprint3implementation.grounds.Tile;
import sprint3implementation.towers.Tower;
import sprint3implementation.characters.Worker;


import javax.swing.*;

public class Zeus extends GodCard {

    private boolean isBuildingPhase = false;
    private boolean isMoving = false;
    private boolean builtUnderSelf = false;
    private Worker activeWorker;

    public Zeus() {
        super("Zeus", "Zeus can build under itself.");
    }

    @Override
    public boolean executeSpecialAbility(Player currentPlayer, Tile selectedTile, Tile clickedTile, Tower tower) {
        if (!isBuildingPhase) {
            // Movement Phase
            MoveAction moveAction = new MoveAction(selectedTile, clickedTile, selectedTile.getWorker());
            moveAction.execute();

            if (moveAction.isMoveSuccessful()) {
                isMoving = true;
                isBuildingPhase = true;
                builtUnderSelf = false;
                activeWorker = clickedTile.getWorker();
                JOptionPane.showMessageDialog(null, "Now is your building phase!", "Building Stage", JOptionPane.PLAIN_MESSAGE);
            }

        } else {
            // Building Phase
            Tile activeWorkerTile = activeWorker.getCurrentTile();
//            Worker worker = currentPlayer.getCurrentWorker();
//            Tile workerTile = currentPlayer.getCurrentWorker().getCurrentTile();

            if (clickedTile.hasWorker() && clickedTile != activeWorkerTile){
                JOptionPane.showMessageDialog(null, "You cannot build under another worker!", "Invalid Action", JOptionPane.ERROR_MESSAGE);
                return true;

            }
            if (clickedTile == activeWorkerTile) {
//                int response = JOptionPane.showConfirmDialog(
//                        null,
//                        "Do you want to build under yourself using Zeus's power?",
//                        "Build Under Self",
//                        JOptionPane.YES_NO_OPTION
//                );

                int response = JOptionPane.showConfirmDialog(
                        null,
                        getDescription() + "\n" + currentPlayer.getName() + ", do you want to build under yourself using Zeus's power?",
                        "Build Under Self?",
                        JOptionPane.YES_NO_OPTION
                );

                if (response == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(null, "You chose not to build under yourself. Please select another tile.", "Notice", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    builtUnderSelf = true;
                }

                if (response == JOptionPane.YES_OPTION) {
                    builtUnderSelf = true;
                    isMoving = false;
//                    JOptionPane.showMessageDialog(null, "You can build under yourself now.", "Building Stage", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    builtUnderSelf = false;
                    isBuildingPhase = false;
                    isMoving = false;
                    currentPlayer.setActionSuccessful(true); // Player ends turn after 1 build
                    return false;
                }
            }

            // Build Action
            BuildAction buildAction = new BuildAction(clickedTile, tower, true);
            buildAction.execute();

            if (buildAction.isBuildSuccessful()) {
                isMoving = false;
                isBuildingPhase = false;
                currentPlayer.setActionSuccessful(true);
                currentPlayer.setBuiltUnderSelf(builtUnderSelf);

                if (builtUnderSelf){
                    int newLevel = activeWorkerTile.getTower().getLevelCount();
                    ImageIcon newIcon = activeWorker.getPlayer().getPlayerPositionTower().get(newLevel);
                    activeWorkerTile.updateIcon(newIcon);


                }
//                Tile currentTile = worker.getCurrentTile();
//                int newLevel = currentTile.getTower().getLevelCount();
//                ImageIcon newIcon = worker.getPlayer().getPlayerPositionTower().get(newLevel);
//                currentTile.updateIcon(newIcon);
            }
        }

        return isMoving;
    }
}
