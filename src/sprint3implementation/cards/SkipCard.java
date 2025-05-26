package sprint3implementation.cards;

import jdk.nashorn.internal.scripts.JO;
import sprint3implementation.characters.Player;
import sprint3implementation.main.GameController;

import javax.swing.*;

public class SkipCard extends FunctionCard{
    public SkipCard() {
        super("Skip Card","You can skip your next player.");
    }


//    @Override
//    public boolean applyCardEffect(Player user, GameController controller) {
//        if(!isUsed){
//
//        }
//        // Skip next player turn by advancing the currentPlayerIndex by 2
//        int playerCount = controller.getPlayerList().size();
//
//        // Assuming GameController has getCurrentPlayerIndex() and setCurrentPlayerIndex()
//        int currentIndex = controller.getCurrentPlayerIndex();
//        int newIndex = (currentIndex + 2) % playerCount;
//
//        controller.setCurrentPlayerIndex(newIndex);
//        controller.setCurrentPlayer(controller.getPlayerList().get(newIndex));
//        controller.updateCurrentPlayerLabel();
//
//        System.out.println(user.getName() + " used Skip Card! Next player's turn skipped.");
//        return true;
//    }


    @Override
    public boolean applyCardEffect(Player currentPlayer, GameController gameController){
        if(isUsed){
            JOptionPane.showMessageDialog(null, "You have already used your Skip Card!", "Card Unavailable", JOptionPane.WARNING_MESSAGE);
            return false;

        }else{
            int response = JOptionPane.showConfirmDialog(
                    null,
                    currentPlayer.getName() + ", do you want to use your Skip Card to skip the next player's turn?",
                    "Use Skip Card?",
                    JOptionPane.YES_NO_OPTION
            );

            if(response == JOptionPane.YES_OPTION){
                int newIndex = (gameController.getCurrentPlayerIndex() + 2) % gameController.getPlayerList().size();
                gameController.setCurrentPlayerIndex(newIndex);
//                int currentPlayerIndex = (gameController.getCurrentPlayerIndex() + 2) % gameController.getPlayerList().size();
                JOptionPane.showMessageDialog(null, "Next player's turn is skipped!", "Skip Card Used", JOptionPane.INFORMATION_MESSAGE);
                isUsed = true;
                return true;

            }else {

                int newIndex = (gameController.getCurrentPlayerIndex() + 1) % gameController.getPlayerList().size();
                gameController.setCurrentPlayerIndex(newIndex);
                return false;
            }
        }
    }
//    @Override
//    public boolean applyCardEffect(Player user, GameController controller) {
//        if (isUsed) {
//            JOptionPane.showMessageDialog(null, "You have already used your Skip Card!", "Card Unavailable", JOptionPane.WARNING_MESSAGE);
//            return false;
//        }
//
//        int response = JOptionPane.showConfirmDialog(
//                null,
//                user.getName() + ", do you want to use your Skip Card to skip the next player's turn?",
//                "Use Skip Card?",
//                JOptionPane.YES_NO_OPTION
//        );
//
//        if (response == JOptionPane.YES_OPTION) {
//            int playerCount = controller.getPlayerList().size();
//            int currentIndex = controller.getCurrentPlayerIndex();
//            int newIndex = (currentIndex + 2) % playerCount;
//
//            controller.setCurrentPlayerIndex(newIndex);
//            controller.setCurrentPlayer(controller.getPlayerList().get(newIndex));
//            controller.updateCurrentPlayerLabel();
//
//            JOptionPane.showMessageDialog(null, "Next player's turn is skipped!", "Skip Card Used", JOptionPane.INFORMATION_MESSAGE);
//            isUsed = true;
//            return true;
//        }
//
//        return false;
//    }

//    @Override
//    public void applyCardEffect(Player currentPlayer, GameController gameController) {
//        if (!isUsed) {
//            JOptionPane.showMessageDialog(
//                    null,
//                    "Your Skip Card has already been used!",
//                    "Error",
//                    JOptionPane.ERROR_MESSAGE
//            );
//            return;
//        }
//
//        int response = JOptionPane.showConfirmDialog(
//                null,
//                currentPlayer.getName() + ", do you want to use Skip Card now?",
//                "Use Skip Card?",
//                JOptionPane.YES_NO_OPTION
//        );
//
//        if (response == JOptionPane.YES_OPTION) {
//            JOptionPane.showMessageDialog(
//                    null,
//                    currentPlayer.getName() + " used a Skip Card!",
//                    "Use Skip Card",
//                    JOptionPane.PLAIN_MESSAGE
//            );
//
//            isUsed = true;
//
//            // Get current index and player list from controller
//            int currentIndex = gameController.getCurrentPlayerIndex();
//            List<Player> players = gameController.getPlayerList();
//
//            // Skip the next player's turn by moving index + 2 modulo player count
//            int newIndex = (currentIndex + 2) % players.size();
//
//            // Update current player in controller
//            gameController.setCurrentPlayerIndex(newIndex);
//            gameController.setCurrentPlayer(players.get(newIndex));
//        }
//    }

//    @Override
//    public void applyCardEffect(Player currentPlayer, GameController gameController) {
//        if(!isUsed){
//            int respoonse = JOptionPane.showConfirmDialog(
//                    null,
//                    currentPlayer.getName() + ", do you want to use Skip Card now?",
//                    "Use Skip Card?",
//                    JOptionPane.YES_NO_OPTION);
//
//            if (respoonse == JOptionPane.YES_OPTION) {
//                JOptionPane.showMessageDialog(
//                        null,
//                        currentPlayer.getName() + " used a Skip Card!",
//                        "Use Skip Card", JOptionPane.PLAIN_MESSAGE);
//
//                isUsed = true;
//
//                // Get current index and player list from controller
//                int currentIndex = gameController.getCurrentPlayerIndex();
//                List<Player> players = gameController.getPlayerList();
//
//                // Skip the next player's turn by moving index + 2 modulo player count
//                int newIndex = (currentIndex + 2) % players.size();
//
//                // Update current player index in game controller
//                gameController.setCurrentPlayerIndex(newIndex);
//
//                // Optionally update current player reference inside game controller
//                gameController.setCurrentPlayer(players.get(newIndex));
//
//
//            }else {
//                JOptionPane.showMessageDialog(null, "Your Skip Card has already been used!", "Error", JOptionPane.ERROR_MESSAGE);
//
//            }
//        if(!isUsed){
//            if (name.equals("Skip Card")) {
//                JOptionPane.showMessageDialog(null, currentPlayer.getName() + " used a Skip Card!", "Use Skip Card", JOptionPane.PLAIN_MESSAGE);
//
//                gameController.skipNextPlayerTurn();
//
//            }else{
//                JOptionPane.showMessageDialog(null, "Your Skip Card has already been used!", "Error", JOptionPane.ERROR_MESSAGE);
//            }


//        }else{
//            JOptionPane.showMessageDialog(null, "Your Skip Card has already been used!", "Error", JOptionPane.ERROR_MESSAGE);

        }
//        if(!isUsed){
//            setUsed(true);
//
//            gameController.skipNextPlayerTurn();
//            JOptionPane.showMessageDialog(null, currentPlayer.getName() + " used a Skip Card!", "Use Skip Card", JOptionPane.PLAIN_MESSAGE);
//
//        }else{
//            JOptionPane.showMessageDialog(null, "Your Skip Card has already been used!", "Error", JOptionPane.ERROR_MESSAGE);
//        }





