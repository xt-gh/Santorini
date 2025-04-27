package sprint2implementation.cards;

import sprint2implementation.main.GameController;


public class Demeter extends GodCard {
    public Demeter(){
        super("Demeter", "can build twice in a turn");
    }

    @Override
    public boolean canBuildTwice(){
        return true;
    }

    @Override
    public void executeSpecialAbility(GameController gameController) {

    }
}
