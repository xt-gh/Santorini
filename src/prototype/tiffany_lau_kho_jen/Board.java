package prototype.tiffany_lau_kho_jen;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Board extends JPanel {
    private static final int TILE_SIZE = 130;
    private Player playerOne;
    private Player playerTwo;
    private Player selectedPlayer;
    private Building buildingManager;
    private GameLogic logic;

    public Board(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.selectedPlayer = null;
        this.buildingManager = new Building(TILE_SIZE);
        this.logic = new GameLogic();

        setLayout(null);
        setPreferredSize(new Dimension(5 * TILE_SIZE, 5 * TILE_SIZE));
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                handleClick(event.getX(), event.getY());
            }
        });

        add(playerOne);
        add(playerTwo);
        updatePlayerPosition(playerOne);
        updatePlayerPosition(playerTwo);
        SwingUtilities.invokeLater(this::showTurnMessage);
    }

    private void handleClick(int x, int y) {
        int col = x / TILE_SIZE;
        int row = y / TILE_SIZE;
        Point clicked = new Point(col, row);

        Player current = logic.isPlayerOneTurn() ? playerOne : playerTwo;

        if (!logic.isBuildingPhase()) {
            if (current.getRow() == row && current.getColumn() == col) {
                selectedPlayer = current;
                selectedPlayer.selectPlayer();
            }

            else if (selectedPlayer != null) {
                Point from = new Point(selectedPlayer.getColumn(), selectedPlayer.getRow());
                int fromLevel = buildingManager.getBuildingLevel(from);
                int toLevel = buildingManager.getBuildingLevel(clicked);

                if (PlayerMovement.isValidMove(from, clicked, fromLevel, toLevel, playerOne, playerTwo)) {
                    selectedPlayer.setPosition(row, col, toLevel);
                    updatePlayerPosition(selectedPlayer);

                    if (toLevel == 3) {
                        declareWinner();
                        return;
                    }

                    logic.enterBuildingPhase();
                    repaint();
                }

                else {
                    JOptionPane.showMessageDialog(this, "Invalid move!", "Move",
                    JOptionPane.WARNING_MESSAGE);
                }
            }
        }

        else {
            if (selectedPlayer == null || !isAdjacent(clicked, selectedPlayer)) {
                JOptionPane.showMessageDialog(this, "You can only build on tiles adjacent " +
                 "to you!","Invalid Build", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!buildingManager.canBuild(clicked, playerOne, playerTwo)) {
                JOptionPane.showMessageDialog(this, "Invalid build!", "Build",
                JOptionPane.WARNING_MESSAGE);
                return;
            }

            buildingManager.build(clicked, playerOne, playerTwo);
            logic.switchTurn();
            checkLosingCondition();

            if (selectedPlayer != null) selectedPlayer.deselectPlayer();
            selectedPlayer = null;
            repaint();
            SwingUtilities.invokeLater(this::showTurnMessage);
        }
    }

    private void declareWinner() {
        String winner = logic.isPlayerOneTurn() ? "Player One" : "Player Two";
        JOptionPane.showMessageDialog(this, winner + " Wins!", "Game Over",
        JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private void checkLosingCondition() {
        Player opponent = logic.isPlayerOneTurn() ? playerTwo : playerOne;
        Point current = new Point(opponent.getColumn(), opponent.getRow());
        int currentLevel = buildingManager.getBuildingLevel(current);

        for (int row = opponent.getRow() - 1; row <= opponent.getRow() + 1; row++) {
            for (int column = opponent.getColumn() - 1; column <= opponent.getColumn() + 1; column++) {
                if (row >= 0 && row < 5 && column >= 0 && column < 5) {
                    Point neighboringTile = new Point(column, row);
                    int level = buildingManager.getBuildingLevel(neighboringTile);

                    boolean unoccupiedByPlayerOne = !(playerOne.getRow() == row && playerOne.getColumn() == column);
                    boolean unoccupiedByPlayerTwo = !(playerTwo.getRow() == row && playerTwo.getColumn() == column);

                    if (level < 4 && level <= currentLevel + 1 && unoccupiedByPlayerOne && unoccupiedByPlayerTwo) {
                        return;
                    }
                }
            }
        }

        String winner = logic.isPlayerOneTurn() ? "Player One" : "Player Two";
        JOptionPane.showMessageDialog(this, winner + " Wins by Default! " +
        "Opponent Cannot Move!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private boolean isAdjacent(Point tile, Player player) {
        int rowDifference = Math.abs(tile.y - player.getRow());
        int columnDifference = Math.abs(tile.x - player.getColumn());
        return (rowDifference <= 1) && (columnDifference <= 1) && !(rowDifference == 0 && columnDifference == 0);
    }

    private void updatePlayerPosition(Player player) {
        int x = 190 + (player.getColumn() * TILE_SIZE) + (TILE_SIZE / 2) - (player.getWidth() / 2);
        int y = 60 + (player.getRow() * TILE_SIZE) + (TILE_SIZE / 2) - (player.getHeight() / 2);
        int levelOffset = buildingManager.getBuildingLevel(new Point(player.getColumn(), player.getRow())) * 3;
        player.setLocation(x, y - levelOffset);
    }

    private void showTurnMessage() {
        JOptionPane.showMessageDialog(this, logic.getCurrentPlayerName() + "'s Turn!",
        "Turn Info", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        buildingManager.drawBuildings(g, TILE_SIZE, playerOne, playerTwo);
    }

    private void drawBoard(Graphics g) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                int x = col * TILE_SIZE;
                int y = row * TILE_SIZE;

                g.setColor(new Color(34, 139, 34));
                g.fillRect(x, y, TILE_SIZE, TILE_SIZE);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(4));
                g2d.setColor(Color.WHITE);
                g2d.drawRect(x, y, TILE_SIZE, TILE_SIZE);
            }
        }
    }
}
