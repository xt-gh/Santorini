package santorini.game;

/**
 * Static holder for game-end results.
 * Replaces System.exit(0) and JOptionPane calls that originally
 * terminated the Swing application on win/loss/timeout.
 *
 * The GameService checks this after each action to detect game-over state.
 */
public class GameResult {

    private static String winner = null;
    private static String loser = null;
    private static String message = null;

    /**
     * Records a win (e.g., worker moved to level 3).
     *
     * @param playerName the name of the winning player
     */
    public static void recordWin(String playerName) {
        winner = playerName;
        message = playerName + " WINS!!";
    }

    /**
     * Records a loss (e.g., both workers stuck, or time expired).
     *
     * @param playerName the name of the losing player
     * @param reason     the reason for the loss
     */
    public static void recordLoss(String playerName, String reason) {
        loser = playerName;
        message = playerName + " LOSES! " + reason;
    }

    public static boolean isGameOver() {
        return winner != null || loser != null;
    }

    public static String getWinner() {
        return winner;
    }

    public static String getLoser() {
        return loser;
    }

    public static String getMessage() {
        return message;
    }

    /**
     * Resets all game-end state. Called at game start/restart.
     */
    public static void reset() {
        winner = null;
        loser = null;
        message = null;
    }
}
