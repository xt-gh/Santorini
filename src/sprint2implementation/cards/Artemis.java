package sprint2implementation.cards;

import sprint2implementation.main.GameController;


public class Artemis extends GodCard {
    public Artemis(){
        super("Artemis", "can move twice in a turn");
    }

    @Override
    public boolean canMoveTwice(){
        return true;
    }

    @Override
    public void executeSpecialAbility(GameController gameController) {

    }

}
