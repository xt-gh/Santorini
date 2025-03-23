package prototype.emil_chin_jia_zhen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SantoriniGame {
    private static final int GRID_SIZE = 7; // Size of the game grid
    private static final int TILE_SIZE = 100; // Size of each tile on the grid
    private static final int MAX_BUILD_HEIGHT = 4; // Maximum height of buildings in Santorini

    private JFrame window; // Main window for the game
    private JPanel boardPanel; // Panel to hold the game board
    private JPanel mainMenu; // Main menu panel
    private JPanel[][] tiles = new JPanel[GRID_SIZE][GRID_SIZE]; // 2D array of tiles on the board
    private JLabel[][] buildings = new JLabel[GRID_SIZE][GRID_SIZE]; // 2D array to track building heights
    private int[][] buildingLevels = new int[GRID_SIZE][GRID_SIZE]; // Array to store building heights at each position
    private JLabel[] workers = new JLabel[2]; // Two workers for the two players
    private Point[] workerPositions = {new Point(0, 0), new Point(4, 4)}; // Initial worker positions
    private int currentPlayer = 0; // 0 = Player 1, 1 = Player 2
    private boolean isBuildingPhase = false; // Flag to indicate if it's the building phase
    private boolean gameStarted = false; // Flag to indicate if the game has started

    public static void main(String[] args) {
        // Start the game in the Swing event dispatch thread
        SwingUtilities.invokeLater(SantoriniGame::new);
    }

    public SantoriniGame() {
        // Initialize the main game window
        window = new JFrame("Santorini");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(900, 900);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());

        // Create the main menu
        createMainMenu();

        // Show the main menu when the game starts
        window.add(mainMenu, BorderLayout.CENTER);
        window.setVisible(true);
    }

    private void createMainMenu() {
        // Create the main menu panel
        mainMenu = new JPanel() {
            private Image background = new ImageIcon(getClass().getResource("/prototype/emil_chin_jia_zhen/images/mainmenu.png")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };

        mainMenu.setLayout(new BoxLayout(mainMenu, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Santorini"); // Title for the main menu
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.WHITE); // Make text visible on any background
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = new JButton("Start"); // Button to start the game
        JButton quitButton = new JButton("Quit"); // Button to quit the game

        startButton.setFont(new Font("Arial", Font.BOLD, 30));
        quitButton.setFont(new Font("Arial", Font.BOLD, 30));

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.addActionListener(e -> startGame()); // Start the game when the button is clicked
        quitButton.addActionListener(e -> System.exit(0)); // Quit the game when the button is clicked

        // Adding components to the main menu panel
        mainMenu.add(Box.createVerticalStrut(100));
        mainMenu.add(title);
        mainMenu.add(Box.createVerticalStrut(50));
        mainMenu.add(startButton);
        mainMenu.add(Box.createVerticalStrut(20));
        mainMenu.add(quitButton);
    }

    private void startGame() {
        // Start the game by setting the gameStarted flag and initializing the board
        gameStarted = true;
        window.remove(mainMenu); // Remove the main menu
        initializeBoard(); // Initialize the game board
        window.revalidate();
        window.repaint();
    }

    private void initializeBoard() {
        // Create the game board panel with a grid layout
        boardPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, 5, 5)) {
            private Image background = new ImageIcon(getClass().getResource("/prototype/emil_chin_jia_zhen/images/dirt.jpg")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };

        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        boardPanel.setOpaque(false);

        // Initialize the board tiles
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                // Each tile is a JPanel with a background image
                tiles[row][col] = new JPanel() {
                    private Image tileBackground = new ImageIcon(getClass().getResource("/prototype/emil_chin_jia_zhen/images/tile3.jpg")).getImage();

                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.drawImage(tileBackground, 0, 0, getWidth(), getHeight(), this);
                    }
                };

                tiles[row][col].setLayout(new GridBagLayout());
                tiles[row][col].setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));

                int finalRow = row, finalCol = col;
                // Add mouse listener to handle tile clicks
                tiles[row][col].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (gameStarted) handleTileClick(finalRow, finalCol);
                    }
                });

                boardPanel.add(tiles[row][col]);
            }
        }

        // Initialize workers and buildings
        initializeBuildingsAndWorkers();

        // Wrapper panel to center the board on the window
        JPanel wrapperPanel = new JPanel(new GridBagLayout()) {
            private Image background = new ImageIcon(getClass().getResource("/prototype/emil_chin_jia_zhen/images/ocean.jpg")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        wrapperPanel.add(boardPanel);
        window.add(wrapperPanel, BorderLayout.CENTER);

        window.revalidate();
        window.repaint();
    }

    private void initializeBuildingsAndWorkers() {
        // Initialize workers on the grid with initial positions
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new JLabel();
            ImageIcon icon = new ImageIcon(getClass().getResource("/prototype/emil_chin_jia_zhen/images/sb.png"));
            Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            workers[i].setIcon(new ImageIcon(img));
            workers[i].setPreferredSize(new Dimension(50, 50));

            updateWorkerPosition(i); // Update worker position on the board
        }
    }

    private void updateWorkerPosition(int workerIndex) {
        // Update the position of the worker when it moves
        Point oldPos = new Point(workerPositions[workerIndex]);
        Point newPos = workerPositions[workerIndex];

        // Remove worker from the old position
        tiles[oldPos.x][oldPos.y].remove(workers[workerIndex]);
        tiles[oldPos.x][oldPos.y].revalidate();
        tiles[oldPos.x][oldPos.y].repaint();

        // Add worker to the new position
        tiles[newPos.x][newPos.y].add(workers[workerIndex]);
        tiles[newPos.x][newPos.y].revalidate();
        tiles[newPos.x][newPos.y].repaint();

        // Force a full refresh of the entire board
        tiles[newPos.x][newPos.y].getParent().revalidate();
        tiles[newPos.x][newPos.y].getParent().repaint();
    }

    private void handleTileClick(int row, int col) {
        // Handle tile click for movement and building
        if (isBuildingPhase) {
            handleBuild(row, col);
            return;
        }

        Point currentPos = workerPositions[currentPlayer];

        if (Math.abs(currentPos.x - row) <= 1 && Math.abs(currentPos.y - col) <= 1) {
            // Check if the tile is not occupied by another worker
            for (Point pos : workerPositions) {
                if (pos.equals(new Point(row, col))) return;
            }

            int currentLevel = buildingLevels[currentPos.x][currentPos.y];
            int newLevel = buildingLevels[row][col];

            if (newLevel - currentLevel > 1) return;

            workerPositions[currentPlayer] = new Point(row, col); // Move the worker
            updateWorkerPosition(currentPlayer);

            if (buildingLevels[row][col] == 3) {
                showWinMessage(currentPlayer); // Check for win condition
                return;
            }

            isBuildingPhase = true; // Switch to building phase
        }
    }

    private void handleBuild(int row, int col) {
        // Handle building on a tile
        Point workerPos = workerPositions[currentPlayer];

        if (Math.abs(workerPos.x - row) <= 1 && Math.abs(workerPos.y - col) <= 1 &&
                !workerPos.equals(new Point(row, col)) && buildingLevels[row][col] < MAX_BUILD_HEIGHT) {

            for (Point pos : workerPositions) {
                if (pos.equals(new Point(row, col))) return;
            }

            buildingLevels[row][col]++; // Increment building level
            updateBuildingDisplay(row, col); // Update display for the building

            isBuildingPhase = false; // Switch back to movement phase
            currentPlayer = 1 - currentPlayer; // Switch to the other player
        }
    }

    private void updateBuildingDisplay(int row, int col) {
        // Update the display of buildings on the grid
        if (buildings[row][col] == null) {
            buildings[row][col] = new JLabel();
            buildings[row][col].setOpaque(true);
            tiles[row][col].add(buildings[row][col]);
        }

        // Set the building color based on the level
        Color[] buildingColors = {Color.WHITE, Color.LIGHT_GRAY, Color.GRAY, Color.DARK_GRAY, Color.BLACK};
        buildings[row][col].setBackground(buildingColors[buildingLevels[row][col]]);
        buildings[row][col].setPreferredSize(new Dimension(50, 50));
        buildings[row][col].revalidate();
        buildings[row][col].repaint();
    }

    private void showWinMessage(int winner) {
        // Show the win message when a player wins
        int choice = JOptionPane.showOptionDialog(window,
                "Player " + winner + " wins! Restart or Quit?",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Restart", "Quit"},
                "Restart");

        if (choice == JOptionPane.YES_OPTION) {
            restartGame(); // Restart the game
        } else {
            System.exit(0); // Quit the game
        }
    }

    private void restartGame() {
        // Restart the game by removing all components and displaying the main menu
        window.getContentPane().removeAll();
        gameStarted = false;
        createMainMenu(); // Create the main menu
        window.add(mainMenu, BorderLayout.CENTER);
        window.revalidate();
        window.repaint();
    }
}
