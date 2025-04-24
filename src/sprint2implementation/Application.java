package sprint2implementation;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Application
{
    public static void main(String[] args)
    {
        // instantiate the window object
        Window windowObj = new Window();
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
        Board gameBoard = new Board(600,5,5);
        int boardSize = gameBoard.getBoardSize();
        int width = (screenSize.width - boardSize)/2;
        int height = (screenSize.height - boardSize)/2;
        gameBoard.setBounds(width,height, boardSize, boardSize);
        layeredPane.add(gameBoard, JLayeredPane.PALETTE_LAYER);

        // Create player's turn label
        JLabel turnLabel = new JLabel("Player: ");
        turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        turnLabel.setForeground(Color.WHITE);
        turnLabel.setBounds(20, 20, 600, 30);
        layeredPane.add(turnLabel, JLayeredPane.MODAL_LAYER);

        // instantiates the type of god cards
        GodCard demeter = new Demeter();
        GodCard artemis = new Artemis();

        // Created a list to store all the god cards
        List<GodCard> godCards = new ArrayList<>();
        godCards.add(demeter);
        godCards.add(artemis);

        // Assign god cards randomly to every players by shuffling them
        Collections.shuffle(godCards);

        // Add Players into the game
        Player player1 = new Player("Player 1", "src/sprint2implementation/pics/player_1.png", 2);
        Player player2 = new Player("Player 2", "src/sprint2implementation/pics/player_2.png", 2);

        // Add God Card to Players randomly
        player1.setGodCard(godCards.get(0));
        player2.setGodCard(godCards.get(1));

        player1.setPlayerPositionTower(1, "src/sprint2implementation/pics/Lvl_1_player_1.png");
        player1.setPlayerPositionTower(2, "src/sprint2implementation/pics/Lvl_2_player_1.png");
        player1.setPlayerPositionTower(3, "src/sprint2implementation/pics/Lvl_3_player_1.png");

        player2.setPlayerPositionTower(1, "src/sprint2implementation/pics/Lvl_1_player_2.png");
        player2.setPlayerPositionTower(2, "src/sprint2implementation/pics/Lvl_2_player_2.png");
        player2.setPlayerPositionTower(3, "src/sprint2implementation/pics/Lvl_3_player_2.png");

        GameController controller = new GameController(gameBoard, player1, player2, turnLabel);
        windowFrame.addComponentListener(new ResizeListener(windowFrame, gameBoard, boardSize, boardSize));
        windowFrame.setVisible(true);
    }
}
