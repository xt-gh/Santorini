package santorini.dto;

import java.util.List;

/**
 * DTO representing the complete game state sent to the frontend.
 */
public class GameStateDTO {

    private int boardRows;
    private int boardColumns;
    private List<TileDTO> tiles;
    private List<PlayerDTO> players;
    private String currentPlayerName;
    private int currentPlayerIndex;
    private String phase;           // "SELECT_WORKER", "MOVE", "BUILD", "GAME_OVER"
    private String message;         // Info/error message for the frontend
    private boolean gameOver;
    private String winner;
    private String gameOverMessage;
    private boolean gameStarted;

    // Pending choice info (god cards / skip card)
    private boolean pendingChoice;
    private String choiceType;      // "SECOND_MOVE", "SECOND_BUILD", "BUILD_UNDER_SELF", "USE_SKIP_CARD"
    private String choiceMessage;

    // Selected worker info
    private int selectedWorkerRow;
    private int selectedWorkerCol;
    private boolean hasSelectedWorker;

    // Timer info
    private long turnStartTimeMs;
    private int turnTimeLimitSeconds;

    // Getters and Setters

    public int getBoardRows() { return boardRows; }
    public void setBoardRows(int boardRows) { this.boardRows = boardRows; }

    public int getBoardColumns() { return boardColumns; }
    public void setBoardColumns(int boardColumns) { this.boardColumns = boardColumns; }

    public List<TileDTO> getTiles() { return tiles; }
    public void setTiles(List<TileDTO> tiles) { this.tiles = tiles; }

    public List<PlayerDTO> getPlayers() { return players; }
    public void setPlayers(List<PlayerDTO> players) { this.players = players; }

    public String getCurrentPlayerName() { return currentPlayerName; }
    public void setCurrentPlayerName(String currentPlayerName) { this.currentPlayerName = currentPlayerName; }

    public int getCurrentPlayerIndex() { return currentPlayerIndex; }
    public void setCurrentPlayerIndex(int currentPlayerIndex) { this.currentPlayerIndex = currentPlayerIndex; }

    public String getPhase() { return phase; }
    public void setPhase(String phase) { this.phase = phase; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }

    public String getWinner() { return winner; }
    public void setWinner(String winner) { this.winner = winner; }

    public String getGameOverMessage() { return gameOverMessage; }
    public void setGameOverMessage(String gameOverMessage) { this.gameOverMessage = gameOverMessage; }

    public boolean isGameStarted() { return gameStarted; }
    public void setGameStarted(boolean gameStarted) { this.gameStarted = gameStarted; }

    public boolean isPendingChoice() { return pendingChoice; }
    public void setPendingChoice(boolean pendingChoice) { this.pendingChoice = pendingChoice; }

    public String getChoiceType() { return choiceType; }
    public void setChoiceType(String choiceType) { this.choiceType = choiceType; }

    public String getChoiceMessage() { return choiceMessage; }
    public void setChoiceMessage(String choiceMessage) { this.choiceMessage = choiceMessage; }

    public int getSelectedWorkerRow() { return selectedWorkerRow; }
    public void setSelectedWorkerRow(int selectedWorkerRow) { this.selectedWorkerRow = selectedWorkerRow; }

    public int getSelectedWorkerCol() { return selectedWorkerCol; }
    public void setSelectedWorkerCol(int selectedWorkerCol) { this.selectedWorkerCol = selectedWorkerCol; }

    public boolean isHasSelectedWorker() { return hasSelectedWorker; }
    public void setHasSelectedWorker(boolean hasSelectedWorker) { this.hasSelectedWorker = hasSelectedWorker; }

    public long getTurnStartTimeMs() { return turnStartTimeMs; }
    public void setTurnStartTimeMs(long turnStartTimeMs) { this.turnStartTimeMs = turnStartTimeMs; }

    public int getTurnTimeLimitSeconds() { return turnTimeLimitSeconds; }
    public void setTurnTimeLimitSeconds(int turnTimeLimitSeconds) { this.turnTimeLimitSeconds = turnTimeLimitSeconds; }
}
