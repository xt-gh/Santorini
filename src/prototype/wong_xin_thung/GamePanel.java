package prototype.wong_xin_thung;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel {
    private JFrame frame;
    private static final int FRAME_SIZE = 800;
    private static final int BOARD_SIZE = (int) (FRAME_SIZE * 0.8);
    private Board board;
    private Player player1, player2;
    private Player currentPlayer;
    private Worker selectedWorker;
    private boolean isBuildingPhase;

    public GamePanel() {
        frame = new JFrame("Santorini Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_SIZE, FRAME_SIZE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Layered Pane for Background + Board
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(FRAME_SIZE, FRAME_SIZE));
        frame.setContentPane(layeredPane);

        // Background Panel
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/prototype/wong_xin_thung/pics/santorini_background.jpg");
        backgroundPanel.setBounds(0, 0, FRAME_SIZE, FRAME_SIZE);
        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        // Board Panel
        board = new Board(BOARD_SIZE);
        board.setBounds((FRAME_SIZE - BOARD_SIZE) / 2, (FRAME_SIZE - BOARD_SIZE) / 2, BOARD_SIZE, BOARD_SIZE);
        board.setOpaque(false);
        layeredPane.add(board, JLayeredPane.PALETTE_LAYER);

        player1 = new Player("P1", "Blue",Color.BLUE);
        player2 = new Player("P2", "Red",Color.RED);
//        player1 = new Player("P1");
//        player2 = new Player("P2");
        currentPlayer = player1;

        // Set up buttons
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                JButton button = board.getButton(row, col);
                // if button is clicked, triggers actionPerformed()
                button.addActionListener(new ButtonClickListener(row, col));
            }
        }

        board.placeInitialWorkers(player1, player2);
        updateBoard();
        frame.setVisible(true);
    }

    public void updateBoard() {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                Cell cell = board.getCell(row, col);
                JButton button = board.getButton(row, col);
                Tower tower = cell.getTower();

                Worker worker = cell.getWorker();
                if (worker != null) {
                    // set the background colour to represent the player
                    button.setBackground(worker.getPlayer().getColor());
                } else {
                    button.setText("");
                    button.setBackground(Color.lightGray);
                }


                // Set tower icon
                if (tower != null) {
                    ImageIcon icon = Tower.getIcon(tower.getLevel());
                    if (icon != null) {
                        Image img = icon.getImage().getScaledInstance(button.getWidth()-15, button.getHeight()-15, Image.SCALE_SMOOTH);
                        button.setIcon(new ImageIcon(img));
                    }
                } else {
                    button.setIcon(null);
                }
            }
        }
    }

    private class ButtonClickListener implements ActionListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            // check if not building phase
            if (!isBuildingPhase) {
                // if no worker selected, then picks one
                if (selectedWorker == null) {
                    selectedWorker = board.getCell(row, col).getWorker();
                    // no works in the selected cell
                    // OR the worker does not belong to the currentPlayer
                    if (selectedWorker == null || selectedWorker.getPlayer() != currentPlayer) {
                        selectedWorker = null;
                    }
                    // if selectedWorker is already chosen, then proceed to move
                } else {
                    // call moveWorker() to move the selected worker to the specific row & col
                    if (board.moveWorker(selectedWorker, row, col)) {
                        updateBoard();  // update to the board

                        // after move, check if the currentPlayer win
                        if (board.checkWinCondition(currentPlayer)) {
                            JOptionPane.showMessageDialog(frame, currentPlayer.getName() + " (" + (currentPlayer.getColourText()) + ")" + " Wins!");
                            System.exit(0);
                        }
                        isBuildingPhase = true;

                    }
                }
            } else {
                // build the tower
                if (board.buildTower(selectedWorker, row, col)) {
                    updateBoard();
                    currentPlayer = (currentPlayer == player1) ? player2 : player1;
                    selectedWorker = null;
                    isBuildingPhase = false;

                    // if the current player stuck, then lose
                    if (!board.canMove(currentPlayer)){
                        JOptionPane.showMessageDialog(frame, currentPlayer.getName() + " (" + (currentPlayer.getColourText()) + ")" + " Lose!");
                        System.exit(0);
                    }
                }
            }
        }
    }
}
