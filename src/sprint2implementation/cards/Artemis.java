package sprint2implementation.cards;

public class Artemis extends GodCard {
    public Artemis(){
        super("Artemis", "can move twice in a turn");
    }

    @Override
    public boolean canMoveTwice(){
        return true;
    }
}
