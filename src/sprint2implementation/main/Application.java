// Please make sure you read these instructions before you run the program in Intellij.
// This is because without following these instructions, you won't be able to run the program in the Application class and
// there will be an error.
//
//  Instructions to run the Program in Intellij:
//  1. Go to the resources folder and right click on it.
//  2. Navigate to the "Mark Directory As" at the last row and click on Resources Root.
//  3. Go back to the Application class and run the program. The program should run as expected.
//
//    The purpose of creating a resources folder is to allow the pictures to load into the game
//    when the artifact is built, converted to a JAR file followed by an executable file.
//
//    For further information, you can go and read the "Instructions to Build and Run an Executable using Windows",
//    on page 1 and 2.

package sprint2implementation.main;
import sprint2implementation.cards.Artemis;
import sprint2implementation.cards.Demeter;
import sprint2implementation.cards.GodCard;
import sprint2implementation.characters.Player;
import sprint2implementation.grounds.Board;
import sprint2implementation.setups.ResizeListener;
import sprint2implementation.setups.Window;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


public class Application {
    private static List<GodCard> instantiateGodCards() {
        // instantiates the type of god cards
        GodCard demeter = new Demeter();
        GodCard artemis = new Artemis();

        // Created a list to store all the god cards
        List<GodCard> godCards = new ArrayList<>();
        godCards.add(demeter);
        godCards.add(artemis);

        // Assign god cards randomly to every players by shuffling them
        Collections.shuffle(godCards);

        return godCards;
    }

    private static Map<Integer, Player> instantiatePlayers(int playerNum, int workerNum, List<GodCard> godCardList)
    {
        Map<Integer, Player> playerList = new HashMap<>();

        for (int playerNo = 0; playerNo < playerNum; playerNo++)
        {
            Player player = new Player("Player "+(playerNo+1), Application.class.getResource("/pics/player_"+(playerNo+1)+".png"), workerNum);
            player.setGodCard(godCardList.get(playerNo));
            for (int towerNo = 1; towerNo < 4 ; towerNo++)
            {
                player.setPlayerPositionTower(towerNo, Application.class.getResource("/pics/Lvl_"+towerNo+"_player_"+(playerNo+1)+".png"));
            }
            // store player into the player list (Hash Map)
            playerList.put(playerNo, player);
        }
        return playerList;
    }

    public static void main(String[] args) {
        // instantiate the window object
        Window windowObj = Window.getInstance();
        JFrame windowFrame = windowObj.getNewFrame();
        Dimension screenSize = windowObj.getCurrentScreenSize();

        // Create a layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(screenSize);
        windowFrame.setContentPane(layeredPane);

        // Set the background of the window(lowest layer)
        JLabel bgLabel = windowObj.setBgLabel(screenSize);
        bgLabel.setBounds(0, 0, screenSize.width, screenSize.height);
        layeredPane.add(bgLabel, JLayeredPane.DEFAULT_LAYER);

        // Create a game board (a layer higher)
        Board gameBoard = Board.getInstance(600, 5, 5);
        int boardSize = gameBoard.getBoardSize();
        int width = (screenSize.width - boardSize) / 2;
        int height = (screenSize.height - boardSize) / 2;
        gameBoard.setBounds(width, height, boardSize, boardSize);
        layeredPane.add(gameBoard, JLayeredPane.PALETTE_LAYER);

        // Create player's turn label
        JLabel turnLabel = new JLabel("Player: ");
        turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        turnLabel.setForeground(Color.WHITE);
        turnLabel.setBounds(20, 20, 600, 30);
        layeredPane.add(turnLabel, JLayeredPane.MODAL_LAYER);

        List<GodCard> godCardList = instantiateGodCards();
        Map<Integer, Player> playerList = instantiatePlayers(2, 2, godCardList);

        GameController gameController = GameController.getInstance(gameBoard, playerList, turnLabel);
        windowFrame.addComponentListener(ResizeListener.getInstance(windowFrame, gameBoard, boardSize, boardSize));
        windowFrame.setVisible(true);
    }

}
