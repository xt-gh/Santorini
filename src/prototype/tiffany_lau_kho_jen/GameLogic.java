package prototype.tiffany_lau_kho_jen;


public class GameLogic
{
    private boolean isPlayerOneTurn = true;
    private boolean isBuildingPhase = false;

    public boolean isPlayerOneTurn() {
        return isPlayerOneTurn;
    }

    public boolean isBuildingPhase() {
        return isBuildingPhase;
    }

    public void switchTurn() {
        isPlayerOneTurn = !isPlayerOneTurn;
        isBuildingPhase = false;
    }

    public void enterBuildingPhase() {
        isBuildingPhase = true;
    }

    public String getCurrentPlayerName() {
        return isPlayerOneTurn ? "Player One" : "Player Two";
    }

}

