package santorini.service;

import org.springframework.stereotype.Service;
import santorini.dto.*;
import santorini.game.GameResult;
import santorini.game.actions.MoveAction;
import santorini.game.cards.*;
import santorini.game.characters.Player;
import santorini.game.characters.Worker;
import santorini.game.grounds.Board;
import santorini.game.grounds.Tile;
import santorini.game.towers.Tower;

import java.util.*;

/**
 * Service that orchestrates the Santorini game.
 * Replaces the Swing-based GameController with REST-compatible game management.
 *
 * Preserves the original game flow:
 * 1. Select a worker (click a tile with your worker)
 * 2. Move the worker (click an adjacent tile)
 * 3. Build (click an adjacent tile)
 * 4. Turn ends, next player
 *
 * God card abilities (Artemis double-move, Demeter double-build, Zeus build-under-self)
 * are handled via a pending-choice mechanism instead of JOptionPane dialogs.
 */
@Service
public class GameService {

    private static final int TURN_TIME_LIMIT_SECONDS = 2 * 60; // 2 minutes

    private Board gameBoard;
    private Map<Integer, Player> playerList;
    private Player currentPlayer;
    private int currentPlayerIndex;

    private Tile selectedTile;
    private Tile lastMovedTile;
    private boolean isMoving = false;

    private boolean gameStarted = false;
    private String lastMessage = null;

    // Pending skip card choice
    private boolean pendingSkipCardChoice = false;

    // Timer
    private long turnStartTimeMs;

    /**
     * Creates and initializes a new game.
     * Mirrors the original Application.main() setup logic.
     */
    public GameStateDTO createGame() {
        // Reset any previous game state
        Board.resetInstance();
        GameResult.reset();

        // Create 5x5 board
        gameBoard = Board.createInstance(5, 5);

        // Instantiate god cards and shuffle (same as original)
        List<GodCard> godCardList = instantiateGodCards();

        // Create players (same as original: 2 players, 2 workers each)
        int playerNum = 2;
        List<FunctionCard> functionCardList = instantiateFunctionCards(playerNum);
        playerList = instantiatePlayers(playerNum, 2, godCardList, functionCardList);

        // Initialize game state
        currentPlayerIndex = 0;
        currentPlayer = playerList.get(currentPlayerIndex);
        selectedTile = null;
        lastMovedTile = null;
        isMoving = false;
        pendingSkipCardChoice = false;
        lastMessage = "Game started! " + currentPlayer.getName() + "'s turn. Select a worker.";

        // Randomly place workers (same as original)
        randomiseWorkers();

        // Start timer
        turnStartTimeMs = System.currentTimeMillis();

        gameStarted = true;
        return buildGameState();
    }

    /**
     * Gets the current game state.
     */
    public GameStateDTO getGameState() {
        if (!gameStarted) {
            return null;
        }
        return buildGameState();
    }

    /**
     * Handles a tile click — the core game interaction.
     * Mirrors the original GameController.handleTileClick() logic.
     */
    public GameStateDTO clickTile(int row, int col) {
        if (!gameStarted || GameResult.isGameOver()) {
            lastMessage = "Game is not active.";
            return buildGameState();
        }

        // Reject clicks when there's a pending choice
        if (hasPendingChoice()) {
            lastMessage = "Please resolve the pending choice first.";
            return buildGameState();
        }

        // Check timer
        if (isTurnExpired()) {
            GameResult.recordLoss(currentPlayer.getName(), "Time's up!");
            lastMessage = currentPlayer.getName() + " ran out of time!";
            return buildGameState();
        }

        Tile clickedTile = gameBoard.getTileLocation(row, col);
        if (clickedTile == null) {
            lastMessage = "Invalid tile position.";
            return buildGameState();
        }

        handleTileClick(clickedTile);
        return buildGameState();
    }

    /**
     * Resolves a pending choice (god card ability or skip card).
     */
    public GameStateDTO resolveChoice(boolean accepted) {
        if (!gameStarted || GameResult.isGameOver()) {
            lastMessage = "Game is not active.";
            return buildGameState();
        }

        if (pendingSkipCardChoice) {
            resolveSkipCardChoice(accepted);
            return buildGameState();
        }

        GodCard godCard = currentPlayer.getGodCard();
        if (godCard != null && godCard.hasPendingChoice()) {
            godCard.resolveChoice(accepted);

            // Special handling for Zeus: execute the build after accepting
            if (godCard instanceof Zeus && accepted) {
                ((Zeus) godCard).executePendingBuild(currentPlayer);
                // Check if action completed
                if (currentPlayer.isActionSuccessful()) {
                    currentPlayer.setActionSuccessful(false);
                    selectedTile = null;
                    currentPlayer.clearCurrentWorker();
                    beginTurnTransition();
                }
            }

            // Special handling for Demeter: if declined, mark action successful
            if (godCard instanceof Demeter && !accepted) {
                currentPlayer.setActionSuccessful(true);
                currentPlayer.setActionSuccessful(false);
                selectedTile = null;
                currentPlayer.clearCurrentWorker();
                beginTurnTransition();
            }

            lastMessage = accepted ? "Choice accepted." : "Choice declined.";
        } else {
            lastMessage = "No pending choice to resolve.";
        }

        return buildGameState();
    }

    /**
     * Ends the current game immediately.
     */
    public GameStateDTO endGame() {
        if (!gameStarted || GameResult.isGameOver()) {
            lastMessage = "Game is already over or not active.";
            return buildGameState();
        }
        GameResult.recordLoss(currentPlayer.getName(), currentPlayer.getName() + " surrendered! The game was ended early.");
        lastMessage = GameResult.getMessage();
        return buildGameState();
    }

    /**
     * Completely resets the server state to return to the Home screen.
     */
    public GameStateDTO resetToHome() {
        this.gameStarted = false;
        this.gameBoard = null;
        this.playerList = null;
        this.currentPlayer = null;
        this.selectedTile = null;
        this.lastMovedTile = null;
        this.lastMessage = null;
        return buildGameState();
    }


    // ==================== Core Game Logic (from original GameController) ====================

    /**
     * Handles the logic when a tile is clicked during a player's turn.
     * Mirrors the original GameController.handleTileClick().
     */
    private void handleTileClick(Tile clickedTile) {
        //----------- 1st click: Select a worker -------------
        if (selectedTile == null) {
            if (clickedTile.getWorker() != null && clickedTile.getWorker().getPlayer() == currentPlayer) {
                Worker selectedWorker = clickedTile.getWorker();

                // Check if the selected worker is stuck
                if (isWorkerStuck(clickedTile)) {
                    lastMessage = "This worker is stuck. Please move another worker.";
                    selectedWorker.setBooleanStuck(true);
                    selectedTile = null;
                    checkLosingCondition();
                    return;
                }

                selectedTile = clickedTile;
                lastMovedTile = clickedTile;
                currentPlayer.setCurrentWorker(selectedWorker);
                lastMessage = currentPlayer.getName() + " selected worker at (" + clickedTile.getTileRow() + ", " + clickedTile.getTileColumn() + "). Click a tile to move.";
            } else {
                lastMessage = "Select one of your workers.";
            }

        } else {
            //----------- 2nd click: Move or Build -------------
            boolean canAct;

            // Check adjacency based on god power: Zeus can build on the same tile
            if (currentPlayer.getGodCard() != null && currentPlayer.getGodCard().getName().equalsIgnoreCase("zeus")){
                canAct = isAdjacentOrSame(selectedTile, clickedTile);
            } else {
                canAct = isAdjacent(selectedTile, clickedTile);
            }

            if (!canAct) {
                lastMessage = "Tile not adjacent!";
                return;
            }

            // Prevent acting on a tile occupied by another worker
            Worker currentWorker = currentPlayer.getCurrentWorker();
            if (clickedTile.getWorker() != null && clickedTile.getWorker() != currentWorker) {
                lastMessage = "Tile already occupied by another worker!";
                return;
            }

            //----------- Proceed with the action (move/build) -------------
            Tower tower = clickedTile.getTower();
            if (tower.isTowerEmpty()) {
                tower = new Tower();
            }

            isMoving = currentWorker.executeAction(currentPlayer, selectedTile, clickedTile, tower);

            if (isMoving) {
                lastMovedTile = clickedTile;
                selectedTile = clickedTile;
            }

            // Check for game-end conditions after the action
            if (GameResult.isGameOver()) {
                lastMessage = GameResult.getMessage();
                return;
            }

            // Check if god card has a pending choice
            if (currentPlayer.getGodCard() != null && currentPlayer.getGodCard().hasPendingChoice()) {
                lastMessage = currentPlayer.getGodCard().getChoiceMessage();
                return;
            }
        }

        // Check if the current player's turn is complete
        if (currentPlayer.isActionSuccessful()) {
            currentPlayer.setActionSuccessful(false);
            selectedTile = null;
            currentPlayer.clearCurrentWorker();
            beginTurnTransition();
        }
    }


    /**
     * Begins the turn transition after a player completes their action.
     * Checks for skip card, then advances to next player.
     */
    private void beginTurnTransition() {
        // Check if current player has an unused skip card
        FunctionCard skipCard = currentPlayer.getFunctionCard("Skip Card");
        if (skipCard != null && skipCard.canUse()) {
            pendingSkipCardChoice = true;
            lastMessage = currentPlayer.getName() + ", do you want to use your Skip Card to skip the next player's turn?";
            return;
        }

        // Normal turn advance
        advanceToNextPlayer(false);
    }

    /**
     * Resolves the skip card choice.
     */
    private void resolveSkipCardChoice(boolean accepted) {
        pendingSkipCardChoice = false;

        FunctionCard skipCard = currentPlayer.getFunctionCard("Skip Card");
        if (accepted && skipCard != null) {
            skipCard.markUsed();
            // Skip the next player (advance by 2)
            currentPlayerIndex = (currentPlayerIndex + 2) % playerList.size();
            currentPlayer = playerList.get(currentPlayerIndex);
            lastMessage = "Skip Card used! Next player's turn was skipped. Now it's " + currentPlayer.getName() + "'s turn.";
        } else {
            // Normal advance (by 1)
            advanceToNextPlayer(false);
        }

        // Reset god card state and timer for new turn
        resetForNewTurn();
    }

    /**
     * Advances to the next player.
     */
    private void advanceToNextPlayer(boolean skipOne) {
        int advance = skipOne ? 2 : 1;
        currentPlayerIndex = (currentPlayerIndex + advance) % playerList.size();
        currentPlayer = playerList.get(currentPlayerIndex);
        lastMessage = "Now it's " + currentPlayer.getName() + "'s turn. Select a worker.";

        resetForNewTurn();
    }

    /**
     * Resets state for a new turn.
     */
    private void resetForNewTurn() {
        // Reset god card state for all players
        for (Player player : playerList.values()) {
            if (player.getGodCard() != null) {
                player.getGodCard().resetState();
            }
        }
        isMoving = false;
        turnStartTimeMs = System.currentTimeMillis();
    }


    // ==================== Game Logic Helpers (from original GameController) ====================

    /**
     * Checks whether a worker is stuck (cannot move to any adjacent tile).
     * Mirrors the original GameController.isWorkerStuck().
     */
    private boolean isWorkerStuck(Tile currentTile) {
        int currentRow = currentTile.getTileRow();
        int currentColumn = currentTile.getTileColumn();

        for (int row = currentRow - 1; row <= currentRow + 1; row++) {
            for (int column = currentColumn - 1; column <= currentColumn + 1; column++) {
                if (row == currentRow && column == currentColumn) {
                    continue;
                }
                if (row < 0 || row >= gameBoard.getBoardRows() || column < 0 || column >= gameBoard.getBoardColumns()) {
                    continue;
                }

                Tile neighborTile = gameBoard.getTileLocation(row, column);
                MoveAction testMove = new MoveAction(currentTile, neighborTile, currentTile.getWorker());

                if (testMove.isMoveSuccessful()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if both workers of the current player are stuck.
     * Mirrors the original GameController.checkLosingCondition().
     */
    private void checkLosingCondition() {
        boolean bothWorkersStuck = true;
        for (Worker worker : currentPlayer.getWorkerList()) {
            bothWorkersStuck = bothWorkersStuck && worker.isStuck();
        }
        if (bothWorkersStuck) {
            GameResult.recordLoss(currentPlayer.getName(), "All workers are stuck!");
            lastMessage = GameResult.getMessage();
        }
    }

    /**
     * Randomly places all workers on the board at the start of the game.
     * Mirrors the original GameController.randomiseWorkers().
     */
    private void randomiseWorkers() {
        List<Worker> allWorkers = new ArrayList<>();
        for (int playerNum = 0; playerNum < playerList.size(); playerNum++){
            Player player = playerList.get(playerNum);
            allWorkers.addAll(player.getWorkerList());
            player.getWorkerList().clear();
        }

        int workerIndex = 0;
        Random random = new Random();

        while(workerIndex < allWorkers.size()){
            int row = random.nextInt(gameBoard.getBoardRows());
            int col = random.nextInt(gameBoard.getBoardColumns());
            Tile tile = gameBoard.getTileLocation(row, col);

            if (tile.getWorker() == null){
                Worker worker = allWorkers.get(workerIndex);
                Player player = worker.getPlayer();
                tile.setWorker(worker);
                worker.setPosition(row, col);
                player.addWorker(worker);
                workerIndex++;
            }
        }
    }

    /**
     * Determines whether two tiles are adjacent.
     * Mirrors the original GameController.isAdjacent().
     */
    private boolean isAdjacent(Tile tile1, Tile tile2) {
        int dx = Math.abs(tile1.getTileRow() - tile2.getTileRow());
        int dy = Math.abs(tile1.getTileColumn() - tile2.getTileColumn());
        return (dx <= 1 && dy <= 1) && !(dx == 0 && dy == 0);
    }

    /**
     * Determines whether two tiles are either adjacent or the same tile.
     * Mirrors the original GameController.isAdjacentOrSame().
     */
    private boolean isAdjacentOrSame(Tile tile1, Tile tile2) {
        int dx = Math.abs(tile1.getTileRow() - tile2.getTileRow());
        int dy = Math.abs(tile1.getTileColumn() - tile2.getTileColumn());
        return (dx <= 1 && dy <= 1);
    }

    /**
     * Checks if the current turn has exceeded the time limit.
     */
    private boolean isTurnExpired() {
        long elapsed = System.currentTimeMillis() - turnStartTimeMs;
        return elapsed > (long) TURN_TIME_LIMIT_SECONDS * 1000;
    }

    /**
     * Whether there is any pending choice (god card or skip card).
     */
    private boolean hasPendingChoice() {
        if (pendingSkipCardChoice) return true;
        if (currentPlayer != null && currentPlayer.getGodCard() != null
                && currentPlayer.getGodCard().hasPendingChoice()) return true;
        return false;
    }


    // ==================== Setup Helpers (from original Application) ====================

    /**
     * Instantiates and shuffles the God cards.
     * Mirrors the original Application.instantiateGodCards().
     */
    private List<GodCard> instantiateGodCards() {
        GodCard demeter = new Demeter();
        GodCard artemis = new Artemis();
        GodCard zeus = new Zeus();

        List<GodCard> godCards = new ArrayList<>();
        godCards.add(demeter);
        godCards.add(artemis);
        godCards.add(zeus);

        Collections.shuffle(godCards);
        return godCards;
    }

    /**
     * Instantiates function cards for each player.
     * Mirrors the original Application.instantiateFunctionCards().
     */
    private List<FunctionCard> instantiateFunctionCards(int playerNum) {
        List<FunctionCard> functionCards = new ArrayList<>();
        for (int i = 0; i < playerNum; i++) {
            functionCards.add(new SkipCard());
        }
        return functionCards;
    }

    /**
     * Creates players and assigns GodCards and FunctionCards.
     * Mirrors the original Application.instantiatePlayers(), minus ImageIcon logic.
     */
    private Map<Integer, Player> instantiatePlayers(int playerNum, int workerNum,
                                                     List<GodCard> godCardList,
                                                     List<FunctionCard> functionCardList) {
        Map<Integer, Player> players = new HashMap<>();

        for (int playerNo = 0; playerNo < playerNum; playerNo++) {
            Player player = new Player("Player " + (playerNo + 1), workerNum);
            player.setGodCard(godCardList.get(playerNo));
            player.addFunctionCard(functionCardList.get(playerNo));
            players.put(playerNo, player);
        }
        return players;
    }


    // ==================== DTO Builder ====================

    /**
     * Builds the complete game state DTO from current internal state.
     */
    private GameStateDTO buildGameState() {
        GameStateDTO dto = new GameStateDTO();
        dto.setGameStarted(this.gameStarted);

        if (!gameStarted) {
            dto.setMessage("No game in progress. Create a new game.");
            return dto;
        }

        dto.setBoardRows(gameBoard.getBoardRows());
        dto.setBoardColumns(gameBoard.getBoardColumns());

        // Build tile DTOs
        List<TileDTO> tileDTOs = new ArrayList<>();
        for (int r = 0; r < gameBoard.getBoardRows(); r++) {
            for (int c = 0; c < gameBoard.getBoardColumns(); c++) {
                Tile tile = gameBoard.getTileLocation(r, c);
                Tower tower = tile.getTower();

                int towerLevel = (tower != null) ? tower.getLevelCount() : 0;
                boolean hasDome = (tower != null) && tower.hasDome();
                boolean hasWorker = tile.hasWorker();
                int ownerIndex = -1;
                String ownerName = null;

                if (hasWorker) {
                    Player owner = tile.getWorker().getPlayer();
                    ownerName = owner.getName();
                    for (Map.Entry<Integer, Player> entry : playerList.entrySet()) {
                        if (entry.getValue() == owner) {
                            ownerIndex = entry.getKey();
                            break;
                        }
                    }
                }

                tileDTOs.add(new TileDTO(r, c, towerLevel, hasDome, hasWorker, ownerIndex, ownerName));
            }
        }
        dto.setTiles(tileDTOs);

        // Build player DTOs
        List<PlayerDTO> playerDTOs = new ArrayList<>();
        for (int i = 0; i < playerList.size(); i++) {
            Player player = playerList.get(i);
            PlayerDTO pDto = new PlayerDTO();
            pDto.setPlayerIndex(i);
            pDto.setName(player.getName());

            if (player.getGodCard() != null) {
                pDto.setGodCardName(player.getGodCard().getName());
                pDto.setGodCardDescription(player.getGodCard().getDescription());
            }

            List<WorkerDTO> workerDTOs = new ArrayList<>();
            for (Worker worker : player.getWorkerList()) {
                workerDTOs.add(new WorkerDTO(worker.getRow(), worker.getCol(), worker.isStuck()));
            }
            pDto.setWorkers(workerDTOs);

            // Skip card info
            FunctionCard skipCard = player.getFunctionCard("Skip Card");
            pDto.setHasSkipCard(skipCard != null || player.getFunctionCards().stream()
                    .anyMatch(fc -> fc.getName().equalsIgnoreCase("Skip Card")));
            pDto.setSkipCardUsed(player.getFunctionCards().stream()
                    .filter(fc -> fc.getName().equalsIgnoreCase("Skip Card"))
                    .anyMatch(FunctionCard::isUsed));

            playerDTOs.add(pDto);
        }
        dto.setPlayers(playerDTOs);

        // Current player info
        dto.setCurrentPlayerName(currentPlayer.getName());
        dto.setCurrentPlayerIndex(currentPlayerIndex);

        // Determine phase
        if (GameResult.isGameOver()) {
            dto.setPhase("GAME_OVER");
            dto.setGameOver(true);
            dto.setWinner(GameResult.getWinner());
            dto.setGameOverMessage(GameResult.getMessage());
        } else if (selectedTile == null) {
            dto.setPhase("SELECT_WORKER");
        } else {
            dto.setPhase(isMoving ? "BUILD" : "MOVE");
        }

        // Selected worker
        if (selectedTile != null && currentPlayer.getCurrentWorker() != null) {
            dto.setHasSelectedWorker(true);
            dto.setSelectedWorkerRow(selectedTile.getTileRow());
            dto.setSelectedWorkerCol(selectedTile.getTileColumn());
        }

        // Pending choice
        if (pendingSkipCardChoice) {
            dto.setPendingChoice(true);
            dto.setChoiceType("USE_SKIP_CARD");
            dto.setChoiceMessage(lastMessage);
        } else if (currentPlayer.getGodCard() != null && currentPlayer.getGodCard().hasPendingChoice()) {
            dto.setPendingChoice(true);
            dto.setChoiceType(currentPlayer.getGodCard().getChoiceType());
            dto.setChoiceMessage(currentPlayer.getGodCard().getChoiceMessage());
        }

        // Timer
        dto.setTurnStartTimeMs(turnStartTimeMs);
        dto.setTurnTimeLimitSeconds(TURN_TIME_LIMIT_SECONDS);

        // Message
        dto.setMessage(lastMessage);

        return dto;
    }
}
