package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sprint2implementation.cards.Artemis;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArtemisMovementTest {
    // Test case 2.13
    private Board gameBoard;

    @BeforeEach
    void setUp() {
        gameBoard = Board.getInstance(600, 5, 5);
    }

    @Test
    void testArtemisCannotMoveBackToOriginalTile() {
        List<GodCard> godCards = new ArrayList<>(List.of(new Artemis(), new Artemis()));
        Map<Integer, Player> playerList = Application.instantiatePlayers(2, 2, godCards);
        JLabel label = new JLabel();
        GameController controller = GameController.getInstance(gameBoard, playerList, label);

        Player player = playerList.get(0);
        Worker worker = player.getWorkerList().get(0);
        Tile start = gameBoard.getTileLocation(2, 2);
        start.setWorker(worker);
        worker.setPosition(2, 2);

        Tile tile23 = gameBoard.getTileLocation(2, 3);
        Tower tower = new Tower();
        boolean move1 = worker.executeAction(player, start, tile23, tower);
        assertTrue(move1, "First move should succeed.");

        boolean move2 = worker.executeAction(player, tile23, start, tower);
        assertFalse(move2, "Artemis should not move back to original tile.");
    }
}