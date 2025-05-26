package sprint3implementation.cards;

import sprint3implementation.characters.Player;
import sprint3implementation.main.GameController;

public abstract class FunctionCard implements Card{

    protected String name;

    protected String description;

    protected boolean isUsed = false;


    public FunctionCard(String name, String description){
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

//    public abstract void applyCardEffect(Player currentPlayer, GameController gameController);

    public abstract boolean applyCardEffect(Player currentPlayer, GameController gameController);
}
