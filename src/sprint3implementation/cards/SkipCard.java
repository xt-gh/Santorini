package sprint3implementation.cards;

import sprint3implementation.characters.Player;
import sprint3implementation.main.GameController;
import javax.swing.*;

/**
 * Represents a Skip Card that allows a player to skip the next player's turn.
 * The card can only be used once. Once activated, it skips one player's turn
 * by advancing the game to the player after the next.
 *
 * @author Xin Thung
 */
public class SkipCard extends FunctionCard {

    /**
     * Constructor for the SkipCard.
     * Sets the name and description of the card.
     */
    public SkipCard() {
        super("Skip Card", "You can skip your next player.");
    }

    /**
     * Activates the effect of the Skip Card.
     * If the card has not been used, prompts the user to confirm usage.
     * If confirmed, it skips the next player's turn.
     * If declined, the turn moves to the next player as usual.
     *
     * @param currentPlayer  The player who is using the card.
     * @param gameController The main game controller managing game state.
     * @return true if the card effect was used; false otherwise.
     */
    @Override
    public boolean activateCardEffect(Player currentPlayer, GameController gameController) {
        if (isUsed) {
            return false;   // card is used -- exit the loop

        } else {
            // ask the player if they want to use the skip card
            int response = JOptionPane.showConfirmDialog(
                    null,
                    currentPlayer.getName() + ", do you want to use your Skip Card to skip the next player's turn?",
                    "Use Skip Card?",
                    JOptionPane.YES_NO_OPTION
            );

            if (response == JOptionPane.YES_OPTION) {
                // skip the next player.s turn by advancing index by 2
                int newIndex = (gameController.getCurrentPlayerIndex() + 2) % gameController.getPlayerList().size();
                gameController.setCurrentPlayerIndex(newIndex);
                JOptionPane.showMessageDialog(null, "Next player's turn is skipped!", "Skip Card Used", JOptionPane.INFORMATION_MESSAGE);
                isUsed = true;
                return true;

            } else {
                // if the player choose not to use it, move to the next player as usual
                int newIndex = (gameController.getCurrentPlayerIndex() + 1) % gameController.getPlayerList().size();
                gameController.setCurrentPlayerIndex(newIndex);
                return false;
            }
        }
    }
}

