package prototype.tiffany_lau_kho_jen;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Santorini");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        int boardSize = 5 * 130;
        frame.setSize(boardSize + 400, boardSize + 150);
        frame.setLocationRelativeTo(null);

        ImageIcon image = new ImageIcon("src/prototype/tiffany_lau_kho_jen/pictures/imageicontwo.jpg");
        frame.setIconImage(image.getImage());



        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.setContentPane(layeredPane);

        ImageIcon background = new ImageIcon("src/prototype/tiffany_lau_kho_jen/pictures/" +
        "blue_sea_background.jpg");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        Player playerOne = new Player("src/prototype/tiffany_lau_kho_jen/pictures/playerOne.png",
        new String[]{
                "src/prototype/tiffany_lau_kho_jen/pictures/player1move1.png",
                "src/prototype/tiffany_lau_kho_jen/pictures/player1move2.png",
                "src/prototype/tiffany_lau_kho_jen/pictures/player1move3.png"
        }, 0, 0);

        Player playerTwo = new Player("src/prototype/tiffany_lau_kho_jen/pictures/playerTwo.png",
         new String[]{
                "src/prototype/tiffany_lau_kho_jen/pictures/player2move1.png",
                "src/prototype/tiffany_lau_kho_jen/pictures/player2move2.png",
                "src/prototype/tiffany_lau_kho_jen/pictures/player2move3.png"
        }, 4, 4);

        Board santoriniBoard = new Board(playerOne, playerTwo);
        santoriniBoard.setBounds(200, 60, boardSize, boardSize);
        layeredPane.add(santoriniBoard, Integer.valueOf(1));

        layeredPane.add(playerOne, Integer.valueOf(2));
        layeredPane.add(playerTwo, Integer.valueOf(2));

        frame.setVisible(true);
    }
}
