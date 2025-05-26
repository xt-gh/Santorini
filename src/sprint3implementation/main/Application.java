/**
 * Please make sure you read these instructions before you run the program in Intellij.
 *  This is because without following these instructions, you won't be able to run the program in the Application class and
 *  there will be an error.
 *
 *   Instructions to run the Program in Intellij:
 *   1. Go to the resources folder and right click on it.
 *   2. Navigate to the "Mark Directory As" at the last row and click on Resources Root.
 *   3. Go back to the Application class and run the program. The program should run as expected.
 *
 *     The purpose of creating a resources folder is to allow the pictures to load into the game
 *     when the artifact is built, converted to a JAR file followed by an executable file.
 *
 *     For further information, you can go and read the "Instructions to Build and Run an Executable using Windows",
 *     on page 1 and 2.
 */

package sprint3implementation.main;
import sprint3implementation.cards.*;
import sprint3implementation.characters.Player;
import sprint3implementation.grounds.Board;
import sprint3implementation.setups.ResizeListener;
import sprint3implementation.setups.Window;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Main class to run the Santorini game application.
 *
 * @author Yee Peen
 * Modified by: Tiffany, Emil
 */
public class Application {
    /**
     * Instantiates and shuffles the God cards available in the game.
     *
     * @return a shuffled list of GodCard objects
     */
    private static List<GodCard> instantiateGodCards() {
        // instantiates the type of god cards
//        GodCard demeter = new Demeter();
        GodCard artemis = new Artemis();
        GodCard zeus = new Zeus();

        // Created a list to store all the god cards
        List<GodCard> godCards = new ArrayList<>();
//        godCards.add(demeter);
        godCards.add(artemis);
        godCards.add(zeus);

        // Assign god cards randomly to every players by shuffling them
        Collections.shuffle(godCards);

        return godCards;
    }

    private static List<FunctionCard> instantiateFunctionCards(int playerNum){
        List<FunctionCard> functionCards = new ArrayList<>();
        for (int i = 0; i < playerNum; i++) {
            functionCards.add(new SkipCard()); // each player gets their own SkipCard instance
        }
        return functionCards;
//        FunctionCard skipCard = new SkipCard();
//
//        List<FunctionCard> functionCards = new ArrayList<>();
//        functionCards.add(skipCard);
//
//        return functionCards;
    }

    /**
     * Creates players and assigns each one a unique GodCard
     * and associated images.
     *
     * @param playerNum the number of players.
     * @param workerNum the number of workers per player.
     * @param godCardList the list of GodCards to assign
     * @return a map of players indexed by their ID.
     */
    public static Map<Integer, Player> instantiatePlayers(int playerNum, int workerNum, List<GodCard> godCardList, List<FunctionCard> functionCardList)
    {
        Map<Integer, Player> playerList = new HashMap<>();


        for (int playerNo = 0; playerNo < playerNum; playerNo++)
        {
            Player player = new Player("Player "+(playerNo+1), Application.class.getResource("/pics/player_"+(playerNo+1)+".png"), workerNum);
            player.setGodCard(godCardList.get(playerNo));
            player.addFunctionCard(functionCardList.get(playerNo));
            for (int towerNo = 1; towerNo < 4 ; towerNo++)
            {
                player.setPlayerPositionTower(towerNo, Application.class.getResource("/pics/Lvl_"+towerNo+"_player_"+(playerNo+1)+".png"));
            }
            // store player into the player list (Hash Map)
            playerList.put(playerNo, player);
        }
        return playerList;
    }

    /**
     * Entry point of the application.
     *
     * Sets up the window, board, UI layers, and initializes the game controller.
     *
     * @param args command-line arguments
     */
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
        turnLabel.setFont(new Font("Gill Sans", Font.BOLD, 20));
        turnLabel.setForeground(Color.black);
        turnLabel.setOpaque(true);
        turnLabel.setBackground(new Color(225,225,225,250));
        turnLabel.setBounds(25, 20, 500, 40);
        layeredPane.add(turnLabel, JLayeredPane.MODAL_LAYER);

        // Create timer label
        JLabel timerLabel = new JLabel("Time: ");
        timerLabel.setFont(new Font("Gill Sans", Font.BOLD, 20));
        timerLabel.setForeground(Color.black);
        timerLabel.setOpaque(true);
        timerLabel.setBackground(new Color(225,225,225,250));
        timerLabel.setBounds((screenSize.width - 200) / 2, 20, 200, 40);
        layeredPane.add(timerLabel, JLayeredPane.MODAL_LAYER);



        int playerNum = 2;
        List<GodCard> godCardList = instantiateGodCards();
        List<FunctionCard> functionCardList = instantiateFunctionCards(playerNum);
        Map<Integer, Player> playerList = instantiatePlayers(playerNum, 2, godCardList, functionCardList);


        GameController gameController = GameController.getInstance(gameBoard, playerList, turnLabel, timerLabel);
        windowFrame.addComponentListener(ResizeListener.getInstance(windowFrame, gameBoard, boardSize, boardSize));
        windowFrame.setVisible(true);
    }

}
