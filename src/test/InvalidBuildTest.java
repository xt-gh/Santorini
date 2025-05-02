package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sprint2implementation.cards.Artemis;
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
import static org.junit.jupiter.api.Assertions.assertFalse;

public class InvalidBuildTest {
    // Test case 3.9
    private Board gameBoard;

    @BeforeEach
    void setUp() {
        gameBoard = Board.getInstance(600, 5, 5);
    }

    @Test
    void testBuildFailsWhenTileIsOccupiedByAnotherWorker() {
        List<GodCard> godCards = new ArrayList<>(List.of(new Artemis(), new Demeter()));
        Map<Integer, Player> playerList = Application.instantiatePlayers(2, 2, godCards);
        JLabel label = new JLabel();
        GameController controller = GameController.getInstance(gameBoard, playerList, label);

        Player player = playerList.get(0);
        Worker worker1 = player.getWorkerList().get(0);
        Worker worker2 = player.getWorkerList().get(1);

        Tile tile1 = gameBoard.getTileLocation(2, 2);
        Tile tile2 = gameBoard.getTileLocation(3, 3);
        tile1.setWorker(worker1);
        tile2.setWorker(worker2);
        worker1.setPosition(2, 2);
        worker2.setPosition(3, 3);

        boolean result = worker1.executeAction(player, tile1, tile2, new Tower());
        assertFalse(result, "Build should fail on occupied tile.");
    }
}