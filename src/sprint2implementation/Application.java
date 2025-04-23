package sprint2implementation;
import javax.swing.*;
import java.awt.*;


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

//      Create a game board (a layer higher)
        Board gameBoard = new Board(600,5,5);
        int boardSize = gameBoard.getBoardSize();
        int width = (screenSize.width - boardSize)/2;
        int height = (screenSize.height - boardSize)/2;
        gameBoard.setBounds(width,height, boardSize, boardSize);
        layeredPane.add(gameBoard, JLayeredPane.PALETTE_LAYER);

        // Create player's turn label
        JLabel turnLabel = new JLabel("Player: ");
        turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        turnLabel.setForeground(Color.BLUE);
        turnLabel.setBounds(20, 20, 400, 30);
        layeredPane.add(turnLabel, JLayeredPane.MODAL_LAYER);
        
        // Add Players into the game
        Player player1 = new Player("Player 1", "src/sprint2implementation/pics/player_1.png", 2);
        Player player2 = new Player("Player 2", "src/sprint2implementation/pics/player_2.png", 2);

        player1.setPlayerPositionTower(1, "src/sprint2implementation/pics/Lvl_1_player_1.png");
        player1.setPlayerPositionTower(2, "src/sprint2implementation/pics/Lvl_2_player_1.png");
        player1.setPlayerPositionTower(3, "src/sprint2implementation/pics/Lvl_3_player_1.png");

        player2.setPlayerPositionTower(1, "src/sprint2implementation/pics/Lvl_1_player_2.png");
        player2.setPlayerPositionTower(2, "src/sprint2implementation/pics/Lvl_2_player_2.png");
        player2.setPlayerPositionTower(3, "src/sprint2implementation/pics/Lvl_3_player_2.png");

        System.out.println(player1.getPlayerPositionTower());


        GameController controller = new GameController(gameBoard, player1, player2, turnLabel);
        windowFrame.addComponentListener(new ResizeListener(windowFrame, gameBoard, boardSize, boardSize));
        windowFrame.setVisible(true);
    }
}
