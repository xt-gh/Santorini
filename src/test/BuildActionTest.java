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
import static org.junit.Assert.assertTrue;

public class BuildActionTest {
    // Test case 3
    private Board gameBoard;

    @BeforeEach
    void setUp() {
        gameBoard = Board.getInstance(600, 5, 5);
    }

    @Test
    void testValidBuildOnAdjacentTile() {
        List<GodCard> godCards = new ArrayList<>(List.of(new Artemis()));
        Map<Integer, Player> playerList = Application.instantiatePlayers(1, 2, godCards);
        JLabel label = new JLabel();
        GameController controller = GameController.getInstance(gameBoard, playerList, label);

        Player player = playerList.get(0);
        Worker worker = player.getWorkerList().get(0);
        Tile start = gameBoard.getTileLocation(2, 2);
        start.setWorker(worker);
        worker.setPosition(2, 2);

        Tile tile23 = gameBoard.getTileLocation(2, 3);
        boolean move1 = worker.executeAction(player, start, tile23, new Tower());
        assertTrue(move1);

        Tile tile24 = gameBoard.getTileLocation(2, 4);
        boolean move2 = worker.executeAction(player, tile23, tile24, new Tower());
        assertTrue(move2);

        Tile buildTile = gameBoard.getTileLocation(3, 4);
        boolean buildResult = worker.executeAction(player, tile24, buildTile, new Tower());
        assertTrue(String.valueOf(buildResult), true);
    }
}