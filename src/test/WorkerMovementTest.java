package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sprint2implementation.cards.Demeter;
import sprint2implementation.cards.GodCard;
import sprint2implementation.characters.Player;
import sprint2implementation.characters.Worker;
import sprint2implementation.grounds.Board;
import sprint2implementation.grounds.Tile;
import sprint2implementation.main.Application;
import sprint2implementation.main.GameController;
import sprint2implementation.towers.Tower;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkerMovementTest {
    private Board gameBoard;

    @BeforeEach
    void setUp() {
        gameBoard = Board.getInstance(600, 5, 5);
    }

    @Test
    void testWorkerMovement() {
        // Test case 2.1

        List<GodCard> godCards = new ArrayList<>(List.of(new Demeter(), new Demeter()));
        Map<Integer, Player> playerList = Application.instantiatePlayers(2, 2, godCards);
        JLabel label = new JLabel();
        GameController controller = GameController.getInstance(gameBoard, playerList, label);

        Player player = playerList.get(0);
        Worker worker = player.getWorkerList().get(0);
        Tile start = gameBoard.getTileLocation(2, 2);
        start.setWorker(worker);
        worker.setPosition(2, 2);

        Tile destination = gameBoard.getTileLocation(2, 3);
        boolean result = worker.executeAction(player, start, destination, new Tower());

        assertTrue(result, "Worker should be able to move to adjacent tile.");
    }
}