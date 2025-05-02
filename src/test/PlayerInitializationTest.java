package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sprint2implementation.cards.Artemis;
import sprint2implementation.cards.Demeter;
import sprint2implementation.cards.GodCard;
import sprint2implementation.characters.Player;
import sprint2implementation.grounds.Board;
import sprint2implementation.main.Application;
import sprint2implementation.main.GameController;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerInitializationTest {
    // Test case 1.2
    private Board gameBoard;

    @BeforeEach
    void setUp() {
        gameBoard = Board.getInstance(600, 5, 5);
    }

    @Test
    void testNumberOfPlayersOnBoard() {
        int playerNum = 2;
        int workerNum = 2;
        List<GodCard> godCards = new ArrayList<>(List.of(new Artemis(), new Demeter()));
        Map<Integer, Player> playerList = Application.instantiatePlayers(playerNum, workerNum, godCards);
        JLabel currentPlayerLabel = new JLabel();
        GameController controller = GameController.getInstance(gameBoard, playerList, currentPlayerLabel);

        assertEquals(String.valueOf(2), playerList.size(), 2);

        for (Player player : playerList.values()) {
            assertNotNull(player.getGodCard(), "Player's GodCard should not be null.");
            assertTrue(godCards.contains(player.getGodCard()), "Player's GodCard should be one of the valid options.");
        }
    }
}