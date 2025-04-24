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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Application {
    public static List<GodCard> instantiateGodCards() {
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

//    public static List<Player> instantiatePlayers(int playerNum, int workerNum, List<GodCard> godCardList)
//    {
//        List<Player> playerList = new ArrayList<>();
//
//        for (int playerNo = 0; playerNo < playerNum; playerNo++)
//        {
//            Player player = new Player("Player "+(playerNo+1), "src/sprint2implementation/pics/player_"+(playerNo+1)+".png", workerNum);
//            player.setGodCard(godCardList.get(playerNo));
//            for (int towerNo = 1; towerNo < 4 ; towerNo++)
//            {
//                player.setPlayerPositionTower(towerNo, "src/sprint2implementation/pics/Lvl_"+towerNo+"_player_"+(playerNo+1)+".png");
//            }
//            // store player into the player list
//            playerList.add(player);
//        }
//        return playerList;
//    }


    public static List<Player> instantiatePlayers(int playerNum, int workerNum, List<GodCard> godCardList)
    {
        List<Player> playerList = new ArrayList<>();

        for (int playerNo = 0; playerNo < playerNum; playerNo++)
        {
            Player player = new Player("Player "+(playerNo+1), Application.class.getResource("/pics/player_"+(playerNo+1)+".png"), workerNum);
            player.setGodCard(godCardList.get(playerNo));
            for (int towerNo = 1; towerNo < 4 ; towerNo++)
            {
                player.setPlayerPositionTower(towerNo, Application.class.getResource("/pics/Lvl_"+towerNo+"_player_"+(playerNo+1)+".png"));
            }
            // store player into the player list
            playerList.add(player);
        }
        return playerList;
    }

    public static void main(String[] args) {
        // instantiate the window object
        sprint2implementation.setups.Window windowObj = new Window();
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
        Board gameBoard = new Board(600, 5, 5);
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
        List<Player> playerList = instantiatePlayers(2, 2, godCardList);

        GameController controller = new GameController(gameBoard, playerList, turnLabel);
        windowFrame.addComponentListener(new ResizeListener(windowFrame, gameBoard, boardSize, boardSize));
        windowFrame.setVisible(true);
    }

}
